package com.galaxy.novelit.notification.service;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.entity.Comment;
import com.galaxy.novelit.comment.repository.CommentRepository;
import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.notification.redis.request.AlarmRedisRequest;
import com.galaxy.novelit.notification.redis.service.AlarmRedisService;
import com.galaxy.novelit.notification.repository.EmitterRepository;
import com.galaxy.novelit.notification.response.NotificationInfo;
import com.galaxy.novelit.notification.response.NotificationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 60 * 1000; // 1시간 지속

    private final EmitterRepository emitterRepository;
    private final DirectoryRepository directoryRepository;
    private final CommentRepository commentRepository;
    private final AlarmRedisService alarmRedisService;


    public SseEmitter subscribe(String lastEventId, String subscriberUUID, HttpServletResponse response)
    {
        String id = subscriberUUID + "_" + System.currentTimeMillis();

        SseEmitter emitter = createEmitter(id);

        sendToClient(emitter, id, "Connection" , new NotificationResponse("Connection", "최초연결"));

        if (!lastEventId.isEmpty()) {
            Map<String, NotificationInfo> events = emitterRepository.findAllEventCacheStartWithId(id);
            events.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendToClient(emitter, entry.getKey(),"Connection" , entry.getValue()));
        }

        return emitter;
    }

    private SseEmitter createEmitter(String id) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
    }


    private void sendToClient(SseEmitter emitter, String id, String name, Object data)
    {
        try{
            emitter.send(SseEmitter.event()
                .id(id)
                .name(name)
                .data(data));
        } catch (IOException exception)
        {
            emitterRepository.deleteById(id);
            emitter.completeWithError(exception);
        }
    }

    public void notifyAllCommentReceiver(CommentAddRequest commentAddRequest, String publisherUUID) {
        Directory directory = directoryRepository.findDirectoryByUuid(commentAddRequest.directoryUUID())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_DIRECTORY, "method: notice. 파일이 없습니다!"));

        NotificationInfo notificationInfo = NotificationResponse.create(commentAddRequest, directory);

        Comment comment = commentRepository.findCommentBySpaceUUID(commentAddRequest.spaceUUID())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        Set<String> subscribers = commentInfoList.stream()
            .map(CommentInfo::getCommentUserUUID)
            .collect(Collectors.toSet());

        for (String subscriberUUID : subscribers) {
            if (subscriberUUID.equals(publisherUUID)) continue;

            Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmittersStartWithId(subscriberUUID);

            sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notificationInfo);

                    sendToClient(emitter, key, "Connection", notificationInfo);

                    alarmRedisService.save(AlarmRedisRequest.builder()
                        .pubName(commentAddRequest.commentNickname())
                        .subUUID(subscriberUUID)
                        .directoryName(directory.getName())
                        .build());
                }
            );

        }
    }
}

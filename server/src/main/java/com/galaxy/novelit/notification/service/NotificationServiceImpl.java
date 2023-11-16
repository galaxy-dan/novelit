package com.galaxy.novelit.notification.service;

import com.galaxy.novelit.comment.domain.Comment;
import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.repository.CommentRepository;
import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.notification.dto.response.NotificationResponseDto;
import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.service.AlarmRedisService;
import com.galaxy.novelit.notification.repository.EmitterRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private static final Long DEFAULT_TIMEOUT = 60L * 60 * 1000; // 1시간 지속

    private final EmitterRepository emitterRepository;
    private final DirectoryRepository directoryRepository;
    private final CommentRepository commentRepository;
    private final AlarmRedisService alarmRedisService;


    public SseEmitter subscribe(String lastEventId, String subscriberUUID, HttpServletResponse response)
    {
        //String id = subscriberUUID;
        String id = subscriberUUID + "_" + System.currentTimeMillis();

        // subscriberUUID
        SseEmitter emitter = createEmitter(id);

        sendToClient(emitter, id, "alertComment" ,NotificationResponseDto.builder()
            .type("Connection")
            .content("최초연결")
            .build());

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(id);
            events.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendToClient(emitter, entry.getKey(),"alertComment" , entry.getValue()));
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


    // 처음 구독
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

    @Override
    // 알림 보낼 로직에 send 메서드 호출하면 됨
    public void notify(String commentNickname, String directoryUUID, String publisherUUID) {
        // 파일 찾기
        Directory directory = directoryRepository.findDirectoryByUuid(
                directoryUUID)
            .orElseThrow(() -> new NoSuchElementFoundException("작품이 없습니다!"));

        // 유저UUID, 파일 이름찾기
        String subscriberUUID = directory.getUserUUID();
        String directoryName = directory.getName();

        // 알림 responseDto 만들기
        NotificationResponseDto notificationResponseDto = NotificationResponseDto.createAlarmComment(commentNickname);


        Map<String,SseEmitter> sseEmitters = emitterRepository.findAllEmittersStartWithId(subscriberUUID);


        sseEmitters.forEach(
            (key, emitter) -> {
                // 데이터 캐시 저장 (유실된 데이터 처리 위함)
                emitterRepository.saveEventCache(key, notificationResponseDto);

                sendToClient(emitter, key, "alertComment", notificationResponseDto);

                // 알림 레디스에 저장
                alarmRedisService.save(AlarmRedisRequestDto.builder()
//                    .pubUUID(publisherUUID)
                    .pubName(commentNickname)
                    .subUUID(subscriberUUID)
                    .directoryName(directoryName)
                    .build());
            }
        );
    }

    @Override
    public void notice(CommentAddRequestDto commentAddRequestDto, String publisherUUID) {
        Directory directory = directoryRepository.findDirectoryByUuid(
                commentAddRequestDto.getDirectoryUUID())
            .orElseThrow(() -> new NoSuchElementFoundException("작품이 없습니다!"));

        String subscriberUUID = directory.getUserUUID();
        String directoryName = directory.getName();

        NotificationResponseDto notificationResponseDto = NotificationResponseDto.create(commentAddRequestDto);

        Comment comment = commentRepository.findCommentBySpaceUUID(
            commentAddRequestDto.getSpaceUUID());
        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        Set<String> userSet = commentInfoList.stream()
            .map(CommentInfo::getUserUUID)
            .collect(Collectors.toSet());

        log.info("userSet : {}", userSet.size());

        if(userSet.size() >= 2) {

            for (String userUUID : userSet) {
                Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmittersStartWithId(
                    userUUID);

                log.info("userSet : 2 : {}", userUUID);

                sseEmitters.forEach(
                    (key, emitter) -> {
                        // 데이터 캐시 저장 (유실된 데이터 처리 위함)
                        emitterRepository.saveEventCache(key, notificationResponseDto);

                        sendToClient(emitter, key, "alertComment", notificationResponseDto);

                        // 알림 레디스에 저장
                        alarmRedisService.save(AlarmRedisRequestDto.builder()
                            .pubName(commentAddRequestDto.getCommentNickname())
                            .subUUID(subscriberUUID)
                            .directoryName(directoryName)
                            .build());
                    }
                );
            }
        }
        else if (userSet.size() == 1){
            if (subscriberUUID.equals(publisherUUID)) return;
            Map<String,SseEmitter> sseEmitters = emitterRepository.findAllEmittersStartWithId(subscriberUUID);

            log.info("userSet : 1 : {}", subscriberUUID);

            sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장 (유실된 데이터 처리 위함)
                    emitterRepository.saveEventCache(key, notificationResponseDto);

                    sendToClient(emitter, key, "alertComment", notificationResponseDto);

                    // 알림 레디스에 저장
                    alarmRedisService.save(AlarmRedisRequestDto.builder()
                        .pubName(commentAddRequestDto.getCommentNickname())
                        .subUUID(subscriberUUID)
                        .directoryName(directoryName)
                        .build());
                }
            );
        }
    }
}

package com.galaxy.novelit.notification.response;

import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.directory.domain.Directory;

public record NotificationResponse (String type,
                                    String content) implements NotificationInfo{
    public static NotificationResponse createAlarmComment(String publisherName){
        return new NotificationResponse("Connection", publisherName + " 님이 댓글을 남겼습니다.");
    }

    public static NotificationResponse create(CommentAddRequest commentAddRequest, Directory directory) {
        return new NotificationResponse("Connection", 
                commentAddRequest.commentNickname() + "님이" +
                        directory.getName() + "에 댓글을 남겼습니다.");
    }
}

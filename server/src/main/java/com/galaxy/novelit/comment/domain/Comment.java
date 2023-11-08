package com.galaxy.novelit.comment.domain;

import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Document(collection = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    private String _id;
    private String spaceUUID;
    private String directoryUUID;
    private List<CommentInfo> commentInfoList;

    public static Comment create(CommentAddRequestDto commentAddRequestDto, String userUUID) {
        List<CommentInfo> commentInfoList = new ArrayList<>();

        UUID uuid = UUID.randomUUID();

        String commentUUID = uuid.toString();

        commentInfoList.add(CommentInfo.builder()
            .commentUUID(commentUUID)
            .commentContent(commentAddRequestDto.getCommentContent())
            .commentNickname(commentAddRequestDto.getCommentNickname())
            .userUUID(userUUID)
            .build());


        return Comment.builder()
            .spaceUUID(commentAddRequestDto.getSpaceUUID())
            .directoryUUID(commentAddRequestDto.getDirectoryUUID())
            .commentInfoList(commentInfoList)
            .build();
    }

    public void updateCommentInfoList(List<CommentInfo> list){
        this.commentInfoList = list;
    }
}

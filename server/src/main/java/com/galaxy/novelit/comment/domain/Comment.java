package com.galaxy.novelit.comment.domain;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.mapper.CommentInfoMapper;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    private String userUUID;
    private String spaceUUID;
    private String directoryUUID;
    private List<CommentInfo> commentInfoList;

    public static Comment create(CommentAddRequestDto commentAddRequestDto) {
        List<CommentInfo> commentInfoList = new ArrayList<>();

        UUID uuid = UUID.randomUUID();

        String commentUUID = uuid.toString();

        commentInfoList.add(CommentInfo.builder()
            .commentUUID(commentUUID)
            .commentContent(commentAddRequestDto.getCommentContent())
            .commentNickname(commentAddRequestDto.getCommentNickname())
            .commentPassword(commentAddRequestDto.getCommentPassword())
            .build());

        log.info("commentInfoList : {}", commentInfoList.get(0).getCommentUUID());

        return Comment.builder()
            .userUUID("f72a8efc-99dc-4afd-a658-6f42073fb7a3")
            .spaceUUID(commentAddRequestDto.getSpaceUUID())
            .directoryUUID(commentAddRequestDto.getDirectoryUUID())
            .commentInfoList(commentInfoList)
            .build();
    }
    public static Comment create(Comment comment, List<CommentInfo> commentInfoList) {

        return Comment.builder()
            .userUUID("f72a8efc-99dc-4afd-a658-6f42073fb7a3")
            .spaceUUID(comment.getSpaceUUID())
            .directoryUUID(comment.getDirectoryUUID())
            .commentInfoList(commentInfoList)
            .build();
    }

    public static List<CommentInfo> dtoListToInfoList(List<CommentInfoDto> commentInfoDtoList) {
        List<CommentInfo> list = new ArrayList<>();
        for (CommentInfoDto dto :commentInfoDtoList) {
            list.add(CommentInfo.dtoToInfo(dto));
        }
        return list;
    }

    public void updateCommentInfoList(List<CommentInfo> list){
        this.commentInfoList = list;
    }
}

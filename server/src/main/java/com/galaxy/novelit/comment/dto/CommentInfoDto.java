package com.galaxy.novelit.comment.dto;

import com.galaxy.novelit.comment.domain.CommentInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInfoDto {
    private String commentUUID;
    private String commentContent;
    private String commentNickname;
    private String userUUID;

    public static CommentInfoDto infotoDto(CommentInfo commentInfo) {
        return CommentInfoDto.builder()
            .commentUUID(commentInfo.getCommentUUID())
            .commentContent(commentInfo.getCommentContent())
            .commentNickname(commentInfo.getCommentNickname())
            .userUUID(commentInfo.getUserUUID())
            .build();
    }


    public static List<CommentInfoDto> infoListToDtoList(List<CommentInfo> commentInfoList) {
        List<CommentInfoDto> dtoList = new ArrayList<>();

        for (CommentInfo info :commentInfoList) {
            dtoList.add(CommentInfoDto.infotoDto(info));
        }

        return dtoList;
    }
}

package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.domain.Comment;
import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentGetRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import com.galaxy.novelit.comment.dto.response.CommentGetResponseDto;
import com.galaxy.novelit.comment.mapper.CommentInfoMapper;
import com.galaxy.novelit.comment.repository.CommentRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentInfoMapper commentInfoMapper;
    @Override
    public void addComment(CommentAddRequestDto commentAddRequestDto) {

        List<CommentInfo> commentInfoList = new ArrayList<>();

        for (CommentInfoDto infoRequestDto: commentAddRequestDto.getCommentList()) {
            CommentInfo commentInfo = CommentInfo.builder()
                .commentOrder(infoRequestDto.getCommentOrder())
                .commentContent(infoRequestDto.getCommentContent())
                .commentId(infoRequestDto.getCommentId())
                .commentPassword(infoRequestDto.getCommentPassword())
                .build();

            commentInfoList.add(commentInfo);
        }

        Comment comment = Comment.builder()
            .spaceId(commentAddRequestDto.getSpaceId())
            .content(commentAddRequestDto.getContent())
            .userUUID(commentAddRequestDto.getUserUUID())
            .directoryUUID(commentAddRequestDto.getDirectoryUUID())
            .commentInfoList(commentInfoList)
            .build();

        commentRepository.save(comment);
    }

    @Override
    public CommentGetResponseDto getAllComments(String directoryUUID, Long spaceId) {

        Comment comment = commentRepository.findByDirectoryUUIDAndSpaceId(directoryUUID, spaceId);

        List<CommentInfoDto> infoDtoList = new ArrayList<>();

        for (CommentInfo info : comment.getCommentInfoList()) {
           infoDtoList.add(commentInfoMapper.infoToDto(info));
        }

        CommentGetResponseDto commentGetResponseDto = new CommentGetResponseDto(
            comment.getDirectoryUUID(),
            comment.getSpaceId(),
            infoDtoList
        );

        return commentGetResponseDto;
    }



    @Override
    public void updateComment(CommentUpdateRequestDto commentUpdateRequestDto) {
        String directoryUUID = commentUpdateRequestDto.getDirectoryUUID();
        Long spaceId = commentUpdateRequestDto.getSpaceId();

        // 코멘트 서치
        Comment comment = commentRepository.findByDirectoryUUIDAndSpaceId(directoryUUID, spaceId);

        // 해당 코멘트 서치
        for (CommentInfo info: comment.getCommentInfoList()) {
            // 소설가인 경우 : 로그인한 사람이랑 같음. 비밀번호없음
            if (info.getCommentId().equals(commentUpdateRequestDto.getUserUUID())){
                info.setCommentContent(commentUpdateRequestDto.getUpdateContent());
                commentRepository.save(comment);
                log.info("novelist");
                return;
            }
            // 편집자인 경우 : 아이디 비번 둘다 확인
            if (info.getCommentId().equals(commentUpdateRequestDto.getCommentId())
                && info.getCommentPassword().equals(commentUpdateRequestDto.getCommentPassword())){
                info.setCommentContent(commentUpdateRequestDto.getUpdateContent());
                commentRepository.save(comment);
                log.info("editor");
                return;
            }
        }

    }

    @Override
    public void deleteComment(CommentDeleteRequestDto commentDeleteRequestDto) {
        String directoryUUID = commentDeleteRequestDto.getDirectoryUUID();
        Long spaceId = commentDeleteRequestDto.getSpaceId();

        // 코멘트 서치
        Comment comment = commentRepository.findByDirectoryUUIDAndSpaceId(directoryUUID, spaceId);

        // commentOrder 서치
        List<CommentInfo> commentInfoList = comment.getCommentInfoList();
        CommentInfo info = getCommentInfo(commentDeleteRequestDto.getCommentOrder(), commentInfoList);

        if (info == null) return;

        // 소설가인 경우 : 비밀번호 없음
        if (info.getCommentId().equals(commentDeleteRequestDto.getUserUUID())){
            commentInfoList.remove(commentDeleteRequestDto.getCommentOrder());
            return;
        }

        // 편집자인 경우
        if (info.getCommentId().equals(commentDeleteRequestDto.getCommentId())
        && info.getCommentPassword().equals(commentDeleteRequestDto.getCommentPassword())){
            commentInfoList.remove(commentDeleteRequestDto.getCommentOrder());
        }
    }

    public CommentInfo getCommentInfo(Long commentOrder, List<CommentInfo> infoList){
        for (CommentInfo info: infoList) {
            if (info.getCommentOrder() == commentOrder){
                return info;
            }
        }
        return null;
    }
}

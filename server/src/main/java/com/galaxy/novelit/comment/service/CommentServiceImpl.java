package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.domain.Comment;
import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import com.galaxy.novelit.comment.repository.CommentInfoRepository;
import com.galaxy.novelit.comment.repository.CommentRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentInfoRepository commentInfoRepository;
    //private final CommentInfoMapper commentInfoMapper;
    @Override
    public void addComment(CommentAddRequestDto commentAddRequestDto) {
        // spaceUUID get
        Comment comment = commentRepository.findCommentBySpaceUUID(commentAddRequestDto.getSpaceUUID());

        // 새롭게 들어갈때
        if (comment == null) {
            log.info("create");
            comment = Comment.create(commentAddRequestDto);
        }
        // 이미 있으면
        else {
            // list에 넣어주기
            List<CommentInfo> commentInfoList = comment.getCommentInfoList();
            commentInfoList.add(CommentInfo.create(commentAddRequestDto));
            // save
            comment = Comment.create(comment, commentInfoList);
        }
        commentRepository.save(comment);
    }

    @Override
    public List<CommentInfoDto> getAllComments(String spaceUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(spaceUUID);

        return CommentInfoDto.infoListToDtoList(comment.getCommentInfoList());
    }



    @Override
    public void updateComment(CommentUpdateRequestDto commentUpdateRequestDto) {
        // 코멘트 서치
        Comment comment = commentRepository.findCommentBySpaceUUID(
            commentUpdateRequestDto.getSpaceUUID());
        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        // 세부 코멘트 서치
        for (CommentInfo info :commentInfoList) {
            // 소설가인 경우 : 로그인한 사람이랑 같음. 비밀번호없음
            if (info.getCommentUUID().equals(commentUpdateRequestDto.getCommentUUID())) {
                if (info.getCommentUUID().equals(commentUpdateRequestDto.getCommentUUID())) {
                    // 내용 업데이트
                    info.updateCommentContent(commentUpdateRequestDto.getCommentContent());
                    comment.updateCommentInfoList(commentInfoList);
                    commentRepository.save(comment);
                    log.info("novelist");
                    break;
                }
                // 편집자인 경우 : 아이디 비번 둘다 확인
                if (info.getCommentNickname().equals(commentUpdateRequestDto.getCommentNickname()) &&
                     info.getCommentPassword().equals(commentUpdateRequestDto.getCommentPassword())) {
                    // 내용 업데이트
                    info.updateCommentContent(commentUpdateRequestDto.getCommentContent());
                    comment.updateCommentInfoList(commentInfoList);
                    commentRepository.save(comment);
                    break;
                }
            }
        }

  /*      commentInfo.get().updateCommentContent(commentUpdateRequestDto.getCommentContent());
        comment.
        commentRepository.save(commentInfo);

        // NoSuchCommentInfoException으로 바꾸기


        if (commentInfo.getCommentUUID().equals(commentInfoDto.getCommentUUID())){

        }

        if (commentInfo.getCommentNickname().equals(commentInfoDto.getCommentNickname())
            && commentInfo.getCommentPassword().equals(commentInfoDto.getCommentPassword())){
            commentInfo.updateCommentContent(commentInfoDto.getCommentContent());
            commentInfoRepository.save(commentInfo);
            log.info("editor");
        }*/

    }

    @Override
    public void deleteComment(CommentDeleteRequestDto commentDeleteRequestDto) {
        // 코멘트 서치
        Comment comment = commentRepository.findCommentBySpaceUUID(
            commentDeleteRequestDto.getSpaceUUID());
        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        // 세부 코멘트 서치
        for (CommentInfo info :commentInfoList) {
            // 소설가인 경우 : 로그인한 사람이랑 같음. 비밀번호없음
            if (info.getCommentUUID().equals(commentDeleteRequestDto.getCommentUUID())) {
                if (info.getCommentUUID().equals(commentDeleteRequestDto.getCommentUUID())) {
                    // 삭제
                    commentInfoList.remove(info);
                    comment.updateCommentInfoList(commentInfoList);
                    commentRepository.save(comment);
                    log.info("novelist");
                    break;
                }
                // 편집자인 경우 : 아이디 비번 둘다 확인
                if (info.getCommentNickname().equals(commentDeleteRequestDto.getCommentNickname()) &&
                    info.getCommentPassword().equals(commentDeleteRequestDto.getCommentPassword())) {
                    // 내용 업데이트
                    commentInfoList.remove(info);
                    comment.updateCommentInfoList(commentInfoList);
                    commentRepository.save(comment);
                    break;
                }
            }
        }
        /*// 코멘트 서치
        CommentInfo commentInfo = commentInfoRepository.findCommentInfoByCommentUUID(
            commentDeleteRequestDto.getCommentUUID())
            .orElseThrow(() -> new NoSuchElementException());
        // NoSuchCommentInfoException으로 바꾸기

        // 소설가인 경우 : 로그인한 사람이랑 같음. 비밀번호없음
        if (commentInfo.getCommentUUID().equals(commentDeleteRequestDto.getCommentUUID())){
            commentInfoRepository.deleteCommentInfoByCommentUUID(
                commentDeleteRequestDto.getCommentUUID());
            log.info("novelist");
        }
        // 편집자인 경우 : 아이디 비번 둘다 확인
        if (commentInfo.getCommentNickname().equals(commentDeleteRequestDto.getCommentNickname())
            && commentInfo.getCommentPassword().equals(commentDeleteRequestDto.getCommentPassword())){
            commentInfoRepository.deleteCommentInfoByCommentUUID(commentDeleteRequestDto.getCommentUUID());
            log.info("editor");
        }*/

    }
}

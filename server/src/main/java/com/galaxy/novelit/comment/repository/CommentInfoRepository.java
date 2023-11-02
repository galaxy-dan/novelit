package com.galaxy.novelit.comment.repository;

import com.galaxy.novelit.comment.domain.CommentInfo;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface CommentInfoRepository extends MongoRepository<CommentInfo, String>{

    //Optional<CommentInfo> findCommentInfoBySpace
    Optional<CommentInfo> findCommentInfoByCommentUUID(String commentUUID);

    Optional<CommentInfo> deleteCommentInfoByCommentUUID(String commentUUID);
}

package com.galaxy.novelit.comment.repository;

import com.galaxy.novelit.comment.domain.Comment;
import com.galaxy.novelit.comment.domain.CommentInfo;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>{
    Comment findCommentBySpaceUUID(String spaceUUID);
}

package com.galaxy.novelit.comment.repository;

import com.galaxy.novelit.comment.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>{
    Optional<Comment> findCommentBySpaceUUID(String spaceUUID);
}

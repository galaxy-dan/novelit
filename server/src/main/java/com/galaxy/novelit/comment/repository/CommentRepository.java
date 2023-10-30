package com.galaxy.novelit.comment.repository;

import com.galaxy.novelit.comment.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Comment findByDirectoryUUIDAndSpaceId(String directoryUUID, Long spaceId);
}

package com.galaxy.novelit.comment.repository;

import com.galaxy.novelit.comment.domain.CommentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// 테스트용 리포지토리
@Repository
public interface CommentInfoRepository extends MongoRepository<CommentInfo, String> {

}

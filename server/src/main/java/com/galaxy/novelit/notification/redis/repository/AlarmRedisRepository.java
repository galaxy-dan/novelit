package com.galaxy.novelit.notification.redis.repository;

import com.galaxy.novelit.notification.redis.dto.response.AlarmRedisResponseDto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRedisRepository extends CrudRepository<AlarmRedisResponseDto, String> {
    List<AlarmRedisResponseDto> findByNotiDto_SubUUID(String subUUID);
}

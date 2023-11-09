package com.galaxy.novelit.notification.redis.repository;

import com.galaxy.novelit.notification.redis.domain.AlarmRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRedisRepository extends CrudRepository<AlarmRedis, String> {
    //List<AlarmRedis> findByNotiDto_SubUUID(String subUUID);
}

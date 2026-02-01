package com.galaxy.novelit.notification.redis.repository;

import com.galaxy.novelit.notification.redis.entity.AlarmRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRedisRepository extends CrudRepository<AlarmRedis, String> {

    Optional<List<AlarmRedis>> findAllByNoti_SubUUID(String subUUID);
}

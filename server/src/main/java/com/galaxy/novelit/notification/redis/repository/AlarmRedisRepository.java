package com.galaxy.novelit.notification.redis.repository;

import com.galaxy.novelit.notification.redis.domain.AlarmRedis;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRedisRepository extends CrudRepository<AlarmRedis, String> {

    Optional<List<AlarmRedis>> findAllByNoti_SubUUID(String subUUID);
}

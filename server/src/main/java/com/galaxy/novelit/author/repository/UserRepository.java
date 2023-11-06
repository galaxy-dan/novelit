package com.galaxy.novelit.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.novelit.author.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserUUID(String userUuid);

    User findByEmail(String email);
}

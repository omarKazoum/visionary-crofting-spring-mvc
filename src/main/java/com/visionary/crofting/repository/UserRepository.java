package com.visionary.crofting.repository;

import com.visionary.crofting.entity.Client;
import com.visionary.crofting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}

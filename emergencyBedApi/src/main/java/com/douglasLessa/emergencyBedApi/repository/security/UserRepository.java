package com.douglasLessa.emergencyBedApi.repository.security;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.douglasLessa.emergencyBedApi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.login = ?1")
    User findByLogin(String login);

    List<User> findAllByLoginOrderByLogin(String login);

    List<User> findAllByOrderByLoginAsc();
}
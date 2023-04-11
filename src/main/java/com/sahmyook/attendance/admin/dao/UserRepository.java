package com.sahmyook.attendance.admin.dao;

import com.sahmyook.attendance.admin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findById(String userId);

    User findByUserId(String userId);

    boolean existsByUserId(String userId);
}

package com.pharm.demo.order.data.repository;

import com.pharm.demo.order.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPhoneNumber(String phoneNumber);
}

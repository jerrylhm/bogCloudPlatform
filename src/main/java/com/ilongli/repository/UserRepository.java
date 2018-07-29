package com.ilongli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ilongli.entity.User;

/**
 * 用户的repo
 * @author ilongli
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}

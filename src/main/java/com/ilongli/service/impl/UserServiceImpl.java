package com.ilongli.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ilongli.entity.User;
import com.ilongli.repository.UserRepository;
import com.ilongli.service.UserService;

/**
 * 用户服务层实现类
 * @author ilongli
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Resource
	public void setUserRepository(UserRepository userRepository) {
		super.setJpaRepository(userRepository);
	}
}
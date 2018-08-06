package com.ilongli.service.impl;

import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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

	private UserRepository userRepository;
	
	@Resource
	public void setUserRepository(UserRepository userRepository) {
		super.setJpaRepository(userRepository);
		this.userRepository = userRepository;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Set<String> findRoles(String username) {
		return userRepository.findRoles(username);
	}

	@Override
	public Set<String> findPermissions(String username) {
		return userRepository.findPermissions(username);
	}

	@Override
	@Transactional
	public boolean correlationRoles(Long userId, Long... roleIds) {
        if(roleIds == null || roleIds.length == 0) {
            return false;
        }
        for(Long roleId : roleIds) {
            if(userRepository.exists(userId, roleId) == 0) {
            	userRepository.correlationRole(userId, roleId);
            }
        }
        return true;
	}

	@Override
	@Transactional
	public boolean uncorrelationRoles(Long userId, Long... roleIds) {
        if(roleIds == null || roleIds.length == 0) {
            return false;
        }
        for(Long roleId : roleIds) {
            if(userRepository.exists(userId, roleId) != 0) {
            	userRepository.uncorrelationRole(userId, roleId);
            }
        }
        return true;
	}

	@Override
	public boolean exists(Long userId, Long roleId) {
		return userRepository.exists(userId, roleId) != 0;
	}
}
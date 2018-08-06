package com.ilongli.service;

import java.util.Set;

import com.ilongli.entity.User;

/**
 * 用户服务层接口
 * @author ilongli
 *
 */
public interface UserService extends BaseService<User, Long> {
	
	/**
	 * 根据用户名查找用户
	 * @param username	用户名
	 * @return	User实体
	 */
	public User findByUsername(String username);
	
	/**
	 * 根据用户名查找该用户的所有角色
	 * @param username	用户名
	 * @return	角色字符串Set集合
	 */
	public Set<String> findRoles(String username);
	
	/**
	 * 根据用户名查找该用户的所有权限
	 * @param username	用户名
	 * @return	权限字符串Set集合
	 */
	public Set<String> findPermissions(String username);

	
	/**
	 * 将用户id和角色id进行绑定
	 * @param userId	用户id
	 * @param roleIds	角色ids
	 * @return	成功返回true，失败返回false
	 */
	public boolean correlationRoles(Long userId, Long... roleIds);
	
	/**
	 * 解除用户id和角色id的绑定
	 * @param userId	用户id
	 * @param roleIds	角色ids
	 * @return	成功返回true，失败返回false
	 */
	public boolean uncorrelationRoles(Long userId, Long... roleIds);

	/**
	 * 检查用户是否存在指定角色
	 * @param userId	用户id
	 * @param roleId	角色id
	 * @return	存在返回true，不存在返回false
	 */
	public boolean exists(Long userId, Long roleId);
}

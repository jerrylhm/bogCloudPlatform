package com.ilongli.repository;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ilongli.entity.User;

/**
 * 用户的repo
 * @author ilongli
 */
public interface UserRepository extends JpaRepository<User, Long> {

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
	@Query("SELECT r.role FROM User u, Role r, UserRole ur "
			+ "WHERE u.username=:username AND u.id=ur.userId AND r.id=ur.roleId")
	public Set<String> findRoles(@Param("username")String username);
	
	/**
	 * 根据用户名查找该用户的所有权限
	 * @param username	用户名
	 * @return	权限字符串Set集合
	 */
	@Query("SELECT p.permission FROM User u, Role r, Permission p, UserRole ur, RolePermission rp "
			+ "WHERE u.username=:username AND u.id=ur.userId AND r.id=ur.roleId AND r.id=rp.roleId AND p.id=rp.permissionId")
	public Set<String> findPermissions(@Param("username")String username);
	
	/**
	 * 将用户id和角色id进行绑定
	 * @param userId	用户id
	 * @param roleId	角色id
	 * @return	成功返回1，失败返回-1
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO sys_users_roles(user_id, role_id) VALUES(:userId,:roleId)", nativeQuery = true)
	public int correlationRole(@Param("userId")Long userId, @Param("roleId")Long roleId);
	
	/**
	 * 解除用户id和角色id的绑定
	 * @param userId	用户id
	 * @param roleId	角色id
	 * @return	成功返回1，失败返回-1
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM UserRole WHERE userId=:userId AND roleId=:roleId")
	public int uncorrelationRole(@Param("userId")Long userId, @Param("roleId")Long roleId);
	
	/**
	 * 检查用户是否存在指定角色
	 * @param userId	用户id
	 * @param roleId	角色id
	 * @return	存在返回1，不存在返回0
	 */
	@Query("SELECT count(1) FROM UserRole WHERE userId=:userId AND roleId=:roleId")
	public int exists(@Param("userId")Long userId, @Param("roleId")Long roleId);
}

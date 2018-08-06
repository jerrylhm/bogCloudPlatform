package com.ilongli.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ilongli.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * 将角色id和权限id进行绑定
	 * @param roleId		角色id
	 * @param permissionId	权限id
	 * @return	成功返回1，失败返回-1
	 */
	@Modifying
	@Query(value = "INSERT INTO sys_roles_permissions(role_id, permission_id) VALUES(:roleId, :permissionId)", nativeQuery = true)
	public int correlationPermission(@Param("roleId")Long roleId, @Param("permissionId")Long permissionId);
	
	/**
	 * 解除角色id和权限id的绑定
	 * @param roleId		角色id
	 * @param permissionId	权限id
	 * @return	成功返回1，失败返回-1
	 */
	@Modifying
	@Query(value = "DELETE FROM RolePermission WHERE roleId=:roleId AND permissionId=:permissionId")
	public int uncorrelationPermission(@Param("roleId")Long roleId, @Param("permissionId")Long permissionId);
	
	/**
	 * 检查角色是否存在指定权限
	 * @param roleId	角色id
	 * @param permissionId	权限id
	 * @return	存在返回1，不存在返回0
	 */
	@Query("SELECT count(1) FROM RolePermission WHERE roleId=:roleId AND permissionId=:permissionId")
	public int exists(@Param("roleId")Long roleId, @Param("permissionId")Long permissionId);
	
	/**
	 * 删除和角色关联的相关数据
	 * @param roleId	角色id
	 * @return	成功返回1，失败返回-1
	 */
	@Modifying
	@Query("DELETE FROM UserRole WHERE roleId=:roleId")
	public int deleteUserRole(@Param("roleId")Long roleId);
}

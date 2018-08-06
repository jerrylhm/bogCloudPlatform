package com.ilongli.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ilongli.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	/**
	 * 删除和权限关联的相关数据
	 * @param roleId	角色id
	 * @return	成功返回1，失败返回-1
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM RolePermission WHERE permissionId=:permissionId")
	public int deleteRolePermission(@Param("permissionId")Long permissionId);
}

package com.ilongli.service;

import com.ilongli.entity.Permission;

/**
 * 权限服务层接口
 * @author ilongli
 *
 */
public interface PermissionService extends BaseService<Permission, Long> {
	
	/**
	 * 删除权限
	 * @param permissionId	权限id
	 * @return	成功返回true，失败返回false
	 */
	public boolean deletePermission(Long permissionId);
	
	/**
	 * 删除和权限关联的相关数据
	 * @param roleId	角色id
	 * @return	成功返回true，失败返回false
	 */
	public boolean deleteRolePermission(Long permissionId);
}

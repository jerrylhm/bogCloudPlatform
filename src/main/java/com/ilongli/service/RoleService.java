package com.ilongli.service;

import com.ilongli.entity.Role;

/**
 * 角色服务层接口
 * @author ilongli
 *
 */
public interface RoleService extends BaseService<Role, Long> {
	
	/**
	 * 删除角色
	 * @param roleId	角色id
	 * @return	成功返回true，失败返回false
	 */
	public boolean deleteRole(Long roleId);
	
	/**
	 * 将角色id和权限id进行绑定
	 * @param roleId		角色id
	 * @param permissionIds	权限ids
	 * @return	成功返回true，失败返回false
	 */
	public boolean correlationPermissions(Long roleId, Long... permissionIds);

	
	/**
	 * 解除角色id和权限id的绑定
	 * @param roleId		角色id
	 * @param permissionIds	权限ids
	 * @return	成功返回true，失败返回false
	 */
	public boolean uncorrelationPermissions(Long roleId, Long... permissionIds);
	
	
	/**
	 * 检查角色是否存在指定权限
	 * @param roleId	角色id
	 * @param permissionId	权限id
	 * @return	存在返回true，不存在返回false
	 */
	public boolean exists(Long roleId, Long permissionId);

	/**
	 * 删除和角色关联的相关数据
	 * @param roleId	角色id
	 * @return	成功返回true，失败返回false
	 */
	public boolean deleteUserRole(Long roleId);
}

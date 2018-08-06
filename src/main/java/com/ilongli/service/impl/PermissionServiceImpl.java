package com.ilongli.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ilongli.entity.Permission;
import com.ilongli.repository.PermissionRepository;
import com.ilongli.service.PermissionService;

/**
 * 权限服务层实现类
 * @author ilongli
 *
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements PermissionService {

	private PermissionRepository permissionRepository;
	
	@Resource
	public void setPermissionRepository(PermissionRepository permissionRepository) {
		super.setJpaRepository(permissionRepository);
		this.permissionRepository = permissionRepository;
	}

	@Override
	@Transactional
	public boolean deletePermission(Long permissionId) {
		if(permissionId == null) {
			return false;
		}
		//首先把与permission关联的相关表的数据删掉
		permissionRepository.deleteRolePermission(permissionId);
		permissionRepository.deleteById(permissionId);
		return true;
	}
	
	@Override
	@Transactional
	public boolean deleteRolePermission(Long permissionId) {
		return permissionRepository.deleteRolePermission(permissionId) != -1;
	}

}

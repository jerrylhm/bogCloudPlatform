package com.ilongli.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ilongli.entity.Role;
import com.ilongli.repository.RoleRepository;
import com.ilongli.service.RoleService;

/**
 * 角色服务层实现类
 * @author ilongli
 *
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

	private RoleRepository roleRepository;
	
	@Resource
	public void setRoleRepository(RoleRepository roleRepository) {
		super.setJpaRepository(roleRepository);
		this.roleRepository = roleRepository;
	}
	
	@Override
	@Transactional
	public boolean deleteRole(Long roleId) {
		if(roleId == null) {
			return false;
		}
		//首先把和role关联的相关表数据删掉
		roleRepository.deleteUserRole(roleId);
		roleRepository.deleteById(roleId);
		return true;
	}

	@Override
	@Transactional
	public boolean correlationPermissions(Long roleId, Long... permissionIds) {
        if(permissionIds == null || permissionIds.length == 0) {
            return false;
        }
        for(Long permissionId : permissionIds) {
            if(roleRepository.exists(roleId, permissionId) == 0) {
            	roleRepository.correlationPermission(roleId, permissionId);
            }
        }
		return true;
	}

	@Override
	@Transactional
	public boolean uncorrelationPermissions(Long roleId, Long... permissionIds) {
        if(permissionIds == null || permissionIds.length == 0) {
            return false;
        }
        for(Long permissionId : permissionIds) {
            if(roleRepository.exists(roleId, permissionId) != 0) {
            	roleRepository.uncorrelationPermission(roleId, permissionId);
            }
        }
		return true;
	}

	@Override
	public boolean exists(Long roleId, Long permissionId) {
		return roleRepository.exists(roleId, permissionId) != 0;
	}

	@Override
	@Transactional
	public boolean deleteUserRole(Long roleId) {
		return roleRepository.deleteUserRole(roleId) != -1;
	}

}

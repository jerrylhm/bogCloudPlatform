package com.ilongli.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ilongli.service.BaseService;

/**
 * 基础服务层实现类
 * @author ilongli
 * @param <T>	实体类
 * @param <ID>	主键类型
 * 
 * jpa继承关系:
 * Repository
 *  -CrudRepository
 *   -PagingAndSortingRepository
 *    -JpaRepository
 *
 * 增强类:
 * JpaSpecificationExecutor
 * 
 */
@Service
public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

	private JpaRepository<T, ID> jpaRepository;
	
	public void setJpaRepository(JpaRepository<T, ID> jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
	/**
	 * 增
	 */
	@Override
	@Transactional
	@Modifying
	public T save(T entity) {
		return jpaRepository.save(entity);
	}
	
	/**
	 * 删
	 */
	@Override
	@Transactional
	@Modifying
	public void delete(ID id) {
		jpaRepository.deleteById(id);
	}
	
	/**
	 * 改
	 */
	@Override
	@Transactional
	@Modifying
	public T update(T entity) {
		return jpaRepository.save(entity);
	}
	
	
	/**
	 * 查
	 */
	@Override
	public T find(ID id) {
		return jpaRepository.findById(id).get();
	}
	
	@Override
	public List<T> findAll() {
		return jpaRepository.findAll();
	}
	
}

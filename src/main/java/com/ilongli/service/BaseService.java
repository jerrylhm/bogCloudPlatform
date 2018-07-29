package com.ilongli.service;

import java.util.List;

/**
 * 基础服务层接口
 * @author ilongli
 * @param <T>	实体类
 * @param <ID>	主键类型
 * 
 */
public interface BaseService<T, ID> {
	public T save(T entity);

	public void delete(ID id);

	public T update(T entity);
	
	public T find(ID id);

	public List<T> findAll();
}

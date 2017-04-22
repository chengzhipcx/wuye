package com.onlymvp.dao;

import java.io.Serializable;

/**
 * 通用增,删,改方法接口
 * 
 * chengzhi
 *
 * @param <T>
 *            要操作的范型
 */
public interface BaseDAO<T> {

	public Serializable insert(T t) throws Exception;

	public boolean update(T t) throws Exception;

	public void delete(T t) throws Exception;
	
	public boolean deleteById(Integer id) throws Exception;
	
	public T queryById(Integer id) throws Exception;

}

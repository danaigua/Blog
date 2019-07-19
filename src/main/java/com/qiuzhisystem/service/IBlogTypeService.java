package com.qiuzhisystem.service;

import java.util.List;
import java.util.Map;

import com.qiuzhisystem.entity.BlogType;

/**
 * 博客类别service接口
 * @author JOB
 *
 */
public interface IBlogTypeService {
	/**
	 * 查询所有的博客类型，以及对应的博客数量
	 * @return
	 */
	public List<BlogType> countList();
	/**
	 * 分页查询博客类别信息
	 * @param map
	 * @return
	 */
	public List<BlogType> list(Map<String, Object> map);
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	/**
	 * 修改
	 * @param blogType
	 * @return
	 */
	public Integer update (BlogType blogType);
	/**
	 * 删除博客
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	/**
	 * 添加
	 * @param blogType
	 * @return
	 */
	public Integer add (BlogType blogType);
}
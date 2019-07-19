package com.qiuzhisystem.dao;
/**
 * 博客类型dao接口
 * @author JOB
 *
 */

import java.util.List;
import java.util.Map;

import com.qiuzhisystem.entity.BlogType;

public interface IBlogTypeDao {
	/**
	 * 查询所有的博客类型，以及对应的博客数量
	 * @return
	 */
	public List<BlogType> countList();
	/**
	 * 根据id查找实体
	 */
	public BlogType findById(Integer id);
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
	 * 添加
	 * @param blogType
	 * @return
	 */
	public Integer add (BlogType blogType);
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
}

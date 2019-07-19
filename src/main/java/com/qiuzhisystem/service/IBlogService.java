package com.qiuzhisystem.service;

import java.util.List;
import java.util.Map;

import com.qiuzhisystem.entity.Blog;

/**
 * 博客接口类
 * @author JOB
 *
 */
public interface IBlogService {
	/**
	 * 根据日期月份分组查询
	 * @return
	 */
	public List<Blog> countList();
	/**
	 * 分页查询博客
	 * @param map
	 * @return
	 */
	public List<Blog> list(Map<String, Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public Blog findById(Integer id);
	/**
	 * 更新博客内容
	 * @param blog
	 * @return
	 */
	public Integer update(Blog blog);
	/**
	 * 获取上一个博客
	 * @param id
	 * @return
	 */
	public Blog getLastBlog(Integer id);
	/**
	 * 获取下一个博客
	 * @param id
	 * @return
	 */
	public Blog getNextBlog(Integer id);
	/**
	 * 添加一个博客
	 * @param blog
	 * @return
	 */
	public Integer add(Blog blog);
	/**
	 * 删除博客信息
	 * @return
	 */
	public Integer delete(Integer id);
	/**
	 * 通过id查找实体
	 * @param typeId
	 * @return
	 */
	public Integer getBlogByTypeId(Integer typeId);
}

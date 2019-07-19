package com.qiuzhisystem.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qiuzhisystem.dao.IBlogTypeDao;
import com.qiuzhisystem.entity.BlogType;
import com.qiuzhisystem.service.IBlogTypeService;

/**
 * 博客类别的实现类
 * @author JOB
 *
 */
@Service("blogTypeService")
public class BlogTypeServiceImpl implements IBlogTypeService{
	@Resource
	private IBlogTypeDao blogTypeDao;
	
	public List<BlogType> countList() {
		return blogTypeDao.countList();
	}

	public List<BlogType> list(Map<String, Object> map) {
		return blogTypeDao.list(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return blogTypeDao.getTotal(map);
	}

	public Integer update(BlogType blogType) {
		return blogTypeDao.update(blogType);
	}

	public Integer delete(Integer id) {
		return blogTypeDao.delete(id);
	}

	public Integer add(BlogType blogType) {
		return blogTypeDao.add(blogType);
	}

}

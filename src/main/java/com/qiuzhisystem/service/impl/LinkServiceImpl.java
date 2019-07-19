package com.qiuzhisystem.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qiuzhisystem.dao.ILinkDao;
import com.qiuzhisystem.entity.Link;
import com.qiuzhisystem.service.ILinkService;
/**
 * linkservice接口的实现类
 * @author JOB
 *
 */
@Service("linkService")
public class LinkServiceImpl implements ILinkService{

	@Resource
	private ILinkDao linkDao;

	public List<Link> list(Map<String, Object> map) {
		return linkDao.list(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return null;
	}

	public Integer add(Link link) {
		return linkDao.add(link);
	}

	public Integer update(Link link) {
		return linkDao.update(link);
	}

	public Integer delete(Integer id) {
		return linkDao.delete(id);
	}
	
	
}

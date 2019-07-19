package com.qiuzhisystem.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qiuzhisystem.dao.IBlogerDao;
import com.qiuzhisystem.entity.Bloger;
import com.qiuzhisystem.service.IBlogerService;
/**
 * 博主的service实现类
 * @author 12952
 *
 */
@Service("blogerService")
public class BlogerServiceImpl implements IBlogerService {
	@Resource
	private IBlogerDao blogerDao;

	public Bloger getByUserName(String userName) {
		return blogerDao.getByUserName(userName);
	}

	public Bloger find() {
		return blogerDao.find();
	}

	public Integer update(Bloger bloger) {
		return blogerDao.update(bloger);
	}
}

package com.qiuzhisystem.service;
/**
 * 博主的service接口
 * @author 12952
 *
 */

import com.qiuzhisystem.dao.IBlogerDao;
import com.qiuzhisystem.entity.Bloger;

public interface IBlogerService {
	/**
	 * 通过用户名查找实体
	 * 
	 * @param name
	 * @return
	 */
	public Bloger getByUserName(String userName);
	/**
	 * 查询博主详细
	 * @return
	 */
	public Bloger find();
	/**
	 * 更新博主信息
	 * @param bloger
	 * @return
	 */
	public Integer update(Bloger bloger);
}

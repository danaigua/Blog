package com.qiuzhisystem.service;

import java.util.List;
import java.util.Map;

import com.qiuzhisystem.entity.Link;

/**
 * 友情链接的service接口
 * @author JOB
 *
 */
public interface ILinkService {
	/**
	 * 列出所有友情链接
	 * @param map
	 * @return
	 */
	public List<Link> list(Map<String, Object> map);
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	/**
	 * 添加一个链接
	 * @param link
	 * @return
	 */
	public Integer add(Link link);
	/**
	 * 修改一个链接
	 * @param link
	 * @return
	 */
	public Integer update(Link link);
	/**
	 * 删除一个链接
	 * @param link
	 * @return
	 */
	public Integer delete(Integer id);
}

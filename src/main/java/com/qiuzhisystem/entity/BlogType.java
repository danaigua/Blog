package com.qiuzhisystem.entity;
/**
 * 博客类型实体类
 * 博客数据字典
 * @author JOB
 *
 */
public class BlogType {
	
	private Integer id;//编号
	private String typeName;//博客类型名称
	private Integer orderNo;//排序序号 从小到大
	private Integer blogCount;//属性数量
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	
}

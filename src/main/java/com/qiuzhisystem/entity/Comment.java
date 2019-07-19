package com.qiuzhisystem.entity;
/**
 * 评论类的实体
 * @author JOB
 *
 */

import java.util.Date;

public class Comment {

	private Integer id;
	private String userIp;//评论ip地址
	private String content;//评论内容
	private Blog blog;//被评论的博客
	private Date commentDate;//评论日期
	private Integer state;//审核状态：0待审核 1通过 2未通过
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
}

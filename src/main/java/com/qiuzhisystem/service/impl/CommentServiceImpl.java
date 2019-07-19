package com.qiuzhisystem.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qiuzhisystem.dao.ICommentDao;
import com.qiuzhisystem.entity.Comment;
import com.qiuzhisystem.service.ICommentService;
@Service("commentService")
public class CommentServiceImpl implements ICommentService{

	@Resource
	private ICommentDao commentDao;
	
	
	public List<Comment> list(Map<String, Object> map) {
		return commentDao.list(map);
	}
	public Integer add(Comment comment) {
		return commentDao.add(comment);
	}
	public Long getTotal(Map<String, Object> map) {
		return commentDao.getTotal(map);
	}
	public int update(Comment comment) {
		return commentDao.update(comment);
	}
	public int delete(Integer id) {
		return commentDao.delete(id);
	}


}

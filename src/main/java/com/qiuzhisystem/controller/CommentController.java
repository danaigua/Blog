package com.qiuzhisystem.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiuzhisystem.entity.Blog;
import com.qiuzhisystem.entity.Comment;
import com.qiuzhisystem.service.IBlogService;
import com.qiuzhisystem.service.ICommentService;
import com.qiuzhisystem.utils.ResponseUtil;

import net.sf.json.JSONObject;

/**
 * 评论的控制层
 * 
 * @author JOB
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private ICommentService commentService;

	@Resource
	private IBlogService blogService;

	/**
	 * 添加或者修改评论
	 * @param comment
	 * @param imageCode
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Comment comment, @RequestParam("imageCode") String imageCode, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		String sRand = (String) session.getAttribute("sRand");//获取正确的验证码
		JSONObject result = new JSONObject();
		int resultTotal = 0;
		if(!imageCode.equals(sRand)) {
			result.put("success",false);
			result.put("errorInfo", "验证码错误");
		}else {
			String userIp = request.getRemoteAddr();//获取用户id
			comment.setUserIp(userIp);
			if(comment.getId() == null) {
				resultTotal = commentService.add(comment);
				//实现回复次数+1
				Blog blog = blogService.findById(comment.getBlog().getId());
				blog.setReplyHit(blog.getReplyHit() + 1);
				blogService.update(blog);
			}else {
				//修改操作
			}
			if(resultTotal > 0) {
				result.put("success", true);
			}else {
				
				result.put("success", false);
			}
		}
		ResponseUtil.write(response, result);
		return null;
	}
}

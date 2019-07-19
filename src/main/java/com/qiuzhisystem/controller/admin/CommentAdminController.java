package com.qiuzhisystem.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiuzhisystem.entity.Blog;
import com.qiuzhisystem.entity.Comment;
import com.qiuzhisystem.entity.PageBean;
import com.qiuzhisystem.service.ICommentService;
import com.qiuzhisystem.utils.ResponseUtil;
import com.qiuzhisystem.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 管理员评论模块
 * @author JOB
 *
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

	@Resource
	private ICommentService commentService;
	/**
	 * 分页查询评论信息
	 * @param page
	 * @param rows
	 * @param state
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			@RequestParam(value = "state", required = false) String state, HttpServletResponse response)
			throws Exception {
		if(StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", state);
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Comment> commentList = commentService.list(map);
		Long total = commentService.getTotal(map);
		JSONObject result = new JSONObject();
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(commentList, config);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	/**
	 * 评论审核
	 * @param ids
	 * @param response
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/review")
	public String review(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response, @RequestParam(value = "state", required = false) String state) throws Exception {
		String[] idsStr = ids.split(",");
		for(int i = 0; i < idsStr.length; i++) {
			Comment comment = new Comment();
			comment.setId(Integer.parseInt(idsStr[i]));
			comment.setState(Integer.parseInt(state));
			commentService.update(comment);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	/**
	 * 评论信息删除
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response) throws Exception {
		String[] idsStr = ids.split(",");
		for(int i = 0; i < idsStr.length; i++) {
			commentService.delete(Integer.parseInt(idsStr[i]));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}

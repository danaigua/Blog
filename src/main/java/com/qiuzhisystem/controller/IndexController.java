package com.qiuzhisystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 主页Controller
 * @author JOB
 *
 */
import org.springframework.web.servlet.ModelAndView;

import com.qiuzhisystem.entity.Blog;
import com.qiuzhisystem.entity.PageBean;
import com.qiuzhisystem.service.IBlogService;
import com.qiuzhisystem.utils.StringUtil;
@Controller
@RequestMapping("/")
public class IndexController {
	@Resource
	private IBlogService blogService;
	
	/**
	 * 请求主页
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "page",required = false)String page) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Blog> blogList = blogService.list(map);
		mav.addObject("blogList", blogList);
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		mav.addObject("pageTitle", "博客系统");
		mav.setViewName("mainTemp");
		return mav;
		
	}
}

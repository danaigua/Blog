package com.qiuzhisystem.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qiuzhisystem.entity.Bloger;
import com.qiuzhisystem.service.IBlogerService;
import com.qiuzhisystem.utils.Md5Util;
/**
 * 博主控制器
 * @author JOB
 *
 */
@Controller
@RequestMapping("/bloger")
public class BlogerController {
	
	@Resource
	private IBlogerService blogerService;
	/**
	 * 登陆控制器
	 * @param bloger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Bloger bloger, HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();//获取当前登陆的用户
		UsernamePasswordToken token = new UsernamePasswordToken(bloger.getUserName(), Md5Util.md5(bloger.getPassword(), "qiuzhisystem"));
		try {
			subject.login(token);//登陆认证
			return "redirect:/admin/main.jsp";
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("bloger", bloger);
			request.setAttribute("errorInfo", "用户名或者密码错误！");
			return "login";
		}
	}
	
}

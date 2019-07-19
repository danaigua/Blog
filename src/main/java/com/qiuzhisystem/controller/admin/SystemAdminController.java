package com.qiuzhisystem.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 管理员系统controller层
 * @author JOB
 *
 */

import com.qiuzhisystem.entity.Blog;
import com.qiuzhisystem.entity.BlogType;
import com.qiuzhisystem.entity.Bloger;
import com.qiuzhisystem.entity.Link;
import com.qiuzhisystem.service.IBlogService;
import com.qiuzhisystem.service.IBlogTypeService;
import com.qiuzhisystem.service.IBlogerService;
import com.qiuzhisystem.service.ILinkService;
import com.qiuzhisystem.utils.ResponseUtil;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Resource
	private IBlogerService blogerService;
	
	@Resource
	private ILinkService linkService;
	
	@Resource
	private IBlogTypeService blogTypeService;
	
	@Resource
	private IBlogService blogService;
	/**
	 * 刷新系统缓存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext application=RequestContextUtils.findWebApplicationContext(request).getServletContext();
		Bloger blogger=blogerService.find(); // 获取博主信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		List<Link> linkList=linkService.list(null); // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);
		
		List<BlogType> blogTypeCountList=blogTypeService.countList(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		List<Blog> blogCountList= blogService.countList(); // 根据日期分组查询博客
		application.setAttribute("blogCountList", blogCountList);
		
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
		
	}
}

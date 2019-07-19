package com.qiuzhisystem.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.qiuzhisystem.entity.Blog;
import com.qiuzhisystem.entity.BlogType;
import com.qiuzhisystem.entity.Bloger;
import com.qiuzhisystem.entity.Link;
import com.qiuzhisystem.service.IBlogService;
import com.qiuzhisystem.service.IBlogTypeService;
import com.qiuzhisystem.service.IBlogerService;
import com.qiuzhisystem.service.ILinkService;

@Component
public class InitComponent implements ServletContextListener, ApplicationContextAware{
	private static ApplicationContext applicationContext;
	//初始化的时候调用
	//把博主信息放在application中
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		IBlogerService blogerService = (IBlogerService) applicationContext.getBean("blogerService");
		Bloger bloger = blogerService.find();
		bloger.setPassword(null);
		application.setAttribute("blogger", bloger);
		
		ILinkService linkService = (ILinkService) applicationContext.getBean("linkService");
		List<Link> linkList = linkService.list(null);//查询所有的友情链接信息
		application.setAttribute("linkList", linkList);
		
		//查询所有的博客类别
		IBlogTypeService blogTypeService = (IBlogTypeService)applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeCountList = blogTypeService.countList();
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		//按照日志年月分类
		IBlogService blogService = (IBlogService)applicationContext.getBean("blogService");
		List<Blog> blogCountList = blogService.countList();
		application.setAttribute("blogCountList", blogCountList);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}

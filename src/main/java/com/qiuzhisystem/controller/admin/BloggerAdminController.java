package com.qiuzhisystem.controller.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 管理员博主controller层
 * @author JOB
 *
 */
import org.springframework.web.multipart.MultipartFile;

import com.qiuzhisystem.entity.Bloger;
import com.qiuzhisystem.service.IBlogerService;
import com.qiuzhisystem.utils.DateUtil;
import com.qiuzhisystem.utils.Md5Util;
import com.qiuzhisystem.utils.ResponseUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Resource
	private IBlogerService blogerService;

	/**
	 * 查询博主信息
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/find")
	public String find(HttpServletResponse response) throws Exception {
		Bloger bloger = blogerService.find();
		JSONObject jsonObject = JSONObject.fromObject(bloger);
		ResponseUtil.write(response, jsonObject);
		return null;
	}

	/**
	 * 修改博主信息
	 * 
	 * @param imageFile
	 * @param bloger
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(@RequestParam("imageFile") MultipartFile imageFile, Bloger bloger, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		if (!imageFile.isEmpty()) {
			String filePath = request.getServletContext().getRealPath("/");// 获取服务器路径
			String imageName = DateUtil.getCurrentTime() + "." + imageFile.getOriginalFilename().split("\\.")[1];//解析名称
			imageFile.transferTo(new File(filePath + "static/userImage" + imageName));//转移到指定路径
			bloger.setImageName(imageName);
		}
		int resultTotal = blogerService.update(bloger);
		StringBuffer result = new StringBuffer();
		if(resultTotal>0) {
			result.append("<script language='javascript'>alert('修改成功');</script>;");
		}else {
			result.append("<script language='javascript'>alert('修改失败');</script>;");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	/**
	 * 修改博主密码
	 * @param newPassword
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String newPassword,HttpServletResponse response)throws Exception{
		Bloger blogger=new Bloger();
		blogger.setPassword(Md5Util.md5(newPassword,"qiuzhisystem"));
		int resultTotal=blogerService.update(blogger);
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout()throws Exception{
		SecurityUtils.getSubject().logout(); 
		return "redirect:/login.jsp";
	}
}

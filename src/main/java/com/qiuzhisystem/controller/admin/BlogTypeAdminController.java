package com.qiuzhisystem.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiuzhisystem.entity.BlogType;
import com.qiuzhisystem.entity.PageBean;
import com.qiuzhisystem.service.IBlogService;
import com.qiuzhisystem.service.IBlogTypeService;
import com.qiuzhisystem.utils.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 管理员博客类别controller层
 * @author JOB
 *
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

	@Resource
	private IBlogTypeService blogTypeService;
	@Resource
	private IBlogService blogService;
	/**
	 * 分页查询博客类别
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false)String rows, HttpServletResponse response) throws Exception{
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<BlogType> blogTypeList = blogTypeService.list(map);
		Long total = blogTypeService.getTotal(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(blogTypeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	/**
	 * 添加或者修改博客列别信息
	 * @param blogType
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(BlogType blogType, HttpServletResponse response)throws Exception{
		int resultTotal = 0;
		if(blogType.getId()==null) {
			resultTotal =blogTypeService.add(blogType);
		}else {
			resultTotal =blogTypeService.update(blogType);
		}
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids", required=false) String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		JSONObject result=new JSONObject();
		for(int i=0;i<idsStr.length;i++){
			if(blogService.getBlogByTypeId(Integer.parseInt(idsStr[i]))>0){
				result.put("exist", "博客类别下有博客，不能删除！");
			}else{
				blogTypeService.delete(Integer.parseInt(idsStr[i]));				
			}
		}
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}

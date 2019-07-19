package com.qiuzhisystem.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * @author JOB
 *
 */
public class StringUtil {

	/**
	 * 判断字符串是否未空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str == null || "".equals(str.trim())) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if(! isEmpty(str)) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 模糊查询
	 * @param str
	 * @return
	 */
	public static String formatLike(String str) {
		if(isNotEmpty(str)) {
			return "%" + str + "%";
		}else {
			return null;
		}
	}
	/**
	 * 过滤集合里面的空格
	 * @param list
	 * @return
	 */
	public static List<String> filterWhite(List<String> list){
		List<String> resultList = new ArrayList<String>();
		for (String string : list) {
			if(isNotEmpty(string)) {
				resultList.add(string);
			}
		}
		return resultList;
	}
}

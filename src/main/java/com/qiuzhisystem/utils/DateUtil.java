package com.qiuzhisystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * 将日期转换为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) throws Exception {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(date != null) {
			result = sdf.format(date);
		}
		return result;
	}
	/**
	 * 将日期转换为时间对象
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str, String format) throws Exception {
		if(StringUtil.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime() throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		return sdf.format(date).toString();
	}
}

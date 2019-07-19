package com.qiuzhisystem.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * md5加密
 * @author JOB
 *
 */
public class Md5Util {
	public static String md5(String str, String salt) {
		return new Md5Hash(str, salt).toString();
	}
	public static void main(String[] args) {
		System.out.println(Md5Util.md5("123456", "qiuzhisystem"));
	}
}

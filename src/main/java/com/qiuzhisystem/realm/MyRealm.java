package com.qiuzhisystem.realm;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.qiuzhisystem.entity.Bloger;
import com.qiuzhisystem.service.IBlogerService;

/**
 * 自定义realm
 * @author 12952
 *
 */
public class MyRealm extends AuthorizingRealm {
	@Resource
	private IBlogerService blogerService;
	/**
	 * 为当前用户设置角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
	/**
	 * 验证当前登陆的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String)token.getPrincipal();
		Bloger bloger = blogerService.getByUserName(userName);
		if(bloger != null) {
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", bloger);
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(bloger.getUserName(), bloger.getPassword(), "xxx");
			return authcInfo;
		}else {
			return null;
		}
	}
	
}

package com.news.security.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.news.dao.ManagerDao;
import com.news.entity.Manager;

/**  
 * @author: husong
 * @date:   2017年12月18日 下午3:08:07   
 */
public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private ManagerDao managerDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AuthorizationInfo authorizationInfo = null;
		try{
			//获取用户名
			String userName = principals.toString();
		    //根据用户名查询权限
			Manager manager = managerDao.selectOne(userName);
			if(manager == null||manager.getRole()==null){
				throw new UnknownAccountException("No account found for user [" + userName + "]");
			}
			Set<String> roles = new HashSet<String>();
			roles.add(manager.getRole());
			authorizationInfo = new SimpleAuthorizationInfo(roles);
		}catch(Exception e){
			e.printStackTrace();
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		AuthenticationInfo authenticationInfo = null;
		MyUsernamePasswordToken usernamePasswordToken = (MyUsernamePasswordToken) token;
		//获取用户名
		String userName = usernamePasswordToken.getUsername();
	    //根据用户名查询正确的账号密码
		Manager manager = managerDao.selectOne(userName);
		if(manager == null){
			throw new UnknownAccountException("No account found for user [" + userName + "]");
		}
		if(!usernamePasswordToken.getType().equals(manager.getType())){
			throw new IllegalArgumentException("用户类型不对！");
		}
		if(manager.getStatus()==null||!"1".equals(manager.getStatus())){
			throw new LockedAccountException("账号被锁定！");
		}
		authenticationInfo = new SimpleAuthenticationInfo(manager.getUserName(), manager.getPassword(), this.getName());
		return authenticationInfo;
	}

}

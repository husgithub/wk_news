package com.news.security.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

/**  
 * 自定义认证器，以此区分不同类型的用户执行对应的Realm
 * @author: husong
 * @date:   2017年12月22日 下午3:44:25   
 */
public class MyRealmAuthenticator extends ModularRealmAuthenticator {
	
	private Map<String,Object> myRealms ;

	public void setMyRealms(Map<String, Object> myRealms) {
		this.myRealms = myRealms;
	}
	
	/**
	 * 重写认证方法
	 */
	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(myRealms==null||myRealms.isEmpty()){
        	throw new IllegalStateException("没有初始化Realms");
        }
        //设置父类setRealms，验证权限时需要
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add((Realm)myRealms.get("userRealm"));
        super.setRealms(realms);
        return doSingleRealmAuthentication((Realm)myRealms.get("userRealm"), authenticationToken);
    }
	

}

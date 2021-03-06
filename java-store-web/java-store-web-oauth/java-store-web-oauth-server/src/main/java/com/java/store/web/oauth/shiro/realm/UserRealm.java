package com.java.store.web.oauth.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.java.store.web.oauth.common.persistence.entity.User;
import com.java.store.web.oauth.server.server.UserService;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 可以通过初始化方法 注解时用：@PostConstruct ; xml 时 ： init-method="xx()"
     */
    /*public void setCredentialMatcher(){
		HashedCredentialsMatcher  credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(2);
		System.out.println("++++++++++++++++++++++" + credentialsMatcher);
		setCredentialsMatcher(credentialsMatcher);
	}*/
    
	// 获取授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //暂时不加权限 code here .......
        //改用户拥有那些角色 
        //角色拥有那些权限
        if("admin".equals("test")){
        	authorizationInfo.addRole("admin");
		}
		authorizationInfo.addRole("user");
        return authorizationInfo;
    }

    // 获取认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        User user = userService.findByUsername(username);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),//salt加密盐值
                getName()  //realm name
        		);
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
    
    
    public static void main(String[] args) {
//		String saltSource = "testtest";
		String hashAlgorithmName = "MD5";
		String credentials = "admin";
		int hashIterations = 2;
//		Object salt = new Md5Hash(saltSource);
//		System.out.println(salt);
		Object salt1 = ByteSource.Util.bytes("adminadmin");
		System.out.println(salt1);
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt1, hashIterations);
		System.out.println(result);
		System.out.println("68f3139a38b232392cc9d3b6ddd762f7".equals(result.toString()));
	}
}

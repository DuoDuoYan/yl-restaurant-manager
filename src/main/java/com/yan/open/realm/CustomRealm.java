package com.yan.open.realm;

import com.yan.open.model.Permission;
import com.yan.open.model.User;
import com.yan.open.service.AdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class CustomRealm  extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    private static final Logger log = LoggerFactory.getLogger(CustomRealm.class);

    @Override
    public String getName() {
        return getClass().getName();
    }

    /**
     * 权限校验
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //根据用户获取角色roleId，根据roleId获取permission
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取permission
        if(user != null){
            List<Permission> permissionLists = adminService.getPermissions(user);
            if(permissionLists.size()!=0){
                for (Permission p: permissionLists){
                    authorizationInfo.addStringPermission(p.getUrl());
                }
                return authorizationInfo;
            }
        }
        return null;
    }

    /**
     * 身份验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //将token转换为UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取账号，通过查询账号查询数据库是否有值，有值则进行密码验证
        User user = adminService.findByName(token.getUsername());
        log.info("realm:"+user);
        if(null == user){
            throw new UnknownAccountException(); //没有找到账号
        }
        //用账号来计算盐值
        ByteSource salt = ByteSource.Util.bytes(user.getPhone());
        //数据库中的密码做MD5的加密
//        SimpleHash hash = new SimpleHash("MD5",password,salt,1024);
        AuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(),user.getPassword(),salt,getName());
        return info;
    }
}

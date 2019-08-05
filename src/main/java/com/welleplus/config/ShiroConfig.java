package com.welleplus.config;

import com.welleplus.filter.ShiroUserFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro设置
 */
@Configuration
public class ShiroConfig {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/error/nologin");
//        shiroFilterFactoryBean.setSuccessUrl("/list/getlist");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/unauth");

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        // 注意这里不要用Bean的方式，否则会报错
        filters.put("authc", new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/logout","logout");
        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/error/*","anon");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        shiroFilterFactoryBean.setLoginUrl("/user/login");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/webjars/**", "anon");
//        filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/", "anon");
//        filterChainDefinitionMap.put("/front/**", "anon");
//        filterChainDefinitionMap.put("/api/**", "anon");
//
//        filterChainDefinitionMap.put("/admin/**", "authc");
//        filterChainDefinitionMap.put("/user/**", "authc");
//        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
//        filterChainDefinitionMap.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

//    @Bean
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
//        defaultSecurityManager.setRealm(customRealm());
//        defaultSecurityManager.setSessionManager(sessionManager());
//        defaultSecurityManager.setRememberMeManager(rememberMeManager());
//        return defaultSecurityManager;
//    }

    @Bean
    public CustomRealm customRealm(CredentialsMatcher credentialsMatcher) {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(credentialsMatcher);
        return customRealm;
    }

    /**
     * 交由SecurityManage管理
     * @return
     */
    @Bean
    @DependsOn("credentialsMatcher")
    public SecurityManager securityManager(CredentialsMatcher credentialsMatcher){
        DefaultWebSecurityManager defaultSecurityManager =  new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(customRealm(credentialsMatcher));
        defaultSecurityManager.setSessionManager(sessionManager());
        defaultSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultSecurityManager;
    }

    /**
     * 功能增强
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher  credentialsMatcher = new HashedCredentialsMatcher();
        //加密方式
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密迭代次数
        credentialsMatcher.setHashIterations(1024);
        //true加密用的hex编码，false用的base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        return credentialsMatcher;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        //cookie.setHttpOnly(true)
        cookie.setMaxAge(24 * 60 * 60);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }


    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME);
//        cookie.setHttpOnly(false);
        //cookie.setMaxAge(Cookie.ONE_YEAR);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        rememberMeManager.setCookie(cookie);
        return rememberMeManager;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(CredentialsMatcher credentialsMatcher){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager(credentialsMatcher));
        return authorizationAttributeSourceAdvisor;
    }

}

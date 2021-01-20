package com.llb.cxy.boot.config.security;

import com.llb.cxy.core.exception.LLBAccessDeniedHandler;
import com.llb.cxy.core.exception.LLBAuthenticationEntryPoint;
import com.llb.cxy.security.config.JsonAuthenticationSecurityConfig;
import com.llb.cxy.security.service.SpringDataUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * description: SecurityConfig <br>
 * date: 2019/11/22 9:51 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Configuration
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private DataSource dataSource; // 数据源

    @Autowired
    private SpringDataUserDetailsService springDataUserDetailsService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    /*@Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private WechatAuthenticationSecurityConfig wechatAuthenticationSecurityConfig;*/

    @Autowired
    private JsonAuthenticationSecurityConfig jsonAuthenticationSecurityConfig;


    @Autowired
    private TokenStore tokenStore;

    @Value("${server.servlet.context-path}")
    private String projectName;

    /**
     * 全局用户信息
     * 基于密码加密的认证
     *
     * @param auth 认证管理
     * @throws Exception 用户认证异常信息
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        // 配置用户信息来源和密码加密策略
        auth.userDetailsService(springDataUserDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * 需要配置这个支持password模式
     * support password grant type
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //安全拦截机制（最重要）
    //会被EnableResourceServer定义过的方法覆盖，因为当前类是在EnableResourceServer启动之前执行的
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //将 SmsCodeAuthenticationSecurityConfig 注入进来，然后通过 http.apply(xxx) 添加进去。
        /*http.apply(smsCodeAuthenticationSecurityConfig);
        http.apply(jsonAuthenticationSecurityConfig);
        http.apply(wechatAuthenticationSecurityConfig);*/

        /*http
                .requestMatchers()//拦截请求
                //.antMatchers("/login", "/oauth/**")
                .and()
                .authorizeRequests()//请求不需要权限认证
                //.antMatchers("/login").permitAll()
                //.antMatchers("/login/**","/logout/**","/oauth/**", "/wx/**").permitAll()
                // "/oauth/**" 不能代替 "/oauth/login"
                .antMatchers( "/oauth/login", "/login/**", "/logout/**", "/wx/**").permitAll()
                .anyRequest()// 任何请求
                .authenticated()// 需要身份认证

                .and().formLogin()

                .and().csrf().disable()// 关闭跨站伪造
        */
        http

                .requestMatchers()//拦截请求
                //.antMatchers("/login", "/oauth/**")
                //.antMatchers("/user/extra")
                .and()
                .authorizeRequests()//请求不需要权限认证
                //.antMatchers("/login").permitAll()
                //.antMatchers("/login/**","/logout/**","/oauth/**", "/wx/**").permitAll()
                // "/oauth/**" 不能代替 "/oauth/login"
                .antMatchers( "/oauth/login", "/login/**", "/logout/**",
                        "/wx/**", "/oauth/**", "/rsa/publicKey").permitAll()
                .anyRequest()// 任何请求
                .authenticated()// 需要身份认证

                .and().formLogin().loginPage("/login")// 自定义的登录接口
                // 我们必须允许所有用户访问我们的登录页（例如为验证的用户），
                // 这个formLogin().permitAll()方法允许任意用户访问基于表单登录的所有的URL,
                // 定URL无需保护，一般应用与静态资源文件
                .permitAll()
                /*修改UsernamePasswordAuthenticationFilter过滤器的默认过滤的提交username、password的认证请求路径，
                     当我们使用httpBasic()弹窗认证或formLogin()默认页面或自定义页面，提交username、password进行认证时，
                     提交username、password的认证请求由UsernamePasswordAuthenticationFilter过滤器过滤，
                     UsernamePasswordAuthenticationFilter过滤器默认过滤的提交username、password的认证请求为请求路径是"/login"的"POST"请求，
                     使用loginProcessingUrl作用是将UsernamePasswordAuthenticationFilter过滤器的默认过滤的提交username、password的认证路径修改为，
                     自定义认证页面中的提交username、password的自定义认证请求的请求路径，
                     这样当自定义认证页面login.html提交username、password的自定义认证请求提交的username和password的值，
                     就会被UserDetailsService接口的实现类UserDetailsService类中的loadUserByUsername方法认证，
                     自定义认证页面的认证功能才会生效*/
                //.loginProcessingUrl("/oauth/login")
                //.loginProcessingUrl("/process")

                .and()
                .logout().permitAll()
                //.logoutSuccessUrl("/my/index") //注销之后跳转的URL。默认是/login?logout
                // /logout退出清除cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                //.logoutSuccessHandler(new LogoutSuccessHandler(tokenStore))//如果指定了这个选项那么logoutSuccessUrl()的设置会被忽略
                .logoutSuccessHandler(
                        (request, response, authentication) -> {
                            String callback = request.getParameter("callback");
                            if (callback == null){
                                callback = "/login?logout";
                            }
                            response.sendRedirect(callback);
                        }
                )
                .invalidateHttpSession(true)//指定是否在注销时让HttpSession无效。 默认设置为 true

                .and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new LLBAccessDeniedHandler())
                //加了之后authorization_code会跳转不过来
                .authenticationEntryPoint(new LLBAuthenticationEntryPoint(projectName))

                //.and().apply(wechatAuthenticationSecurityConfig)
                .and().apply(jsonAuthenticationSecurityConfig)

                .and()
                .rememberMe()
                //.tokenRepository(persistentTokenRepository()) // 设置数据访问层
                //.tokenValiditySeconds(60 * 60 * 2) // 记住我的时间(秒)
                .tokenValiditySeconds(120) // 记住我的时间(秒)

                .and().csrf().disable()// 关闭跨站伪造
        ;

        /*http
         *//*
                异常处理
                默认 权限不足  返回403，可以在这里自定义返回内容
                 *//*
                .exceptionHandling()
                .accessDeniedHandler(new DefinedAccessDeniedHandler())
                .authenticationEntryPoint(new DefinedAuthenticationEntryPoint());*/

        /*http
         *//**
         *权限验证配置项
         *//*
                .authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .withObjectPostProcessor(new DefindeObjectPostProcessor());*/

        /*http
                // 开启授权认证
                .authorizeRequests()
                //.antMatchers("/").permitAll()// 设置所有人都可以访问登录页面
                // 需要授权访问的
                .antMatchers(AUTH_URL_REG).authenticated()
                // OPTIONS预检请求不处理
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 其他请求不处理
                .anyRequest().permitAll();*/

        //http://www.pbteach.com/post/java_distribut/springsecurity-07/
        //如果让logout在GET请求下生效，必须关闭防止CSRF攻击csrf().disable()。如果开启了CSRF，必须使用post方式请求/logout
        /*http
                .logout()
                .logoutUrl(LOGOUT_URL)
                .invalidateHttpSession(true)
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new DefinedLogoutSuccessHandler());*/

       /* http
                .rememberMe()
                .rememberMeParameter(REMEMBER_ME)
                .tokenRepository(new RedisTokenRepositoryImpl());*/

//        http
                // 实现 json 登录
//                .addFilterAt(getJsonFilter(super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        //http.addFilterAfter(getJsonFilter(super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 持久化token
     *
     * Security中，默认是使用PersistentTokenRepository的子类InMemoryTokenRepositoryImpl，将token放在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建表persistent_logins，将token持久化到数据库
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // 设置数据源
//        tokenRepository.setCreateTableOnStartup(true); // 启动创建表，创建成功后注释掉
        return tokenRepository;
    }

    /**
     * 默认不拦截静态资源的url pattern。我们也可以用下面的WebSecurity这个方式跳过静态资源的认证
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/resource/**", "/js/**", "/images/**", "/css/**", "/fonts/**");
    }

    /**
     * 决策管理
     *
     * @return
     */
    /*private AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new UrlRoleVoter());
        UnanimousBased based = new UnanimousBased(decisionVoters);
        return based;
    }*/

    /*class DefindeObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O object) {
            object.setSecurityMetadataSource(new DefinedFilterInvocationSecurityMetadataSource());
            return object;
        }
    }*/

    /**
     * {@link org.springframework.security.access.vote.RoleVoter}
     */
    /*class UrlRoleVoter implements AccessDecisionVoter<Object> {

        @Override
        public boolean supports(ConfigAttribute attribute) {
            if (null == attribute.getAttribute()) {
                return false;
            }
            return true;
        }

        @Override
        public boolean supports(Class<?> clazz) {
            return true;
        }

        @Override
        public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
            if (null == authentication) {
                return ACCESS_DENIED;
            }
            int result = ACCESS_ABSTAIN;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (ConfigAttribute attribute : attributes) {
                if (this.supports(attribute)) {
                    result = ACCESS_DENIED;
                    for (GrantedAuthority authority : authorities) {
                        if (attribute.getAttribute().equals(authority.getAuthority())) {
                            return ACCESS_GRANTED;
                        }
                    }
                }
            }
            return result;
        }
    }*/

    /**
     * 权限验证数据源
     * <p>
     * 此处实现
     * 从数据库中获取URL对应的role信息
     */
    /*class DefinedFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
        @Override
        public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
            String requestUrl = ((FilterInvocation) o).getRequestUrl();
            List<String> roleIds = webUserDao.listRoleByUrl(requestUrl);
            return SecurityConfig.createList(roleIds.toArray(new String[0]));
        }

        @Override
        public Collection<ConfigAttribute> getAllConfigAttributes() {
            return null;
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return FilterInvocation.class.isAssignableFrom(aClass);
        }
    }*/

}
package com.llb.cxy.boot.config.security;

import com.llb.cxy.core.exception.LLBOAuth2WebResponseExceptionTranslator;
import com.llb.cxy.security.enhancer.CustomTokenEnhancer;
import com.llb.cxy.security.service.SpringDataUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;

/**
 * description: AuthServerConfig <br>
 * date: 2019/11/22 9:37 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Configuration
//提供/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
//开启授权服务器
@EnableAuthorizationServer
@Order(1)
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 可以添加自定义逻辑
     * 实现 ClientDetailsService
     * ClientDetails details = baseAppRemoteService.getAppClientInfo(clientId).getData();
     */
    @Autowired
    private ClientDetailsService clientDetailsService;
    /*@Autowired
    private AuthorizationCodeServices authorizationCodeServices;*/

    //这个Bean是在WebSecurityConfigurerAdapter中定义的
    //注入AuthenticationManager ，密码模式用到
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private SpringDataUserDetailsService springDataUserDetailsService;

    /*@Autowired
    private RedisConnectionFactory connectionFactory;  // redis连接工厂

    @Autowired
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;*/

    @Autowired
    private DefaultTokenServices tokenServices;

    /**
     * 对Jwt签名时，增加一个密钥
     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        /*final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        // final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        // converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;*/

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //jwtAccessTokenConverter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }

    //令牌存储 设置token 由Jwt产生，不使用默认的透明令牌
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入自定义token生成方式
     *
     * @return
     */
    @Bean
    public TokenEnhancer customTokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    //将客户端信息存储到数据库
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder());
        return clientDetailsService;
    }

    /**
     * 配置客户端详情服务
     * 客户端详细信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        //配置客户端保存的数据源信息。
        clients.withClientDetails(clientDetailsService);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        // 复用refresh token
        //tokenServices.setReuseRefreshToken(false);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(getTokenEnhancerChain());
        //tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));//一天
        // 数据库设置类这里不生效
        // tokenServices.setAccessTokenValiditySeconds(120);//设置20秒过期
        // tokenServices.setRefreshTokenValiditySeconds(240);//设置刷新token的过期时间
        return tokenServices;
    }


    private TokenEnhancerChain getTokenEnhancerChain() {
        // 将增强的token设置到增强链中
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), accessTokenConverter()));
        return tokenEnhancerChain;
    }

    /**
     * 授权码存储到数据库中
     * 用于定义授权码模式下认证服务器生成的授权码
     *     保存在数据库还是内存中，
     * @param dataSource
     * @return
     */
    /*@Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);//设置授权码模式的授权码如何存取
    }*/

    /**
     * 令牌访问端点
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //指定认证管理器
                .authenticationManager(authenticationManager)
                //指定token存储位置
                .tokenStore(tokenStore())
                // 配置JwtAccessToken转换器
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(springDataUserDetailsService)
                //.tokenGranter(tokenGranter) // 四种授权模式+刷新令牌的模式+自定义授权模式
                //.reuseRefreshTokens(false)//使用刷新令牌
                //该字段设置设置refresh token是否重复使用,true:reuse;false:no reuse.
                .reuseRefreshTokens(false)
                // 自定义异常转换类
                .exceptionTranslator(new LLBOAuth2WebResponseExceptionTranslator())
                //支持GET  POST  请求获取token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                //开启刷新token
                .reuseRefreshTokens(true)
        ;
        // 将增强的token设置到增强链中 自定义token生成方式
        endpoints.tokenEnhancer(getTokenEnhancerChain());

        //添加endpoints.tokenServices
        endpoints.tokenServices(tokenServices());
        // 自定义确认授权页面
        //endpoints.pathMapping("/oauth/confirm_access", "/oauth/confirm_access");
        // 自定义错误页
        //endpoints.pathMapping("/oauth/error", "/oauth/error");
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * 令牌访问端点的安全策略
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                .tokenKeyAccess("permitAll()")       ///oauth/token_key公开 开启/oauth/token_key验证端口无权限访问
                .checkTokenAccess("permitAll()")     //checkToken这个endpoint完全公开 开启/oauth/check_token验证端口认证权限访问
                //.checkTokenAccess("isAuthenticated")
                .allowFormAuthenticationForClients() //让/oauth/token支持client_id以及client_secret作登录认证 允许表单认证，申请令牌
        .passwordEncoder(passwordEncoder())
        //将拦截器放入到拦截链条中,将拦截器放入到认证链条中。
        //.addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter)
        ;
    }

}
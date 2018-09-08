package com.github.vspro.framework.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.sql.DataSource;

/**
 * Created  on 2018/9/8.
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
//@EnableAuthorizationServer的作用就是添加一条拦截规则(这里是Oauth2)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean@ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource oauth2DataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean
    public ClientDetailsService clientDetailsService(){
        return new JdbcClientDetailsService(oauth2DataSource());
    }


    @Bean
    public ClientDetailsUserDetailsService clientDetailsUserDetailsService(){
        return new ClientDetailsUserDetailsService(clientDetailsService());
    }


    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(oauth2DataSource());
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(oauth2DataSource());
    }

    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory(){
        return new DefaultOAuth2RequestFactory(clientDetailsService());
    }


    @Bean
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setClientDetailsService(clientDetailsService());
        return defaultTokenServices;
    }


    @Bean
    public TokenStoreUserApprovalHandler approvalHandler(){
        // 基于token存储,也可以使用基于scope的ApprovalStoreUserApprovalHandler
        //ApprovalStoreUserApprovalHandlert和TokenStoreUserApprovalHandler不同在于
        //ApprovalStoreUserApprovalHandlert要对用户的每个scope进行授权
        //而TokenStoreUserApprovalHandler只有同意拒绝两个按钮
        TokenStoreUserApprovalHandler approvalHandler = new TokenStoreUserApprovalHandler();
        approvalHandler.setRequestFactory(oAuth2RequestFactory());
        approvalHandler.setTokenStore(tokenStore());
        return approvalHandler;
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
        security.realm("simple")
                //exposes public key for token verification if using JWT tokens
                //使用标准的spel语言就行
                //默认都是denyAll()
                .tokenKeyAccess("permitAll()")
                //used by Resource Servers to decode access tokens
                .checkTokenAccess("isAuthenticated()")
                //allowFormAuthenticationForClients是为了注册clientCredentialsTokenEndpointFilter
                //clientCredentialsTokenEndpointFilter,解析request中的client_id和client_secret
                //构造成UsernamePasswordAuthenticationToken,然后通过UserDetailsService查询作简单的认证而已
                //一般是针对password模式和client_credentials
                //当然也可以使用http basic认证
                //如果使用了http basic认证,就不用使用clientCredentialsTokenEndpointFilter
                //因为本质是一样的
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //如果是password模式认证，需要指定authenticationManager
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(clientDetailsUserDetailsService())
                .authorizationCodeServices(authorizationCodeServices())
                .userApprovalHandler(approvalHandler())
                .tokenServices(tokenServices());

        //自定义oauth_confirm页面
        endpoints.pathMapping("/oauth/confirm_access", "/confirm_access");
//        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET);

    }
}

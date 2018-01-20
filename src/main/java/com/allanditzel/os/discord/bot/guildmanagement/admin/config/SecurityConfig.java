package com.allanditzel.os.discord.bot.guildmanagement.admin.config;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String USER_AGENT_STRING_VALUE = "Mozilla/5.0 (X11; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0";

    @Autowired
    protected OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/admin/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/admin")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(oauthFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory requestFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        Header userAuthHeader = new BasicHeader(HttpHeaders.USER_AGENT, USER_AGENT_STRING_VALUE);
        httpClientBuilder.setDefaultHeaders(Collections.singletonList(userAuthHeader));

        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }

    @Bean
    public Filter oauthFilter() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        OAuth2ClientAuthenticationProcessingFilter oauthFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
        OAuth2RestTemplate oauthTemplate = discordRestTemplate();
        OAuth2AccessTokenSupport authAccessProvider = new AuthorizationCodeAccessTokenProvider();
        // Set request factory for '/oauth/token'
        authAccessProvider.setRequestFactory(requestFactory());
        AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider>asList(
                (AuthorizationCodeAccessTokenProvider) authAccessProvider));
        oauthTemplate.setAccessTokenProvider(accessTokenProvider);
        // Set request factory for '/userinfo'
        oauthTemplate.setRequestFactory(requestFactory());
        oauthFilter.setRestTemplate(oauthTemplate);
        UserInfoTokenServices userInfoTokenService = new UserInfoTokenServices(discordResource().getUserInfoUri(), discordResource().getClientId());
        userInfoTokenService.setRestTemplate(oauthTemplate);
        oauthFilter.setTokenServices(userInfoTokenService);
        return oauthFilter;
    }

    @Bean
    public OAuth2RestTemplate discordRestTemplate() {
        return new OAuth2RestTemplate(discord(), oauth2ClientContext);
    }

    @Bean
    @ConfigurationProperties("discord.client")
    public AuthorizationCodeResourceDetails discord() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("discord.resource")
    public ResourceServerProperties discordResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}

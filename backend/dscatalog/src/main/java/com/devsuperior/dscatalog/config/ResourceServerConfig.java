package com.devsuperior.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //Rotas liberadas o acesso ao público
    private static final String[] PUBLIC = {"/oauth/token"};

    //Rotas liberadas apenas aos perfis de OPERATOR e ADMIN
    private static final String[] OPERATOR_OR_ADMIN = {"/products/**", "/categories/**"};

    //Rotas liberadas apenas aos perfis de ADMIN
    private static final String[] ADMIN = {"/users/**"};

    @Autowired
    private JwtTokenStore tokenStore;

    /**
     * Esse método serve para analisar se o token que chegará na requisição é válido
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll()                                //Rotas do vetor Public todas permitidas
                .antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll()     //Rotas dos perfils operator e admin apenas as de GET são todas permitidas
                .antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")     //Demais rotas do vetor operator_or_admin apenas a quem possua os papeis
                .antMatchers(ADMIN).hasRole("ADMIN")                            //Rotas do vetor ADMIN apenas a quem tem papel de admin
                .anyRequest().authenticated();                                  //As demais rotas apenas estando autenticado.
    }
}

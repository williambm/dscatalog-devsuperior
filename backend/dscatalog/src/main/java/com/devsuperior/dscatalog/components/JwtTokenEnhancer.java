package com.devsuperior.dscatalog.components;

import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        //O email vai vir através do objeto do OAuth2Authentication pegamos o getName que no nosso caso logamos por email na aplicação
        User user = userRepository.findByEmail(oAuth2Authentication.getName());

        //Para mapear valores em um token temos que usar um Map que tem a estrutura de chave / valor
        Map<String,Object> map = new HashMap<>();
        map.put("userFirstName",user.getFirstName());
        map.put("userId",user.getId());

        //Para passar os valores do meu Map tenho que usar o oAuth2AccessToken, porém como ele é uma interface utilizo o casting para uma implementação.
        // No caso DefaultOAuth2AccessToken
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
        token.setAdditionalInformation(map);

        return token;
    }
}

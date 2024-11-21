package com.Sportagram.sportagram.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.Sportagram.sportagram.dto.CustomOAuth2User;
import com.Sportagram.sportagram.dto.OAuth2Response;
import com.Sportagram.sportagram.dto.GoogleResponse;
import com.Sportagram.sportagram.entity.Users;
import com.Sportagram.sportagram.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DefaultOAuth2UserService OAuth2UserService의 구현체

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        Users existData = userRepository.findByUserName(username);

        String role = "ROLE_USER";

        // 이메일에서 @ 앞부분 추출
        String email = oAuth2Response.getEmail();
        String userId = email.substring(0, email.indexOf("@"));

        if (existData == null) {

            Users userEntity = new Users();
            userEntity.setUserName(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setUserID(userId);
            // userEntity.setRole(role);

            userRepository.save(userEntity);
        }
        else {

            existData.setUserName(username);
            existData.setEmail(oAuth2Response.getEmail());
            existData.setUserID(userId);

            // role = existData.getRole();

            userRepository.save(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
package com.recaptcha.service.impl;

import com.recaptcha.controller.response.RecaptchaResponse;
import com.recaptcha.service.RecaptchaService;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";

    private final String RECAPTCHA_SECRET = "6LfKJeAmAAAAAAxaZgRToYjrvj8b3Z9r09jdx6U2";

    @Override
    public boolean validateRecaptcha(String captcha) {

        boolean result = false;

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("secret", RECAPTCHA_SECRET);
        request.add("response", captcha);


        RestTemplate restTemplate = new RestTemplate();

        RecaptchaResponse apiResponse= restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, request, RecaptchaResponse.class);

        if (apiResponse == null) {
            result = false;
        } else {
            result = apiResponse.getSuccess();
        }

        return result;
    }
}

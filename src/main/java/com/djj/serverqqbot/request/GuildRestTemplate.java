package com.djj.serverqqbot.request;

import com.djj.serverqqbot.properties.GuildProperties;
import com.djj.serverqqbot.util.BotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Author：DJJ
 * Date：2024/7/28
 */
@Slf4j
@Component
public class GuildRestTemplate {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GuildProperties guildProperties;

    public String exchange(String uri, HttpMethod httpMethod, MultiValueMap<String, Object> params) {
        log.info("guild url:" + guildProperties.getBaseUrl() + uri);
        ResponseEntity<String> responseEntity = restTemplate.exchange(guildProperties.getBaseUrl() + uri,
                httpMethod,
                httpEntity(params),
                String.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody();
        }
        return null;
    }

    public String exchange(String uri, HttpMethod httpMethod) {
        return exchange(uri, httpMethod, new LinkedMultiValueMap<>());
    }

    public String postForBodyJson(String uri, String bodyJson) {
        log.info("guild url:" + guildProperties.getBaseUrl() + uri);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(guildProperties.getBaseUrl() + uri,
                bodyHttpEntity(bodyJson),
                String.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody();
        }
        return null;
    }

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, BotUtil.buildAuthToken(guildProperties.getBotAppId(), guildProperties.getBotToken()));
        return headers;
    }

    private HttpEntity httpEntity(MultiValueMap<String, Object> params) {
        return new HttpEntity(params, httpHeaders());
    }

    private <T> HttpEntity<T> bodyHttpEntity(T body) {
        return new HttpEntity<T>(body, httpHeaders());
    }
}


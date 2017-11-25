package com.jauharteam.ojek.webapp.token;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ojek.common.util.StringUtil.string;

public class AccessTokenUtil {

    private static final Pattern accessTokenPattern = Pattern.compile("([a-zA-Z0-9\\+\\/=]*)\\.([a-zA-Z0-9\\+\\/=]*)\\.([a-zA-Z0-9\\+\\/=]*)");

    private static String generateString(AccessToken token) {
        String accessToken = string(token.getAccessToken());
        String userAgent = string(token.getUserAgent());
        String ipAddress = string(token.getIpAddress());

        accessToken = Base64.getEncoder().encodeToString(accessToken.getBytes());
        userAgent = Base64.getEncoder().encodeToString(userAgent.getBytes());
        ipAddress = Base64.getEncoder().encodeToString(ipAddress.getBytes());

        return accessToken + "." + userAgent + "." + ipAddress;
    }

    private static AccessToken parseAccessToken(String accessToken) {
        accessToken = string(accessToken);
        Matcher matcher = accessTokenPattern.matcher(accessToken);
        if (matcher.matches()) {
            AccessToken token = new AccessToken();

            token.setAccessToken(matcher.group(1));
            token.setUserAgent(matcher.group(2));
            token.setIpAddress(matcher.group(3));

            token.setAccessToken(new String(Base64.getDecoder().decode(token.getAccessToken())));
            token.setUserAgent(new String(Base64.getDecoder().decode(token.getUserAgent())));
            token.setIpAddress(new String(Base64.getDecoder().decode(token.getIpAddress())));

            return token;
        }
        return new AccessToken("","","");
    }

}

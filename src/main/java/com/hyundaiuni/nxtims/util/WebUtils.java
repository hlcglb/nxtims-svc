package com.hyundaiuni.nxtims.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.util.CookieGenerator;

public class WebUtils extends org.springframework.web.util.WebUtils {
    /**
     * 주어진 도메인, 이름에 해당하는 첫번째 쿠키를 리턴함.
     * 
     * @param request Http 응답 객체
     * @param cookieDomain 쿠키 도메인
     * @param cookieName 쿠키명
     * @return 주어진 도메인, 이름에 해당하는 첫번째 쿠키를 리턴함.
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieDomain, String cookieName) {
        Assert.notNull(request, "Request must not be null");

        Cookie cookies[] = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookieDomain.equals(cookie.getDomain()) && cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }

        return null;
    }

    /**
     * 주어진 이름, 값으로 쿠키에 저장함.
     * 
     * @param response Http 응답 객체
     * @param cookieName 쿠기에 저장할 이름
     * @param cookieValue 쿠키에 저장할 값
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue) {
        setCookie(response, cookieName, cookieValue, null);
    }

    /**
     * 주어진 이름, 값으로 쿠키에 저장함.
     * 
     * @param response Http 응답 객체
     * @param cookieName 쿠기에 저장할 이름
     * @param cookieValue 쿠키에 저장할 값
     * @param cookieMaxAge 쿠키를 저장할 시간(초)
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue,
        Integer cookieMaxAge) {
        setCookie(response, null, cookieName, cookieValue, cookieMaxAge);
    }

    /**
     * 주어진 이름, 값으로 쿠키에 저장함.
     * 
     * @param response Http 응답 객체
     * @param cookieDomain 쿠기에 저장할 도메인
     * @param cookieName 쿠기에 저장할 이름
     * @param cookieValue 쿠키에 저장할 값
     * @param cookieMaxAge 쿠키를 저장할 시간(초)
     */
    public static void setCookie(HttpServletResponse response, String cookieDomain, String cookieName,
        String cookieValue, Integer cookieMaxAge) {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieDomain(cookieDomain);
        cookieGenerator.setCookieName(cookieName);
        cookieGenerator.removeCookie(response);
        cookieGenerator.setCookieMaxAge(cookieMaxAge);
        cookieGenerator.addCookie(response, cookieValue);
    }

    /**
     * 주어진 이름, 값으로 쿠키에 추가함.
     * 
     * @param response Http 응답 객체
     * @param cookieName 쿠기에 저장할 이름
     * @param cookieValue 쿠키에 저장할 값
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue) {
        addCookie(response, cookieName, cookieValue, null);
    }

    /**
     * 주어진 이름, 값으로 쿠키에 추가함.
     * 
     * @param response Http 응답 객체
     * @param cookieName 쿠기에 저장할 이름
     * @param cookieValue 쿠키에 저장할 값
     * @param cookieMaxAge 쿠키를 저장할 시간(초)
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue,
        Integer cookieMaxAge) {
        addCookie(response, null, cookieName, cookieValue, cookieMaxAge);
    }

    /**
     * 주어진 이름, 값으로 쿠키에 추가함.
     * 
     * @param response Http 응답 객체
     * @param cookieDomain 쿠기에 저장할 도메인
     * @param cookieName 쿠기에 저장할 이름
     * @param cookieValue 쿠키에 저장할 값
     * @param cookieMaxAge 쿠키를 저장할 시간(초)
     */
    public static void addCookie(HttpServletResponse response, String cookieDomain, String cookieName,
        String cookieValue, Integer cookieMaxAge) {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieDomain(cookieDomain);
        cookieGenerator.setCookieName(cookieName);
        cookieGenerator.setCookieMaxAge(cookieMaxAge);
        cookieGenerator.addCookie(response, cookieValue);
    }

    /**
     * 주어진 이름의 쿠키를 삭제함.
     * 
     * @param response Http 응답 객체
     * @param cookieName 쿠기에 저장할 이름
     */
    public static void removeCookie(HttpServletResponse response, String cookieName) {
        removeCookie(response, null, cookieName);
    }

    /**
     * 주어진 이름의 쿠키를 삭제함.
     * 
     * @param response Http 응답 객체
     * @param cookieDomain 쿠기에 저장할 도메인
     * @param cookieName 쿠기에 저장할 이름
     */
    public static void removeCookie(HttpServletResponse response, String cookieDomain, String cookieName) {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieDomain(cookieDomain);
        cookieGenerator.setCookieName(cookieName);
        cookieGenerator.removeCookie(response);
    }

    /**
     * 특정형식의 String을 Map으로 변환
     * 
     * @param query requetParameter
     * @param paramSeparator parameter 구분자
     * @param keyValueSeparator key Value 구분자
     * @param enc character set
     */
    public static Map<String, Object> requestParamtoMap(String query, char paramSeparator, char keyValueSeparator,
        String enc) throws UnsupportedEncodingException {
        Map<String, Object> queryPairs = new LinkedHashMap<String, Object>();

        if(StringUtils.isEmpty(query)) {
            return queryPairs;
        }

        String[] pairs = StringUtils.split(URLDecoder.decode(query, enc), paramSeparator);

        for(String pair : pairs) {
            int idx = pair.indexOf(keyValueSeparator);

            queryPairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }

        return queryPairs;
    }

    public static String mapToRequestParam(Map<String, Object> param, char paramSeparator, char keyValueSeparator, String enc)
        throws UnsupportedEncodingException {
        if(MapUtils.isNotEmpty(param)) {
            StringBuilder stringBuiler = new StringBuilder();

            int len = CollectionUtils.size(param.keySet());
            int i = 0;

            for(String key : param.keySet()) {
                stringBuiler.append(key).append(keyValueSeparator).append(param.get(key));

                i++;

                if(len > i) {
                    stringBuiler.append(paramSeparator);
                }
            }

            return URLEncoder.encode(stringBuiler.toString(), enc);
        }
        else {
            return "";
        }
    }
}

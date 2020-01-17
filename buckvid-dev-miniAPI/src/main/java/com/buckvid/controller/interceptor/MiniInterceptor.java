package com.buckvid.controller.interceptor;

import com.buckvid.utils.BuckvidJSONResult;
import com.buckvid.utils.JsonUtils;
import com.buckvid.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static com.buckvid.controller.BasicController.USER_REDIS_SESSION;

public class MiniInterceptor implements HandlerInterceptor {
    @Autowired
    public RedisOperator redis;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String userId = request.getHeader("userId");
        String userToken = request.getHeader("userToken");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String uniqueToken = redis.get(USER_REDIS_SESSION + ":" + userId);
            if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
                System.out.println("Please sign in");
                returnErrorResponse(response, new BuckvidJSONResult().errorTokenMsg("Please sign in"));
                return false;
            } else {
                if (!uniqueToken.equals(userToken)) {
                    System.out.println("Logged in on another device");
                    returnErrorResponse(response, new BuckvidJSONResult().errorTokenMsg("Logged in on another device"));
                    return false;
                }
            }
        } else {
            System.out.println("Please sign in");
            returnErrorResponse(response, new BuckvidJSONResult().errorTokenMsg("Please sign in"));
            return false;
        }
        /**
        * false: request has been intercepted
        * true: move forward
        * */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response, BuckvidJSONResult result)
            throws IOException, UnsupportedEncodingException {
        OutputStream out=null;
        try{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } finally{
            if(out!=null){
                out.close();
            }
        }
    }
}

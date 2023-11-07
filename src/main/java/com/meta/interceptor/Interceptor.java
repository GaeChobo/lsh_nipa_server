package com.meta.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.meta.service.SecurityService;

public class Interceptor extends HandlerInterceptorAdapter {

	
    // Logger
    private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

    
    private SecurityService securityService;
        
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


            try
            {
                    // Enumeration eHeader = request.getHeaderNames();
                    //
                    // System.out.println("------------------------");
                    //
                    // while (eHeader.hasMoreElements())
                    // {
                    // String name = (String) eHeader.nextElement();
                    // String value = request.getHeader(name);
                    //
                    // System.out.println(name + ":" + value);
                    //
                    // }
                    //
                    // System.out.println("------------------------");
                    // System.out.println();

                    String jwt = request.getHeader("x-access-token");

                    if (jwt != null)
                    {
                            boolean isValid = securityService.validationToken(jwt);
                            boolean isExpire = securityService.expireToken(jwt);

                            if (isValid && isExpire)
                            {
                                    String areaCode = securityService.getSubject(jwt);

                                    String[] split = areaCode.split(",");

                                    request.setAttribute("areaCode", split[0]);

                                    logger.info("connected User : " + split[1]);

                                    return true;
                            } else if (isValid && !isExpire) // 토큰 만료
                            {
                                 
                                    return false;
                            } else
                            {
 
                            }
                    } else
                    {
                            if (!request.getMethod().equals("OPTIONS")) // options 처리 (react axios)
                                    return false;
                    }

                    return false;



            } catch (Exception e) // null 또는 세션 만료 처리
            {
                    return false;
            }
    }
    
}

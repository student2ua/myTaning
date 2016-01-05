package com.tor.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: tor
 * Date: 05.01.16
 * Time: 18:35
 * http://www.expertwebindia.com/how-to-get-client-ip-address-in-java/
 * */
public class ClientIpAddress extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
      //proxy server
    String ipAddr=httpServletRequest.getHeader("X-FORWARDED-FOR") ;
        if (ipAddr==null){
            ipAddr=httpServletRequest.getRemoteAddr();
        }
    }
}

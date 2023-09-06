package com.tor.csrf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * User: tor
 * Date: 029, 29.08.2021
 * Time: 23:06
 */
@WebServlet("/LoginCheck")
public class LoginServlet extends HttpServlet {

    static final String login = "testuser";
    private static final String PASS = "qwerty";
    static final String CSRF_PARAM_NAME = "doubleSubCookie";
    static final String CSRF_SESION_ATRR = "csrfToken";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String username = req.getParameter("username");
        final String password = req.getParameter("password");
        if (username.equals(login) && password.equals(PASS)) {
            UUID idOne = UUID.randomUUID();
            Cookie loginCookie = new Cookie("user", idOne.toString());
            loginCookie.setMaxAge((int) TimeUnit.MINUTES.toSeconds(30));
            resp.addCookie(loginCookie);
            /*Generate the CSRF token for the session and set a cookie in the browser*/
            final HttpSession session = req.getSession();
            String storedSubCookie = (String) session.getAttribute(CSRF_PARAM_NAME);
            if (storedSubCookie == null) {
                storedSubCookie = generateCSRFToken();
                System.out.println(storedSubCookie);
                Cookie doubleSubCookie = new Cookie(CSRF_PARAM_NAME, storedSubCookie);
                doubleSubCookie.setMaxAge((int) TimeUnit.MINUTES.toSeconds(30));
                resp.addCookie(doubleSubCookie);
                Cookie secretCookie=new Cookie(CSRF_SESION_ATRR,hashAndSalt(storedSubCookie,login.getBytes()));//as sample use uuid  as salt  idOne.toString().getBytes()
                secretCookie.setMaxAge((int) TimeUnit.MINUTES.toSeconds(30));
//                secretCookie.setSecure(true);   in real life
                secretCookie.setHttpOnly(true);
                resp.addCookie(secretCookie);
            }
            resp.sendRedirect("form.jsp");
        } else {
            resp.sendRedirect("error.jsp");
        }
    }

    public static String hashAndSalt(String stringToHash, byte[] salt) {
        MessageDigest messageDigest = null;
        String stringHash=null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256"); //SHA-1 - 160-bit  ?
            messageDigest.update(salt);
            stringHash= new BigInteger(1,messageDigest.digest(stringToHash.getBytes())).toString(16); //hex
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return stringHash;
    }

    private String generateCSRFToken() {
        SecureRandom random = new SecureRandom();
        byte[] b = new byte[20];
        random.nextBytes(b);
        final String s = DatatypeConverter.printBase64Binary(b);
        return s.substring(1,s.length()-1);
    }
}

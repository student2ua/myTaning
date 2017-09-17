package com.tor.web.asyncServlet.chat;

/**
 * User: Admin
 * Date: 18.02.17
 * Time: 20:16
 * https://ynovikov.wordpress.com/tag/%D0%B0%D1%81%D0%B8%D0%BD%D1%85%D1%80%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9-%D1%81%D0%B5%D1%80%D0%B2%D0%BB%D0%B5%D1%82/
 * https://ynovikov.wordpress.com/tag/asynchronous-servlet/
 */
public class Message {

    public final String message;
    public final String username;

    public Message(final String message, final String username) {
        this.message = message;
        this.username = username;
    }
}
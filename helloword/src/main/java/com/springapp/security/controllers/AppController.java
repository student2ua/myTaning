package com.springapp.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: tor
 * Date: 23.06.14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AppController {
    /**
     * будет иметь публичный доступ, к нему не будет применяться никаких ограничений безопасности
     */
    @RequestMapping(value = {"/", "index**"}, method = {RequestMethod.GET})
    public ModelAndView welcomePage() {
        ModelAndView view = new ModelAndView();
        view.addObject("title", "Spring Security Tutorial");
        view.addObject("message", "Welcome Page!");
        view.setViewName("index");
        return view;
    }

    /**
     * бласть с ограниченным доступом, только пользователи обладающие ролью администратора (admin) будут иметь доступ к ней
     */
    @RequestMapping(value = {"protected**"}, method = {RequestMethod.GET})
    public ModelAndView protectedPage() {
        ModelAndView view = new ModelAndView();
        view.addObject("title", "Spring Security Tutorial ProtPage");
        view.addObject("message", "Admin Page!");
        view.setViewName("protected");
        return view;
    }

    /**
     * область с ограниченным доступом, только пользователи обладающие ролью супер администратора будут иметь доступ к ней
     */
    @RequestMapping(value = {"confidential**"}, method = {RequestMethod.GET})
    public ModelAndView superAdminPage() {
        ModelAndView view = new ModelAndView();
        view.addObject("title", "Spring Security Tutorial SuperAdmin");
        view.addObject("message", "SuperAdmin Page!");
        view.setViewName("protected");
        return view;
    }
}

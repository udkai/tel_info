package com.balance.customer.controller;

import com.balance.util.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liukai on 2018/3/20.
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController extends BaseController{
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/resources/index");
        return mv;
    }
}

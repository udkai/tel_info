package com.balance.customer.controller;

import com.balance.customer.model.ResourcesInfo;
import com.balance.customer.model.UserSection;
import com.balance.customer.service.ResourcesService;
import com.balance.util.config.PubConfig;
import com.balance.util.controller.BaseController;
import com.balance.util.page.PageNavigate;
import com.balance.util.string.StringUtil;
import com.balance.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 来源管理
 * Created by liukai on 2018/3/20.
 */
@Controller
@RequestMapping("/customer/resources")
public class ResourcesController extends BaseController{
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private PubConfig pubConfig;
    public final  static int pageSize_f = 50;//定义每页数据记录数
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        int count = resourcesService.count();
        int pageIndex = WebUtil.getSafeInt(request.getParameter("pageIndex"), 1);// 获取当前页数
        int pageSize = pageSize_f;// 直接取全局变量，每页记录数
        String url = pubConfig.getDynamicServer() + "/customer/resources/index.htm?";
        PageNavigate pageNavigate = new PageNavigate(url, pageIndex, pageSize, count);//
        List<ResourcesInfo> list=resourcesService.list(pageIndex,pageSize);
        mv.addObject("list",list);
        mv.addObject("pageNavigate",pageNavigate);
        mv.setViewName("/customer/resources/index");
        return mv;
    }

    /**
     * 解除
     * @param request
     * @param resources
     * @param create_at
     * @param response
     * @return
     */
    @RequestMapping("/relieve")
    public String relieve(HttpServletRequest request, String resources,String create_at, HttpServletResponse response) {
        int flag = resourcesService.updateRelieve(resources,create_at);
        if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("操作失败！");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("操作成功！");
    }

    /**
     * 分配显示页面的解除分配
     * @param resources
     * @param allot_at
     * @param user_name
     * @param idSection
     * @return
     */
    @RequestMapping("/relieves")
    public String relieves(HttpServletRequest request,Integer id, String resources,String allot_at, String user_name,String idSection ) {
        String[]ids=idSection.split("-");
        int flag = resourcesService.updateRelieves(id,resources,user_name,allot_at,ids[0],ids[1]);
        if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("操作失败！");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("操作成功！");
    }
    /**
     * 删除
     * @param request
     * @param resources
     * @param create_at
     * @param response
     * @return
     */
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request, String resources,String create_at, HttpServletResponse response) {
        int flag = resourcesService.delete(resources,create_at);
        if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("操作失败！");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("操作成功！");
    }

    /**
     * 分配显示
     * @param request
     * @param resources_allot
     * @param create_at
     * @param response
     * @return
     */
    @RequestMapping("/allotShow")
    public ModelAndView showAll(HttpServletRequest request, String resources_allot,String create_at, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        List<UserSection> list = resourcesService.listAllAllot(resources_allot);
//        int count = list.size();
//        int pageIndex = WebUtil.getSafeInt(request.getParameter("pageIndex"), 1);// 获取当前页数
//        int pageSize = GlobalConst.pageSize;// 直接取全局变量，每页记录数
//        String url = pubConfig.getDynamicServer() + "/customer/customerInfo/listNumberSection.htm?";
        mv.addObject("resources",resources_allot);
        mv.addObject("create_at",create_at);
        mv.addObject("list", list);
        mv.setViewName("/customer/resources/allotShow");
        return mv;
    }
}

package com.balance.statistics.controller;

import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.User;
import com.balance.statistics.VO.StatisticsSearchVO;
import com.balance.statistics.service.StatisticsBusinessService;
import com.balance.util.config.PubConfig;
import com.balance.util.controller.BaseController;
import com.balance.util.global.GlobalConst;
import com.balance.util.page.PageNavigate;
import com.balance.util.string.StringUtil;
import com.balance.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 业务统计分析
 * Created by liukai on 2018/3/20.
 */
@Controller
@RequestMapping("/statistics/business")
public class StatisticsBusinessController extends BaseController {
    @Autowired
    private StatisticsBusinessService statisticsBusinessService;
    @Autowired
    private PubConfig pubConfig;

    /**
     * 首页
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
//        setBtnAutho(request, "CustomerInfo");
        ModelAndView mv = new ModelAndView();
         List<User> userList = statisticsBusinessService.findUserList();
        mv.addObject("userList", userList);
//        mv.addObject("backUrl", StringUtil.encodeUrl(url));
        mv.setViewName("/statistics/business/index");

        return mv;
    }



    private String createUrl(StatisticsSearchVO statisticsSearchVO, int pageIndex, int pageSize) {
        String url = pubConfig.getDynamicServer() + "/statistics/business/index.htm?";
       if(statisticsSearchVO.getStartTime()!=null){
            url+="&startTime="+statisticsSearchVO.getStartTime();
       }
        if(statisticsSearchVO.getEndTime()!=null){
            url+="&endTime="+statisticsSearchVO.getEndTime();
        }
        if(StringUtil.isNotNullOrEmpty(statisticsSearchVO.getResources())){
            url+="&resources="+statisticsSearchVO.getResourcesParm();
        }
        if(statisticsSearchVO.getUserId()!=null){
            url+="&userId="+statisticsSearchVO.getUserId();
        }
        return url;
    }

}

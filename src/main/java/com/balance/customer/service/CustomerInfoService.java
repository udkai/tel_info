package com.balance.customer.service;


import com.balance.common.dao.CommonDao;
import com.balance.common.vo.ComboboxVO;
import com.balance.customer.VO.CustomerInfoSearchVO;
import com.balance.customer.dao.CustomerInfoDao;
import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.User;
import com.balance.customer.util.StatusUtil;
import com.balance.sys.model.SysUser;
import com.balance.util.code.SerialNumUtil;
import com.balance.util.date.DateUtil;
import com.balance.util.encrypt.EncryptUtil;
import com.balance.util.excel.Excel2007Util;
import com.balance.util.excel.ExcelUtil;
import com.balance.util.string.StringUtil;
import com.balance.util.web.WebUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dsy on 2017/4/17.
 * 学员档案  Service
 */
@Service
public class CustomerInfoService {
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private CommonDao commonDao;

    /**
     * 查询
     */
    public List<CustomerInfo> search(CustomerInfoSearchVO customerInfoSearchVO, int pageIndex, int pageSize) {
        return customerInfoDao.search(customerInfoSearchVO, pageIndex, pageSize);
    }

    public int count(CustomerInfoSearchVO customerInfoSearchVO) {

        return customerInfoDao.count(customerInfoSearchVO);
    }

    /**
     * 查询业务员list
     */
    public List<User> findUserList() {
        return customerInfoDao.findUserList();
    }

    /**
     * 归档客户
     *
     * @param customer_id
     * @return
     */
    public int update(String customer_id) {
        return customerInfoDao.update(customer_id);
    }

    /**
     * 新增
     *
     * @param customerInfo
     * @return 成功1  失败0
     */
    public int add(CustomerInfo customerInfo) {
        return customerInfoDao.add(customerInfo);
    }

    /**
     * 修改
     *
     * @param customerInfo 客户信息
     * @return 成功1 失败0
     */
    public int update(CustomerInfo customerInfo) {

        return customerInfoDao.update(customerInfo);
    }

    /**
     * @return 客户信息
     */
    public CustomerInfo get(int id) {
        return customerInfoDao.getById(id);
    }


    /**
     * 删除
     *
     * @param customer_id_start
     * @param customer_id_end
     * @return
     */
    public int delete(String customer_id_start, String customer_id_end) {
        return customerInfoDao.delete(customer_id_start, customer_id_end);
    }

    /**
     * 批量导入客户资料信息
     * 如果校验错误，返回错误信息，如果正确，返回空值
     *
     * @param file_path excel地址
     * @return
     */
    public String saveImport(String file_path, String resources, String create_person) {
        // 1读取excel数据
        List<String[]> list = new Excel2007Util().readExcelContent(file_path, 2);
        // 2校验数据
//        String checkResult = checkData(list);
//        if (checkResult.length() != 0)
//            return checkResult;
        // 3导入数据
        List<CustomerInfo> listTrans = transData(list);
        for (CustomerInfo customerInfo : listTrans) {
            String customer_old_id = commonDao.getCustomerId();
            customer_old_id = customer_old_id == null ? "00000001" : customer_old_id;
            String customer_id = StringUtil.createCustomerId(customer_old_id);
            customerInfo.setResources(resources);
            customerInfo.setId(customer_id);
            customerInfo.setStatus(0);
            customerInfo.setRemark_status(0);
            customerInfo.setCreate_by(create_person);// 导入人
            customerInfoDao.batchAdd(customerInfo);
            commonDao.updateCustomerId(customer_id);
        }
        return "";
    }

    /**
     * 校验数据
     *
     * @param list
     * @return
     */
    private String checkData(List<String[]> list) {
        // 数据列序号 姓名 电话号码  技术等级 现职级时间 军衔

//        List<ComboboxVO> listClass = commonDao.listClass();
//        HashMap<String, String> hashClass = new HashMap();// 班级hashmap
//        for (ComboboxVO vo : listClass) {
//            hashClass.put(vo.getContent(), vo.getValue());
//        }
        //校验文件内电话是否重复
        StringBuffer sb = new StringBuffer();
        String[] tels = new String[list.size()];
        int j = 0;
        for (String[] str : list) {
            tels[j] = str[1].trim();
            j++;
        }
        for (int a = 0; a < tels.length; a++) {
            String tel = tels[a];
            for (int b = 1 + a; b < tels.length; b++) {
                String tel_check = tels[b];
                if (tel.equals(tel_check)) {
                    sb.append("第" + (a + 2) + "行和" + (b + 2) + "电话号码重复<br/>");
                }
            }

        }
        //校验数据库是否重复
        if (sb.length() != 0) {
            return sb.toString();
        }
        int i = 2;
        for (String[] str : list) {

            if (str.length != 2) {
                sb.append("第" + i + "行数据不全" + "<br/>");
            }
            if (StringUtil.isNullOrEmpty(str[0])) {
                sb.append("第" + i + "行姓名为空" + "<br/>");
            }
            if (StringUtil.isNullOrEmpty(str[1])) {
                sb.append("第" + i + "行电话号码为空" + "<br/>");
            }
            CustomerInfo customerInfo = customerInfoDao.getByMobile(str[1]);
            if (customerInfo != null) {
                sb.append("第" + i + "行电话号码已存在-" + str[1] + "<br/>");
            }

            i++;
        }
        return sb.toString();
    }

    /**
     * 转换数据
     *
     * @param list
     * @return
     */
    private List<CustomerInfo> transData(List<String[]> list) {
        List<CustomerInfo> listTrans = new ArrayList();
        for (String[] str : list) {
            CustomerInfo customerInfo = new CustomerInfo();

            customerInfo.setName(str[0]);//姓名
            customerInfo.setMobile(str[1]);// 手机号码

            listTrans.add(customerInfo);
        }
        return listTrans;
    }

    public void export(CustomerInfoSearchVO customerInfoSearchVO, HttpServletResponse response) {
        List<CustomerInfo> dataList = customerInfoDao.export(customerInfoSearchVO);
        String[][] data = null;

        long sumMoney = 0;
//    if (dataList.size() <= 10000) {
        data = new String[dataList.size() + 2][8];
        data[0][0] = "客户编号";
        data[0][1] = "姓名";
        data[0][2] = "电话号码";
        data[0][3] = "备注";
        data[0][4] = "客户状态";
        data[0][5] = "所属业务员编号";
        data[0][6] = "所属业务员姓名";
        data[0][7] = "操作日期";

        for (int i = 0; i < dataList.size(); i++) {
            CustomerInfo customerInfo = dataList.get(i);
            data[i + 1][0] = WebUtil.getSafeStr(customerInfo.getId());
            data[i + 1][1] = WebUtil.getSafeStr(customerInfo.getName());
            data[i + 1][2] = WebUtil.getSafeStr(customerInfo.getMobile());
            data[i + 1][3] = WebUtil.getSafeStr(customerInfo.getRemark());
            data[i + 1][4] = WebUtil.getSafeStr(StatusUtil.parseStatus(customerInfo.getCustomer_status()));
            data[i + 1][5] = WebUtil.getSafeStr(customerInfo.getUser_id());
            data[i + 1][6] = WebUtil.getSafeStr(customerInfo.getUser_name());
            data[i + 1][7] = WebUtil.getSafeStr(DateUtil.dateToString(customerInfo.getLast_modify_at(),"yyyy-MM-dd HH:mm:ss"));
        }
//    }
//    if (dataList.size() + 1 > 6000) {
//        data = new String[2][13];
//        data[0][0] = "客户编号";
//        data[0][1] = "姓名";
//        data[0][2] = "电话号码";
//        data[0][3] = "备注";
//        data[0][4] = "所属业务员编号";
//        data[0][5] = "所属业务员姓名";
//        data[0][6] = "操作日期";
//        data[1][1] = "数据总数大于10000行无法导出，请缩小查询范围！";
//    }

        String report_name = "客户电话资料";
        String titleName = "客户电话资料";
        HSSFWorkbook wb;
        wb = ExcelUtil.createExcelWithTitle(data, titleName);
        OutputStream os = null;
        try {
            response.setContentType("application/msexcel");
            response.reset();
            response.setHeader("content-disposition", "attachment; filename=" + new String(report_name.getBytes("gb2312"), "ISO-8859-1") + ".xls");
            System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                os = null;
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

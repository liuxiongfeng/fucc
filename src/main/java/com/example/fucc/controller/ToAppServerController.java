package com.example.fucc.controller;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPOutputStream;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.fucc.config.FunctionProperties;
import com.example.fucc.service.AppService;
import com.example.fucc.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ToAppServerController {


    @Resource
    private AppService appService;
    /*
     * @Author: liuxiongfeng
     * @Date: 10:57 2018-7-26
     * @Description: 测试 ：向appserver请求获取数据
     **/
    @RequestMapping("/getAppServerInfo")
    public ModelAndView getAppInfo(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader("token");//获取请求header中的token
        String passtoken = request.getHeader("passtoken");//获取请求passtoken中的passtoken
        ModelAndView modelAndView = new ModelAndView("info"); //设置返回的页面
        Map<String,String> map = new HashMap();
        JSONObject jsonObject = null; //定义json对象
        HttpClientResult result = null;
        try {
            String requestUrl = request.getParameter("requestUrl");
            Map<String,String> requestMap = new HashMap();
            requestMap.put("userId",request.getParameter("userId"));
            requestMap.put("platform",request.getParameter("platform"));
            requestMap.put("question",request.getParameter("question"));
            result = HttpClientUtil.doPost("http://192.168.25.170:9921/robot-swhy/http/RobotService",requestMap);
            jsonObject = JSON.parseObject(result.getContent());
        }catch (Exception e){
            e.printStackTrace();
        }
        modelAndView.addObject(jsonObject);
        return modelAndView;
    }


    /*
     * @Author: liuxiongfeng
     * @Date: 10:01 2018-8-3
     * @Description: 测试：调试app服务端的例子
     **/
    @RequestMapping("/demo")
    public ModelAndView demo(String url) {

        //String token = request.getHeader("token");//获取请求header中的token
        //String passtoken = request.getHeader("passtoken");//获取请求passtoken中的passtoken
        Map<String,String> headerMap = new HashMap();
        headerMap.put("token","39ac835ac5be488ca5243ad5025c90b5");
        headerMap.put("passtoken","Vk1FUEhPOXlPODgyajA5MVnO88NPNWtggsrO8oMmoYE%3D");


        ModelAndView modelAndView = new ModelAndView("info");
        Map<String,String> map = new HashMap();
        JSONObject jsonObject = null;
        HttpClientResult result = null;
        url = "http://180.168.116.155:8001/mobile/staff/message/queryUnreadMessageRN/?" +
                "token=d69a625d7bf746f69ca06757e5633360&" +
                "passtoken=WG5ZcExDS2VXajhRNXljVu4xwkieMDJLShJMS4MZR8E%3D";
        try {
            /*拼接参数到map中*/
            Map<String,String> paramMap = new HashMap();
            String ipAddress = url.split("\\?")[0];
            String param = url.split("\\?")[1];
            String[] params = param.split("&");
            for (String pp : params){
                String key = pp.split("=")[0];
                String value = pp.split("=")[1];
                //paramMap.put(key,value);d
            }
            result = HttpClientUtil.doGet(ipAddress,headerMap,paramMap);
            jsonObject = JSON.parseObject(result.getContent());
        }catch (Exception e){
            e.printStackTrace();
        }
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    /*
     * @Author: liuxiongfeng
     * @Date: 10:03 2018-8-3
     * @Description: 调用esb的观点接口，获取观点数据
     **/
    @RequestMapping("/viewPoint")
    public String viewPoint(HttpServletRequest request, String userId, String iid, Model model) {
        if (userId == null && iid == null){
            String param = request.getParameter("data");
            String decode = BASE64Util.decode(param);
            JSONObject jsonObject = JSONObject.parseObject(decode);
            userId = jsonObject.getString("userid");
            iid = jsonObject.getString("iid");
        }
        JSONObject o   = null;
        try {
            JSONObject dfd = EsbUtils.getViewPoint("106817","483","1");
            JSONArray o_result =  (JSONArray)dfd.get("O_RESULT");
            o = (JSONObject)o_result.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> params = JSONObject.parseObject(o.toJSONString(), new TypeReference<Map<String, String>>(){});
        model.addAttribute("result", params);
        model.addAttribute("re",o.toString());
        return "viewpoint/viewDetail";
    }

    /*
     * @Author: liuxiongfeng
     * @Date: 10:03 2018-8-3
     * @Description: 接受app客户端的请求，解密请求数据，再转发请求数据
     **/

    @RequestMapping("/server")
    public ModelAndView server(HttpServletRequest request) {
        ModelAndView mv = null;

        String bizcode = request.getParameter("bizcode");
        String data = request.getParameter("data");
        String sign = request.getParameter("sign");
        String timestamp = request.getParameter("timestamp");
        String token = request.getHeader("token");//获取请求header中的token
        String passToken = request.getHeader("passToken");//获取请求passtoken中的passtoken

        //验证signkey的合法性
        boolean b = appService.VerifySignKey(bizcode, data, sign, timestamp);
        if (!b){
            return null;
        }
        String decode = BASE64Util.decode(data);
        JSONObject dataJSON = JSONObject.parseObject(decode);
        String userId = dataJSON.get("userid").toString();

        //验证passtoken
        String servletPath = request.getServletPath();
        // 新增需求，验证RN版本的非白名单中的请求是否合法
        if (servletPath.indexOf("RN") != -1) {
            long stamp = 0; // passToken为非白名单路径的必传值，否则可能抛出异常
            try {
                stamp = Long.parseLong(AES.decrptPwd(passToken));
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            long tempDate = System.currentTimeMillis();

            // 如果传入验证时间戳在服务器当前时间限定的范围内，则正常请求，否则返回错误信息
            if (tempDate - 1000 * 60 * 60 > stamp || tempDate + 1000 * 60 * 60 < stamp) {
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("success", false);
                jsonResult.put("code", "40001");
                jsonResult.put("note", "请矫正系统时间");
                return null;
            }
        }
        //验证token
        boolean b1 = appService.loginCookieQuery(userId, token);
        if (!b1){
            return null;
        }
        String function1 = FunctionProperties.getString(bizcode);

        mv = new ModelAndView("forward:" + function1);//默认为forward模式
        return mv;
    }

    @RequestMapping("/viewEdit")
    public String viewEdit(HttpServletRequest request, String userId, String iid, Model model) {
        try {
            JSONObject label = EsbUtils.getLabel();
            List labelList = (List)label.get("O_RESULT");
            if (labelList.size()>0) model.addAttribute("labels",labelList);
            JSONObject viewType = EsbUtils.getViewType();
            List viewTypeList = (List)viewType.get("O_RESULT");
            if(viewTypeList.size()>0) model.addAttribute("viewTypes",viewTypeList);
            String bt = request.getParameter("bt");
            model.addAttribute("ry","管理员");
            String gdid = request.getParameter("gdid");
            if(gdid!=null && !gdid.equals("")){
                JSONObject o   = null;
                try {
                    JSONObject dfd = EsbUtils.getViewPoint("106817",gdid,"2");
                    JSONArray o_result =  (JSONArray)dfd.get("O_RESULT");
                    o = (JSONObject)o_result.get(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                model.addAttribute("result", o.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "viewpoint/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/ueditor/config",headers = "Accept=application/json")
    public String ueditor(@RequestParam("action") String action, @RequestParam("noCache") String nocache, HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("application/json;charset=utf-8");
            org.springframework.core.io.Resource resource = new ClassPathResource("config.json");
            File file = resource.getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "/ueditor/imgUpload")
    @ResponseBody
    public String imgUpload(MultipartFile upfile,HttpServletRequest request) throws FileNotFoundException {
        String imgPath = "/home/weblogic/data/app/userfiles/Image/viewPoint";
        String suffixName = upfile.getOriginalFilename().substring(upfile.getOriginalFilename().lastIndexOf(".")); // 获取文件的后缀名
        String fileName = "up_" + new Date().getTime() + suffixName;
        try {
            SSH2Util.uploadFile(upfile,fileName,imgPath,"0640");
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String,Object> configs = new HashMap<>();
            configs.put("state","SUCCESS");
            configs.put("url","Image/viewPoint/"+fileName);
            configs.put("title","Image/viewPoint/"+fileName);
            configs.put("original","Image/viewPoint/"+fileName);
            return mapper.writeValueAsString(configs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("/saveView")
    @ResponseBody
    public JSONObject saveView(HttpServletRequest request) throws Exception{
        JSONObject json = new JSONObject();
        json.put("I_VIEWPOINT_ID",request.getParameter("gdid"));
        json.put("I_TITLE",request.getParameter("bt"));
        json.put("I_OPEN_CONTENT",request.getParameter("gknr"));
        json.put("I_CLOSE_CONTENT",request.getParameter("smnr"));
        json.put("I_SOURCE",request.getParameter("gdly"));
        json.put("I_BASIS",request.getParameter("tzyj"));
        json.put("I_ABSTRACT",request.getParameter("zy"));
        json.put("I_CHARGE_AMT",request.getParameter("jg"));
        json.put("I_RISK_LEVEL",request.getParameter("fxdj"));
        json.put("I_LABEL_ID",request.getParameter("gdbq"));
        json.put("I_VIEWPOINT_TYPE",request.getParameter("lx"));//观点类型
        json.put("I_IS_CHARGEABLE",request.getParameter("sfsf"));
        //json.put("I_RYBH",request.getParameter("rybh"));//人员编号的参数
        json.put("I_RYBH","106817");//人员编号的参数
        if (null == request.getParameter("gdid")||"".equals(request.getParameter("gdid"))){
            json.put("I_TYPE",1);//观点创建
        }else {
            json.put("I_TYPE",2);//观点编辑
        }
        JSONObject jsonObject = EsbUtils.saveView(json);
        return jsonObject;
    }

    public static Connection getConnect(){
        try {
            Connection conn = new Connection("192.168.24.252", 22);
            // 连接到主机
            conn.connect();
            // 使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword("weblogic", "sywg1234");
            if (!isconn) {
                System.out.println("用户名称或者是密码不正确");
            } else {
                System.out.println("服务器连接成功.");
                return conn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

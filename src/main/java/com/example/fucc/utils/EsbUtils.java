package com.example.fucc.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 对接crm 的esb接口
 */
public class EsbUtils {
    private static final String METHOD_POST = "POST";
    private static final String DEFAULT_CHARSET = "utf-8";
    private static final String MQ_CONFIG_PROPERTIES_FILE = "rabbitmq.properties";
    private static Boolean propInit = false;
    private static String sessionId;
    private static String esbLoginUrl;
    private static String esbLogoutUrl;
    private static String esbValidateUrl;
    private static String esbServiceUrl;
    private static String loginId;
    private static String loginPwd;
    private static String userToken;
    private static String viewPoint;
    private static String viewPointList;
    private static String label;
    private static String viewType;
    private static Integer connectTimeout;
    private static Integer readTimeout;
    private static String viewPointCreateESB;
    private static Log logger = LogFactory.getLog(EsbUtils.class);

    /**
     * 初始化静态变量
     * @throws IOException
     */

    private static void init() throws IOException {
        if(!propInit){
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Properties properties = new Properties();
            Enumeration urls = classLoader.getResources(MQ_CONFIG_PROPERTIES_FILE);
            while (urls.hasMoreElements()) {
                URL url = (URL) urls.nextElement();
                InputStream is = null;
                try {
                    URLConnection con = url.openConnection();
                    con.setUseCaches(false);
                    is = con.getInputStream();
                    properties.load(is);
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
            }
            esbLoginUrl = properties.getProperty("esbLoginUrl");
            esbLogoutUrl = properties.getProperty("esbLogoutUrl");
            esbValidateUrl = properties.getProperty("esbValidateUrl");
            esbServiceUrl = properties.getProperty("esbServiceUrl");
            loginId = properties.getProperty("loginId");
            loginPwd = properties.getProperty("loginPwd");
            userToken = properties.getProperty("userToken");
            viewPoint = properties.getProperty("viewPointESB");
            viewPointList = properties.getProperty("viewPointListESB");
            label = properties.getProperty("labelESB");
            viewType = properties.getProperty("viewTypeESB");
            readTimeout = Integer.valueOf(properties.getProperty("readTimeout"));
            connectTimeout = Integer.valueOf(properties.getProperty("connectTimeout"));
            viewPointCreateESB = properties.getProperty("viewPointCreateESB");
            propInit = true;
        }
    }


    /**
     * 获取观点详情
     * @return
     * @throws Exception
     */
    public static JSONObject getViewPoint(String userId,String iid,String type) throws Exception {
        init();
        JSONObject ret = new JSONObject();
        boolean isLogin = valadate();
        if (isLogin) {
            try {
                JSONObject json = new JSONObject();

                json.put("I_RYBH", userId);
                json.put("I_ID", iid);
                json.put("I_TYPE", type);
                ret = getService(viewPoint, json.toString());
                System.out.println(ret);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("调用crm接口失败");
                return null;
            }
        } else {
            logger.error("登录esb接口失败");
            return null;
        }
        return ret;
    }

    /**
     * 获取观点详情列表
     * @return
     * @throws Exception
     */
    public static JSONObject getViewPointList(String userId,String type,String pageNO,String pageLength) throws Exception {
        init();
        JSONObject ret = new JSONObject();
        boolean isLogin = valadate();
        if (isLogin) {
            try {
                JSONObject json = new JSONObject();

                json.put("I_RYBH", userId);
                json.put("I_TYPE", type);
                if (StringUtils.isEmpty(pageNO)){
                    json.put("I_PAGENO",1);
                }else {
                    json.put("I_PAGENO", pageNO);
                }
                if (StringUtils.isEmpty(pageLength)){
                    json.put("I_PAGELENGTH", 10);
                }else {
                    json.put("I_PAGELENGTH", pageLength);
                }
                ret = getService(viewPointList, json.toString());
                System.out.println(ret);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("调用crm接口失败");
                return null;
            }
        } else {
            logger.error("登录esb接口失败");
            return null;
        }
        return ret;
    }

    /**
     * 观点保存
     * @param json
     * @return
     * @throws Exception
     */
    public static JSONObject saveView(JSONObject json) throws Exception{
        init();
        JSONObject ret = new JSONObject();
        boolean isLogin = valadate();
        if (isLogin) {
            try {
                ret = getService(viewPointCreateESB, json.toString());
                System.out.println(ret);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("调用crm接口失败");
                return null;
            }
        } else {
            logger.error("登录esb接口失败");
            return null;
        }
        return ret;
    }


    /**
     * 获取观点标签
     * @return
     * @throws Exception
     */
    public static JSONObject getLabel() throws Exception {
        init();
        JSONObject ret = new JSONObject();
        boolean isLogin = valadate();
        if (isLogin) {
            try {
                JSONObject json = new JSONObject();

                //先写死测试
                json.put("I_PAGENO", "");
                json.put("I_PAGELENGTH", "");
                json.put("I_SORT", "");
                json.put("I_UPDATE_TIME", "");
                json.put("I_STATUS", "2");
                json.put("I_LABEL_BLOCK", "3");
                ret = getService(label, json.toString());
                System.out.println(ret);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("调用crm接口失败");
                return null;
            }
        } else {
            logger.error("登录esb接口失败");
            return null;
        }
        return ret;
    }

    /**
     * 获取观点类型
     * @return
     * @throws Exception
     */
    public static JSONObject getViewType() throws Exception {
        init();
        JSONObject ret = new JSONObject();
        boolean isLogin = valadate();
        if (isLogin) {
            try {
                JSONObject json = new JSONObject();

                //先写死测试
                ret = getService(viewType, json.toString());
                System.out.println(ret);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("调用crm接口失败");
                return null;
            }
        } else {
            logger.error("登录esb接口失败");
            return null;
        }
        return ret;
    }

    public static boolean loginCookieQuery(String userId, String token) throws Exception {
        init();
        JSONObject ret = new JSONObject();
        boolean isLogin = valadate();
        if (isLogin) {
            try {
                JSONObject json = new JSONObject();
                json.put("I_USERID", userId);
                json.put("I_TOKEN", token);
                ret = getService(userToken, json.toString());
                System.out.println(ret);
                JSONArray o_result =(JSONArray) ret.get("O_RESULT");
                JSONObject o = (JSONObject)o_result.get(0);
                if (o.size() != 0){
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("验证userid失败");
                return false;
            }
        } else {
            logger.error("登录esb接口失败");
        }
        return false;
    }

    public static JSONObject getCookieInfo(String userId, String token) throws Exception {
        init();
        JSONObject ret = new JSONObject();
        boolean isLogin = valadate();
        if (isLogin) {
            try {
                JSONObject json = new JSONObject();
                json.put("I_USERID", userId);
                json.put("I_TOKEN", token);
                ret = getService(userToken, json.toString());
                System.out.println(ret);
                JSONArray o_result =(JSONArray) ret.get("O_RESULT");
                JSONObject o = (JSONObject)o_result.get(0);
                if (o.size() != 0){
                    return o;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("获取用户信息失败");
            }
        } else {
            logger.error("登录esb接口失败");
        }
        return null;
    }

    /**
     *esb登陆
     * @return
     * @throws Exception
     */
    public static Boolean login() throws Exception{
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("loginId",loginId);
        jsonParams.put("loginPwd",loginPwd);
        logger.info("登陆esb接口");
        JSONObject jsonRet = JSONObject.parseObject(doPost(esbLoginUrl,jsonParams.toString(),DEFAULT_CHARSET));
        String code = String.valueOf(jsonRet.get("code"));
        if("1".equals(code)){
            logger.info("esb接口登陆成功");
            sessionId = String.valueOf(jsonRet.get("sessionId"));
            return true;
        }else {
            logger.error("esb接口登陆失败,note=[" + String.valueOf(jsonRet.get("note")) + "]");
            return false;
        }
    }

    /**
     * esb登出
     * @return
     * @throws Exception
     */
    public static Boolean logout()throws Exception{
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("sessionId",sessionId);
        JSONObject jsonRet = JSONObject.parseObject(doPost(esbLogoutUrl,jsonParams.toString(),DEFAULT_CHARSET));
        String code = String.valueOf(jsonRet.get("code"));
        if("1".equals(code)){
            logger.info("esb接口登出成功");
            sessionId = String.valueOf(jsonRet.get("sessionId"));
            return true;
        }else {
            logger.error("esb接口登出失败,note=[" + String.valueOf(jsonRet.get("note")) + "]");
            return false;
        }
    }

    /**
     * esb会话验证，过期则重新登录
     * @return
     * @throws Exception
     */
    public static Boolean valadate() throws Exception{
        Boolean flag = true;
        try {
            if(sessionId =="" || sessionId == null){
                flag = login();
            }else {
                JSONObject jsonParams = new JSONObject();
                jsonParams.put("sessionId",sessionId);
                JSONObject jsonRet = JSONObject.parseObject(doPost(esbValidateUrl,jsonParams.toString(),DEFAULT_CHARSET));
                String code = String.valueOf(jsonRet.get("code"));
                if("1".equals(code)){
                    logger.info("crm esb会话正常");
                    flag = true;
                }else {
                    logger.info("crm esb会话过期 , 重新登陆esb");
                    flag = login();
                }
            }
        } catch (Exception e) {
            flag = false;
            logger.error("验证crm esb会话失败:"+e.getMessage());
        }
        return flag;
    }

    /**
     * esb接口调用
     * @param serviceId 接口id
     * @param params    参数
     * @return
     * @throws Exception
     */
    public static JSONObject getService(String serviceId,String params)throws Exception{
        JSONObject jsonRet = new JSONObject();
        JSONObject jsonParams = JSONObject.parseObject(params);
        jsonParams.put("sessionId",sessionId);
        jsonParams.put("serviceId",serviceId);
        System.out.println(jsonParams.toString());
        jsonRet = JSONObject.parseObject(URLDecoder.decode(doPost(esbServiceUrl, jsonParams.toString(), DEFAULT_CHARSET),"utf-8"));
        return jsonRet;
    }

    /**
     * post调用esb的https接口
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws Exception
     */
    public static String doPost(String url, String params, String charset) throws Exception{
        HttpsURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        String ctype = "application/json;charset=" + charset;
        byte[] content = {};
        if(params != null){
            content = params.getBytes(charset);
        }
        try {
            try{
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
                SSLContext.setDefault(ctx);

                conn = getConnection(new URL(null,url,new sun.net.www.protocol.https.Handler()), METHOD_POST, ctype);
//                conn = getConnection(new URL(url), METHOD_POST, ctype);
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            }catch(Exception e){
                System.out.println(e.getMessage());
                throw e;
            }
            try{
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
            }catch(IOException e){
                System.out.println(e.getMessage());
                throw e;
            }

        }finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static HttpsURLConnection getConnection(URL url, String method, String ctype)
            throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("User-Agent", "stargate");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }
}

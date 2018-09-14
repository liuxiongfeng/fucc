package com.example.fucc.utils;

import ch.ethz.ssh2.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by zhangyuhan on 2018/8/30.
 */
public class SSH2Util {
    private static final String MQ_CONFIG_PROPERTIES_FILE = "rabbitmq.properties";
    private static Boolean propInit = false;
    private static String ip;
    private static int port;
    private static String name;
    private static String password;

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
            ip = properties.getProperty("ssh.ip");
            String p = properties.getProperty("ssh.port");
            port = p.equals("")?22:Integer.valueOf(p);
            name = properties.getProperty("ssh.name");
            password = properties.getProperty("ssh.password");
            propInit = true;
        }
    }

    /**
     * 获取服务器上相应文件的流
     * @param remoteFile
     * @param remoteDir
     * @return
     * @throws Exception
     */
    public static SCPInputStream getStream(String remoteFile,String remoteDir) throws Exception{
        init();
        Connection conn = new Connection(ip,port);
        conn.connect();
        boolean isAuthenticated = conn.authenticateWithPassword(name,password);
        if(!isAuthenticated){
            System.out.println("建立连接失败");
        }
        SCPClient client = conn.createSCPClient();
        return client.get(remoteDir + "/" + remoteFile);
    }

    /**
     * 上传文件
     * @param f
     * @param remoteName
     * @param remoteDir
     * @param mode
     * @throws IOException
     */
    public static void uploadFile(MultipartFile f,String remoteName,String remoteDir,String mode) throws IOException{
        init();
        InputStream is = null;
        SCPOutputStream os = null;
        Connection conn = null;
        try {
            init();
            conn = new Connection(ip,port);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(name,password);
            if(!isAuthenticated){
                System.out.println("建立连接失败");
            }
            SCPClient client = new SCPClient(conn);
            is = f.getInputStream();
            os = client.put(remoteName,is.available(),remoteDir,mode);
            byte[] b = new byte[4096];
            int i;
            while ((i = is.read(b))!=-1){
                os.write(b,0,i);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is!=null){
                is.close();
            }
            if (os!=null){
                os.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

}

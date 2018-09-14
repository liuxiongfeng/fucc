package com.example.fucc.servlet;

import ch.ethz.ssh2.SCPInputStream;
import com.example.fucc.utils.SSH2Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by zhangyuhan on 2018/8/6.
 */
@WebServlet(urlPatterns = "/UserFiles/*")
public class ImageStream extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getRequestURI();
        String realFile = null;
        boolean resource = false;
        int n = filename.indexOf("/UserFiles/");
        if(n >= 0) {
            filename = filename.substring(n + "/UserFiles/".length());
            filename = URLDecoder.decode(filename, "UTF-8");

            realFile = "/home/weblogic/data/app/userfiles/" + filename;
        }
        int index = realFile.lastIndexOf("/");
        String remoteName = realFile.substring(index+1,realFile.length());
        String remoteDir = realFile.substring(0,index);
        SCPInputStream is = null;
        ServletOutputStream os = null;
        try {
            is = SSH2Util.getStream(remoteName,remoteDir);
            String dispatch = this.getServletContext().getMimeType(filename);
            if(dispatch != null && dispatch.startsWith("text")) {
                resp.setContentType(dispatch + ";charset=GBK");
            }
            resp.setHeader("Content-disposition", "filename=" + new String(filename.getBytes("GB2312"), "ISO8859-1"));
            os = resp.getOutputStream();
            int b;
            while((b = is.read()) != -1) {
                os.write(b);
            }
            os.flush();
        } catch (Exception var12) {
            this.forwardError(req, resp);
        }finally {
            if (is!=null){
                is.close();
            }
            if (os!=null){
                os.close();
            }
        }

    }

//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String filename = req.getRequestURI();
//        String realFile = null;
//        boolean resource = false;
//        int n = filename.indexOf("/UserFiles/");
//        if(n >= 0) {
//            filename = filename.substring(n + "/UserFiles/".length());
//            filename = URLDecoder.decode(filename, "UTF-8");
//
//            realFile = "/home/weblogic/data/app/userfiles/Image/" + filename;
//        }
//        try {
//            File e = new File(realFile);
//            if(e.exists()) {
//                String dispatch = this.getServletContext().getMimeType(filename);
//                if(dispatch != null && dispatch.startsWith("text")) {
//                    resp.setContentType(dispatch + ";charset=GBK");
//                }
//
//                resp.setHeader("Content-disposition", "filename=" + new String(filename.getBytes("GB2312"), "ISO8859-1"));
//                ServletOutputStream outputStream = resp.getOutputStream();
//                FileInputStream fi = new FileInputStream(realFile);
//
//                int b;
//                while((b = fi.read()) != -1) {
//                    outputStream.write(b);
//                }
//
//                fi.close();
//                outputStream.flush();
//                outputStream.close();
//            } else if(resource && filename.startsWith("images/")) {
//                RequestDispatcher dispatch1 = req.getRequestDispatcher("/" + filename);
//                dispatch1.forward(req, resp);
//            } else {
//                this.forwardError(req, resp);
//            }
//        } catch (Exception var12) {
//            this.forwardError(req, resp);
//        }
//
//    }

    protected void forwardError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(403);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import com.foundation.manage.util.FileUtil;
import com.foundation.manage.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/fileUpload")
public class FileUploadController extends BaseController {
    String domain = PropertiesUtil.getPropertieyValue("ms.domain");


    @ResponseBody
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public JSONObject upload(HttpServletRequest request,
                             HttpServletResponse response, @RequestParam MultipartFile upload) {
        return FileUtil.upload(request,upload);
    }
    @ResponseBody
    @RequestMapping(value = "/uploadImage1", method = RequestMethod.POST)
    public JSONObject upload1(HttpServletRequest request,
                             HttpServletResponse response, @RequestParam MultipartFile upload1) {
        return FileUtil.upload(request,upload1);
    }


    /*
     * ckeditor上传
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void fileUpload(HttpServletRequest request,
                           HttpServletResponse response, @RequestParam MultipartFile upload) {
        OutputStream out = null;
        PrintWriter printWriter = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();
            //String baseDir = getUploadpath(request) + "upload\\";
            String baseDir = getUploadpath(request) + "upload";
            //判断有没有结尾符,没有得加上
            if (!baseDir.endsWith(File.separator)) {
                baseDir = baseDir + File.separator;
            }
            File baseDirFile = new File(baseDir);
            if (!baseDirFile.exists()) {
                baseDirFile.mkdirs();
            }

            File uFile = new File(baseDir + fileName);
            if (!uFile.exists()) {
                uFile.createNewFile();
            }

            String uploadPath = baseDir + fileName;
            System.out.println(uploadPath);
            out = new FileOutputStream(new File(uploadPath));
            out.write(bytes);
            String callback = request.getParameter("CKEditorFuncNum");
            System.out.println("callback:" + callback);
            printWriter = response.getWriter();
            String filePath = request.getContextPath() + "/upload/" + fileName;

            String uploadedFileName = domain + "/upload/" + fileName;

            printWriter
                    .println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
                            + callback
                            + ",'"
                            + uploadedFileName
                            + "',''"
                            + ")</script>");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }


    /*
     * ckeditor浏览服务器
     */
    @RequestMapping(value = "/showImage")
    public void showImage(HttpServletRequest request,
                          HttpServletResponse response) {
        PrintWriter out = null;
        List<String> fileList = new ArrayList<String>();
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //String filePath = getUploadpath(request) + "upload\\";
            String filePath = getUploadpath(request) + "upload";
            //判断有没有结尾符,没有得加上
            if (!filePath.endsWith(File.separator)) {
                filePath = filePath + File.separator;
            }
            out = response.getWriter();
            File file = new File(filePath);
            String callback = request.getParameter("CKEditorFuncNum");
            System.out.println("showImage method callback:" + callback);

            String staticDomain = PropertiesUtil.getPropertieyValue("ms.static.domain");
            String ckeditorPath = staticDomain + "/assets/common/plugins/ckeditor/ckeditor.js";

            out.println("<script type='text/javascript' src='" + ckeditorPath + "'></script>");
            out.println("<script>");
            out.println("function choose(obj){");
            out.println("window.opener.CKEDITOR.tools.callFunction(" + callback
                    + ",obj)");
            out.println("window.close();");
            out.println("}");
            out.println("</script>");
            out.println("<h2>单击图片进行选择</h2>");
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File file2 : files) {
                    fileList.add(file2.getName());
                    String fileName = file2.getName();
                    System.out.println("fileName:" + fileName);
                    fileName = domain + "/upload/" + fileName;
                    out.println("<img width='150px' height='150px'  src='" + fileName + "' onclick=\""
                            + "choose('" + fileName + "')\">");
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }


        return;
    }


    // 获得服务器的上传路径
    public String getUploadpath(HttpServletRequest request) {
        String path = "";
        path = request.getSession().getServletContext().getRealPath("/");
        return path;
    }


}

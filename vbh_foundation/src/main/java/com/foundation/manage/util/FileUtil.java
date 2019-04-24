package com.foundation.manage.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件处理
 * 创建人：Devin W Zhang
 * 创建时间：2019-3-11 17:00:53
 */
public class FileUtil {
public static Logger logger= LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取文件大小 返回 KB 保留3位小数  没有文件时返回0
     *
     * @param filepath 文件完整路径，包括文件名
     * @return
     */
    public static Double getFilesize(String filepath) {
        File backupath = new File(filepath);
        return Double.valueOf(backupath.length()) / 1000.000;
    }

    /**
     * 创建目录
     *
     * @return
     */
    public static Boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (!dir.getParentFile().exists()) {                //判断有没有父路径，就是判断文件整个路径是否存在
            return dir.getParentFile().mkdirs();        //不存在就全部创建
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
     *                        String
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myDelFile = new File(filePath);
            myDelFile.delete();
        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 读取到字节数组0
     *
     * @param filePath //路径
     * @throws IOException
     */
    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        fi.close();
        return buffer;
    }

    /**
     * 读取到字节数组1
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filePath) throws IOException {

        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    /**
     * 读取到字节数组2
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray3(String filePath) throws IOException {

        FileChannel fc = null;
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(filePath, "r");
            fc = rf.getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
                    fc.size()).load();
            //System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rf.close();
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 获得服务器的上传路径
    public static String getUploadpath(HttpServletRequest request) {
        String path = "";
        path = request.getSession().getServletContext().getRealPath("/");
        return path;
    }

    public static JSONObject upload(HttpServletRequest request, MultipartFile file) {

        OutputStream out = null;
        JSONObject pd = new JSONObject();

        try {
            if (null != file && !file.isEmpty()) {
                String fileName = DateUtil.date2Str(new Date(),"yyyyMMddHHmmssSSS")+"_"+ file.getOriginalFilename();
                //文件上传路径

                String baseDir = getUploadpath(request) + "upload";
                //判断有没有结尾符,没有得加上
                if (!baseDir.endsWith(File.separator)) {
                    baseDir = baseDir + File.separator;
                }

                //String baseDir = "/foundation/upload/";
                logger.info("=================="+baseDir);
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
                logger.info("=================="+uploadPath);
                out = new FileOutputStream(new File(uploadPath));
                out.write(file.getBytes());
                out.flush();
                out.close();

                String domain = PropertiesUtil.getPropertieyValue("ms.domain");

                pd.put("filePath", domain + "/upload/" + fileName);

                pd.put("fileName", fileName);
                pd.put("result", 1);
            } else {
                System.out.println("上传失败");
                pd.put("result", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("result", 0);
        }
        return pd;
    }

}
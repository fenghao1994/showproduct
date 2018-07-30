package com.shfound.showproduct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.UUID;

@Controller
public class ImageUploadController {
    @Autowired
    private Environment env;

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public void uploadImg(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "uploadImage",
                                  required = false) MultipartFile file) {
        try {
            if (file == null) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("图片不能为空");
            } else {
                String fileName = file.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                // 这里我使用随机字符串来重新命名图片
                fileName = UUID.randomUUID().toString() + Calendar.getInstance().getTimeInMillis() + suffixName;
                savePic(file.getInputStream(), fileName);
                String url = env.getProperty("web.router") + fileName;
                response.getWriter().write(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePic(InputStream inputStream, String fileName) {

        OutputStream os = null;
        try {
            String path = env.getProperty("image.upload.path");
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

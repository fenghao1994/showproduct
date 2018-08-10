package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.model.InviteModel;
import com.shfound.showproduct.service.InviteService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

//邀请审核
@Controller
@RequestMapping("/invite")
public class InviteController {

    @Autowired
    public InviteService inviteService;
    @Autowired
    private Environment env;

    @RequestMapping(value = "/client/create/invite", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult> createInvite(HttpServletRequest request, @RequestParam(name = "image") MultipartFile file) {
        try {
            String superUser = request.getParameter("superUser");
            String subUser = request.getParameter("subUser");

            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + Calendar.getInstance().getTimeInMillis() + suffixName;
            savePic(file.getInputStream(), fileName);
            String url = env.getProperty("web.router") + fileName;

            InviteModel inviteModel = new InviteModel();
            inviteModel.setImgPath(url);
            inviteModel.setStatus(0);
            inviteModel.setSuperUser(superUser);
            inviteModel.setSubUser(subUser);

            int invite = inviteService.createInvite(inviteModel);
            SuccessResult successResult = new SuccessResult();
            successResult.setCode(invite);
            if (invite == 1000) {
                successResult.setMessage("上传成功,等待管理员审核");
            } else {
                successResult.setMessage("提交失败,此微信ID 可能已经被其他人邀请过");
            }
            return new ResponseEntity<>(successResult, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            SuccessResult successResult = new SuccessResult();
            successResult.setCode(1001);
            successResult.setMessage("上传失败" + e.getMessage());
            return new ResponseEntity<>(successResult, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/root/update/invite", method = RequestMethod.POST)
    public ResponseEntity updateInvite(@RequestParam("id") int id, @RequestParam("status") int status) {
        InviteModel inviteModel = new InviteModel();
        inviteModel.setId(id);
        inviteModel.setStatus(status);
        int s = inviteService.updateInvite(inviteModel);
        if (s == 1000) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/root/getAllInvite", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<List<InviteModel>>> getAllInvite() {
        List<InviteModel> allInviteModel = inviteService.getAllInviteModel();
        SuccessResult<List<InviteModel>> listSuccessResult = new SuccessResult<>();
        listSuccessResult.setCode(1000);
        listSuccessResult.setDate(allInviteModel);

        return new ResponseEntity<>(listSuccessResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/root/delete", method = RequestMethod.POST)
    public ResponseEntity delete() {
        inviteService.deleteImg();
        return new ResponseEntity(HttpStatus.OK);
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

package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.controller.result.UserResult;
import com.shfound.showproduct.model.UserModel;
import com.shfound.showproduct.service.UserService;
import com.shfound.showproduct.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> register(@RequestParam("mobile") String mobile, @RequestParam("code") String code, @RequestParam("password") String password) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (!Utils.isMobilePhone(mobile)) {
            setUserResultAttr(userResult, 1001, "手机号错误");
        } else {
            //todo 验证码验证
            UserModel userModel = new UserModel();
            userModel.setMobile(mobile);
            userModel.setPassword(password);
            int resultCode = userService.register(userModel);
            switch (resultCode) {
                case 1000: setUserResultAttr(userResult, 1000, "注册成功");;break;
                case 1001: setUserResultAttr(userResult, 1001, "注册失败");;break;
                case 1002: setUserResultAttr(userResult, 1001, "账户已存在");;break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> login(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (!Utils.isMobilePhone(mobile)) {
            setUserResultAttr(userResult, 1001, "手机号错误");
        } else {
            UserModel userModel = new UserModel();
            userModel.setMobile(mobile);
            userModel.setPassword(password);
            int resultCode = userService.login(userModel);
            switch (resultCode) {
                case 1000:setUserResultAttr(userResult, 1000, "登陆成功");;break;
                case 1001:setUserResultAttr(userResult, 1001, "用户不存在");;break;
                case 1002:setUserResultAttr(userResult, 1001, "密码错误");;break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> updatePassword(@RequestParam("mobile") String mobile, @RequestParam("code") String code, @RequestParam("password")String password) {
        //todo 验证验证码
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (!Utils.isMobilePhone(mobile)) {
            setUserResultAttr(userResult, 1001, "手机号错误");
        } else {
            UserModel userModel = new UserModel();
            userModel.setMobile(mobile);
            userModel.setPassword(password);
            int resultCode = userService.updateUserPassword(userModel);
            switch (resultCode) {
                case 1000:setUserResultAttr(userResult, 1000, "修改成功");break;
                case 1001:setUserResultAttr(userResult, 1001, "用户不存在");break;
                case 1002:setUserResultAttr(userResult, 1001, "修改密码失败");break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/headimg", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> updateHeadImg(@RequestParam("mobile") String mobile, @RequestParam("headImg")String headImg) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (!Utils.isMobilePhone(mobile)) {
            setUserResultAttr(userResult, 1001, "手机号错误");
        } else {
            UserModel userModel = new UserModel();
            userModel.setMobile(mobile);
            userModel.setHeadImg(headImg);
            int resultCode = userService.updateUserHeadImg(userModel);
            switch (resultCode) {
                case 1000:setUserResultAttr(userResult, 1000, "修改成功");break;
                case 1001:setUserResultAttr(userResult, 1001, "用户不存在");break;
                case 1002:setUserResultAttr(userResult, 1001, "修改失败");break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    private void setUserResultAttr(UserResult userResult, int code, String msg) {
        userResult.setCode(code);
        userResult.setMsg(msg);
    }

    private void setSuccessResult(SuccessResult<UserResult>successResult, UserResult userResult) {
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(userResult);
    }
}

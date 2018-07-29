package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.CustomerResult;
import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.controller.result.UserResult;
import com.shfound.showproduct.model.UserModel;
import com.shfound.showproduct.service.UserService;
import com.shfound.showproduct.util.Utils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> register(@RequestParam(value = "mobile", required = false) String mobile, @RequestParam(value = "wxId") String wxId, @RequestParam("password") String password, @RequestParam(value = "super_invite_code", required = false)String superInviteCode) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (Strings.isEmpty(wxId)) {
            setUserResultAttr(userResult, 1001, "微信号不能为空");
        } else if (Strings.isEmpty(mobile) && !Utils.isMobilePhone(mobile)) {
            setUserResultAttr(userResult, 1001, "手机号错误");
        } else {
            UserModel userModel = new UserModel();
            userModel.setMobile(mobile);
            userModel.setPassword(password);
            int resultCode = userService.register(userModel);
            switch (resultCode) {
                case 1000:
                    setUserResultAttr(userResult, 1000, "注册成功");
                    break;
                case 1001:
                    setUserResultAttr(userResult, 1001, "注册失败");
                    break;
                case 1002:
                    setUserResultAttr(userResult, 1001, "账户已存在");
                    break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> login(@RequestParam(value = "wxId") String wxId, @RequestParam("password") String password) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (Strings.isEmpty(wxId)) {
            setUserResultAttr(userResult, 1001, "微信号不能为空");
        } else {
            UserModel userModel = new UserModel();
            userModel.setMobile(wxId);
            userModel.setPassword(password);
            int resultCode = userService.login(userModel);
            switch (resultCode) {
                case 1000:
                    setUserResultAttr(userResult, 1000, "登陆成功");
                    setRedis(userResult, wxId);
                    break;
                case 1001:
                    setUserResultAttr(userResult, 1001, "用户不存在");
                    break;
                case 1002:
                    setUserResultAttr(userResult, 1001, "密码错误");
                    break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    private void setRedis(UserResult userResult, String mobile) {
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(token, mobile, 2L, TimeUnit.DAYS);
        userResult.setToken(token);
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> updatePassword(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {
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
                case 1000:
                    setUserResultAttr(userResult, 1000, "修改成功");
                    break;
                case 1001:
                    setUserResultAttr(userResult, 1001, "用户不存在");
                    break;
                case 1002:
                    setUserResultAttr(userResult, 1001, "修改密码失败");
                    break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/headimg", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> updateHeadImg(@RequestParam("mobile") String mobile, @RequestParam("headImg") String headImg) {
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
                case 1000:
                    setUserResultAttr(userResult, 1000, "修改成功");
                    break;
                case 1001:
                    setUserResultAttr(userResult, 1001, "用户不存在");
                    break;
                case 1002:
                    setUserResultAttr(userResult, 1001, "修改失败");
                    break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    private void setUserResultAttr(UserResult userResult, int code, String msg) {
        userResult.setCode(code);
        userResult.setMsg(msg);
    }

    private void setSuccessResult(SuccessResult<UserResult> successResult, UserResult userResult) {
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(userResult);
    }

    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping(value = "getAllUser", method = RequestMethod.POST)
    public ResponseEntity<List<UserModel>> getAllUser() {
        List<UserModel> allUser = userService.getAllUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    /**
     * 管理端 通过搜索找到用户
     * @param wxId
     * @return
     */
    @RequestMapping(value = "/getSearchUser", method = RequestMethod.POST)
    public ResponseEntity<List<UserModel>> getSearchUer(@RequestParam("wxId") String wxId) {
        UserModel user = userService.getOneUser(wxId);
        List<UserModel> list = new ArrayList<>();
        if (user != null) {
            list.add(user);
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

    /**
     * 获取关系图谱
     * @param wxId
     * @return
     */
    @RequestMapping(value = "/getRelationship", method = RequestMethod.POST)
    public ResponseEntity relationship(@RequestParam("wxId")String wxId) {
        CustomerResult customerResult = userService.getCustomerResult(wxId);

        return new ResponseEntity<>(customerResult, HttpStatus.OK);
    }
}

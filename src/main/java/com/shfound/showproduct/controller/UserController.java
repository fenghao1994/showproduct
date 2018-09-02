package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.CustomerResult;
import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.controller.result.UserResult;
import com.shfound.showproduct.model.UserModel;
import com.shfound.showproduct.service.UserService;
import com.shfound.showproduct.util.Utils;
import io.rong.RongCloud;
import io.rong.models.response.TokenResult;
import io.rong.methods.user.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private Environment env;

    RongCloud rongCloud;
    User User;


    @RequestMapping(value = "/invitation")
    public String invitationWithoutId(Model model) {
        model.addAttribute("code", "");
        return "inviteuser";
    }

    @RequestMapping(value = "/invitation/{id}")
    public String invitation(@PathVariable("id")String id, Model model) {
        if (id == null) {
            id = "";
        }
        model.addAttribute("code", id);
        return "inviteuser";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> register(@RequestParam(value = "wxId") String wxId, @RequestParam("password") String password,@RequestParam("mobile") String mobile, @RequestParam(value = "super_invite_code")String superInviteCode) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (Strings.isEmpty(wxId)) {
            setUserResultAttr(userResult, 1001, "微信号不能为空");
        } else {
            UserModel userModel = new UserModel();
            userModel.setWxId(wxId);
            userModel.setPassword(password);
            userModel.setMobile(mobile);
            userModel.setSuperInviteCode(superInviteCode);
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

    @RequestMapping(value = "/loginByRoot", method = RequestMethod.POST)
    public ResponseEntity rootLogin(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username.equals("admin") && password.equals("admin")){
            request.getSession().setAttribute("isLogin", true);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            request.getSession().setAttribute("isLogin", false);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> login(@RequestParam(value = "wxId") String wxId, @RequestParam("password") String password) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (Strings.isEmpty(wxId)) {
            setUserResultAttr(userResult, 1001, "微信号不能为空");
        } else {
            UserModel userModel = new UserModel();
            userModel.setWxId(wxId);
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
                case 1003:
                    setUserResultAttr(userResult, 1002, "填入手机号");
                    setRedis(userResult, wxId);
                    break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    private void setRedis(UserResult userResult, String wxId) {
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(token, wxId, 2L, TimeUnit.DAYS);
        userResult.setToken(token);
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> updatePassword(@RequestParam("wxId") String wxId, @RequestParam("password") String password, @RequestParam("mobile") String mobile) {
        SuccessResult<UserResult> successResult = new SuccessResult<>();
        UserResult userResult = new UserResult();
        if (StringUtils.isEmpty(wxId)) {
            setUserResultAttr(userResult, 1001, "微信账号错误");
        } else {
            UserModel userModel = new UserModel();
            userModel.setWxId(wxId);
            userModel.setPassword(password);
            userModel.setMobile(mobile);
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
                case 1003:
                    setUserResultAttr(userResult, 1001, "输入手机号与绑定手机号不匹配");
                    break;
            }
        }
        setSuccessResult(successResult, userResult);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/update/headimg", method = RequestMethod.POST)
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
    @RequestMapping(value = "/root/getAllUser", method = RequestMethod.POST)
    public ResponseEntity<List<UserModel>> getAllUser() {
        List<UserModel> allUser = userService.getAllUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    /**
     * 管理端 通过搜索找到用户
     * @param wxId
     * @return
     */
    @RequestMapping(value = "/root/getSearchUser", method = RequestMethod.POST)
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

    /**
     * 更新手机号
     */
    @RequestMapping(value = "/mobile", method = RequestMethod.POST)
    public ResponseEntity<UserResult> updateMobile(@RequestParam("wxId")String wxId, @RequestParam("mobile")String mobile) {
        int index = userService.updateMobile(wxId, mobile);
        UserResult userResult = new UserResult();
        if (index >= 0) {
            userResult.setCode(1000);
            userResult.setMsg("绑定成功");
        } else {
            userResult.setCode(1001);
            userResult.setMsg("绑定失败，请联系客服");
        }
        return new ResponseEntity<>(userResult, HttpStatus.OK);
    }


    @RequestMapping(value = "/client/shareurl", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<UserResult>> getShareUrl(@RequestParam("wxId")String wxId) {
        String root = env.getProperty("web.router");
        UserResult userResult = new UserResult();
        userResult.setCode(1000);
        String shareUrl = root + "invitation/" + wxId;
        userResult.setMsg(shareUrl);
        SuccessResult successResult = new SuccessResult();
        successResult.setDate(userResult);
        successResult.setCode(1000);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/root/create/relationship", method = RequestMethod.POST)
    public ResponseEntity createRelationShip(@RequestParam("superUser") String superUser, @RequestParam("subUser") String subUser) {
        boolean relationship = userService.createRelationship(superUser, subUser);
        if (relationship) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

//    @RequestMapping(value = "/client/user/id", method = RequestMethod.POST)
//    public ResponseEntity<Integer> getUserId(@RequestParam("wxId") String wxid) {
//        UserModel userModel = new UserModel();
//        userModel.setWxId(wxid);
//        UserModel exite = userService.isExite(userModel);
//        if (exite != null) {
//            return new ResponseEntity<>(exite.getId(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
//        }
//    }

    @RequestMapping(value = "/client/chat/token", method = RequestMethod.POST)
    public ResponseEntity<TokenResult> getChatToken(@RequestParam("wxId") String wxId) throws Exception {
        if (rongCloud == null) {
            rongCloud = RongCloud.getInstance(env.getProperty("ry.app.key"), env.getProperty("ry.secret"));
            User = rongCloud.user;
        }

        String name = wxId;
        String name1 = "";
        if (name.length() >= 5) {
            name1 += name.substring(0, 2);
            name1 += "**";
            name1 += name.substring(4, name.length());
        } else {
            name1 = name;
        }
        io.rong.models.user.UserModel user = new io.rong.models.user.UserModel()
                .setId(wxId)
                .setName(name1)
                .setPortrait(env.getProperty("ry.header.img"));
        TokenResult result = User.register(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

package com.shfound.showproduct.service;

import com.shfound.showproduct.controller.result.CustomerResult;
import com.shfound.showproduct.model.ProductInfoModel;
import com.shfound.showproduct.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 用户注册
     *
     * @param userModel 1000 注册成功  1001 注册失败  1002  账户已存在
     * @return
     */
    public int register(UserModel userModel) {

        UserModel user = isExite(userModel);
        if (user == null) {
            int index = insertUser(userModel);
            if (index >= 0) {
                return 1000;
            } else {
                return 1001;
            }
        } else {
            return 1002;
        }
    }

    private int insertUser(UserModel userModel) {
        String sql = "INSERT INTO user (mobile, password, head_img, wx_id, super_invite_code) VALUE (?, ? , ? , ?, ?)";
        int update = jdbcTemplate.update(sql, new Object[]{userModel.getMobile(), userModel.getPassword(),
                StringUtils.isEmpty(userModel.getHeadImg()) ? "" : userModel.getHeadImg(), userModel.getWxId(), userModel.getSuperInviteCode()});
        return update;
    }

    private UserModel isExite(UserModel userModel) {
        String sql = "SELECT * FROM user WHERE wx_id = ?";
        RowMapper<UserModel> rowMapper = new BeanPropertyRowMapper<>(UserModel.class);
        UserModel user;
        try {
            user = jdbcTemplate.queryForObject(sql, rowMapper, userModel.getWxId());
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    /**
     * 登陆
     *
     * @param userModel 1000 登陆成功 1001 用户不存在 1002 密码错误 1003 填入手机号
     * @return
     */
    public int login(UserModel userModel) {
        UserModel user = isExite(userModel);
        if (user == null) {
            return 1001;
        } else {
            if (userModel.getPassword().equals(user.getPassword()) && !StringUtils.isEmpty(user.getMobile())) {
                return 1000;
            } else if (userModel.getPassword().equals(user.getPassword()) && StringUtils.isEmpty(user.getMobile())){
                return 1003;
            } else {
                return 1002;
            }
        }
    }

    /**
     * 修改用户密码
     *
     * @param userModel 1000 修改成功 1001 用户不存在  1002修改失败 1003手机号不匹配
     * @return
     */
    public int updateUserPassword(UserModel userModel) {
        UserModel user = isExite(userModel);
        if (user == null) {
            return 1001;
        } else {
            if (!user.getMobile().equals(userModel.getMobile())) {
                return 1003;
            }
            int index = updatePassword(userModel);
            if (index > 0) {
                return 1000;
            } else {
                return 1002;
            }
        }
    }

    private int updatePassword(UserModel userModel) {
        String sql = "UPDATE user SET password = ? WHERE wx_id = ?";
        int update = jdbcTemplate.update(sql, new Object[]{userModel.getPassword(), userModel.getWxId()});
        return update;
    }

    /**
     * 修改用户头像
     *
     * @param userModel 1000 修改成功， 1001 用户不存在 1002 修改失败
     * @return
     */
    public int updateUserHeadImg(UserModel userModel) {
        UserModel user = isExite(userModel);
        if (user == null) {
            return 1001;
        } else {
            int index = updateHeadImg(userModel);
            if (index >= 0) {
                return 1000;
            }
            return 1002;
        }
    }

    private int updateHeadImg(UserModel userModel) {
        String sql = "UPDATE user SET head_img = ? WHERE mobile = ?";
        int update = jdbcTemplate.update(sql, new Object[]{userModel.getHeadImg(), userModel.getMobile()});
        return update;
    }

    public List<UserModel> getAllUser() {
        String sql = "SELECT * FROM user";
        List<UserModel> userModels = new ArrayList<>();
        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        fullUserInfo(userModels, mapArrayList);
        return userModels;
    }

    private void fullUserInfo(List<UserModel> list, List<Map<String, Object>> maps) {
        if (maps != null && maps.size() > 0) {
            for (int i = 0; i < maps.size(); i++) {
                UserModel userModel = new UserModel();
                userModel.setId((Integer) maps.get(i).get("id"));
//                userModel.setHeadImg(maps.get(i).get("headimg"));
                userModel.setMobile((String) maps.get(i).get("mobile"));
                userModel.setWxId((String) maps.get(i).get("wx_id"));
                userModel.setSuperInviteCode((String) maps.get(i).get("super_invite_code"));
                list.add(userModel);
            }
        }
    }

    public UserModel getOneUser(String wxId) {
        UserModel userModel = new UserModel();
        userModel.setWxId(wxId);
        return isExite(userModel);
    }

    public CustomerResult getCustomerResult(String wxId) {
        UserModel userModel = new UserModel();
        userModel.setWxId(wxId);
        UserModel user = isExite(userModel);
        if (user == null) {
            return null;
        }
        List<UserModel> firstLevel = getInvitionUser(wxId);
        List<UserModel> secondLevel = new ArrayList<>();
        for (int i = 0; i < firstLevel.size(); i++) {
            secondLevel.addAll(getInvitionUser(firstLevel.get(i).getWxId()));
        }
        CustomerResult customerResult = new CustomerResult();
        customerResult.setFirstLevel(firstLevel);
        customerResult.setSecondLevel(secondLevel);
        customerResult.setWxId(wxId);
        return customerResult;
    }

    private List<UserModel> getInvitionUser(String wxId) {
        String sql = "SELECT * FROM user WHERE super_invite_code = '" + wxId + "'";
        List<UserModel> list = new ArrayList<>();
        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                UserModel userModel = new UserModel();
                userModel.setId((Integer) mapArrayList.get(i).get("id"));
                userModel.setHeadImg((String) mapArrayList.get(i).get("head_img"));
                userModel.setWxId((String) mapArrayList.get(i).get("wx_id"));
                userModel.setSuperInviteCode((String) mapArrayList.get(i).get("super_invite_code"));
                list.add(userModel);
            }
        }
        return list;
    }

    public int updateMobile(String wxId, String mobile) {
        UserModel userModel = new UserModel();
        userModel.setWxId(wxId);
        UserModel exite = isExite(userModel);
        if (exite != null) {
            String sql = "UPDATE user SET mobile = ? WHERE wx_id = ?";
            int update = jdbcTemplate.update(sql, new Object[]{mobile, wxId});
            return update;
        }
        return -1;
    }
}

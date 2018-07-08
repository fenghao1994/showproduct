package com.shfound.showproduct.service;

import com.shfound.showproduct.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 用户注册
     * @param userModel  1000 注册成功  1001 注册失败  1002  账户已存在
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
        String sql = "INSERT INTO user (mobile, password, head_img) VALUE (?, ? , ?)";
        int update = jdbcTemplate.update(sql, new Object[]{userModel.getMobile(), userModel.getPassword(),
                StringUtils.isEmpty(userModel.getHeadImg()) ? "" : userModel.getHeadImg()});
        return update;
    }

    private UserModel isExite(UserModel userModel) {
        String sql = "SELECT * FROM user WHERE mobile = ?";
        RowMapper<UserModel> rowMapper = new BeanPropertyRowMapper<>(UserModel.class);
        UserModel user;
        try {
            user = jdbcTemplate.queryForObject(sql, rowMapper, userModel);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    /**
     * 登陆
     * @param userModel 1000 登陆成功 1001 用户不存在 1002 密码错误
     * @return
     */
    public int login(UserModel userModel) {
        UserModel user = isExite(userModel);
        if (user == null) {
            return 1001;
        } else {
            if (userModel.getPassword().equals(user.getPassword())) {
                return 1000;
            } else {
                return 1002;
            }
        }
    }

    /**
     * 修改用户密码
     * @param userModel 1000 修改成功 1001 用户不存在  1002修改失败
     * @return
     */
    public int updateUserPassword(UserModel userModel) {
        UserModel user = isExite(userModel);
        if (user == null) {
            return 1001;
        } else {
            int index = updatePassword(userModel);
            if (index >= 0) {
                return 1000;
            } else {
                return 1002;
            }
        }
    }

    private int updatePassword(UserModel userModel) {
        String sql = "UPDATE user SET password = ? WHERE mobile = ?";
        int update = jdbcTemplate.update(sql, new Object[]{userModel.getPassword(), userModel.getMobile()});
        return update;
    }

    /**
     * 修改用户头像
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

}

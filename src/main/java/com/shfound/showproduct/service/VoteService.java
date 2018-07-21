package com.shfound.showproduct.service;

import com.shfound.showproduct.controller.result.VoteResult;
import com.shfound.showproduct.model.ProductInfoModel;
import com.shfound.showproduct.model.UserModel;
import com.shfound.showproduct.model.VoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class VoteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createVote(VoteModel voteModel) {
        String sql = "INSERT INTO vote (user_id, prod_id, prod_limit) VALUE(?, ?, ?)";
        int update = jdbcTemplate.update(sql, new Object[]{voteModel.getUserId(), voteModel.getProdId(), voteModel.getProdLimit()});

        return update >= 0 ? true : false;
    }

    public VoteResult getAllVote(int prodId) {
        VoteResult voteResult = new VoteResult();
        String sql = "SELECT * FROM vote WHERE prod_id = " + prodId;
        List<VoteModel> voteModelList = new ArrayList<>();
        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                VoteModel voteModel = new VoteModel();
                voteModel.setId((Integer) mapArrayList.get(i).get("id"));
                voteModel.setProdLimit((Double) mapArrayList.get(i).get("prod_limit"));
                voteModel.setProdId((Integer) mapArrayList.get(i).get("prod_id"));
                voteModel.setUserId((Integer) mapArrayList.get(i).get("user_id"));
                voteModel.setCreateTime((Date) mapArrayList.get(i).get("create_time"));
                voteModel.setEditTime((Date) mapArrayList.get(i).get("end_time"));
                voteModelList.add(voteModel);

            }
        }

        String sql1 = "SELECT * FROM product_info WHERE id = ?";
        RowMapper<ProductInfoModel> rowMapper = new BeanPropertyRowMapper<>(ProductInfoModel.class);
        ProductInfoModel productInfoModel;
        try {
            productInfoModel = jdbcTemplate.queryForObject(sql1, rowMapper, prodId);
        } catch (Exception e) {
            productInfoModel = null;
        }
        if (productInfoModel != null && productInfoModel.getProName() != null && !productInfoModel.getProName().isEmpty())
            voteResult.setProdName(productInfoModel.getProName());
        List<String> mobiles = new ArrayList<>();
        List<Double> limits = new ArrayList<>();
        double amount = 0;
        for (VoteModel voteModel : voteModelList) {
            String sql2 = "SELECT * FROM user WHERE id = ?";
            RowMapper<UserModel> rowMapper1 = new BeanPropertyRowMapper<>(UserModel.class);
            UserModel userModel = jdbcTemplate.queryForObject(sql2, rowMapper1, voteModel.getUserId());
            mobiles.add(userModel.getMobile());
            limits.add(voteModel.getProdLimit());
            amount += voteModel.getProdLimit();
        }
        voteResult.setUserLimits(limits);
        voteResult.setUserMobiles(mobiles);
        voteResult.setAmount(amount);
        return voteResult;
    }
}

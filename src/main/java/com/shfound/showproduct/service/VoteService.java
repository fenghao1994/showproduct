package com.shfound.showproduct.service;

import com.shfound.showproduct.controller.result.VoteResult;
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
        String sql = "INSERT INTO vote SET(user_id, prod_id, limit) VALUE(?, ?, ?)";
        int update = jdbcTemplate.update(sql, new Object[]{voteModel.getUserId(), voteModel.getProdId(), voteModel.getLimit()});
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
                voteModel.setLimit((Double) mapArrayList.get(i).get("limit"));
                voteModel.setProdId((Integer) mapArrayList.get(i).get("prod_id"));
                voteModel.setUserId((Integer) mapArrayList.get(i).get("user_id"));
                voteModel.setCreateTime((Date) mapArrayList.get(i).get("create_time"));
                voteModel.setEditTime((Date) mapArrayList.get(i).get("end_time"));
                voteModelList.add(voteModel);

            }
        }

        String sql1 = "SELECT prod_name FROM productInfo WHERE id = ?";
        RowMapper<String> rowMapper = new BeanPropertyRowMapper<>(String.class);
        String proName;
        try {
            proName = jdbcTemplate.queryForObject(sql1, rowMapper, prodId);
        } catch (Exception e) {
            proName = null;
        }
        voteResult.setProdName(proName);
        List<String> mobiles = new ArrayList<>();
        List<Double> limits = new ArrayList<>();
        double amount = 0;
        for (VoteModel voteModel: voteModelList) {
            String sql2 = "SELECT mobile FROM user WHERE id = ?";
            RowMapper<String> rowMapper1 = new BeanPropertyRowMapper<>(String.class);
            String mobile = jdbcTemplate.queryForObject(sql2, rowMapper1, voteModel.getUserId());
            mobiles.add(mobile);
            limits.add(voteModel.getLimit());
            amount += voteModel.getLimit();
        }
        voteResult.setUserLimits(limits);
        voteResult.setUserMobiles(mobiles);
        voteResult.setAmount(amount);
        return voteResult;
    }
}

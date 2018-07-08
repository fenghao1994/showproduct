package com.shfound.showproduct.service;

import com.shfound.showproduct.model.VoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VoteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createVote(VoteModel voteModel) {
        String sql = "INSERT INTO vote SET(user_id, prod_id, limit) VALUE(?, ?, ?)";
        int update = jdbcTemplate.update(sql, new Object[]{voteModel.getUserId(), voteModel.getProdId(), voteModel.getLimit()});
        return update >= 0 ? true : false;
    }

//    public boolean getAllVote
}

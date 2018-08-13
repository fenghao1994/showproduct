package com.shfound.showproduct.service;

import com.shfound.showproduct.controller.result.VoteClientResult;
import com.shfound.showproduct.controller.result.VoteResult;
import com.shfound.showproduct.model.VoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class VoteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createVote(VoteModel voteModel) {
        boolean flag = checkVoteWithwxIDAndProdId(voteModel);
        if (flag) {
            String sql = "INSERT INTO vote (wx_id, prod_id, prod_limit) VALUE(?, ?, ?)";
            int update = jdbcTemplate.update(sql, new Object[]{voteModel.getWxId(), voteModel.getProdId(), voteModel.getProdLimit()});

            return update > 0 ? true : false;
        } else {
            String sql = "UPDATE vote SET prod_limit = ? WHERE wx_id = ?  AND prod_id = ?";
            int update = jdbcTemplate.update(sql, new Object[]{voteModel.getProdLimit(), voteModel.getWxId(), voteModel.getProdId()});
            return update > 0 ? true : false;
        }
    }

    private boolean checkVoteWithwxIDAndProdId(VoteModel model) {
        String sql = "SELECT * FROM vote WHERE wx_id = '" + model.getWxId() + "' AND prod_id = " + model.getProdId();
        List<VoteModel> voteModelList = new ArrayList<>();
        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                VoteModel voteModel = new VoteModel();
                voteModel.setId((Integer) mapArrayList.get(i).get("id"));
                voteModel.setProdLimit((int) mapArrayList.get(i).get("prod_limit"));
                voteModel.setProdId((Integer) mapArrayList.get(i).get("prod_id"));
                voteModel.setWxId((String) mapArrayList.get(i).get("wx_id"));
//                voteModel.setCreateTime((long) mapArrayList.get(i).get("create_time"));
//                voteModel.setEditTime((long) mapArrayList.get(i).get("end_time"));
                voteModelList.add(voteModel);
            }
        }
        return voteModelList.isEmpty();
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
                voteModel.setProdLimit((int) mapArrayList.get(i).get("prod_limit"));
                voteModel.setProdId((Integer) mapArrayList.get(i).get("prod_id"));
                voteModel.setWxId((String) mapArrayList.get(i).get("wx_id"));
//                voteModel.setCreateTime((long) mapArrayList.get(i).get("create_time"));
//                voteModel.setEditTime((long) mapArrayList.get(i).get("end_time"));
                voteModelList.add(voteModel);
            }
        }
        int amount = 0;
        for (VoteModel voteModel : voteModelList) {
            amount += voteModel.getProdLimit();
        }
        voteResult.setVoteModels(voteModelList);
        voteResult.setAmount(amount);
        return voteResult;
    }

    /**
     * 获取用户的预约项目
     * @param wxId
     * @return
     */
    public List<VoteClientResult> getClientVote(String wxId) {
        List<VoteClientResult> voteClientResults = new ArrayList<>();
        String sql = "SELECT * FROM vote WHERE wx_id = '" + wxId + "'";
        List<VoteModel> voteModelList = new ArrayList<>();
        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                VoteModel voteModel = new VoteModel();
                voteModel.setId((Integer) mapArrayList.get(i).get("id"));
                voteModel.setProdLimit((int) mapArrayList.get(i).get("prod_limit"));
                voteModel.setProdId((Integer) mapArrayList.get(i).get("prod_id"));
                voteModel.setWxId((String) mapArrayList.get(i).get("wx_id"));
//                voteModel.setCreateTime((long) mapArrayList.get(i).get("create_time"));
//                voteModel.setEditTime((long) mapArrayList.get(i).get("end_time"));
                voteModelList.add(voteModel);
            }
        }

        for (int i = 0; i < voteModelList.size(); i++) {
            String sql1 = "SELECT pro_name From product_info WHERE id = " + voteModelList.get(i).getProdId();
            String prodName = jdbcTemplate.queryForObject(sql1, String.class);
            VoteClientResult clientResult = new VoteClientResult();
            clientResult.setProdLimit(voteModelList.get(i).getProdLimit());
            clientResult.setProdName(prodName);
            voteClientResults.add(clientResult);
        }
        Collections.reverse(voteClientResults);
        return voteClientResults;
    }
}

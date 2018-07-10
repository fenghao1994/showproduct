package com.shfound.showproduct.service;

import com.shfound.showproduct.model.AdvertisementModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AdvertisementService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createAdvert(AdvertisementModel advertisementModel) {
        String sql = "INSERT INTO advertisement (img_url, jump_page, is_show, marks, is_delete) VALUES (? ,? ,?, ?, ?)";
        int index = jdbcTemplate.update(sql, new Object[]{advertisementModel.getImgUrl(), advertisementModel.getJumpPage(),
                advertisementModel.isShow(), advertisementModel.getMarks(), advertisementModel.isDelete()});
        if (index >= 0) {
            return true;
        }
        return false;
    }

    public boolean updateAdvert(AdvertisementModel advertisementModel) {
        String sql = "UPDATE advertisement SET img_url = ?, jump_page = ?, is_show = ?, marks = ?, is_delete = ? " +
                "WHERE id = ?";
        int index = jdbcTemplate.update(sql, new Object[]{advertisementModel.getImgUrl(), advertisementModel.getJumpPage(),
                advertisementModel.isShow(), advertisementModel.getMarks(), advertisementModel.isDelete(), advertisementModel.getId()});
        if (index >= 0) {
            return true;
        }
        return false;
    }

    public List<AdvertisementModel> getAllAdvert() {
        String sql = "SELECT * FROM advertisement";
        List<AdvertisementModel> advertisementModels = new ArrayList<>();

        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        findResult(advertisementModels, mapArrayList);
        return advertisementModels;
    }

    public List<AdvertisementModel> getAllClientAdvert() {
        String sql = "SELECT * FROM advertisement WHERE is_delete = FALSE";
        List<AdvertisementModel> advertisementModels = new ArrayList<>();

        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        findResult(advertisementModels, mapArrayList);
        return advertisementModels;
    }

    private void findResult(List<AdvertisementModel> advertisementModels, List<Map<String, Object>> mapArrayList) {
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                AdvertisementModel advertisementModel = new AdvertisementModel();
                advertisementModel.setCreateDate((Date) mapArrayList.get(i).get("create_date"));
                advertisementModel.setDelete((Boolean) mapArrayList.get(i).get("is_delete"));
                advertisementModel.setEditDate((Date) mapArrayList.get(i).get("edit_date"));
                advertisementModel.setId((Integer) mapArrayList.get(i).get("id"));
                advertisementModel.setImgUrl((String) mapArrayList.get(i).get("img_url"));
                advertisementModel.setJumpPage((String) mapArrayList.get(i).get("jump_page"));
                advertisementModel.setMarks((String) mapArrayList.get(i).get("marks"));
                advertisementModel.setShow((Boolean) mapArrayList.get(i).get("is_show"));
                advertisementModels.add(advertisementModel);
            }
        }
    }
}

package com.shfound.showproduct.service;

import com.shfound.showproduct.model.ProductInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ProductService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean createPro(ProductInfoModel productInfoModel) {
        String sql = "INSERT INTO product_info (pro_name, pro_sim_desc, pro_grade," +
                "is_hot, start_time, end_time, icon_url, pro_img_url, pro_label, pro_type," +
                "marks, pro_recommend, pro_real_limit, pro_mock_limit, collection_coin_address," +
                "is_stop_collection, is_delete) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
        int index = jdbcTemplate.update(sql, new Object[]{productInfoModel.getProName(), productInfoModel.getProSimDesc(),
        productInfoModel.getProGrade(), productInfoModel.isHot(), productInfoModel.getStartTime(), productInfoModel.getEndTime(),
        productInfoModel.getIconUrl(), productInfoModel.getProImgUrl(), productInfoModel.getProLabel(), productInfoModel.getProType(),
        productInfoModel.getMarks(), productInfoModel.getProRecommend(), productInfoModel.getProRealLimit(), productInfoModel.getProMockLimit(),
        productInfoModel.getCollectionCoinAddress(), productInfoModel.isStopCollection(), productInfoModel.isDelete()});
        if (index >= 0) {
            return true;
        }
        return false;
    }

    public boolean updatePro(ProductInfoModel productInfoModel) {
        String sql = "UPDATE product_info SET pro_name = ?, pro_sim_desc = ?, pro_grade = ?," +
                "is_hot = ?, start_time = ?, end_time = ?, icon_url = ?, pro_img_url = ?, pro_label = ?, pro_type = ?," +
                "marks = ?, pro_recommend = ?, pro_real_limit = ?, pro_mock_limit = ?, collection_coin_address = ?," +
                "is_stop_collection = ?, is_delete = ? WHERE id = ?";
        int index = jdbcTemplate.update(sql, new Object[]{productInfoModel.getProName(), productInfoModel.getProSimDesc(),
                productInfoModel.getProGrade(), productInfoModel.isHot(), productInfoModel.getStartTime(), productInfoModel.getEndTime(),
                productInfoModel.getIconUrl(), productInfoModel.getProImgUrl(), productInfoModel.getProLabel(), productInfoModel.getProType(),
                productInfoModel.getMarks(), productInfoModel.getProRecommend(), productInfoModel.getProRealLimit(), productInfoModel.getProMockLimit(),
                productInfoModel.getCollectionCoinAddress(), productInfoModel.isStopCollection(), productInfoModel.isDelete(), productInfoModel.getId()});
        if (index >= 0) {
            return true;
        }
        return false;
    }


    public List<ProductInfoModel> getProducts() {
        String sql = "SELECT * FROM product_info";
        List<ProductInfoModel> productInfoModels = new ArrayList<>();

        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        findResult(productInfoModels, mapArrayList);
        return productInfoModels;
    }

    public List<ProductInfoModel> getClientProducts() {
        String sql = "SELECT * FROM product_info WHERE is_delete = FALSE ";
        List<ProductInfoModel> productInfoModels = new ArrayList<>();

        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        findResult(productInfoModels, mapArrayList);
        return productInfoModels;
    }

    public List<ProductInfoModel> getSearchProducts(String searchName) {
        String sql = "SELECT * FROM product_info WHERE pro_name LIKE '%" + searchName + "%'";
        List<ProductInfoModel> productInfoModels = new ArrayList<>();

        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        findResult(productInfoModels, mapArrayList);
        return productInfoModels;
    }

    public List<ProductInfoModel> getClientSearchProducts(String searchName) {
        String sql = "SELECT * FROM product_info WHERE is_delete = FALSE AND pro_name LIKE + '%" + searchName + "%'";
        List<ProductInfoModel> productInfoModels = new ArrayList<>();

        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        findResult(productInfoModels, mapArrayList);
        return productInfoModels;
    }

    private void findResult(List<ProductInfoModel> productInfoModels, List<Map<String, Object>> mapArrayList) {
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                ProductInfoModel productInfoModel = new ProductInfoModel();
                productInfoModel.setId((Integer) mapArrayList.get(i).get("id"));
                productInfoModel.setCollectionCoinAddress((String) mapArrayList.get(i).get("collection_coin_address"));
                productInfoModel.setCreateTime((Date) mapArrayList.get(i).get("create_time"));
                productInfoModel.setDelete((Boolean) mapArrayList.get(i).get("is_delete"));
                productInfoModel.setEditTime((Date) mapArrayList.get(i).get("edit_time"));
                productInfoModel.setEndTime((Long) mapArrayList.get(i).get("end_time"));
                productInfoModel.setHot((Boolean) mapArrayList.get(i).get("is_hot"));
                productInfoModel.setIconUrl((String) mapArrayList.get(i).get("icon_url"));
                productInfoModel.setMarks((String) mapArrayList.get(i).get("marks"));
                productInfoModel.setProGrade((String) mapArrayList.get(i).get("pro_grade"));
                productInfoModel.setProImgUrl((String) mapArrayList.get(i).get("pro_img_url"));
                productInfoModel.setProLabel((String) mapArrayList.get(i).get("pro_label"));
                productInfoModel.setProMockLimit((String) mapArrayList.get(i).get("pro_mock_limit"));
                productInfoModel.setProName((String) mapArrayList.get(i).get("pro_name"));
                productInfoModel.setProRealLimit((String) mapArrayList.get(i).get("pro_real_limit"));
                productInfoModel.setProRecommend((String) mapArrayList.get(i).get("pro_recommend"));
                productInfoModel.setProSimDesc((String) mapArrayList.get(i).get("pro_sim_desc"));
                productInfoModel.setProType((Integer) mapArrayList.get(i).get("pro_type"));
                productInfoModel.setStopCollection((Boolean) mapArrayList.get(i).get("is_stop_collection"));
                productInfoModel.setStartTime((Long) mapArrayList.get(i).get("start_time"));
                productInfoModels.add(productInfoModel);
            }
        }
    }
}

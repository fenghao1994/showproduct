package com.shfound.showproduct.service;

import com.shfound.showproduct.model.InviteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InviteService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @Autowired
    private Environment env;

    //1000 提交成功 1001 提交失败 邀请人可能已被其他人邀请过了
    public int createInvite(InviteModel inviteModel) {
        boolean relationship = userService.createRelationship(inviteModel.getSuperUser(), inviteModel.getSubUser());
        if (!relationship) {
            return 1001;
        }
        String sql = "INSERT INTO invite (super_user, sub_user, img_path, statue) VALUE (?, ? , ? , ?)";
        int update = jdbcTemplate.update(sql, new Object[]{inviteModel.getSuperUser(), inviteModel.getSubUser()
                , inviteModel.getImgPath(), inviteModel.getStatus()});
        return update;
    }


    public int updateInvite(InviteModel inviteModel) {
        boolean relationship = userService.createRelationship(inviteModel.getSuperUser(), inviteModel.getSubUser());
        if (!relationship) {
            return 1001;
        }
        String sql = "UPDATE invite SET status = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, new Object[]{inviteModel.getStatus(), inviteModel.getId()});
        return update > 0 ? 1000 : 1001;
    }

    public List<InviteModel> getAllInviteModel() {
        String sql = "SELECT * FROM invite WHERE status = 0 AND is_delete = FALSE";
        List<InviteModel> inviteModels = new ArrayList<>();
        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                InviteModel inviteModel = new InviteModel();
                inviteModel.setId((Integer) mapArrayList.get(i).get("id"));
                inviteModel.setImgPath((String) mapArrayList.get(i).get("img_path"));
                inviteModel.setSubUser((String) mapArrayList.get(i).get("sub_user"));
                inviteModel.setSuperUser((String) mapArrayList.get(i).get("super_user"));
                inviteModels.add(inviteModel);
            }
        }
        return inviteModels;
    }

    public void deleteImg() {

        String sql = "SELECT * FROM invite WHERE status = 1 OR status = 2 AND is_delete = FALSE";
        List<InviteModel> inviteModels = new ArrayList<>();
        List<Map<String, Object>> mapArrayList = jdbcTemplate.queryForList(sql);
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                InviteModel inviteModel = new InviteModel();
                inviteModel.setId((Integer) mapArrayList.get(i).get("id"));
                inviteModel.setImgPath((String) mapArrayList.get(i).get("img_path"));
                inviteModels.add(inviteModel);
            }
        }
        deleteImgFromDisk(inviteModels);
        String sql1 = "UPDATE invite SET is_delete = TRUE WHERE id = ?";
        for (InviteModel inviteModel : inviteModels) {
            int update = jdbcTemplate.update(sql1, new Object[]{inviteModel.getId()});
        }
    }
    private void deleteImgFromDisk(List<InviteModel> list) {
        String rootUrl = env.getProperty("image.upload.path");
        for (InviteModel inviteModel : list) {
            String path = inviteModel.getImgPath().replace(rootUrl, "");
            File file = new File(path);
            file.delete();
        }
    }
}

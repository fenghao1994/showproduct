package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.model.AdvertisementModel;
import com.shfound.showproduct.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/advert")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @RequestMapping(value = "/client/getAllAdvert", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<List<AdvertisementModel>>> getClientAllAdvert() {
        SuccessResult<List<AdvertisementModel>> successResult = new SuccessResult<>();
        List<AdvertisementModel> allAdvert = advertisementService.getAllClientAdvert();
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(allAdvert);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllAdvert", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<List<AdvertisementModel>>> getAllAdvert() {
        SuccessResult<List<AdvertisementModel>> successResult = new SuccessResult<>();
        List<AdvertisementModel> allAdvert = advertisementService.getAllAdvert();
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(allAdvert);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/create/advert", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult> createAdvert(@RequestParam("imgUrl")String imgUrl, @RequestParam(value = "jumpPage", required = false) String jumpPage,
    @RequestParam(value = "isShow", required = false) boolean isShow, @RequestParam(value = "marks", required = false) String marks, @RequestParam(value = "isDelete", required = false) boolean isDelete) {
        SuccessResult successResult = new SuccessResult();
        AdvertisementModel advertisementModel = new AdvertisementModel();
        advertisementModel.setImgUrl(imgUrl);
        advertisementModel.setJumpPage(jumpPage);
        advertisementModel.setShow(isShow);
        advertisementModel.setMarks(marks);
        advertisementModel.setDelete(isDelete);
        boolean isSuccess = advertisementService.createAdvert(advertisementModel);
        if (isSuccess) {
            successResult.setCode(1000);
            successResult.setMessage("创建成功");
        } else {
            successResult.setCode(1001);
            successResult.setMessage("创建失败");
        }
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/advert", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult> updateAdvert(@RequestParam("id")int id, @RequestParam("imgUrl")String imgUrl, @RequestParam(value = "jumpPage", required = false) String jumpPage,
                                                      @RequestParam(value = "isShow", required = false) boolean isShow, @RequestParam(value = "marks", required = false) String marks, @RequestParam(value = "isDelete", required = false) boolean isDelete) {
        SuccessResult successResult = new SuccessResult();
        AdvertisementModel advertisementModel = new AdvertisementModel();
        advertisementModel.setId(id);
        advertisementModel.setImgUrl(imgUrl);
        advertisementModel.setJumpPage(jumpPage);
        advertisementModel.setShow(isShow);
        advertisementModel.setMarks(marks);
        advertisementModel.setDelete(isDelete);
        boolean isSuccess = advertisementService.updateAdvert(advertisementModel);
        if (isSuccess) {
            successResult.setCode(1000);
            successResult.setMessage("创建成功");
        } else {
            successResult.setCode(1001);
            successResult.setMessage("创建失败");
        }
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }
}

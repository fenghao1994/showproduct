package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.model.ProductInfoModel;
import com.shfound.showproduct.service.ProductService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/create/product", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult> createPro(@RequestParam("proName") String proName, @RequestParam("proSimDesc") String proSimDesc,
                                                   @RequestParam(value = "proGrade", required = false) String proGrade, @RequestParam(value = "isHot", required = false, defaultValue = "false") boolean isHot,
                                                   @RequestParam(value = "stratTime", required = false, defaultValue = "0") long startTime, @RequestParam(value = "endTime", required = false, defaultValue = "0") long endTime,
                                                   @RequestParam(value = "iconUrl", required = false) String iconUrl, @RequestParam("proImgUrl") String proImgUrl,
                                                   @RequestParam(value = "proLabel", required = false) String proLabel, @RequestParam(value = "proType", defaultValue = "0") int proType,
                                                   @RequestParam(value = "marks", required = false) String marks, @RequestParam("proRecommend") String proRecommend,
                                                   @RequestParam(value = "proRealLimit", required = false) String proRealLimit, @RequestParam(value = "proMockLimit", required = false) String proMockLimit,
                                                   @RequestParam(value = "collectionCoinAddress", required = false) String collectionCoinAddress, @RequestParam(value = "isStopCollection", required = false, defaultValue = "false") boolean isStopCollection,
                                                   @RequestParam(value = "isDelete", required = false, defaultValue = "false") boolean isDelete) {
        SuccessResult successResult = new SuccessResult();
        ProductInfoModel productInfoModel = new ProductInfoModel();
        productInfoModel.setProName(proName);
        productInfoModel.setProSimDesc(proSimDesc);
        productInfoModel.setProGrade(proGrade);
        productInfoModel.setHot(isHot);
        productInfoModel.setStartTime(startTime);
        productInfoModel.setEndTime(endTime);
        productInfoModel.setIconUrl(iconUrl);
        productInfoModel.setProImgUrl(proImgUrl);
        productInfoModel.setProLabel(proLabel);
        productInfoModel.setProType(proType);
        productInfoModel.setMarks(marks);
        productInfoModel.setProRecommend(proRecommend);
        productInfoModel.setProRealLimit(proRealLimit);
        productInfoModel.setProMockLimit(proMockLimit);
        productInfoModel.setCollectionCoinAddress(collectionCoinAddress);
        productInfoModel.setStopCollection(isStopCollection);
        productInfoModel.setDelete(isDelete);
        boolean isSuccess = productService.createPro(productInfoModel);
        if (isSuccess) {
            successResult.setCode(1000);
            successResult.setMessage("创建成功");
        } else {
            successResult.setCode(1001);
            successResult.setMessage("创建失败");
        }
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/product", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult> updatePro(@RequestParam("id") int id, @RequestParam("proName") String proName, @RequestParam("proSimDesc") String proSimDesc,
                                                   @RequestParam(value = "proGrade", required = false) String proGrade, @RequestParam(value = "isHot", required = false) boolean isHot,
                                                   @RequestParam(value = "stratTime", required = false) long startTime, @RequestParam(value = "endTime", required = false) long endTime,
                                                   @RequestParam(value = "iconUrl", required = false) String iconUrl, @RequestParam("proImgUrl") String proImgUrl,
                                                   @RequestParam(value = "proLabel", required = false) String proLabel, @RequestParam("proType") int proType,
                                                   @RequestParam(value = "marks", required = false) String marks, @RequestParam("proRecommend") String proRecommend,
                                                   @RequestParam(value = "proRealLimit", required = false) String proRealLimit, @RequestParam(value = "proMockLimit", required = false) String proMockLimit,
                                                   @RequestParam(value = "collectionCoinAddress", required = false) String collectionCoinAddress, @RequestParam(value = "isStopCollection", required = false) boolean isStopCollection,
                                                   @RequestParam(value = "isDelete", required = false) boolean isDelete) {
        SuccessResult successResult = new SuccessResult();
        ProductInfoModel productInfoModel = new ProductInfoModel();
        productInfoModel.setId(id);
        productInfoModel.setProName(proName);
        productInfoModel.setProSimDesc(proSimDesc);
        productInfoModel.setProGrade(proGrade);
        productInfoModel.setHot(isHot);
        productInfoModel.setStartTime(startTime);
        productInfoModel.setEndTime(endTime);
        productInfoModel.setIconUrl(iconUrl);
        productInfoModel.setProImgUrl(proImgUrl);
        productInfoModel.setProLabel(proLabel);
        productInfoModel.setProType(proType);
        productInfoModel.setMarks(marks);
        productInfoModel.setProRecommend(proRecommend);
        productInfoModel.setProRealLimit(proRealLimit);
        productInfoModel.setProMockLimit(proMockLimit);
        productInfoModel.setCollectionCoinAddress(collectionCoinAddress);
        productInfoModel.setStopCollection(isStopCollection);
        productInfoModel.setDelete(isDelete);
        boolean isSuccess = productService.updatePro(productInfoModel);
        if (isSuccess) {
            successResult.setCode(1000);
            successResult.setMessage("修改成功");
        } else {
            successResult.setCode(1001);
            successResult.setMessage("修改失败");
        }
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllProduct", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<List<ProductInfoModel>>> getAllProduct() {
        SuccessResult<List<ProductInfoModel>> successResult = new SuccessResult<>();
        List<ProductInfoModel> products = productService.getProducts();
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(products);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/getSearchProduct", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<List<ProductInfoModel>>> getSearchProduct(@RequestParam("search") String search) {
        SuccessResult<List<ProductInfoModel>> successResult = new SuccessResult<>();
        List<ProductInfoModel> products = productService.getSearchProducts(search);
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(products);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/getSearchProduct", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<List<ProductInfoModel>>> getClientSearchProduct(@RequestParam("search") String search) {
        SuccessResult<List<ProductInfoModel>> successResult = new SuccessResult<>();
        List<ProductInfoModel> products = productService.getClientSearchProducts(search);
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(products);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/getAllProduct", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<List<ProductInfoModel>>> getAllClientProduct() {
        SuccessResult<List<ProductInfoModel>> successResult = new SuccessResult<>();
        List<ProductInfoModel> products = productService.getClientProducts();
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(products);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }
}

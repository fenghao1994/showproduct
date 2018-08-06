package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.SimpleProduct;
import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.model.ProductInfoModel;
import com.shfound.showproduct.service.ProductService;
import org.apache.logging.log4j.util.Strings;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.ws.Response;
import java.util.ArrayList;
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
                                                   @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime, @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime,
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
        if (!Strings.isEmpty(startTime)) {
            startTime = startTime.replace("T", " ");
        }
        productInfoModel.setStartTime(startTime);
        if (!Strings.isEmpty(endTime)) {
            endTime = endTime.replace("T", " ");
        }
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
                                                   @RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime,
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
        if (!Strings.isEmpty(startTime)) {
            startTime = startTime.replace("T", " ");
        }
        productInfoModel.setStartTime(startTime);
        if (!Strings.isEmpty(endTime)) {
            endTime = endTime.replace("T", " ");
        }
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

    @RequestMapping(value = "/getAllSimpleProduct", method = RequestMethod.POST)
    public ResponseEntity<List<SimpleProduct>> getAllSimpleProduct() {
//        SuccessResult<List<SimpleProduct>> successResult = new SuccessResult<>();
        List<ProductInfoModel> products = productService.getProducts();
        List<SimpleProduct> list = new ArrayList<>();
        for (ProductInfoModel productInfoModel : products) {
            SimpleProduct simpleProduct = new SimpleProduct();
            simpleProduct.setId(productInfoModel.getId());
            simpleProduct.setName(productInfoModel.getProName());
            list.add(simpleProduct);
        }
//        successResult.setCode(1000);
//        successResult.setMessage("响应成功");
//        successResult.setDate(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
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

    @RequestMapping(value = "/getOneSimpleSearch", method = RequestMethod.POST)
    public ResponseEntity<List<SimpleProduct>> getOneSimProduct(@RequestParam("name") String name) {
        List<ProductInfoModel> products = productService.getSearchProducts(name);
        List<SimpleProduct> list = new ArrayList<>();
        if (products != null && !products.isEmpty()) {
            for (ProductInfoModel productInfoModel : products) {
                SimpleProduct simpleProduct = new SimpleProduct();
                simpleProduct.setId(productInfoModel.getId());
                simpleProduct.setName(productInfoModel.getProName());
                list.add(simpleProduct);
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
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
    public ResponseEntity<SuccessResult<List<ProductInfoModel>>> getAllClientProduct(@RequestParam("type") int type) {
        SuccessResult<List<ProductInfoModel>> successResult = new SuccessResult<>();
        List<ProductInfoModel> products = productService.getClientProducts(type);
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(products);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/editProd", method = RequestMethod.POST)
    public ResponseEntity<ProductInfoModel> editProduct(@RequestParam("id") int id) {
        ProductInfoModel productInfoModel = productService.getOneProdById(id);
        return new ResponseEntity<>(productInfoModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/getProRecommend", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<String>> getProRecommend(@RequestParam("id") int id) {
        SuccessResult<String> successResult = new SuccessResult<>();
        String proRecommend = productService.getProRecommend(id);
        successResult.setCode(1000);
        successResult.setDate(proRecommend);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }
}

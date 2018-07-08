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

import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/create/product", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult> createPro(@ModelAttribute("productInfo")ProductInfoModel productInfoModel) {
        SuccessResult successResult = new SuccessResult();
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
    public ResponseEntity<SuccessResult> updatePro(@ModelAttribute("productInfo") ProductInfoModel productInfoModel) {
        SuccessResult successResult = new SuccessResult();
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

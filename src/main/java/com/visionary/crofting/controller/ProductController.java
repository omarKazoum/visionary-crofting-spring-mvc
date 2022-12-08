package com.visionary.crofting.controller;

import com.visionary.crofting.entity.Product;
import com.visionary.crofting.requests.ProductRequest;
import com.visionary.crofting.response.ApiResponse;
import com.visionary.crofting.service.Impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Product>> saveProduct(@RequestBody ProductRequest productRequest ){
        try {
            ApiResponse<Product> response = productService.save(productRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Product> productApiResponse = new ApiResponse<>() ;
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(productApiResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/product/{reference}")
    public ResponseEntity<ApiResponse<Product>> getProductByREF(@PathVariable String reference ){
        try {
            ApiResponse<Product> response = productService.find(reference);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Product> productApiResponse = new ApiResponse<>() ;
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(productApiResponse, HttpStatus.OK);
        }
    }


    @DeleteMapping("/product/{reference}")
    public ResponseEntity<ApiResponse<Product>> deleteProductById(@PathVariable String reference){
        try {
            ApiResponse<Product> response = productService.delete(reference);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Product> productApiResponse = new ApiResponse<>() ;
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(productApiResponse, HttpStatus.OK);
        }
    }

    @PutMapping("/product/{reference}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable String reference,@RequestBody ProductRequest productRequest){
        try {
            ApiResponse<Product> response = productService.update(reference,productRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Product> productApiResponse = new ApiResponse<>() ;
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(productApiResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/")
    public ModelAndView getProductsList(@RequestParam(value = "page",required = false,defaultValue = "0") int pageIndex,@RequestParam(name = "size", required = false,defaultValue = "5") int size){
      Map<String,Object> model=new HashMap<>();
      Page<Product> page=productService.findAll(PageRequest.of(pageIndex,size));
      model.put("products",page.getContent());
      model.put("page",page);
      return new ModelAndView("products",model);

    }

}

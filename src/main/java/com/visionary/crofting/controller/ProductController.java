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
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping({"/products"})
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

    @GetMapping("/{reference}")
    public ModelAndView getProductByREF(@PathVariable String reference ){
        Optional<Product> optionalProduct=productService.findProductByReference(reference);
        System.out.println("selected reference "+reference);
        if (optionalProduct.isPresent()){
            Map<String,Object> model=new HashMap<>();
            model.put("product",optionalProduct.get());
            return new ModelAndView("product",model);
        }else
            return new ModelAndView(new RedirectView("/error"));
    }

    @PostMapping("/products/{reference}")
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

    @GetMapping({"/",""})
    public ModelAndView getProductsList(@RequestParam(value = "page",required = false,defaultValue = "0") int pageIndex,
                                        @RequestParam(name = "size", required = false,defaultValue = "5") int size,
                                        @RequestParam(name = "word", required = false,defaultValue = "") String word){
      Map<String,Object> model=new HashMap<>();
      Page<Product> page=productService.findAll(PageRequest.of(pageIndex,size),word);
      model.put("products",page.getContent());
      model.put("edit",true);
      model.put("page",page);
      return new ModelAndView("products",model);
    }

}

package com.visionary.crofting.service.Impl;

import com.visionary.crofting.entity.Product;
import com.visionary.crofting.repository.ProductRepository;
import com.visionary.crofting.requests.ProductRequest;
import com.visionary.crofting.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;


    public ApiResponse<Product> save(ProductRequest request) {
        try {
            ApiResponse<Product> productApiResponse = new ApiResponse<>();
            Product product = new Product();

            product.setReference(request.getReference());
            product.setTitle(request.getTitle());
            product.setDescription(request.getDescription());
            product.setInitialPrice(request.getInitialPrice());
            product.setQuantity(request.getQuantity());
            // product.setStock(request.getStock());

            productRepository.save(product);
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.SUCCESS);
            return productApiResponse;
        }catch (Exception e){
            ApiResponse<Product> productApiResponse = new ApiResponse<>();
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return productApiResponse;
        }

    }


    public Optional<Product> findProductByReference(String reference)  {
        Product p=productRepository.findProductByReference(reference);
        return p==null?Optional.empty(): Optional.of(p);
    }

    public Page<Product> findAll(Pageable pageable,String word)  {
        if(word!="" ){
                return productRepository.findAll(hasDescription(word).or(hasTitle(word)),pageable);
        }
        else return productRepository.findAll(pageable);

    }
    private static Specification<Product> hasTitle(String title){
        return (product,criteriaQuery,criteriaBuilder)->
             criteriaBuilder.like(product.get("title"),"%"+title+"%");
    }
    private static Specification<Product> hasDescription(String description){
        return (product,criteriaQuery,criteriaBuilder)->{
            return criteriaBuilder.like(product.get("description"),"%"+description+"%");
        };
    }

    public void delete(String reference)  {
        Optional<Product> optionalProduct=findProductByReference(reference);
        if(optionalProduct.isPresent()){
            productRepository.delete(optionalProduct.get());
        }
    }

    public ApiResponse<Product> update(String reference, ProductRequest Request) {
        /*try {
            ApiResponse<Product> productApiResponse = new ApiResponse<>();
            ApiResponse<Product> productResponse = this.findProductByReference(reference);
            if (!Objects.isNull(productResponse.getData())){
                if ( Request.getTitle() != null) {
                    productResponse.getData().setTitle(Request.getTitle());
                }
                if ( Request.getDescription() != null ) {
                    productResponse.getData().setDescription(Request.getDescription());
                }
                if ( Request.getInitialPrice() != 0.0f) {
                    productResponse.getData().setInitialPrice(Request.getInitialPrice());
                }
                if ( Request.getQuantity() != 0) {
                    productResponse.getData().setQuantity(Request.getQuantity());
                }
                // Need to add stock field
                productRepository.save(productResponse.getData());
                productApiResponse.setResponseCode(ApiResponse.ResponseCode.SUCCESS);
                productApiResponse.setResponseMessage("Product updated");
                productApiResponse.setData(productApiResponse.getData());
                return productApiResponse;
            }
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.NOT_EXIST);
            productApiResponse.setResponseMessage("Product not updated");
            return productApiResponse;
        }catch (Exception e){
            ApiResponse<Product> productApiResponse = new ApiResponse<>();
            productApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return productApiResponse;
        }*/
        return null;
    }

    public boolean validateREF(String reference){
        Pattern refPattern =Pattern.compile("^[a-zA-Z]{3}\\d{3}$");
        Matcher refMatcher = refPattern.matcher(reference);
        if(!refMatcher.matches() || reference.isEmpty() || reference.isBlank() ){
            return false;
        }
        return true;
    }
}

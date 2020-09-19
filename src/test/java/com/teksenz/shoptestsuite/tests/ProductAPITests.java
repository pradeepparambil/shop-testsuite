package com.teksenz.shoptestsuite.tests;

import com.teksenz.shoptestsuite.lib.TestBase;
import com.teksenz.shoptestsuite.models.Product;
import com.teksenz.shoptestsuite.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;


@Slf4j
public class ProductAPITests extends TestBase {

    @Test(description = "Save a product")
    public void validateSavingAProduct(){
        Product product = Product.builder()
                .name("LG 43\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(600F)
                .build();
        ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build()
                .saveNewProduct(product,HttpStatus.SC_CREATED);

    }


    @Test
    public void validateFindingAProduct(){
        Product product = Product.builder()
                .name("LG 43\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(600F)
                .build();


        ProductService productService = ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build();

        productService
                .saveNewProduct(product,HttpStatus.SC_CREATED)
                .findProductById(productService.getProductUuid(),HttpStatus.SC_OK,product);
    }
    @Test
    public void validateUpdatingAProduct(){
        Product product = Product.builder()
                .name("LG 43\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(600F)
                .build();

        Product productToUpdate = Product.builder()
                .name("Samsung 57\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(1600F)
                .build();

        ProductService productService = ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build();

        productService
                .saveNewProduct(product,HttpStatus.SC_CREATED)
                .updateProduct(productService.getProductUuid(),productToUpdate)
                .findProductById(productService.getProductUuid(),HttpStatus.SC_OK,productToUpdate);

    }
    @Test
    public void validateDeleteProductById(){
        Product product = Product.builder()
                .name("LG 43\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(600F)
                .build();


        ProductService productService = ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build();

        productService
                .saveNewProduct(product,HttpStatus.SC_CREATED)
                .deleteProduct(productService.getProductUuid(),HttpStatus.SC_NO_CONTENT)
                .findProductById(productService.getProductUuid(), HttpStatus.SC_NOT_FOUND,null);
    }
    @Test
    public void validateFindAllProducts(){
        Product product = Product.builder()
                .name("LG 43\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(600F)
                .build();


        ProductService productService = ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build();

        productService
                .saveNewProduct(product,HttpStatus.SC_CREATED)
                .setProductIdFor(product)
                .findAllProducts(HttpStatus.SC_OK, List.of(product));
    }
}

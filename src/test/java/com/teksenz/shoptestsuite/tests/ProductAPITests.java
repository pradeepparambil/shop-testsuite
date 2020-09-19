package com.teksenz.shoptestsuite.tests;

import com.teksenz.shoptestsuite.lib.TestBase;
import com.teksenz.shoptestsuite.models.Product;
import com.teksenz.shoptestsuite.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;


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
                .saveNewProduct(product);

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
                .saveNewProduct(product)
                .findProductById(productService.getProductUuid(),product);
    }
}

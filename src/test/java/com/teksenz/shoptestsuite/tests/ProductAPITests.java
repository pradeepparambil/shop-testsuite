package com.teksenz.shoptestsuite.tests;

import com.teksenz.shoptestsuite.lib.TestBase;
import com.teksenz.shoptestsuite.models.Product;
import com.teksenz.shoptestsuite.models.UserInfo;
import com.teksenz.shoptestsuite.services.ProductService;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;


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
        UserInfo userInfo = UserInfo.builder()
                .username("maria")
                .password("maria123")
                .build();

        ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build()
                .saveNewProduct(product,HttpStatus.SC_CREATED,userInfo);

    }

    @Test(description = "Save a product as a user without permission")
    public void validateSavingAProductNoPermission(){
        Product product = Product.builder()
                .name("LG 43\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(600F)
                .build();
        UserInfo userInfo = UserInfo.builder()
                .username("john")
                .password("john123")
                .build();

        ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build()
                .saveNewProduct(product,HttpStatus.SC_FORBIDDEN,userInfo);

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

        UserInfo userInfo = UserInfo.builder()
                .username("maria")
                .password("maria123")
                .build();


        ProductService productService = ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build();


        productService
                .saveNewProduct(product,HttpStatus.SC_CREATED,userInfo)
                .findProductById(productService.getProductUuid(),HttpStatus.SC_OK,product, userInfo);
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
        UserInfo userInfo = UserInfo.builder()
                .username("maria")
                .password("maria123")
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
                .saveNewProduct(product,HttpStatus.SC_CREATED,userInfo)
                .updateProduct(productService.getProductUuid(),productToUpdate, HttpStatus.SC_NO_CONTENT,userInfo)
                .findProductById(productService.getProductUuid(),HttpStatus.SC_OK,productToUpdate,userInfo);
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
        UserInfo userInfo = UserInfo.builder()
                .username("maria")
                .password("maria123")
                .build();


        ProductService productService = ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build();

        productService
                .saveNewProduct(product,HttpStatus.SC_CREATED,userInfo)
                .deleteProduct(productService.getProductUuid(),HttpStatus.SC_NO_CONTENT,userInfo)
                .findProductById(productService.getProductUuid(), HttpStatus.SC_NOT_FOUND,null,userInfo);
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
        UserInfo userInfo = UserInfo.builder()
                .username("maria")
                .password("maria123")
                .build();


        ProductService productService = ProductService
                .builder()
                .requestSpecification(requestSpecification)
                .build();

        productService
                .saveNewProduct(product,HttpStatus.SC_CREATED,userInfo)
                .setProductIdFor(product)
                .findAllProducts(HttpStatus.SC_OK, List.of(product));
    }

    @Test(enabled = false)
    public void debugTest() {
        Product product = Product.builder()
                .name("LG 43\" Full HD LED TV - 43LF5100")
                .description("The LG LF5100 Edge Lit LED TV has a " +
                        "sleek and slim profile with the benefit " +
                        "of bright and clear colour detail and energy efficiency.")
                .price(600F)
                .build();
        given()
//                .log(LogDetail.ALL)
                .baseUri(baseUri)
                .basePath(basePath)
                .contentType(ContentType.JSON)
                .body(product)
                .log().all()
        .when()
                .post("/products")
        .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .assertThat().header("Location",containsString("/api/v1/products/"));

    }
}

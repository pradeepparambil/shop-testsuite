package com.teksenz.shoptestsuite.services;

import com.teksenz.shoptestsuite.lib.ServiceBase;
import com.teksenz.shoptestsuite.models.Product;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.testng.Assert.assertEquals;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductService extends ServiceBase {
    private UUID productUuid;

    @Builder
    public ProductService(RequestSpecification requestSpecification, UUID productUuid) {
        super(requestSpecification);
        this.productUuid = productUuid;
    }

    public ProductService saveNewProduct(Product product) {

        String location = given()
                .spec(requestSpecification)
                .body(product)
                .when()
                .post("/products")
                .then()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .assertThat().header("Location",containsString("/api/v1/products/"))
                .extract().header("Location");
        productUuid = UUID.fromString(location.substring("/api/v1/products/".length()));
        return this;
//        return location.substring("/api/v1/products/".length());

    }
    public ProductService findProductById(UUID uuid, Product product) {
        product.setId(uuid);
        Product actProduct = given().spec(requestSpecification)
                .when()
                .get("/products/"+uuid)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Product.class);
        assertEquals(actProduct,product,"product details doesn't match");
        return this;
    }
}

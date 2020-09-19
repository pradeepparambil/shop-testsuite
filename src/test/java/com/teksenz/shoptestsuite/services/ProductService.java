package com.teksenz.shoptestsuite.services;

import com.teksenz.shoptestsuite.lib.ServiceBase;
import com.teksenz.shoptestsuite.models.Product;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

    public ProductService saveNewProduct(Product product, int expStatusCode) {

        String location = given()
                .spec(requestSpecification)
                .body(product)
                .when()
                .post("/products")
                .then()
                .assertThat().statusCode(expStatusCode)
                .assertThat().header("Location",containsString("/api/v1/products/"))
                .extract().header("Location");
        productUuid = UUID.fromString(location.substring("/api/v1/products/".length()));
        return this;

    }
    public ProductService findProductById(UUID uuid,int expStatusCode, Product expProduct) {
        ExtractableResponse<Response> response = given().spec(requestSpecification)
                .when()
                .get("/products/"+uuid)
                .then()
                .assertThat().statusCode(expStatusCode)
                .extract();
        if(expStatusCode == HttpStatus.SC_OK){
            expProduct.setId(uuid);
            Product actProduct = response.as(Product.class);
            assertEquals(actProduct,expProduct,"product details doesn't match");
        }
        return this;
    }

    public ProductService updateProduct(UUID uuid, Product product) {
        given().spec(requestSpecification)
                .body(product)
                .pathParam("productId",uuid)
        .when()
                .put("/products/{productId}")
                .then()
                .assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
        return this;
    }

    public ProductService deleteProduct(UUID uuid, int expStatusCode) {
        given().spec(requestSpecification)
                .pathParam("productId",uuid)
                .when()
                .delete("/products/{productId}")
                .then()
                .assertThat().statusCode(expStatusCode);
        return this;

    }

    public ProductService findAllProducts(int expStatusCode, List<Product> expProducts) {
        ExtractableResponse<Response> response = given().spec(requestSpecification)
                .when()
                .get("/products")
                .then()
                .assertThat().statusCode(expStatusCode)
                .extract();
        if(expStatusCode == HttpStatus.SC_OK){

            List<Product> actProducts = List.of(response.as(Product[].class));
            expProducts.forEach(product -> assertTrue(actProducts.contains(product),"Product not present in the actual list"));
        }
        return this;

    }

    public ProductService setProductIdFor(Product product) {
        product.setId(productUuid);
        return this;
    }
}

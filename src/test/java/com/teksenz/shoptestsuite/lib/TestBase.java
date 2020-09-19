package com.teksenz.shoptestsuite.lib;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeSuite;

import java.io.PrintStream;

import static io.restassured.RestAssured.config;
@Slf4j
public class TestBase {
    protected final String baseUri = "http://localhost:8080";
    protected final String basePath = "/api/v1";
    protected RequestSpecification requestSpecification;
    @BeforeSuite
    public void beforeSuite(){
//        PrintStream logStream = new PrintStream(log);
//        LogConfig logConfig = RestAssuredConfig.config().getLogConfig();
//        logConfig.defaultStream(logStream);
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
//                .setConfig(new RestAssuredConfig().logConfig(logConfig))
                .build();

    }


}

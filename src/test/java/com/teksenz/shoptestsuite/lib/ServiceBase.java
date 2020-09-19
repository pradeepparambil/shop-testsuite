package com.teksenz.shoptestsuite.lib;

import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceBase {
    protected RequestSpecification requestSpecification;
}

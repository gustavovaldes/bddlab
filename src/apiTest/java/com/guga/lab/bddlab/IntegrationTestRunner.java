package com.guga.lab.bddlab;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "junit:build/reports/integration/integrationTestResults4.xml"},
        glue = {"com.guga.lab.bddlab"},
        tags = "~@NotImplemented"
)
public class IntegrationTestRunner {
}

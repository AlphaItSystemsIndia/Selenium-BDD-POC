package com.amazon.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:reports/cucumber-tests.html"},
        features = "src/test/resources/features",
        glue = "com.amazon.test.stepDefinitions"
)
public class TestRunner {
}

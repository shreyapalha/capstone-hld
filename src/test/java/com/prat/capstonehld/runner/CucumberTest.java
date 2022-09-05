package com.prat.capstonehld.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "",
        features = "src/test/resources/features",
        plugin={
                "pretty",
                "html:../target/cucumber/capstonehld.html",
                "json:../target/cucumber/capstonehld.json"
        }
)
public class CucumberTest {
}
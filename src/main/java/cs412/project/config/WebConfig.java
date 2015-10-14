package cs412.project.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Tom on 14/10/2015.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cs412.project")
public class WebConfig {


}

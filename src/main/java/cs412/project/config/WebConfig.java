package cs412.project.config;

import cs412.project.spelling.SpellChecker;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.BritishEnglish;
import org.springframework.context.annotation.Bean;
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


	@Bean
	public BritishEnglish britishEnglish(){
		return new BritishEnglish();
	}

	@Bean
	public AmericanEnglish americanEnglish(){ return new AmericanEnglish(); }

	@Bean
	public SpellChecker spellChecker(){
		return new SpellChecker();
	}

}

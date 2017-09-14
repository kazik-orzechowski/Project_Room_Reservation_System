package pl.lps.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfiguration {

	    @Bean
	    public LocaleResolver localeResolver() {
	        SessionLocaleResolver slr = new SessionLocaleResolver();
	        slr.setDefaultLocale(new Locale("pl"));
	        return slr;
	}
}

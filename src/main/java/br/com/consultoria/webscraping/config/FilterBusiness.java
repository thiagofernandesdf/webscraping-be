package br.com.consultoria.webscraping.config;

import br.com.consultoria.webscraping.business.ComboBusiness;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;

@Configuration
public class FilterBusiness {

    @Bean
    @ApplicationScope
    public ComboBusiness applicationScopedBean() throws IOException {
        return new ComboBusiness();
    }

}

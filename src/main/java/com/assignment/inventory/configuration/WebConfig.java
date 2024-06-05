package com.assignment.inventory.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  /**
   * Configures CORS mappings for the application.
   *
   * @param registry the CorsRegistry to add mappings to
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // Allow CORS requests from any origin, with any method and any header
    registry.addMapping("/**")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowedOrigins("*");
  }

  /**
   * Configures content negotiation settings.
   *
   * @param configurer the ContentNegotiationConfigurer to configure
   */
  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    // Set the default content type to JSON
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }
}

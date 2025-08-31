package cz.ondra.gamehub.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls();

        return gsonBuilder.create();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        converters.add(gsonHttpMessageConverter);
    }
}


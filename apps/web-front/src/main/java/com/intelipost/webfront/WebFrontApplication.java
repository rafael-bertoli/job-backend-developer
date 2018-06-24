package com.intelipost.webfront;

import com.github.caryyu.spring.embedded.redisserver.RedisServerConfiguration;
import com.intelipost.webfront.session.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableSpringHttpSession
@SpringBootApplication
public class WebFrontApplication implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;
    
    public static void main(String[] args) {
        SpringApplication.run(WebFrontApplication.class, args);
    }
    
    /**
     * Bean para inicialização do servidor redis embedded.
     * <p>
     * Os seguintes atributos podem ser custumizados:
     * <p>
     * spring.redis.embedded= true/false (Habilita serviço redis embedded ou
     * não)
     * spring.redis.port=22001 (Porta que sera inicializado o serviço)
     * </p> 
     * @return configuração do servidor redis embedded
     */
    @Bean
    public RedisServerConfiguration redisServerConfiguration() {
        return new RedisServerConfiguration();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //Handler para verificar authenticação no serviço
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/user");
    }
}

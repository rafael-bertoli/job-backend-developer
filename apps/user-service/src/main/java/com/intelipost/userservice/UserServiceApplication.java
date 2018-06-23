package com.intelipost.userservice;

import com.github.caryyu.spring.embedded.redisserver.RedisServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableCaching
@SpringBootApplication
public class UserServiceApplication implements WebMvcConfigurer {

    @Autowired
    private BaseInterceptor baseInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
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
        registry.addInterceptor(baseInterceptor).addPathPatterns("/users/*");
    }
}

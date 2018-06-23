package com.intelipost.userservice;

import com.github.caryyu.spring.embedded.redisserver.RedisServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class UserServiceApplication {

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
}

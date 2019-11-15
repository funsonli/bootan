package com.funsonli.bootan;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@SpringBootApplication
@EnableJpaAuditing
@MapperScan("com.funsonli.bootan.module.*.mapper")
@EntityScan("com.funsonli.bootan.module.*.entity")
@EnableAsync
@EnableScheduling
@EnableAdminServer
public class BootanApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootanApplication.class, args);
    }

}

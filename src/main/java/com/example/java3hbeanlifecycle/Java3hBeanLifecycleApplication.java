package com.example.java3hbeanlifecycle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class Java3hBeanLifecycleApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Java3hBeanLifecycleApplication.class, args);
//        Thread.sleep(5000);
//        run.close();
    }
}

@Component
class Runner implements CommandLineRunner {

    @Autowired
    private Demo demo;

    @Autowired
    private ApplicationContext applicationContext;

    //SpEL - Spring Expression Language
    @Value("${config.url}")
    private String configUrl;

    @Value("${config.isLocal}")
    private Boolean isLocal;

    @Value("${config.username}")
    private String username;

    @Value("${config.password}")
    private Integer password;

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public void run(String... args) throws Exception {

//        for (int i = 0; i < 10; i++) {
//            demo.demoMethod();
//        }

        System.out.printf("URL: %s\n", configProperties.getUrl());
        System.out.printf("username: %s\n", configProperties.getUsername());
        System.out.printf("password: %s\n", configProperties.getPassword());
        System.out.printf("isLocal: %s\n", configProperties.getIsLocal());

//        ((ConfigurableApplicationContext)applicationContext).close();
    }
}

@Configuration
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ConfigProperties {

    private String url;
    private String username;
    private Integer password;
    private Boolean isLocal;
}


@Component
class Demo {

    public Demo() {
        System.out.println("constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy");
    }

    public void demoMethod() {
        System.out.println("demoMethod");
    }
}

class A {
    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("on close");
                while (true);
            }
        });
        System.out.println("starting working");
        Thread.sleep(5000);
        System.out.println("closing application");
    }
}
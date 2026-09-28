package com.exploration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1, bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class MyApplicationTest {

    private final ApplicationContext context;

    public MyApplicationTest(ApplicationContext context) {
        this.context = context;
    }

    private MyApplication myApp;

    @Test
    public void testHome() {
        myApp = context.getBean(MyApplication.class);
        myApp.home();
    }
}

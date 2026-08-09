package com.exploration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
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

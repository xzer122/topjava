package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "John Smith", "js@mail.ru", "123456", Role.ADMIN));
            adminUserController.create(new User(null, "John Smith", "sj@mail.ru", "password123456", Role.USER));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            adminUserController.getAll().forEach(System.out::println);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println(adminUserController.getByMail("js@mail.ru"));
        }
    }
}

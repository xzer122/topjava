package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal meal = new Meal(FIRST_USER_MEAL_1.getDateTime(), "Duplicate", 500);
        assertThrows(DataAccessException.class, () -> service.create(meal, ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(FIRST_USER_MEAL_ID, ADMIN_ID);
        assertMatch(meal, FIRST_USER_MEAL_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, FIRST_USER_MEAL_9, FIRST_USER_MEAL_8, FIRST_USER_MEAL_7,
                FIRST_USER_MEAL_6, FIRST_USER_MEAL_5, FIRST_USER_MEAL_4,
                FIRST_USER_MEAL_3, FIRST_USER_MEAL_2, FIRST_USER_MEAL_1);
    }


    @Test
    public void update() {
        Meal meal = getUpdated();
        service.update(meal, ADMIN_ID);
        assertMatch(service.get(FIRST_USER_MEAL_ID, ADMIN_ID), getUpdated());
    }

    @Test
    public void delete() {
        service.delete(FIRST_USER_MEAL_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(FIRST_USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void getOtherUsersFood() {
        assertThrows(NotFoundException.class, () -> service.get(SECOND_USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void updateOtherUsersFood() {
        assertThrows(NotFoundException.class, () -> service.update(SECOND_USER_MEAL, ADMIN_ID));
    }

    @Test
    public void deleteOtherUsersFood() {
        assertThrows(NotFoundException.class, () -> service.delete(SECOND_USER_MEAL_ID, ADMIN_ID));
    }
}

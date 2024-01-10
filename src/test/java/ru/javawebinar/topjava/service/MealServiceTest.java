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
        Meal created = service.create(getNew(), FIRST_USER);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        newMeal.setUser_Id(FIRST_USER);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, FIRST_USER), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal meal = new Meal(LocalDateTime.of(2020, 1, 30, 11, 0, 0), "Duplicate", 500);
        meal.setUser_Id(FIRST_USER);
        assertThrows(DataAccessException.class, () ->
                service.create(meal, FIRST_USER));
    }

    @Test
    public void get() {
        Meal meal = service.get(firstUserMealId, FIRST_USER);
        assertMatch(meal, firstUserMeal);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, SECOND_USER));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(FIRST_USER);
        assertMatch(all, firstUserMeal);
    }

    @Test
    public void update() {
        Meal meal = getUpdated();
        service.update(meal, FIRST_USER);
        assertMatch(service.get(firstUserMealId, FIRST_USER), getUpdated());
    }

    @Test
    public void delete() {
        service.delete(firstUserMealId, FIRST_USER);
        assertThrows(NotFoundException.class, () -> service.get(firstUserMealId, FIRST_USER));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, SECOND_USER));
    }
}

package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Meal get(int mealID, int userID) {
        log.info("get {}", mealID);
        return service.get(mealID, userID);
    }

    public Meal create(Meal meal, int userID) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userID);
    }

    public void delete(int mealID, int userID) {
        log.info("delete {}", mealID);
        service.delete(mealID, userID);
    }

    public void update(Meal meal, int mealID, int userID) {
        log.info("update {} with id={}", meal, mealID);
        assureIdConsistent(meal, mealID);
        service.update(meal, userID);
    }
}

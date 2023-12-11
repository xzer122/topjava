package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final int userId = SecurityUtil.authUserId();

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(String startDate, String startTime, String endDate, String endTime) {
        LocalDate startD = startDate.equals("") ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate endD = endDate.equals("") ? LocalDate.MAX : LocalDate.parse(endDate);
        LocalTime startT = startTime.equals("") ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime endT = endTime.equals("") ? LocalTime.MAX : LocalTime.parse(endTime);

        log.info("getAll from {} {} to {} {}", startD, startT, endD, endT);

        return MealsUtil.getTos(service.getFiltered(userId, startD, startT, endD, endT), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(mealId, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}
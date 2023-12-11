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

        return MealsUtil.getFilteredTos(service.getFiltered(SecurityUtil.authUserId(), startD, endD), SecurityUtil.authUserCaloriesPerDay(), startT, endT);
    }

    public Meal get(int mealId) {
        log.info("get by user={}", mealId);
        return service.get(mealId, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}
package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        return MealsUtil.getTos(new ArrayList<>(service.getAll(SecurityUtil.authUserId())), 2000);
    }

    public List<MealTo> getAllFilteredByDateTime(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<MealTo> filteredByDate = MealsUtil.getTos(new ArrayList<>(service.getAll(SecurityUtil.authUserId()).stream()
                .filter(mealTo -> mealTo.getDate().isAfter(startDate) && mealTo.getDate().isBefore(endDate))
                .collect(Collectors.toList())), 2000);
        return filteredByDate.stream()
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public Meal get(int mealId) {
        return service.get(mealId, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int mealId) {
        service.delete(mealId, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        service.update(meal, SecurityUtil.authUserId());
    }
}
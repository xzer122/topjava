package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int mealID, int userId) {
        checkNotFoundWithId(repository.delete(mealID, userId), mealID);
    }

    public Meal get(int mealID, int userId) {
        return checkNotFoundWithId(repository.get(mealID, userId), mealID);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}
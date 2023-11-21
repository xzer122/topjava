package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userID) {
        return repository.save(meal, userID);
    }

    public void delete(int mealID, int userID) {
        checkNotFoundWithId(repository.delete(mealID, userID), mealID);
    }

    public Meal get(int mealID, int userID) {
        return checkNotFoundWithId(repository.get(mealID, userID), mealID);
    }

    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public Meal update(Meal meal, int userID) {
        return checkNotFoundWithId(repository.save(meal, userID), meal.getId());
    }
}
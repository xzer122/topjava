package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int userID);

    // false if meal does not belong to userId
    boolean delete(int id, int userID);

    // null if meal does not belong to userId
    Meal get(int id, int userID);

    // ORDERED dateTime desc
    Collection<Meal> getAll();
}

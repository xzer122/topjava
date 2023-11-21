package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

@Controller
public class MealRestController extends AbstractMealController{

    @Override
    public Collection<Meal> getAll() {
        return super.getAll();
    }

    @Override
    public Meal get(int mealID, int userID) {
        return super.get(mealID, userID);
    }

    @Override
    public Meal create(Meal meal, int userID) {
        return super.create(meal, userID);
    }

    @Override
    public void delete(int mealID, int userID) {
        super.delete(mealID, userID);
    }

    @Override
    public void update(Meal meal, int mealID, int userID) {
        super.update(meal, mealID, userID);
    }
}
package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MutualMealSingletonRepository {
    private volatile MutualMealSingletonRepository instance;
    private MutualMealSingletonRepository() {}
    private Map<User, List<Meal>> mutualRepository = new HashMap<>();

    public MutualMealSingletonRepository getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    return new MutualMealSingletonRepository();
                }
            }
        }
        return instance;
    }

    public Map<User, List<Meal>> getMutualRepository() {
        return mutualRepository;
    }


}

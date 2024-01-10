package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int FIRST_USER = 100001;
    public static final int SECOND_USER = 100000;

    public static final int firstUserMealId = 100003;
    public static final int secondUserMealId = 100004;
    public static final int NOT_FOUND = 10;

    public static final Meal firstUserMeal = new Meal(firstUserMealId, LocalDateTime.of(2020, 1, 30, 11, 0 ,0),"Завтрак админа", 400);
    public static final Meal secondUserMeal = new Meal(secondUserMealId, LocalDateTime.of(2020, 1, 30, 12, 0, 0),"Еда юзера", 400);

    static {
        firstUserMeal.setUser_Id(FIRST_USER);
        secondUserMeal.setUser_Id(SECOND_USER);
    }

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "New meal", 500);
    }

    public static Meal getUpdated() {
        Meal newMeal = new Meal(firstUserMeal);
        newMeal.setDateTime(LocalDateTime.now());
        newMeal.setDescription("Updated");
        newMeal.setCalories(500);
        return newMeal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected){
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}

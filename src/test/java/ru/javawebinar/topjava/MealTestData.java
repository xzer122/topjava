package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;


public class MealTestData {
    public static final int FIRST_USER_MEAL_ID = 100003;
    public static final int SECOND_USER_MEAL_ID = 100012;

    public static final Meal FIRST_USER_MEAL_1 = new Meal(FIRST_USER_MEAL_ID, LocalDateTime.of(2020, 1, 30, 11, 0, 0), "Завтрак админа", 400);
    public static final Meal FIRST_USER_MEAL_2 = new Meal(100004, LocalDateTime.of(2020, 1,30,13,0,0),"обед админа",900);
    public static final Meal FIRST_USER_MEAL_3 = new Meal(100005, LocalDateTime.of(2020, 1, 30, 18, 0 ,0),"ужин админа", 800);
    public static final Meal FIRST_USER_MEAL_4 = new Meal(100006, LocalDateTime.of(2020, 1, 31, 10, 0 ,0),"Завтрак админа", 200);
    public static final Meal FIRST_USER_MEAL_5 = new Meal(100007, LocalDateTime.of(2020, 1, 31, 13, 0 ,0),"Обед админа", 700);
    public static final Meal FIRST_USER_MEAL_6 = new Meal(100008, LocalDateTime.of(2020, 1, 31, 19, 0 ,0),"Ужин админа", 700);
    public static final Meal FIRST_USER_MEAL_7 = new Meal(100009, LocalDateTime.of(2020, 2, 1, 9, 0 ,0),"Вкусный завтрак админа", 400);
    public static final Meal FIRST_USER_MEAL_8 = new Meal(100010, LocalDateTime.of(2020, 2, 1, 14, 0 ,0),"Сытный обед админа", 1200);
    public static final Meal FIRST_USER_MEAL_9 = new Meal(100011, LocalDateTime.of(2020, 2, 1, 20, 0 ,0),"Горячий ужин админа", 700);
    public static final Meal SECOND_USER_MEAL = new Meal(SECOND_USER_MEAL_ID, LocalDateTime.of(2020, 1, 30, 12, 0, 0),"Еда юзера", 400);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2000, 1, 1, 12, 0, 0), "New meal", 500);
    }

    public static Meal getUpdated() {
        Meal newMeal = new Meal(FIRST_USER_MEAL_1);
        newMeal.setDateTime(LocalDateTime.of(2000, 1, 1, 12, 0, 0));
        newMeal.setDescription("Updated");
        newMeal.setCalories(500);
        return newMeal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected){
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}

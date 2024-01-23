package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MealTestData {
    public static final int ADMIN_MEAL_ID = 100003;
    public static final int USER_MEAL_ID = 100013;

    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, 1, 30, 11, 0, 0), "Завтрак админа", 1000);
    public static final Meal adminMeal2 = new Meal(100004, LocalDateTime.of(2020, 1,30,13,0,0),"обед админа",200);
    public static final Meal adminMeal3 = new Meal(100005, LocalDateTime.of(2020, 1, 30, 18, 0 ,0),"ужин админа", 200);
    public static final Meal adminMeal4 = new Meal(100006, LocalDateTime.of(2020, 1, 31, 10, 0 ,0),"Завтрак админа", 500);
    public static final Meal adminMeal5 = new Meal(100007, LocalDateTime.of(2020, 1, 31, 13, 0 ,0),"Обед админа", 500);
    public static final Meal adminMeal6 = new Meal(100008, LocalDateTime.of(2020, 1, 31, 19, 0 ,0),"Ужин админа", 500);
    public static final Meal adminMeal7 = new Meal(100009, LocalDateTime.of(2020, 2, 1, 9, 0 ,0),"Вкусный завтрак админа", 500);
    public static final Meal adminMeal8 = new Meal(100010, LocalDateTime.of(2020, 2, 1, 14, 0 ,0),"Сытный обед админа", 1001);
    public static final Meal adminMeal9 = new Meal(100011, LocalDateTime.of(2020, 2, 1, 20, 0 ,0),"Горячий ужин админа", 500);
    public static final Meal adminMeal10 = new Meal(100012, LocalDateTime.of(2020, 1, 31, 0, 0 ,0),"Еда на пограничное значение", 2001);
    public static final Meal userMeal = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, 1, 30, 12, 0, 0),"Тост с яицом и лососем (завтрак)", 500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2000, 1, 1, 12, 0, 0), "New meal", 500);
    }

    public static Meal getUpdated() {
        Meal newMeal = new Meal(adminMeal1);
        newMeal.setDateTime(LocalDateTime.of(2000, 1, 1, 12, 0, 0));
        newMeal.setDescription("Updated");
        newMeal.setCalories(500);
        return newMeal;
    }

    public static List<Meal> getFiltered() {
        List<Meal> filtered = Arrays.asList(adminMeal1, adminMeal2, adminMeal3);
        Collections.reverse(filtered);
        return filtered;
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

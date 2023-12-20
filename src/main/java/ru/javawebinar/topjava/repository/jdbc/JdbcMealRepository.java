package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class JdbcMealRepository implements MealRepository {

    private final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    public JdbcMealRepository (JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("date_time", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("user_id", meal.getUser_Id());

        if (meal.isNew()){
            Number key = insertMeal.executeAndReturnKey(map);
            meal.setId(key.intValue());
        } else if (namedParameterJdbcTemplate.update("UPDATE meals SET date_time=:date_time, desctiption=:description, " +
                "calories=:calories WHERE id=:?, user_id=?", map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("UPDATE FROM meals WHERE id=?, user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=?, user_id=?", ROW_MAPPER, new Object[]{id, userId});
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?", ROW_MAPPER, new Object[]{userId});
        return getByPredicate(meals, meal -> true, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?", ROW_MAPPER, new Object[userId]);
        return getByPredicate(meals, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime), userId);
    }

    private List<Meal> getByPredicate(List<Meal> meals, Predicate<Meal> filter, int user_id) {
        return meals.stream()
                .filter(meal -> meal.getUser_Id() == user_id)
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

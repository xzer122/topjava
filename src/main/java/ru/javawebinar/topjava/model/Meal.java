package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity implements Comparable<Meal>{

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private int userId;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setUserId(int userId) {this.userId = userId;}

    @Override
    public int compareTo(Meal o) {
        int cmp = Integer.compare(id, o.getId());
        if (cmp == 0) {
            cmp = this.dateTime.compareTo(o.dateTime);
            if (cmp == 0) {
                cmp = this.description.compareTo(o.description);
                if (cmp == 0) {
                    cmp = Integer.compare(this.calories, o.calories);
                    if (cmp == 0) {
                        cmp = Integer.compare(this.userId, o.userId);
                    }
                }
            }
        }
        return cmp;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}

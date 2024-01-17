package ru.itis.game;

public enum Entities {
    FIRST_CAR(0),
    SECOND_CAR(1),
    RANDOM_CARS(2)
    ;

    public final int id;

    Entities(int id) {
        this.id = id;
    }
}

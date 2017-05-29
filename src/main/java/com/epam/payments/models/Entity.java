package com.epam.payments.models;

/**
 * {@code Entity} is a base class for entities of heirs.
 *
 * @author Aleksey Tsoy
 */
public abstract class Entity {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

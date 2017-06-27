package com.epam.payments.models;

import java.io.InputStream;

public class Provider extends Entity {
    private String name;
    private InputStream logotype;
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getLogotype() {
        return logotype;
    }

    public void setLogotype(InputStream logotype) {
        this.logotype = logotype;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

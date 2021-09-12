package com.sample.test.demo.constants;


import java.util.Locale;

public enum PizzaTypes {
    SMALL_NOTOPPINGS("Small 6 Slices - no toppings", 6.75),
    SMALL_ONETOPPING("Small 6 Slices - 1 topping", 7.50),
    MEDIUM_TWOTOPPINGS("Medium 8 Slices - 2 toppings", 9.75),
    LARGE_NOTOPPINGS("Large 10 Slices - no toppings", 11.75),
    LARGE_TWOTOPPINGS("Large 10 Slices - 2 toppings", 13.50);

    private String displayName;
    private double cost;

    private PizzaTypes(String displayName, double cost) {
        this.displayName = displayName;
        this.cost = cost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getCost() {
        return cost;
    }

    public String getFormattedCost() {
        return String.format(Locale.US, "%.2f", cost);
    }

}

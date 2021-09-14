package com.sample.test.demo.constants;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum PizzaToppings {
    MANGOS("Diced Mango"),
    OLIVES("Olives"),
    MUSHROOMS("Mushrooms"),
    ONIONS("Caramelized onions"),
    ITALIANHAM("Italian Ham"),
    PEPPERONI("Classic Pepperoni"),
    SALAMI("Salami"),
    PROVOLONE("Provolone cheese"),
    EXTRACHEESE("Extra cheese"),
    MOZZARELLA("Mozzarella cheese"),
    PARMESAN("Parmesan cheese");

    private String displayName;
    private static final List<PizzaToppings> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    PizzaToppings(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PizzaToppings getRandomTopping() {
        return VALUES.get(new Random().nextInt(VALUES.size()));
    }

    public static List<String> getAllValues() {
        return VALUES.stream().map(v -> v.getDisplayName()).collect(Collectors.toList());
    }
}

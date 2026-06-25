package com.crimsonashduels;

import java.util.*;

public class KitManager {
    private final Map<String, Object> kits = new HashMap<>();

    public List<String> getKitNames() {
        return new ArrayList<>(kits.keySet());
    }
}

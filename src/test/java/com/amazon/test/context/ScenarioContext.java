package com.amazon.test.context;


import java.util.HashMap;

/**
 * This class stores context data of a scenario.
 * The data stored in the context is shared among all step definitions of a scenario during the execution of that scenario.
 */
public class ScenarioContext {
    private static HashMap<ContextItem, Object> contextData = null;

    /**
     * Creates new context data.
     * When called first time a new context data is created.
     * Subsequent calls clears context data.
     */
    public static void create() {
        if (contextData == null) contextData = new HashMap<>();
        else contextData.clear();
    }

    /**
     * Purges all context data.
     */
    public static void purge() {
        contextData.clear();
        contextData = null;
    }

    /**
     * Get an item stored in the context data.
     *
     * @param contextItem item key
     * @return stored item or null if item key does not exist in the context
     */
    public static Object getItem(ContextItem contextItem) {
        return contextData.get(contextItem);
    }

    /**
     * Set an item in the context data.
     *
     * @param contextItem item key
     * @param value       item value
     */
    public static void setItem(ContextItem contextItem, Object value) {
        contextData.put(contextItem, value);
    }
}

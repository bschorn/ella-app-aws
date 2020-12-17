package org.schorn.ella.aws.core;

import org.schorn.ella.app.config.IAppConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 *
 *
 */
public class AWSConfig implements IAppConfig.Mutable {

    static public AWSConfig getInstance() {
        return new AWSConfig();
    }

    static private final Map<String,Object> MAP = new HashMap<>();
    static {
        for (String key : System.getenv().keySet()) {
            MAP.put(key, System.getenv(key));
        }
        for (Map.Entry<Object,Object> entry : System.getProperties().entrySet()) {
            MAP.put(entry.getKey().toString(), entry.getValue());
        }
    }

    private AWSConfig() {
    }

    @Override
    public boolean has(String name) {
        return MAP.containsKey(name);
    }

    @Override
    public Optional<Object> getObject(String name) {
        return Optional.ofNullable(MAP.get(name));
    }

    @Override
    public Optional<String> getString(String name) {
        Object value = MAP.get(name);
        return Optional.ofNullable(value == null ? null : value.toString());
    }

    @Override
    public <T> Optional<T> get(Class<T> classOfT, String name) {
        Object value = MAP.get(name);
        if (classOfT.isInstance(value)) {
            return Optional.of(classOfT.cast(value));
        }
        return Optional.empty();
    }

    @Override
    public <T> boolean isInstance(Class<T> classOfT, String name) {
        Object value = MAP.get(name);
        return classOfT.isInstance(value);
    }

    @Override
    public Object set(String name, Object value) {
        return MAP.put(name, value);
    }
}

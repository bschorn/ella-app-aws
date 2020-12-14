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

    private final Map<String,Object> map = new HashMap<>();

    @Override
    public Optional<Object> getObject(String name) {
        return Optional.ofNullable(this.map.get(name));
    }

    @Override
    public Optional<String> getString(String name) {
        Object value = this.map.get(name);
        return Optional.ofNullable(value == null ? null : value.toString());
    }

    @Override
    public <T> Optional<T> get(Class<T> classOfT, String name) {
        Object value = this.map.get(name);
        if (classOfT.isInstance(value)) {
            return Optional.of(classOfT.cast(value));
        }
        return Optional.empty();
    }

    @Override
    public <T> boolean isInstance(Class<T> classOfT, String name) {
        Object value = this.map.get(name);
        return classOfT.isInstance(value);
    }

    @Override
    public Object set(String name, Object value) {
        return this.map.put(name, value);
    }
}

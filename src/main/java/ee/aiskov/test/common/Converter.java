package ee.aiskov.test.common;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class Converter {
    private final Map<Class, Function<Object, Object>> mapFunctions = new HashMap<>();

    private final Mapper mapper;

    public Converter(Mapper mapper) {
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    public <T> Function<Object, T> convert(Class<T> to) {
        return (Function<Object, T>) this.mapFunctions.computeIfAbsent(to, this::converter);
    }

    public <T> T populate(Object from, T to) {
        mapper.map(from, to);
        return to;
    }

    private <T> Function<Object, T> converter(Class<T> type) {
        return from -> (T) mapper.map(from, type);
    }
}

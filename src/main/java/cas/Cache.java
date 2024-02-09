package cas;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.id(), (id, stored) -> {
            if (stored.version() != model.version()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(id, model.name(), model.version() + 1);
        }) != null;
    }

    public void delete(int id) {
        memory.computeIfPresent(id, (key, value) -> null);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}

package contacts;

import java.time.LocalDateTime;
import java.util.Optional;

public abstract class ContactBuilder<T extends Contact> {
    protected T result;

    private void check() {
        if (result == null) {
            throw new IllegalStateException("result not initialized");
        }
    }

    ContactBuilder<T> withField(String fieldName, Object value) {
        check();
        Optional<String> r = result.setValue(fieldName, value);
        if (r.isPresent()) {
            throw new RuntimeException(r.get());
        }
        return this;
    }

    public Contact build() {
        check();
        result.setValue("timeCreated", LocalDateTime.now());
        result.setValue("timeEdited", result.getValue("timeCreated"));
        return result;
    }
}

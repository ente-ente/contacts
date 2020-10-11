package contacts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

abstract class Contact implements Serializable {
    protected HashMap<String, Object> values;
    protected HashMap<String, FieldSpec> spec;

    public Contact() {
        this.spec = new HashMap<>();
        spec.putAll(ContactFieldSpecs.spec);
        this.values = new LinkedHashMap<>();
    }

    public abstract String[] getAlterableFieldNames();

    public abstract String getShortInfo();

    public Optional<String> setValue(String fieldName, Object value) {
        if (!spec.containsKey(fieldName)) {
            throw new IllegalStateException("Field " + fieldName + " does not exist for " + this.getClass().toString() );
        }
        Optional<String> result = spec.get(fieldName).validate.apply(value);
        if (result.isEmpty()) {
            values.put(fieldName, value);
        }
        return result;
    }

    public Object getValue(String fieldName) {
        if (!spec.containsKey(fieldName)) {
            throw new IllegalStateException("Field " + fieldName + " does not exist for " + this.getClass().toString() );
        }
        return values.getOrDefault(fieldName, spec.get(fieldName).defaultValue);
    }

    public HashMap<String, Object> getValueMap() {
        return values;
    }

    public FieldSpec getFieldSpec(String fieldName) {
        return spec.get(fieldName);
    }

    public HashMap<String, FieldSpec>  getFieldSpecs() {
        return spec;
    }
}


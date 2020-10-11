package contacts;

import java.util.HashMap;

final class Person extends Contact {
    private static final long serialVersionUID = 42L;
    private final HashMap<String, Object> values;

    Person() {
        super();
        values = super.values;
        spec.putAll(PersonFieldSpecs.spec);
    }

    @Override
    public String[] getAlterableFieldNames() {
        return PersonFieldSpecs.alterableFields;
    }

    @Override
    public String getShortInfo() {
        return values.get("name") + " " + values.get("surname");
    }
}

final class PersonBuilder extends ContactBuilder<Person> {

    public PersonBuilder() {
        result = new Person();
    }
}

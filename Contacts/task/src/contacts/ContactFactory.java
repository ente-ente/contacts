package contacts;

import java.util.HashMap;

public interface ContactFactory {
    static Person createPerson() {
        PersonBuilder builder = new PersonBuilder();
        setFields(builder, PersonFieldSpecs.alterableFields, PersonFieldSpecs.spec);
        return (Person) builder.build();
    }

    static Organization createOrganization() {
        OrganizationBuilder builder = new OrganizationBuilder();
        setFields(builder, OrganizationFieldSpecs.alterableFields, OrganizationFieldSpecs.spec);
        return (Organization) builder.build();
    }

    static void setFields(ContactBuilder builder, String[] fields, HashMap<String, FieldSpec> fieldSpec) {
        for (String field : fields) {
            try {
                builder.withField(field, Dialogues.INSTANCE.getValue(fieldSpec.get(field).displayNameIn));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

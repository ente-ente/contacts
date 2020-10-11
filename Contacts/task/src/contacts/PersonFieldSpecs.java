package contacts;

import java.util.HashMap;

enum PersonFieldSpecs {
    ;
    static final HashMap<String, FieldSpec> spec = new HashMap<>();
    static final String[] allFields = new String[]{"name", "surname", "birth", "gender", "phone", "timeCreated", "timeEdited"};
    static final String[] alterableFields = new String[]{"name", "surname", "birth", "gender", "phone"};
    static {
        spec.putAll(ContactFieldSpecs.spec);
        spec.put("name", new FieldSpec("name", "name", "", "", ValidationHelpers.NO_VALIDATION));
        spec.put("surname", new FieldSpec("surname", "surname", "", "", ValidationHelpers.NO_VALIDATION));
        spec.put("birth", new FieldSpec("birth", "birth date", "","[no data]", ValidationHelpers.VALIDATE_DATE));
        spec.put("gender", new FieldSpec("gender", "gender (M,F)", "gender", "[no data]", ValidationHelpers.VALIDATE_GENDER));
    }
}

enum OrganizationFieldSpecs {
    ;
    static final HashMap<String, FieldSpec> spec = new HashMap<>();
    static final String[] allFields = new String[]{"name", "address", "phone", "timeCreated", "timeEdited"};
    static final String[] alterableFields = new String[]{"name", "address", "phone"};
    static {
        spec.putAll(ContactFieldSpecs.spec);
        spec.put("name", new FieldSpec("name", "organization name", "", "", ValidationHelpers.NO_VALIDATION));
        spec.put("address", new FieldSpec("address", "address", "", "", ValidationHelpers.NO_VALIDATION));
    }
}

enum ContactFieldSpecs {
    ;
    static final HashMap<String, FieldSpec> spec = new HashMap<>();
    static {
        spec.put("timeCreated", new FieldSpec("timeCreated", "time created", "", "", ValidationHelpers.NO_VALIDATION));
        spec.put("timeEdited", new FieldSpec("timeEdited", "time last edit",  "", "", ValidationHelpers.NO_VALIDATION));
        spec.put("phone", new FieldSpec("phone", "number", "", "[no number]", ValidationHelpers.VALIDATE_PHONE));
    }
}
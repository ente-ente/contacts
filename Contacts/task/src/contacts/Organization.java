package contacts;
import java.util.HashMap;

public class Organization extends Contact {
    private static final long serialVersionUID = 42L;
    private final HashMap<String, Object> values;

    Organization() {
        super();
        values = super.values;
        spec.putAll(contacts.OrganizationFieldSpecs.spec);
    }

    @Override
    public String[] getAlterableFieldNames() {
        return contacts.OrganizationFieldSpecs.alterableFields;
    }

    @Override
    public String getShortInfo() {
        return values.get("name").toString();
    }
}

final class OrganizationBuilder extends ContactBuilder<Organization> {
    public OrganizationBuilder() {
        result = new Organization();
    }
}

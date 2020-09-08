package contacts;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String phone;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastEdited;
    private final ContactType type;

    public Contact(ContactType type) {
        this.type = type;
    }

    private static final Pattern javaPattern = Pattern.compile("\\+?([\\d[a-z][A-Z]]+([\\u0020\\-]\\([\\d[a-z][A-Z]]{2,}\\))?([\\u0020\\-][\\d[a-z][A-Z]]{2,})*|\\([\\d[a-z][A-Z]]{2,}\\)([\\u0020\\-][\\d[a-z][A-Z]]{2,})*)");

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public LocalDateTime getTimeLastEdited() {
        return timeLastEdited;
    }

    public void setTimeLastEdited(LocalDateTime timeLastEdited) {
        this.timeLastEdited = timeLastEdited;
    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String number) {
        if (isValidPhone(number)) {
            this.phone = number;
            return true;
        } else this.phone = null;
        return false;
    }

    private static boolean isValidPhone(String number) {
        Matcher matcher = javaPattern.matcher(number);
        return matcher.matches();
    }

    public ContactType getType() {
        return type;
    }
}

class Organization extends Contact {
    private static final long serialVersionUID = 1L;
    private String organizationName;
    private String address;
    public final ContactType type = ContactType.ORGANIZATION;

    public Organization() {
        super(ContactType.ORGANIZATION);
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
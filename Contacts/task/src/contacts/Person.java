package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Person extends Contact {
    private static final long serialVersionUID = 1L;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String gender;

    public Person() {
        super(ContactType.PERSON);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException d) {
            return false;
        }
        return true;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean setGender(String gender) {
        if (gender.matches("M|F")) {
            this.gender = gender;
            return true;
        }
        return false;
    }

    public String getGender() {
        return gender;
    }
}

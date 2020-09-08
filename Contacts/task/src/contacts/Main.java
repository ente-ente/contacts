package contacts;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static ArrayList<Contact> phoneBook;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean serialize = args.length > 0;
        if (serialize) {
            File file = new File(args[0]);
            if (!file.exists()) {
                phoneBook = new ArrayList<Contact>();
            } else {
                try {
                    phoneBook = (ArrayList<Contact>) SerializationUtils.deserialize(file);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            phoneBook = new ArrayList<Contact>();
        }

        while (true) {
            System.out.print("Enter action (add, remove, edit, count, info, exit): ");
            String action = scanner.nextLine().trim();
            switch (action) {
                case "add":
                    var contact = addContact();
                    contact.setTimeCreated(LocalDateTime.now());
                    contact.setTimeLastEdited(LocalDateTime.now());
                    System.out.println("The record added.");
                    System.out.println();
                    break;
                case "remove":
                    if (phoneBook.size() == 0) {
                        System.out.println("No records to remove!");
                    } else if (removeContact(selectContact(showContacts()))) {
                        System.out.println("The record removed.");
                    }
                    System.out.println();
                    break;
                case "edit":
                    if (phoneBook.size() == 0) {
                        System.out.println("No records to edit!");
                    } else {
                        contact = selectContact(showContacts());
                        if (contact != null && editContact(contact)) {
                            contact.setTimeLastEdited(LocalDateTime.now());
                            System.out.println("The record updated!");
                        }
                    }
                    System.out.println();
                    break;
                case "count":
                    System.out.println("The Phone Book has " + phoneBook.size() + " records.");
                    System.out.println();
                    break;
                case "info":
                    if (phoneBook.size() == 0) {
                        System.out.println("No records to show!");
                    } else {
                        showInfo(showContacts());
                    }
                    System.out.println();
                    break;
                case "exit":
                    if (serialize) {
                        try {
                            SerializationUtils.serialize(phoneBook, new File(args[0]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                default:
                    System.out.println("Not a valid action!");
            }
        }
    }



    private static boolean editContact(Contact contact) {
        if (contact.getType() == ContactType.PERSON) {
            return editPerson((Person) contact);
        } else {
            return editOrganization((Organization) contact);
        }
    }

    private static boolean editOrganization(Organization contact) {
        System.out.print("Select a field (name, address, number): ");
        String field = scanner.nextLine().trim();
        switch (field) {
            case "name":
                setName(contact);
                break;
            case "address":
                setAddress(contact);
                break;
            case "number":
                setPhone(contact);
                break;
            default:
                return false;
        }
        return true;
    }

    private static boolean removeContact(Contact contact) {
        if (contact == null) {
            return false;
        }
        return phoneBook.remove(contact);
    }

    private static HashMap<Integer, Contact> showContacts() {
        HashMap<Integer, Contact> records = new HashMap<>();
        int count = 0;
        for (var contact : phoneBook) {
            count++;
            records.put(count, contact);
            System.out.println(count + ". " + shortInfo(contact));
        }
        return records;
    }
    
    private static void showInfo(HashMap<Integer, Contact> records) {
        var contact = selectContact(records);
        if (contact != null) {
            if (contact instanceof Person) {
                showPersonInfo((Person) contact);
            } else {
                showOrganizationInfo((Organization) contact);
            }
        }
    }

    private static void showOrganizationInfo(Organization contact) {
        System.out.println(organizationInfo(contact));
        System.out.println(contactInfo(contact));
    }

    private static String organizationInfo(Organization contact) {
        return  "Organization name: " + contact.getOrganizationName() +
                "\nAddress: " + contact.getAddress();
    }

    private static void showPersonInfo(Person contact) {
        System.out.println(personInfo(contact));
        System.out.println(contactInfo(contact));
    }

    private static String personInfo(Person contact) {
        return "Name: " + contact.getName() +
                "\nSurname: " + contact.getSurname() +
                "\n Birth date: " + (contact.getBirthDate() != null ? contact.getBirthDate().toString() : "[no data]") +
                "\n Gender: " + (contact.getGender() != null ? contact.getGender() : "[no data]");
    }

    private static String contactInfo(Contact contact) {
        return String.format("Number: %s\nTime created: %s\nTime last edit: %s",
                contact.getPhone() != null ? contact.getPhone() : "[no data]",
                contact.getTimeCreated(),
                contact.getTimeLastEdited());
    }

    private static String shortInfo(Contact contact) {
        if (contact.getType() == ContactType.PERSON) {
            return ((Person) contact).getName() + " " + ((Person) contact).getSurname();
        }
        return ((Organization) contact).getOrganizationName();
    }

    private static Contact selectContact(HashMap<Integer, Contact> records) {
        System.out.print("Select a record: ");
        int key = Integer.parseInt(scanner.nextLine());
        if (records.containsKey(key)) {
            return records.get(key);
        }
        return null;
    }

    private static boolean editPerson(Person contact) {
        System.out.print("Select a field (name, surname, birth, gender, number): ");
        String field = scanner.nextLine().trim();
        switch (field) {
            case "name":
                setName(contact);
                break;
            case "surname":
                setSurname(contact);
                break;
            case "number":
                setPhone(contact);
                break;
            case "birth":
                setBirth(contact);
                break;
            case "gender":
                setGender(contact);
                break;
            default:
                return false;
        }
        return true;
    }

    private static void setGender(Person contact) {
        System.out.print("Enter the gender (M, F): ");
        if (!contact.setGender(scanner.nextLine().trim())) {
            System.out.println("Bad gender!");
        };
    }

    private static void setBirth(Person contact) {
        System.out.print("Enter the birth date: ");
        if (!contact.setBirthDate(scanner.nextLine().trim())) {
            System.out.println("Bad birth date!");
        }
    }

    private static void setSurname(Person contact) {
        System.out.print("Enter the surname: ");
        contact.setSurname(scanner.nextLine().trim());
    }

    private static Contact addContact() {
        System.out.print("Enter the type: ");
        Contact contact;
        if (scanner.nextLine().trim().equals("person")) {
            return addPerson();
        } else {
            return addOrganization();
        }
    }

    private static Contact addOrganization() {
        var contact = new Organization();
        setName(contact);
        setAddress(contact);
        setPhone(contact);
        contact.setTimeCreated(LocalDateTime.now());
        phoneBook.add(contact);
        return contact;
    }

    private static void setName(Organization contact) {
        System.out.print("Enter the organization name: ");
        contact.setOrganizationName(scanner.nextLine().trim());
    }

    private static void setAddress(Organization contact) {
        System.out.print("Enter the address: ");
        contact.setAddress(scanner.nextLine().trim());
    }

    private static Contact addPerson() {
        var contact = new Person();
        setName(contact);
        setSurname(contact);
        setBirth(contact);
        setGender(contact);
        setPhone(contact);
        phoneBook.add(contact);
        return contact;
    }

    private static void setPhone(Contact contact) {
        System.out.print("Enter the number: ");
        scanner.useDelimiter(System.getProperty("line.separator"));
        if (!contact.setPhone(scanner.nextLine().trim())) {
            System.out.println("Wrong number format!");
        }
        scanner.reset();
    }

    private static void setName(Person contact) {
        System.out.print("Enter the name: ");
        contact.setName(scanner.nextLine().trim());
    }
}


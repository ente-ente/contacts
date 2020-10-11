package contacts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneBook {
    private final List<Contact> phoneBook;
    private final File file;

    public static PhoneBook openPhoneBook(String filename) {
        if (filename.isEmpty()) {
            return new PhoneBook();
        }
        File file = new File(filename);
        return new PhoneBook(file);
    }

    @SuppressWarnings("unchecked")
    private PhoneBook(File file) {
        this.file = file;
        if (!file.exists()) {
            this.phoneBook = new ArrayList<>();
        } else {
            try {
                this.phoneBook = (List<Contact>) SerializationUtils.deserialize(file);
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalStateException("cannot load from file " + file, e);
            }
        }
    }

    private PhoneBook() {
        this.phoneBook = new ArrayList<>();
        this.file = null;
    }

    public List<Contact> getContactList() {
        return phoneBook;
    }

    public Optional<Contact> getContact(Contact contact) {
        if (phoneBook.contains(contact)) {
            return Optional.of(phoneBook.get(phoneBook.indexOf(contact)));
        }
        return Optional.empty();
    }

    public Contact getContact(int i) {
        return phoneBook.get(i);
    }

    public int size() {
        return phoneBook.size();
    }

    public void close() {
        if (file == null) {
            return;
        }
        try {
            SerializationUtils.serialize(phoneBook, this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addContact(Contact contact) {
        if (contact != null) {
            phoneBook.add(contact);
        }
    }

    public boolean removeContact(Contact contact) {
        if (contact == null) {
            return false;
        }
        return phoneBook.remove(contact);
    }
}



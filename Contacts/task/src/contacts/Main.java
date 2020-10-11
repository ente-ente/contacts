package contacts;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "";
        PhoneBook phonebook = PhoneBook.openPhoneBook(fileName);
        if (!"".equals(fileName)) {
            System.out.println("open " + fileName);
        }
        Context.INSTANCE.currentPhoneBook = phonebook;
        Menu.INSTANCE.run(Context.INSTANCE);
    }
}

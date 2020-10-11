package contacts;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Optional<String> fileName = args.length > 0 ? Optional.of(args[0]) : Optional.empty();
        PhoneBook phonebook = PhoneBook.openPhoneBook(fileName);
        fileName.ifPresent(fn -> System.out.println("open " + fn));
        Context.INSTANCE.currentPhoneBook = phonebook;
        Menu.INSTANCE.run(Context.INSTANCE);
    }

}

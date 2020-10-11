package contacts;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public enum Dialogues {
    INSTANCE;
    Scanner scanner = new Scanner(System.in);

    public String getType() {
        System.out.println("Enter the type (person, organization): ");
        return scanner.nextLine().trim();
    }

    public String getValue(String field) {
        System.out.println("Enter the " + field + ": ");
        return scanner.nextLine().trim();
    }

    public Optional<String> getAction(String displayName, List<String> actions) {
        StringBuilder sb = new StringBuilder("\n[" + displayName + "] " + "Enter action (");
        for (int i = 0; i < actions.size() - 1; i++) {
            sb.append(actions.get(i));
            sb.append(", ");
        }
        sb.append(actions.get(actions.size() - 1));
        sb.append("): ");
        System.out.print(sb.toString());
        return Optional.of(scanner.nextLine().trim());
    }

    public String getSearchTerm() {
        System.out.println("Enter search query: ");
        return scanner.nextLine().trim();
    }

    public String getFieldToEdit(String[] alterableFields) {
        System.out.println("Select a field (" + String.join(", ", alterableFields) + "): ");
        return scanner.nextLine().trim();
    }

    public String newFieldValue(String fieldToEdit) {
        System.out.println("Enter " + fieldToEdit + ": " );
        return scanner.nextLine().trim();
    }
}

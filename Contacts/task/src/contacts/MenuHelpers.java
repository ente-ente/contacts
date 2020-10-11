package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class MenuHelpers {
    public static final Function<Context, Optional<String>> COUNT_RECORDS = o -> {
        System.out.println("The Phone Book has " + o.currentPhoneBook.size() + " records.");
        return Optional.of("menu");
    };
    public static final Function<Context, Optional<String>> ADD = MenuHelpers::add;
    public static final Function<Context, Optional<String>> ACTION_PROMPT = MenuHelpers::chooseAction;
    public static final Function<Context, Optional<String>> SHOW_ALL_RECORDS = MenuHelpers::showContacts;
    public static final Function<Context, Optional<String>> SHOW_RECORD = MenuHelpers::showRecord;
    public static final Function<Context, Optional<String>> TO_MAIN_MENU = o -> Optional.of("menu");
    public static final Function<Context, Optional<String>> DELETE_RECORD = MenuHelpers::deleteRecord;
    public static final Function<Context, Optional<String>> EDIT_RECORD = MenuHelpers::editRecord;
    public static final Function<Context, Optional<String>> SEARCH = MenuHelpers::filterRecords;

    private static Optional<String> filterRecords(Context context) {
        List<Contact> matching = filterRecords(context.currentPhoneBook.getContactList());
        for (int i = 0; i < matching.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, matching.get(i).getShortInfo());
        }
        List<String> actions = context.activeMenu.children;
        Optional<String> nextAction = Dialogues.INSTANCE.getAction(context.activeMenu.displayName, actions);

        while (nextAction.isPresent() && nextAction.get().equals("again")) {
            matching = filterRecords(matching);
            for (int i = 0; i < matching.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, matching.get(i).getShortInfo());
            }
            nextAction = Dialogues.INSTANCE.getAction(context.activeMenu.displayName, actions);
        }
        if(nextAction.isPresent() && nextAction.get().equals("back")) {
            return Optional.of("menu");
        } else if (nextAction.isPresent()) {
            try {
                int contactNumber = Integer.parseInt(nextAction.get());
                context.currentContact = matching.get(contactNumber - 1);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                return Optional.empty();
            }
            return Optional.of("[number]");
        } else {
            return Optional.empty();
        }
    }

    private static String concatFieldValues(Contact contact) {
        String[] values = contact.getValueMap().values().stream().map(v -> null != v ? v.toString() : "").toArray(String[]::new);
        return String.join("", values);
    }

    private static List<Contact> filterRecords(List<Contact> records) {
        String expression = Dialogues.INSTANCE.getSearchTerm();
        Pattern pattern = Pattern.compile("(?i).*" + expression + ".*");
        return records.stream()
                .filter(c -> {
                    Matcher matcher = pattern.matcher(concatFieldValues(c));
                    return matcher.find();
                })
                .collect(Collectors.toList());
    }

    private static Optional<String> deleteRecord(Context context) {
        context.currentPhoneBook.removeContact(context.currentContact);
        context.currentContact = null;
        System.out.println("The record deleted.");
        return Optional.of("menu");
    }

    private static Optional<String> editRecord(Context context) {
        var contact = context.currentContact;
        var alterableFields = contact.getAlterableFieldNames();
        String fieldToEdit = "";
        while (!contact.getFieldSpecs().containsKey(fieldToEdit)) {
            fieldToEdit = Dialogues.INSTANCE.getFieldToEdit(alterableFields);
        }
        contact.setValue(fieldToEdit, Dialogues.INSTANCE.newFieldValue(fieldToEdit));
        System.out.println("Saved");

        return showRecord(context);
    }

    private static Optional<String> showRecord(Context context) {
        Contact contact = context.currentContact;
        for (var field : contact.getAlterableFieldNames()) {
            System.out.printf("%s: %s%n", capitalize(contact.getFieldSpec(field).displayNameOut), contact.getValue(field).toString());
        }
        printTimeStamp("timeCreated", contact);
        printTimeStamp("timeEdited", contact);
        return Optional.of("record");
    }

    private static void printTimeStamp(String time, Contact contact) {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        System.out.printf("%s: %s%n", capitalize(contact.getFieldSpec(time).displayNameOut), ((LocalDateTime) contact.getValue(time)).truncatedTo(ChronoUnit.SECONDS).format(dtf));
    }

    private static String capitalize(String displayName) {
        return displayName.substring(0, 1).toUpperCase() + displayName.substring(1);
    }

    private static Optional<String> showContacts(Context context) {
        List<Contact> contacts = context.currentPhoneBook.getContactList();
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, contacts.get(i).getShortInfo());
        }
        return chooseAction(context);
    }

    private static Optional<String> chooseAction(Context context) {
        if (context.activeMenu.children.isEmpty()) {
            return Optional.empty();
        }
        var actions = context.activeMenu.children;
        Optional<String> result = Dialogues.INSTANCE.getAction(context.activeMenu.displayName, actions);
        if (result.isPresent()) {
            if (actions.contains(result.get())) {
                return result;
            } else if (actions.contains("[number]")) {
                try {
                    int contactNumber = Integer.parseInt(result.get());
                    context.currentContact = context.currentPhoneBook.getContact(contactNumber - 1);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    return Optional.empty();
                }
                return Optional.of("[number]");
            }
        }
        return Optional.empty();
    }

    static Optional<String> add(Context o) {
        o.currentContact = null;
        String type = Dialogues.INSTANCE.getType();
        if (type.equals("person")) {
            o.currentContact = ContactFactory.createPerson();
        } else if (type.equals("organization")) {
            o.currentContact = ContactFactory.createOrganization();
        }
        if (o.currentContact != null) {
            o.currentPhoneBook.addContact(o.currentContact);
            System.out.println("The record added.");
        }
        return Optional.of("menu");
    }
}

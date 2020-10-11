package contacts;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public class FieldSpec implements Serializable {
    public FieldSpec(String name, String displayNameIn, String displayNameOut, String defaultValue, SerializableFunction<Object, Optional<String>> validate) {
        this.name = name;
        this.displayNameIn = displayNameIn;
        this.displayNameOut = "".equals(displayNameOut) ? displayNameIn : displayNameOut;
        this.defaultValue = defaultValue;
        this.validate = validate;
    }

    String name;
    String displayNameIn;
    String displayNameOut;
    String defaultValue;
    SerializableFunction<Object, Optional<String>> validate;
}

interface SerializableFunction<T, R> extends Function<T, R>, Serializable {}

final class ValidationHelpers {
    private static final Pattern phonePattern = Pattern.compile(
            "\\+?([\\da-zA-Z]+([\\u0020\\-]\\([\\da-zA-Z]{2,}\\))?([\\u0020\\-][\\da-zA-Z]{2,})*|\\([\\da-zA-Z]{2,}\\)([\\u0020\\-][\\da-zA-Z]{2,})*)");
    private static final Pattern genderPattern = Pattern.compile(
            "[MF]");
    public static final SerializableFunction<Object, Optional<String>>  NO_VALIDATION = o->Optional.empty();
    public static final SerializableFunction<Object, Optional<String>>  VALIDATE_PHONE = o-> {
        if (phonePattern.matcher((String) o).matches()) {
            return Optional.empty();
        } else {
            return Optional.of("Wrong number format!");
        }
    };
    public static final SerializableFunction<Object, Optional<String>>  VALIDATE_GENDER = o-> {
        if (genderPattern.matcher((String) o).matches()) {
            return Optional.empty();
        } else {
            return Optional.of("Bad gender!");
        }
    };
    public static final SerializableFunction<Object, Optional<String>> VALIDATE_DATE = o-> {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            var parse = dateFormatter.parse((String) o);
        } catch (DateTimeParseException e) {
            return Optional.of("Bad date!");
        }
        return Optional.empty();
    };


}

package contacts;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MenuItem {
    String displayName;
    Context context;
    List<String> children;
    Function<Context, Optional<String>> execute;

    public MenuItem(String displayName, List<String> children, Context currentContext, Function<Context, Optional<String>> execute) {
        this.displayName = displayName;
        context = currentContext;
        this.children = children;
        this.execute = execute;
    }
}


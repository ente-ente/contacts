package contacts;

import java.util.HashMap;
import java.util.Optional;

public enum Menu {
    INSTANCE;
    public void run(Context context) {
        HashMap<String, MenuItem> items = MenuFactory.getMenu(context);
        MenuItem currentItem;
        Optional<String> nextKey;
        currentItem = items.get("menu");

        while (true) {
            context.activeMenu = currentItem;
            nextKey = currentItem.execute.apply(context);
            if (nextKey.isPresent()) {
                if (nextKey.get().equals("exit")) {
                    break;
                }
                currentItem = items.getOrDefault(nextKey.get(), currentItem);
            }

        }
    }

}

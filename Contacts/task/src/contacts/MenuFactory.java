package contacts;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuFactory {
    public static HashMap<String, MenuItem> getMenu(Context context) {
        HashMap<String, MenuItem> menu = new HashMap<>();
        MenuItem exitItem = new MenuItem("exit", null, null, null);
        MenuItem addItem = new MenuItem("add", null, context, MenuHelpers.ADD);
        MenuItem backItem = new MenuItem( "back", null, context, MenuHelpers.TO_MAIN_MENU);
        MenuItem selectRecordItem = new MenuItem("[number]", null, context, MenuHelpers.SHOW_RECORD);
        MenuItem editRecordItem = new MenuItem("edit", null, context, MenuHelpers.EDIT_RECORD);
        MenuItem countItem = new MenuItem("count", null, context, MenuHelpers.COUNT_RECORDS);
        MenuItem searchItem = new MenuItem("search", new ArrayList<>() {{
            add("[number]");
            add("back");
            add("again");
        }}, context, MenuHelpers.SEARCH);
        MenuItem deleteRecordItem = new MenuItem("delete", null, context, MenuHelpers.DELETE_RECORD);
        MenuItem listItem = new MenuItem( "list", new ArrayList<>() {{
            add("[number]");
            add("back");
        }}, context, MenuHelpers.SHOW_ALL_RECORDS);
        MenuItem menuItem = new MenuItem("menu", new ArrayList<>() {{
            add("add");
            add("list");
            add("search");
            add("count");
            add("exit");
        }}, context, MenuHelpers.ACTION_PROMPT);
        MenuItem recordItem = new MenuItem( "record", new ArrayList<>() {{
            add("edit");
            add("delete");
            add("menu");
        }}, context, MenuHelpers.ACTION_PROMPT);
        menu.put("count", countItem);
        menu.put("search", searchItem);
        menu.put("edit", editRecordItem);
        menu.put("delete", deleteRecordItem);
        menu.put("[number]", selectRecordItem);
        menu.put("record", recordItem);
        menu.put("exit", exitItem);
        menu.put("add", addItem);
        menu.put("menu", menuItem);
        menu.put("back", backItem);
        menu.put("list", listItem);
        return menu;
    }
}

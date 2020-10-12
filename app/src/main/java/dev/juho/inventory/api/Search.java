package dev.juho.inventory.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search {

    public static List<Item> filter(List<Item> itemList, String search, ItemOrder itemOrder) {
        if (itemList.isEmpty() || (search == null || search.isEmpty())) {
            sortItems(itemList, itemOrder);
            return itemList;
        }

        String searchUpperCase = search.toUpperCase();
        List<Item> filteredItems;

        if (searchUpperCase.contains(":")) {
            filteredItems = typeSearch(itemList, searchUpperCase);
        } else {
            filteredItems = nameSearch(itemList, searchUpperCase);
        }

        sortItems(filteredItems, itemOrder);
        return filteredItems;
    }

    private static void sortItems(List<Item> itemList, ItemOrder itemOrder) {
        switch (itemOrder) {
            case LAST_UPDATED:
                itemList.sort((a, b) -> Long.compare(a.getLastUpdate(), b.getLastUpdate()));
                Collections.reverse(itemList);
                break;

            case NAME:
                itemList.sort((a, b) -> a.getName().compareTo(b.getName()));
                break;
        }
    }

    private static List<Item> nameSearch(List<Item> itemList, String searchUpperCase) {
        List<Item> matchedItems = new ArrayList<>();

        for (Item item : itemList) {
            if (item.getName().toUpperCase().startsWith(searchUpperCase)) {
                matchedItems.add(item);
            }
        }

        return matchedItems;
    }

    private static List<Item> typeSearch(List<Item> itemList, String searchUpperCase) {
        int colonIndex = searchUpperCase.indexOf(":");
        if (colonIndex == -1) {
            return itemList;
        }

        String searchType = searchUpperCase.substring(0, colonIndex).toUpperCase();
        String search = searchUpperCase.substring(colonIndex + 1).toUpperCase();

        switch (searchType) {
            case "TAG":
                return tagSearch(itemList, search);

            default:
                return itemList;
        }
    }

    private static List<Item> tagSearch(List<Item> itemList, String tagUpperCase) {
        List<Item> matchedItems = new ArrayList<>();

        for (Item item : itemList) {
            for (int i = 0; i < item.getTags().length(); i++) {
                String tag = item.getTags().optString(i, "^^^^^^").toUpperCase();
                if (tag.startsWith(tagUpperCase)) {
                    matchedItems.add(item);
                    break;
                }
            }
        }

        return matchedItems;
    }

}

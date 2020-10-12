package dev.juho.inventory.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search {

    public static List<Item> filter(List<Item> itemList, String search, ItemOrder itemOrder) {
        if (itemList.isEmpty() || (search == null || search.isEmpty())) {
            return itemList;
        }

        String searchUpperCase = search.toUpperCase();
        List<Item> filteredItems = nameSearch(itemList, searchUpperCase);
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

}

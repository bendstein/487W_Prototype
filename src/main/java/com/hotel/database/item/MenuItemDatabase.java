package com.hotel.database.item;

import com.hotel.database.jpa.JpaMenuItemRepository;
import com.hotel.model.item.Allergen;
import com.hotel.model.item.MenuItem;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class MenuItemDatabase {

    /**
     * CRUD repository for menu items
     */
    private final JpaMenuItemRepository repo;

    public MenuItemDatabase(JpaMenuItemRepository repo) {
        this.repo = repo;
    }

    /**
     * @return A list of all items in the database
     */
    public Collection<MenuItem> getDatabase(Sort s) {
        return repo.getAll(s);
    }

    /**
     * Add the given item to the database, if it isn't already present
     * @param item The item to add to the database
     */
    public void add(MenuItem item) {
        if(item != null) repo.save(item);
    }

    /**
     * Remove the given item from the database
     * @param item The item to remove from the database
     */
    public void remove(MenuItem item) {
        repo.delete(item);
    }

    /**
     * Remove the given item from the database
     * @param id The id of the item to remove from the database
     */
    public void remove(long id) {
        repo.deleteById(id);
    }

    /**
     * Edit the parameters of the item with the given id
     * @param id The id of the item to edit
     * @param new_name The item's new name
     * @param new_allergens The item's new allergens
     * @param new_price The item's new price
     * @param new_description The item's new description
     */
    public void edit(@NotNull long id, String new_name, Set<String> new_allergens, double new_price, String new_description) {

        MenuItem item = find(id);

        if(item == null) return;

        item.setName((new_name == null || new_name.isEmpty())? MenuItem.DEFAULT_NAME : new_name);

        item.setAllergens(new HashSet<>());
        for(String s : new_allergens) item.getAllergens().add(new Allergen(s));

        item.setPrice(new_price < MenuItem.MINIMUM_PRICE? new BigDecimal(MenuItem.MINIMUM_PRICE) : new BigDecimal(new_price));
        item.setDescription((new_description == null || new_description.isEmpty())? MenuItem.DEFAULT_DESCRIPTION : new_description);

        repo.save(item);

    }

    public void edit(@NotNull long id, String new_name, Set<String> new_allergens, double new_price, String new_description, String imgName) {

        MenuItem item = find(id);

        if(item == null) return;

        item.setName((new_name == null || new_name.isEmpty())? MenuItem.DEFAULT_NAME : new_name);

        item.setAllergens(new HashSet<>());
        for(String s : new_allergens) item.getAllergens().add(new Allergen(s));

        item.setPrice(new_price < MenuItem.MINIMUM_PRICE? new BigDecimal(MenuItem.MINIMUM_PRICE) : new BigDecimal(new_price));
        item.setDescription((new_description == null || new_description.isEmpty())? MenuItem.DEFAULT_DESCRIPTION : new_description);

        if(imgName != null && imgName.length() > 0){
            item.setImage(imgName);
        }

        repo.save(item);

    }

    /**
     * @param id The id of the item to retrieve
     * @return the item with the given id, or null if no such item exists
     */
    public MenuItem find(long id) {

        Optional<MenuItem> item = repo.findById(id);

        return item.isPresent()? item.get() : null;

    }

    /**
     * @param name The name of the item(s) to retrieve
     * @return a list of items with the given name
     */
    public ArrayList<MenuItem> findName(String name) {

        ArrayList<MenuItem> items = new ArrayList<>();

        repo.findAll().forEach(i -> {
            if(i.getName().equals(name) || i.getName().contains(name)) items.add(i);
        });

        return items;

    }

    /**
     * @param id The id to check the existence of
     * @return true if the id exists in the database
     */
    public boolean containsId(long id) {
        return repo.existsById(id);
    }
}

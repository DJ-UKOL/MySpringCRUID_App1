package org.example.repositories;

import org.example.models.Item;
import org.example.models.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository {

    List<Item> findByItemName(String itemName);

    // person.getItems()
    List<Item> findByOwner(Person owner);
}

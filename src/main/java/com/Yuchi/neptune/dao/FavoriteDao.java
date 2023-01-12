package com.Yuchi.neptune.dao;

import com.Yuchi.neptune.entity.db.Item;
import com.Yuchi.neptune.entity.db.ItemType;
import com.Yuchi.neptune.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FavoriteDao {
    @Autowired
    private SessionFactory sessionFactory; // from ApplicationConfig.java

    //Insert a favorite record to the DB
    public void setFavoriteItem(String userId, Item item) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            user.getItemSet().add(item);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    // Remove a favorite record from the DB
    public void unsetFavoriteItem(String userId, String itemId) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    public Set<Item> getFavoriteItems(String userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId).getItemSet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new HashSet<>();
    }

    public Map<String, List<String>> getFavoriteGameIds(Set<Item> items) {
        Map<String, List<String>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }
        for (Item item : items) {
            itemMap.get(item.getType().toString()).add(item.getGameId());
        }
        return itemMap;
    }


}

package com.serli.oracle.of.bacon.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Arrays;
import java.util.Optional;

/**
 * The Mongo database store several informations (biography, img, etc.) on the actors.
 * This class enables to search for a specific actor and get these informations
 */
public class MongoDbRepository {

    private final MongoClient mongoClient;

    public MongoDbRepository() {
        mongoClient = new MongoClient("localhost", 27017);
    }

    /**
     * Get the informations on an actor which has a given name
     * @param name name given by the user, can be lower cased or upper cased
     * @return informations on the actor
     */
    public Optional<Document> getActorByName(String name) {

        name = name.replaceAll("\\s\\(.*?\\)","");
        if (name.contains(",")) {
            name = String.format("%s %s", name.split(", ")[1], name.split(", ")[0]);
        }
        System.out.println(name);
        MongoCursor<Document> cursor = mongoClient.getDatabase("workshop")
                .getCollection("actors")
                .find(Filters.eq("name", name)).iterator();
        if (cursor.hasNext()){
            return Optional.of(cursor.next());
        }
        return Optional.empty();
    }
}

package com.serli.oracle.of.bacon.repository;


import com.serli.oracle.of.bacon.utils.Json;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Path;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Configure an access to a Neo4J Graph repository, and retrieve informations on the link between actors
 */
public class Neo4JRepository {
    private final Driver driver;

    public Neo4JRepository() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "workshop"));
    }

    /**
     * <i>The object of the game is to start with any actor or actress who has been in a movie and connect them to Kevin Bacon
     * in the smallest number of links possible. Two people are linked if they've been in a movie together. </i>
     * @param actorName name of the actor who must be linked to Kevin Bacon
     * @return graph of films and actors linked the actor to Kevin Bacon
     */
    public List<?> getConnectionsToKevinBacon(String actorName) {
        Session session = driver.session();

        StatementResult query = session.run("MATCH r = shortestPath(" +
                "(kevin:Actor {name:'Bacon, Kevin (I)'})" +
                "-[*]-" +
                "(parametrizedActor:Actor {name:{name}}))" +
                " RETURN r;",
                parameters("name", actorName));

        List<GraphItem> result = new ArrayList<>();

        while (query.hasNext()) {
            Record record = query.next();
            Path path = record.get("r").asPath();
            path.nodes().forEach(x -> {
                result.add(new GraphNode(x.id(),
                        x.asMap().values().iterator().next().toString(),
                        x.labels().iterator().next()));
            });
            path.relationships().forEach(x -> {
                result.add(
                    new GraphEdge(
                        x.id(),
                        x.startNodeId(),
                        x.endNodeId(),
                        x.type()
                    )
                );
            });
        }
        return result;
    }

    private static abstract class GraphItem {
        public final long id;

        private GraphItem(long id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GraphItem graphItem = (GraphItem) o;

            return id == graphItem.id;
        }

        @Override
        public int hashCode() {
            return (int) (id ^ (id >>> 32));
        }
    }

    private static class GraphNode extends GraphItem {
        public final String type;
        public final String value;

        public GraphNode(long id, String value, String type) {
            super(id);
            this.value = value;
            this.type = type;
        }

        @Override
        public String toString() {
            return String.format(
                    Json.obj()
                            .add("data", Json.obj()
                                    .add("id", id)
                                    .add("type", type)
                                    .add("value", value))
                            .toString(),
                id, type,value);
        }
    }

    private static class GraphEdge extends GraphItem {
        public final long source;
        public final long target;
        public final String value;

        public GraphEdge(long id, long source, long target, String value) {
            super(id);
            this.source = source;
            this.target = target;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format(
                    Json.obj()
                            .add("data", Json.obj()
                                    .add("id", id)
                                    .add("source", source)
                                    .add("target", target)
                                    .add("value", value))
                            .toString(),
                    id, source, target , value);
        }
    }
}

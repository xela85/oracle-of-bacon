# Oracle of Bacon
This application is an Oracle of Bacon implementation based on NoSQL data stores :
* ElasticSearch (http) - localhost:9200
* Redis - localhost:6379
* Mongo - localhost:27017
* Neo4J (bolt) - locahost:7687


# Students

GIMENEZ Nathanaël (Nat-Faeeria)<br/>
LEBRUN Alexandre (xela85)s

#Steps reached :
  - Redis last 10 history
  - Neo4J Oracle of Bacon
  - ElasticSearch completion and suggest
  - MongoDB search for actors informations
  - Vue.js display of actors info when a node is clicked

To build :
```
./gradlew build
```

# Import data

### ElasticSearch
Please use the CompletionLoader class and give the location of your actors file to its main method.
As it is CPU heavy import process may take several minutes

### Neo4J
To import actors and movies data into Neo4J, please use the following command :

```
bin/neo4j-import --nodes:Movie movies.csv --nodes:Actor actors.csv --relationships roles.csv --into <your neo4j database path>
```

We also added an index on :Actor

###

To Run, execute class *com.serli.oracle.of.bacon.Application*.

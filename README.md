# README

## start local redis redis 
> docker run --rm -p 6379:6379 --name redis redis

## start application

> ./mvnw spring-boot:run

### add datasource
> curl -H "client: client1" -H "Content-Type: application/json" \
--request POST \
--data '{"name":"datasource1","type":"JDBC","properties":{"hostname":"localhost", "port":5432,"username":"postgres","password":"postgres"}}' \
localhost:8080/datasources/

### get datasource
> curl -H "client: client1" -H "Content-Type: application/json" localhost:8080/datasources/datasource1

### update datasource
> curl -H "client: client1" -H "Content-Type: application/json" \
--request PUT \
--data '{"name":"datasource1","type":"JDBC","properties":{"hostname":"localhost", "port":5432,"username":"postgres","password":"postgres","databaseName":"demo"}}' \
localhost:8080/datasources/

### delete datasource
> curl -H "client: client1" -H "Content-Type: application/json" --request DELETE localhost:8080/datasources/datasource1

## References
### Infinitest
see http://infinitest.github.io

## Currently not implemented
- validation - required, type, ... 
- password handling - passwords are not masked ATM
- security - authentication / authorization - eg. integrate with OAuth2.0/OIDC (eg. Keycloak / Okta)
- other types of datasource than JDBC (NoSQL)
- loading drivers not available in applicaton (possiblity to define own driver alias - upload jar file with jdbc driver, define url template and properties)
- use better suited NOSql database then Redis, implement "classical" SQL repository backend 


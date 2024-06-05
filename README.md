# Getting Started

## Initial configurations
setup your database credentials via environment variables or overwrite the application.yml default values
The environment variables as follows
* MYSQL_DB_SERVER
* MYSQL_DB_PORT
* MYSQL_DB_USER
* MYSQL_DB_PASS

## Default basic auth credentials
The application setup with two user records with admin and user roles. All the API endpoints are secured. To access the api via rest client the basic auth needs to be setup in the authorization
default user credentials are
* #### userName: user , password: user
* #### userName: admin , password: admin

## Database
The SQL script available under src/main/resources/data-sql directory  


# MusicTracker 
MusicTracker is a basic CRUD website made using <b>Spring</b> and <b>Thymeleaf</b>. The project is made by <b>Oleksandr Shmelov</b>, student of <i>Karel de Grote Hogeschool</i>.

## About
This project was created for a college assignment. We had some requirements that we had to fulfill, thus code has many implementation of the same concept.  
The project reflects what we have learned in the academic program.

#### Entities relationships
There are 3 main entities in the project: <b>Artist</b>, <b>Song</b> and <b>Tour</b>.  
The structure is as follows:
* <b>Artist</b> can have many <b>Songs</b> and many <b>Tours</b>
* <b>Song</b> can be made by many <b>Artists</b>. (Many-to-Many)
* <b>Tour</b> can be performed by one <b>Artist</b>. (One-to-Many)

## Setup

<b>Important remark: <u>server port is 8090!</u></b> NOT 8080.

This application has an option to work with remote database located on KdG's servers (PostgreSQL). However, to be able to establish connection with KdG server you will need to be connected to the KdG local network. This can be achieved using KdG VPN.
* In order to use remote DB, change profile of the application to "remote_db".   
(See "Profiles" paragraph for details.)

<br>
Another option is to use file-based H2 database. Application will be able to run it on every machine, but any changes to the data will be lost after restarting the application.

* In order to use local DB, change profile of the application to "local_db".  
(See "Profiles" paragraph for details.)

### Profiles
There are a couple of pre-made configuration profiles, in case you want to make some adjustments to the setup of the application. Edit "<u>spring.profiles.active</u>" attribute in <u>application.properties</u> file to enable them. You will need <u>two profiles</u> to be active at the same time, one for the database location and one for enabling certain repository implementation.

<b>Database location profiles:</b>
* Use "remote_db" profile if you want to use cloud based DB. <b>(Application won't start without KdG network)</b>
* Use "local_db" in order to switch to the locally based H2 database. (The database if file-based, thus watch out for two client connections. The scenario when two "clients" try to use the same DB will result in unexpected exceptions.)
  
<b>Repositories implementation profiles:</b>
* Use "jpa" for enabling all JPA repositories.
* Use "entityManager" for enabling all EntityManager repositories.
* Use "jdbcTemplate" for enabling all JDBCTemplate repositories.
* Use "local_data" for enabling "dummy", Collections-based repositories.


## Structure
Application implements <b>three-layered architecture</b>. This setup helps the app run smoothly by dividing its tasks into three main parts. Each of these parts has its own job, making it easier to debug and manage the app over time.

## Technologies used
* <b>Spring Boot 3.1.4</b> (Spring 6) is used as a main framework of development.
* <b>Gradle</b> was used as a build and deployment tool.
* <b>Java 17</b> was used as a language of development.
* <b>Kotlin</b> was used as a language of Gradle configuration.
* <b>Thymeleaf</b> was used as a template engine.
* Communication with a database is facilitated through the utilization of various repository implementations such as:
  * <b>Java Persistence API</b> (JpaRepositories) implementation.  
  * Basic <b>EntityManager</b> implementation.  
  * <b>JDBCTemplate</b> implementation.  
* <b>PostgreSQL</b> was used as a production SQL dialect.
* <b>H2</b> database as a development assistance tool (locally-based database).
* <b>HTML</b> and <b>CSS</b> were used for the front-end development.
* <b>Bootstrap</b> framework was used to facilitate front-end styling.
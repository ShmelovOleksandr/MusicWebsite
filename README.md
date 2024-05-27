# MusicTracker 
MusicTracker is a basic CRUD website made using <b>Spring</b> and <b>Thymeleaf</b>. The project is made by <b>Oleksandr Shmelov</b>, student of <i>Karel de Grote Hogeschool</i>.

## Author
<b>Oleksandr Shmelov</b> <i>(oleksandr.shmelov@student.kdg.be)</i> 
<p><i> Student ID:</i> <b>0159900-44</b></p>
<p><i> Academic year:</i> <b>2023-2024</b></p>
<p><i> Group:</i> <b>ACS-202</b></p>

## About
This project was created for a college assignment.
It reflects what we have learned in the academic program.

### Entities relationships
There are 3 main entities in the project: <b>Artist</b>, <b>Song</b> and <b>Tour</b>.  
The structure is as follows:
* <b>Artist</b> can have many <b>Songs</b> and many <b>Tours</b>
* <b>Song</b> can be made by many <b>Artists</b>. (Many-to-Many)
* <b>Tour</b> can be performed by one <b>Artist</b>. (One-to-Many)
  ![](.gitlab/prog5_relations.svg)
## Build/Run

### Requires:
* Java 17 (or higher)
* Nodejs
* Npm
* Docker

### To run:
```shell
docker-compose up -d
./gradlew build
./gradlew bootRun
```

#### Server port:<b> 8080</b>. 
Access [here](http://localhost:8080).

### Profiles

* <b>test</b> - Changes database to the "testing" one. Used only to run tests.

### Tests
To run all tests execute the following:
```shell
./gradlew test
```

## Week 2
```http request
###
# Fetching all artists - OK 
# @no-redirect
GET http://localhost:8080/api/artists
Accept: application/json

Response:
HTTP/1.1 200 
  
Headers:
Content-Type: application/json

Body:
[
  {
    "id": 1,
    "name": "Taylor Swift",
    "birthDate": "1989-12-13",
    "listeners": 100734996
  },
  {
    "id": 2,
    "name": "Ed Sheeran",
    "birthDate": "1991-02-17",
    "listeners": 74889692
  },
  {
    "id": 3,
    "name": "Beyonce",
    "birthDate": "1981-09-04",
    "listeners": 49366942
  },
  {
    "id": 4,
    "name": "Elvis Presley",
    "birthDate": "1935-01-08",
    "listeners": 17850458
  }
]


###
# Fetching one artist - Not Found
# @no-redirect
GET http://localhost:8080/api/artists/6
Accept: application/json

Response:
HTTP/1.1 404 
  
Headers:
Content-Type: application/problem+json

Body:
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "No artists with given id have been found.",
  "instance": "/api/artists/6"
}

###
#Fetching one artist - OK
# @no-redirect
GET http://localhost:8080/api/artists/4
Accept: application/json

Response:
HTTP/1.1 200 

Headers:
Content-Type: application/json

Body:
{
  "id": 4,
  "name": "Elvis Presley",
  "birthDate": "1935-01-08",
  "listeners": 17850458
}
```

## Week 3 (TODO)
```http request
###
# Creating an artist - CREATED
POST http://localhost:8080/api/artists
Accept: application/json
Content-Type: application/json

{
  "name": "test name",
  "birthDate": "2000-01-01",
  "listeners": 1000
}

Response:
HTTP/1.1 201 

  Headers:
  Content-Type: application/json
  
  Body:
  {
    "id": 5
    "name": "test name",
    "birthDate": "2000-01-01",
    "listeners": 1000
  }

###
# Patch an artist - OK
PATCH http://localhost:8080/api/artists/1
Accept: application/json
Content-Type: application/json

{
  "name": "Taylor",
  "birthDate": "2001-01-01",
  "listeners": 1000000
}

Response:
{
  "id": 1
  "name": "Taylor",
  "birthDate": "2000-01-01",
  "listeners": 1000000
}

### Get all artists (JSON)
# @no-redirect
GET http://localhost:8080/api/artists
Accept: application/json

Response:
HTTP/1.1 200 
  
  Headers:
  Content-Type: application/json
  
  Body:
  [
    {
      "id": 1,
      "name": "Taylor Swift",
      "birthDate": "1989-12-13",
      "listeners": 100734996
    },
    {
      "id": 2,
      "name": "Ed Sheeran",
      "birthDate": "1991-02-17",
      "listeners": 74889692
    },
    {
      "id": 3,
      "name": "Beyonce",
      "birthDate": "1981-09-04",
      "listeners": 49366942
    },
    {
      "id": 4,
      "name": "Elvis Presley",
      "birthDate": "1935-01-08",
      "listeners": 17850458
    }
  ]

### Get all artists (XML)
# @no-redirect
GET http://localhost:8080/api/artists
Accept: application/xml

Response:
HTTP/1.1 200 
  
  Headers:
  Content-Type: application/xml
  
  Body:
  <List>
      <item>
          <id>1</id>
          <name>Taylor Swift</name>
          <birthDate>1989-12-13</birthDate>
          <listeners>100734996</listeners>
      </item>
      <item>
          <id>2</id>
          <name>Ed Sheeran</name>
          <birthDate>1991-02-17</birthDate>
          <listeners>74889692</listeners>
      </item>
      <item>
          <id>3</id>
          <name>Beyonce</name>
          <birthDate>1981-09-04</birthDate>
          <listeners>49366942</listeners>
      </item>
      <item>
          <id>4</id>
          <name>Elvis Presley</name>
          <birthDate>1935-01-08</birthDate>
          <listeners>17850458</listeners>
      </item>
  </List>

```

## Week 4
#### Users:
* Username: <b>taylor</b> - Password: <b>1234</b>
* Username: <b>ed</b> - Password: <b>1234</b>
* Username: <b>beyonce</b> - Password: <b>1234</b>
* Username: <b>elvis</b> - Password: <b>1234</b>

#### Publicly accessed pages:

* [/](http://localhost:8080/)
* [/artists](http://localhost:8080/artists)
* [/artists/1](http://localhost:8080/artists/1)
* [/songs](http://localhost:8080/songs)
* [/songs/1](http://localhost:8080/songs/1)
* [/tours](http://localhost:8080/tours)
* [/tours/1](http://localhost:8080/tours/1)

#### Authorization only pages:
* [/sessionHistory](http://localhost:8080/sessionHistory)
* [/artists/new](http://localhost:8080/artists/new) (Admin only)
* [/songs/new](http://localhost:8080/songs/new)
* [/tours/new](http://localhost:8080/tours/new)



## Week 5
#### Users:
* Username: <b>taylor</b> - Password: <b>1234</b> (Roles: USER, ARTIST)
* Username: <b>ed</b> - Password: <b>1234</b> (Roles: USER, ARTIST)
* Username: <b>beyonce</b> - Password: <b>1234</b> (Roles: USER, ARTIST)
* Username: <b>elvis</b> - Password: <b>1234</b> (Roles: USER, ARTIST)
##### New users:
* Username: <b>admin</b> - Password: <b>1234</b> (Roles: USER, ADMIN)
* Username: <b>user</b> - Password: <b>1234</b> (Roles: USER)

#### Roles:
* UNAUTHENTICATED - Can access certain public pages. Can't edit/create/delete anything.
* USER - Can access all public pages. Can't edit/create/delete anything.
* ARTIST - Can edit and delete their own artist profile. Can create/edit/delete songs and tours.
* ADMIN - Can access everything and edit/create/delete anything. 


## Week 7
Code coverage:
![img.png](.gitlab/code_coverage.png))

#### MVC tests:
* <b>SongsControllerTest</b> (be.kdg.programming5.musicwebsite.controller.SongsControllerTest)

#### API tests:
* <b>RestArtistControllerTest</b> (be.kdg.programming5.musicwebsite.api.controller.RestArtistControllerTest)

#### Role verification tests:
* <b>SongsControllerTest</b> (be.kdg.programming5.musicwebsite.controller.SongsControllerTest)
* <b>RestArtistControllerTest</b> (be.kdg.programming5.musicwebsite.api.controller.RestArtistControllerTest)

## Week 8

#### Mocking tests
* <b>RestArtistControllerUnitTest</b> (be.kdg.programming5.musicwebsite.api.controller.RestArtistControllerUnitTest)
* <b>ArtistServiceImpUnitTest</b> (be.kdg.programming5.musicwebsite.service.ArtistServiceImpUnitTest)

#### 'verify' tests
* <b>RestArtistControllerUnitTest</b> (be.kdg.programming5.musicwebsite.api.controller.RestArtistControllerUnitTest)

## Week 11 

### Bootstrap icons:
Icons can be found [here](http://localhost:8080/artists/2). 

On this page you will see an "Edit" button with a "pen" icon and a "Delete" button with a "bin" icon.

<b>IMPORTANT: You must be logged in as "ed" user</b> (You can modify/delete only your own artist profile)

#### Source file: 
* artistsDetails.html (src/main/resources/templates/view/artists/artistDetails.html)

### Form with custom client-side validation:

Form can be found [here](http://localhost:8080/artists/2/editor).

This form has validation of all fields:
* name: Min length = 3; Max length = 18; Required 
* birthDate: Should be in the past; Required 
* Listeners: Should not be negative; Required

<b>IMPORTANT: You must be logged in as "ed" user</b> (You can modify/delete only your own artist profile)

#### Source file:
* artistEditor.js (src/main/js/artistEditor.js)

### JavaScript dependencies:

* #### animejs: 
  Animation can be found [here](http://localhost:8080/artists/2).

  Fade in/out animations were added to the "Show songs" and "Show tours" buttons.

  ##### Source file:
  * artistDetails.js (src/main/js/artistDetails.js)
  
* #### axios: 
  Package is used to enhance fetch calls. (No representation on the website itself)

  ##### Source files:
  * artistCreator.js (src/main/js/artistCreator.js)
  * artistDetails.js (src/main/js/artistDetails.js)
  * artistEditor.js (src/main/js/artistEditor.js)
  * artists.js (src/main/js/artists.js)
  * registration.js (src/main/js/registration.js)


## Week 12
#### Pipeline results:
![](.gitlab/gitlab_pipeline_result.png)
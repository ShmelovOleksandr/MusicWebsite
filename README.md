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
## Setup

#### Requires:
* Java 17 (or higher)
* Nodejs
* Npm
* Docker

Server port:<b> 8080</b>.


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
```

### Week 4
#### Users:
* Username: <b>taylor</b> - Password: <b>1234</b>
* Username: <b>ed</b> - Password: <b>1234</b>
* Username: <b>beyonce</b> - Password: <b>1234</b>
* Username: <b>elvis</b> - Password: <b>1234</b>

#### Publicly accessed urls:
"<b>/</b>", "<b>/artists</b>", "<b>/songs</b>", "<b>/tours</b>", "<b>/artists/{id}</b>", "<b>/songs/{id}</b>", "<b>/tours/{id}</b>"


### Week 5
#### Users:
* Username: <b>admin</b> - Password: <b>1234</b> (Role: ADMIN)
* Username: <b>user</b> - Password: <b>1234</b> (Role: AUTHORIZED)

#### Roles:
* ADMIN - Can access everything and edit anything. 
* AUTHORIZED - Can edit and delete artist.

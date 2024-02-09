-- CREATE DATABASE MusicWebsite;

CREATE TABLE Artist (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(30) NOT NULL,
    birthDate date,
    listeners numeric CHECK ( listeners >= 0 ) NOT NULL
);

CREATE TABLE Song (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(50) NOT NULL,
    length numeric NOT NULL,
    genre varchar(20) NOT NULL
);

CREATE TABLE Artist_Song (
    song_id int REFERENCES Song(id) ON DELETE CASCADE,
    artist_id int REFERENCES Artist(id) ON DELETE CASCADE,
    PRIMARY KEY ( song_id, artist_id )
);

CREATE TABLE Tour (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    location varchar(50) NOT NULL,
    date date  NOT NULL,
    price decimal CHECK ( price >= 0 ) NOT NULL,
    artist_id int REFERENCES Artist(id) ON DELETE CASCADE
);

INSERT INTO Artist(name, birthDate, listeners) VALUES ('Taylor Swift', to_date('13/12/1989', 'DD/MM/YYYY'), 100734996);
INSERT INTO Artist(name, birthDate, listeners) VALUES ('Ed Sheeran', to_date('17/2/1991', 'DD/MM/YYYY'), 74889692);
INSERT INTO Artist(name, birthDate, listeners) VALUES ('Beyonce', to_date('4/9/1981', 'DD/MM/YYYY'), 49366942);
INSERT INTO Artist(name, birthDate, listeners) VALUES ('Elvis Presley', to_date('8/1/1935', 'DD/MM/YYYY'), 17850458);
INSERT INTO Song(name, length, genre) VALUES ('Run', 240, 'POP');
INSERT INTO Song(name, length, genre) VALUES ('Halo', 261, 'POP');
INSERT INTO Tour(location, date, price, artist_id) VALUES ('Las Vegas, USA', to_date('23/10/2023', 'DD/MM/YYYY'), 39.0, 2);
INSERT INTO Artist_Song VALUES (1, 1);
INSERT INTO Artist_Song VALUES (1, 2);
INSERT INTO Artist_Song VALUES (2, 3);

INSERT INTO Artist(name, birthDate, listeners) VALUES ('Taylor Swift', DATE '1989-12-13', 100734996),
                                                      ('Ed Sheeran', DATE'1991-2-17', 74889692),
                                                      ('Beyonce', DATE'1981-9-4', 49366942),
                                                      ('Elvis Presley', DATE'1935-1-8', 17850458);

INSERT INTO Song(name, length, genre) VALUES ('Run', 240, 'POP'),
                                             ('Shape of You', 233, 'POP'),
                                             ('Halo', 261, 'POP'),
                                             ('Can''t Help Falling In Love', 182, 'ROCK');

INSERT INTO Tour(location, date, price, artist_id) VALUES ('Tokyo, Japan', DATE'2024-2-7', 125.86, 1),
                                                          ('Las Vegas, USA', DATE'2023-10-23', 39.0, 3),
                                                          ('Kansas City, USA', DATE'2023-10-1', 325.0, 2),
                                                          ('Sydney, Australia', DATE'2024-2-23', 79.90, 1);

INSERT INTO Artist_Song VALUES (1, 1),
                               (1, 2),
                               (2, 2),
                               (3, 3),
                               (4, 4);
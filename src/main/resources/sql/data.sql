INSERT INTO website_user(username, password, is_admin) VALUES ('taylor', '$2a$12$SAgySMF8xPVh/HPgwOkmj.amBtsVKnKydezdZXk.4Ud.AIO1Nx1ae', false),
                                                              ('admin', '$2a$12$SAgySMF8xPVh/HPgwOkmj.amBtsVKnKydezdZXk.4Ud.AIO1Nx1ae', true),
                                                              ('ed', '$2a$12$SAgySMF8xPVh/HPgwOkmj.amBtsVKnKydezdZXk.4Ud.AIO1Nx1ae', false),
                                                              ('beyonce', '$2a$12$SAgySMF8xPVh/HPgwOkmj.amBtsVKnKydezdZXk.4Ud.AIO1Nx1ae', false),
                                                              ('elvis', '$2a$12$SAgySMF8xPVh/HPgwOkmj.amBtsVKnKydezdZXk.4Ud.AIO1Nx1ae', false),
                                                              ('user', '$2a$12$SAgySMF8xPVh/HPgwOkmj.amBtsVKnKydezdZXk.4Ud.AIO1Nx1ae', false);


INSERT INTO Artist(name, birthDate, listeners, website_user_id) VALUES ('Taylor Swift', DATE '1989-12-13', 100734996, 1),
                                                                       ('Ed Sheeran', DATE'1991-2-17', 74889692, 2),
                                                                       ('Beyonce', DATE'1981-9-4', 49366942, 3),
                                                                       ('Elvis Presley', DATE'1935-1-8', 17850458, 4);

INSERT INTO Song(name, length, genre) VALUES ('Run', 240, 'POP'),
                                             ('Shape of You', 233, 'POP'),
                                             ('Halo', 261, 'POP'),
                                             ('Can''t Help Falling In Love', 182, 'ROCK');

INSERT INTO Artist_Song VALUES (1, 1),
                               (1, 2),
                               (2, 2),
                               (3, 3),
                               (4, 4);

INSERT INTO Tour(location, date, price, artist_id) VALUES ('Tokyo, Japan', DATE'2024-2-7', 125.86, 1),
                                                          ('Las Vegas, USA', DATE'2023-10-23', 39.0, 3),
                                                          ('Kansas City, USA', DATE'2023-10-1', 325.0, 2),
                                                          ('Sydney, Australia', DATE'2024-2-23', 79.90, 1);

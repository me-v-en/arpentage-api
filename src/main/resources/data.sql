DROP TABLE IF EXISTS loan;
-- DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS member;


CREATE TABLE member
(
    id            INT AUTO_INCREMENT  PRIMARY KEY,
    pseudo        VARCHAR(250) NOT NULL,
    mail          VARCHAR(250),
    creation_date TIMESTAMP    NOT NULL
);

CREATE TABLE book
(
    id            INT AUTO_INCREMENT  PRIMARY KEY,
    owner_id      INT          NOT NULL,
    title         VARCHAR(250) NOT NULL,
    author        VARCHAR(250) NOT NULL,
    description   TEXT,
    creation_date TIMESTAMP    NOT NULL

);

CREATE TABLE loan
(
    id        INT AUTO_INCREMENT  PRIMARY KEY,
    borrower_id INT       NOT NULL,
    book_id   INT       NOT NULL,
    lend_date TIMESTAMP NOT NULL,
    ongoing BOOLEAN NOT NULL
);

-- CREATE TABLE comment
-- (
--     id            INT AUTO_INCREMENT  PRIMARY KEY,
--     id_member     INT       NOT NULL,
--     book_id       INT       NOT NULL,
--     description   TEXT,
--     note          INT,
--     creation_date TIMESTAMP NOT NULL
-- );


INSERT INTO member (pseudo, mail, creation_date)
VALUES ('Laurent', 'laurentgina@mail.com', '2023-12-13 12:13:00'),
       ('Sophie', 'sophiefoncek@mail.com', '2023-12-13 12:13:00'),
       ('Agathe', 'agathefeeling@mail.com', '2023-12-13 12:13:00');


INSERT INTO book (owner_id, title, author, description, creation_date)
VALUES (1, 'L''art de la guerre', 'Sun Tzu', 'Un livre sur la guerre, pour ou contre ? L''auteur sait pas',
        '2023-12-13 12:13:00'),
       (2, 'La fricadelle pour les nuls', 'Etchebest', 'L''authentique recette enfin révélé.', '2023-12-13 12:13:00'),
       (3, 'Oui-oui à la ZAD', 'Lénard Schiste',
        'Oui-oui vit en communauté et remet en question la propriété privée avec ses petits camarades.',
        '2023-12-13 12:13:00');

INSERT INTO loan ( borrower_id, book_id, lend_date,ongoing)
VALUES ( 2, 3, '2023-12-13 12:13:00', true);

-- INSERT INTO comment (id_member, book_id, description, note, creation_date)
-- VALUES (2, 3, 'ZAD partout', 5, '2023-12-13 12:13:00');


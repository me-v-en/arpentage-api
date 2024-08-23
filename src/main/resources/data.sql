DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS lease;
DROP TABLE IF EXISTS comment;


CREATE TABLE member
(
    id     INT AUTO_INCREMENT  PRIMARY KEY,
    pseudo VARCHAR(250) NOT NULL,
    mail   VARCHAR(250)
);

CREATE TABLE book
(
    id          INT AUTO_INCREMENT  PRIMARY KEY,
    ownerId     INT          NOT NULL,
    title       VARCHAR(250) NOT NULL,
    author      VARCHAR(250) NOT NULL,
    description TEXT
);

CREATE TABLE lease
(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    idLender INT  NOT NULL,
    idLendee INT  NOT NULL,
    idBook   INT  NOT NULL,
    lendDate DATE NOT NULL
);

CREATE TABLE comment
(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    idMember      INT  NOT NULL,
    idBook      INT  NOT NULL,
    description TEXT,
    note        INT,
    timestamp TIMESTAMP NOT NULL
);


INSERT INTO member (pseudo, mail)
VALUES ('Laurent', 'laurentgina@mail.com'),
       ('Sophie', 'sophiefoncek@mail.com'),
       ('Agathe', 'agathefeeling@mail.com');


INSERT INTO book (ownerId, title, author, description)
VALUES (1, 'L''art de la guerre', 'Sun Tzu', 'Un livre sur la guerre, pour ou contre ? L''auteur sait pas');
VALUES (2, 'La fricadelle pour les nuls', 'Etchebest', 'L''authentique recette enfin révélé.');
VALUES (3, 'Oui-oui à la ZAD', 'Lénard SCHISTE', 'Oui-oui vit en communauté et remet en question la propriété privée avec ses petits camarades.');

INSERT INTO lease (idLender, idLendee, idBook, lendDate)
VALUES (1,2,3, '2023-12-13');

INSERT INTO comment (idMember, idBook, description, note, timestamp)
VALUES (2,3,'ZAD partout',5, '2023-12-13 13:12:00');



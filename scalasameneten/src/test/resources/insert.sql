--select * from VOORKEUR
INSERT INTO VOORKEUR(VOORKEUR_NAAM)
VALUES	('vlees'), ('vis'), ('kip'),
          ('soep'), ('sushi'), ('pizza'),
          ('wok'), ('plantaardig'), ('mexicaans')

--select * from VOEDINGSRESTRICTIE
    INSERT INTO VOEDINGSRESTRICTIE (RESTRICTIE_NAAM,TYPE)
VALUES	('noten','allergie'),
    ('varkensvlees','geloof'),
    ('vlees','dieet')


--select * from GEBRUIKER
INSERT INTO GEBRUIKER (GEBRUIKERSNAAM)
VALUES	('user1'),
    ('user2'),
    ('user3')

--select * from GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
INSERT INTO GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (RESTRICTIE_NAAM,TYPE,GEBRUIKER_ID)
VALUES	('noten','allergie',1),
    ('varkensvlees','geloof',1)

--select * from VOORKEUR_VAN_GEBRUIKER
INSERT INTO VOORKEUR_VAN_GEBRUIKER(GEBRUIKER_ID,VOORKEUR_NAAM)
VALUES	(1,'kip'), (1,'pizza'),
    (2,'kip'), (2,'vis'), (2,'wok'), (2,'sushi')


--select * from RESTAURANT
INSERT INTO RESTAURANT(RESTAURANT_NAAM,POSTCODE,STRAATNAAM,HUISNUMMER)
VALUES	('Stone Grill House','6811KR','Nieuwe Plein',26),
    ('Grieks Restaurant Rhodos','6811KR','Nieuwe Plein',36),
    ('Mama Bowls','6811EZ','Rijnstraat',69),
    ('Ristorante Il Boccone','6811CP','Ruiterstraat',6),
    ('Retiro','6814BM','Apeldoornseweg',88)

--select * from RESTAURANT_HEEFT_VOORKEUR
INSERT INTO RESTAURANT_HEEFT_VOORKEUR(VOORKEUR_NAAM,RESTAURANT_ID)
VALUES	('vlees',1), ('kip',1),
    ('kip',2),
    ('plantaardig',3), ('vis',3),
    ('pizza',4),
    ('vlees',5), ('wok',5)

--select * from VOEDINGSRESTRICTIE_IN_RESTAURANT
INSERT INTO VOEDINGSRESTRICTIE_IN_RESTAURANT(RESTAURANT_ID,RESTRICTIE_NAAM,TYPE)
VALUES	(1,'vlees','dieet'), (5,'vlees','dieet'),
    (3,'noten','allergie'),
    (1,'varkensvlees','geloof')


--select * from GROEP
INSERT INTO GROEP(GROEPNAAM)
VALUES	('Scala')

--select * from GEBRUIKER_IN_GROEP
INSERT INTO GEBRUIKER_IN_GROEP(GEBRUIKER_ID,GROEPNAAM)
VALUES	(2,'Scala')
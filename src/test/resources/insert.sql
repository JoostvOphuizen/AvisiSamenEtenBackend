INSERT INTO VOORKEUR(VOORKEUR_NAAM)
VALUES	('vlees'), ('vis'), ('kip'),
		('soep'), ('sushi'), ('pizza'),
		('wok'), ('plantaardig'), ('mexicaans');

INSERT INTO VOEDINGSRESTRICTIE (RESTRICTIE_NAAM)
VALUES	('noten'),
		('varkensvlees'),
		('vlees');

INSERT INTO GEBRUIKER (GEBRUIKERSNAAM,EMAIL,TOKEN, FOTO)
VALUES	('user1', 'test@email.com','0000-0000-0000', 'src\assets\profile-user.png'),
		('user2', 'test@email.com','1111-1111-1111', 'src\assets\profile-user.png'),
		('user3', 'test@email.com','1234-1234-1234', 'src\assets\profile-user.png');

INSERT INTO GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (RESTRICTIE_NAAM,GEBRUIKER_ID)
VALUES	('noten',1),
		('varkensvlees',1);

INSERT INTO VOORKEUR_VAN_GEBRUIKER(GEBRUIKER_ID,VOORKEUR_NAAM)
VALUES	(1,'kip'), (1,'pizza'),
		(2,'kip'), (2,'vis'), (2,'wok'), (2,'sushi');

INSERT INTO RESTAURANT(RESTAURANT_NAAM,POSTCODE,STRAATNAAM,HUISNUMMER,LINK,FOTO)
VALUES	('Stone Grill House','6811KR','Nieuwe Plein',26,'https://www.bol.com/nl/nl/',null),
		('Grieks Restaurant Rhodos','6811KR','Nieuwe Plein',36,'https://www.rhodosarnhem.nl/','https://www.theater.nl/static-images/2017/05/2_30052017142140_O_rhodos.jpg'),
		('Mama Bowls','6811EZ','Rijnstraat',69,'https://www.bol.com/nl/nl/',null),
		('Ristorante Il Boccone','6811CP','Ruiterstraat',6,'https://www.bol.com/nl/nl/',null),
		('Retiro','6814BM','Apeldoornseweg',88,'https://www.bol.com/nl/nl/',null);

INSERT INTO RESTAURANT_HEEFT_VOORKEUR(VOORKEUR_NAAM,RESTAURANT_ID)
VALUES	('vlees',1), ('kip',1),
		('kip',2),
		('plantaardig',3), ('vis',3),
		('pizza',4), 
		('vlees',5), ('wok',5);

INSERT INTO VOEDINGSRESTRICTIE_IN_RESTAURANT(RESTAURANT_ID,RESTRICTIE_NAAM)
VALUES	(1,'vlees'), (5,'vlees'),
		(3,'noten'),
		(1,'varkensvlees');

INSERT INTO GROEP(GROEPNAAM)
VALUES	('Scala');

INSERT INTO GEBRUIKER_IN_GROEP(GEBRUIKER_ID,GROEPNAAM)
VALUES	(2,'Scala');

INSERT INTO REVIEW (GEBRUIKER_ID,RESTAURANT_ID,BEOORDELING,TEKST, DATUM)
VALUES  (1,1,5,'mooi','01/01/2022'),
		(2,1,3,'minder mooi','01/02/2022'),
		(1,2,5,'SUPER!','01/02/2022'),
		(1,3,2,'matig','01/02/2022'),
		(1,4,4,'Hier een langere review om te zien dat het ook meeschaalt met de grote van een review. Verder moet nu ook de grote van de review niet meer uitmaken nadat het datatuype van deze kolom is aangepast.','01/02/2022');

INSERT INTO HIST_BEZOEK (GEBRUIKER_ID, RESTAURANT_ID, DATUM, REVIEW_ID)
VALUES  (1, 1, '01/01/2022', 1),
		(1, 2, '01/02/2022', 3),
		(1, 4, '01/02/2022', 5),
		(2, 1, '01/02/2022', 2),
		(2, 3, '01/01/2022', null),
		(2, 4, '01/01/2022', null),
		(2, 5, '01/01/2022', null),
		(1, 3, '01/02/2022', 4),
		(2, 3, '03/03/2022', null);
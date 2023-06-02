drop table if exists GEBRUIKER;
create table GEBRUIKER (
   GEBRUIKER_ID         int                   AUTO_INCREMENT,
   GEBRUIKERSNAAM       varchar(256)                 not null,
   EMAIL                varchar(256)                 not null,
   TOKEN                varchar(14)                not null,
   FOTO                 varchar(512)                 null,
   constraint PK_GEBRUIKER primary key (GEBRUIKER_ID)
);

drop table if exists GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE;
create table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (
    RESTRICTIE_NAAM      VARCHAR(256)                 not null,
    GEBRUIKER_ID         INT                   not null,
    constraint PK_GEBRUIKER_HEEFT_VOEDINGSRES primary key (RESTRICTIE_NAAM, GEBRUIKER_ID)
);

drop table if exists GEBRUIKER_IN_GROEP;
create table GEBRUIKER_IN_GROEP (
    GEBRUIKER_ID         INT                   not null,
    GROEPNAAM            VARCHAR(256)                 not null,
    constraint PK_GEBRUIKER_IN_GROEP primary key (GEBRUIKER_ID, GROEPNAAM)
);

drop table if exists GROEP;
create table GROEP (
	GROEPNAAM            VARCHAR(256)                 not null,
	constraint PK_GROEP primary key (GROEPNAAM)
);

drop table if exists GEBRUIKER_IN_UITNODIGINGSGROEP;
create table GEBRUIKER_IN_UITNODIGINGSGROEP (
    GEBRUIKER_ID    INT     not null,
    UITNODIGING_TOKEN       varchar(14)     not null,
    constraint PK_GEBRUIKER_IN_UITNODIGINGSGROEP primary key (GEBRUIKER_ID,UITNODIGING_TOKEN)
);

drop table if exists HIST_BEZOEK;
create table HIST_BEZOEK (
    DATUM       datetime       not null,
    RESTAURANT_ID   int     not null,
    GEBRUIKER_ID    int     not null,
    REVIEW_ID       int     null,
    constraint PK_HIST_BEZOEK primary key (DATUM,RESTAURANT_ID,GEBRUIKER_ID)
);

drop table if exists RESTAURANT;
create table RESTAURANT (
    RESTAURANT_ID        INT         AUTO_INCREMENT         not null,
    RESTAURANT_NAAM      VARCHAR(256)                 not null,
    POSTCODE             VARCHAR(6)             not null,
    STRAATNAAM           VARCHAR(256)                 not null,
    HUISNUMMER           INT               not null,
    LINK                 VARCHAR(512)       null,
    FOTO                 VARCHAR(512)       null,
    constraint PK_RESTAURANT primary key (RESTAURANT_ID)
);

drop table if exists RESTAURANT_HEEFT_VOORKEUR;
create table RESTAURANT_HEEFT_VOORKEUR (
    VOORKEUR_NAAM        VARCHAR(256)                 not null,
    RESTAURANT_ID        INT                   not null,
    constraint PK_RESTAURANT_HEEFT_VOORKEUR primary key (VOORKEUR_NAAM, RESTAURANT_ID)
);

drop table if exists REVIEW;
create table REVIEW (
    REVIEW_ID            INT                  AUTO_INCREMENT        not null,
    GEBRUIKER_ID         INT                  not null,
    RESTAURANT_ID        INT                  not null,
    BEOORDELING          INT               not null,
    TEKST                VARCHAR(1024)             null,
    DATUM				datetime        not null,
    constraint PK_REVIEW primary key (REVIEW_ID)
);

drop table if exists UITNODIGINGSGROEP;
create table UITNODIGINGSGROEP (
    UITNODIGINGS_TOKEN      varchar(14)     not null,
    GEBRUIKER_ID        int     not null,
    RESTAURANT_ID       int     null,
    constraint PK_UITNODIGINGSGROEP primary key (UITNODIGINGS_TOKEN)
);

drop table if exists VOEDINGSRESTRICTIE;
create table VOEDINGSRESTRICTIE (
	RESTRICTIE_NAAM      VARCHAR(256)                 not null,
	constraint PK_VOEDINGSRESTRICTIE primary key (RESTRICTIE_NAAM)
);

drop table if exists VOEDINGSRESTRICTIE_IN_RESTAURANT;
create table VOEDINGSRESTRICTIE_IN_RESTAURANT (
    RESTAURANT_ID        INT                   not null,
    RESTRICTIE_NAAM      VARCHAR(256)                 not null,
    constraint PK_VOEDINGSRESTRICTIE_IN_RESTA primary key (RESTAURANT_ID, RESTRICTIE_NAAM)
);

drop table if exists voorkeur;
create table VOORKEUR (
	VOORKEUR_NAAM        VARCHAR(256)                 not null,
	constraint PK_VOORKEUR primary key (VOORKEUR_NAAM)
);

drop table if exists VOORKEUR_VAN_GEBRUIKER;
create table VOORKEUR_VAN_GEBRUIKER (
	GEBRUIKER_ID         INT                   not null,
	VOORKEUR_NAAM        VARCHAR(256)                 not null,
	constraint PK_VOORKEUR_VAN_GEBRUIKER primary key (GEBRUIKER_ID, VOORKEUR_NAAM)
);
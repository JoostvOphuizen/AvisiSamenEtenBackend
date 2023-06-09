/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2014                    */
/* Created on:     1-6-2023 14:01:25                            */
/*==============================================================*/


use master
go

drop database if exists sameneten
go

create database sameneten
go

use sameneten
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE') and o.name = 'FK_GEBRUIKE_FK_GHV_GE_GEBRUIKE')
alter table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
   drop constraint FK_GEBRUIKE_FK_GHV_GE_GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE') and o.name = 'FK_GEBRUIKE_FK_GHV_VO_VOEDINGS')
alter table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
   drop constraint FK_GEBRUIKE_FK_GHV_VO_VOEDINGS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GEBRUIKER_IN_GROEP') and o.name = 'FK_GEBRUIKE_FK_GIG_GE_GEBRUIKE')
alter table GEBRUIKER_IN_GROEP
   drop constraint FK_GEBRUIKE_FK_GIG_GE_GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GEBRUIKER_IN_GROEP') and o.name = 'FK_GEBRUIKE_FK_GIG_GR_GROEP')
alter table GEBRUIKER_IN_GROEP
   drop constraint FK_GEBRUIKE_FK_GIG_GR_GROEP
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GEBRUIKER_IN_UITNODINGSGROEP') and o.name = 'FK_GEBRUIKE_FK_GIU_GE_GEBRUIKE')
alter table GEBRUIKER_IN_UITNODINGSGROEP
   drop constraint FK_GEBRUIKE_FK_GIU_GE_GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GEBRUIKER_IN_UITNODINGSGROEP') and o.name = 'FK_GEBRUIKE_FK_GIU_UI_UITNODIG')
alter table GEBRUIKER_IN_UITNODINGSGROEP
   drop constraint FK_GEBRUIKE_FK_GIU_UI_UITNODIG
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('HIST_BEZOEK') and o.name = 'FK_HIST_BEZ_BEZOEK_VA_GEBRUIKE')
alter table HIST_BEZOEK
   drop constraint FK_HIST_BEZ_BEZOEK_VA_GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('HIST_BEZOEK') and o.name = 'FK_HIST_BEZ_BEZOEK_VA_RESTAURA')
alter table HIST_BEZOEK
   drop constraint FK_HIST_BEZ_BEZOEK_VA_RESTAURA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('HIST_BEZOEK') and o.name = 'FK_HIST_BEZ_REVIEW_VA_REVIEW')
alter table HIST_BEZOEK
   drop constraint FK_HIST_BEZ_REVIEW_VA_REVIEW
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RESTAURANT_HEEFT_VOORKEUR') and o.name = 'FK_RESTAURA_RESTAURAN_VOORKEUR')
alter table RESTAURANT_HEEFT_VOORKEUR
   drop constraint FK_RESTAURA_RESTAURAN_VOORKEUR
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RESTAURANT_HEEFT_VOORKEUR') and o.name = 'FK_RESTAURA_RESTAURAN_RESTAURA')
alter table RESTAURANT_HEEFT_VOORKEUR
   drop constraint FK_RESTAURA_RESTAURAN_RESTAURA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('REVIEW') and o.name = 'FK_REVIEW_REVIEW_OV_RESTAURA')
alter table REVIEW
   drop constraint FK_REVIEW_REVIEW_OV_RESTAURA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('REVIEW') and o.name = 'FK_REVIEW_REVIEW_VA_GEBRUIKE')
alter table REVIEW
   drop constraint FK_REVIEW_REVIEW_VA_GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UITNODIGINGSGROEP') and o.name = 'FK_UITNODIG_ORGANISEE_GEBRUIKE')
alter table UITNODIGINGSGROEP
   drop constraint FK_UITNODIG_ORGANISEE_GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UITNODIGINGSGROEP') and o.name = 'FK_UITNODIG_RESTAURAN_RESTAURA')
alter table UITNODIGINGSGROEP
   drop constraint FK_UITNODIG_RESTAURAN_RESTAURA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT') and o.name = 'FK_VOEDINGS_VOEDINGSR_RESTAURA')
alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
   drop constraint FK_VOEDINGS_VOEDINGSR_RESTAURA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT') and o.name = 'FK_VOEDINGS_VOEDINGSR_VOEDINGS')
alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
   drop constraint FK_VOEDINGS_VOEDINGSR_VOEDINGS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOORKEUR_VAN_GEBRUIKER') and o.name = 'FK_VOORKEUR_VOORKEUR__GEBRUIKE')
alter table VOORKEUR_VAN_GEBRUIKER
   drop constraint FK_VOORKEUR_VOORKEUR__GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOORKEUR_VAN_GEBRUIKER') and o.name = 'FK_VOORKEUR_VOORKEUR__VOORKEUR')
alter table VOORKEUR_VAN_GEBRUIKER
   drop constraint FK_VOORKEUR_VOORKEUR__VOORKEUR
go

if exists (select 1
            from  sysobjects
           where  id = object_id('GEBRUIKER')
            and   type = 'U')
   drop table GEBRUIKER
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE')
            and   name  = 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE2_FK'
            and   indid > 0
            and   indid < 255)
   drop index GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE.GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE')
            and   name  = 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE_FK'
            and   indid > 0
            and   indid < 255)
   drop index GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE.GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE')
            and   type = 'U')
   drop table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_IN_GROEP')
            and   name  = 'GEBRUIKER_IN_GROEP2_FK'
            and   indid > 0
            and   indid < 255)
   drop index GEBRUIKER_IN_GROEP.GEBRUIKER_IN_GROEP2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_IN_GROEP')
            and   name  = 'GEBRUIKER_IN_GROEP_FK'
            and   indid > 0
            and   indid < 255)
   drop index GEBRUIKER_IN_GROEP.GEBRUIKER_IN_GROEP_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('GEBRUIKER_IN_GROEP')
            and   type = 'U')
   drop table GEBRUIKER_IN_GROEP
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_IN_UITNODINGSGROEP')
            and   name  = 'GEBRUIKER_IN_UITNODINGSGROEP2_FK'
            and   indid > 0
            and   indid < 255)
   drop index GEBRUIKER_IN_UITNODINGSGROEP.GEBRUIKER_IN_UITNODINGSGROEP2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_IN_UITNODINGSGROEP')
            and   name  = 'GEBRUIKER_IN_UITNODINGSGROEP_FK'
            and   indid > 0
            and   indid < 255)
   drop index GEBRUIKER_IN_UITNODINGSGROEP.GEBRUIKER_IN_UITNODINGSGROEP_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('GEBRUIKER_IN_UITNODINGSGROEP')
            and   type = 'U')
   drop table GEBRUIKER_IN_UITNODINGSGROEP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('GROEP')
            and   type = 'U')
   drop table GROEP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('HIST_BEZOEK')
            and   type = 'U')
   drop table HIST_BEZOEK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RESTAURANT')
            and   type = 'U')
   drop table RESTAURANT
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RESTAURANT_HEEFT_VOORKEUR')
            and   name  = 'RESTAURANT_HEEFT_VOORKEUR2_FK'
            and   indid > 0
            and   indid < 255)
   drop index RESTAURANT_HEEFT_VOORKEUR.RESTAURANT_HEEFT_VOORKEUR2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RESTAURANT_HEEFT_VOORKEUR')
            and   name  = 'RESTAURANT_HEEFT_VOORKEUR_FK'
            and   indid > 0
            and   indid < 255)
   drop index RESTAURANT_HEEFT_VOORKEUR.RESTAURANT_HEEFT_VOORKEUR_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RESTAURANT_HEEFT_VOORKEUR')
            and   type = 'U')
   drop table RESTAURANT_HEEFT_VOORKEUR
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('REVIEW')
            and   name  = 'REVIEW_VAN_GEBRUIKER_FK'
            and   indid > 0
            and   indid < 255)
   drop index REVIEW.REVIEW_VAN_GEBRUIKER_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('REVIEW')
            and   name  = 'REVIEW_OVER_RESTAURANT_FK'
            and   indid > 0
            and   indid < 255)
   drop index REVIEW.REVIEW_OVER_RESTAURANT_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('REVIEW')
            and   type = 'U')
   drop table REVIEW
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('UITNODIGINGSGROEP')
            and   name  = 'ORGANISEERD_FK'
            and   indid > 0
            and   indid < 255)
   drop index UITNODIGINGSGROEP.ORGANISEERD_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('UITNODIGINGSGROEP')
            and   type = 'U')
   drop table UITNODIGINGSGROEP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VOEDINGSRESTRICTIE')
            and   type = 'U')
   drop table VOEDINGSRESTRICTIE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT')
            and   name  = 'VOEDINGSRESTRICTIE_IN_RESTAURANT2_FK'
            and   indid > 0
            and   indid < 255)
   drop index VOEDINGSRESTRICTIE_IN_RESTAURANT.VOEDINGSRESTRICTIE_IN_RESTAURANT2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT')
            and   name  = 'VOEDINGSRESTRICTIE_IN_RESTAURANT_FK'
            and   indid > 0
            and   indid < 255)
   drop index VOEDINGSRESTRICTIE_IN_RESTAURANT.VOEDINGSRESTRICTIE_IN_RESTAURANT_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT')
            and   type = 'U')
   drop table VOEDINGSRESTRICTIE_IN_RESTAURANT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VOORKEUR')
            and   type = 'U')
   drop table VOORKEUR
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VOORKEUR_VAN_GEBRUIKER')
            and   name  = 'VOORKEUR_VAN_GEBRUIKER2_FK'
            and   indid > 0
            and   indid < 255)
   drop index VOORKEUR_VAN_GEBRUIKER.VOORKEUR_VAN_GEBRUIKER2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VOORKEUR_VAN_GEBRUIKER')
            and   name  = 'VOORKEUR_VAN_GEBRUIKER_FK'
            and   indid > 0
            and   indid < 255)
   drop index VOORKEUR_VAN_GEBRUIKER.VOORKEUR_VAN_GEBRUIKER_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VOORKEUR_VAN_GEBRUIKER')
            and   type = 'U')
   drop table VOORKEUR_VAN_GEBRUIKER
go

if exists(select 1 from systypes where name='DATUM')
   drop type DATUM
go

if exists(select 1 from systypes where name='ID')
   drop type ID
go

if exists(select 1 from systypes where name='LINK')
   drop type LINK
go

if exists(select 1 from systypes where name='NAAM')
   drop type NAAM
go

if exists(select 1 from systypes where name='NUMMER')
   drop type NUMMER
go

if exists(select 1 from systypes where name='POSTCODE')
   drop type POSTCODE
go

if exists(select 1 from systypes where name='TEKSTVAK')
   drop type TEKSTVAK
go

if exists(select 1 from systypes where name='TOKEN')
   drop type TOKEN
go

/*==============================================================*/
/* Domain: DATUM                                                */
/*==============================================================*/
create type DATUM
   from datetime
go

/*==============================================================*/
/* Domain: ID                                                   */
/*==============================================================*/
create type ID
   from int
go

/*==============================================================*/
/* Domain: LINK                                                 */
/*==============================================================*/
create type LINK
   from varchar(512)
go

/*==============================================================*/
/* Domain: NAAM                                                 */
/*==============================================================*/
create type NAAM
   from varchar(256)
go

/*==============================================================*/
/* Domain: NUMMER                                               */
/*==============================================================*/
create type NUMMER
   from int
go

/*==============================================================*/
/* Domain: POSTCODE                                             */
/*==============================================================*/
create type POSTCODE
   from varchar(6)
go

/*==============================================================*/
/* Domain: TEKSTVAK                                             */
/*==============================================================*/
create type TEKSTVAK
   from varchar(1024)
go

/*==============================================================*/
/* Domain: TOKEN                                                */
/*==============================================================*/
create type TOKEN
   from varchar(14)
go

/*==============================================================*/
/* Table: GEBRUIKER                                             */
/*==============================================================*/
create table GEBRUIKER (
   GEBRUIKER_ID         ID                   identity,
   GEBRUIKERSNAAM       NAAM                 not null,
   EMAIL                NAAM                 not null,
   TOKEN                TOKEN                not null,
   FOTO                 LINK                 null DEFAULT 'src\assets\profile-user.png',
   constraint PK_GEBRUIKER primary key (GEBRUIKER_ID)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('GEBRUIKER') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'GEBRUIKER' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Gebruiker', 
   'user', @CurrentUser, 'table', 'GEBRUIKER'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'GEBRUIKER_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKERSNAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'GEBRUIKERSNAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruikersnaam',
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'GEBRUIKERSNAAM'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'EMAIL')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'EMAIL'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'email',
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'EMAIL'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'TOKEN')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'TOKEN'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'token',
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'TOKEN'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'FOTO')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'FOTO'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'foto',
   'user', @CurrentUser, 'table', 'GEBRUIKER', 'column', 'FOTO'
go

/*==============================================================*/
/* Table: GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE                    */
/*==============================================================*/
create table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (
   RESTRICTIE_NAAM      NAAM                 not null,
   GEBRUIKER_ID         ID                   not null,
   constraint PK_GEBRUIKER_HEEFT_VOEDINGSRES primary key (RESTRICTIE_NAAM, GEBRUIKER_ID)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Gebruiker_heeft_Voedingsrestrictie', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTRICTIE_NAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE', 'column', 'RESTRICTIE_NAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restrictie_naam',
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE', 'column', 'RESTRICTIE_NAAM'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE', 'column', 'GEBRUIKER_ID'
go

/*==============================================================*/
/* Index: GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE_FK                 */
/*==============================================================*/




create nonclustered index GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE_FK on GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (RESTRICTIE_NAAM ASC)
go

/*==============================================================*/
/* Index: GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE2_FK                */
/*==============================================================*/




create nonclustered index GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE2_FK on GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Table: GEBRUIKER_IN_GROEP                                    */
/*==============================================================*/
create table GEBRUIKER_IN_GROEP (
   GEBRUIKER_ID         ID                   not null,
   GROEPNAAM            NAAM                 not null,
   constraint PK_GEBRUIKER_IN_GROEP primary key (GEBRUIKER_ID, GROEPNAAM)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('GEBRUIKER_IN_GROEP') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_GROEP' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Gebruiker_in_Groep', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_GROEP'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER_IN_GROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_GROEP', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_GROEP', 'column', 'GEBRUIKER_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER_IN_GROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GROEPNAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_GROEP', 'column', 'GROEPNAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'groepnaam',
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_GROEP', 'column', 'GROEPNAAM'
go

/*==============================================================*/
/* Index: GEBRUIKER_IN_GROEP_FK                                 */
/*==============================================================*/




create nonclustered index GEBRUIKER_IN_GROEP_FK on GEBRUIKER_IN_GROEP (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Index: GEBRUIKER_IN_GROEP2_FK                                */
/*==============================================================*/




create nonclustered index GEBRUIKER_IN_GROEP2_FK on GEBRUIKER_IN_GROEP (GROEPNAAM ASC)
go

/*==============================================================*/
/* Table: GEBRUIKER_IN_UITNODINGSGROEP                          */
/*==============================================================*/
create table GEBRUIKER_IN_UITNODINGSGROEP (
   GEBRUIKER_ID         ID                   not null,
   UITNODIGING_TOKEN    TOKEN                not null,
   constraint PK_GEBRUIKER_IN_UITNODINGSGROE primary key (GEBRUIKER_ID, UITNODIGING_TOKEN)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('GEBRUIKER_IN_UITNODINGSGROEP') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_UITNODINGSGROEP' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Gebruiker_in_Uitnodingsgroep', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_UITNODINGSGROEP'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER_IN_UITNODINGSGROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_UITNODINGSGROEP', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_UITNODINGSGROEP', 'column', 'GEBRUIKER_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GEBRUIKER_IN_UITNODINGSGROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'UITNODIGING_TOKEN')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_UITNODINGSGROEP', 'column', 'UITNODIGING_TOKEN'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'uitnodiging_token',
   'user', @CurrentUser, 'table', 'GEBRUIKER_IN_UITNODINGSGROEP', 'column', 'UITNODIGING_TOKEN'
go

/*==============================================================*/
/* Index: GEBRUIKER_IN_UITNODINGSGROEP_FK                       */
/*==============================================================*/




create nonclustered index GEBRUIKER_IN_UITNODINGSGROEP_FK on GEBRUIKER_IN_UITNODINGSGROEP (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Index: GEBRUIKER_IN_UITNODINGSGROEP2_FK                      */
/*==============================================================*/




create nonclustered index GEBRUIKER_IN_UITNODINGSGROEP2_FK on GEBRUIKER_IN_UITNODINGSGROEP (UITNODIGING_TOKEN ASC)
go

/*==============================================================*/
/* Table: GROEP                                                 */
/*==============================================================*/
create table GROEP (
   GROEPNAAM            NAAM                 not null,
   constraint PK_GROEP primary key (GROEPNAAM)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('GROEP') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'GROEP' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Groep', 
   'user', @CurrentUser, 'table', 'GROEP'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('GROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GROEPNAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'GROEP', 'column', 'GROEPNAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'groepnaam',
   'user', @CurrentUser, 'table', 'GROEP', 'column', 'GROEPNAAM'
go

/*==============================================================*/
/* Table: HIST_BEZOEK                                           */
/*==============================================================*/
create table HIST_BEZOEK (
   DATUM                DATUM                not null,
   RESTAURANT_ID        ID                   not null,
   GEBRUIKER_ID         ID                   not null,
   REVIEW_ID            ID                   null,
   constraint PK_HIST_BEZOEK primary key (DATUM, RESTAURANT_ID, GEBRUIKER_ID)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('HIST_BEZOEK') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'HIST_BEZOEK' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'HIST_Bezoek', 
   'user', @CurrentUser, 'table', 'HIST_BEZOEK'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('HIST_BEZOEK')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'DATUM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'DATUM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'datum',
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'DATUM'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('HIST_BEZOEK')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTAURANT_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'RESTAURANT_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restaurant_id',
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'RESTAURANT_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('HIST_BEZOEK')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'GEBRUIKER_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('HIST_BEZOEK')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'REVIEW_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'REVIEW_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'review_id',
   'user', @CurrentUser, 'table', 'HIST_BEZOEK', 'column', 'REVIEW_ID'
go

/*==============================================================*/
/* Table: RESTAURANT                                            */
/*==============================================================*/
create table RESTAURANT (
   RESTAURANT_ID        ID                   identity,
   RESTAURANT_NAAM      NAAM                 not null,
   POSTCODE             POSTCODE             not null,
   STRAATNAAM           NAAM                 not null,
   HUISNUMMER           NUMMER               not null,
   LINK                 LINK                 not null,
   FOTO                 LINK                 null,
   constraint PK_RESTAURANT primary key (RESTAURANT_ID)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('RESTAURANT') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'RESTAURANT' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Restaurant', 
   'user', @CurrentUser, 'table', 'RESTAURANT'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTAURANT_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'RESTAURANT_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restaurant_id',
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'RESTAURANT_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTAURANT_NAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'RESTAURANT_NAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restaurant_naam',
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'RESTAURANT_NAAM'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'POSTCODE')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'POSTCODE'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'postcode',
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'POSTCODE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'STRAATNAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'STRAATNAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'straatnaam',
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'STRAATNAAM'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'HUISNUMMER')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'HUISNUMMER'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'huisnummer',
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'HUISNUMMER'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'LINK')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'LINK'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'link',
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'LINK'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'FOTO')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'FOTO'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'foto',
   'user', @CurrentUser, 'table', 'RESTAURANT', 'column', 'FOTO'
go

/*==============================================================*/
/* Table: RESTAURANT_HEEFT_VOORKEUR                             */
/*==============================================================*/
create table RESTAURANT_HEEFT_VOORKEUR (
   VOORKEUR_NAAM        NAAM                 not null,
   RESTAURANT_ID        ID                   not null,
   constraint PK_RESTAURANT_HEEFT_VOORKEUR primary key (VOORKEUR_NAAM, RESTAURANT_ID)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('RESTAURANT_HEEFT_VOORKEUR') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'RESTAURANT_HEEFT_VOORKEUR' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Restaurant_heeft_Voorkeur', 
   'user', @CurrentUser, 'table', 'RESTAURANT_HEEFT_VOORKEUR'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT_HEEFT_VOORKEUR')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'VOORKEUR_NAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT_HEEFT_VOORKEUR', 'column', 'VOORKEUR_NAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'voorkeur_naam',
   'user', @CurrentUser, 'table', 'RESTAURANT_HEEFT_VOORKEUR', 'column', 'VOORKEUR_NAAM'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('RESTAURANT_HEEFT_VOORKEUR')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTAURANT_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'RESTAURANT_HEEFT_VOORKEUR', 'column', 'RESTAURANT_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restaurant_id',
   'user', @CurrentUser, 'table', 'RESTAURANT_HEEFT_VOORKEUR', 'column', 'RESTAURANT_ID'
go

/*==============================================================*/
/* Index: RESTAURANT_HEEFT_VOORKEUR_FK                          */
/*==============================================================*/




create nonclustered index RESTAURANT_HEEFT_VOORKEUR_FK on RESTAURANT_HEEFT_VOORKEUR (VOORKEUR_NAAM ASC)
go

/*==============================================================*/
/* Index: RESTAURANT_HEEFT_VOORKEUR2_FK                         */
/*==============================================================*/




create nonclustered index RESTAURANT_HEEFT_VOORKEUR2_FK on RESTAURANT_HEEFT_VOORKEUR (RESTAURANT_ID ASC)
go

/*==============================================================*/
/* Table: REVIEW                                                */
/*==============================================================*/
create table REVIEW (
   REVIEW_ID            ID                   identity,
   GEBRUIKER_ID         ID                   not null,
   RESTAURANT_ID        ID                   not null,
   BEOORDELING          NUMMER               not null,
   TEKST                TEKSTVAK             null,
   DATUM                DATUM                not null,
   constraint PK_REVIEW primary key (REVIEW_ID)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('REVIEW') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'REVIEW' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Review', 
   'user', @CurrentUser, 'table', 'REVIEW'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('REVIEW')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'REVIEW_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'REVIEW_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'review_id',
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'REVIEW_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('REVIEW')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'GEBRUIKER_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('REVIEW')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTAURANT_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'RESTAURANT_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restaurant_id',
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'RESTAURANT_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('REVIEW')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'BEOORDELING')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'BEOORDELING'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'beoordeling',
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'BEOORDELING'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('REVIEW')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'TEKST')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'TEKST'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'tekst',
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'TEKST'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('REVIEW')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'DATUM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'DATUM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'datum',
   'user', @CurrentUser, 'table', 'REVIEW', 'column', 'DATUM'
go

/*==============================================================*/
/* Index: REVIEW_OVER_RESTAURANT_FK                             */
/*==============================================================*/




create nonclustered index REVIEW_OVER_RESTAURANT_FK on REVIEW (RESTAURANT_ID ASC)
go

/*==============================================================*/
/* Index: REVIEW_VAN_GEBRUIKER_FK                               */
/*==============================================================*/




create nonclustered index REVIEW_VAN_GEBRUIKER_FK on REVIEW (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Table: UITNODIGINGSGROEP                                     */
/*==============================================================*/
create table UITNODIGINGSGROEP (
   UITNODIGING_TOKEN    TOKEN                not null,
   GEBRUIKER_ID         ID                   not null,
   RESTAURANT_ID        ID                   null,
   constraint PK_UITNODIGINGSGROEP primary key (UITNODIGING_TOKEN)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('UITNODIGINGSGROEP') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Uitnodigingsgroep', 
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('UITNODIGINGSGROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'UITNODIGING_TOKEN')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP', 'column', 'UITNODIGING_TOKEN'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'uitnodiging_token',
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP', 'column', 'UITNODIGING_TOKEN'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('UITNODIGINGSGROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP', 'column', 'GEBRUIKER_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('UITNODIGINGSGROEP')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTAURANT_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP', 'column', 'RESTAURANT_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restaurant_id',
   'user', @CurrentUser, 'table', 'UITNODIGINGSGROEP', 'column', 'RESTAURANT_ID'
go

/*==============================================================*/
/* Index: ORGANISEERD_FK                                        */
/*==============================================================*/




create nonclustered index ORGANISEERD_FK on UITNODIGINGSGROEP (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Table: VOEDINGSRESTRICTIE                                    */
/*==============================================================*/
create table VOEDINGSRESTRICTIE (
   RESTRICTIE_NAAM      NAAM                 not null,
   constraint PK_VOEDINGSRESTRICTIE primary key (RESTRICTIE_NAAM)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('VOEDINGSRESTRICTIE') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Voedingsrestrictie', 
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOEDINGSRESTRICTIE')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTRICTIE_NAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE', 'column', 'RESTRICTIE_NAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restrictie_naam',
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE', 'column', 'RESTRICTIE_NAAM'
go

/*==============================================================*/
/* Table: VOEDINGSRESTRICTIE_IN_RESTAURANT                      */
/*==============================================================*/
create table VOEDINGSRESTRICTIE_IN_RESTAURANT (
   RESTAURANT_ID        ID                   not null,
   RESTRICTIE_NAAM      NAAM                 not null,
   constraint PK_VOEDINGSRESTRICTIE_IN_RESTA primary key (RESTAURANT_ID, RESTRICTIE_NAAM)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Voedingsrestrictie_in_Restaurant', 
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTAURANT_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT', 'column', 'RESTAURANT_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restaurant_id',
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT', 'column', 'RESTAURANT_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'RESTRICTIE_NAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT', 'column', 'RESTRICTIE_NAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'restrictie_naam',
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT', 'column', 'RESTRICTIE_NAAM'
go

/*==============================================================*/
/* Index: VOEDINGSRESTRICTIE_IN_RESTAURANT_FK                   */
/*==============================================================*/




create nonclustered index VOEDINGSRESTRICTIE_IN_RESTAURANT_FK on VOEDINGSRESTRICTIE_IN_RESTAURANT (RESTAURANT_ID ASC)
go

/*==============================================================*/
/* Index: VOEDINGSRESTRICTIE_IN_RESTAURANT2_FK                  */
/*==============================================================*/




create nonclustered index VOEDINGSRESTRICTIE_IN_RESTAURANT2_FK on VOEDINGSRESTRICTIE_IN_RESTAURANT (RESTRICTIE_NAAM ASC)
go

/*==============================================================*/
/* Table: VOORKEUR                                              */
/*==============================================================*/
create table VOORKEUR (
   VOORKEUR_NAAM        NAAM                 not null,
   constraint PK_VOORKEUR primary key (VOORKEUR_NAAM)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('VOORKEUR') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'VOORKEUR' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Voorkeur', 
   'user', @CurrentUser, 'table', 'VOORKEUR'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOORKEUR')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'VOORKEUR_NAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'VOORKEUR', 'column', 'VOORKEUR_NAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'voorkeur_naam',
   'user', @CurrentUser, 'table', 'VOORKEUR', 'column', 'VOORKEUR_NAAM'
go

/*==============================================================*/
/* Table: VOORKEUR_VAN_GEBRUIKER                                */
/*==============================================================*/
create table VOORKEUR_VAN_GEBRUIKER (
   GEBRUIKER_ID         ID                   not null,
   VOORKEUR_NAAM        NAAM                 not null,
   constraint PK_VOORKEUR_VAN_GEBRUIKER primary key (GEBRUIKER_ID, VOORKEUR_NAAM)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('VOORKEUR_VAN_GEBRUIKER') and minor_id = 0)
begin 
   declare @CurrentUser sysname 
select @CurrentUser = user_name() 
execute sp_dropextendedproperty 'MS_Description',  
   'user', @CurrentUser, 'table', 'VOORKEUR_VAN_GEBRUIKER' 
 
end 


select @CurrentUser = user_name() 
execute sp_addextendedproperty 'MS_Description',  
   'Voorkeur_van_Gebruiker', 
   'user', @CurrentUser, 'table', 'VOORKEUR_VAN_GEBRUIKER'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOORKEUR_VAN_GEBRUIKER')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'GEBRUIKER_ID')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'VOORKEUR_VAN_GEBRUIKER', 'column', 'GEBRUIKER_ID'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'gebruiker_id',
   'user', @CurrentUser, 'table', 'VOORKEUR_VAN_GEBRUIKER', 'column', 'GEBRUIKER_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOORKEUR_VAN_GEBRUIKER')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'VOORKEUR_NAAM')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'VOORKEUR_VAN_GEBRUIKER', 'column', 'VOORKEUR_NAAM'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'voorkeur_naam',
   'user', @CurrentUser, 'table', 'VOORKEUR_VAN_GEBRUIKER', 'column', 'VOORKEUR_NAAM'
go

/*==============================================================*/
/* Index: VOORKEUR_VAN_GEBRUIKER_FK                             */
/*==============================================================*/




create nonclustered index VOORKEUR_VAN_GEBRUIKER_FK on VOORKEUR_VAN_GEBRUIKER (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Index: VOORKEUR_VAN_GEBRUIKER2_FK                            */
/*==============================================================*/




create nonclustered index VOORKEUR_VAN_GEBRUIKER2_FK on VOORKEUR_VAN_GEBRUIKER (VOORKEUR_NAAM ASC)
go

alter table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
   add constraint FK_GEBRUIKE_FK_GHV_GE_GEBRUIKE foreign key (GEBRUIKER_ID)
      references GEBRUIKER (GEBRUIKER_ID)
         on delete cascade
go

alter table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
   add constraint FK_GEBRUIKE_FK_GHV_VO_VOEDINGS foreign key (RESTRICTIE_NAAM)
      references VOEDINGSRESTRICTIE (RESTRICTIE_NAAM)
         on update cascade on delete cascade
go

alter table GEBRUIKER_IN_GROEP
   add constraint FK_GEBRUIKE_FK_GIG_GE_GEBRUIKE foreign key (GEBRUIKER_ID)
      references GEBRUIKER (GEBRUIKER_ID)
         on delete cascade
go

alter table GEBRUIKER_IN_GROEP
   add constraint FK_GEBRUIKE_FK_GIG_GR_GROEP foreign key (GROEPNAAM)
      references GROEP (GROEPNAAM)
         on update cascade on delete cascade
go

alter table GEBRUIKER_IN_UITNODINGSGROEP
   add constraint FK_GEBRUIKE_FK_GIU_GE_GEBRUIKE foreign key (GEBRUIKER_ID)
      references GEBRUIKER (GEBRUIKER_ID)
         on delete cascade
go

alter table GEBRUIKER_IN_UITNODINGSGROEP
   add constraint FK_GEBRUIKE_FK_GIU_UI_UITNODIG foreign key (UITNODIGING_TOKEN)
      references UITNODIGINGSGROEP (UITNODIGING_TOKEN)
go

alter table HIST_BEZOEK
   add constraint FK_HIST_BEZ_BEZOEK_VA_GEBRUIKE foreign key (GEBRUIKER_ID)
      references GEBRUIKER (GEBRUIKER_ID)
         on delete cascade
go

alter table HIST_BEZOEK
   add constraint FK_HIST_BEZ_BEZOEK_VA_RESTAURA foreign key (RESTAURANT_ID)
      references RESTAURANT (RESTAURANT_ID)
go

alter table HIST_BEZOEK
   add constraint FK_HIST_BEZ_REVIEW_VA_REVIEW foreign key (REVIEW_ID)
      references REVIEW (REVIEW_ID)
         on delete cascade
go

alter table RESTAURANT_HEEFT_VOORKEUR
   add constraint FK_RESTAURA_RESTAURAN_VOORKEUR foreign key (VOORKEUR_NAAM)
      references VOORKEUR (VOORKEUR_NAAM)
         on update cascade on delete cascade
go

alter table RESTAURANT_HEEFT_VOORKEUR
   add constraint FK_RESTAURA_RESTAURAN_RESTAURA foreign key (RESTAURANT_ID)
      references RESTAURANT (RESTAURANT_ID)
         on delete cascade
go

alter table REVIEW
   add constraint FK_REVIEW_REVIEW_OV_RESTAURA foreign key (RESTAURANT_ID)
      references RESTAURANT (RESTAURANT_ID)
         on delete cascade
go

alter table REVIEW
   add constraint FK_REVIEW_REVIEW_VA_GEBRUIKE foreign key (GEBRUIKER_ID)
      references GEBRUIKER (GEBRUIKER_ID)
go

alter table UITNODIGINGSGROEP
   add constraint FK_UITNODIG_ORGANISEE_GEBRUIKE foreign key (GEBRUIKER_ID)
      references GEBRUIKER (GEBRUIKER_ID)
         on delete cascade
go

alter table UITNODIGINGSGROEP
   add constraint FK_UITNODIG_RESTAURAN_RESTAURA foreign key (RESTAURANT_ID)
      references RESTAURANT (RESTAURANT_ID)
         on delete cascade
go

alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
   add constraint FK_VOEDINGS_VOEDINGSR_RESTAURA foreign key (RESTAURANT_ID)
      references RESTAURANT (RESTAURANT_ID)
         on delete cascade
go

alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
   add constraint FK_VOEDINGS_VOEDINGSR_VOEDINGS foreign key (RESTRICTIE_NAAM)
      references VOEDINGSRESTRICTIE (RESTRICTIE_NAAM)
         on update cascade on delete cascade
go

alter table VOORKEUR_VAN_GEBRUIKER
   add constraint FK_VOORKEUR_VOORKEUR__GEBRUIKE foreign key (GEBRUIKER_ID)
      references GEBRUIKER (GEBRUIKER_ID)
         on delete cascade
go

alter table VOORKEUR_VAN_GEBRUIKER
   add constraint FK_VOORKEUR_VOORKEUR__VOORKEUR foreign key (VOORKEUR_NAAM)
      references VOORKEUR (VOORKEUR_NAAM)
         on update cascade on delete cascade
go


/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2014                    */
/* Created on:     17-4-2023 11:19:31                           */
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
   where r.fkeyid = object_id('RESTAURANT_HEEFT_VOORKEUR') and o.name = 'FK_RESTAURA_FK_RHV_RE_RESTAURA')
alter table RESTAURANT_HEEFT_VOORKEUR
drop constraint FK_RESTAURA_FK_RHV_RE_RESTAURA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RESTAURANT_HEEFT_VOORKEUR') and o.name = 'FK_RESTAURA_FK_RHV_VO_VOORKEUR')
alter table RESTAURANT_HEEFT_VOORKEUR
drop constraint FK_RESTAURA_FK_RHV_VO_VOORKEUR
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT') and o.name = 'FK_VOEDINGS_FK_VIR_RE_RESTAURA')
alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
drop constraint FK_VOEDINGS_FK_VIR_RE_RESTAURA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT') and o.name = 'FK_VOEDINGS_FK_VIR_VO_VOEDINGS')
alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
drop constraint FK_VOEDINGS_FK_VIR_VO_VOEDINGS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOORKEUR_VAN_GEBRUIKER') and o.name = 'FK_VOORKEUR_FK_VVG_GE_GEBRUIKE')
alter table VOORKEUR_VAN_GEBRUIKER
drop constraint FK_VOORKEUR_FK_VVG_GE_GEBRUIKE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VOORKEUR_VAN_GEBRUIKER') and o.name = 'FK_VOORKEUR_FK_VVG_VO_VOORKEUR')
alter table VOORKEUR_VAN_GEBRUIKER
drop constraint FK_VOORKEUR_FK_VVG_VO_VOORKEUR
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
    and   name  = 'FK_GHV_VOEDINGSRESTRICTIE'
    and   indid > 0
    and   indid < 255)
drop index GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE.FK_GHV_VOEDINGSRESTRICTIE
    go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE')
            and   name  = 'FK_GHV_GEBRUIKER'
            and   indid > 0
            and   indid < 255)
drop index GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE.FK_GHV_GEBRUIKER
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
    and   name  = 'FK_GIG_GEBRUIKER'
    and   indid > 0
    and   indid < 255)
drop index GEBRUIKER_IN_GROEP.FK_GIG_GEBRUIKER
    go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GEBRUIKER_IN_GROEP')
            and   name  = 'FK_GIG_GROEP'
            and   indid > 0
            and   indid < 255)
drop index GEBRUIKER_IN_GROEP.FK_GIG_GROEP
    go

if exists (select 1
            from  sysobjects
           where  id = object_id('GEBRUIKER_IN_GROEP')
            and   type = 'U')
drop table GEBRUIKER_IN_GROEP
    go

    if exists (select 1
    from  sysobjects
    where  id = object_id('GROEP')
    and   type = 'U')
drop table GROEP
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
            and   name  = 'FK_RHV_RESTAURANT'
            and   indid > 0
            and   indid < 255)
drop index RESTAURANT_HEEFT_VOORKEUR.FK_RHV_RESTAURANT
    go

if exists (select 1
            from  sysobjects
           where  id = object_id('RESTAURANT_HEEFT_VOORKEUR')
            and   type = 'U')
drop table RESTAURANT_HEEFT_VOORKEUR
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
    and   name  = 'FK_VIR_RESTAURANT'
    and   indid > 0
    and   indid < 255)
drop index VOEDINGSRESTRICTIE_IN_RESTAURANT.FK_VIR_RESTAURANT
    go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT')
            and   name  = 'FK_VIR_VOEDINGSRESTRICTIE'
            and   indid > 0
            and   indid < 255)
drop index VOEDINGSRESTRICTIE_IN_RESTAURANT.FK_VIR_VOEDINGSRESTRICTIE
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
    and   name  = 'FK_VVG_GEBRUIKER'
    and   indid > 0
    and   indid < 255)
drop index VOORKEUR_VAN_GEBRUIKER.FK_VVG_GEBRUIKER
    go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VOORKEUR_VAN_GEBRUIKER')
            and   name  = 'FK_VVG_VOORKEUR'
            and   indid > 0
            and   indid < 255)
drop index VOORKEUR_VAN_GEBRUIKER.FK_VVG_VOORKEUR
    go

if exists (select 1
            from  sysobjects
           where  id = object_id('VOORKEUR_VAN_GEBRUIKER')
            and   type = 'U')
drop table VOORKEUR_VAN_GEBRUIKER
    go

    if exists(select 1 from systypes where name='ID')
drop type ID
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

/*==============================================================*/
/* Domain: ID                                                   */
/*==============================================================*/
create type ID
    from int
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
/* Table: GEBRUIKER                                             */
/*==============================================================*/
create table GEBRUIKER (
                           GEBRUIKER_ID         ID         IDENTITY(1,1)          not null,
                           GEBRUIKERSNAAM       NAAM                 not null,
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

/*==============================================================*/
/* Table: GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE                    */
/*==============================================================*/
create table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (
                                                    RESTRICTIE_NAAM      NAAM                 not null,
                                                    TYPE                 NAAM                 not null,
                                                    GEBRUIKER_ID         ID                   not null,
                                                    constraint PK_GEBRUIKER_HEEFT_VOEDINGSRES primary key (RESTRICTIE_NAAM, TYPE, GEBRUIKER_ID)
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
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'TYPE')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE', 'column', 'TYPE'

end


select @CurrentUser = user_name()
    execute sp_addextendedproperty 'MS_Description',
   'type',
   'user', @CurrentUser, 'table', 'GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE', 'column', 'TYPE'
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
/* Index: FK_GHV_GEBRUIKER                                      */
/*==============================================================*/




create nonclustered index FK_GHV_GEBRUIKER on GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (RESTRICTIE_NAAM ASC,
  TYPE ASC)
go

/*==============================================================*/
/* Index: FK_GHV_VOEDINGSRESTRICTIE                             */
/*==============================================================*/




create nonclustered index FK_GHV_VOEDINGSRESTRICTIE on GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE (GEBRUIKER_ID ASC)
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
/* Index: FK_GIG_GROEP                                          */
/*==============================================================*/




create nonclustered index FK_GIG_GROEP on GEBRUIKER_IN_GROEP (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Index: FK_GIG_GEBRUIKER                                      */
/*==============================================================*/




create nonclustered index FK_GIG_GEBRUIKER on GEBRUIKER_IN_GROEP (GROEPNAAM ASC)
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
/* Table: RESTAURANT                                            */
/*==============================================================*/
create table RESTAURANT (
                            RESTAURANT_ID        ID         IDENTITY(1,1)          not null,
                            RESTAURANT_NAAM      NAAM                 not null,
                            POSTCODE             POSTCODE             not null,
                            STRAATNAAM           NAAM                 not null,
                            HUISNUMMER           NUMMER               not null,
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
/* Index: FK_RHV_RESTAURANT                                     */
/*==============================================================*/




create nonclustered index FK_RHV_RESTAURANT on RESTAURANT_HEEFT_VOORKEUR (VOORKEUR_NAAM ASC)
go

/*==============================================================*/
/* Index: RESTAURANT_HEEFT_VOORKEUR2_FK                         */
/*==============================================================*/




create nonclustered index RESTAURANT_HEEFT_VOORKEUR2_FK on RESTAURANT_HEEFT_VOORKEUR (RESTAURANT_ID ASC)
go

/*==============================================================*/
/* Table: VOEDINGSRESTRICTIE                                    */
/*==============================================================*/
create table VOEDINGSRESTRICTIE (
                                    RESTRICTIE_NAAM      NAAM                 not null,
                                    TYPE                 NAAM                 not null,
                                    constraint PK_VOEDINGSRESTRICTIE primary key (RESTRICTIE_NAAM, TYPE)
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

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOEDINGSRESTRICTIE')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'TYPE')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE', 'column', 'TYPE'

end


select @CurrentUser = user_name()
    execute sp_addextendedproperty 'MS_Description',
   'type',
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE', 'column', 'TYPE'
go

/*==============================================================*/
/* Table: VOEDINGSRESTRICTIE_IN_RESTAURANT                      */
/*==============================================================*/
create table VOEDINGSRESTRICTIE_IN_RESTAURANT (
                                                  RESTAURANT_ID        ID                   not null,
                                                  RESTRICTIE_NAAM      NAAM                 not null,
                                                  TYPE                 NAAM                 not null,
                                                  constraint PK_VOEDINGSRESTRICTIE_IN_RESTA primary key (RESTAURANT_ID, RESTRICTIE_NAAM, TYPE)
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

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('VOEDINGSRESTRICTIE_IN_RESTAURANT')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'TYPE')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT', 'column', 'TYPE'

end


select @CurrentUser = user_name()
    execute sp_addextendedproperty 'MS_Description',
   'type',
   'user', @CurrentUser, 'table', 'VOEDINGSRESTRICTIE_IN_RESTAURANT', 'column', 'TYPE'
go

/*==============================================================*/
/* Index: FK_VIR_VOEDINGSRESTRICTIE                             */
/*==============================================================*/




create nonclustered index FK_VIR_VOEDINGSRESTRICTIE on VOEDINGSRESTRICTIE_IN_RESTAURANT (RESTAURANT_ID ASC)
go

/*==============================================================*/
/* Index: FK_VIR_RESTAURANT                                     */
/*==============================================================*/




create nonclustered index FK_VIR_RESTAURANT on VOEDINGSRESTRICTIE_IN_RESTAURANT (RESTRICTIE_NAAM ASC,
  TYPE ASC)
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
/* Index: FK_VVG_VOORKEUR                                       */
/*==============================================================*/




create nonclustered index FK_VVG_VOORKEUR on VOORKEUR_VAN_GEBRUIKER (GEBRUIKER_ID ASC)
go

/*==============================================================*/
/* Index: FK_VVG_GEBRUIKER                                      */
/*==============================================================*/




create nonclustered index FK_VVG_GEBRUIKER on VOORKEUR_VAN_GEBRUIKER (VOORKEUR_NAAM ASC)
go

alter table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
    add constraint FK_GEBRUIKE_FK_GHV_GE_GEBRUIKE foreign key (GEBRUIKER_ID)
        references GEBRUIKER (GEBRUIKER_ID)
    go

alter table GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE
    add constraint FK_GEBRUIKE_FK_GHV_VO_VOEDINGS foreign key (RESTRICTIE_NAAM, TYPE)
        references VOEDINGSRESTRICTIE (RESTRICTIE_NAAM, TYPE)
    go

alter table GEBRUIKER_IN_GROEP
    add constraint FK_GEBRUIKE_FK_GIG_GE_GEBRUIKE foreign key (GEBRUIKER_ID)
        references GEBRUIKER (GEBRUIKER_ID)
    go

alter table GEBRUIKER_IN_GROEP
    add constraint FK_GEBRUIKE_FK_GIG_GR_GROEP foreign key (GROEPNAAM)
        references GROEP (GROEPNAAM)
    go

alter table RESTAURANT_HEEFT_VOORKEUR
    add constraint FK_RESTAURA_FK_RHV_RE_RESTAURA foreign key (RESTAURANT_ID)
        references RESTAURANT (RESTAURANT_ID)
    go

alter table RESTAURANT_HEEFT_VOORKEUR
    add constraint FK_RESTAURA_FK_RHV_VO_VOORKEUR foreign key (VOORKEUR_NAAM)
        references VOORKEUR (VOORKEUR_NAAM)
    go

alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
    add constraint FK_VOEDINGS_FK_VIR_RE_RESTAURA foreign key (RESTAURANT_ID)
        references RESTAURANT (RESTAURANT_ID)
    go

alter table VOEDINGSRESTRICTIE_IN_RESTAURANT
    add constraint FK_VOEDINGS_FK_VIR_VO_VOEDINGS foreign key (RESTRICTIE_NAAM, TYPE)
        references VOEDINGSRESTRICTIE (RESTRICTIE_NAAM, TYPE)
    go

alter table VOORKEUR_VAN_GEBRUIKER
    add constraint FK_VOORKEUR_FK_VVG_GE_GEBRUIKE foreign key (GEBRUIKER_ID)
        references GEBRUIKER (GEBRUIKER_ID)
    go

alter table VOORKEUR_VAN_GEBRUIKER
    add constraint FK_VOORKEUR_FK_VVG_VO_VOORKEUR foreign key (VOORKEUR_NAAM)
        references VOORKEUR (VOORKEUR_NAAM)
    go

USE MASTER
GO

--https://stackoverflow.com/questions/1379437/checking-if-a-sql-server-login-already-exists
IF NOT EXISTS
    (SELECT name
     FROM master.sys.server_principals
     WHERE name = 'SamenEtenUser')
BEGIN
    CREATE LOGIN SamenEtenUser WITH PASSWORD = N'Welkom01!'
END

USE sameneten

IF NOT EXISTS
    (SELECT name
     FROM sys.database_principals
     WHERE name = 'SamenEtenUser')
BEGIN
    CREATE USER SamenEtenUser FOR LOGIN SamenEtenUser
	ALTER ROLE [db_owner] ADD MEMBER SamenEtenUser
END


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
    ('user2')

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
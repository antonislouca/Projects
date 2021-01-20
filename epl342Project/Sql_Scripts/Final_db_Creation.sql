-- SET ANSI_NULLS ON
-- GO

-- SET QUOTED_IDENTIFIER ON
-- GO

CREATE TABLE [dbo].[GP_user]
(
    [uid] [int]  IDENTITY(1,1) NOT NULL, -- do we need regular id ?
    [firstName] [nvarchar] (50) NOT NULL,
    [lastName] [nvarchar] (50) NOT NULL,
    [name] AS firstName +' '+lastName,
    [link] [nvarchar] (200) NULL ,
    [birthday] [DATE]  NOT NULL,
    [email] [nvarchar] (50) NULL,
    [website] [nvarchar] (150)  NULL,
    [currentWork] [int] NULL,
    [username] [nvarchar] (50) NOT NULL DEFAULT 'username',
    [password] [nvarchar] (50) NOT NULL DEFAULT 'password',
    [hometown] [int]  NULL,
    [location] [int] NULL,
    [gender] [BIT] NULL,                 -- 0 is for MALE, 1 is for FEMALE
    [verified] [BIT] NOT NULL DEFAULT 0, -- 1 MEANS TRUE, 0 MEANS FALSE
    [numFriends] [int] NOT NULL DEFAULT 0,
    [profPic] [nvarchar] (200) NULL DEFAULT'',
    [FriendListprivacy] [int] NULL DEFAULT 1
    CONSTRAINT [PK_user] PRIMARY KEY (uid) --NONCLUSTERED
)
GO

CREATE TABLE [dbo].[GP_album]
(
    [aid] [int] IDENTITY(1,1) NOT NULL,
    [fromid] [int] NOT NULL,            -- id of creator of the album
    [name] [nvarchar] (50) NOT NULL,    -- album title
    [description] [nvarchar] (200) NULL DEFAULT 'No description',
    [location] [int] NULL,  -- id of relationship FK, maybe it can be null
    [link] [nvarchar] (200) NOT NULL,
    [privacy] [int] NOT NULL DEFAULT 1, -- privacy id FK
    [count] INT NOT NULL DEFAULT 0,
  --  [comments] [nvarchar] (1500) NULL,

    CONSTRAINT [PK_album] PRIMARY KEY (aid)
)
GO

CREATE TABLE [dbo].[GP_privacy]
(
    [prid] [int] IDENTITY(1,1) NOT NULL,
    [type] [nvarchar] (30) NOT NULL DEFAULT N'OPEN',

    CONSTRAINT [PK_prid] PRIMARY KEY (prid),

        CONSTRAINT [chk_privacy] CHECK (
            [type] in (N'OPEN',N'NETWORK',N'FRIEND',N'CLOSED')
        --int this sequence 1         2          3        4
        )
)
GO

CREATE TABLE [dbo].[GP_photos]
(
    [pid] [int] IDENTITY(1,1) NOT NULL,
    [fromid] [int] NOT NULL,            -- id of creator of the photo
    [name] [nvarchar] (50) NOT NULL,
    [source] [nvarchar](200) NOT NULL,  -- path to photo where its stored.
    [height] [int] NOT NULL,
    [width] [int] NOT NULL,
    [link] [nvarchar] (200) NOT NULL DEFAULT 'recourses/_phSxWIShM8.jpg',
    [albumID] [int] NULL,
    [numOfLikes] [int] NOT NULL DEFAULT 0,
    [privacy] [int] NOT NULL DEFAULT 1,
    CONSTRAINT [PK_photos] PRIMARY KEY (pid)
)
GO

CREATE TABLE [dbo].[GP_videos]
(
    [vid] [int] IDENTITY(1,1) NOT NULL,
    [fromid] [int] NOT NULL,            -- id of creator of the video
    [name] [nvarchar] (100) NOT NULL,   -- title
    [message] [nvarchar](200) NOT NULL,
    [link] [nvarchar] (200) NOT NULL,   -- link to particular video
    [length] [int] NOT NULL,
    [description] [nvarchar] (200)  NULL DEFAULT 'No Description provided.',
   -- [comments] [nvarchar] (200) NOT NULL,
    [privacy] [int] NOT NULL DEFAULT 1,
    CONSTRAINT [PK_videos] PRIMARY KEY (vid)
)
GO

CREATE TABLE [dbo].[GP_links]
(
    [lid] [int] IDENTITY(1,1) NOT NULL,
    [fromid] [int] NOT NULL,            -- id of creator of the LINK
    [link] [nvarchar](200) NOT NULL,    --URL OF LINK
    [name] [nvarchar] (50) NOT NULL,
    [caption] [nvarchar] (50) NULL DEFAULT 'No Caption provided.',
    [description] [nvarchar] (200) NULL DEFAULT 'No Description provided.',
    [message] [nvarchar] (200) NULL DEFAULT 'No message',
    [privacy] [int] NOT NULL DEFAULT 1,
    CONSTRAINT [PK_links] PRIMARY KEY (lid)
)
GO

CREATE TABLE [dbo].[GP_events]
(
    [eid] [int] IDENTITY(1,1) NOT NULL,
    [owner] [int] NOT NULL,             -- FK TO USERid of creator of the event
    [name] [nvarchar] (50) NOT NULL,    -- name of event
    [description] [nvarchar] (200) NULL DEFAULT 'No Description provided.',
    [start_time] [datetime] NOT NULL,
    [end_time] [datetime] NOT NULL,
    [location] [nvarchar](200) NULL DEFAULT 'No further details',
                                            -- further details
    [venue] [int]  NULL,                 -- FK to city list
    [privacy] [int] NOT NULL DEFAULT 1, -- FK to privacy table

    CONSTRAINT [PK_events] PRIMARY KEY (eid)
)
GO

CREATE TABLE [dbo].[GP_friendswith]
(
    [uid1] [int] NOT NULL,
    [uid2] [int] NOT NULL,
    CONSTRAINT [PK_frinedswith] PRIMARY KEY (uid1,uid2)
)
GO

CREATE TABLE [dbo].[GP_activities]
(
    [activityid] [int] IDENTITY(1,1) NOT NULL,
    [type] [nvarchar] (50) NULL,
    CONSTRAINT [PK_activityP] PRIMARY KEY (activityid)
)
GO

CREATE TABLE [dbo].[GP_interestedin]
(
    [userIntid] [int] NOT NULL,
    [actid] [int] NOT NULL,
    CONSTRAINT [PK_interest] PRIMARY KEY (userIntid, actid)
)
GO

CREATE TABLE [dbo].[GP_likes]
(
    [idlikes] [int] IDENTITY(1,1) NOT NULL,
    [userid] [int] NOT NULL,
    [photoid] [int] NOT NULL,
    CONSTRAINT [PK_likes] PRIMARY KEY (idlikes)
)
GO

CREATE TABLE [dbo].[GP_commentsOnAlbums]
(
    [userid] [int] NOT NULL,
    [albumid] [int] NOT NULL,
    [description] [nvarchar] (500) NOT NULL,
    CONSTRAINT [PK_commentsonAlbums] PRIMARY KEY (albumid,userid)
)
GO

CREATE TABLE [dbo].[GP_commentsOnVideos]
(
    [userid] [int] NOT NULL,
    [videoid] [int] NOT NULL,
    [description] [nvarchar] (500) NOT NULL,
    CONSTRAINT [PK_commentsonvideos] PRIMARY KEY (videoid,userid)
)
GO

CREATE TABLE [dbo].[GP_location](
    [loc_ID] int IDENTITY(1,1) NOT NULL,
    [city] [nvarchar] (50) NOT NULL,
    CONSTRAINT [PK_comments] PRIMARY KEY (loc_ID)
)
GO

CREATE TABLE [dbo].[GP_participates_in]
(
    [userid] [int] NOT NULL,
    [eventid] [int] NOT NULL,
    CONSTRAINT [PK_participatesin] PRIMARY KEY (eventid,userid)
)
GO
/*
CREATE TABLE [dbo].[GP_hasVideo]
(
    [userid] [int] NOT NULL,
    [videoid] [int] NOT NULL,
    CONSTRAINT [PK_hasVideo] PRIMARY KEY (videoid,userid)
)
GO
*/

CREATE TABLE [dbo].[GP_friendRequest]
(
    [userid1] [int] NOT NULL,
    [userid2] [int] NOT NULL,
    [ignored] [bit] NOT NULL DEFAULT 0, -- 1: is ignored, 0: is not ignored
    CONSTRAINT [PK_friendsWith] PRIMARY KEY (userid1,userid2)
)
GO

CREATE TABLE [dbo].[GP_works]
(
    [workid] [int] IDENTITY(1,1) NOT NULL,
    [type] [nvarchar] (100) NULL, --company name
    CONSTRAINT [PK_works] PRIMARY KEY (workid)
)
GO

CREATE TABLE [dbo].[GP_worksFor]
(
    [workid] [int] NOT NULL,
    [userid] [int] NOT NULL,
    CONSTRAINT [PK_workFor] PRIMARY KEY (workid,userid)
)
GO

CREATE TABLE [dbo].[GP_education]
(
    [educationid] [int] IDENTITY(1,1) NOT NULL,
    [institution] [nvarchar] (100) NULL,
    CONSTRAINT [PK_education] PRIMARY KEY (educationid)
)
GO

CREATE TABLE [dbo].[GP_educatedIn]
(
    [educationid] [int] NOT NULL,
    [userid] [int] NOT NULL,
    CONSTRAINT [PK_educatedIn] PRIMARY KEY (educationid,userid)
)
GO

CREATE TABLE [dbo].[GP_quotes]
(
    [quoteid] [int] IDENTITY(1,1) NOT NULL,
    [type] [nvarchar] (500) NULL,
    CONSTRAINT [PK_quote] PRIMARY KEY (quoteid)
)
GO

CREATE TABLE [dbo].[GP_quotedOn]
(
    [quoteid] [int] NOT NULL,
    [userid] [int] NOT NULL,
    CONSTRAINT [PK_quotedOn] PRIMARY KEY (quoteid,userid)
)
GO


CREATE TABLE [dbo].[GP_updates]
(
    [updateid] [int] IDENTITY(1,1) NOT NULL,
    [upType] NVARCHAR (3000)  NULL,
    [upClass] NVARCHAR (20)  NOT NULL,
    [userid] [int] NOT NULL,
    [date] [datetime] NOT NULL,
    CONSTRAINT [PK_updates] PRIMARY KEY (updateid)
)
GO
--- Types
--1 ->PHOTOS
--2 ->ALBUMS
--3 ->LINKS
--4 ->EVENTS
--5 ->VIDEOS

/*CREATE TABLE [dbo].[GP_updateTypes](
    [typeid] [int] IDENTITY(1,1) NOT NULL,
    [upClass] [nvarchar] (50) NOT NULL,
    CONSTRAINT [PK_updateTypes] PRIMARY KEY (typeid))
GO*/
--FOREIGN KEY CONSTRAINS--------------------------------------------------------
--------------------------------------------------------------------------------
/*
ALTER TABLE [dbo].[GP_updates]  WITH CHECK ADD
CONSTRAINT [FK_upType] FOREIGN KEY([typeid])
REFERENCES [dbo].[GP_updateTypes] ([typeid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
*/

--------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_links]  WITH CHECK ADD
CONSTRAINT [FK_privacyLink] FOREIGN KEY([privacy])
REFERENCES [dbo].[GP_privacy] ([prid])
ON DELETE SET DEFAULT ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[GP_user]  WITH CHECK ADD
CONSTRAINT [FK_privacyUser] FOREIGN KEY([FriendListprivacy])
REFERENCES [dbo].[GP_privacy] ([prid])
ON DELETE SET DEFAULT ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[GP_videos]  WITH CHECK ADD
CONSTRAINT [FK_privacyVideos] FOREIGN KEY([privacy])
REFERENCES [dbo].[GP_privacy] ([prid])
ON DELETE SET DEFAULT ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[GP_photos]  WITH CHECK ADD
CONSTRAINT [FK_privacyPhotos] FOREIGN KEY([privacy])
REFERENCES [dbo].[GP_privacy] ([prid])
ON DELETE SET DEFAULT ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[GP_videos]  WITH CHECK ADD
CONSTRAINT [FK_fromidVideos] FOREIGN KEY([fromid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE NO ACTION ON UPDATE NO ACTION
--ON DELETE NO ACTION ON UPDATE CASCADE
GO
--------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_updates]  WITH CHECK ADD
CONSTRAINT [FK_userIdUp] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
--------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_quotedOn]  WITH CHECK ADD
CONSTRAINT [FK_quote] FOREIGN KEY([quoteid])
REFERENCES [dbo].[GP_quotes] ([quoteid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_quotedOn]  WITH CHECK ADD
CONSTRAINT [FK_userQ] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE  ON UPDATE CASCADE
GO
--------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_user]  WITH CHECK ADD
CONSTRAINT [FK_hometown] FOREIGN KEY([hometown])
REFERENCES [dbo].[GP_location] ([loc_ID])
ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[GP_user]  WITH CHECK ADD
CONSTRAINT [FK_location] FOREIGN KEY([location])
REFERENCES [dbo].[GP_location] ([loc_ID])
ON DELETE NO ACTION ON UPDATE NO ACTION

GO
--------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_educatedIn]  WITH CHECK ADD
CONSTRAINT [FK_educatedUser] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_educatedIn]  WITH CHECK ADD
CONSTRAINT [FK_educatedEdu] FOREIGN KEY([educationid])
REFERENCES [dbo].[GP_education] ([educationid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_worksFor]  WITH CHECK ADD
CONSTRAINT [FK_WorkforUser] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_worksFor]  WITH CHECK ADD
CONSTRAINT [FK_WorkforWork] FOREIGN KEY([workid])
REFERENCES [dbo].[GP_works] ([workid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
-----------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_album]  WITH CHECK ADD
CONSTRAINT [FK_Aowner] FOREIGN KEY([fromid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[GP_album]  WITH CHECK ADD
CONSTRAINT [FK_loc] FOREIGN KEY([location])
REFERENCES [dbo].[GP_location] ([loc_ID])
ON DELETE SET NULL ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[GP_album]  WITH CHECK ADD
CONSTRAINT [FK_privacy] FOREIGN KEY([privacy])
REFERENCES [dbo].[GP_privacy] ([prid])
-- ON DELETE SET DEFAULT ON UPDATE CASCADE
 ON DELETE NO ACTION ON UPDATE NO ACTION
GO


--------------------------------------------------------------------------------

ALTER TABLE [dbo].[GP_photos]  WITH CHECK ADD
CONSTRAINT [FK_album] FOREIGN KEY([albumID])
REFERENCES [dbo].[GP_album] ([aid])
ON DELETE NO ACTION ON UPDATE NO ACTION
--ON DELETE CASCADE ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[GP_photos]  WITH CHECK ADD
CONSTRAINT [FK_Powner] FOREIGN KEY([fromid])
REFERENCES [dbo].[GP_user] ([uid])
--ON DELETE CASCADE ON UPDATE CASCADE
ON DELETE NO ACTION ON UPDATE NO ACTION
GO

--------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_links]  WITH CHECK ADD
CONSTRAINT [FK_Lowner] FOREIGN KEY([fromid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE NO ACTION ON UPDATE NO ACTION
--ON DELETE CASCADE ON UPDATE CASCADE
GO

--------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_events]  WITH CHECK ADD
CONSTRAINT [FK_Eowner] FOREIGN KEY([owner])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_events]  WITH CHECK ADD
CONSTRAINT [FK_locE] FOREIGN KEY([venue])
REFERENCES [dbo].[GP_location] ([loc_ID])
ON DELETE SET NULL ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_events]  WITH CHECK ADD
CONSTRAINT [FK_privacyEvents] FOREIGN KEY([privacy])
REFERENCES [dbo].[GP_privacy] ([prid])
ON DELETE NO ACTION ON UPDATE NO ACTION
--ON DELETE SET DEFAULT ON UPDATE CASCADE
GO
--------------------------------------------------------------------------------


ALTER TABLE [dbo].[GP_friendswith]  WITH CHECK ADD
CONSTRAINT [FK_friended1] FOREIGN KEY([uid1])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_friendswith]  WITH CHECK ADD
CONSTRAINT [FK_friended2] FOREIGN KEY([uid2])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE NO ACTION ON UPDATE NO ACTION
GO
-----------------------------------------------------------------


ALTER TABLE [dbo].[GP_interestedin]  WITH CHECK ADD
CONSTRAINT [FK_activties] FOREIGN KEY([actid])
REFERENCES [dbo].[GP_activities] ([activityid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_interestedin]  WITH CHECK ADD
CONSTRAINT [FK_userInter] FOREIGN KEY([userIntid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO

--------------------------------------------------------------------------------

ALTER TABLE [dbo].[GP_likes]  WITH CHECK ADD
CONSTRAINT [FK_likes] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_likes]  WITH CHECK ADD
CONSTRAINT [FK_likesPhoto] FOREIGN KEY([photoid])
REFERENCES [dbo].[GP_photos] ([pid])
ON DELETE NO ACTION ON UPDATE NO ACTION
GO

--------------------------------------------------------------------------------

ALTER TABLE [dbo].[GP_participates_in]  WITH CHECK ADD
CONSTRAINT [FK_userPart] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE NO ACTION ON UPDATE NO ACTION
--ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_participates_in]  WITH CHECK ADD
CONSTRAINT [FK_eventPart] FOREIGN KEY([eventid])
REFERENCES [dbo].[GP_events] ([eid])
ON DELETE CASCADE ON UPDATE CASCADE
--ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-------------------------------------------------------------------------------

ALTER TABLE [dbo].[GP_commentsOnAlbums]  WITH CHECK ADD
CONSTRAINT [FK_userCommAlbum] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_commentsOnAlbums]  WITH CHECK ADD
CONSTRAINT [FK_albumComm] FOREIGN KEY([albumid])
REFERENCES [dbo].[GP_album] ([aid])
ON DELETE NO ACTION ON UPDATE NO ACTION
GO
-------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_commentsOnVideos]  WITH CHECK ADD
CONSTRAINT [FK_userCommVideo] FOREIGN KEY([userid])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_commentsOnVideos]  WITH CHECK ADD
CONSTRAINT [FK_VideoCommVideo] FOREIGN KEY([videoid])
REFERENCES [dbo].[GP_videos] ([vid])
ON DELETE NO ACTION ON UPDATE NO ACTION
GO

------------------------------------------------------------------------------

-------------------------------------------------------------------------------
ALTER TABLE [dbo].[GP_friendRequest]  WITH CHECK ADD
CONSTRAINT [FK_friendR1] FOREIGN KEY([userid1])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GP_friendRequest]  WITH CHECK ADD
CONSTRAINT [FK_friendR2] FOREIGN KEY([userid2])
REFERENCES [dbo].[GP_user] ([uid])
ON DELETE NO ACTION ON UPDATE NO ACTION
GO

--------------------------------------------------------------------------------
--INDEX CREATION
--------------------------------------------------------------------------------
CREATE  INDEX idxup ON [dbo].[GP_updates] (upClass);
CREATE  INDEX idxnameA ON [dbo].[GP_album] (name);
CREATE  INDEX idxnameP ON [dbo].[GP_photos] (name);

--CREATE  INDEX idxuserName ON [dbo].[GP_user] (name);
--CREATE  INDEX idxBdAay ON [dbo].[GP_user] (birthday);
--CREATE  INDEX idxcity ON [dbo].[GP_user] (hometown);


--------------------------------------------------------------------------------
--CREATE VIEWS
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--ALBUM VIEW
--------------------------------------------------------------------------------
GO
CREATE VIEW [dbo].[GP_albumUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass ='albums'
   GROUP BY UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition
--DROP VIEW[dbo].[GP_albumUpdates]
GO
--------------------------------------------------------------------------------
-- PHOTOS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_photosUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass ='photos'
   GROUP BY UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition

GO
--------------------------------------------------------------------------------
--VIDEOS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_videoUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass ='videos'
  GROUP BY UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition
GO

--------------------------------------------------------------------------------
--LINKS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_linkUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass ='links'
   GROUP BY UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition


GO
--------------------------------------------------------------------------------
--EVENTS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_eventUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType],UP.upClass,UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass = 'events'
   GROUP BY UP.[updateid], UP.[upType],UP.upClass, UP.[userid], UP.[date]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition

GO
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--TRIGGER FOR PHOTOS UPDATES
--------------------------------------------------------------------------------
CREATE TRIGGER [trupdateOnPhotos] ON [dbo].[GP_photos]
FOR Update,INSERT AS

BEGIN

    INSERT INTO [dbo].[GP_updates](
        [upType],
        [upClass],
        [userid],
        [date]
    )
    SELECT
        'Photo id: '+CAST(i.pid as nvarchar(4))+
        +CAST(i.name as nvarchar(50)),
        'photos',
        i.fromid,
        GETDATE()

    FROM inserted i

END

GO

--------------------------------------------------------------------------------
--TRIGGER FOR Albums UPDATES
--------------------------------------------------------------------------------
CREATE TRIGGER [trupdateOnAlbums] ON [dbo].[GP_album]
FOR Update,INSERT AS

BEGIN
declare @loc as nvarchar(50)
declare @privacy as nvarchar(30)
declare @locOUT as nvarchar(50)
declare @privacyOut as nvarchar(30)
SET @loc= (select location from inserted)
SET @privacy=(select privacy from inserted)

 EXEC spGetLocFromID @loc,@locOUT OUT
 EXEC  spGetPrivacy @privacy,@privacyOut  OUT
 -- inserted.privacy
    INSERT INTO [dbo].[GP_updates](
        [upType],
        [upClass],
        [userid],
        [date]
    )
    SELECT
        'Album: '+CAST(i.aid as nvarchar(4))+
        CAST(i.name as nvarchar(50))
        +' '+CAST(i.[description] as nvarchar(200))+' '+
        @loc
        +' '+@privacy,
        'albums',
        i.fromid,
        GETDATE()
    FROM inserted i

END

GO


--------------------------------------------------------------------------------
--TRIGGER FOR VIDEOS UPDATES
--------------------------------------------------------------------------------
CREATE TRIGGER [trupdateOnVideos] ON [dbo].[GP_videos]
FOR Update,INSERT AS

BEGIN
    INSERT INTO [dbo].[GP_updates](
        [upType],
        [upClass],
        [userid],
        [date]
    )
    SELECT
        'Video:'+CAST(i.vid as nvarchar(4))+' '+
        CAST(i.name as nvarchar(100))
        +' '+CAST(i.[message] as nvarchar(200))+' '+
        CAST(i.[description] as nvarchar(200)),
        'videos',
        i.fromid,
        GETDATE()
    FROM inserted i

END

GO
--------------------------------------------------------------------------------
--TRIGGER FOR Links UPDATES
--------------------------------------------------------------------------------
CREATE TRIGGER [trupdateOnLinks] ON [dbo].[GP_links]
FOR Update,INSERT AS

BEGIN
    INSERT INTO [dbo].[GP_updates](
        [upType],
        [upClass],
        [userid],
        [date]
    )
    SELECT
        'Link:'+CAST(i.lid as nvarchar(4))+''+
        CAST(i.name as nvarchar(50))
        +' '+CAST(i.[description] as nvarchar(50))+' '+
        CAST(i.[message] as nvarchar(200))
        +' '+CAST(i.caption as nvarchar(25)),
        'links',
        i.fromid,
        GETDATE()
    FROM inserted i

END

GO
--------------------------------------------------------------------------------
--TRIGGER FOR EVENTS UPDATES
--------------------------------------------------------------------------------
CREATE TRIGGER [trupdateOnEvents] ON [dbo].[GP_events]
FOR Update,INSERT AS

BEGIN
declare @loc as nvarchar(50)
declare @privacy as nvarchar(30)
declare @locOUT as nvarchar(50)
declare @privacyOut as nvarchar(30)
SET @loc= (select venue from inserted)
SET @privacy=(select privacy from inserted)

 EXEC spGetLocFromID @loc,@locOUT OUT
 EXEC  spGetPrivacy @privacy,@privacyOut  OUT
    INSERT INTO [dbo].[GP_updates](
        [upType],
        [upClass],
        [userid],
        [date]
    )
    SELECT
        'Event:'+CAST(i.eid as nvarchar(4))+' '+
        CAST(i.name as nvarchar(50))
        +' '+CAST(i.[description] as nvarchar(50))+' '+
       @locOUT
        +' '+@privacyOut+' '+
        CAST(i.location as nvarchar(200))+' StartTime '+
        CAST(i.start_time as nvarchar(20))+' EndTime '+
        CAST(i.end_time as nvarchar(20)),
        'events',
        i.owner,
        GETDATE()
    FROM inserted i

END

GO
--------------------------------------------------------------------------------
--TRIGGER FOR COUNT IN ALBUM TABLE
--assuming one pic is inserted at a time
--------------------------------------------------------------------------------
CREATE TRIGGER [TR_Album_count] ON [dbo].[GP_photos]
FOR INSERT, DELETE AS
BEGIN
    IF EXISTS  (SELECT * FROM deleted) -- DELETE was used
    BEGIN
        UPDATE [dbo].[GP_album]
        SET count = count - 1
        FROM [dbo].[GP_album], deleted
        WHERE deleted.albumID = dbo.GP_album.aid
    END
    ELSE    -- INSERT was used
    BEGIN
        UPDATE [dbo].[GP_album]
        SET count = count + 1
        FROM [dbo].[GP_album], inserted
        WHERE inserted.albumID = dbo.GP_album.aid
    END
END

GO
--------------------------------------------------------------------------------
--TRIGGER FOR numFriends in User
--------------------------------------------------------------------------------
CREATE TRIGGER [TR_User_numFriends] ON [dbo].[GP_friendswith]
FOR INSERT, DELETE AS
BEGIN
    IF EXISTS  (SELECT * FROM deleted) -- DELETE was used
    BEGIN
        UPDATE [dbo].[GP_user]
        SET numFriends = numFriends - 1
        FROM [dbo].[GP_user], deleted
        WHERE deleted.uid1 = dbo.GP_user.uid
    END
    ELSE    -- INSERT was used
    BEGIN
        UPDATE [dbo].[GP_user]
        SET numFriends = numFriends + 1
        FROM [dbo].[GP_user], inserted
        WHERE inserted.uid1 = dbo.GP_user.uid
    END
END

GO

--------------------------------------------------------------------------------
--TRIGGER FOR currentWork in User
--------------------------------------------------------------------------------
CREATE TRIGGER [TR_User_currentWork] ON [dbo].[GP_worksFor]
FOR INSERT, DELETE AS
BEGIN
    IF EXISTS  (SELECT * FROM deleted) -- DELETE was used
    BEGIN
        IF EXISTS
            (
                SELECT U.uid
                FROM deleted D, GP_user U
                WHERE D.userid = U.uid
            )
            UPDATE [dbo].[GP_user]
            SET GP_user.currentWork = NULL
            FROM [dbo].[GP_user], deleted
            WHERE deleted.userid = dbo.GP_user.uid
    END
    ELSE    -- INSERT was used
    BEGIN
        UPDATE [dbo].[GP_user]
        SET GP_user.currentWork = inserted.workid
        FROM [dbo].[GP_user], inserted
        WHERE inserted.userid = dbo.GP_user.uid
    END
END

GO

--------------------------------------------------------------------------------
--TRIGGER FOR numOfLikes in Photos
--------------------------------------------------------------------------------
CREATE TRIGGER [TR_User_numOfLikes] ON [dbo].[GP_likes]
FOR INSERT, DELETE AS
BEGIN
    IF EXISTS  (SELECT * FROM deleted) -- DELETE was used
    BEGIN
        UPDATE [dbo].[GP_photos]
        SET numOfLikes = numOfLikes - 1
        FROM [dbo].[GP_photos], deleted
        WHERE deleted.photoid = dbo.GP_photos.pid
    END
    ELSE    -- INSERT was used
    BEGIN
        UPDATE [dbo].[GP_photos]
        SET numOfLikes = numOfLikes + 1
        FROM [dbo].[GP_photos], inserted
        WHERE inserted.photoid = dbo.GP_photos.pid
    END
END

GO

--SELECT * FROM GP_photos
--------------------------------------------------------------------------------
--STORED PROCEDURES
--------------------------------------------------------------------------------
--Question C
CREATE PROC spFindUserEvents -- drop PROC spFindUserEvents
   @Name NVARCHAR(200),
   @user int
--@EventL NVARCHAR(200) OUT
AS

SET NOCOUNT ON;
SELECT  P.eid, UR.username, P.name, P.[description], P.start_time, P.end_time,
    P.[location], L.city, P.privacy
FROM [dbo].[GP_events] AS P, dbo.GP_privacy as PR, [dbo].[GP_location] AS L,
    GP_user UR
WHERE P.name=@Name AND P.privacy = PR.prid AND L.loc_ID=p.venue
        AND UR.uid = P.[owner] 

RETURN
GO
CREATE PROC spFindUserLinks
   @Name NVARCHAR(200),
   @user int
  -- @link NVARCHAR(200) OUT
AS

SET NOCOUNT ON;
SELECT P.lid , P.fromid , P.link , P.[name] ,
      P.[caption], P.[description] , P.[message] , PR.type
FROM [dbo].[GP_LINKS] AS P, dbo.GP_privacy as PR
WHERE P.name=@Name AND P.privacy=PR.prid
RETURN
GO

CREATE PROC spFindUserVideos
@Name NVARCHAR(200),
@user int
 --@videolink NVARCHAR(200) OUT
AS
SET NOCOUNT ON;
SELECT P.vid , P.fromid , P.name , P.[message] , P.[link]  , P. [length],
    P.[description] , PR.[type]
FROM [dbo].[GP_videos] AS P, dbo.GP_privacy as PR
WHERE P.message=@Name AND P.fromid=@user AND PR.[type]=P.privacy

RETURN
GO


create proc spGetVideoComments
@aid int
as
select A.description
from [dbo].[GP_commentsOnVideos] as A
where A.videoid=@aid
RETURN
GO

CREATE PROC spFindUserPhotos
   @Name NVARCHAR(200),
   @user int
AS
SET NOCOUNT ON;

SELECT PH.pid, PH.fromid, PH.name,
   PH.source, PH.height, PH.width,
   PH.link, PH.albumID, PC.type, PH.numOfLikes
FROM [dbo].[GP_photos] AS PH, [dbo].[GP_privacy] PC
WHERE PH.Name = @Name  AND PH.privacy = PC.prid

RETURN
GO

create proc spGetAlbumComments
@aid int
as
select A.description
from [dbo].[GP_commentsOnAlbums] as A
where A.albumid=@aid
RETURN
GO

CREATE PROC spFindUserAlbums

   @Name NVARCHAR(200),

   @user int

AS

SET NOCOUNT ON;

DECLARE @albumID INT
SET @albumID =(
SELECT A.aid
FROM [dbo].[GP_album] AS A
WHERE A.Name=@Name AND A.fromid=@user
   )


select  P.[aid],P.[fromid]  , P.[name]  ,P.[description]  , P.[location]  ,
  P.[link] ,PR.[type] ,P.[count]
from [dbo].[GP_album] AS P,dbo.GP_privacy as PR
WHERE  P.privacy=PR.prid  and @albumID=P.aid
RETURN
GO

-------Question D
CREATE PROC spFindEducation
@value NVARCHAR (50)
AS
--SET @value ='ATOS'
SET NOCOUNT ON;
SELECT U.uid--, U.username,U.name
        FROM [dbo].GP_user as U
        WHERE U.uid IN (
            SELECT distinct EI.userid
            FROM [dbo].[GP_educatedIn] AS EI,dbo.GP_education as E
            WHERE E.educationid =EI.educationid and E.institution=@value
        )


RETURN
GO

CREATE PROC spFindWorks
@value NVARCHAR (50)
AS

SET NOCOUNT ON;
 SELECT U.uid--, U.username,U.name
        FROM [dbo].GP_user as U
        WHERE U.uid IN (
            SELECT distinct WF.userid
            FROM [dbo].[GP_worksFor] AS WF,[dbo].[GP_works] AS W
            WHERE W.workid =WF.workid and W.type=@value
        )
RETURN
GO


CREATE PROC spComplexSearch
@searchType BIT,
@value NVARCHAR (MAX)
AS
 SET NOCOUNT ON;
 CREATE table #temp (uid int)--,username nvarchar(50),name nvarchar(50))
 Create table #splitedVal(types nvarchar(50))
 insert into #splitedVal
 select * from STRING_SPLIT(@value, ',')
 DECLARE c CURSOR LOCAL READ_ONLY
 FOR SELECT types from #splitedVal

 DECLARE @searchVal NVARCHAR(50)
    OPEN c
    FETCH NEXT FROM c into @searchVAl
IF @searchType=0
    BEGIN
        WHILE @@FETCH_STATUS =0
        BEGIN
            INSERT INTO #temp
            EXEC spFindEducation @searchVal
            FETCH NEXT FROM c into @searchVal
        END

    END
ELSE
    BEGIN
        WHILE @@FETCH_STATUS =0
        BEGIN
            INSERT INTO #temp
            EXEC spFindWorks @searchVal
            FETCH NEXT FROM c into @searchVal
        END
    END

CLOSE c
DEALLOCATE c

SELECT uid, count(uid) as commonMatches --this might need a distinct not sure
FROM #temp
GROUP BY uid
order BY COUNT(uid) DESC

RETURN
GO


--------------------------------------------------------------------------------

----------------------Search all------------------------------------------------
CREATE PROC spSearchALL
@name NVARCHAR (50),
@bdate NVARCHAR (50),
@loc NVARCHAR (50),
@function int

AS
 SET NOCOUNT ON;
CREATE TABLE #temptable1 (uid int)--,name nvarchar(100)
--,email nvarchar(50),birthday date,location nvarchar(50) ,hometown nvarchar(50))
CREATE TABLE #temptable2 (uid int)--,name nvarchar(100)
--,email nvarchar(50),birthday date,location nvarchar(50) ,hometown nvarchar(50))
CREATE TABLE #temptable3 (uid int)--,name nvarchar(100)
--,email nvarchar(50),birthday date,location nvarchar(50) ,hometown nvarchar(50))

--DROP TABLE #temptable1
    IF @function=1
        BEGIN
        INSERT INTO #temptable1
        EXEC spSearchName @name
        select *
        from #temptable1
        END
    ELSE IF @function=2
        BEGIN
        INSERT INTO #temptable2
        EXEC spSearchDate @bdate
        select * from #temptable2
        END
    ELSE IF @function=3
        BEGIN
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
        select * from #temptable2
        END
    ELSE IF @function=4     --name &date
        BEGIN
        INSERT INTO #temptable1
        EXEC spSearchName @name
        INSERT INTO #temptable2
        EXEC spSearchDate @bdate
    SELECT *
    FROM #temptable1,#temptable2
    WHERE #temptable1.uid=#temptable2.uid

        END
    ELSE IF @function=5     --name & loc
       BEGIN
        INSERT INTO #temptable1
        EXEC spSearchName @name
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
    SELECT *
    FROM #temptable1,#temptable2
    WHERE #temptable1.uid=#temptable2.uid

       END
    ELSE IF @function=6     --loc&date
       BEGIN
        INSERT INTO #temptable1
        EXEC spSearchDate @bdate
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
    SELECT *
    FROM #temptable1,#temptable2
    WHERE #temptable1.uid=#temptable2.uid

        END
    ELSE
       BEGIN    --loc&date&name
        INSERT INTO #temptable1
        EXEC spSearchDate @bdate
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
        INSERT INTO #temptable3
        EXEC spSearchName @name

    SELECT *
    FROM #temptable1,#temptable2,#temptable3
    WHERE #temptable1.uid=#temptable2.uid AND #temptable2.uid=#temptable3.uid

        END
RETURN

GO

CREATE PROC spSearchLocation
@loc NVARCHAR (50)

AS
 SET NOCOUNT ON;
    SELECT U.uid--,U.name,U.link ,U.birthday,U.email,U.website
  --  ,U.[location],U.hometown,WF.type,
--U.gender,U.numFriends,U.profPic,U.FrindListPrivacy
    FROM [dbo].[GP_user] AS U, [dbo].[GP_location] as L--,GP_worksFor AS WF
    WHERE L.loc_ID=U.location and @loc=L.city --and WF.workid=U.currentWork
GO
-------------


CREATE PROC spSearchDate
@bdate NVARCHAR (50)

AS
 SET NOCOUNT ON;
    SELECT  U.uid--,U.name,U.link ,U.birthday,U.email,U.website
    --,U.[location],U.hometown,WF.[type],
  --  U.gender,U.numFriends,U.profPic,U.FrindListPrivacy
    FROM [dbo].[GP_user] AS U--,dbo.GP_worksFor AS WF
    WHERE U.birthday=CONVERT(date,@bdate,103)--and WF.workid=U.currentWork

GO

CREATE PROC spSearchName
@name NVARCHAR (100)

AS
 SET NOCOUNT ON;
    SELECT U.uid--,U.name,U.link ,U.birthday,U.email,U.website
    --,U.[location],U.hometown,WF.[type],
  --  U.gender,U.numFriends,U.profPic,U.FrindListPrivacy
    FROM [dbo].[GP_user] AS U--,dbo.GP_worksFor AS WF
    WHERE U.name=@name --and WF.workid=U.currentWork

GO

--Query H
CREATE proc spFindfriends
@user INT
AS
 SET NOCOUNT ON;
SELECT U.uid  ,U.name
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@user AND U.uid=FW.uid2
RETURN
GO

--------------------------------------------------------------------------------
--delete friend
--------------------------------------------------------------------------------
CREATE PROC spDeleteFriend
@user1 INT,
@user2 INT
AS
 SET NOCOUNT ON;
 BEGIN TRAN

 /*if uid is not given then we have to query by name and declare an inner
 variable that will after represent the uid of user2*/
  DELETE from dbo.GP_friendswith
   WHERE dbo.GP_friendswith.uid1 = @user1 AND dbo.GP_friendswith.uid2 =@user2
    OR dbo.GP_friendswith.uid1=@user2 and
   dbo.GP_friendswith.uid2=@user1
COMMIT

GO

---Query J for albums-----------------------------------------------------------
CREATE PROC spFindUpdatesAlbum
@levels INT,
@userid INT
AS
 SET NOCOUNT ON;
 with AlbumTest AS(
    SELECT *,ROW_NUMBER()OVER(order by [date] desc)AS rownumber
    FROM [dbo].[GP_albumUpdatesView]
     WHERE @userid=[dbo].[GP_albumUpdatesView].userid
)
SELECT *
FROM AlbumTest
WHERE rownumber BETWEEN 0 and @levels

GO
---Query J for photos-----------------------------------------------------------
CREATE PROC spFindUpdatesPhotos
@levels INT,
@userid INT
AS
 SET NOCOUNT ON;
 with PhotosTest AS(
    SELECT *,ROW_NUMBER()OVER(order by [date] desc)AS rownumber
    FROM [dbo].[GP_photosUpdatesView]
    WHERE @userid=[dbo].[GP_photosUpdatesView].userid
)
SELECT *
FROM PhotosTest
WHERE rownumber BETWEEN 0 and @levels

GO

---Query J for video-----------------------------------------------------------
CREATE PROC spFindUpdatesVideos
@levels INT,
@userid INT
AS
 SET NOCOUNT ON;
 with VideosTest AS(
    SELECT *,ROW_NUMBER()OVER(order by [date] desc)AS rownumber
    FROM [dbo].[GP_videoUpdatesView] AS V
    WHERE @userid=V.userid
)
SELECT *
FROM VideosTest
WHERE rownumber BETWEEN 0 and @levels
GO


---Query J for links-----------------------------------------------------------
CREATE PROC spFindUpdatesLinks
@levels INT,
@userid INT
AS
 SET NOCOUNT ON;
 with LinksTest AS(
    SELECT *,ROW_NUMBER()OVER(order by [date] desc)AS rownumber
    FROM [dbo].[GP_linkUpdatesView]
    WHERE @userid=[dbo].[GP_linkUpdatesView].userid
)
SELECT *
FROM LinksTest
WHERE rownumber BETWEEN 0 and @levels

GO

---Query J for events-----------------------------------------------------------
CREATE PROC spFindUpdatesEvents
@levels INT,
@userid INT
AS
 SET NOCOUNT ON;
 with EventTest AS(
    SELECT *,ROW_NUMBER()OVER(order by [date] desc)AS rownumber
    FROM [dbo].[GP_eventUpdatesView]
    WHERE @userid=[dbo].[GP_eventUpdatesView].userid

)
SELECT *
FROM EventTest
WHERE rownumber BETWEEN 0 and @levels

GO

---Query K----------------------------------------------------------------------
CREATE PROC spFindUpdatesGlobal
@levels INT,
@userid INT
AS
 SET NOCOUNT ON;
 with UpdatesTest AS(
    SELECT *,ROW_NUMBER()OVER(order by [date] desc )AS rownumber
    FROM [dbo].[GP_updates]
   WHERE @userid= [dbo].[GP_updates].userid
)
SELECT *
FROM UpdatesTest
WHERE rownumber BETWEEN 0 and @levels

GO

-- create proc spGetPrivacy
-- @privacy int,
-- @return AS NVARCHAR(50)OUT
-- --@returnP nvarchar(30) out
-- as
-- set nocount on;

--  set @return=(select P.type
-- from [dbo].[GP_privacy] AS P
-- where P.prid=@privacy
-- )


--------------------------------------------------------------------------------
--get privacy id from name
GO
create proc spGetPrivacyfromName

@returnP nvarchar(30),
@return AS INT OUT
as
set nocount on;

set @return=(select P.type
from [dbo].[GP_privacy] AS P
where P.[type]=@returnP
)


--------------------------------------------------------------------------------
--get location from id
GO
create PROC spGetLocFromID
@locid as INT
as
set nocount on;

SELECT L.City
FROM [dbo].[GP_location] as L
where L.loc_ID=@locid
-- print @return
RETURN

DECLARE @a NVARCHAR (50)
EXEC spGetLocFromID 1, @a
PRINT @a

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--get id from location
GO
create PROC spGetLocIDfromName
@locid AS NVARCHAR(50),
@return as INT OUT
as
set nocount on;

set @return=(SELECT L.loc_ID
FROM [dbo].[GP_location] as L
where L.City=@locid)
go


--retrieves the photos of user given based on what the other user
--can see based on access
CREATE PROC spShowPhotos
    @name NVARCHAR(50),
    @userid INT
as
set NOCOUNT on;
CREATE TABLE #tempNet
(
    uid int
)
CREATE TABLE #tempFriends
(
    uid int
)
CREATE TABLE #result
(
    pid int,
    fromid INT,
    name NVARCHAR(50),
    [source] [nvarchar] (200),
    [height] int ,
    [width] int,
    [link] [nvarchar](200),
    [albumID] [int],
    privacy INT ,
    numOfLikes int
)

INSERT into #tempFriends
SELECT U.uid
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@userid AND U.uid=FW.uid2

insert INTO #tempNet
EXEC [dbo].[User_Network] @userid,3

insert INTO #result
SELECT P.pid , P.fromid , P.name , P.[source] , P.[height]  , P. [width],
    P.[link] , P.[albumID] , P.privacy , P.numOfLikes
from [dbo].[GP_photos] as P, #tempFriends as F
WHERE P.fromid=F.uid
    and P.privacy<=3 and P.name=@name and P.fromid!=@userid

INSERT INTO #result
SELECT P.pid , P.fromid , P.name , P.[source] , P.[height]  , P. [width],
    P.[link] , P.[albumID] , P.privacy , P.numOfLikes
from [dbo].[GP_photos] as P, #tempNet as F
WHERE P.fromid=F.uid
    and P.privacy<=2 and P.name=@name and P.fromid!=@userid

insert INTO #result
SELECT P.pid , P.fromid , P.name , P.[source] , P.[height]  , P. [width],
    P.[link] , P.[albumID] , P.privacy , P.numOfLikes
from [dbo].[GP_photos] as P
where P.privacy=1
    and P.fromid!=@userid and P.name=@name

SELECT DISTINCT PH.pid, PH.fromid, PH.name,
    PH.source, PH.height, PH.width,
    PH.link, PH.albumID, PC.type, PH.numOfLikes
FROM #result as R,--[dbo].[GP_user] as U,
    dbo.GP_photos as PH, dbo.GP_privacy as PC
WHERE --R.fromid = U.uid
    PH.privacy = PC.prid AND PH.pid = R.pid

RETURN
GO


--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--Retrieves videos that can be shown -- drop proc spShowVideos
CREATE PROC spShowVideos
    @userid INT,
    @name NVARCHAR(100)
as
set NOCOUNT on;
CREATE TABLE #tempNet
(
    uid int
)
CREATE TABLE #tempFriends
(
    uid int
)
CREATE TABLE #result
(
    vid int , name[nvarchar] (100),
    fromid [int] ,
   
    [message] [nvarchar](200),
    [link] [nvarchar] (200) ,
    [length][int],
    [description][nvarchar] (200) ,
    privacy int
)

INSERT into #tempFriends
SELECT U.uid
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@userid AND U.uid=FW.uid2

insert INTO #tempNet
EXEC [dbo].[User_Network] @userid,3

insert INTO #result
SELECT P.vid , P.name , P.fromid , P.[message] , P.[link]  , P. [length],
    P.[description] , P.privacy
from [dbo].[GP_videos] as P, #tempFriends as F
WHERE P.fromid=F.uid
    and P.privacy<=3 and P.name=@name and P.fromid!=@userid

INSERT INTO #result
SELECT P.vid  , P.name , P.fromid , P.[message] , P.[link]  , P. [length],
    P.[description] , P.[privacy]
from [dbo].[GP_videos] as P, #tempNet as F
WHERE P.fromid=F.uid
    and P.privacy<=2 and P.name=@name and P.fromid!=@userid

insert INTO #result
SELECT P.vid  , P.name , P.fromid , P.[message] , P.[link]  , P. [length],
    P.[description] , P.[privacy]
from [dbo].[GP_videos] as P
where P.privacy=1
    and P.fromid!=@userid and P.name=@name

SELECT DISTINCT P.vid , P.name , P.fromid  , P.[message] , P.[link]  , P. [length],
    P.[description] , PR.[type]
FROM #result as P, dbo.GP_privacy as PR
WHERE P.privacy = PR.prid

RETURN
GO

-------------------------------------------------------------------------------
CREATE PROC spShowLinks
    @userid INT,
    @name NVARCHAR(50)
as
set NOCOUNT on;
CREATE TABLE #tempNet
(
    uid int
)
CREATE TABLE #tempFriends
(
    uid int
)
CREATE TABLE #result
(
    lid int,
    fromid int, 
    link NVARCHAR(200),
   [name] [nvarchar] (50),
    [caption] [nvarchar] (50),
    [description] [nvarchar](200)
,
    [message] [nvarchar] (200),
    [privacy] [int]
)

INSERT into #tempFriends
SELECT U.uid
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@userid AND U.uid=FW.uid2

insert INTO #tempNet
EXEC [dbo].[User_Network] @userid,3

insert INTO #result
SELECT P.lid , P.fromid , P.link , P.[name] ,
    P.[caption], P.[description] , P.[message] , P.[privacy]

from [dbo].[GP_links] as P, #tempFriends as F
WHERE P.fromid=F.uid and P.privacy<=3 and P.name=@name and P.fromid!=@userid

INSERT INTO #result
SELECT P.lid , P.fromid , P.link , P.[name] ,
    P.[caption], P.[description] , P.[message] , P.[privacy]
from [dbo].[GP_links] as P, #tempNet as F
WHERE P.fromid=F.uid and P.privacy<=2 and P.name=@name and P.fromid!=@userid

insert INTO #result
SELECT P.lid , P.fromid , P.link , P.[name] ,
    P.[caption], P.[description] , P.[message] , P.[privacy]
from [dbo].[GP_links] as P
where P.privacy=1 and P.fromid!=@userid and P.name=@name

SELECT DISTINCT P.lid , P.fromid , P.link , P.[name] ,
    P.[caption], P.[description] , P.[message] , PR.type
FROM #result as P, dbo.GP_privacy as PR
WHERE P.privacy=PR.prid

RETURN
GO


CREATE PROC spShowAlbums
    @userid INT,
    @name NVARCHAR(50)
as
set NOCOUNT on;

CREATE TABLE #tempNet
(
    uid int
)
CREATE TABLE #tempFriends
(
    uid int
)
CREATE TABLE #result
(
    [aid] [int] ,
    [fromid] [int] ,
    [name] [nvarchar] (50) ,
    [description] [nvarchar] (200) ,
    [location] [int] ,
    [link] [nvarchar] (200) ,
    [privacy] [int] ,
    [count] INT
)

INSERT into #tempFriends
SELECT U.uid
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@userid AND U.uid=FW.uid2

--SELECT * from #tempFriends

insert INTO #tempNet
EXEC [dbo].[User_Network] @userid,3

--SELECT * FROM #tempNet

insert INTO #result
SELECT P.[aid],P.[fromid]  , P.[name]  ,P.[description]  , P.[location]  ,
  P.[link] ,P.[privacy] ,P.[count]
from [dbo].[GP_album] as P, #tempFriends as F
WHERE P.fromid=F.uid and P.privacy<=3 and P.name=@name and P.fromid!=@userid

--  SELECT * FROM #result
INSERT INTO #result
SELECT P.[aid],P.[fromid]  , P.[name]  ,P.[description]  , P.[location]  ,
  P.[link] ,P.[privacy] ,P.[count]
from [dbo].[GP_album] as P, #tempNet as F
WHERE P.fromid=F.uid and P.privacy<=2 and P.name=@name and P.fromid!=@userid



insert INTO #result
SELECT P.[aid],P.[fromid]  , P.[name]  ,P.[description]  , P.[location]  ,
  P.[link] ,P.[privacy] ,P.[count]
from [dbo].[GP_album] as P
where P.privacy=1 and P.fromid!=@userid and P.name=@name

--SELECT aid,fromid,U.name
--FROM #result,[dbo].[GP_user] as U
--WHERE #result.fromid=U.uid


SELECT DISTINCT P.[aid],P.[fromid] , P.[name]
 ,P.[description]  , P.[location] ,
  P.[link] ,PR.[type] ,P.[count]
FROM #result as P, dbo.GP_privacy as PR
WHERE P.privacy=PR.prid


RETURN
GO


CREATE proc getPhotosOFAlbum
    @aid INT
as
set NOCOUNT on;

SELECT PH.pid, PH.fromid, PH.name,
    PH.source, PH.height, PH.width,
    PH.link, PH.albumID, PC.type, PH.numOfLikes
FROM dbo.GP_photos as PH, dbo.GP_privacy as PC
WHERE PH.albumID=@aid and PC.prid=PH.privacy
GO


--------------------------------------------------------------------------------
--------Query N-----------------------------------------------------------------
CREATE PROC spShowEventsName
    @userid INT,
    @name NVARCHAR(50)
as
set NOCOUNT on;
CREATE TABLE #tempNet
(
    uid int
)
CREATE TABLE #tempFriends
(
    uid int
)
CREATE TABLE #result
(
    eid int,
    owner INT,
    name NVARCHAR(50),
    [description] [nvarchar] (200),
    [start_time] [datetime],
    [end_time] [datetime],
    [location] [nvarchar](200),
    [venue] [int],
    privacy nvarchar(25)
)
INSERT into #tempFriends
SELECT U.uid
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@userid AND U.uid=FW.uid2


insert INTO #tempNet
EXEC [dbo].[User_Network] @userid,3

insert INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], P.venue, PR.type
from [dbo].[GP_events] as P, #tempFriends as F, dbo.GP_privacy as PR
WHERE P.[owner]=F.uid and PR.prid =P.privacy and P.privacy<=3 and P.name=@name
    and P.owner!=@userid

INSERT INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], P.venue, PR.type
from [dbo].[GP_events] as P, #tempFriends as F, dbo.GP_privacy as PR
WHERE P.[owner]=F.uid and PR.prid =P.privacy
    and P.privacy<=2 and P.name=@name
    and P.owner!=@userid

insert INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], P.venue, PR.[type]
from [dbo].[GP_events] as P, dbo.GP_privacy as PR
where P.privacy=1 and PR.prid =P.privacy
    and P.owner!=@userid and P.name=@name

SELECT DISTINCT P.eid, U.name, P.name, P.[description], P.start_time, P.end_time,
    P.[location], L.city, P.privacy
FROM #result as P, [dbo].[GP_user] as U, [dbo].[GP_location] AS L
WHERE P.[owner]=U.uid and L.loc_ID=P.venue

RETURN
GO


CREATE PROC spShowEventsVenue
    @userid INT,
    @loc NVARCHAR(50)

as
set NOCOUNT on;

CREATE TABLE #tempNet
(
    uid int
)
CREATE TABLE #tempFriends
(
    uid int
)
declare @venueid int
exec spGetLocIDfromName @loc,@venueid out
--returns location

CREATE TABLE #result
(
    eid int,
    owner INT,
    name NVARCHAR(50),
    [description] [nvarchar] (200),
    [start_time] [datetime],
    [end_time] [datetime],
    [location] [nvarchar](200),
    [venue]NVARCHAR(50),
    privacy nvarchar(25)
)

INSERT into #tempFriends
SELECT U.uid
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@userid AND U.uid=FW.uid2

insert INTO #tempNet
EXEC [dbo].[User_Network] @userid,3

insert INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], @loc, PR.type
from [dbo].[GP_events] as P, #tempFriends as F, dbo.GP_privacy as PR
WHERE P.[owner]=F.uid and PR.prid =P.privacy
    and P.privacy<=3 and P.[venue]=@venueid
    and P.owner!=@userid

INSERT INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], @loc, PR.type
from [dbo].[GP_events] as P, #tempFriends as F, dbo.GP_privacy as PR
WHERE P.[owner]=F.uid and PR.prid =P.privacy
    and P.privacy<=2 and P.[venue]=@venueid
    and P.owner!=@userid


insert INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], @loc, PR.type
from [dbo].[GP_events] as P, dbo.GP_privacy as PR
where P.privacy=1 and PR.prid =P.privacy
    and P.owner!=@userid and P.[venue]=@venueid

SELECT DISTINCT P.eid, u.name, P.name, P.[description], P.start_time, P.end_time,
    P.[location], @loc, P.privacy
FROM #result as P, [dbo].[GP_user] as U
WHERE P.[owner]=U.uid
RETURN
GO


--------------------------------------------------------------------------------
---------------mark as interrested in-------------------------------------------
CREATE proc spMarkParticipation
    @eventID INT,
    @userid INT
as
set NOCOUNT ON;
if not exists(
    SELECT *
from dbo.GP_participates_in
WHERE eventID=eventid and userid=dbo.GP_participates_in.userid
)
BEGIN
    INSERT INTO dbo.GP_participates_in
        (userid,eventid)
    values
        (@userid, @eventID)

END
GO


CREATE proc spFindfriendsOnlyID
    @user INT
AS
SET NOCOUNT ON;
SELECT U.uid  , U.name
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@user AND U.uid=FW.uid2
RETURN
GO


IF OBJECT_ID('[dbo].[User_Friends]', 'P') IS NOT NULL
    DROP PROCEDURE [dbo].[User_Friends]
GO

CREATE PROC [dbo].[User_Friends]
@userID int
AS
    SET NOCOUNT ON
    SELECT DISTINCT uid2
    FROM [dbo].[GP_friendswith]
    WHERE uid1 = @userID
RETURN
GO
--------------------------------------------------------------------------------
IF OBJECT_ID('[dbo].[User_Network]', 'P') IS NOT NULL
    DROP PROCEDURE [dbo].[User_Network]
GO

CREATE PROC [dbo].[User_Network]
@userID int,
@maxLevel int
AS
    SET NOCOUNT ON

    DECLARE @friendID int
    DECLARE @level int
    SET @level = 1

    IF OBJECT_ID('tempdb..#UserNet0') IS NOT NULL
    DROP TABLE #UserNet0

    CREATE TABLE #UserNet0 (uid int)
    INSERT INTO #UserNet0     -- User_friends @userID
        SELECT DISTINCT uid2
        FROM [dbo].[GP_friendswith]
        WHERE uid1 = @userID


    DECLARE c CURSOR LOCAL FAST_FORWARD
    FOR SELECT uid FROM #UserNet0
    OPEN c
    FETCH NEXT FROM c INTO @friendID
    WHILE @@FETCH_STATUS = 0 AND @level <= @maxLevel
    BEGIN
        CREATE TABLE #UserFriends (uid int)
        INSERT INTO #UserFriends -- User_friends @userID
            SELECT DISTINCT uid2
            FROM [dbo].[GP_friendswith]
            WHERE uid1 = @friendID

        INSERT INTO #UserNet0
        SELECT uid
        FROM #UserFriends
        WHERE NOT EXISTS (
            SELECT uid
            FROM #UserNet0
            WHERE #UserNet0.uid = #UserFriends.uid
        )
        FETCH NEXT FROM c INTO @friendID
        DROP TABLE #UserFriends
        SET @level = @level + 1
    END
    CLOSE c
    DEALLOCATE c
    SELECT * FROM #UserNet0 WHERE #UserNet0.uid != @userID
RETURN
GO

exec spShowVideos 2, 'Video1'
--------------------------------------------------------------------------------
-- Query g: Manage friend requests
--      Show
--          All friend requests
IF OBJECT_ID('spShowFriendReq', 'P') IS NOT NULL
    DROP PROC spShowFriendReq
GO
CREATE PROC spShowFriendReq
@UserID INT -- TODO Might need change
AS
    SET NOCOUNT ON;
    SELECT  FR.userid1, FR.userid2, U.name, U.birthday, U.email, W.type, U.website
    FROM    GP_friendRequest FR INNER JOIN GP_user U ON FR.userid1 = U.uid
                                INNER JOIN GP_worksFor WF ON WF.userid = U.uid
                                INNER JOIN GP_works W ON W.workid = WF.workid
    WHERE   FR.userid2 = @UserID AND FR.ignored = 0
RETURN
GO
--      Accept
--          One person at a time
CREATE PROC spAcceptFriendReq
@UserID INT,
@FriendID INT
AS
    SET NOCOUNT ON;
    BEGIN TRANSACTION
        INSERT INTO GP_friendswith (uid1, uid2) VALUES (@UserID, @FriendID)
        INSERT INTO GP_friendswith (uid1, uid2) VALUES (@FriendID, @UserID)

        IF @@ROWCOUNT = 0 ROLLBACK

        DELETE FROM GP_friendRequest -- TODO: depending on imp. the order changes
        WHERE   userid1 = @UserID
            AND userid2 = @FriendID
    COMMIT
RETURN
GO
--      Reject
--          One person at a time
CREATE PROC spRejectFriendReq
@UserID INT,
@FriendID INT
AS
    SET NOCOUNT ON;
    DELETE FROM GP_friendRequest -- TODO: dep. on imp. the order might change
    WHERE   userid1 = @UserID
        AND userid2 = @FriendID
RETURN
GO
--      Ignore
--          One person at a time
--      (Set Ignored Status)
CREATE PROC spIgnoreFriendReq
@UserID INT,
@FriendID INT,
@Status BIT
AS
    SET NOCOUNT ON;
    UPDATE GP_friendRequest -- TODO: dep. on imp. the order might change
    SET ignored = @Status
    WHERE   userid1 = @UserID
        AND userid2 = @FriendID
RETURN
GO
--------------------------------------------------------------------------------
-- Query o: Find popular friends
--      Notes: Must check privacy
--      Opt: Don't create the same table again. Create it one time.
CREATE PROC spFindPoPFriends
@UserID INT
AS
    DECLARE @MaxFriends INT
    SET NOCOUNT ON;
    ---- Find the maximum number of friends
    IF OBJECT_ID('tempdb..#UserFriends') IS NOT NULL
        DROP TABLE #UserFriends

    CREATE TABLE #UserFriends (uid int)
    INSERT INTO #UserFriends EXEC [dbo].[User_Friends] @UserID

    SELECT @MaxFriends = MAX(numFriends)
    FROM GP_user, #UserFriends
    WHERE GP_user.uid = #UserFriends.uid

    ---- Find the userids with that number of friends
    SELECT DISTINCT UR.uid AS userID
    FROM #UserFriends UF, GP_user UR
    WHERE FriendListprivacy <= 3     -- TODO @Kon add the privacy restrictions
        AND UF.uid = UR.uid
        AND UR.numFriends = @MaxFriends

RETURN
GO
--------------------------------------------------------------------------------
-- Query p: Find common friends
--      Notes: At least
CREATE PROC spFindCommonFriends
@UserID INT
AS
    SET NOCOUNT ON;
    SELECT DISTINCT F1.uid1 AS userIDofCommonFriends
    FROM GP_friendswith F1
    WHERE F1.uid1 != @UserID
    AND NOT EXISTS
    (
        (
            SELECT F2.uid2
            FROM GP_friendswith F2
            WHERE F2.uid1 = @UserID AND F2.uid2 != F1.uid1
        )
        EXCEPT
        (
            SELECT F3.uid2
            FROM GP_friendswith F3
            WHERE F3.uid1 = F1.uid1
        )
    )
RETURN
GO
--------------------------------------------------------------------------------
-- Query q: Find friends with largest albums
--      Notes: INPUT X is set at runtime
--             Must have more than X photos in the album
--             Nothing is mentioned about PRIVACY
CREATE PROC spFindFriendsWithLargestAlbums
@UserID INT,
@X INT
AS
    SET NOCOUNT ON;
    IF OBJECT_ID('tempdb..#UserFriends') IS NOT NULL
        DROP TABLE #UserFriends

    CREATE TABLE #UserFriends (uid int)
    INSERT INTO #UserFriends EXEC [dbo].[User_Friends] @UserID

    SELECT DISTINCT UR.uid -- TODO: Add accordingingly
    FROM GP_album AL INNER JOIN GP_User UR ON AL.fromid = UR.uid
    WHERE AL.count > @X AND AL.fromid IN (SELECT * FROM #UserFriends)
        AND AL.privacy <= 3 -- OPEN or NET or FRIEND
RETURN
GO
--------------------------------------------------------------------------------
-- Query r: Find users (network friends) with largest albums
--      Notes: Must check privacy
CREATE PROC spFindNetFriendsWithLargestAlbums
@UserID INT,
@X INT
AS
    SET NOCOUNT ON;
    IF OBJECT_ID('tempdb..#UserNet1') IS NOT NULL
        DROP TABLE #UserNet1
    IF OBJECT_ID('tempdb..#UserFriends1') IS NOT NULL
        DROP TABLE #UserFriends1
    IF OBJECT_ID('tempdb..#UserSet1') IS NOT NULL
        DROP TABLE #UserSet1

    CREATE TABLE #UserNet1 (uid int)
    INSERT INTO #UserNet1 EXEC [dbo].[User_Network] @UserID, 3

    CREATE TABLE #UserFriends1 (uid int)
    INSERT INTO #UserFriends1 EXEC [dbo].[User_Friends] @UserID

    CREATE TABLE #UserSet1 (uid int)
    INSERT INTO #UserSet1
            SELECT DISTINCT fromid
            FROM #UserFriends1 UF INNER JOIN GP_album AL ON UF.uid = AL.fromid
            WHERE privacy <= 3 -- OPEN or NET or FRIEND
                AND count > @X
        UNION
            SELECT DISTINCT fromid
            FROM #UserNet1 UN INNER JOIN GP_album AL ON UN.uid = AL.fromid
            WHERE privacy <= 2 -- OPEN or NET
                AND count > @X

    SELECT US.uid -- TODO: Add accordingly
    FROM #UserSet1 US, GP_User UR
    WHERE US.uid = UR.uid

RETURN
GO
--------------------------------------------------------------------------------
-- Query s: Find friends with common intrests
--      Notes: The exact same intrests
CREATE PROC spFindFriendsWithCommonIntrests
@UserID INT
AS
    SET NOCOUNT ON;
    -- Find the friends of that user and store them in a temporary table
    IF OBJECT_ID('tempdb..#UserFriends') IS NOT NULL
        DROP TABLE #UserFriends

    CREATE TABLE #UserFriends (uid int)
    INSERT INTO #UserFriends EXEC [dbo].[User_Friends] @UserID

    ---- Find the user's friends with the exact same intrests
    SELECT DISTINCT intr.userIntid
    FROM GP_interestedin AS intr
    WHERE intr.userIntid IN (SELECT uid FROM #UserFriends)
    AND
    NOT EXISTS ( -- Find the exact same intrests; use of A XOR B = (A-B)U(B-A)
        (
            (

                SELECT actid
                FROM GP_interestedin
                WHERE userIntid = @UserID
            )
            EXCEPT
            (
                SELECT actid
                FROM GP_interestedin
                WHERE userIntid = intr.userIntid
            )
        )
        UNION
        (
            (
                SELECT actid
                FROM GP_interestedin
                WHERE userIntid = intr.userIntid
            )
            EXCEPT
            (
                SELECT actid
                FROM GP_interestedin
                WHERE userIntid = @UserID
            )
        )

    ) -- EXEC spFindFriendsWithCommonIntrests 1
RETURN
GO
--------------------------------------------------------------------------------

-- Query that returns the id of a City ----
go
CREATE PROC returnLocID
@cityin varchar(50),
@cityid INT OUT
AS
SELECT @cityid = C1.loc_ID
FROM [dbo].[GP_location] AS C1
WHERE C1.city != @cityin

GO

-- Query that returns the id of a Work ----
CREATE PROC returnWorkID
@workin varchar(50),
@workd INT OUT
AS
SELECT @workd = C1.workid
FROM [dbo].[GP_work] AS C1
WHERE C1.type != @workin

GO


-- -- Query that returns the id of a Privacy ----

create proc spGetPrivacy
@privacy int,
@return AS NVARCHAR(50)OUT
AS
set nocount on;

set @return=(select P.type
from [dbo].[GP_privacy] AS P
where P.prid=@privacy
)

GO


-- -- Query that returns the id of a Education ----

create proc spGetEducation
@edu int,
@return AS NVARCHAR(50)OUT
AS
set nocount on;

set @return=(select P.educationid
from [dbo].[GP_education] AS P
where P.institution=@edu
)

GO

-- Insert Query for User (a)
CREATE PROC insertUser

@uname varchar(50),
@fname varchar(50),
@lname varchar(50),
@pass varchar(50),
@em varchar(50),
@bday date
AS

INSERT INTO [dbo].[GP_user] (username, firstName, lastName, password, email, birthday)
VALUES (@uname, @fname, @lname, @pass, @em, @bday);

GO


----- UPDATE QUERIES (B) -------------------------------

-- Update Query for User (b)

CREATE PROC updateUser
@userID INT,
@uname varchar(50),
@pass varchar(50),
@fname varchar(50),
@lname varchar(50),
@birth DATE,
@em varchar(50),
@wor varchar(50),
@edu varchar(50),
@web varchar(50),
@home varchar(50),
@loc varchar(50)

AS

DECLARE @locate INT
DECLARE @homet INT
DECLARE @workt INT
DECLARE @educ INT

EXEC [dbo].[returnLocID] @loc, @locate OUT
EXEC [dbo].[returnLocID] @home, @homet OUT
EXEC [dbo].[returnWorkID] @wor, @workt OUT
EXEC [dbo].[spGetEducation] @edu, @educ OUT

UPDATE [dbo].[GP_user]
SET username = @uname, password = @pass , website = @web, hometown = @home, location = @loc,
	email = @em, birthday = @birth, firstname = @fname, lastname = @lname
WHERE uid = @userID

INSERT INTO [dbo].[GP_worksfor] (  [workid] , [userid])
VALUES (@workt, @userID)

INSERT INTO [dbo].[GP_educatedIn] (  [educationid] , [userid])
VALUES (@educ, @userID)

GO




-- Update Query for album (b)
CREATE PROC updateAlbum
@albumID INT,
@name varchar(50),
@desc varchar(50),
@loc varchar(50),
@pr varchar(50)

AS

DECLARE @locate INT
DECLARE @priv INT

EXEC [dbo].[returnLocID] @loc, @locate OUT
EXEC [dbo].[spGetPrivacy] @pr, @priv OUT

UPDATE [dbo].[GP_album]
SET name = @name, description = @desc , location = @locate , privacy = @priv
WHERE aid = @albumID

GO


-- Update Query for photos (b)

CREATE PROC updatePhoto
@photoID INT,
@name varchar(50)--,
--@pr varchar(50)

AS

DECLARE @priv INT

--EXEC [dbo].[spGetPrivacy] @pr, @priv OUT

UPDATE [dbo].[GP_photos]
SET name = @name --privacy = @priv
WHERE pid = @photoID

GO





-- Update Query for videos (b)

CREATE PROC updateVideo
@videoID INT,
@message varchar(50),
@desc varchar(200)

AS

UPDATE [dbo].[GP_videos]
SET message = @message, description = @desc
WHERE vid = @videoID

GO





-- Update Query for links (b)

CREATE PROC updateLink
@linkID INT,
@name varchar(50),
@desc varchar(200),
@capt varchar(25),
@msg varchar(200)

AS

UPDATE [dbo].[GP_links]
SET name = @name, description = @desc , caption = @capt , message = @msg
WHERE lid = @linkID

GO





-- Update Query for events (b)
CREATE PROC updateEvent
@eventID INT,
@name varchar(50),
@desc varchar(200),
@sttime datetime,
@ftime datetime,
@loc varchar(200),
@vn varchar(50),
@pr varchar(50)


AS

DECLARE @ven INT
DECLARE @priv INT

EXEC [dbo].[returnLocID] @vn, @ven OUT
EXEC [dbo].[spGetPrivacy] @pr, @priv OUT

UPDATE [dbo].[GP_events]
SET name = @name, description = @desc , location = @loc , privacy = @priv, start_time = @sttime,
	end_time = @ftime, venue = @ven
WHERE eid = @eventID

GO


-- query (l) ------------------------------
-- Send a fried request

CREATE PROC insertFriendReq
@uid1 INT,
@uid2 INT

AS

INSERT INTO [dbo].[GP_friendRequest] (userid1, userid2)
VALUES (@uid1,@uid2);

GO



-- query (t) ------------------------------drop proc findLeastFamousEvents
CREATE PROC findLeastFamousEvents
AS
	SET NOCOUNT ON

	CREATE TABLE #participants (evid INT, mycount INT)

	DECLARE @MIN_E INT

	INSERT INTO #participants
		SELECT P1.eventid AS evid, COUNT(P1.userid) mycount
		FROM [dbo].[GP_participates_in] AS P1
		GROUP BY P1.eventid

	SELECT @MIN_E = MIN(mycount)
	FROM #participants

	SELECT 	EV.eid, UR.username, EV.name, EV.[description], EV.start_time,EV.end_time, EV.[location], L1.city, PC.[type]
	FROM 	#participants AS PA, GP_events EV, GP_user UR, GP_location L1, GP_privacy PC
	WHERE  	pa.mycount = @MIN_e
        AND EV.eid = PA.evid AND UR.uid = EV.[owner] AND L1.loc_ID = EV.venue
        AND PC.prid = EV.privacy
RETURN
GO -- EXEC findLeastFamousEvents

-- query (u) ------------------------------

CREATE PROC findaverageAge
@UserID INT
AS

CREATE TABLE #UserNet1 (uid int)
INSERT INTO #UserNet1 EXEC [dbo].[User_Network] @UserID,1


SELECT AVG(AgeYearsIntTrunc)
FROM (	SELECT T2.uid AS fid , DATEDIFF(hour,T1.birthday,GETDATE())/8766 AS AgeYearsIntTrunc
		FROM #UserNet1 AS T2	, [dbo].[GP_user] as T1
		WHERE T1.uid = T2.uid) AS temp
GROUP BY fid
GO

create PROC findAlbums
@UserID INT
AS
set NOCOUNT ON
 SELECT AL.aid, AL.fromid, AL.name, AL.[description], AL.[location], AL.link, PC.[type], AL.[count]
 from  [dbo].[GP_album] AS AL, GP_PRIVACY PC
 WHERE @userID = AL.fromID
    AND PC.prid = AL.privacy
RETURN
go



create PROC findPhotos
@UserID INT
AS
set NOCOUNT ON
 SELECT PH.pid, PH.fromid, PH.name,
    PH.source, PH.height, PH.width,
    PH.link, PH.albumID, PR.type, PH.numOfLikes
from [dbo].[GP_photos] as PH, dbo.GP_privacy as PR
 WHERE @userID = PH.fromID and PH.privacy=PR.prid

go


create PROC findEvents
@UserID INT
AS
set NOCOUNT ON
 SELECT P.eid, UR.username, P.name, P.[description], P.start_time, P.end_time,
    P.[location], L.city, PR.type
from [dbo].[GP_events] as P, dbo.GP_privacy as PR,dbo.GP_location as L, GP_user UR
 WHERE @userID = P.owner and L.loc_ID=P.venue and P.privacy=PR.prid
    and UR.uid = P.[owner]

go

create PROC findLinks
@UserID INT
AS
set NOCOUNT ON
 SELECT T.lid, T.fromid, T.link, T.name, T.caption, T.description,
        T.message, P.type
 from  [dbo].[GP_links] AS T, GP_privacy AS P
 WHERE @userID = T.fromID AND P.prid = T.privacy
RETURN
go


create PROC findVideos
@UserID INT
AS
set NOCOUNT ON
 SELECT T.vid, T.fromid, T.name, T.message, T.link, T.length, T.[description], PC.[type]
 from  [dbo].[GP_videos] AS T, GP_privacy PC
 WHERE @userID = T.fromID
    AND PC.prid = T.privacy
RETURN
go


CREATE PROC spShowIgnoredFriendReq
@UserID INT -- TODO Might need change
AS
    SET NOCOUNT ON;
    SELECT  FR.userid1, FR.userid2, U.name, U.birthday, U.email, W.type, U.website
    FROM    GP_friendRequest FR INNER JOIN GP_user U ON FR.userid1 = U.uid
                                INNER JOIN GP_worksFor WF ON WF.userid = U.uid
                                INNER JOIN GP_works W ON W.workid = WF.workid
    WHERE   FR.userid1 = @UserID AND FR.ignored = 1
RETURN
GO

--------------------------------------------------------------------------------
--admin functions
--------------------------------------------------------------------------------
create PROC spExportUser
AS
set NOCOUNT ON
 SELECT* from [dbo].[GP_user]

RETURN
go



create PROC spExportAlbum
AS
set NOCOUNT ON
 SELECT* from  [dbo].[GP_album]

RETURN
go


create PROC spExportPrivacy
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_privacy]

RETURN
go



create PROC spExportPhotos
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_photos]

RETURN
go


create PROC spExportVideos
AS
set NOCOUNT ON
 SELECT * from   [dbo].[GP_videos]

RETURN
go


create PROC spExportLinks
AS
set NOCOUNT ON
 SELECT * from   [dbo].[GP_links]

RETURN
go


create PROC spExportEvents
AS
set NOCOUNT ON
 SELECT * from   [dbo].[GP_events]

RETURN
go

create PROC spExportFriendsWith
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_friendswith]

RETURN
go

create PROC spExportActivities
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_activities]

RETURN
go

create PROC spExportInterestedIn
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_interestedin]

RETURN
go

create PROC spExportLikes
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_likes]

RETURN
go

create PROC spExportcommentsOnAlbums
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_commentsOnAlbums]

RETURN
go


create PROC spExportcommentsOnVideos
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_commentsOnVideos]

RETURN
go

create PROC spExportLocation
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_location]

RETURN
go

create PROC spExportParticipates
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_participates_in]
RETURN
go

create PROC spExportFriendRequests
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_friendRequest]

RETURN
go


create PROC spExportworks
AS
set NOCOUNT ON
 SELECT * from  [dbo].[GP_works]

RETURN
go

create PROC spExportWorksFor
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_worksFor]

RETURN
go

create PROC spExportEducation
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_education]
RETURN
go

create PROC spExportEducatedIn
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_educatedIn]
RETURN
go

create PROC spExportQuotes
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_quotes]
RETURN
go


create PROC spExportQuotedOn
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_quotedOn]

RETURN
go


create PROC spExportUpdates
AS
set NOCOUNT ON
 SELECT * from [dbo].[GP_updates]

RETURN
go


---------------------delete ROWS-----------------------------------------------------

create proc spDeleteRows
as
set NOCOUNT ON
EXEC sp_msforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT all"

BEGIN TRANSACTION
delete from dbo.GP_user;
DBCC CHECKIDENT('dbo.GP_user', RESEED, 1);
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_album];
DBCC CHECKIDENT('dbo.GP_album', RESEED, 1);
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_privacy];
DBCC CHECKIDENT('dbo.GP_privacy', RESEED, 1);
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_photos];
DBCC CHECKIDENT('dbo.GP_photos', RESEED, 1);
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_videos];
DBCC CHECKIDENT('dbo.GP_videos', RESEED,1);
COMMIT

--SELECT * FROM dbo.GP_videos

BEGIN TRANSACTION
delete from [dbo].[GP_links];
DBCC CHECKIDENT('dbo.GP_links', RESEED, 1);
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_events];
DBCC CHECKIDENT('dbo.GP_events', RESEED, 1);
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_friendswith];
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_activities];
DBCC CHECKIDENT('dbo.GP_activities', RESEED, 1);
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_interestedin];
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_likes];
DBCC CHECKIDENT('dbo.GP_likes', RESEED, 1);
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_commentsOnAlbums];
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_commentsOnVideos];
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_location];
DBCC CHECKIDENT('dbo.GP_location', RESEED, 1);
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_participates_in];
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_friendRequest];
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_works];
DBCC CHECKIDENT('dbo.GP_works', RESEED, 1);
COMMIT
BEGIN TRANSACTION
delete from [dbo].[GP_worksFor];
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_education];
DBCC CHECKIDENT('dbo.GP_education', RESEED, 1);
COMMIT
BEGIN TRANSACTION
delete from [dbo].[GP_educatedIn];
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_quotes];
DBCC CHECKIDENT('dbo.GP_quotes', RESEED, 1);
COMMIT

BEGIN TRANSACTION
delete from [dbo].[GP_quotedOn];
COMMIT


BEGIN TRANSACTION
delete from [dbo].[GP_updates];
DBCC CHECKIDENT('dbo.GP_updates', RESEED, 1);
COMMIT


EXEC sp_msforeachtable "ALTER TABLE ? WITH CHECK CHECK CONSTRAINT all"
GO

EXEC spDeleteRows
GO
--------------------------import------------------------------------------------
CREATE PROC spImportData
@data NVARCHAR(MAX)

as
set NOCOUNT on;

EXEC (@data)

GO

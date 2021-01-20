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
    [link] [nvarchar] (200) NULL,
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
    [link] [nvarchar] (200) NOT NULL,
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
--ON DELETE SET DEFAULT ON UPDATE CASCADE 
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
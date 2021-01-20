--------------------------------------------------------------------------------
--TRIGGER FOR USER AND HOME TOWN ON DELETE
--------------------------------------------------------------------------------
GO
CREATE TRIGGER [truserhometown_FK_Delete] ON [dbo].[GP_location]
   FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_user]  AS U ON U.hometown=D.loc_ID
              )
     BEGIN
        UPDATE [dbo].[GP_user]
        SET hometown = null
        FROM [dbo].[GP_user] as U, deleted AS D
        WHERE D.loc_ID=U.hometown
    END
END
GO
--------------------------------------------------------------------------------
--TRIGGER FOR USER AND HOME TOWN ON UPDATE
--------------------------------------------------------------------------------

GO
CREATE TRIGGER [truserhometown_FK_update] ON [dbo].[GP_location]
   for UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM deleted D
        INNER JOIN [dbo].[GP_user] AS U ON U.hometown=D.loc_ID
              )
        BEGIN
        UPDATE [dbo].[GP_user] 
            SET   hometown=i.loc_ID
            FROM inserted i 
        END
END
GO

--------------------------------------------------------------------------------
--TRIGGER FOR USER AND location ON DELETE
--------------------------------------------------------------------------------

GO
CREATE TRIGGER [truserlocationONDelete] ON [dbo].[GP_location]
   FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_user]  AS U ON U.location=D.loc_ID
              )
     BEGIN
        UPDATE [dbo].[GP_user]
        SET location = null
        FROM [dbo].[GP_user] as U, deleted AS D
        WHERE D.loc_ID=U.[location]
    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER FOR USER AND location ON UPDATE
--------------------------------------------------------------------------------
GO
CREATE TRIGGER [truserLocation_FK_update] ON [dbo].[GP_location]
   FOR UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM deleted D
        INNER JOIN [dbo].[GP_user] AS U ON U.[location]=D.loc_ID
              )
        BEGIN
        UPDATE [dbo].[GP_user] 
            SET  location=inserted.loc_ID
            FROM  inserted  
        END
END
GO

--------------------------------------------------------------------------------
--TRIGGER FOR album AND photos ON DELETE
--------------------------------------------------------------------------------
CREATE TRIGGER [trPhotosAlbumDelete] ON [dbo].[GP_photos]
   FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_album] AS U ON U.aid=D.albumID
              )
     BEGIN
            DELETE  [dbo].[GP_photos]
            FROM [dbo].[GP_photos] U1
            INNER JOIN Deleted D ON U1.albumID=D.albumID
    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER FOR album and photos ON UPDATE of album id
--------------------------------------------------------------------------------

GO
CREATE TRIGGER [FK_Album_update] ON [dbo].[GP_photos]
   FOR UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM deleted D
        INNER JOIN [dbo].[GP_photos] AS U ON U.albumid=D.albumID
            )
        BEGIN
            UPDATE [dbo].[GP_photos]
            SET   albumID=I.albumID
            FROM inserted I

    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER FOR friends with ON delete
--------------------------------------------------------------------------------
CREATE TRIGGER [FK_friends_with_onDelete] ON [dbo].[GP_user]
  FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_user] AS U ON U.uid=D.uid
              )
     BEGIN
            DELETE  [dbo].[GP_friendswith]
            FROM [dbo].[GP_friendswith] U1
            INNER JOIN Deleted D ON U1.uid2=D.uid
    END
END
GO
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--TRIGGER FOR friends with ON UPDATE
--------------------------------------------------------------------------------
/*
GO
CREATE TRIGGER [FK_Album_update] ON [dbo].[GP_photos]
   FOR UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM inserted D
        INNER JOIN [dbo].[GP_album] AS U ON U.aid=D.albumID
              )
        BEGIN
        UPDATE [dbo].[GP_photos]
            SET   albumID=I.albumID
            FROM [dbo].[GP_photos] AS U1
            INNER JOIN inserted I ON  U1.albumID=I.albumID

    END
END
GO*/


--------------------------------------------------------------------------------
--TRIGGER FOR likes photo update photo
--------------------------------------------------------------------------------

GO
CREATE TRIGGER [trFK_likesPhotosonUpdate] ON [dbo].[GP_photos]
   FOR UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM deleted D
        INNER JOIN [dbo].[GP_likes] AS U ON U.photoid=D.pid
              )
        BEGIN
        UPDATE [dbo].[GP_likes]
            SET   photoid=I.pid
            FROM  inserted I 

    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER likes photo on delete
--------------------------------------------------------------------------------
CREATE TRIGGER [trFK_likesPhotosonDelete] ON [dbo].[GP_photos]
  FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_photos] AS U ON U.pid=D.pid
              )
     BEGIN
            DELETE [dbo].[GP_likes]
            FROM [dbo].[GP_likes] U1
            INNER JOIN Deleted D ON U1.photoid=D.pid
    END
END
GO


--------------------------------------------------------------------------------
--TRIGGER FOR participates in on update
--------------------------------------------------------------------------------

GO
CREATE TRIGGER [trparticipatesInUpdate] ON [dbo].[GP_user]
   FOR UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM deleted D
        INNER JOIN [dbo].[GP_participates_in]AS U ON U.userid=D.uid
              )
        BEGIN
        UPDATE [dbo].[GP_participates_in]
            SET   userid=I.uid
            FROM  inserted I

    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER FOR participates in on delete
--------------------------------------------------------------------------------
CREATE TRIGGER [trparticipatesInOnDelete] ON [dbo].[GP_user]
  FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_participates_in] AS U ON U.userid=D.uid
              )
     BEGIN
            DELETE [dbo].[GP_participates_in]
            FROM [dbo].[GP_participates_in] U1
            INNER JOIN Deleted D ON U1.userid=D.uid
    END
END
GO


--------------------------------------------------------------------------------
--TRIGGER comments on album on update 
--------------------------------------------------------------------------------

GO
CREATE TRIGGER [trcommnentsOnAlbumonupdate] ON [dbo].[GP_album]
   FOR UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM deleted D
        INNER JOIN [dbo].[GP_commentsOnAlbums] AS U ON U.albumid=D.aid
              )
        BEGIN
            UPDATE  [dbo].[GP_commentsOnAlbums]
            SET   albumid=I.aid
            FROM inserted I

    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER FOR comments on album on delete
--------------------------------------------------------------------------------
CREATE TRIGGER [trcommnentsOnAlbumOndelete] ON [dbo].[GP_album]
  FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_commentsOnAlbums] AS U ON U.albumid=D.aid
              )
     BEGIN
            DELETE [dbo].[GP_commentsOnAlbums]
            FROM [dbo].[GP_commentsOnAlbums] U1
            INNER JOIN Deleted D ON U1.albumid=D.aid
    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER friends with on update 
--------------------------------------------------------------------------------

GO
CREATE TRIGGER [trFriendsWithOnUpdate] ON [dbo].[GP_user]
   FOR UPDATE AS
BEGIN
    SET NOCOUNT ON
    IF  EXISTS ( SELECT *
    FROM deleted D
        INNER JOIN [dbo].[GP_friendRequest] AS U ON U.userid2=D.uid
              )
        BEGIN
        UPDATE  [dbo].[GP_friendRequest]
            SET   userid2=I.uid
            FROM  inserted I 

    END
END
GO

--------------------------------------------------------------------------------
--TRIGGER friends with on delete
--------------------------------------------------------------------------------
CREATE TRIGGER [trFriendsWithOnDelete] ON [dbo].[GP_user]
  FOR DELETE AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS ( SELECT *
    FROM Deleted D
        INNER JOIN [dbo].[GP_friendRequest] AS U ON U.userid2=D.uid
              )
     BEGIN
            DELETE [dbo].[GP_friendRequest]
            FROM [dbo].[GP_friendRequest] U1
            INNER JOIN Deleted D ON U1.userid2=D.uid
    END
END
GO

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
SET @loc= (select location from inserted)
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
CREATE TRIGGER [TR_User_currentWork] ON [dbo].[GP_likes]
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

SELECT * FROM GP_photos

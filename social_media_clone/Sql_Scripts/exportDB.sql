CREATE PROC spExportDB
--@DBreturned NVARCHAR(MAX) OUT
as 
set NOCOUNT ON
  Declare @uid [int]  
  Declare @firstName [nvarchar] (50) 
  Declare @lastName [nvarchar] (50) 
  Declare @name [nvarchar] (100) 
  Declare @link [nvarchar] 
  Declare @birthday [DATE]  
  Declare @email [nvarchar] (50) 
  Declare @website [nvarchar] (150)  
  Declare @username [nvarchar] (50) 
  Declare @password [nvarchar] (50) 
  Declare @hometown [int] 
  Declare @location [int] 
  Declare @gender [BIT]          
  Declare @verified [BIT]
  Declare @numFriends [int] 
  Declare @profPic [nvarchar] (200) 
  Declare @FriendListprivacy [int] 
declare @DBreturned NVARCHAR(MAX) 
    DECLARE c CURSOR LOCAL READ_ONLY FOR  SELECT * from [dbo].[GP_user]
    OPEN c 
    SET @DBreturned='USER TABLE:'+ CHAR(10)
    FETCH NEXT FROM c into @uid,@firstName,@lastName,@name,@link,@birthday,
    @email,@website,@username,@password,@hometown,@location,@gender, @verified,
    @numFriends,@profPic, @FriendListprivacy;
   
   WHILE @@FETCH_STATUS = 0  
    BEGIN  
    SET @DBreturned=@DBreturned +''+@uid  +' '+ @firstName +' '+ 
    @lastName +' '+ @name +' '+ @link +' '+
     CONVERT(nvarchar(20),@birthday) +' '+ 
    @email +' '+ @website +' '+ @username +' '+
     @password +' '+ @hometown +' '+ @location +' '+ 
     @gender +' '+  @verified +' '+ 
    @numFriends +' '+ @profPic +' '+  @FriendListprivacy+CHAR(10)

    FETCH NEXT FROM c into @uid,@firstName,@lastName,@name,@link,@birthday,
    @email,@website,@username,@password,@hometown,@location,@gender, @verified,
    @numFriends,@profPic, @FriendListprivacy;
    END
    close c 
    DEALLOCATE c

RETURN
GO

declare @result  NVARCHAR(MAX)
EXEC spExportDB @result out


drop proc spExportDB
GO

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
DROP PROC spDeleteRows
GO
create proc spDeleteRows
as
set NOCOUNT ON
EXEC sp_msforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT all"

BEGIN TRANSACTION 
delete from dbo.GP_user;
DBCC CHECKIDENT('dbo.GP_user', RESEED, 0);
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_album];
DBCC CHECKIDENT('dbo.GP_album', RESEED, 0);
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_privacy];
DBCC CHECKIDENT('dbo.GP_privacy', RESEED, 0);
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_photos];
DBCC CHECKIDENT('dbo.GP_photos', RESEED, 0);
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_videos];
DBCC CHECKIDENT('dbo.GP_videos', RESEED, 0);
COMMIT

--SELECT * FROM dbo.GP_videos

BEGIN TRANSACTION 
delete from [dbo].[GP_links];
DBCC CHECKIDENT('dbo.GP_links', RESEED, 0);
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_events];
DBCC CHECKIDENT('dbo.GP_events', RESEED, 0);
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_friendswith];
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_activities];
DBCC CHECKIDENT('dbo.GP_activities', RESEED, 0);
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_interestedin];
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_likes];
DBCC CHECKIDENT('dbo.GP_likes', RESEED, 0);
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_commentsOnAlbums];
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_commentsOnVideos];
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_location];
DBCC CHECKIDENT('dbo.GP_location', RESEED, 0);
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_participates_in];
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_friendRequest];
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_works];
DBCC CHECKIDENT('dbo.GP_works', RESEED, 0);
COMMIT
BEGIN TRANSACTION 
delete from [dbo].[GP_worksFor];
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_education];
DBCC CHECKIDENT('dbo.GP_education', RESEED, 0);
COMMIT
BEGIN TRANSACTION 
delete from [dbo].[GP_educatedIn];
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_quotes];
DBCC CHECKIDENT('dbo.GP_quotes', RESEED, 0);
COMMIT

BEGIN TRANSACTION 
delete from [dbo].[GP_quotedOn];
COMMIT


BEGIN TRANSACTION 
delete from [dbo].[GP_updates];
DBCC CHECKIDENT('dbo.GP_updates', RESEED, 0);
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

/*declare @re NVARCHAR(max)
set @re ='INSERT into [dbo].GP_videos([fromid],
name,message,link,length,[description],privacy)
 VALUES(100,''stg'',''msg'',''link'',15,''desc'',2)'

 EXEC spImportData @re*/


 select * from dbo.GP_user
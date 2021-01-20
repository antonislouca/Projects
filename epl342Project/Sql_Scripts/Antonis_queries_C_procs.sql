--------------QUERY C for album-------------------------------------------------
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

exec spFindUserAlbums 'album',37
GO
------------------------get album comments-----------------------------------------------

create proc spGetAlbumComments
@aid int
as
select A.description
from [dbo].[GP_commentsOnAlbums] as A
where A.albumid=@aid
RETURN
GO



--------------------------------------------------------------------------------
--------------QUERY C for Photos------------------------------------------------
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
------------------------RUN ABOVE-----------------------------------------------

DECLARE @photolink NVARCHAR(200)
EXEC spFindUserPhotos 'A GOOD PIC1',100
PRINT @photolink
GO
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
--------------QUERY C for VIDEOS-------------------------------------------------
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
------------------------RUN ABOVE-----------------------------------------------


GO
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
--------------QUERY C for LINKS-------------------------------------------------
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
------------------------RUN ABOVE-----------------------------------------------
EXEC spFindUserLinks 'VID NAME',100
GO
--------------------------------------------------------------------------------
--------------QUERY C for Events-------------------------------------------------
CREATE PROC spFindUserEvents
   @Name NVARCHAR(200),
   @user int
--@EventL NVARCHAR(200) OUT
AS

SET NOCOUNT ON;
SELECT  P.eid, @Name, P.name, P.[description], P.start_time, P.end_time,
    P.[location], L.city, P.privacy
FROM [dbo].[GP_events] AS P, dbo.GP_privacy as PR, [dbo].[GP_location] AS L
WHERE P.name=@Name AND P.privacy = PR.prid AND L.loc_ID=p.venue

RETURN
GO
------------------------RUN ABOVE-----------------------------------------------
EXEC spFindUserEvents 'VID NAME',100
GO
--------------------------------------------------------------------------------


--------------------------------------------------------------------------------
--added search features please dont kill me
--------------------------------------------------------------------------------
CREATE PROC spFindUserAlbumsbasedonLocation
   @loc NVARCHAR(200),
   @user int
AS
SET NOCOUNT ON;
create table #albumID
(
   aid int
)

DECLARE @locid INT
set @locid = 
(SELECT L.loc_ID
from [dbo].[GP_location] as L
where L.city=@loc)

insert into #albumID
SELECT A.aid
FROM [dbo].[GP_album] AS A
WHERE A.location=@locid AND A.fromid=@user

--create table #album (PhotoLinks  NVARCHAR(200))
--insert INTO #album 
select P.albumID, P.pid
from dbo.GP_photos as P,#albumID as A
where P.albumID=A.aid
group by P.albumID,P.pid
RETURN
GO


----------DROP IT
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserAlbumsbasedonLocation'
)
BEGIN
   Drop PROC spFindUserAlbumsbasedonLocation;
END
GO

--------------------------------------------------------------------------------
CREATE PROC spFindUserAlbumsbasedonDescription
   @desc NVARCHAR(200),
   @user int
AS
SET NOCOUNT ON;
create table #albumID
(
   aid int
)

insert into #albumID
SELECT A.aid
FROM [dbo].[GP_album] AS A
WHERE CONTAINS(A.Description,@desc) AND A.fromid=@user

--create table #album (PhotoLinks  NVARCHAR(200))
--insert INTO #album 
select P.albumID, P.pid
from dbo.GP_photos as P
where P.albumID=@albumID
group by P.albumID,P.pid
RETURN

GO

----------DROP IT
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserAlbumsbasedonDescription'
)
BEGIN
   Drop PROC spFindUserAlbumsbasedonDescription;
END
GO

------------------------seaerch videos based on description---------------------
CREATE PROC spFindUserVideosWithDesc
   @desc NVARCHAR(200),
   @user int

AS
SET NOCOUNT ON;
SELECT *
FROM [dbo].[GP_videos] AS V
WHERE CONTAINS(V.description,@desc) AND V.fromid=@user

RETURN
GO

----------DROP IT
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserVideosWithDesc'
)
BEGIN
   Drop PROC spFindUserVideosWithDesc;
END
GO

--------------------------------------------------------------------------------
------------------------seaerch videos based on length--------------------------
CREATE PROC spFindUserVideosWithLen
   @len int,
   @user int

AS
SET NOCOUNT ON;
SELECT *
FROM [dbo].[GP_videos] AS V
WHERE V.length= @len AND V.fromid=@user

RETURN
GO

----------DROP IT
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserVideosWithLen'
)
BEGIN
   Drop PROC spFindUserVideosWithLen;
END
GO

--------------------------------------------------------------------------------
------search events based on venue
CREATE PROC spFindUserEventsOnvenue
   @ven NVARCHAR(200),
   @user int

AS

SET NOCOUNT ON;
SELECT *
FROM [dbo].[GP_events] AS L
WHERE L.venue=@ven AND L.fromid=@user
RETURN
GO


----------DROP IT
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserEventsOnvenue'
)
BEGIN
   Drop PROC spFindUserEventsOnvenue;
END
GO

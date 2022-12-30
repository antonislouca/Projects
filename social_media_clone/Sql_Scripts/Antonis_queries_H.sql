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
    WHERE @userid=[dbo].[GP_videoUpdatesView].userid
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
--------------------------------------------------------------------------------
EXEC spFindUpdatesPhotos 2,36
go
--------------------------------------------------------------------------------
create proc spGetPrivacy
@privacy int,
@return AS NVARCHAR(50)OUT
--@returnP nvarchar(30) out
as
set nocount on;

 set @return=(select P.type
from [dbo].[GP_privacy] AS P
where P.prid=@privacy 
)

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
@locid as INT,
@return AS NVARCHAR(50)OUT
as
set nocount on;

set @return=(SELECT L.City
FROM [dbo].[GP_location] as L
where L.loc_ID=@locid)
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


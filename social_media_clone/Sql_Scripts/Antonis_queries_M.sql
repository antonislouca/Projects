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
--exec spShowPhotos 'A GOOD PIC1', 3 -- DROP PROC spShowPhotos

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--Retrieves videos that can be shown
CREATE PROC spShowVideos
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
    vid int ,
    fromid [int] ,
    name[nvarchar] (100),
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
SELECT P.vid , P.fromid , P.name , P.[message] , P.[link]  , P. [length],
    P.[description] , P.privacy
from [dbo].[GP_videos] as P, #tempFriends as F
WHERE P.fromid=F.uid
    and P.privacy<=3 and P.name=@name and P.fromid!=@userid

INSERT INTO #result
SELECT P.vid , P.fromid , P.name , P.[message] , P.[link]  , P. [length],
    P.[description] , P.[privacy]
from [dbo].[GP_videos] as P, #tempNet as F
WHERE P.fromid=F.uid
    and P.privacy<=2 and P.name=@name and P.fromid!=@userid

insert INTO #result
SELECT P.vid , P.fromid , P.name , P.[message] , P.[link]  , P. [length],
    P.[description] , P.[privacy]
from [dbo].[GP_videos] as P
where P.privacy=1
    and P.fromid!=@userid and P.name=@name

SELECT DISTINCT P.vid , P.fromid , P.name , P.[message] , P.[link]  , P. [length],
    P.[description] , PR.[type]
FROM #result as P, dbo.GP_privacy as PR
WHERE P.privacy = PR.prid

RETURN
GO
--------------------------------------------------------------------------------
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

--------------------------------------------------------------------------------
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

--exec spShowAlbums 46,'album'



--------------------------------------------------------------------------------
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

--EXEC getPhotosOFAlbum 6
go

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

exec spShowEventsName 3,'event1'
go
-------------------------search on venue----------------------------------------
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

EXEC spShowEventsVenue 3,'Ayia Thekla'
go
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
--------------------------------------------------------------------------------
CREATE proc spFindfriendsOnlyID
    @user INT
AS
SET NOCOUNT ON;
SELECT U.uid  , U.name
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE  FW.uid1=@user AND U.uid=FW.uid2
RETURN
GO

--------------------------------------------------------------------------------
--DO THIS SHIT TOMMORROW NOT FOR IMPLEMENTED IN GUI
---------------search events based on description-------------------------------
CREATE PROC spShowEventsDescr
    @userid INT,
    @desc NVARCHAR(200)
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
    P.[location], P.venue, P.privacy
from [dbo].[GP_events] as P, #tempFriends as F
WHERE P.[owner]=F.uid and P.privacy<=3 and contains(P.description,@desc)
    and P.owner!=@userid


INSERT INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], P.venue, P.privacy
from [dbo].[GP_events] as P, #tempNet as F
WHERE P.[owner]=F.uid and P.privacy<=2 and contains(P.description,@desc)
    and P.owner!=@userid



insert INTO #result
SELECT P.eid, P.[owner], P.name, P.[description], P.start_time, P.end_time,
    P.[location], P.venue, P.privacy
from [dbo].[GP_events] as P
where P.privacy=1 and P.owner!=@userid and contains(P.description,@desc)

SELECT DISTINCT P.eid, U.name, P.name, P.[description]
, P.start_time, P.end_time, P.[location], L.city,PR.[type]
FROM #result as P, [dbo].[GP_user] as U, [dbo].[GP_location] AS L
    , dbo.GP_privacy as PR
WHERE P.[owner]=U.uid and L.loc_ID=P.venue and PR.prid =P.privacy

RETURN
GO




---------drop it
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowEventsDescr'
)
BEGIN
    Drop PROC spShowEventsDescr;
END
GO



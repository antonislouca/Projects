-- Query that returns the id of a City ----
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
SET message = @name, description = @desc 
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

UPDATE [dbo].[GP_album]
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



-- query (t) ------------------------------

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

	SELECT 	pa.evid
	FROM 	#participants AS PA
	WHERE  	pa.mycount = @MIN_e
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



print DATEDIFF()

go



create PROC findAlbums
@UserID INT
AS
set NOCOUNT ON
 SELECT* 
 from  [dbo].[GP_album] AS T
 WHERE @userID = T.fromID

go



create PROC findPhotos
@UserID INT
AS
set NOCOUNT ON
 SELECT* 
 from  [dbo].[GP_photos] AS T
 WHERE @userID = T.fromID

go


create PROC findEvents
@UserID INT
AS
set NOCOUNT ON
 SELECT* 
 from  [dbo].[GP_events] AS T
 WHERE @userID = T.owner

go


create PROC findLinks
@UserID INT
AS
set NOCOUNT ON
 SELECT* 
 from  [dbo].[GP_links] AS T
 WHERE @userID = T.fromID

go


create PROC findVideos
@UserID INT
AS
set NOCOUNT ON
 SELECT* 
 from  [dbo].[GP_videos] AS T
 WHERE @userID = T.fromID

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


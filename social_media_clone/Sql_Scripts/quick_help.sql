-- -- To show the user's profile
-- IF EXISTS (
--     SELECT currentWork
--     FROM GP_user
--     WHERE 
-- )

SELECT  uid AS userID, name, link, 
        birthday, email, website, 
        username, password, 
        HT.city AS userHometown, 
        LC.city AS userLocation, 
        gender, verified, numFriends, profPic, 
        PC.type AS Privacy

FROM    GP_user UR,
        GP_location HT, GP_location LC, GP_privacy PC
        --GP_educatedIn ED, GP_quotedOn QU --GP_interestedin IT
WHERE uid = 104 -- SET USER
    AND HT.loc_ID = UR.hometown AND LC.loc_ID = UR.location
    AND UR.FriendListPrivacy = PC.prid
GO -- SELECT * from GP_user where uid = 24

-- Get user info (again)--------
SELECT WS.type as CurrentWork
FROM GP_user UR, GP_worksFor WF, GP_works WS
WHERE uid = 1
    AND UR.uid = WF.userid AND WF.workid = WS.workid

GO

-- Get user Education
SELECT institution
FROM GP_educatedIn ED, GP_education
WHERE userid = 12
GO
-- Get user  Quotes
SELECT type AS quote
FROM GP_quotedOn, GP_quotes
WHERE userid = 1
GO
-- Get user Intrests
SELECT type AS Interest
FROM GP_activities, GP_interestedin
WHERE userIntid = 1
GO
-- Authenticate a user (username, password)-----------
SELECT  UR.uid
FROM    GP_user UR
WHERE UR.username = 'kchris12'
AND UR.password = 'pass'
go
-----------Find user photos (Query C for Photos: Antonis)-----------------------
CREATE PROC spFindUserPhotos
@Name NVARCHAR(200),
@user int
AS
    SET NOCOUNT ON;

    SELECT  PH.pid, PH.fromid, PH.name, 
            PH.source, PH.height, PH.width, 
            PH.link, PH.albumID, PC.type
    FROM [dbo].[GP_photos] AS PH, [dbo].[GP_privacy] PC
    WHERE PH.Name = @Name AND PH.fromid = @user
        AND PH.privacy = PC.prid 

RETURN
GO
select * from GP_user


EXEC spFindUserPhotos 'A GOOD PIC1', 37
-------------------------------------------------------------------------------
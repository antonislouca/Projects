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
    WHERE   FR.userid1 = @UserID AND FR.ignored = 0
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
-- Query w: Users with same friends NOT WORKING!!!!!!

-- DECLARE @UserID INT;
-- -- SET @UserID = 4;

-- SELECT DISTINCT F1.uid1 AS userIDofSameFriends
-- FROM GP_friendswith F1
-- WHERE NOT EXISTS (
--     ( -- Keep the users that have the same userIDs as friends
--         SELECT F2.uid2
--         FROM GP_friendswith F2
--         WHERE F2.uid1 = @UserID AND F1.uid1 = F2.uid2
--     )
--     EXCEPT 
--     ( -- -- Must have all the friends of the given user
--         SELECT F3.uid2
--         FROM GP_friendswith F3
--         WHERE F3.uid1 = F1.uid1 AND F3.uid1 != @UserID -- remove self
--     )
-- )

-- GO

--------------------------------------------------------------------------------
-- data
--------------------------------------------------------------------------------
EXEC sp_MSforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT all"
SET QUOTED_IDENTIFIER OFF
SET NOCOUNT ON


DELETE FROM [dbo].[GP_album]
INSERT INTO [dbo].[GP_album] (fromid, name, link, privacy, count)
VALUES (1, 'name1.1', 'link1', 1, 3), (1, 'name1.2', 'link1', 1, 5)
INSERT INTO [dbo].[GP_album] (fromid, name, link, privacy, count)
VALUES (2, 'name2.1', 'link1', 1, 4), (2, 'name2.2', 'link1', 1, 4)
INSERT INTO [dbo].[GP_album] (fromid, name, link, privacy, count)
VALUES (3, 'name3.1', 'link1', 1, 2), (3, 'name3.2', 'link1', 1, 5)
INSERT INTO [dbo].[GP_album] (fromid, name, link, privacy, count)
VALUES (4, 'name4.1', 'link1', 1, 3), (4, 'name4.2', 'link1', 1, 2)
INSERT INTO [dbo].[GP_album] (fromid, name, link, privacy, count)
VALUES (5, 'name5.1', 'link1', 1, 4), (5, 'name5.2', 'link1', 2, 5)
INSERT INTO [dbo].[GP_album] (fromid, name, link, privacy, count)
VALUES (6, 'name6.1', 'link1', 1, 4), (6, 'name6.2', 'link1', 2, 6)
SELECT * FROM [dbo].[GP_album]

DELETE FROM [dbo].[GP_friendswith]
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (1, 2)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (1, 3)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (1, 4)
-- INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (1, 5)

INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (2, 1)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (2, 3)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (2, 4)
-- INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (2, 5)

INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (3, 1)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (3, 2)
-- INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (3, 5)

INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (4, 1)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (4, 2)
-- INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (4, 5)

INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (5, 1)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (5, 2)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (5, 3)
INSERT INTO [dbo].[GP_friendswith] (uid1, uid2) VALUES (5, 4)


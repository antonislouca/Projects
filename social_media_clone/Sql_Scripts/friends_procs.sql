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
--Query H 
CREATE proc spFindfriends
@user INT
AS
 SET NOCOUNT ON;
SELECT U.name,U.uid 
FROM dbo.GP_user as U, dbo.GP_friendswith AS FW
WHERE U.uid=FW.uid2 AND FW.uid1=@user
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

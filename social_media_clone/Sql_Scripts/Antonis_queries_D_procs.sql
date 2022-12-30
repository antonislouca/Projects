CREATE PROC spSearchName
@name NVARCHAR (100)

AS
 SET NOCOUNT ON;
    SELECT U.uid--,U.name,U.link ,U.birthday,U.email,U.website
    --,U.[location],U.hometown,WF.[type], 
  --  U.gender,U.numFriends,U.profPic,U.FrindListPrivacy
    FROM [dbo].[GP_user] AS U--,dbo.GP_worksFor AS WF
    WHERE U.name=@name --and WF.workid=U.currentWork

GO 
--------------------------------------------------------------------------------

EXEC spSearchName 'Constance Beer'
-- @brithdau [DATE],@city [nvarchar] (50)
GO
----------------------Search based on birthdate---------------------------------
CREATE PROC spSearchDate
@bdate NVARCHAR (50)

AS
 SET NOCOUNT ON;
    SELECT  U.uid--,U.name,U.link ,U.birthday,U.email,U.website
    --,U.[location],U.hometown,WF.[type], 
  --  U.gender,U.numFriends,U.profPic,U.FrindListPrivacy
    FROM [dbo].[GP_user] AS U--,dbo.GP_worksFor AS WF
    WHERE U.birthday=CONVERT(date,@bdate,103)--and WF.workid=U.currentWork

GO 
--------------------------------------------------------------------------------
EXEC spSearchDate '08/10/1983'
 
GO
--------------------------------------------------------------------------------

----------------------Search based on location---------------------------------
CREATE PROC spSearchLocation
@loc NVARCHAR (50)

AS
 SET NOCOUNT ON;
    SELECT U.uid--,U.name,U.link ,U.birthday,U.email,U.website
  --  ,U.[location],U.hometown,WF.type, 
--U.gender,U.numFriends,U.profPic,U.FrindListPrivacy
    FROM [dbo].[GP_user] AS U, [dbo].[GP_location] as L--,GP_worksFor AS WF 
    WHERE L.loc_ID=U.location and @loc=L.city --and WF.workid=U.currentWork
GO 
--------------------------------------------------------------------------------
EXEC spSearchLocation 'Paralimni'
GO

--------------------------------------------------------------------------------

----------------------Search all------------------------------------------------
CREATE PROC spSearchALL 
@name NVARCHAR (50),
@bdate NVARCHAR (50),
@loc NVARCHAR (50),
@function int

AS
 SET NOCOUNT ON;
CREATE TABLE #temptable1 (uid int)--,name nvarchar(100)
--,email nvarchar(50),birthday date,location nvarchar(50) ,hometown nvarchar(50))
CREATE TABLE #temptable2 (uid int)--,name nvarchar(100)
--,email nvarchar(50),birthday date,location nvarchar(50) ,hometown nvarchar(50))
CREATE TABLE #temptable3 (uid int)--,name nvarchar(100)
--,email nvarchar(50),birthday date,location nvarchar(50) ,hometown nvarchar(50))

--DROP TABLE #temptable1
    IF @function=1 
        BEGIN
        INSERT INTO #temptable1
        EXEC spSearchName @name
        select *
        from #temptable1
        END
    ELSE IF @function=2 
        BEGIN
        INSERT INTO #temptable2
        EXEC spSearchDate @bdate
        select * from #temptable2
        END
    ELSE IF @function=3
        BEGIN
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
        select * from #temptable2
        END
    ELSE IF @function=4     --name &date
        BEGIN
        INSERT INTO #temptable1
        EXEC spSearchName @name
        INSERT INTO #temptable2
        EXEC spSearchDate @bdate
    SELECT *
    FROM #temptable1,#temptable2
    WHERE #temptable1.uid=#temptable2.uid 

        END
    ELSE IF @function=5     --name & loc
       BEGIN
        INSERT INTO #temptable1
        EXEC spSearchName @name
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
    SELECT *
    FROM #temptable1,#temptable2
    WHERE #temptable1.uid=#temptable2.uid 

       END
    ELSE IF @function=6     --loc&date
       BEGIN
        INSERT INTO #temptable1
        EXEC spSearchDate @bdate
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
    SELECT *
    FROM #temptable1,#temptable2
    WHERE #temptable1.uid=#temptable2.uid 

        END
    ELSE
       BEGIN    --loc&date&name
        INSERT INTO #temptable1
        EXEC spSearchDate @bdate
        INSERT INTO #temptable2
        EXEC spSearchLocation @loc
        INSERT INTO #temptable3
        EXEC spSearchName @name

    SELECT *
    FROM #temptable1,#temptable2,#temptable3
    WHERE #temptable1.uid=#temptable2.uid AND #temptable2.uid=#temptable3.uid

        END
RETURN
   
GO
--------------------------------------------------------------------------------
--run above
EXEC spSearchALL 'Constance Beer',null,null,1
--------------------------------------------------------------------------------
--QUERY I 
--------------------------------------------------------------------------------
GO
CREATE PROC spPhotosPerAlbum
AS
 SET NOCOUNT ON;
    SELECT A.count
    FROM [dbo].[GP_album] as A
    RETURN
GO

--------------------------------------------------------------------------------
--QUERY E -- dont know if i have to call the functionalities from D query
--------------------------------------------------------------------------------
CREATE PROC spComplexSearch
@searchType BIT,
@value NVARCHAR (MAX)
AS
 SET NOCOUNT ON;
 CREATE table #temp (uid int)--,username nvarchar(50),name nvarchar(50))
 Create table #splitedVal(types nvarchar(50))
 insert into #splitedVal
 select * from STRING_SPLIT(@value, ',') 
 DECLARE c CURSOR LOCAL READ_ONLY
 FOR SELECT types from #splitedVal

 DECLARE @searchVal NVARCHAR(50)
    OPEN c 
    FETCH NEXT FROM c into @searchVAl
IF @searchType=0 
    BEGIN
        WHILE @@FETCH_STATUS =0
        BEGIN
            INSERT INTO #temp
            EXEC spFindEducation @searchVal
            FETCH NEXT FROM c into @searchVal
        END
        
    END
ELSE 
    BEGIN
        WHILE @@FETCH_STATUS =0
        BEGIN
            INSERT INTO #temp
            EXEC spFindWorks @searchVal
            FETCH NEXT FROM c into @searchVal
        END
    END

CLOSE c
DEALLOCATE c

SELECT uid, count(uid) as commonMatches --this might need a distinct not sure
FROM #temp
GROUP BY uid
order BY COUNT(uid) DESC

RETURN
GO
/*TEST OF SPCOMPLEXSEARCH*/
EXEC spComplexSearch 0,'University of Cyprus,Stanford University'
GO
-----------------------------FOR education--------------------------------------
CREATE PROC spFindEducation
@value NVARCHAR (50)
AS
--SET @value ='ATOS'
SET NOCOUNT ON;
SELECT U.uid--, U.username,U.name
        FROM [dbo].GP_user as U
        WHERE U.uid IN (
            SELECT distinct EI.userid
            FROM [dbo].[GP_educatedIn] AS EI,dbo.GP_education as E
            WHERE E.educationid =EI.educationid and E.institution=@value
        )


RETURN
GO

----------------------------For WORKS-------------------------------------------
CREATE PROC spFindWorks
@value NVARCHAR (50)
AS

SET NOCOUNT ON;
 SELECT U.uid--, U.username,U.name
        FROM [dbo].GP_user as U
        WHERE U.uid IN (
            SELECT distinct WF.userid
            FROM [dbo].[GP_worksFor] AS WF,[dbo].[GP_works] AS W
            WHERE W.workid =WF.workid and W.type=@value
        )
RETURN
GO



--------------------------------------------------------------------------------

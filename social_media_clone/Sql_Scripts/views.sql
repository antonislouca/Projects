--------------------------------------------------------------------------------
--ALBUM VIEW
--------------------------------------------------------------------------------

CREATE VIEW [dbo].[GP_albumUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType], UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass =2
   GROUP BY UP.[date], UP.[updateid],UP.[upType],UP.[userid]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition
--DROP VIEW[dbo].[GP_albumUpdates]
GO
--------------------------------------------------------------------------------
-- PHOTOS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_photosUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType], UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass =1
  --  GROUP BY UP.[date], UP.[updateid],UP.[upType],UP.[userid]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition

GO
--------------------------------------------------------------------------------
--VIDEOS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_videoUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType], UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass =5
    GROUP BY UP.[date], UP.[updateid],UP.[upType],UP.[userid]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition
GO

--------------------------------------------------------------------------------
--LINKS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_linkUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType], UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass =3
    GROUP BY UP.[date], UP.[updateid],UP.[upType],UP.[userid]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition


GO
--------------------------------------------------------------------------------
--EVENTS VIEW
--------------------------------------------------------------------------------
CREATE VIEW [dbo].[GP_eventUpdatesView]
WITH
    SCHEMABINDING
AS
    SELECT UP.[updateid], UP.[upType], UP.[userid], UP.[date]
    FROM [dbo].[GP_updates] AS UP
    WHERE UP.upClass =4
    GROUP BY UP.[date], UP.[updateid],UP.[upType],UP.[userid]
WITH CHECK OPTION --dont know if we need this but what it does is it forces
                    -- view to obey where condition

GO
-- sp_MSforeeachtable is undocumented and may go away at any time or 
--even be modified
SET QUOTED_IDENTIFIER OFF
SET NOCOUNT ON
EXEC sp_MSforeachtable @command1="ALTER TABLE ? NOCHECK CONSTRAINT ALL"
GO
EXEC sp_MSforeachtable @command1="ALTER TABLE ? DISABLE TRIGGER ALL"
GO


--------------------------------------------------------------------------------
--DROP PROCS
--------------------------------------------------------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spAcceptFriendReq'
)
BEGIN
Drop PROC spAcceptFriendReq;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spRejectFriendReq'
)
BEGIN
Drop PROC spRejectFriendReq;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spIgnoreFriendReq'
)
BEGIN
Drop PROC spIgnoreFriendReq;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindPoPFriends'
)
BEGIN
Drop PROC spFindPoPFriends;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindCommonFriends'
)
BEGIN
Drop PROC spFindCommonFriends;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindFriendsWithLargestAlbums'
)
BEGIN
Drop PROC spFindFriendsWithLargestAlbums;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindNetFriendsWithLargestAlbums'
)
BEGIN
Drop PROC spFindNetFriendsWithLargestAlbums;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindFriendsWithCommonIntrests'
)
BEGIN
Drop PROC spFindFriendsWithCommonIntrests;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='returnWorkID'
)
BEGIN
Drop PROC returnWorkID;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetPrivacy'
)
BEGIN
Drop PROC spGetPrivacy;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetEducation'
)
BEGIN
Drop PROC spGetEducation;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='insertUser'
)
BEGIN
Drop PROC insertUser;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='updateUser'
)
BEGIN
Drop PROC updateUser;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='updateAlbum'
)
BEGIN
Drop PROC updateAlbum;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='updatePhoto'
)
BEGIN
Drop PROC updatePhoto;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='updateLink'
)
BEGIN
Drop PROC updateLink;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='insertFriendReq'
)
BEGIN
Drop PROC insertFriendReq;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='findLeastFamousEvents'
)
BEGIN
Drop PROC findLeastFamousEvents;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='findaverageAge'
)
BEGIN
Drop PROC findaverageAge;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='findAlbums'
)
BEGIN
Drop PROC findAlbums;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='findPhotos'
)
BEGIN
Drop PROC findPhotos;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='findEvents'
)
BEGIN
Drop PROC findEvents;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='findLinks'
)
BEGIN
Drop PROC findLinks;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='findVideos'
)
BEGIN
Drop PROC findVideos;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowIgnoredFriendReq'
)
BEGIN
Drop PROC spShowIgnoredFriendReq;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spMarkParticipation'
)
BEGIN
Drop PROC spMarkParticipation;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindfriendsOnlyID'
)
BEGIN
Drop PROC spFindfriendsOnlyID;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='returnLocID'
)
BEGIN
Drop PROC returnLocID;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='updateVideo'
)
BEGIN
Drop PROC updateVideo;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='updateEvent'
)
BEGIN
Drop PROC updateEvent;
END
GO



--------------------------------------------------------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserAlbums'
)
BEGIN
Drop PROC spFindUserAlbums;
END
GO
-----------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetAlbumComments'
)
BEGIN
Drop PROC spGetAlbumComments;
END
GO


----------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserPhotos'
)
BEGIN
Drop PROC spFindUserPhotos;
END
GO
--------------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserVideos'
)
BEGIN
Drop PROC spFindUserVideos;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetVideoComments'
)
BEGIN
Drop PROC spGetVideoComments;
END
GO
--------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserLinks'
)
BEGIN
Drop PROC spFindUserLinks;
END
GO
---------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUserEvents'
)
BEGIN
Drop PROC spFindUserEvents;
END
GO
-------------------------------------
---------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spSearchName'
)
BEGIN
Drop PROC spSearchName;
END
GO
-------------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spSearchDate'
)
BEGIN
Drop PROC spSearchDate;
END
GO

-----------------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spSearchLocation'
)
BEGIN
Drop PROC spSearchLocation;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spSearchALL'
)
BEGIN
Drop PROC spSearchALL;
END
GO

-----------------------------------------------------------
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spSimpleSearch'
)
BEGIN
Drop PROC spSimpleSearch;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindfriends'
)
BEGIN
Drop PROC spFindfriends;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spDeleteFriend'
)
BEGIN
Drop PROC spDeleteFriend;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUpdatesAlbum'
)
BEGIN
Drop PROC spFindUpdatesAlbum;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUpdatesPhotos'
)
BEGIN
Drop PROC spFindUpdatesPhotos;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUpdatesVideos'
)
BEGIN
Drop PROC spFindUpdatesVideos;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUpdatesLinks'
)
BEGIN
Drop PROC spFindUpdatesLinks;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUpdatesEvents'
)
BEGIN
Drop PROC spFindUpdatesEvents;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spComplexSearch'
)
BEGIN
Drop PROC spComplexSearch;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindUpdatesGlobal'
)
BEGIN
Drop PROC spFindUpdatesGlobal;
END
GO




IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetPrivacy'
)
BEGIN
Drop PROC spGetPrivacy;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetPrivacyfromName'
)
BEGIN
Drop PROC spGetPrivacyfromName;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetLocFromID'
)
BEGIN
Drop PROC spGetLocFromID;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spGetLocIDfromName'
)
BEGIN
Drop PROC spGetLocIDfromName;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindEducation'
)
BEGIN
Drop PROC spFindEducation;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spFindWorks'
)
BEGIN
Drop PROC spFindWorks;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spComplexSearch'
)
BEGIN
Drop PROC spComplexSearch;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowPhotos'
)
BEGIN
Drop PROC spShowPhotos;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowVideos'
)
BEGIN
Drop PROC spShowVideos;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowLinks'
)
BEGIN
Drop PROC spShowLinks;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowAlbums'
)
BEGIN
Drop PROC spShowAlbums;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowEventsName'
)
BEGIN
Drop PROC spShowEventsName;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spShowEventsVenue'
)
BEGIN
Drop PROC spShowEventsVenue;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='getPhotosOFAlbum'
)
BEGIN
Drop PROC getPhotosOFAlbum;
END
GO


-----------------------------drop db export-import-delete procedures------------


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportUser'
)
BEGIN
Drop PROC spExportUser;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportAlbum'
)
BEGIN
Drop PROC spExportAlbum;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportPrivacy'
)
BEGIN
Drop PROC spExportPrivacy;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportPhotos'
)
BEGIN
Drop PROC spExportPhotos;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportVideos'
)
BEGIN
Drop PROC spExportVideos;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportLinks'
)
BEGIN
Drop PROC spExportLinks;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportEvents'
)
BEGIN
Drop PROC spExportEvents;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportFriendsWith'
)
BEGIN
Drop PROC spExportFriendsWith;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportActivities'
)
BEGIN
Drop PROC spExportActivities;
END
GO




IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportInterestedIn'
)
BEGIN
Drop PROC spExportInterestedIn;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportLikes'
)
BEGIN
Drop PROC spExportLikes;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportcommentsOnAlbums'
)
BEGIN
Drop PROC spExportcommentsOnAlbums;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportcommentsOnVideos'
)
BEGIN
Drop PROC spExportcommentsOnVideos;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportLocation'
)
BEGIN
Drop PROC spExportLocation;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportParticipates'
)
BEGIN
Drop PROC spExportParticipates;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportFriendRequests'
)
BEGIN
Drop PROC spExportFriendRequests;
END
GO


IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportworks'
)
BEGIN
Drop PROC spExportworks;
END
GO



IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportWorksFor'
)
BEGIN
Drop PROC spExportWorksFor;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportEducation'
)
BEGIN
Drop PROC spExportEducation;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportEducatedIn'
)
BEGIN
Drop PROC spExportEducatedIn;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportQuotes'
)
BEGIN
Drop PROC spExportQuotes;
END
GO
IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportQuotedOn'
)
BEGIN
Drop PROC spExportQuotedOn;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spExportUpdates'
)
BEGIN
Drop PROC spExportUpdates;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spDeleteRows'
)
BEGIN
Drop PROC spDeleteRows;
END
GO

IF EXISTS(
SELECT *
FROM sys.objects
WHERE name='spImportData'
)
BEGIN
Drop PROC spImportData;
END
GO




--------------------------------------------------------------------------------
--DROP INDEXES
--------------------------------------------------------------------------------
IF EXISTS(
SELECT *
FROM sys.indexes
WHERE name='idxup' AND object_id = OBJECT_ID('[dbo].[GP_updates]')
)
BEGIN
    DROP INDEX idxup ON [dbo].[GP_updates];
END

GO
---------
IF EXISTS(
SELECT *
FROM sys.indexes
WHERE name='idxnameA' AND object_id = OBJECT_ID('[dbo].[GP_album]')
)
BEGIN
    DROP INDEX idxnameA ON [dbo].[GP_album];
END
GO
-------------------
IF EXISTS(
SELECT *
FROM sys.indexes
WHERE name='idxnameP' AND object_id = OBJECT_ID('[dbo].[GP_photos]')
)
BEGIN
    DROP INDEX idxnameP ON [dbo].[GP_photos];
END

GO
------------------------------------------


/*
IF EXISTS(
SELECT *
FROM sys.indexes
WHERE name='idxuserName' AND object_id = OBJECT_ID('[dbo].[GP_user]')
)
BEGIN
    DROP INDEX idxuserName ON [dbo].[GP_user];
END
GO
---------------------------------------------------------------------------
IF EXISTS(
SELECT *
FROM sys.indexes
WHERE name='idxBdAay' AND object_id = OBJECT_ID('[dbo].[GP_user]')
)
BEGIN
    DROP INDEX idxBdAay ON [dbo].[GP_user];
END
GO
------------------------------------------------------------------------------
---------------------------------------------------------------------------
IF EXISTS(
SELECT *
FROM sys.indexes
WHERE name='idxcity' AND object_id = OBJECT_ID('[dbo].[GP_user]')
)
BEGIN
    DROP INDEX idxcity ON [dbo].[GP_user];
END
GO
*/
--------------------------------------------------------------------------------
--DROP VIEWS
--------------------------------------------------------------------------------
GO
IF EXISTS(SELECT *
FROM sys.views
WHERE name = 'GP_albumUpdatesView') 
DROP VIEW[dbo].[GP_albumUpdatesView]
GO
IF EXISTS(SELECT *
FROM sys.views
WHERE name = 'GP_photosUpdatesView') 
DROP VIEW[dbo].[GP_photosUpdatesView]
GO
IF EXISTS(SELECT *
FROM sys.views
WHERE name = 'GP_videoUpdatesView') 
DROP VIEW[dbo].[GP_videoUpdatesView]
GO
IF EXISTS(SELECT *
FROM sys.views
WHERE name = 'GP_linkUpdatesView') 
DROP VIEW[dbo].[GP_linkUpdatesView]
GO
IF EXISTS(SELECT *
FROM sys.views
WHERE name = 'GP_eventUpdatesView') 
DROP VIEW[dbo].[GP_eventUpdatesView]
GO


--------------------------------------------------------------------------------
--DROP TRIGGERS
--------------------------------------------------------------------------------
DROP TRIGGER  IF EXISTS [GP_photos].[tr_updateCount]; 
GO
DROP TRIGGER  IF EXISTS [GP_location].[truserlocationONDelete];
GO
DROP TRIGGER  IF EXISTS [GP_location].[truserhometown_FK_Delete];
GO
DROP TRIGGER  IF EXISTS [GP_location].[truserhometown_FK_update];
GO
DROP TRIGGER  IF EXISTS [GP_location].[truserLocation_FK_update];
GO
DROP TRIGGER  IF EXISTS [GP_photos].[trPhotosAlbumDelete];
GO
DROP TRIGGER  IF EXISTS [GP_photos].[FK_Album_update];
GO
DROP TRIGGER  IF EXISTS [GP_user].[FK_friends_with_onDelete];
GO
DROP TRIGGER  IF EXISTS [GP_photos].[trFK_likesPhotosonUpdate];
GO
DROP TRIGGER  IF EXISTS [GP_photos].[trFK_likesPhotosonDelete];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trparticipatesInUpdate];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trparticipatesInOnDelete];
GO
DROP TRIGGER  IF EXISTS [GP_album].[trcommnentsOnAlbumonupdate];
GO
DROP TRIGGER  IF EXISTS [GP_album].[trcommnentsOnAlbumOndelete];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trFriendsWithOnUpdate];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trFriendsWithOnDelete];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trupdateOnPhotos];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trupdateOnAlbums];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trupdateOnVideos];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trupdateOnLinks];
GO
DROP TRIGGER  IF EXISTS [GP_user].[trupdateOnEvents];

--------------------------------------------------------------------------------

DROP TABLE 
--[dbo].[GP_hasVideo],
[dbo].[GP_educatedIn],
--[dbo].[GP_bornIn],
[dbo].[GP_participates_in],
[dbo].[GP_friendRequest], 
[dbo].[GP_worksFor],
[dbo].[GP_works],
[dbo].[GP_education],
[dbo].[GP_quotedOn],
[dbo].[GP_quotes],
[dbo].[GP_likes],
[dbo].[GP_photos],
[dbo].[GP_commentsOnAlbums],
[dbo].[GP_commentsOnVideos],
[dbo].[GP_album],
[dbo].[GP_interestedin],
[dbo].[GP_videos],
[dbo].[GP_links],
[dbo].[GP_events],
[dbo].[GP_friendswith],
[dbo].[GP_updates],
[dbo].[GP_user],
[dbo].[GP_privacy],
[dbo].[GP_location],
[dbo].[GP_activities]
 GO

SET NOCOUNT OFF
 SET QUOTED_IDENTIFIER ON
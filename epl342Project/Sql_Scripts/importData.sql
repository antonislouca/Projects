
EXEC sp_MSforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT all"
SET QUOTED_IDENTIFIER OFF
SET NOCOUNT ON
GO
EXEC spDeleteFriend 4,80

GO
INSERT INTO [dbo].[GP_album] (fromid,name,link,privacy) VALUES (7, 'Album A1','link to album',1)   
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P1' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 1                                                   )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P2' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 4                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P3' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P4' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P5' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 1                                                    )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P6' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 1                                                    )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P7' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P8' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P9' ,'source',10,150,'recourses/_phSxWIShM8.jpg',80, 3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A1P10','source',10,150,'recourses/_phSxWIShM8.jpg',80, 2                                                 )
GO																											
INSERT INTO [dbo].[GP_album] (fromid,name,link,privacy) VALUES (7, 'Album A2','link to album', 4  )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A2P1','source',10,150,'recourses/_phSxWIShM8.jpg',81, 1                                                     )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A2P2','source',10,150,'recourses/_phSxWIShM8.jpg',81, 3                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A2P3','source',10,150,'recourses/_phSxWIShM8.jpg',81, 2                                                  )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A2P4','source',10,150,'recourses/_phSxWIShM8.jpg',81, 4                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,' Photo A2P5','source',10,150,'recourses/_phSxWIShM8.jpg',81, 4                                                   )
GO							                                                                    
INSERT INTO [dbo].[GP_album] (fromid,name,link,privacy) VALUES (7,'Album A3' , 'link to album',    3 )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A3P1','source',10,150,'recourses/_phSxWIShM8.jpg',82, 1                                                     )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A3P2','source',10,150,'recourses/_phSxWIShM8.jpg',82, 3                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A3P3','source',10,150,'recourses/_phSxWIShM8.jpg',82, 2                                                  )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A3P4','source',10,150,'recourses/_phSxWIShM8.jpg',82, 4                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A3P5','source',10,150,'recourses/_phSxWIShM8.jpg',82, 4                                                   )
GO																												
INSERT INTO [dbo].[GP_album]  (fromid,name,link,privacy) VALUES (7,'Album A4 ','link to album', 	 2                                                    )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A4P1','source',10,150,'recourses/_phSxWIShM8.jpg',83,  2                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A4P2','source',10,150,'recourses/_phSxWIShM8.jpg',83,  2                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A4P3','source',10,150,'recourses/_phSxWIShM8.jpg',83,  2                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A4P4','source',10,150,'recourses/_phSxWIShM8.jpg',83,  2                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (7,'Photo A4P5','source',10,150,'recourses/_phSxWIShM8.jpg',83,  2                                                  )
																											
GO																										
																											
INSERT INTO [dbo].[GP_videos] (fromid,name,message,link,length,privacy) VALUES (7,'Video V1','message','file:///C:/Cane.mp4',25,1                                                       )
GO																												
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (7,' Event E1 '  ,'20100112 18:00','20100112 19:00', 1   ,1  )
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (7, 'Event E2 '  ,'20100112 15:00','20100112 16:00',  609  ,3                                                     )
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (7,' Event E3 '  ,'20100112 18:00','20100112 19:00',  609  ,2                                                  )
																												
																												
			
																											
GO																											
																											
                                     
INSERT INTO [dbo].[GP_album] (fromid,name,link,privacy) VALUES ( 71,'Album A5' ,'link to album',      1                                                    )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P1' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 1                                                     )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P2' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 4                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P3' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 3                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P4' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 2                                                  )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P5' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 1                                                     )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P6' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 1                                                     )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P7' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 3                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P8' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 3                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P9' ,'source',10,150,'recourses/_phSxWIShM8.jpg',84, 3                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A5P10','source',10,150,'recourses/_phSxWIShM8.jpg',84, 2                                                  )
GO																											
INSERT INTO [dbo].[GP_album]  (fromid,name,link,privacy) VALUES (71, 'Album A6 ','link to album',     4                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 71,'Photo A6P1' ,'source',10,150,'recourses/_phSxWIShM8.jpg',85,   1                                                    )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 71,'Photo A6P2'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',85,  3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 71,'Photo A6P3'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',85,  2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 71,'Photo A6P4'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',85,  4                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 71,'Photo A6P5'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',85,  4                                                  )
GO																												
																												
																												
INSERT INTO [dbo].[GP_album]  (fromid,name,link,privacy) VALUES ( 71,'Album A7 ','link to album',     3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71,' Photo A7P1' ,'source',10,150,'recourses/_phSxWIShM8.jpg',86,   1                                                   )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71,'Photo A7P2'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',86,  3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71,'Photo A7P3'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',86,  2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71,'Photo A7P4'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',86,  4                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71,'Photo A7P5'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',86,  4                                                  )
																												
GO																												
																												
INSERT INTO [dbo].[GP_album]  (fromid,name,link,privacy) VALUES (71, 'Album A8 ','link to album',     2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71, 'Photo A8P1'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',87,  2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71, 'Photo A8P2'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',87,   2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71, 'Photo A8P3'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',87,   2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71, 'Photo A8P4'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',87,   2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (71, 'Photo A8P5'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',87,   2                                                 )
GO																												
																												
																												
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (71,'Event E4'   ,' 20100112 18:00','20100112 19:00'  , 1   ,1   )
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (71,'Event E5'   ,' 20100112 15:00','20100112 16:00' ,  609 ,3 )
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (71,'Event E6'   ,' 20100112 18:00','20100112 19:00' ,  609  ,2)
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (71,'Event E7'   ,' 20100112 18:00','20100112 19:00'  ,  1  ,2)
																											 							
	--	SELECT * FROM DBO.GP_educatedIn																										
																												
INSERT INTO [dbo].[GP_educatedIn] (educationid,userid) VALUES (1,71)
																												 
 GO                                                                                                  
INSERT INTO [dbo].[GP_album] (fromid,name,link,privacy) VALUES (4, 'Album A9','link to album',      1                                                      )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P1'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  1                                                      )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P2'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  4                                                    )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P3'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  3                                                    )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P4'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  2                                                   )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P5'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  1                                                      )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P6'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  1                                                      )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P7'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  3                                                    )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P8'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  3                                                    )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P9'   ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,  3                                                    )
INSERT INTO [dbo].[GP_photos](fromid,name,source,height,width,link,albumID,privacy) VALUES (4,' Photo A9P10'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   2                                                  )
																											
GO																											
																											
																											
INSERT INTO [dbo].[GP_album]  (fromid,name,link,privacy) VALUES (4, 'Album A10' ,'link to album',     4                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 4,'Photo A10P1'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   1                                                    )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 4,'Photo A10P2'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 4,'Photo A10P3'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 4,'Photo A10P4'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   4                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES ( 4,'Photo A10P5'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   4                                                  )
																												
GO																												
																												
INSERT INTO [dbo].[GP_album]  (fromid,name,link,privacy) VALUES (4, 'Album A11 ','link to album',     3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A11P1'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   1                                                    )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A11P2'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   3                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A11P3'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   2                                                 )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A11P4'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   4                                                  )
INSERT INTO [dbo].[GP_photos] (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A11P5'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',88,   4                                                  )
																												
GO																												
																												
INSERT INTO [dbo].[GP_album]   (fromid,name,link,privacy) VALUES (4,'Album A12' ,'link to album',     2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A12P1'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',89,  2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A12P2'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',89,  2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A12P3'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',89,  2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A12P4'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',89,  2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (4,'Photo A12P5'  ,'source',10,150,'recourses/_phSxWIShM8.jpg',89,  2                                                 )
																											
GO																											
																											
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (4,'Event E8'     ,'20100112 18:00','20100112 19:00'  ,  1      ,1   )
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (4,'Event E9'     ,'20100112 15:00','20100112 16:00'  ,  609    ,3   )
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (4,'Event E10'    ,'20100112 18:00','20100112 19:00'  ,  609    ,2   )
INSERT INTO [dbo].[GP_events] (owner,name,start_time,end_time,venue,privacy) VALUES (4,'Event E11'    ,'20100112 18:00','20100112 19:00'  ,   1     ,2   )
																											
																											
																											
INSERT INTO [dbo].[GP_educatedIn] (educationid,userid) VALUES (  1  ,4)
INSERT INTO [dbo].[GP_educatedIn] (educationid,userid) VALUES (102   ,4  )
GO                                                  
																												 
INSERT INTO [dbo].[GP_album]   (fromid,name,link,privacy) VALUES (10,'Album A13' ,'link to album',  1                                                    )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P1','source',10,150,'recourses/_phSxWIShM8.jpg', 90, 2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P2' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P3' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 3                                                  )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P4' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P5' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P6' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P7' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 3                                                  )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P8' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 3                                                  )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P9' ,'source',10,150,'recourses/_phSxWIShM8.jpg',90 , 2                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P10','source',10,150,'recourses/_phSxWIShM8.jpg',90 ,  2                                                )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P11','source',10,150,'recourses/_phSxWIShM8.jpg',90 ,  4                                                 )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P12','source',10,150,'recourses/_phSxWIShM8.jpg',90 ,  2                                                )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P13','source',10,150,'recourses/_phSxWIShM8.jpg',90 ,  2                                                )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P14','source',10,150,'recourses/_phSxWIShM8.jpg',90 ,  2                                                )
INSERT INTO [dbo].[GP_photos]  (fromid,name,source,height,width,link,albumID,privacy) VALUES (10,'Photo A13P15','source',10,150,'recourses/_phSxWIShM8.jpg',90 ,  3                                                 )
GO																							  
																												
INSERT INTO [dbo].[GP_events](owner,name,start_time,end_time,venue,privacy) VALUES (10,'Event E12','20100112 18:00','20100112 19:00', 1   ,1    )
INSERT INTO [dbo].[GP_events](owner,name,start_time,end_time,venue,privacy) VALUES (10,'Event E13','20100112 15:00','20100112 16:00',  609  ,3    )
INSERT INTO [dbo].[GP_events](owner,name,start_time,end_time,venue,privacy) VALUES (10,'Event E14','20100112 18:00','20100112 19:00',  609  ,2   )
INSERT INTO [dbo].[GP_events](owner,name,start_time,end_time,venue,privacy) VALUES (10,'Event E15','20100112 18:00','20100112 19:00',  1    ,2   )
																														
GO																											
																												 
INSERT INTO  [dbo].[GP_educatedIn](educationid,userid) VALUES (  101,10 )
SET NOCOUNT OFF
SET QUOTED_IDENTIFIER ON
EXEC sp_MSforeachtable "ALTER TABLE ? WITH CHECK CHECK CONSTRAINT all"																							 


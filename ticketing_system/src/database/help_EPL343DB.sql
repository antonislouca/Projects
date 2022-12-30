-- This is how you INSERT:
-- Employee:
    -- INSERT INTO EMPLOYEE (EMP_USERNAME,EMP_PASSWORD,EMP_EMAIL,EMP_PHONE,EMP_F_NAME,EMP_L_NAME, ISADMIN)
    -- VALUES ('user1', 'pass1', 'email@domain.com', 99887766, 'name1', 'last1', 0)
-- FAQ:
    -- INSERT INTO FAQ (FAQ_Q, FAQ_A)
    -- VALUES ('question1', 'answer1')
-- Registration:
    -- INSERT INTO REGISTRATION (R_USERNAME, R_PASSWORD, R_EMAIL, R_PHONE, R_F_NAME, R_L_NAME)
    -- VALUES ('user1', 'pass1', 'email@domain.com', 99887766, 'name1', 'last1')
-- Ticket:
    -- INSERT INTO TICKET (T_PRIORITY,T_CATEGORY,T_DETAILS,T_EMP_ID)
    -- VALUES ('priority1', 'category1', 'details1', 1)
 -- Ticket:
    -- INSERT INTO TICKET (T_PRIORITY, T_CATEGORY, T_DETAILS, T_STATUS, T_S_TIME, T_DURATION, T_EMP_ID, T_ASSIGNED_EMP_ID)
    -- VALUES ('priority1', 'category1', 'details1', 1, '2020-11-27', 30.5, 1, 1)
    

-- Newly registered Users:

-- BEGIN TRANSACTION
--     INSERT INTO EMPLOYEE (EMP_USERNAME, EMP_PASSWORD, EMP_EMAIL, EMP_PHONE, EMP_F_NAME, EMP_L_NAME)
--     SELECT R_USERNAME, R_PASSWORD, R_EMAIL, R_PHONE, R_F_NAME, R_L_NAME
--     FROM REGISTRATION
--     WHERE R_ID = 1;

--     DELETE FROM REGISTRATION
--     WHERE R_ID = 1;
-- COMMIT;
SET NOCOUNT ON
SET QUOTED_IDENTIFIER OFF

DELETE FROM EMPLOYEE
DBCC CHECKIDENT (employee, RESEED, 0)

INSERT INTO EMPLOYEE (EMP_USERNAME,EMP_PASSWORD,EMP_EMAIL,EMP_PHONE,EMP_F_NAME,EMP_L_NAME)
VALUES ('A','B','C',12345,'E','F')
INSERT INTO EMPLOYEE (EMP_USERNAME,EMP_PASSWORD,EMP_EMAIL,EMP_PHONE,EMP_F_NAME,EMP_L_NAME)
VALUES ('delta','empsilon','zhta@hot',99914033,'Emily','Fork')

select *
from EMPLOYEE


delete from REGISTRATION
DBCC CHECKIDENT (registration, RESEED, 0)

insert into REGISTRATION (R_USERNAME, R_PASSWORD, R_EMAIL, R_PHONE, R_F_NAME, R_L_NAME)
VALUEs ('userA', 'passA', 'emailA', 923, 'fnameA','lnameA')

SELECT * FROM EMPLOYEE
SELECT * FROM REGISTRATION
SELECT * FROM FAQ
SELECT * FROM TICKET
SELECT * FROM COMP_POLICY

----
SELECT T_ID, T_PRIORITY, T_CATEGORY, T_DESCRIPTION,
T_STATUS, T_S_TIME, T_DURATION, E1.EMP_USERNAME, E2.EMP_USERNAME
FROM TICKET, EMPLOYEE E1, EMPLOYEE E2
WHERE TICKET.T_EMP_ID = E1.EMP_ID AND T_STATUS = 1
AND TICKET.T_ASSIGNED_EMP_ID = E2.EMP_ID 
---

-- Reset FAQ table
DROP TABLE FAQ
CREATE TABLE FAQ
(
  FAQ_ID INT IDENTITY(1,1) PRIMARY KEY,
  FAQ_Q NVARCHAR(500) NOT NULL,
  FAQ_A NVARCHAR(4000) NOT NULL,
)
INSERT INTO FAQ (FAQ_Q, FAQ_A) VALUES ('What does FAQs mean? ', 'FAQs stand for frequently asked questions.')
INSERT INTO FAQ (FAQ_Q, FAQ_A) VALUES ('How to log out? ', 'To log out simply click on the top right button.')
INSERT INTO FAQ (FAQ_Q, FAQ_A) VALUES ('How to print my document? ', 'To print your files you need to specify the correct printer. That is: PRINTER_ATOS.')
INSERT INTO FAQ (FAQ_Q, FAQ_A) VALUES ('When will my document be approved ? ', 'The documents need some time to be examined. Thus the usual waiting time is a week.')
INSERT INTO FAQ (FAQ_Q, FAQ_A) VALUES ('How to reset my password? ', 'To reset your password you need to send a ticket requesting that! ')

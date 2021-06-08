-- used to insert into table
INSERT INTO iSpy.LoginInfo (username, password, email)
VALUES ('hi', 'hello', 'blahblah@gmail.com');

-- used to check if password and username is correct
SELECT username FROM iSpy.LoginInfo
WHERE username='hi' AND password='hello';

-- used to check if there is a username, wrong password
SELECT username FROM iSpy.LoginInfo
WHERE username='hi';
TRUNCATE TABLE tbl_user RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_user_photo RESTART IDENTITY AND COMMIT NO CHECK;

-- Insert data into tbl_user
INSERT INTO tbl_user (user_id, email, password, name, last_name, is_admin, locale)
VALUES (1, 'mail@maill.com', 'password', 'first name', 'last name', false, 'en'),
       (2, 'mail2@mail.com', 'password', 'first name', 'last name', true, 'en');

INSERT INTO tbl_user_photo
VALUES (1, X'00');
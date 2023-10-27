TRUNCATE TABLE tbl_user RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_user_verification_token RESTART IDENTITY AND COMMIT NO CHECK;

-- Insert data into tbl_user
INSERT INTO tbl_user (user_id, email, password, name, last_name, is_admin,locale)
VALUES (1, 'mail@maill.com', 'password', 'first name', 'last name', false, 'en'),
(2, 'mail2@maill.com', 'password', 'first name', 'last name', false, 'en');

INSERT INTO tbl_user_verification_token (id, user_id, token)
VALUES (1, 1, '7c734a3f-7e90-42f8-88e1-c031cc132961');
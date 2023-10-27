TRUNCATE TABLE tbl_user RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_difficulty RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_recipe RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_follows RESTART IDENTITY AND COMMIT NO CHECK;


-- Insert data into tbl_user
INSERT INTO tbl_user (user_id, email, password, name, last_name, is_admin, is_verified, locale)
VALUES (1, 'mail@maill.com', 'password', 'first name', 'last name', false, true, 'en'),
       (2, 'mail2@mail.com', 'password', 'first name', 'last name', true, true,  'en'),
       (3, 'mail3@mail.com', 'password4', 'namee', 'lastnameee', true, true, 'en'),
       (4, 'mail4@mail.com', 'password4', 'namee', 'lastnameee', true, false, 'en');
INSERT INTO tbl_difficulty (difficulty_name)
VALUES ('easy'),
       ('intermediate'),
       ('hard');

INSERT INTO tbl_recipe (recipe_id, title, description, user_id, is_private, total_minutes, difficulty_id, servings, instructions, language )
VALUES (1, 'title', 'description', 1, false, 75, 1, 4, 'Instruction 1#Instruction 2#Ins3', 'en');


INSERT INTO tbl_follows(user_id, user_followed_id)
VALUES(2,1),
      (3,1),
       (1,2);
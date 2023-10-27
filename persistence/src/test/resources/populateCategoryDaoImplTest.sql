TRUNCATE TABLE tbl_user RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_difficulty RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_recipe RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_category RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_recipe_category RESTART IDENTITY AND COMMIT NO CHECK;

-- Insert data into tbl_user
INSERT INTO tbl_user (user_id, email, password, name, last_name, is_admin, locale)
VALUES (1, 'mail@maill.com', 'password', 'first name', 'last name', false, 'en');

INSERT INTO tbl_difficulty (difficulty_name)
VALUES ('easy'),
       ('intermediate'),
       ('hard');

INSERT INTO tbl_recipe (recipe_id, title, description, user_id, is_private, total_minutes, difficulty_id, servings, instructions, language)
VALUES (1, 'title', 'description', 1, false, 75, 1, 4, 'Instruction 1#Instruction 2#Ins3', 'en');


INSERT INTO tbl_category(category_id, category_name)
VALUES (1, 'Breakfast&brunch'), (2, 'Lunch'), (3, 'Dinner');

INSERT INTO tbl_recipe_category(recipe_id, category_id)
VALUES (1,1), (1, 2);
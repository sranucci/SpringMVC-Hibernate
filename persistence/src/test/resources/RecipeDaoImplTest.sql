TRUNCATE TABLE tbl_user RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_difficulty RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE tbl_recipe RESTART IDENTITY AND COMMIT NO CHECK;

-- Insert data into tbl_user
INSERT INTO tbl_user (user_id, email, password, name, last_name, is_admin, locale)
VALUES (1, 'mail@maill.com', 'password', 'first name', 'last name', false, 'en'),
       (2, 'mail2@mail.com', 'password', 'first name', 'last name', true, 'en'),
       (3, 'mail3@mail.com', 'password', 'first name', 'last name', true, 'en');

INSERT INTO tbl_difficulty (difficulty_name)
VALUES ('easy'),
       ('intermediate'),
       ('hard');

INSERT INTO tbl_recipe (recipe_id, title, description, user_id, is_private, total_minutes, difficulty_id, servings, instructions, language )
VALUES (1, 'title', 'description', 1, false, 75, 1, 4, 'Instruction 1#Instruction 2#Ins3', 'en');
VALUES (2, 'title2', 'description2', 1, false, 75, 1, 4, 'Instruction 1#Instruction 2#Ins3', 'en');

INSERT INTO tbl_like_dislike(recipe_id, user_id, is_like)
VALUES (1, 1, true), (1, 2, false);

INSERT INTO tbl_recipe_photo(id, recipe_id, photo_data, is_primary_photo)
VALUES (1,1,X'00',true);

INSERT INTO tbl_comment (recipe_id, user_id, comment_content)
VALUES (1, 1, 'Loved the recipe'), (1, 1, 'Did I mention i really loved it?'), (1, 2, 'Just mediocre');

INSERT INTO tbl_ingredient(ingredient_id, ingredient_name)
VALUES (1, 'salt'), (2, 'sugar');

INSERT INTO tbl_units(unit_id, unit_name)
VALUES (1, 'g'), (2, 'ml');


INSERT INTO tbl_recipe_ingredient(recipe_id, ingredient_id, unit_id, quantity)
VALUES (1,1,1,1);

INSERT INTO tbl_category(category_id, category_name)
VALUES (1, 'Breakfast&brunch'), (2, 'Lunch');

INSERT INTO tbl_recipe_category(recipe_id, category_id)
VALUES (1,1);

INSERT INTO tbl_follows(user_id, user_followed_id)
VALUES(1,2),
(2,1);
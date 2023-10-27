INSERT INTO tbl_user (user_id, email, password, name, last_name, created_at, is_admin, locale)
VALUES (1, 'asdf', 'asdf', 'asdf', 'asdf', '2023-04-26 03:26:29.268636', true, 'en');

INSERT INTO tbl_difficulty (difficulty_id, difficulty_name)
VALUES (1, 'easy');

INSERT INTO tbl_recipe (recipe_id, title, description, user_id, is_private, total_minutes, difficulty_id, servings, instructions, language )
VALUES
        (1, 'title', 'description', 1, false, 75, 1, 4, 'Instruction 1#Instruction 2#Ins3', 'en'),
        (2, 'title2', 'description2', 1, true, 1, 1, 4, 'Instruction 1#Instruction 2#Ins3', 'en'),
        (3, 'title3', 'description3', 1, true, 75, 1, 4, 'Instruction 1', 'en');

INSERT INTO tbl_ingredient(ingredient_id, ingredient_name)
VALUES (1, 'salt'), (2, 'sugar');

INSERT INTO tbl_units(unit_id, unit_name)
VALUES (1, 'g'), (2, 'ml');

INSERT INTO tbl_recipe_ingredient(recipe_id, ingredient_id, unit_id, quantity)
VALUES (1,1,1,1), (1, 2, 2, 34);
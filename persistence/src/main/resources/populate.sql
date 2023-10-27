-- Insert data into tbl_user
INSERT INTO tbl_user (email, password, name, last_name, is_admin, is_verified)
SELECT 'exampleUser@example.com', 'password1', 'John', 'Doe', true, true
UNION ALL
SELECT 'exampleAdmin@example.com', 'password1', 'Jane', 'Doe', false, true
WHERE NOT EXISTS(SELECT 1 FROM tbl_user);

-- Insert data into tbl_difficulty
-- inserta las dificultades si la tabla esta vacia
INSERT INTO tbl_difficulty (difficulty_name)
SELECT 'easy'
UNION ALL
SELECT 'intermediate'
UNION ALL
SELECT 'hard'
WHERE NOT EXISTS(SELECT 1 FROM tbl_difficulty);

-- Insert data into tbl_category
INSERT INTO tbl_category (category_name)
SELECT 'Breakfast&Brunch'
UNION ALL
SELECT 'Lunch'
UNION ALL
SELECT 'Dinner'
UNION ALL
SELECT 'Appetizer&Snacks'
UNION ALL
SELECT 'Salad'
UNION ALL
SELECT 'Side-dish'
UNION ALL
SELECT 'Baking'
UNION ALL
SELECT 'Dessert'
UNION ALL
SELECT 'Soup'
UNION ALL
SELECT 'HolidayRecipes'
UNION ALL
SELECT 'VegetarianDishes'
UNION ALL
SELECT 'Quick&Easy'
UNION ALL
SELECT 'Healthy'
WHERE NOT EXISTS(SELECT 1 FROM tbl_category);


-- Insert data int tbl_units
INSERT INTO tbl_units (unit_name)
SELECT ('g')
UNION ALL
SELECT ('ml')
UNION ALL
SELECT ('cup')
UNION ALL
SELECT ('tblSpoon')
UNION ALL
SELECT ('teaSpoon')
UNION ALL
SELECT ('unit')
WHERE NOT EXISTS(SELECT 1 FROM tbl_units);

-- Insert data into tbl_ingredient
INSERT INTO tbl_ingredient (ingredient_name)
select 'Acorn squash' union all
select 'Agave nectar' union all
select 'Allspice' union all
select 'Almond butter' union all
select 'Almond extract' union all
select 'Almond flour' union all
select 'Almonds' union all
select 'Anchovies' union all
select 'Apple cider vinegar' union all
select 'Artificial sweetener' union all
select 'Arugula' union all
select 'Asparagus' union all
select 'Avocado oil' union all
select 'Baby potatoes' union all
select 'Bacon' union all
select 'Bagels' union all
select 'Baking powder' union all
select 'Balsamic vinegar' union all
select 'Barbecue sauce' union all
select 'Basil' union all
select 'Bay leaves' union all
select 'Beef broth' union all
select 'Beet greens' union all
select 'Bell pepper' union all
select 'Blue cheese dressing' union all
select 'Blue cheese' union all
select 'Bok choy' union all
select 'Bread' union all
select 'Broccoli' union all
select 'Brown rice' union all
select 'Brown sugar' union all
select 'Brussels sprouts' union all
select 'Butter' union all
select 'Butternut squash' union all
select 'Cabbage' union all
select 'Caesar dressing' union all
select 'Canola oil' union all
select 'Capers' union all
select 'Cardamom' union all
select 'Carrots' union all
select 'Cashews' union all
select 'Catfish' union all
select 'Cauliflower' union all
select 'Cayenne pepper' union all
select 'Celery' union all
select 'Cheddar cheese' union all
select 'Cheese' union all
select 'Chicken breasts' union all
select 'Chicken broth' union all
select 'Chicken thighs' union all
select 'Chicken wings' union all
select 'Chicken' union all
select 'Chili powder' union all
select 'Cilantro' union all
select 'Cinnamon rolls' union all
select 'Cinnamon' union all
select 'Clams' union all
select 'Cloves' union all
select 'Coconut extract' union all
select 'Coconut flour' union all
select 'Coconut milk' union all
select 'Coconut oil' union all
select 'Cod' union all
select 'Colby cheese' union all
select 'Collard greens' union all
select 'Coriander' union all
select 'Corn' union all
select 'Cornstarch' union all
select 'Cottage cheese' union all
select 'Crab meat' union all
select 'Crab' union all
select 'Cream cheese' union all
select 'Croissants' union all
select 'Cumin' union all
select 'Dill' union all
select 'Edamame' union all
select 'Eggs' union all
select 'Endive' union all
select 'English muffins' union all
select 'Fennel' union all
select 'Feta cheese' union all
select 'Fingerling potatoes' union all
select 'Flaxseed oil' union all
select 'Flour' union all
select 'Garlic cloves' union all
select 'Garlic powder' union all
select 'Garlic' union all
select 'Ginger root' union all
select 'Ginger' union all
select 'Goat cheese' union all
select 'Grapeseed oil' union all
select 'Greek yogurt' union all
select 'Green beans' union all
select 'Green bell pepper' union all
select 'Green onion' union all
select 'Ground beef' union all
select 'Ground chicken' union all
select 'Ground pork' union all
select 'Ground turkey' union all
select 'Halibut' union all
select 'Ham' union all
select 'Hazelnuts' union all
select 'Heavy cream' union all
select 'Himalayan salt' union all
select 'Honey mustard dressing' union all
select 'Honey' union all
select 'Hot dogs' union all
select 'Hot sauce' union all
select 'Hummus' union all
select 'Iceberg lettuce' union all
select 'Italian dressing' union all
select 'Kale' union all
select 'Ketchup' union all
select 'Kosher salt' union all
select 'Leeks' union all
select 'Lemon extract' union all
select 'Lemon juice' union all
select 'Lemon' union all
select 'Lime juice' union all
select 'Lime' union all
select 'Lobster' union all
select 'Macadamia nuts' union all
select 'Mahi mahi' union all
select 'Maple syrup' union all
select 'Mayonnaise' union all
select 'Milk' union all
select 'Mint extract' union all
select 'Mint' union all
select 'Molasses' union all
select 'Monterey jack cheese' union all
select 'Mozzarella cheese' union all
select 'Mussels' union all
select 'Mustard greens' union all
select 'Mustard' union all
select 'Napa cabbage' union all
select 'New potatoes' union all
select 'Nutmeg' union all
select 'Oats' union all
select 'Olive oil' union all
select 'Olives' union all
select 'Onion powder' union all
select 'Onion' union all
select 'Orange bell pepper' union all
select 'Orange extract' union all
select 'Orange juice' union all
select 'Oregano' union all
select 'Paprika' union all
select 'Parmesan cheese' union all
select 'Parsley' union all
select 'Pasta' union all
select 'Peanut butter' union all
select 'Peanuts' union all
select 'Peas' union all
select 'Pecans' union all
select 'Pepper jack cheese' union all
select 'Pepper' union all
select 'Pepperoni' union all
select 'Pickles' union all
select 'Pineapple juice' union all
select 'Pink salt' union all
select 'Pistachios' union all
select 'Pizza dough' union all
select 'Pork chops' union all
select 'Pork tenderloin' union all
select 'Pork' union all
select 'Potatoes' union all
select 'Powdered sugar' union all
select 'Provolone cheese' union all
select 'Purple eggplant' union all
select 'Quinoa' union all
select 'Radicchio' union all
select 'Ranch dressing' union all
select 'Red bell pepper' union all
select 'Red cabbage' union all
select 'Red onion' union all
select 'Red pepper flakes' union all
select 'Red potatoes' union all
select 'Red wine vinegar' union all
select 'Relish' union all
select 'Rice vinegar' union all
select 'Rice' union all
select 'Romaine lettuce' union all
select 'Rosemary' union all
select 'Russet potatoes' union all
select 'Sage' union all
select 'Salmon fillets' union all
select 'Salmon' union all
select 'Salt' union all
select 'Sausage' union all
select 'Savoy cabbage' union all
select 'Scallops' union all
select 'Sea salt' union all
select 'Sesame oil' union all
select 'Shallot' union all
select 'Shrimp' union all
select 'Snow peas' union all
select 'Sour cream' union all
select 'Soy sauce' union all
select 'Spaghetti squash' union all
select 'Spinach' union all
select 'Spring mix' union all
select 'Steak' union all
select 'Stevia' union all
select 'Sugar snap peas' union all
select 'Sugar' union all
select 'Sweet potatoes' union all
select 'Swiss chard' union all
select 'Swiss cheese' union all
select 'Swordfish' union all
select 'Tahini' union all
select 'Teriyaki sauce' union all
select 'Thousand island dressing' union all
select 'Thyme' union all
select 'Tilapia fillets' union all
select 'Tilapia' union all
select 'Tomatoes' union all
select 'Tortillas' union all
select 'Trout' union all
select 'Tuna' union all
select 'Turkey breast' union all
select 'Turkey' union all
select 'Turmeric' union all
select 'Turnip greens' union all
select 'Vanilla extract' union all
select 'Vegetable broth' union all
select 'Vegetable oil' union all
select 'Vinegar' union all
select 'Walnuts' union all
select 'Water' union all
select 'Watercress' union all
select 'White rice' union all
select 'White wine vinegar' union all
select 'Whole chicken' union all
select 'Whole wheat flour' union all
select 'Worcestershire sauce' union all
select 'Yellow bell pepper' union all
select 'Yellow onion' union all
select 'Yellow squash' union all
select 'Yogurt' union all
select 'Yukon gold potatoes' union all
select 'Zucchini'
WHERE NOT EXISTS(SELECT 1 FROM tbl_ingredient);


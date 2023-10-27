CREATE TABLE IF NOT EXISTS tbl_user
(
    user_id    SERIAL PRIMARY KEY,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    name       VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    is_admin   BOOLEAN      NOT NULL DEFAULT false,
    is_verified BOOLEAN     NOT NULL DEFAULT true,
    locale VARCHAR(255)    NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_difficulty
(
    difficulty_id   SERIAL PRIMARY KEY,
    difficulty_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_recipe (
    recipe_id     SERIAL PRIMARY KEY,
    title         VARCHAR(100)  NOT NULL,
    description   VARCHAR(512)  NOT NULL,
    user_id       INT           NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    is_private    BOOLEAN       NOT NULL,
    total_minutes INT           NOT NULL CHECK (total_minutes > 0),
    difficulty_id INT           NOT NULL REFERENCES tbl_difficulty (difficulty_id) ON DELETE RESTRICT,
    servings      INT           NOT NULL CHECK (servings > 0),
    created_at    TIMESTAMP     NOT NULL DEFAULT NOW(),
    instructions  VARCHAR(4096) NOT NULL,
    language VARCHAR(5) NOT NULL
);



CREATE TABLE IF NOT EXISTS tbl_recipe_photo
(
    id             SERIAL PRIMARY KEY,
    recipe_id      INTEGER      NOT NULL REFERENCES tbl_recipe (recipe_id) ON DELETE CASCADE,
    photo_data     BYTEA        NOT NULL,
    is_primary_photo BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_comment
(
    recipe_id       INT          NOT NULL REFERENCES tbl_recipe (recipe_id) ON DELETE CASCADE,
    user_id         INT          NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    comment_id      SERIAL PRIMARY KEY,
    comment_content VARCHAR(256) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tbl_like_dislike
(
    recipe_id INT     NOT NULL REFERENCES tbl_recipe (recipe_id) ON DELETE CASCADE,
    user_id   INT     NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    is_like   BOOLEAN NOT NULL DEFAULT true,
    PRIMARY KEY (recipe_id, user_id)
);

CREATE TABLE IF NOT EXISTS tbl_ingredient
(
    ingredient_id   SERIAL PRIMARY KEY,
    ingredient_name VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS tbl_units
(
    unit_id   SERIAL PRIMARY KEY,
    unit_name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_category
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_recipe_category
(
    recipe_id   INT NOT NULL REFERENCES tbl_recipe (recipe_id) ON DELETE CASCADE,
    category_id INT NOT NULL REFERENCES tbl_category (category_id) ON DELETE CASCADE,
    PRIMARY KEY (recipe_id, category_id)
);

CREATE TABLE IF NOT EXISTS tbl_recipe_ingredient
(
    recipe_id     INT   NOT NULL REFERENCES tbl_recipe (recipe_id) ON DELETE CASCADE,
    ingredient_id INT   NOT NULL REFERENCES tbl_ingredient (ingredient_id) ON DELETE CASCADE,
    unit_id       INT   NOT NULL REFERENCES tbl_units (unit_id) ON DELETE RESTRICT,
    quantity      FLOAT NOT NULL CHECK ( quantity > 0 ),
    PRIMARY KEY (recipe_id, ingredient_id)
);

CREATE TABLE IF NOT EXISTS tbl_comment_like_dislike
(
    comment_id INT                  NOT NULL REFERENCES tbl_comment (comment_id) ON DELETE CASCADE,
    user_id    INT                  NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    is_like    BOOLEAN DEFAULT TRUE NOT NULL,
    PRIMARY KEY (comment_id, user_id)
);

CREATE TABLE IF NOT EXISTS tbl_saved_by_user
(
    recipe_id INT NOT NULL REFERENCES tbl_recipe (recipe_id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id   INT NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (recipe_id, user_id)
);

CREATE TABLE IF NOT EXISTS tbl_user_photo
(
    user_id    INT PRIMARY KEY NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    photo_data BYTEA           NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_reset_password_token
(
    id              SERIAL,
    user_id         INT       NOT NULL,
    token           TEXT,
    expiration_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbl_user_verification_token
(
    id      SERIAL,
    user_id INT NOT NULL,
    token   TEXT,
    FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbl_follows
(
    user_id              INT                  NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    user_followed_id     INT                  NOT NULL REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, user_followed_id)
);

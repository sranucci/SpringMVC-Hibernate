package ar.edu.itba.paw.persistence;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class GlobalTestVariables {

    //tbl_user
    public static final String EMAIL = "mail@maill.com";
    public static final String PASSWORD = "password";
    public static final String PASSWORD2 = "password2";
    public static final String FIRST_NAME = "first name";
    public static final String FIRST_NAME2 = "firstt";
    public static final String LAST_NAME = "last name";
    public static final String LAST_NAME2 = "last name";
    public static final boolean IS_ADMIN = false;
    public static final Long USER_ID = 1L;
    public static final Long USER_ID_2 = 2L;
    public static final Long USER_ID_3 = 3L;
    public static final Long USER_ID_4 = 4L;
    public static final long NON_EXISTENT_USER_ID = 0;
    public static final Locale LOCALE = Locale.ENGLISH;

    //tbl_recipe
    public static final Long RECIPE_ID = 1L;
    public static final Long RECIPE_ID_2 = 1L;
    public static final long NON_EXISTENT_RECIPE_ID = 0;

    public static final String RECIPE_TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final boolean IS_PRIVATE = false;
    public static final int TOTAL_MINUTES = 75;
    public static final int MINUTES = 15;
    public static final int HOURS = 1;
    public static final int DIFFICULTY = 1;
    public static final int SERVINGS = 4;
    public static final Instant DATE = Instant.now();
    public static final String INSTRUCTIONS = "Instruction 1#Instruction 2#Ins3";
    public static final String[] INSTRUCTIONS_ARRAY = {"Instruction 1", "Instruction 2", "Ins3"};

    //tbl_ingredient
    public static final String INGREDIENT_STR_1 = "salt";
    public static final long INGREDIENT_ID_1 = 1;
    public static final String INGREDIENT_STR_2 = "salt";
    public static final long INGREDIENT_ID_2 = 2;


    //tbl_units
    public static final String UNIT_STR_1 = "g";
    public static final long UNIT_ID_1= 1;
    public static final String UNIT_STR_2 = "ml";
    public static final long UNIT_ID_2= 2;


    //tbl_category
    public static final String CATEGORY_STR_1 = "Breakfast&brunch";
    public static final long CATEGORY_ID_1= 1L;
    public static final String CATEGORY_STR_2 = "Lunch";
    public static final long CATEGORY_ID_2= 2L;

    public static final byte[] IMG_BYTEA= new byte[]{0x00};

    public static final String LANGUAGE_EN = "en";


    private GlobalTestVariables() {
    }
}

package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.dtos.UploadedRecipeFormDto;
import ar.edu.itba.paw.models.ingredient.RecipeIngredientTransporter;
import ar.edu.itba.paw.servicesInterface.exceptions.FailedToLoadResourceException;
import ar.edu.itba.paw.webapp.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ValidHoursAndMinutes
public abstract class AbstractRecipeFormTemplate {

    protected static final int MAXSIZE = 10 * 1024 * 1024;//10MB

    @ValidOptionalImages
    private List<MultipartFile> recipeImages;

    @ValidLanguage
    private String language;

    @ValidCategories
    private Long[] categories;

    @ValidVisibility
    private String visibility;
    @Size(min = 1, max = 100)
    private String title;


    @Size(min = 1, max = 512)
    private String description;
    @ValidQuantityArray
    private Float[] quantitys;
    @ValidIngredientArray
    private String[] ingredients;
    @ValidMeasureIdArray
    private Long[] measureIds;
    @ValidInstructionArray
    private String[] instructions;
    @ValidServings
    private Integer servings;
    @ValidHours
    private Integer totalHours;
    @ValidMinutes
    private Integer totalMinutes;
    @DifficultyIdValid
    private Long difficulty;




    private <T> T[] cleanArray(T[] arr) {
        if (arr == null)
            return null;
        return Arrays.stream(arr).filter(Objects::nonNull).toArray(size -> Arrays.copyOf(arr, size));
    }

    public Float[] getCleanQuantitys() {
        return cleanArray(quantitys);
    }

    public Long[] getCleanMeasureIds() {
        return cleanArray(measureIds);
    }

    public String[] getCleanIngredients() {
        return cleanArray(ingredients);
    }


    public RecipeIngredientTransporter[] getRecipeIngredients() {
        if (measureIds == null || ingredients == null || quantitys == null) {
            return null;
        }
        List<RecipeIngredientTransporter> l = new LinkedList<>();
        for (int i = 0; i < ingredients.length; i++) {
            if (ingredients[i] != null || quantitys[i] != null)
                l.add(new RecipeIngredientTransporter(ingredients[i], measureIds[i], quantitys[i]));
        }
        return l.toArray(new RecipeIngredientTransporter[0]);
    }


    public String[] getCleanInstructions() {
        return cleanArray(instructions);
    }


    protected int getTotalRecipeTime() {
        if (totalHours == null)
            totalHours = 0;
        if (totalMinutes == null)
            totalMinutes = 0;
        return totalHours * 60 + totalMinutes;
    }


    protected List<byte[]> getImagesAsBytes() {
        try {
            List<byte[]> imageByteList = new LinkedList<>();
            for (MultipartFile image : recipeImages) {
                if (!image.isEmpty())
                    imageByteList.add(image.getBytes());
            }
            return imageByteList;
        } catch (Exception e) {
            throw new FailedToLoadResourceException("Unexpected error while processing the images");
        }
    }


    public abstract UploadedRecipeFormDto asUploadedFormDto();

}

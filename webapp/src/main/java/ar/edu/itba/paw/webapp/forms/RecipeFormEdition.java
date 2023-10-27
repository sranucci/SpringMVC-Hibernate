package ar.edu.itba.paw.webapp.forms;


import ar.edu.itba.paw.dtos.UploadedRecipeFormDto;
import ar.edu.itba.paw.models.ingredient.RecipeIngredient;
import ar.edu.itba.paw.models.recipe.RecipeImage;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.webapp.annotations.ValidNewMainImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RecipeFormEdition extends AbstractRecipeFormTemplate {


    private List<Long> imageIdLists;

    private long mainImageId;

    @ValidNewMainImage
    private MultipartFile newMainImage;


    private byte[] getMainImage(){
        try{
            if (newMainImage.isEmpty()){
                return null;
            }
            return newMainImage.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error reading new file");
        }
    }

    public void setEditionVisibility(boolean isPrivate) {
        setVisibility(isPrivate ? "Private" : "Public");
    }

    public void setTimeFromTotalMinutes(int totalMinutes) {
        setTotalHours(totalMinutes / 60);
        setTotalMinutes(totalMinutes % 60);
    }

    public void setFromRecipe(Recipe recipe) {
        setLanguage(recipe.getLanguage());
        setEditionVisibility(recipe.getIsPrivate());
        setCategories(recipe.getRecipeCategories().stream().map(r -> r.getCategory().getCategoryId()).toArray(Long[]::new));
        setTitle(recipe.getTitle());
        setDescription(recipe.getDescription());

        int size = recipe.getIngredients().size();
        Float[] qts = new Float[size];
        String[] ingredients = new String[size];
        Long[] measureIds = new Long[size];

        for ( int i = 0 ; i < size; i++){
            RecipeIngredient currentIngredient = recipe.getIngredients().get(i);
            qts[i] = currentIngredient.getQuantity();
            ingredients[i] = currentIngredient.getIngredient().getName();
            measureIds[i] = currentIngredient.getUnit().getId();
        }


        setQuantitys(qts);
        setIngredients(ingredients);
        setMeasureIds(measureIds);
        setInstructions(recipe.getInstructions());
        setServings(recipe.getServings());
        setTimeFromTotalMinutes(recipe.getTotalMinutes());
        setDifficulty(recipe.getDifficulty());
        setImageIdLists(recipe.getRecipeImages().stream().filter(img -> !img.isMainImage()).map(RecipeImage::getImageId).collect(Collectors.toList()));
        setMainImageId(recipe.getMainRecipeImage().getImageId());

    }

    @Override
    public UploadedRecipeFormDto asUploadedFormDto() {
        return new UploadedRecipeFormDto(getLanguage(),getServings(), getCategories(), getTitle(),getMainImage(),
                getImagesAsBytes(), getDescription(), getCleanQuantitys(), getCleanIngredients(),
                getCleanMeasureIds(), getCleanInstructions(), getTotalRecipeTime(), getDifficulty(), getVisibility().equals("Private"), imageIdLists);
    }


}

package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.dtos.UploadedRecipeFormDto;
import ar.edu.itba.paw.webapp.annotations.ValidMainImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class RecipeForm extends AbstractRecipeFormTemplate {



    @ValidMainImage
    private MultipartFile newMainImage;


    private byte[] getMainImageAsBytes(){
        try {
            return newMainImage.getBytes();
        } catch ( IOException e) {
            throw new RuntimeException("Error while loading main image");
        }
    }


    @Override
    public UploadedRecipeFormDto asUploadedFormDto() {
        return new UploadedRecipeFormDto(getLanguage(),getServings(), getCategories(), getTitle(),getMainImageAsBytes(),
                getImagesAsBytes(), getDescription(), getCleanQuantitys(), getCleanIngredients(),
                getCleanMeasureIds(), getCleanInstructions(), getTotalRecipeTime(), getDifficulty(), getVisibility().equals("Private"), null);
    }


}

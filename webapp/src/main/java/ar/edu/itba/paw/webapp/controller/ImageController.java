package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.recipe.RecipeImage;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.servicesInterface.UserImageService;
import ar.edu.itba.paw.servicesInterface.exceptions.FailedToLoadResourceException;
import ar.edu.itba.paw.webapp.exceptions.RecipeImageNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
public class ImageController {
    private RecipeService recipeService;
    private UserImageService userImageService;

    @RequestMapping(value = "/recipeImage/{id:\\d+}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getRecipeImage(@PathVariable("id") final long id) {
        Optional<RecipeImage> image = recipeService.getImage(id);
        RecipeImage imageData = image.orElseThrow(RecipeImageNotFoundException::new);
        return ResponseEntity.ok().contentLength(imageData.getImage().length)
                .contentType(MediaType.IMAGE_JPEG).body(imageData.getImage());
    }

    @RequestMapping(value = "/userImage/{id:\\d+}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserImage(@PathVariable("id") final long userId) {
        byte[] image = userImageService.findUserPhotoByUserId(userId).orElseGet(() -> {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("/pictures/defaultUser.png")) {
                return IOUtils.toByteArray(in);
            } catch (IOException e) {
                throw new FailedToLoadResourceException("Failed to read default user image");
            }
        });
        return ResponseEntity.ok().contentLength(image.length)
                .contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @Autowired
    public void setRecipeService(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Autowired
    public void setUserService(final UserImageService userService) {
        this.userImageService = userService;
    }
}

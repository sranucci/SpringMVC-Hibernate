package ar.edu.itba.paw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

@Getter
@AllArgsConstructor
public class UploadedRecipeFormDto {
    private String language;
    private Integer servings;
    private Long[] categories;
    private String title;
    private byte[] mainImage;
    private List<byte[]> recipeImages;
    private String description;
    private Float[] quantitys;
    private String[] ingredients;
    private Long[] measureIds;
    private String[] instructions;
    private Integer totalTime;
    private Long difficultyId;
    private boolean isPrivate;
    private List<Long> imageIds;





    public Iterable<TripleIngredientSelectionDto> getIngredientsIterable() {
        return new IngredientsIterable(ingredients, quantitys, measureIds);
    }

    @AllArgsConstructor
    private static class IngredientsIterable implements Iterable<TripleIngredientSelectionDto> {

        private String[] ingredients;
        private Float[] qtys;
        private Long[] unitIds;

        @Override
        public Iterator<TripleIngredientSelectionDto> iterator() {
            return new IngredientsIterator();
        }

        private class IngredientsIterator implements Iterator<TripleIngredientSelectionDto> {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < qtys.length;//all ararys have same length
            }

            @Override
            public TripleIngredientSelectionDto next() {
                TripleIngredientSelectionDto out = new TripleIngredientSelectionDto(ingredients[current], qtys[current], unitIds[current]);
                current++;
                return out;
            }
        }
    }


}

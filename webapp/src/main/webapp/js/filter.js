const elem = document.querySelectorAll('select');
M.FormSelect.init(elem);

const parseableJson = document.getElementById("scriptId").getAttribute("data-autocomplete");
let completeData = JSON.parse(parseableJson);

let selectedValues = document.getElementById("scriptId").getAttribute("dto-selected-ingredients");
let chipsData = [];
if (selectedValues !== '') {
    chipsData = selectedValues.split('#').map(tag => {
        return {
            tag: tag,
        }
    });
}
//<spring:message code="filter.ingredient"/>
let placeHolderMessage = document.getElementById("placeholder").innerText
const selectedChips = [];
const options = {
    placeholder: placeHolderMessage,
    secondaryPlaceholder: '+',
    autocompleteOptions: {
        data: completeData,
        limit: Infinity,
        minLength: 1
    },
    data: chipsData
}
let chip = document.querySelector('.chips');
M.Chips.init(chip, options);

let selectedIngredients = document.querySelector('#selectedIngredients');
let searchBarQuery = document.querySelector('#searchBarQuery');

document.getElementById('submit').addEventListener('click', function () {
    const chipsData = M.Chips.getInstance(document.querySelector('.chips')).chipsData;
    if (chipsData.length === 0) {
        selectedIngredients.removeAttribute("name");
    } else {
        selectedIngredients.value = chipsData.map(chip => chip.tag).join('#');
    }
    if (searchBarQuery.value === "") {
        searchBarQuery.removeAttribute("name");
    }
});
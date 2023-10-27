document.addEventListener("DOMContentLoaded", function () {
    const elems = document.querySelectorAll('select');
    M.FormSelect.init(elems);
//modal es para popup
    const modal = document.querySelector('.modal');
    const instance = M.Modal.init(modal);
    instance.open();
    instance.close();
})


//for instructions
function deleteCurrent(event, id) {
    event.preventDefault();//no refreshing
    const element = document.getElementById(id)
    element.remove();
}


function submitForm() {
    document.getElementById("imageInpTag").disabled = false;
    document.getElementById("mainImageInpTag").disabled = false;
}



function handleImagePreview(inpTag,previewTag) {
    const files = document.getElementById(inpTag).files;
    const previewContainer = document.getElementById(previewTag);
    [...files].forEach(file => {
        const reader = new FileReader();
        reader.onload = function () {
            const preview = document.createElement("div");
            preview.classList.add("preview-img-container")
            const image = document.createElement("img");
            image.src = reader.result;
            image.className = "preview-image";
            preview.appendChild(image);
            previewContainer.appendChild(preview);
        }
        reader.readAsDataURL(file);
    });
}

document.getElementById("imageInpTag").addEventListener("change", () => handleImagePreview("imageInpTag","image-preview"));
document.getElementById("mainImageInpTag").addEventListener("change",() => handleImagePreview("mainImageInpTag","main-image-preview"))


function handleMainImageUpload(event) {
    const uploadInput = document.getElementById("mainImageInpTag");
    uploadInput.disabled = true;
    const div = document.getElementById("mainImageInp");
    div.style.backgroundColor = "#00bfa5";
    const spanTag = document.getElementById("main-message-success-image");
    spanTag.innerText = document.getElementById("scriptId").getAttribute("data-image-label");
    const imageIcon = document.getElementById("main-image-icon");
    imageIcon.innerText = "done";
    const clearButton = document.getElementById("clearMainImageButton");
    clearButton.disabled = false;
}


function clearMainImageUpload(event) {
    event.preventDefault();
    const uploadInput = document.getElementById("mainImageInpTag");
    uploadInput.disabled = false;
    uploadInput.value = "";
    const div = document.getElementById("mainImageInp");
    div.style.backgroundColor = "";
    const spanTag = document.getElementById("main-message-success-image");
    spanTag.innerHTML = document.getElementById("scriptId").getAttribute("data-add-photo-label");
    const imageIcon = document.getElementById("main-image-icon");
    imageIcon.innerText = "add_a_photo";
    const clearButton = document.getElementById("clearMainImageButton");
    clearButton.disabled = true;
    //deletion of previewed imagecontent
    const previewImageParentNode = document.getElementById("main-image-preview");
    [...previewImageParentNode.childNodes].forEach(image => image.remove());
}

function clearUpload(event) {
    event.preventDefault();
    const uploadInput = document.getElementById("imageInpTag");
    uploadInput.disabled = false;
    uploadInput.value = "";
    const div = document.getElementById("imageInp");
    div.style.backgroundColor = "";
    const spanTag = document.getElementById("message-success-image");
    spanTag.innerHTML = document.getElementById("scriptId").getAttribute("data-add-photo-label");
    const imageIcon = document.getElementById("image-icon");
    imageIcon.innerText = "add_a_photo";
    const clearButton = document.getElementById("clearImageButton");
    clearButton.disabled = true;

    //deletion of previewed imagecontent
    const previewImageParentNode = document.getElementById("image-preview");
    [...previewImageParentNode.childNodes].forEach(image => image.remove());
}


function handleUpload() {
    const uploadInput = document.getElementById("imageInpTag");
    uploadInput.disabled = true;
    const div = document.getElementById("imageInp");
    div.style.backgroundColor = "#00bfa5";
    const spanTag = document.getElementById("message-success-image");
    spanTag.innerText = document.getElementById("scriptId").getAttribute("data-image-label");
    const imageIcon = document.getElementById("image-icon");
    imageIcon.innerText = "done";
    const clearButton = document.getElementById("clearImageButton");
    clearButton.disabled = false;
}

//-------------------------------------------Image Initialization--------------------------------
const clearImageButton = document.getElementById("clearImageButton");
clearImageButton.addEventListener("click", clearUpload)
const imageInpTag = document.getElementById("imageInpTag");
imageInpTag.addEventListener("change", function () {
    handleUpload();
})

const clearMainImageButton = document.getElementById("clearMainImageButton");
clearMainImageButton.addEventListener("click",clearMainImageUpload);
const mainImageInpTag = document.getElementById("mainImageInpTag");
mainImageInpTag.addEventListener("change", handleMainImageUpload);


const parseableJson = document.getElementById("scriptId").getAttribute("data-autocomplete")
let completeData = JSON.parse(parseableJson)

const scriptTag = document.getElementById("scriptId")
const ingredientInputs = document.querySelectorAll('.ing');
for (let i = 0; i < ingredientInputs.length; i++) {
    M.Autocomplete.init(ingredientInputs[i], {data: completeData})
}


//parametro pasado a script url!
const instructionLength = scriptTag.getAttribute("data-instruction-length")
instructionsSize = parseInt(instructionLength);


//en caso de que haya elementos, reseteo los listeners
for (let instNum = 0; instNum < instructionsSize; instNum++) {
    const id = `target-instruction=${instNum}`
    const instructionTag = document.getElementById(id);
    const button = instructionTag.querySelector("button");
    button.addEventListener("click", (e) => deleteCurrent(e, id))
}

const addInstruction = document.getElementById("add-instruction");


addInstruction.addEventListener("click", (e) => {
    e.preventDefault();
    const instructionContainer = document.querySelector("#instructions-container");
    const finalDivChild = document.createElement("div");
    finalDivChild.classList.add("input-container");
    const currentIdx = instructionsSize;
    finalDivChild.setAttribute("id", `target-instruction=${currentIdx}`);
    const addText = document.getElementById("scriptId").getAttribute("data-step-label");

    finalDivChild.innerHTML = `
                            <div class="input-field input-step">
                                <input name="instructions[${currentIdx}]" class="materialize-textarea textarea" placeholder="${addText}"
                                          type="text" />
                                <span class="helper-text" data-error="Mandatory field"></span>
                            </div>
                            <button class="btn-clear btn-flat"><i class="material-icons">clear</i></button>
    `

    const button = finalDivChild.querySelector("button")

    button.addEventListener("click", (e) => deleteCurrent(e, `target-instruction=${currentIdx}`))
    instructionsSize++;
    instructionContainer.appendChild(finalDivChild)
})


//for ingredients


const unitsJson = JSON.parse(scriptTag.getAttribute("data-units"));


const ingredientsLength = scriptTag.getAttribute("data-ingredients-length");
ingredientsSize = parseInt(ingredientsLength);


for (let ingNum = 0; ingNum < ingredientsSize; ingNum++) {
    const id = `ingredient=${ingNum}`
    const ingTag = document.getElementById(id);
    const button = ingTag.querySelector("button");
    button.addEventListener("click", (e) => deleteCurrent(e, id))
}

const ingredientAddButton = document.querySelector("#add-ingredient");


ingredientAddButton.addEventListener("click", (e) => {
    e.preventDefault();

    const container = document.querySelector("#ingredients-container");
    const div = document.createElement("div");

    const currentIngredientIdx = ingredientsSize;
    div.setAttribute("id", `ingredient=${currentIngredientIdx}`);
    const ingredientDataCaptured = document.getElementById("scriptId")
    const ingredientText = ingredientDataCaptured.getAttribute("data-ingredient-label");
    const quantityText = ingredientDataCaptured.getAttribute("data-quantity-label");
    const measureText = ingredientDataCaptured.getAttribute("data-measure-label");
    div.innerHTML = `
    <div class="input-container">
                            <div class="ingredient-input">
                                <div class="input-field">
                                    <input name="ingredients[${currentIngredientIdx}]" id="ingredient${currentIngredientIdx}" class="ing autocomplete" type="text" 
                                           data-autocomplete="ingredient-autocomplete">
                                    <label for="ingredient${currentIngredientIdx}">${ingredientText}</label>
                                    <span class="helper-text" data-error="Mandatory field"></span>
                                </div>
                            </div>

                            <div id="ingredient-autocomplete" class="autocomplete-content"></div>
                            <div class="quantity-container">
                                <div class="input-field ingredient-input">
                                    <input name="quantitys[${currentIngredientIdx}]" id="quantity${currentIngredientIdx}" type="number" min="0" step="any">
                                    <label for="quantity${currentIngredientIdx}">${quantityText}</label>
                                    <span class="helper-text" data-error="Mandatory field"></span>
                                </div>
                               
                                <div class="input-field measure-select">
                                    <select name="measureIds[${currentIngredientIdx}]" id="measure${currentIngredientIdx}">
                                        ${Object.keys(unitsJson).map(key => `<option value="${key}" label="${unitsJson[key]}">${unitsJson[key]}</option>`)}
                                    </select>
                                    <label for="measure${currentIngredientIdx}">${measureText}</label>
                                    <span class="helper-text" data-error="Mandatory field"></span>
                                </div>
                            </div>

                            <button class="btn-clear btn-flat"><i class="material-icons">clear</i>
                            </button>
                        </div>
                        `
    const button = div.querySelector("button");
    button.addEventListener("click", e => deleteCurrent(e, `ingredient=${currentIngredientIdx}`))
    ingredientsSize++;
    container.appendChild(div);

    const elems = document.querySelectorAll('select');
    M.FormSelect.init(elems);
    const ingredientInput = div.querySelector('.ing');
    M.Autocomplete.init(ingredientInput, {data: completeData})

})













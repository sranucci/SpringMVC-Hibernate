//button modal materialize initialization



//
// document.addEventListener('DOMContentLoaded', function () {
//     const elems = document.querySelectorAll('.modal');
//     const instance = M.Modal.init(elems);
//
//     const btn = document.getElementById("modal-delete-button");
//     btn.addEventListener("click", function () {
//         instance[0].open()
//     });
//     const location = window.location.href
//     if (location.includes("?error"))
//         instance[0].open();
//     if (location.includes("?commentError")) {
//         const equalsIndex = location.lastIndexOf("=")
//         let idx = equalsIndex === -1 ? 0 : parseInt(location.substring(equalsIndex + 1));
//         instance[idx].open();
//     }
// });

document.addEventListener('DOMContentLoaded', function () {
    const elems = document.querySelectorAll('.modal');
    const instance = M.Modal.init(elems);

    const btn = document.getElementById("modal-delete-button");
    btn.addEventListener("click", function () {
        instance[0].open()
    });
    const location = window.location.href
    if (location.includes("?error"))
        instance[0].open();
    if (location.includes("?commentError"))
        instance[1].open();
});


//success comment popup
function confirmComment() {
    var popup = document.getElementById("popup");
    popup.style.display = "block";

    setTimeout(function() {
        popup.style.display = "none";
        document.getElementById("commentForm");
    }, 2000);
}
// Check if `commented` is true and call `confirmComment` if needed
var commented = document.getElementById("scriptLoader").getAttribute("data-commented");
if (commented) {
    confirmComment();
}

function comment(){
    document.getElementById("commentValue").value = true;
    document.getElementById("commentForm").submit();
}

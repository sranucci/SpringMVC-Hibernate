
const thumbnailImages = document.querySelectorAll('.thumbnail-image img');


function showAsMainImage(url) {
    const mainImage = document.querySelector('.main-image img');
    mainImage.src = url;
}

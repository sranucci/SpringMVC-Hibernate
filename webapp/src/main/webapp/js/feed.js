
let currenttime = document.getElementById("scriptId").getAttribute("time");
let recipeId = document.getElementById("scriptId").getAttribute("id");

let time = document.getElementById("time_"+recipeId);
console.log("time_"+recipeId)
let currentDate = new Date();
let recipeDate = new Date(currenttime)
let time_string = ""

// Calculate the difference in milliseconds
let difference = currentDate - recipeDate;

// Convert the difference to minutes
let minutesDifference = Math.floor(difference / (1000 * 60));

if(minutesDifference <= 60){
    time_string = minutesDifference + (minutesDifference == 1 ? " minute" : " minutes");
} else {
    let hoursDifference = Math.floor(difference / (1000 * 60 * 60));
    if(hoursDifference <= 24){
        time_string = hoursDifference + (hoursDifference == 1 ? " hour" : " hours");
    }else{
        let daysDifference = Math.floor(difference / (1000 * 60 * 60 * 24));
        time_string = daysDifference + (daysDifference == 1 ? " day" : " days");
    }
}
time.innerHTML=time_string;
console.log("employeeLanding.js loaded");

//Wait for DOM to load
document.addEventListener("DOMContentLoaded", initApp);

//Import post function
import { post } from "../fetchUtil.js"
import { get } from "../fetchUtil.js"

function initApp(){
    
    initEmployeeLanding();

}

async function getCurrentUser() {
    const response = await get("http://localhost:8080/api/employees/currentUser");
    return response;
}

async function initEmployeeLanding() {
    const welcomeMessage = document.getElementById("welcomeMessage");
    let user = await getCurrentUser();
    console.log(user);
    welcomeMessage.textContent = "Welcome, " + user.name + " " + user.surname; 
}
    
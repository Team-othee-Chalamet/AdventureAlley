console.log("login.js loaded");

// Wait for DOM to load
document.addEventListener("DOMContentLoaded", initApp);

// Import post function
import { post } from "../fetchUtil.js"

function initApp(){
    
    initLogin();

}

// Add event listener to the form
function initLogin() {
    const form = document.getElementById("loginForm")
    form.addEventListener("submit", handleLogin);
}

// Handle form submission
async function handleLogin(event) {
    event.preventDefault();
    const body = {
        staffId: event.target.staffId.value,
        password: event.target.password.value
    };
    try {
        const response = await post("http://localhost:8080/api/auth/login", body);
        console.log("Login successful:", response);
        // Store the token in localStorage
        localStorage.setItem("token", response.token);
        // Redirect to another page (e.g., dashboard.html)
        window.location.href = "employeeLanding.html";
    } catch (error) {
        console.error("Login failed:", error.message);
    }
}
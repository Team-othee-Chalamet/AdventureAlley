console.log("login.js loaded");

document.addEventListener("DOMContentLoaded", initApp);

import { post } from "../fetchUtil.js"

function initApp(){
    
    initLogin();

}

function initLogin() {
    const form = document.getElementById("loginForm")
    form.addEventListener("submit", handleLogin);
}

async function handleLogin(event) {
    event.preventDefault();
    const body = {
        staffId: event.target.staffId.value,
        password: event.target.password.value
    };
    try {
        const response = await post("http://localhost:8080/api/auth/login", body);
        console.log("Login successful:", response);
    } catch (error) {
        console.error("Login failed:", error.message);
    }
}
console.log("employeeLanding.js loaded");

document.addEventListener("DOMContentLoaded", initApp);

//Import get function
import { post } from "../fetchUtil.js"
import { get } from "../fetchUtil.js"

function initApp(){
    
    initEmployeeLanding();

}

// Gets current user 
async function getCurrentUser() {
    const response = await get("http://localhost:8080/api/employees/currentUser");
    return response;
}



async function initEmployeeLanding() {
    // If there is no user, redirect to login page
    try {
    const user = await getCurrentUser();
    console.log(user);

    document.getElementById("employeeName").textContent = "Navn: " + user.name + " " + user.surname;
    document.getElementById("employeeUsername").textContent = "Brugernavn: " + user.staffId;
    } catch (error) {
        console.error("Error fetching user data:", error);
        alert("Fejl ved hentning af brugerdata. Prøv at logge ind igen.");
        window.location.href = "login.html";
    }

    

    // Event listener for bookings button
    document.getElementById("manageBookings").addEventListener("click", handleManageBookings); 
}

async function handleManageBookings(clickevent) {
    const table = document.getElementById("bookingsTable");
    table.innerHTML = ""; // Clear existing table content
    const bookings = await get("http://localhost:8080/api/bookings");
    console.log(bookings);
    displayBookings(bookings);
}

function displayBookings(bookings) {
    const table = document.getElementById("bookingsTable");
  
    const header = document.createElement("thead");
    const thName = document.createElement("th");
    thName.textContent = "Navn";
    header.appendChild(thName);

    const thEmail = document.createElement("th");
    thEmail.textContent = "Email";
    header.appendChild(thEmail);

    const thPhoneNr = document.createElement("th");
    thPhoneNr.textContent = "Telefonnummer";
    header.appendChild(thPhoneNr);

    const thSessions = document.createElement("th");
    thSessions.textContent = "Sessions";
    header.appendChild(thSessions);

    const thAddons = document.createElement("th");
    thAddons.textContent = "Addons";
    header.appendChild(thAddons); 

    table.appendChild(header);

    bookings.forEach(booking => {
        displayBooking(booking);
    });
}

function displayBooking(booking) {
    {
        const table = document.getElementById("bookingsTable");

        const row = document.createElement("tr");

        const name = document.createElement("td");
        name.textContent = booking.bookingName;
        row.appendChild(name);

        const email = document.createElement("td");
        email.textContent = booking.bookingEmail;
        row.appendChild(email);

        const phoneNr = document.createElement("td");
        phoneNr.textContent = booking.bookingPhoneNr;
        row.appendChild(phoneNr);

        const sessions = document.createElement("td");
        sessions.textContent = booking.sessionDtos.length;
        row.appendChild(sessions);

        // Addons are not required, so can be null
        const addons = document.createElement("td");
        if (!booking.addons){
            booking.addons = [];
        }
        addons.textContent = booking.addons.length;
        row.appendChild(addons);

        table.appendChild(row);
    };
}
    
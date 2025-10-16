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
    await displayEmployee();

    // Event listener for bookings button
    document.getElementById("manageBookings").addEventListener("click", handleManageBookings); 
}

async function displayEmployee() {
    // If there is no user, redirect to login page
    try {
    const user = await getCurrentUser();
    console.log(user);

    const emplHeader = document.createElement("h1");
    emplHeader.textContent = "Velkommen!";
    const emplName = document.createElement("p");
    emplName.textContent = "Fornavn: " + user.name;
    const emplSurname = document.createElement("p");
    emplSurname.textContent = "Efternavn: " + user.surname;
    const emplUsername = document.createElement("p");
    emplUsername.textContent = "Brugernavn: " + user.staffId;

    const displayBox = document.getElementById("displayBox");
    displayBox.innerHTML = "";

    displayBox.appendChild(emplHeader);
    displayBox.appendChild(emplName);
    displayBox.appendChild(emplSurname);
    displayBox.appendChild(emplUsername);
    

    } catch (error) {
        console.error("Error fetching user data:", error);
        alert("Fejl ved hentning af brugerdata. Prøv at logge ind igen.");
        window.location.href = "login.html";
    }
}

async function handleManageBookings(clickevent) {
    const table = document.getElementById("bookingsTable");
    const displayBox = document.getElementById("displayBox");

    // If table is empty, fill it, else empty it
    if (table.innerHTML === "") {
    const bookings = await get("http://localhost:8080/api/bookings");
    displayBookings(bookings);
} else {
    table.innerHTML = "";
    displayEmployee();
}
}

async function handleTableClick(event) {
    // Get clicked cell and derive the row
    const clickedElement = event.target;
    console.log(clickedElement);
    console.log(clickedElement.closest("tr"));
    const clickedRow = clickedElement.closest("tr");

    // Empty display box and add a form
    const displayBox = document.getElementById("displayBox");
    displayBox.innerHTML = "";
    const form = document.createElement("form");
    form.id = "editBookingForm";

    // Add date to form
    const formDate = document.createElement("label");
    formDate.textContent = "Dato: " + clickedRow.children[1].textContent;
    form.appendChild(formDate);

    // Create a div formRow to have input and label on same row
    const nameRow = document.createElement("div");
    nameRow.className = "formRow";

    const nameLabel = document.createElement("label");
    nameLabel.textContent = "Navn:";
    nameRow.appendChild(nameLabel);

    const inputName = document.createElement("input");
    inputName.type = "text";
    inputName.name = "bookingName";
    inputName.value = clickedRow.children[2].textContent;
    nameRow.appendChild(inputName);

    form.appendChild(nameRow);

    // formRow for email
    const emailRow = document.createElement("div");
    emailRow.className = "formRow";

    const emailLabel = document.createElement("label");
    emailLabel.textContent = "Email:";
    emailRow.appendChild(emailLabel);

    const inputEmail = document.createElement("input");
    //Should be email type, but due to test data it is text
    inputEmail.type = "text";
    inputEmail.name = "bookingEmail";
    inputEmail.value = clickedRow.children[3].textContent;
    emailRow.appendChild(inputEmail);

    form.appendChild(emailRow);

    // Phone number row
    const phoneNrRow = document.createElement("div");
    phoneNrRow.className = "formRow";  
    const phoneNrLabel = document.createElement("label");
    phoneNrLabel.textContent = "Telefonnummer:";
    phoneNrRow.appendChild(phoneNrLabel);

    const inputPhoneNr = document.createElement("input");
    inputPhoneNr.type = "text";
    inputPhoneNr.name = "bookingPhoneNr";
    inputPhoneNr.value = clickedRow.children[4].textContent;
    phoneNrRow.appendChild(inputPhoneNr);
    
    form.appendChild(phoneNrRow);

    const submitButton = document.createElement("button");
    submitButton.id = "submitEditBooking";
    submitButton.type = "submit";
    submitButton.textContent = "Opdater booking";
    submitButton.addEventListener("click", handleSubmition);
    form.appendChild(submitButton);

    displayBox.appendChild(form);
}

function displayBookings(bookings) {
    const table = document.getElementById("bookingsTable");
    table.addEventListener("click", handleTableClick);

    const header = document.createElement("thead");
    
    const thId = document.createElement("th");
    thId.textContent = "ID";
    header.appendChild(thId);

    const thDate = document.createElement("th");
    thDate.textContent = "Dato";
    header.appendChild(thDate);

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
    thAddons.textContent = "Tilkøb";
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

        const id = document.createElement("td");
        id.textContent = booking.id;       
        row.appendChild(id);

        // InitData has bookings without sessions
        const date = document.createElement("td");
        if (!booking.sessionDtos[0]){
            date.textContent = "Ingen sessioner";
        } else {
        date.textContent = booking.sessionDtos[0].date;
        }
        row.appendChild(date);

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

function handleSubmition(event) {
    event.preventDefault();
    console.log("Submit clicked");
}
    
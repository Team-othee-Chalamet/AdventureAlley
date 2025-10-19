console.log("employeeLanding.js loaded");

document.addEventListener("DOMContentLoaded", initApp);

//Import get function
import { post } from "../fetchUtil.js"
import { get } from "../fetchUtil.js"
import { put } from "../fetchUtil.js"

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
    const clickedRow = clickedElement.closest("tr");

    // Empty display box and add a form
    const displayBox = document.getElementById("displayBox");
    displayBox.innerHTML = "";
    const form = document.createElement("form");
    form.id = "editBookingForm";

    // Add id to form
    {
    const idRow = document.createElement("div");
    idRow.className = "formRow";
    const formIdLabel = document.createElement("label");
    formIdLabel.textContent = "Booking ID:";
    idRow.appendChild(formIdLabel);
    const formId = document.createElement("p");
    formId.textContent = clickedRow.children[0].textContent;
    idRow.appendChild(formId);
    form.appendChild(idRow);

    const idForData = document.createElement("input");
    idForData.type = "hidden";
    idForData.name = "id";
    idForData.value = clickedRow.children[0].textContent;
    form.appendChild(idForData);
    }

    // Add date to form
    {
    const dateRow = document.createElement("div");
    dateRow.className = "formRow";
    const formDateLabel = document.createElement("label");
    formDateLabel.textContent = "Dato:";
    dateRow.appendChild(formDateLabel);
    const formDate = document.createElement("p");
    formDate.textContent = clickedRow.children[1].textContent;
    dateRow.appendChild(formDate);
    form.appendChild(dateRow);
    }

    // Add name to form
    {
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
    }

    // Add email to form
    {
    const emailRow = document.createElement("div");
    emailRow.className = "formRow";
    const emailLabel = document.createElement("label");
    emailLabel.textContent = "Email:";
    emailRow.appendChild(emailLabel);
    const inputEmail = document.createElement("input");
    inputEmail.type = "text";
    inputEmail.name = "bookingEmail";
    inputEmail.value = clickedRow.children[3].textContent;
    emailRow.appendChild(inputEmail);
    form.appendChild(emailRow);
}

    // Add phone number to form
    {
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

    displayBox.appendChild(form);
    }
    // Add sessions to form - realized late that they are not declared in scope so will import again though not effective
    let booking = await get("http://localhost:8080/api/bookings/"+clickedRow.children[0].textContent); 
    console.log(booking);
    const sessionsRow = document.createElement("div");
    sessionsRow.className = "formRow";
    const sessionsLabel = document.createElement("label");
    sessionsLabel.id = "sessionsLabel";
    sessionsLabel.textContent = "Sessioner:";
    displayBox.appendChild(sessionsLabel);
    
    // Container for sessions that can scroll
    const sessionContainer = document.createElement("div");
    sessionContainer.id = "sessionContainer";
    
    displayBox.appendChild(sessionContainer);

    // Add sessions to container
    {
    const sessions = booking.sessionDtos;
    sessions.sort((a, b) => a.startTime.localeCompare(b.startTime));
    
    let sessionIndex = 0;
    sessions.forEach(session => {
        const sessionRow = document.createElement("div");
        sessionRow.className = "formRow sessionRow";
        const sessionActivity = document.createElement("p");
        const sessionTime = document.createElement("p");

        const idForSession = document.createElement("input");
        idForSession.type = "hidden";
        idForSession.name = "id";
        idForSession.value = sessionIndex;
        sessionRow.appendChild(idForSession);

        sessionActivity.textContent = session.activityType;
        sessionTime.textContent = session.startTime + " - " + session.endTime;
        sessionRow.appendChild(sessionActivity);
        sessionRow.appendChild(sessionTime);

        const deleteButton = document.createElement("button");
        deleteButton.classList = "button deleteButton";
        deleteButton.type = "button";
        deleteButton.textContent = "Slet";
        sessionRow.appendChild(deleteButton);

        sessionContainer.appendChild(sessionRow);
        sessionIndex++;
    });
    sessionContainer.appendChild(sessionsRow);
    }

    // Click event for sessions button
    let sessionsToDelete = [];

    sessionContainer.addEventListener("click", (event) => {
        const clickedElement = event.target;
        console.log("Clicked element:", clickedElement);

        const sessionRow = clickedElement.closest(".sessionRow");
        if (!sessionRow) return;
        console.log("Session clicked:", sessionRow);

        const sessionRowIndex = sessionRow.querySelector('input[name="id"]').value;
        console.log("Session index:", sessionRowIndex);

        if (clickedElement.type === "button") {
            if (clickedElement.textContent === "Slet") {
            sessionRow.style.textDecoration = "line-through";
            clickedElement.textContent = "Fortryd";
            sessionsToDelete.push(sessionRowIndex);
            console.log("Sessions to delete:", sessionsToDelete);

        } else if (clickedElement.textContent === "Fortryd") {
            sessionRow.style.textDecoration = "none";
            clickedElement.textContent = "Slet";
            sessionsToDelete.splice(sessionsToDelete.indexOf(sessionRowIndex), 1);
            console.log("Sessions to delete:", sessionsToDelete);
        }}
    });

    // Add submit button
    const submitButton = document.createElement("button");
    submitButton.classList = "button";
    submitButton.id = "submitEditBooking";
    submitButton.type = "submit";
    submitButton.textContent = "Opdater booking";
    submitButton.addEventListener("click", async (event) => {
  event.preventDefault();
  console.log("Submit clicked");

  // Remove deleted sessions from booking
  sessionsToDelete.sort((a, b) => b - a); 
  sessionsToDelete.forEach(index => {
    booking.sessionDtos.splice(index, 1);
  });

  // Build body from the existing booking object + form fields
  const formData = new FormData(document.getElementById("editBookingForm"));
  const bookingId = formData.get("id");

  // Copy form fields into booking object
  booking.bookingName = formData.get("bookingName");
  booking.bookingEmail = formData.get("bookingEmail");
  booking.bookingPhoneNr = formData.get("bookingPhoneNr");

  console.log("Final PUT body:", booking);

  try {
    const resp = await put(
      `http://localhost:8080/api/bookings/${bookingId}`,
      booking
    );
    console.log("Booking updated successfully:", resp);
    displayBox.innerHTML = "";
    displayEmployee();
  } catch (error) {
    console.error("Error updating booking:", error);
    alert("Fejl ved opdatering af booking. Prøv igen.");
  }
});
    
    displayBox.appendChild(submitButton);
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
}

function handleSubmission(event) {
    event.preventDefault();
    console.log("Submit clicked");
    const formData = new FormData(document.getElementById("editBookingForm"));
    console.log(formData);
}
    
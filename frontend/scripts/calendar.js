console.log("Calendar.js loaded");

document.addEventListener("DOMContentLoaded", initApp);

function initApp(){
    
    playGround();


}

async function playGround(){

    const sessions = await fetchSessions();

    renderSessions(sessions);

}

async function fetchSessions(){
    const resp = await fetch("http://127.0.0.1:8080/api/sessions/2025-10-08/2025-10-08");
    if(!resp.ok){
        console.log("Error when fetching sessions from backend.")
    }

    return await resp.json();
}

function renderSessions(sessions){
    sessions.forEach(session => {
        renderSession(session)
    });
}

function renderSession(session){
    const timeSlotElement = document.createElement("div");
    
    const sessionElement = document.createElement("div");
    timeSlotElement.appendChild(sessionElement);

    timeSlotElement.classList.add("timeSlot")

    let column;

    switch(session.activityType){
        case "Minigolf":
            sessionElement.classList.add("miniGolf", "session");
            sessionElement.innerHTML = "MiniGolf";
            timeSlotElement.style.setProperty("--duration", "2");
            column = "#miniGolfColumn";
            break;
        case "Gokart":
            sessionElement.classList.add("goKart", "session");
            sessionElement.innerHTML = "Gokart";
            timeSlotElement.style.setProperty("--duration", "4");
            column = "#goKartColumn";
            break;
        case "Sumo":
            sessionElement.classList.add("sumo", "session");
            sessionElement.innerHTML = "Sumo";
            timeSlotElement.style.setProperty("--duration", "1");
            column = "#sumoColumn";
            break;
        case "Paintball":
            sessionElement.classList.add("paintBall", "session");
            sessionElement.innerHTML = "Paintball";
            timeSlotElement.style.setProperty("--duration", "6");
            column = "#paintBallColumn";
            break;
        default:
            break;
    }

console.log(session.startTime)

    switch(session.startTime){
        case "09:00:00":
            timeSlotElement.style.setProperty("--start", "0");
            break;
        case "09:30:00":
            timeSlotElement.style.setProperty("--start", "1");
            break;
        case "10:00:00":
            timeSlotElement.style.setProperty("--start", "2");
            break;
        case "10:30:00":
            timeSlotElement.style.setProperty("--start", "3");
            break;
        case "11:00:00":
            timeSlotElement.style.setProperty("--start", "4");
            break;
        case "11:30:00":
            timeSlotElement.style.setProperty("--start", "5");
            break;
        case "12:00:00":
            timeSlotElement.style.setProperty("--start", "6");
            break;
        case "12:30:00":
            timeSlotElement.style.setProperty("--start", "7");
            break;
        case "13:00:00":
            timeSlotElement.style.setProperty("--start", "8");
            break;
        case "13:30:00":
            timeSlotElement.style.setProperty("--start", "9");
            break;
        case "14:00:00":
            timeSlotElement.style.setProperty("--start", "10");
            break;
        case "14:30:00":
            timeSlotElement.style.setProperty("--start", "11");
            break;
        case "15:00:00":
            timeSlotElement.style.setProperty("--start", "12");
            break;
        case "15:30:00":
            timeSlotElement.style.setProperty("--start", "13");
            break;
        case "16:00:00":
            timeSlotElement.style.setProperty("--start", "14");
            break;
        case "16:30:00":
            timeSlotElement.style.setProperty("--start", "15");
            break;
    }

    if(session.bookingStatus){
        sessionElement.classList.add("booked");
    }


    document.querySelector(column).appendChild(timeSlotElement);

}
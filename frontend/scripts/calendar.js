console.log("Calendar.js loaded");

document.addEventListener("DOMContentLoaded", initApp);

function initApp() {
	const date = new Date();
	date.setDate(date.getDate() + 1);

	const dateStr = date.toISOString().split("T")[0];

	console.log(dateStr);

	reloadAndRenderSessions(dateStr);
	addEventListeners();
}

function setMessage(message) {
	document.querySelector("#message").innerHTML = message;
}

function addEventListeners() {
	document
		.querySelector("#sessionTable")
		.addEventListener("click", () => handleSessionTableClick(event));
	document
		.querySelector("#dateSearchButton")
		.addEventListener("click", () => handleDateSearchClick(event));
	document
		.querySelector("#addSessionButton")
		.addEventListener("click", () => handleAddSessionClick(event));
	document
		.querySelector("#chosenSessionsField")
		.addEventListener("click", () => handleSessionsFieldClick(event));
}

function handleSessionsFieldClick(event) {
	console.log("Clicked sessionsField");

	const target = event.target;
	console.log(target);

	if (target.hasAttribute("removeButton")) {
		console.log("You clicked a remove button");

		const parent = target.parentElement;

		const reservedSessions = document.querySelectorAll(".reserved");
		for (reservedSession of reservedSessions) {
            console.log(reservedSession.getAttribute("startTime"));
            console.log(parent.getAttribute("startTime"));
			if(reservedSession.getAttribute("data-startTime") == parent.getAttribute("startTime") && reservedSession.getAttribute("data-activityType") == parent.getAttribute("activityType") ){
                reservedSession.classList.remove("reserved");
            }
		}

		parent.remove();
	}
}

function handleAddSessionClick(event) {
	console.log("Add session");

	const sessionDiv = document.querySelector(".chosen");
	const activityType = sessionDiv.getAttribute("data-activityType");
	const sessionDate = sessionDiv.getAttribute("data-bookingDate");
	const sessionStartTime = sessionDiv.getAttribute("data-startTime");
	const sessionEndTime = sessionDiv.getAttribute("data-endTime");

	const session = {
		activityType: activityType,
		date: sessionDate,
		startTime: sessionStartTime,
		endTime: sessionEndTime,
	};

	console.log(session);

	addSessionToBooking(session);
}

function addSessionToBooking(session) {
	console.log("Adding session to booking field");

	const bookingSessionDiv = document.createElement("div");
	bookingSessionDiv.classList.add("booking");
	bookingSessionDiv.classList.add(session.activityType);

	bookingSessionDiv.setAttribute("activityType", session.activityType);
	bookingSessionDiv.setAttribute("bookingDate", session.date);
	bookingSessionDiv.setAttribute("startTime", session.startTime);
	bookingSessionDiv.setAttribute("endTime", session.endTime);

	const bookingSessionDivTitle = document.createElement("div");
	bookingSessionDivTitle.classList.add("bookingActivity");
	bookingSessionDivTitle.innerHTML = session.activityType;
	const bookingSessionDivDate = document.createElement("div");
	bookingSessionDivDate.classList.add("bookingDate");
	bookingSessionDivDate.innerHTML = session.date;
	const bookingSessionDivStartTime = document.createElement("div");
	bookingSessionDivStartTime.classList.add("bookingStartTime");
	bookingSessionDivStartTime.innerHTML = session.startTime;

	const removeButton = document.createElement("div");
	removeButton.setAttribute("removeButton", true);
	removeButton.classList.add("removeButton");
	removeButton.innerHTML = "r";

	bookingSessionDiv.appendChild(bookingSessionDivTitle);
	bookingSessionDiv.appendChild(bookingSessionDivDate);
	bookingSessionDiv.appendChild(bookingSessionDivStartTime);
	bookingSessionDiv.appendChild(removeButton);

	console.log(bookingSessionDiv);

	const markedSession = document.querySelector(".chosen");
	const sessions = document.querySelectorAll(".session");
	for (othersession of sessions) {
		othersession.classList.remove("chosen");
	}
	markedSession.classList.toggle("reserved");

	document.querySelector("#chosenSessionsField").appendChild(bookingSessionDiv);
}

async function handleDateSearchClick(event) {
	event.preventDefault();
	console.log("Søg clicked");

	const date = document.querySelector("#dateInput").value;
	removeSessions();

	reloadAndRenderSessions(date);
}

function handleSessionTableClick(event) {
	const session = event.target.closest(".session");

	if (session) {
		if (session.hasAttribute("sessionblock")) {
			//console.log("This is a session")
			if (session.hasAttribute("data-isBooked") || session.classList.contains("chosen") || session.classList.contains("reserved")) {
				console.log("this session is booked");
			} else {
				console.log("This session is unbooked.");
				const sessions = document.querySelectorAll(".session");
				for (othersession of sessions) {
					othersession.classList.remove("chosen");
				}
				session.classList.toggle("chosen");
			}
		}
	} else {
		const markedSession = document.querySelector(".chosen");
		const sessions = document.querySelectorAll(".session");
		for (othersession of sessions) {
			othersession.classList.remove("chosen");
		}
	}
}

async function reloadAndRenderSessions(date) {
	const sessions = await fetchSessions(date);

	console.log(date);

	renderSessions(sessions);
}

async function fetchSessions(date) {
	const resp = await fetch(
		"http://127.0.0.1:8080/api/sessions/" + date + "/" + date
	);
	if (!resp.ok) {
		console.log("Error when fetching sessions from backend.");
		setMessage("Der var en fejl i indlæsningen af kalenderen.");
	} else {
		setMessage("Viser sessions for: " + date);
	}

	return await resp.json();
}

function renderSessions(sessions) {
	sessions.forEach((session) => {
		renderSession(session);
	});
}

function renderSession(session) {
	const timeSlotElement = document.createElement("div");

	const sessionElement = document.createElement("div");
	sessionElement.setAttribute("sessionBlock", true);

	timeSlotElement.appendChild(sessionElement);

	timeSlotElement.classList.add("timeSlot");

	let column;

	switch (session.activityType) {
		case "Minigolf":
			sessionElement.classList.add("Minigolf", "session");
			sessionElement.innerHTML = "MiniGolf";
			timeSlotElement.style.setProperty("--duration", "2");
			sessionElement.setAttribute("data-activityType", "Minigolf");
			column = "#miniGolfColumn";
			break;
		case "Gokart":
			sessionElement.classList.add("Gokart", "session");
			sessionElement.innerHTML = "Gokart";
			timeSlotElement.style.setProperty("--duration", "4");
			sessionElement.setAttribute("data-activityType", "Gokart");
			column = "#goKartColumn";
			break;
		case "Sumo":
			sessionElement.classList.add("Sumo", "session");
			sessionElement.innerHTML = "Sumo";
			timeSlotElement.style.setProperty("--duration", "1");
			sessionElement.setAttribute("data-activityType", "Sumo");
			column = "#sumoColumn";
			break;
		case "Paintball":
			sessionElement.classList.add("Paintball", "session");
			sessionElement.innerHTML = "Paintball";
			timeSlotElement.style.setProperty("--duration", "6");
			sessionElement.setAttribute("data-activityType", "Paintball");
			column = "#paintBallColumn";
			break;
		default:
			break;
	}

	switch (session.startTime) {
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

	sessionElement.setAttribute("data-bookingDate", session.date);

	sessionElement.setAttribute("data-startTime", session.startTime);

	sessionElement.setAttribute("data-endTime", session.endTime);

	if (session.bookingStatus) {
		sessionElement.classList.add("booked");
		sessionElement.setAttribute("data-isBooked", true);
	}

	document.querySelector(column).appendChild(timeSlotElement);
}

function removeSessions() {
	const miniGolfSessionsRendered = document.querySelectorAll(".timeSlot");
	console.log(miniGolfSessionsRendered);
	for (session of miniGolfSessionsRendered) {
		session.remove();
	}
}

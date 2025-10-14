// === scheduleApp.js ===

// API base
const BASE = "http://localhost:8080/api/shifts";

// --- Helpers: uge-beregning + range ---
function startOfNextWeek(base = new Date()) {
  const d = new Date(base);
  // Gør mandag=0 (JS: søn=0)
  const day = (d.getDay() + 6) % 7;
  const nextMon = new Date(d);
  nextMon.setDate(d.getDate() - day + 7); // hop til næste mandag
  nextMon.setHours(0, 0, 0, 0);
  return nextMon;
}

function endOfWeek(startMon) {
  const end = new Date(startMon);
  end.setDate(startMon.getDate() + 7); // eksklusiv næste mandag
  end.setHours(0, 0, 0, 0);
  return end;
}

function inRange(dateStr, start, end) {
  // backend leverer ISO yyyy-mm-dd; fastsæt T00:00 for stabil parse
  const d = new Date(dateStr + "T00:00");
  return d >= start && d < end;
}

// Mandag = 0 ... Søndag = 6
function dowIndex(isoDate) {
  const d = new Date(isoDate + "T00:00");
  return (d.getDay() + 6) % 7;
}

// --- DOM render: header med datoer ---
function renderWeekHeader(startMon) {
  const ths = document.querySelectorAll("#shiftTable thead th");
  for (let i = 0; i < 7; i++) {
    const d = new Date(startMon);
    d.setDate(startMon.getDate() + i);
    const label = ths[i].dataset.label || ths[i].textContent;
    ths[i].textContent = `${label} ${d.toLocaleDateString("da-DK", {
      day: "2-digit",
      month: "2-digit",
    })}`;
  }
}

// --- DOM render: tom uge (nulstiller tbody) ---
function renderEmptyWeek() {
  const tbody = document.querySelector("#rows");
  tbody.innerHTML = ""; // vi opretter rækker efter behov
}

// --- DOM render: én medarbejder pr. celle, stakket lodret pr. dag ---
function renderShifts(shifts) {
  const tbody = document.querySelector("#rows");
  // spor næste ledige række pr. kolonne (0..6)
  const nextRow = Array(7).fill(0);

  for (const s of shifts) {
    if (!s || !s.date) continue;
    const col = dowIndex(s.date);
    const rowIndex = nextRow[col];

    const tr = ensureRow(tbody, rowIndex);
    const name =
      (s.employee && (s.employee.name || s.employee.staffId)) || "Ukendt";

    tr.children[col].textContent = name;
    nextRow[col]++;
  }
}

// sikrer at tbody har en række 'index' med 7 tomme celler
function ensureRow(tbody, index) {
  while (tbody.rows.length <= index) {
    const tr = document.createElement("tr");
    for (let i = 0; i < 7; i++) {
      const td = document.createElement("td");
      td.innerHTML = "&nbsp;"; // tom celle
      tr.appendChild(td);
    }
    tbody.appendChild(tr);
  }
  return tbody.rows[index];
}

// --- Data fetch ---
async function fetchShifts() {
  const resp = await fetch(BASE);
  if (!resp.ok) {
    throw new Error(`FEJLHTTP : ${resp.status}`);
  }
  const data = await resp.json();
  // console.log("ALL shifts:", data);
  return data;
}

// --- Init flow: hent -> filtrér næste uge -> render ---
async function init() {
  try {
    const all = await fetchShifts();

    const start = startOfNextWeek();
    const end = endOfWeek(start);
    const nextWeekShifts = all.filter((s) => s?.date && inRange(s.date, start, end));

    renderWeekHeader(start);
    renderEmptyWeek();
    renderShifts(nextWeekShifts);
  } catch (err) {
    console.error("Init error:", err);
  }
}

// --- Form submit: POST urlencoded body ---
function wireForm() {
  const form = document.getElementById("createShiftForm");
  if (!form) return;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const fd = new FormData(form);
    const qs = new URLSearchParams(fd);

    // Debug: se de værdier du sender
    console.log("POST body:", qs.toString());

    const res = await fetch("http://localhost:8080/api/shifts", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: qs.toString(),
    });

    if (res.ok) {
      // Efter oprettelse: re-render næste uge uden fuld page reload
      await init();
      form.reset();
    } else {
      alert("Create failed: " + res.status);
    }
  });
}

// Kør først når DOM er klar (du bruger defer, men dette er sikkert og klart)
document.addEventListener("DOMContentLoaded", () => {
  wireForm();
  init();
});

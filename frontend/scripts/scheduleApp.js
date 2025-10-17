// === scheduleApp.js ===

// API base
const BASE = "http://localhost:8080/api/shifts";
const EMP_BASE = "http://localhost:8080/api/employees";

function startOfNextWeek(iDag = new Date) { //metoden returnerer et Date objekt til næste mandag fra i Dag
  const d = new Date(iDag);
  const day = (d.getDay() + 6) % 7; // i JS er 0 søndag vi vil have 0 bliver mandag, derfor skrives denne logik som skubber en hver ugedag et tal frem
  const nextMon = new Date(d); //
  nextMon.setDate(d.getDate() - day + 7); //Vi skal sætte datoen til næste mandag. getDate returnere dag på måneden som vi minusser med vores modificerede dag så vi rammer en mandag så plusser vi med 7 for at ramme næste mandag
  nextMon.setHours(0, 0, 0, 0); //Her sætter vi bare timerne på Date objektet
  return nextMon;
}

function endOfWeek(startMon) { // returnere et Date objekt til næste mandag efter næste mandag
  const end = new Date(startMon);
  end.setDate(startMon.getDate() + 7);      
  end.setHours(0, 0, 0, 0);
  return end;
}

function inRange(dateStr, start, end) {
  const d = new Date(dateStr + "T00:00"); //T00:00 parse det til dansk til i stedet for amerikansk
  return d >= start && d < end;
}

// Mandag = 0 ... Søndag = 6
function dowIndex(isoDate) { //
  const d = new Date(isoDate + "T00:00");
  return (d.getDay() + 6) % 7;
}

// --- DOM render: header med datoer ---
function renderWeekHeader(startMon) { // render headeren med datoer
  const ths = document.querySelectorAll("#shiftTable thead th"); // ths = Table headers
  for (let i = 0; i < 7; i++) { // looper 7 gange
    const d = new Date(startMon);
    d.setDate(startMon.getDate() + i);
    const label = ths[i].dataset.label || ths[i].textContent;
    ths[i].textContent = `${label} ${d.toLocaleDateString("da-DK", {
      day: "2-digit",
      month: "2-digit",
    })}`;
  }

  // vis uge-range i #weekRange
  const end = endOfWeek(startMon);
  const rangeEl = document.getElementById("weekRange");
  const dateFormat = (dateInput) =>
      dateInput.toLocaleDateString("da-DK", { day: "2-digit", month: "2-digit" });
  rangeEl.textContent = `· ${dateFormat(startMon)}–${dateFormat(new Date(end - 1))}`;

}

// --- DOM render: tom uge ---
function renderEmptyWeek() {
  const tbody = document.querySelector("#rows");
  if (tbody) tbody.innerHTML = "";
}

// --- DOM render: én medarbejder pr. celle ---
function renderShifts(shifts) {
  const tbody = document.querySelector("#rows");

  const nextRow = Array(7).fill(0); // næste ledige række pr. dag

  for (const s of shifts) {
    const col = dowIndex(s.date); //Finder ugedag konverere til dansk og sætter den som col
    const rowIndex = nextRow[col]; //sætter rowIndex til specifik plads i nextRow Arrayet udfra col

    const tr = ensureRow(tbody, rowIndex); 
    const name = s.employee.name;  // finder det navnet vi skal putte i vores celle
    const cell = tr.children[col];
    cell.textContent = name;
    
    tr.children[col].textContent = name;
    const btn = document.createElement("button");
      btn.textContent = "🗑";
      btn.className = "delete-btn";
      btn.onclick = (e) => { e.stopPropagation(); deleteShift(s.id); };
      cell.appendChild(document.createTextNode(" "));
      cell.appendChild(btn);
    nextRow[col]++;
  }
}


// Sikrer at tbody har en række 'index' med 7 tomme celler
function ensureRow(tbody, index) {
  while (tbody.rows.length <= index) {
    const tr = document.createElement("tr");
    for (let i = 0; i < 7; i++) {
      const td = document.createElement("td");
      td.innerHTML = "&nbsp;";
      tr.appendChild(td);
    }
    tbody.appendChild(tr);
  }
  return tbody.rows[index];
}

// --- Data fetch ---
async function fetchShifts() {
  const resp = await fetch(BASE);
  if (!resp.ok) throw new Error(`FEJLHTTP : ${resp.status}`);
  return resp.json();
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

// --- Dropdown: hent employees og udfyld select ---
async function populateEmployeeSelect() {
  const sel = document.getElementById("employeeSelect");
  if (!sel) return;

  // Hent token fra localStorage (samme key som jeres login gemmer)
  const token = localStorage.getItem("token");

  // Hvis ingen token: vis en venlig besked i dropdown og stop
  if (!token) {
    sel.innerHTML = '<option value="" disabled selected>Log ind for at se medarbejdere</option>';
    console.warn("No token in localStorage. Cannot call /api/employees.");
    return;
  }

  try {
    const res = await fetch(EMP_BASE, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) {
      const msg = await res.text();
      console.error("Employees failed:", res.status, msg);
      sel.innerHTML = '<option value="" disabled selected>Kunne ikke hente medarbejdere</option>';
      return;
    }

    const employees = await res.json();
    sel.innerHTML = '<option value="" disabled selected>Vælg medarbejder…</option>';

    for (const e of employees) {
      const id = e.id ?? e.employeeId ?? e.staffId;
      if (id == null) continue;
      const name = e.name ?? ([e.firstName, e.lastName].filter(Boolean).join(" ") || `Employee #${id}`);
      const opt = document.createElement("option");
      opt.value = id;
      opt.textContent = name;
      sel.appendChild(opt);
    }
  } catch (err) {
    console.error(err);
    sel.innerHTML = '<option value="" disabled selected>Fejl ved hentning</option>';
  }
}


function wireForm() {
  const form = document.getElementById("createShiftForm");
  if (!form) return;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    // Form must have inputs named: date, startTime, endTime, employeeId
    const body = new URLSearchParams(new FormData(form));
    const token = localStorage.getItem("token");

    try {
      const res = await fetch("http://localhost:8080/api/shifts", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          ...(token ? { Authorization: `Bearer ${token}` } : {})
        },
        body
      });

      if (!res.ok) {
        const msg = await res.text().catch(() => "");
        alert(`Create failed ${res.status}: ${msg || "(no message)"}`);
        return;
      }

      form.reset();   // clear form
      init();         // refresh table
    } catch (err) {
      alert("Network error: " + err.message);
    }
  });
}

function renderShifts(shifts) {
  const tbody = document.querySelector("#rows");
  const nextRow = Array(7).fill(0);

  for (const s of shifts) {
    const col = dowIndex(s.date);
    const rowIndex = nextRow[col];
    const tr = ensureRow(tbody, rowIndex);

    const name = s.employee?.name ?? s.employeeName ?? "Ukendt"; // alt efter din DTO
    const cell = tr.children[col];
    cell.textContent = name;

    if (s.id != null) {
      const btn = document.createElement("button");
      btn.textContent = "🗑";
      btn.className = "delete-btn";
      btn.onclick = (e) => { e.stopPropagation(); deleteShift(s.id); };
      cell.appendChild(document.createTextNode(" "));
      cell.appendChild(btn);
    }

    nextRow[col]++;
  }
}

async function deleteShift(id) {
  const token = localStorage.getItem("token");
  if (!confirm("Er du sikker på, at du vil slette denne vagt?")) return;

  const res = await fetch(`${BASE}/${id}`, {
    method: "DELETE",
    headers: { ...(token ? { Authorization: `Bearer ${token}` } : {}) }
  });

  if (!res.ok) {
    const msg = await res.text().catch(() => "");
    alert(`Sletning mislykkedes (${res.status}): ${msg || "(no message)"}`);
    return;
  }
  await init();
}





// --- Kald direkte (script er defer i HTML) ---
wireForm();
populateEmployeeSelect();
init();

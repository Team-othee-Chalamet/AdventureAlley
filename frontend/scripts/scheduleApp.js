// === scheduleApp.js ===

// API base
const BASE = "http://localhost:8080/api/shifts";
const EMP_BASE = "http://localhost:8080/api/employees";

// --- Helpers: uge-beregning + range ---
function startOfNextWeek(base = new Date()) {
  const d = new Date(base);
  const day = (d.getDay() + 6) % 7;          // mandag=0
  const nextMon = new Date(d);
  nextMon.setDate(d.getDate() - day + 7);    // hop til næste mandag
  nextMon.setHours(0, 0, 0, 0);
  return nextMon;
}

function endOfWeek(startMon) {
  const end = new Date(startMon);
  end.setDate(startMon.getDate() + 7);       // eksklusiv næste mandag
  end.setHours(0, 0, 0, 0);
  return end;
}

function inRange(dateStr, start, end) {
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

  // (valgfrit) vis uge-range i #weekRange hvis det element findes
  const end = endOfWeek(startMon);
  const rangeEl = document.getElementById("weekRange");
  if (rangeEl) {
    const fmt = (dt) =>
      dt.toLocaleDateString("da-DK", { day: "2-digit", month: "2-digit" });
    rangeEl.textContent = `· ${fmt(startMon)}–${fmt(new Date(end - 1))}`;
  }
}

// --- DOM render: tom uge ---
function renderEmptyWeek() {
  const tbody = document.querySelector("#rows");
  if (tbody) tbody.innerHTML = "";
}

// --- DOM render: én medarbejder pr. celle ---
function renderShifts(shifts) {
  const tbody = document.querySelector("#rows");
  if (!tbody) return;

  const nextRow = Array(7).fill(0); // næste ledige række pr. dag

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


// --- Form submit: POST urlencoded body ---
function wireForm() {
  const form = document.getElementById("createShiftForm");
  if (!form) return;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const fd = new FormData(form);
    const qs = new URLSearchParams(fd);

    const res = await fetch("http://localhost:8080/api/shifts", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: qs.toString(),
    });

    if (res.ok) {
      await init();   // re-render næste uge
      form.reset();
    } else {
      alert("Create failed: " + res.status);
    }
  });
}

// --- Kald direkte (script er defer i HTML) ---
wireForm();
populateEmployeeSelect();
init();

const BASE = 'http://localhost:8080/api/shifts';
let shifts = [];

//init();

function init() {
  console.log('init shifts');
  fetchShifts().then(renderShifts).catch(err => console.error(err));
}

async function fetchShifts() {

    const resp = await fetch(BASE);

    if (!resp.ok) {
        throw new Error(`FEJLHTTP : ${resp.status}`);
    }   

    shifts = await resp.json();
    console.log(shifts);
    return shifts;
}

function renderShifts() {
    
}


fetchShifts();
renderEmptyWeek();

function renderEmptyWeek(){
const tbody = document.querySelector('#rows'); // Henter tbody fra html
tbody.innerHTML = ''; // sletter alt html i <tbody>
for (let i  = 0; i < 8 ; i++){ //Ydre for loop for at skabe 8 rækker
    const tr = document.createElement('tr');
for (let i = 0; i < 7; i++){ // indre forloop skaber 8 kolonner per række
    const td = document.createElement('td'); // td kan nu lave en "table data cell" altså en enkelt celle
    td.textContent = '\u00A0'; // cellen er tom
    tr.appendChild(td); // tilføjer en enkelt celle<td>, til table row<tr>
}
 
 tbody.appendChild(tr); // tilføjer <tr> i <tbody>
 }
}









import {  getProducts } from "./productApi.js";

window.addEventListener("DOMContentLoaded", initApp);

function initApp(){
    reloadAndRender();
}

async function reloadAndRender(){
    const products = await getProducts();
    renderTable(products);
}

function renderTable(products){
    const tableBody = document.querySelector("#product-table-body");
    tableBody.innerHTML = "";
    products.forEach(renderRow);
}

function renderRow(product) {
    const tableBody = document.querySelector("#product-table-body");
    const html = `
    <tr data-id="${product.id}">
        <td>${product.name}</td>
        <td>${product.amount}</td>
        <td>${product.price}kr.</td>
    </tr>
    `;
    tableBody.insertAdjacentHTML("beforeend", html);
}

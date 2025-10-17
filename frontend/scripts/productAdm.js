import { createProduct, updateProduct, getProducts, deleteProduct } from "./productApi.js";

window.addEventListener("DOMContentLoaded", initApp);

function initApp(){
    reloadAndRender();
    setupEventListeners();
}

function setupEventListeners() {
    const tableBody = document.querySelector("#product-table-body");
    tableBody.addEventListener("click", handleTableClick);
    const openModal = document.querySelector("#show-modal");
    openModal.addEventListener("click", showModal);
    const form = document.querySelector("#product-form");
    form.addEventListener("submit", handleFormSubmit);
    const closeModal = document.querySelector("#close-modal");
    closeModal.addEventListener("click", hideModal);
}

function showModal() {
    const modal = document.querySelector("#modal");
    modal.classList.remove("hidden");
}

function hideModal() {
    const modal = document.querySelector("#modal");
    modal.classList.add("hidden");

    const form = document.querySelector("#product-form");
    form.reset();
}

async function handleTableClick(event){
    const target = event.target;

    if (target.classList.contains("edit-button")){
        const row = target.closest("tr");
        const productId = row.getAttribute("data-id");

        const rawPrice = row.children[3].textContent;
        const cleanPrice = rawPrice.replace("kr.", "").trim();
        
        const productToUpdate = {
            id: productId,
            name: row.children[1].textContent,
            amount: row.children[2].textContent,
            price: cleanPrice
        };
        fillProductForm(productToUpdate);
        showModal();
    } else if (target.classList.contains("delete-button")) {
        const row = target.closest("tr");
        const productId = row.getAttribute("data-id");

        await deleteProduct(productId);
    }
    await reloadAndRender();
}

async function handleFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    const product = {
        name: formData.get("name"),
        amount: formData.get("amount"),
        price: formData.get("price")
    };

    if (formData.get("id")) {
        await updateProduct(formData.get("id"), product);
    } else {
        await createProduct(product);
    }
    form.reset();
    hideModal();
    await reloadAndRender();
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
        <td>${product.id}</td>
        <td>${product.name}</td>
        <td>${product.amount}</td>
        <td>${product.price}kr.</td>
        <td>
            <button class="edit-button button">Rediger</button>
        </td>
        <td>
            <button class="delete-button button">Slet</button>
        </td>
    </tr>
    `;
    tableBody.insertAdjacentHTML("beforeend", html);
}

function fillProductForm(product) {
    document.querySelector("#id").value = product.id;
    document.querySelector("#name").value = product.name;
    document.querySelector("#amount").value = product.amount;
    document.querySelector("#price").value = product.price;
}
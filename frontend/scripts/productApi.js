import {get, post, put, del} from "../fetchUtil.js";

const BASE_URL = "http://localhost:8080/api";
const PRODUCTS_URL = `${BASE_URL}/products`;

export async function getProducts() {
    return get(PRODUCTS_URL);
}

export async function createProduct(newProduct) {
    return post(PRODUCTS_URL, newProduct);
}

export async function updateProduct(id, updatedProduct) {
    return put(`${PRODUCTS_URL}/${id}`, updatedProduct);
}

export async function deleteProduct(id) {
    return del(`${PRODUCTS_URL}/${id}`);
}
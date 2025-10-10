// Method to build fetch type
function createFetchOptions(httpMethod, body, headers = {}) {
  
  //Get token from localStorage, if it exists, add to headers
    const token = localStorage.getItem("token");
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }
  
    // buildes options obj
  const options = {
    method: httpMethod,
    headers: {
      Accept: "application/json",
      ...headers,
    },
  };

  // Not all requests require a body, hence the if statement
  if (body) {
    // Converts the object to a JSON string
    options.body = JSON.stringify(body);
    options.headers["Content-Type"] = "application/json";
  }
  return options;
}

async function responseHandler(res) {
  // 204 = no content
  if (res.status === 204) {
    return null;
  }

  if (!res.ok) {
    throw new Error("HTTP error. status: " + res.status);
  }
  return res.json();
}

// export functions can be imported by other scripts
export async function get(url, headers) {
  const options = createFetchOptions("GET", null, headers);
  const res = await fetch(url, options); // calls the fetch api and saves res
  return responseHandler(res); // Returns the promise from responseHandler
}

export async function post(url, body, headers) {
  const options = createFetchOptions("POST", body, headers);
  const res = await fetch(url, options);
  return responseHandler(res);
}

export async function put(url, body, headers) {
  const options = createFetchOptions("PUT", body, headers);
  const res = await fetch(url, options);
  return responseHandler(res);
}

export async function del(url, headers) {
  const options = createFetchOptions("DELETE", null, headers);
  const res = await fetch(url, options);
  return responseHandler(res);
}

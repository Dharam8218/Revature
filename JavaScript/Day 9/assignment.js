console.log("Start");
setTimeout(() => console.log("Async Task"), 1000);
console.log("End");
console.log("<================================================>");

const res = '{"id":101,"name":"Laptop","price":50000}';
const obj = JSON.parse(res);
console.log(obj.name);
console.log("<================================================>");

const users = ["Rahul", "Aman", "Sneha"];
users.forEach((u) => {
  console.log(`Welcome ${u}!`);
});
console.log("<================================================>");

const prices = [1000, 2000, 3000];
const discounted = prices.map((p) => p - p * 0.1);
console.log(discounted);
console.log("<================================================>");

const products = [
  { name: "Phone", inStock: true },
  { name: "TV", inStock: false },
];
const available = products.filter((p) => p.inStock);
console.log(available);
console.log("<================================================>");

const cart = [{ price: 2000 }, { price: 3000 }, { price: 1500 }];
const total = cart.reduce((sum, item) => sum + item.price, 0);
console.log(total);
console.log("<================================================>");

function calcGST(amount) {
  return amount * 0.18;
}
const calcGSTArrow = (amount) => amount * 0.18;
calcGST(10000);
calcGST(20000);
console.log("<================================================>");

function createCounter() {
  let count = 0;

  return function () {
    count++;
    return count;
  };
}
const counter = createCounter();
console.log(counter());
console.log(counter());
console.log("<================================================>");

function login(user, callback) {
  if (user === "admin") {
    callback("Login Successful");
  } else {
    callback("Invalid User");
  }
}
login("admin", (msg) => console.log(msg));
console.log("<================================================>");


function validate(user, callback) {
  console.log("Validating user...");

  setTimeout(() => {
    if (user === "admin") {
      console.log("User validated");
      callback();
    } else {
      console.log("Invalid user");
    }
  }, 1000);
}

function processPayment(callback) {
  console.log("Processing payment...");

  setTimeout(() => {
    console.log("Payment successful");
    callback();
  }, 1000);
}

function generateInvoice(callback) {
  console.log("Generating invoice...");

  setTimeout(() => {
    console.log("Invoice generated");
    callback();
  }, 1000);
}

validate("admin", () => {
  processPayment(() => {
    generateInvoice(() => {
      console.log("Done");
    });
  });
});
console.log("<================================================>");


async function getData(url) {
  const res = await fetch(url);
  const data = await res.json();
  console.log(data);
}
getData("https://jsonplaceholder.typicode.com/posts/1")
console.log("<================================================>");

document.addEventListener("DOMContentLoaded", () => {
  const ul = document.querySelector("#list");
  const li = document.createElement("li");

  li.textContent = "New Item";
  ul.appendChild(li);
});
console.log("<================================================>");

document.addEventListener("DOMContentLoaded", () => {

  const form = document.getElementById("userForm");
  const msg = document.getElementById("error");

  form.addEventListener("submit", e => {
    const value = document.getElementById("name").value.trim();

    if (value === "") {
      e.preventDefault();  
      msg.textContent = "Name is required";
    } else {
      msg.textContent = "";
    }
  });

});
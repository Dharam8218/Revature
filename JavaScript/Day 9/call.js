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

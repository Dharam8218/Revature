// console.log("Start");

// setTimeout(()=>{
//     console.log("Timeout");
// },0);

// Promise.resolve().then(()=>{
//     console.log("Promise");
// });

// console.log("End");

const arr = [2, 5, 8, 1];
console.log(Math.max(...arr));

const max = arr.reduce((a,b)=>a>b?a:b);
console.log(max);


console.log(Array.isArray(arr));
console.log(Array.isArray(max));


const result = fetch('https://course.acciojob.com/').then(res => res.json()).catch((e)=>console.log(e));


console.log((function(x){return x**2})(5));


async function test() {
  return "Hello";
}
console.log(test());


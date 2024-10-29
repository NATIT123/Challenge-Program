///Create class product
class Product {
  constructor(name, price, quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }
}

///Create value for list products
const products = [
  new Product("Laptop", 999.99, 5),
  new Product("Smartphone", 499.99, 10),
  new Product("Tablet", 299.99, 0),
  new Product("Smartwatch", 199.99, 3),
];

//Create a function to caculate total inventory value
function caculateTotalValue(products) {
  return products.reduce((total, product) => {
    return total + product.price * product.quantity;
  }, 0);
}

///Print total value
const total = caculateTotalValue(products);
const formattedNumber = total.toLocaleString();
console.log(formattedNumber);

///Function to find the most expensive product
function findMostExpensiveProduct(product) {
  return products.reduce((result, product) => {
    return product.price > result.price ? product : result;
  }, product[0]);
}

const expensiveProduct = findMostExpensiveProduct(products);
console.log(expensiveProduct.name);

///Check isExist name product
function isExistName(products, name) {
  const isExist = products.find((products) => {
    return products.name === name;
  });
  return isExist ? true : false;
}

console.log(isExistName(products, "Smartwatch"));

///Sorting Products
function sortProductsWithOptions(products) {
  return products.sort((a, b) => {
    return b.price - a.price;
  });
}

console.log(sortProductsWithOptions(products));

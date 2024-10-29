///Function find missing number in array numbers
function missingNumber(arr) {
  const length = arr.length;

  //Create a hashmap to save value in array
  const mapNumber = new Map();
  for (let i = 0; i < length; i++) {
    mapNumber.set(arr[i], i);
  }

  ///Check in hashmap by check each number in (1,length+1) . If number is not in hashmap return number
  for (let i = 1; i <= length + 1; i++) {
    if (!mapNumber.has(i)) {
      return i;
    }
  }
}

console.log(missingNumber([3, 7, 1, 2, 6, 4]));

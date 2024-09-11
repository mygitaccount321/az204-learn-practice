
//input:
//{
//   "name": "Daniel Elfyn",
//   "country": "USA",
//   "income": 70000
//}


function tax(income){
    if (income == undefined)
         return 0;

    if (income < 1000)
        return income * 0.1;
    else if (income < 10000)
        return income * 0.2;
    else
        return income * 0.4;
}
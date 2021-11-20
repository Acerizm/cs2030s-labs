Logger<Integer> add(Logger<Integer> a ,int b) {
    Logger<Integer> tempLogger = a.map(x -> x + b);
    return tempLogger;
}

private Logger<Integer> tempLogger;

public Logger<Integer> sum(int n) {
    try {
        if (n == 0) {
            tempLogger = Logger.<Integer>of(n);
            return add(tempLogger,n);
        } else {
            tempLogger = add(tempLogger,n);
            sum(n-1);
            return tempLogger;
            //at the end of the day,
            // I have to return the sum in tempLogger
        }
    } catch(Exception ex) {
        throw ex;
    }
}

//Collatz conjecture here
// int sum(int n) {
//     if (n == 0) {
//         return 0;
//     } else {
//         return sum(n - 1) + n;
//     }
// }

/*
if n == 2
Case 1:
    sum(2-1) + 2 = ;
    sum(0) + 1 + 2 = 3
Case 2:
    sum (1-1) = 0
Case 3: 
    3 + 0 = 3;
}
*/
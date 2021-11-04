Logger<Integer> add(Logger<Integer> a ,int b) {
    Logger<Integer> tempLogger = a.map(x -> x + b);
    return tempLogger;
}

Logger<Integer> sum(int n) {
    if (n == 0) {
        Logger<Integer> tempLogger = add(n,0);
        return tempLogger;
    } else {
        return sum(n - 1) + n;
    }
}

/*
if n == 2
Case 1:
    sum(2-1) + 2 = 3;
Case 2:
    sum (1-1) = 0
Case 3: 
    3 + 0 = 3;
}
*/
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.util.stream.IntStream;

class Main {

    public static void main(String[] args) {
        //System.out.println(countTwinPrimes(100));
        //boolean test = isPrime(5+2);
        //System.out.println(test);
        System.out.println(reverse("abc"));                                                   

    }

    //other methods
    static long countTwinPrimes(int n) {
        // twin prime is one pair of prime numbers with a difference of 2
        // 41 and 43 are prime numbers

        //count the number of primes since n
        //using streams                                                     
        long numOfTwinPrimes = Stream.<Integer>iterate(1, x -> x < n+1, x -> x + 1)
            // if you filter without considering the previous number, the number will be missing
            .filter(x -> (isPrime(x) && isPrime(x+2)) || isPrime(x) && isPrime(x-2) )
            // use peek to test for each element inside the stream
            //.peek(x -> System.out.println(x))
            .count(); //count the number
        
        //return the numOfTwinPrimes
        return numOfTwinPrimes;
    }

    static boolean isPrime(int n) {
        return n > 1 && IntStream.range(2, (int) Math.sqrt(n) + 1) // or (2,n)
            .noneMatch(x -> n % x == 0); 
    }

    // Method to reverse the string
    static String reverse(String str) {
        List<Character> reverseList = new ArrayList<Character>();
        char[] letterArray = str.toCharArray();
        int size = letterArray.length;
        IntStream.range(0,size)
            .mapToObj(i -> letterArray[(size - 1) - i])
            .forEach(letter -> reverseList.add(letter));

        String reversedLetters = reverseList.stream()
            .map(String::valueOf)
            .collect(Collectors.joining());
        
        return reversedLetters;
    }

    // Counting Repeats
    static long countRepeats(int[] array) {
        // Count the number of repeats in this array
        
        return 0;
    }
}
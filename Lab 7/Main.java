import java.util.stream.Stream;

import javax.xml.stream.util.StreamReaderDelegate;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            // I don't understand why I can mutate this?
            .forEach(letter -> reverseList.add(letter));

        String reversedLetters = reverseList.stream()
            .map(String::valueOf)
            .collect(Collectors.joining());
        
        return reversedLetters;
    }

    // Counting Repeats
    static long countRepeats(int[] array) {
        // Count the number of repeats in this array
        // Knowledge is referenced from https://www.geeksforgeeks.org/how-to-find-duplicate-elements-in-a-stream-in-java/
        // We will be using Collections.frequency
        // Don't need to re-invent the wheel/algo

        //https://stackoverflow.com/questions/30122439/converting-array-to-list
        //List<Integer> numList  =  new ArrayList<Integer>();
        // need to box the int type because Java needs us to handle auto-boxing
        // one of the retarded disadvantages of java
        // as generics was added late to Java compared to other languages
        // which meant that int type was not a subtype of Object type
        // compared to other languages like C# where int is a type of Object
        // Collections.addAll(numList,Arrays.stream(array).boxed().toArray(Integer[]::new));
        // List<Integer> frequentList = numList.stream()
        //     .filter(number -> Collections.frequency(numList, number) > 1)
        //     .collect(Collectors.toList());
        
        //how to mutate state? I need to keep track of the state
        // We will try to use Java 10's implementation of wrapper classes
        // knowledge: https://stackoverflow.com/questions/30026824/modifying-local-variable-from-inside-lambda/30039206#30039206
        //var wrapper = new Object(){ int value = 0; };
        // Stream.<Integer>iterate(0, x -> x < numList.size(), x -> x + 1)
        //     .filter(x -> (x == x+1))

        // Best knowledge for reducers: 
        // https://stackoverflow.com/questions/24308146/why-is-a-combiner-needed-for-reduce-method-that-converts-type-in-java-8
        // maybe use a reduce method
        
        // Answer: https://stackoverflow.com/questions/52742944/number-of-occurrence-intstream-java
        // I have no idea how this algo even made sense
        return IntStream.range(0, array.length-1)
            .filter(i ->
                array[i] == array[i+1] && (
                   i >= array.length-2 ||
                   array[i] != array[i+2]
                )
             )
             .count();
    }
}
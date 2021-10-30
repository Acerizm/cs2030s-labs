import java.util.stream.Stream;

import javax.xml.stream.util.StreamReaderDelegate;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

// //comparator class here
// class SortByDecending implements Comparator<Integer> {
//     public int compare(int a, int b) {
//         if (a == b) {
//             return 0;
//         } else if (a < b)
//     }
// }

class Main {

    public static void main(String[] args) {
        //System.out.println(countTwinPrimes(100));
        //boolean test = isPrime(5+2);
        //System.out.println(test);
        //System.out.println(reverse("abc")); 
        System.out.println(normalizedMean(Stream.<Integer>of(1, 1)));                                                  

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

    // Credits: https://stackoverflow.com/questions/66828296/how-do-i-find-the-normalized-mean-of-a-stream
    // public static double normalizedMean(Stream < Integer > stream) {
    //     List < Integer > sortedList = stream.sorted().collect(Collectors.toList());
    //     Integer max = sortedList.get(sortedList.size() - 1);
    //     Integer min = sortedList.get(0);
    //     long count = sortedList.size();
    //     Integer sum = sortedList.stream().mapToInt(Integer::intValue).sum();
    //     return (double)((sum / count) - min) / (max - min);
    // }

    //My own algo
    // Idea: https://stackoverflow.com/questions/66828296/how-do-i-find-the-normalized-mean-of-a-stream
    public static double normalizedMean(Stream<Integer> stream) {
        //too slow algo
        // int max = stream.sorted().reduce(
        //     0, 
        //     (sum,currentVal) -> {
        //         if (currentVal > sum) {
        //             return sum+currentVal;
        //         } else {
        //             return sum;
        //         }
        //     }
        //     );
        List<Integer> streamCopy = stream.collect(Collectors.toList());
        //Supplier<Stream<Integer>> streamSupplier = () -> stream;
        // streams cannot be used twice
        double max = streamCopy.stream().mapToDouble(x -> x).max().orElse(0.0);
        double min = streamCopy.stream().mapToDouble(x -> x).min().orElse(0.0);
        // //.count() method returns count because Java
        long count = streamCopy.stream().count();
        double sum = streamCopy.stream().mapToDouble(x -> x).sum();
        // I am forced to use optionals because i cannot use if-else
        Optional<Double> mean = Optional.ofNullable(((sum / count) - min) / (max - min)).filter(meanValue -> !Double.isNaN(meanValue));
        //streamCopy.stream().
        return mean.orElse(0.0);
    }   
}
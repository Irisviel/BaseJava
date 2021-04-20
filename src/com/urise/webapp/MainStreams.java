package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        int[] ints = {7, 7, 2, 3, 0};

        System.out.println("Initial values: " + Arrays.toString(ints));
        System.out.println("Result of minValue(): " + minValue(ints));
        System.out.println();

        List<Integer> integers = Arrays.asList(5, 3, 8, 7, 0, 12);
        System.out.println("Initial values: " + integers);
        System.out.println("Initial values sum: " + integers.stream().reduce(0, Integer::sum));
        System.out.println("Result of oddOrEven(): " + oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (aggregator, element) -> 10 * aggregator + element);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, Integer::sum);
        return sum % 2 == 0 ?
                integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList()) :
                integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
    }
}

//package com.walmart.labs.pcs.normalize.java8Examples;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Predicate;
//
///**
// * Created by pzhong1 on 12/19/14.
// */
//public class LambdaExpressionExample {
//    public static void main(String [] a)  {
//
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
//
//        System.out.println("Print all numbers:");
//        evaluate(list, (n)->true);
//
//        System.out.println("Print no numbers:");
//        evaluate(list, (n)->false);
//
//        System.out.println("Print even numbers:");
//        evaluate(list, (n)-> n%2 == 0 );
//
//        System.out.println("Print odd numbers:");
//        evaluate(list, (n)-> n%2 == 1 );
//
//        System.out.println("Print numbers greater than 5:");
//        evaluate(list, (n)-> n > 5 );
//
//        list.forEach(n -> System.out.println(n));
//        list.stream().map((x) -> x*x).forEach(System.out::println);
//        int sum = list.stream().map(x -> x*x).reduce((x,y) -> x + y).get();
//        System.out.println(sum);
//    }
//
//    public static void evaluate(List<Integer> list, Predicate<Integer> predicate) {
//        for(Integer n: list)  {
//            if(predicate.test(n)) {
//                System.out.println(n + " ");
//            }
//        }
//    }
//}

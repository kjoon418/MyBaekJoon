package baekjoon.BronzeSilver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i >= 3;
            }
        };

        list.stream()
                .filter((i) -> i >= 3)
                .forEach(i -> System.out.println(i));

//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i) >= 3) {
//                System.out.println(list.get(i));
//            }
//        }
    }

    public static <T> boolean streamFilter(Predicate<T> hello, T t) {

        return hello.test(t);
    }

    public static void hello(String str) {
        return;
    }
}

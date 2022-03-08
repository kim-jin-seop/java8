package session9;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {
        /**
         * Arrays.parallelSort : Fork/Join FrameWork를 사용해 병렬적으로 정렬하는 기능 제공
         */
        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();

        IntStream.range(0,size).forEach(i -> numbers[i] = random.nextInt());
        long start = System.nanoTime();
        Arrays.sort(numbers);
        System.out.println("serial sorting took " + (System.nanoTime() - start));

        IntStream.range(0,size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers); //Thread를 사용하여 parallel하게 정렬해줌 -> 데이터의 개수에 따라 달라질 수 있음
        System.out.println("parallel sorting took " + (System.nanoTime() - start));

        /**
         * PermGen -> MetaSpace
         * Heap -> Native Memory
         */
        // 가비지 컬렉터를 공부하자.
    }
}

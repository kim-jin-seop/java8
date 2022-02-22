package session3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        /**
         * Stream
         * 데이터를 담고있는 저장소가 아님 -> 데이터를 활용해 처리를 하는 것
         */

        List<String> names = new ArrayList<>();
        names.add("kim");
        names.add("jin");
        names.add("seop");

        /**
         * 스트림은 데이터에 영향을 주지 않는다.
         * 아래와 같이 문자를 대문자로 바꾸는 코드를 작성했다고해서 names가 대문자가 되지 않는다.
         */
        names.stream().map(String::toUpperCase); // 대문자가 되지 않음

        /**
         * 스트림의 파이프라인은 1개 이상의 중계 오퍼레이션과 1개의 종료 오퍼레이션으로 구성된다.
         * 중계 오퍼레이션은 스트림을 반환하고, 종료 오퍼레이션은 스트림을 반환하지 않는다
         * 중계 오퍼레이션은 Lazy하게 처리가 된다.
         */
        //이렇게 실행하여도 중계오퍼레이션에서 끝났기 때문에 실행이 되지 않아 출력은 없음.
        names.stream().map((s) -> {
            System.out.println("s = " + s);
            return s.toUpperCase(Locale.ROOT);
        });

        //종료 오퍼레이션이 들어와야지 최종적으로 실행됨.
        names.stream().map((s) -> {
            System.out.println("s = " + s);
            return s.toUpperCase(Locale.ROOT);
        }).collect(Collectors.toList());

        /**
         * 병렬처리를 수행하기 용이하다.
         */
        //스트림이 없이 병렬처리를 하는 경우
        for(String name : names){
            if(name.startsWith("k")) {
                System.out.println("name = " + name.toUpperCase());
            }
            // ...
        }

        //스트림으로 병렬처리하는 경우 -> parallelStream() 사용
        //데이터가 방대하게 많은 경우에만 효과가 좋음(항상 성능이 좋다고 보장은 못함)
        System.out.println("<<<<<<parallelStream 사용한 경우>>>>>>");
        List<String> collect = names.parallelStream()
                .map((s) -> {
                    System.out.println(s +" = " + Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .collect(Collectors.toList());

        System.out.println("<<<<<<Stream을 사용한 경우>>>>>>");
        List<String> collect2 = names.stream()
                .map((s) -> {
                    System.out.println(s +" = " + Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .collect(Collectors.toList());
    }
}

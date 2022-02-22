package session3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        /**
         * Stream API 사용 예제
         */
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        System.out.println("spring 으로 시작하는 수업의 id");
        /**
         * filter : 값을 특정 조건으로 걸러준다. 조건이 true인 것들만 나오게한다.
         */
        springClasses.stream()
                .filter(c -> c.getTitle().startsWith("spring"))
                .forEach(c -> System.out.println(c.getId()));

        System.out.println("close 되지 않은 수업");
        springClasses.stream()
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(c -> System.out.println("c = " + c));

        /**
         * map : 새로운 타입의 스트림으로 변환
         * 아래 예시를 보면 온라인 클래스로 들어온 정보를 getTitle을 받아줌으로 써 스트링으로 변환했음
         * 그 다음에 처리할때는 스트링에 대한 처리가 이루어짐.
         */
        System.out.println("수업 이름만 모아서 스트림 만들기");
        springClasses.stream()
                .map(OnlineClass::getTitle)
                .forEach(System.out::println);

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> events = new ArrayList<>();
        events.add(springClasses);
        events.add(javaClasses);

        /**
         * flatMap : 컬렉션을 하나의 스트림으로 변환
         * 예를들어 컬렉션에 리스트가 들어가서 스트림으로 리스트가 흘러가게 되면 flatMap을 활용해 리스트에 있는 데이터를 모두 스트림으로 처리가 가능
         */
        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        events.stream()
                .flatMap(Collection::stream) // list -> list.stream()
                .forEach(oc -> System.out.println(oc.getId()));

        /**
         * iterate(seed,func) : 무한하게 동작하는 스트림
         * 스트림으로 func를 무한하게 실행하게된다. (종료 오퍼레이션은 아니므로 Stream.iterate(10, i -> i + 1) 이것만으로 무한루프는 아님)
         */
        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        Stream.iterate(10, i -> i + 1)
                .skip(10) //처음 10개 스킵
                .limit(10) //10개만 가져옴
                .forEach(System.out::println);

        /**
         * match : allMatch, anyMatch가 있음 종료 오퍼레이션으로 boolean을 return해줌
         * allMatch라면 스트림이 모두 매칭되는 경우 true이고 anyMatch라면 하나라도 매칭되면 true이다.
         */
        System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        boolean test = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(test);

        /**
         * collect() : 스트림의 결과를 컬렉션 타입으로 받기
         */
        System.out.println("스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 List로 만들기");
        List<String> spring = springClasses.stream()
                .map(OnlineClass::getTitle)
                .filter(t -> t.contains("spring"))
                .collect(Collectors.toList());
        spring.forEach(System.out::println);
    }
}

package session2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        Foo foo = new ImplFoo("aa");
        foo.printName();

        // default method 사용
        foo.printUpperName();

        //staitc method 사용
        Foo.helloStatic();

        /**
         * Iterable의 기본 메소드
         * Collection - foreach()
         * foreach는 파라미터로 Consumer functional interface를 받는다.
         * consumer이기 때문에 값 하나를 받아서 소모를하게 된다.
         * */
        List<String> names = new ArrayList<>();
        names.add("kim jin seop");
        names.add("java8");
        names.add("spring");
        names.add("jpa");

        //리스트에 저장된 이름을 모두 출력해주기
        names.forEach((s) -> System.out.println(s));

        // 메소드 레퍼런스를 활용해서 구현할 수 있음
        System.out.println("<< 메소드 레퍼런스 활용해서 구현 >>");
        names.forEach(System.out::println);

        /**
         * Iterable의 기본 메소드
         * Collection - spliterator()
         * 동일하게 foreach처럼 동작하게 된다.
         * 데이터를 쪼개서 사용할 수 있게 된다. 절반으로 쪼개어 진다.
         * 쪼개는 방법은 trySplit()을 사용하여준다.
         * tryAdvance를 활용해 실행할 수 있는데, 만약 다음 값이 있으면 true 없으면 false로 while을 사용해 순회할 수 있다.
         * 혹은 forEachRemaining()을 활용하여 모든 값들을 순회할 수 있다.
         * 순서가 뒤바뀌기 때문에 순서에 의미가 없는 메소드에 적용
         */

        //spliterator 정의
        Spliterator<String> sp1 = names.spliterator();
        //trySplit으로 절반 쪼개기(데이터를 반으로 쪼개어줌)
        Spliterator<String> sp2 = sp1.trySplit();

        //순회 방법1 forEachRemaining으로 남은 것 모두 순회
        sp1.forEachRemaining(System.out::println);
        //순회방법2 tryAdvance 사용
        while (sp2.tryAdvance(System.out::println));

        /**
         * Collection 기본 메소드
         * Collection - stream()
         * 매우 자주쓰이는 기본 메소드
         * 다음에 자세히 공부하도록 하겠다.
         * filter()로 걸러서 count()로 갯수를 셀수도 있고, list나 set으로 반환받을 수 있다.
         */
        System.out.println("<<Stream 예시>>");
        //k로 시작하는 값들만 뽑아서 출력
        names.stream()
                .filter((s) -> s.startsWith("k"))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        /**
         * Collection 기본 메소드
         * Collection - removeIf()
         * 파라미터로 Predicate을 받으며 일치하는 조건의 값을 지워주는 역할을 수행한다.
         */
        names.removeIf(s -> s.equals("jpa"));
        names.forEach(System.out::println);

        /**
         * Comparator 기본 메소드
         * Comparator - reversed()
         * sort()에 들어가는 파라미터가 Comparator가 들어가게 된다.
         * Comparator는 functional interface이다.
         * 정렬을 역순으로 하고 싶다면 compareToIgnoreCase에 reversed()를 해주면 된다. 여기서 사용되는 reversed()는 default 메소드이다.
         */
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        //정렬하기
        names.sort(compareToIgnoreCase);
        //역순으로 정렬하기
        names.sort(compareToIgnoreCase.reversed());

        /**
         * Comparator 기본 메소드
         * Comparator - thenComparing()
         * 정렬을 할 때 추가 조건을 부여할 때 사용한다.
         */
        Comparator<String> ci = String::compareToIgnoreCase;
        names.sort(ci.thenComparing(Comparator.reverseOrder()));

        /**
         * Comparator의 static 메소드들
         * reverseOrder() : 거꾸로 정렬 -> 파라미터 없음
         * naturalOrder() : 정렬 -> 파라미터 없음
         * nullsFirst() : null을 처음으로 오게 정렬 -> 파라미터로 Comparator 받음
         * nullsLast() : null을 뒤로 가게 정렬 -> 파라미터로 Comparator 받음
         */
        System.out.println("<<<<<<<<<<<<");
        names.forEach(System.out::println);
        System.out.println("<<<<<<<<<<<<");
        names.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        names.forEach(System.out::println);
    }
}

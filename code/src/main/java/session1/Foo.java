package session1;

import java.util.function.*;

public class Foo {
    public static void main(String[] args) {

        /*
        람다로 함수형인터페이스 정의하기
        * */
        // 과거 (익명 내부 클래스)
        RunSomething runSomething = new RunSomething() {
            @Override
            public int doIt(int number) {
                System.out.println("hello");
                System.out.println("world");
                return number;
            }
        };

        //java8 람다 표현식
        RunSomething runSomething2 = (number) -> {
            System.out.println("hello");
            System.out.println("world");
            return number;
        };

        /*
        * java에서 정의되어있는 함수형 인터페이스
        * */
        //1. Function : <T,R> : T타입의 데이터를 받아서 R로 return 해줌, 한줄로 줄일 경우 return 생략 가능
        Function<Integer, String> integerToString = (value) -> String.valueOf(value);
        Function<Boolean, Integer> booleanToInteger = (value) -> value ? 1 : 0;
        integerToString.apply(352);

        //compose로 두 함수를 compose할 수 있음 compose의 파라미터로 들어간 함수를 실행한 결과값을 해당 함수로 실행하게 됨. f(g(x))
        Function<Boolean, String> compose = integerToString.compose(booleanToInteger);
        compose.apply(true); // true -> 1로 바뀌고 -> 1이 String으로 변환됨.

        //andThen을 사용하면 해당 함수 뒤에 붙임 booleanToInteger 후 결과값이 integerToString의 input으로 사용됨.
        Function<Boolean, String> booleanStringFunction = booleanToInteger.andThen(integerToString);

        //2. BiFunction : <T,U,R> : T와 U를 매개변수로 받아서 R의 결과값을 내어줌
        BiFunction<Integer,Integer,Integer> plus = (a,b) -> a+b;
        plus.apply(5,3); //  8

        //3. Consumer : <T> : 매개변수 T를 받아 사용, return 없음
        Consumer<String> printT = (i) -> System.out.println(i);
        printT.accept("안녕하세요");

        //4. Supplier : <T> : T로 return받음, 파라미터 없음
        Supplier<Integer> age = () -> 25;
        age.get(); // 25

        //5. Predicate : <T> : 파라미터 T를 받아서 True False를 return
        Predicate<Integer> isEven = (i) -> i % 2 == 0;
        Predicate<Integer> isTenDown = (i) -> i < 10;
        isEven.or(isTenDown); // or -> 같은 T에 대하여 가능
        isEven.and(isTenDown); // and -> 같은 T에 대하여 처리 가능
        isEven.negate(); // not

        //6. UnaryOperator : <T> : Function에서 입력과 출력이 같은 타입일 경우 사용
        Function<Integer, Integer> plus10 = (i) -> i + 10;
        UnaryOperator<Integer> unaryPlus10 = (i) -> i + 10;

        //BinaryOperator : <T> : 입력이 2개가 오고 그에 대한 출력이 1개인데 모두 타입이 T로 같음
        BiFunction<Integer,Integer,Integer> biPlus = (a,b) -> a+b;
        BinaryOperator<Integer> binaryPlus = (a,b) -> a + b;
        
    }
}

package session1;

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

    }
}

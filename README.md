# java8
java8에서 추가된 자바의 기능들을 알아보기

## 함수형 인터페이스와 람다식
- 함수형 인터페이스 : 인터페이스에 메소드 하나만 존재하는 것
```java
@FunctionalInterface
public interface RunSomething {
    //추상 메소드가 하나만 있으면 함수형 인터페이스
    abstract int doIt(int number);

//    static void printName(){
//        System.out.println("jinseop");
//    }
//
//    default void printAge(){
//        System.out.println("26");
//    }
}

```
*@FunctionalInterface* annotation을 반드시 붙여주어 정의하고 추상메소드 하나만 존재하면 됨(abstract는 생략가능)
이 때 default와 static 어느것이 오든지 상관 없음.  

- 람다 표현식
함수형 인터페이스를 람다표현식으로 정의할 수 있다.
  ```java
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
  ```
  람다표현식은 함수형 언어 처럼 함수를 변수에 넣는것 처럼 사용 가능. 이 때 실행되는 라인이 1라인이라면 {}도 생략 가능하다.


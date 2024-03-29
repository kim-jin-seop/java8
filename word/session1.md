# 함수형 인터페이스와 람다식
- ### 함수형 인터페이스
  메소드가 하나만 존재하는 인터페이스를 의미한다.
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


- ### 람다 표현식으로 함수형 인터페이스 정리   
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


- ### 자바에서 제공하는 함수형 인터페이스
  - #### 1. Function
    ``` java
    //Function : <T,R> : T타입의 데이터를 받아서 R로 return 해줌, 한줄로 줄일 경우 return 생략 가능
    Function<Integer, String> integerToString = (value) -> String.valueOf(value);
    Function<Boolean, Integer> booleanToInteger = (value) -> value ? 1 : 0;
    integerToString.apply(352);
    ```
    Function은 파라미터 하나를 입력 받아서 결과를 리턴해주는 것이다. 제너릭으로 <T,R>을 받는데, T는 입력받는 파라미터의 타입, R은 Return 받는 파라미터의 타입이다.
    
    ```java
    //compose로 두 함수를 compose할 수 있음 compose의 파라미터로 들어간 함수를 실행한 결과값을 해당 함수로 실행하게 됨. f(g(x))
    Function<Boolean, String> compose = integerToString.compose(booleanToInteger);
    compose.apply(true); // true -> 1로 바뀌고 -> 1이 String으로 변환됨.
    ```
    Function은 compose를 활용해 두 함수를 합칠 수 있다. f(g(x))로 compose안에 들어있는 function을 실행한 결과값을 input으로 사용할 수 있다.
    
    ```java
    //andThen을 사용하면 해당 함수 뒤에 붙임 booleanToInteger 후 결과값이 integerToString의 input으로 사용됨.
    Function<Boolean, String> booleanStringFunction = booleanToInteger.andThen(integerToString);
    ```
    andThen을 사용해서 함수들을 이어 붙일 수 있다. andThen 앞에서 실행한 결과 값을 파라미터에 있는 함수로 실행시켜준다.
  
  - #### 2. BiFunction
    ```java
    //BiFunction : <T,U,R> : T와 U를 매개변수로 받아서 R의 결과값을 내어줌
    BiFunction<Integer,Integer,Integer> plus = (a,b) -> a+b;
    plus.apply(5,3); //  8
    ```
    BiFuntion은 <T,U,R>이렇게 세가지를 입력받으며 T와 U 두개의 파라미터를 받아 R로 리턴해주는 것이다. Function과 다른점은 입력받는 함수의 파라미터의 개수가 2개가 된다.
  
  - #### 3. Consumer 
    ```java
    //Consumer : <T> : 매개변수 T를 받아 사용, return 없음
    Consumer<String> printT = (i) -> System.out.println(i);
    printT.accept("안녕하세요");
    ```
    Consumer는 <T>라는 매개변수 하나를 받는다. T 하나를 받아서 사용만 하고, 따로 리턴받는 값은 없다.

  - #### 4. Supplier
    ```java
    //Supplier : <T> : T로 return받음, 파라미터 없음
    Supplier<Integer> age = () -> 25;
    age.get(); // 25
    ```
    Supplier는 <T>라는 매개변수 하나를 받는다. 입력받는 파라미터는 없으며 T라는 값으로 리턴을 받기만 한다.

  - #### 5. Predicate
    ```java
    //Predicate : <T> : 파라미터 T를 받아서 True False를 return
    Predicate<Integer> isEven = (i) -> i % 2 == 0;
    Predicate<Integer> isTenDown = (i) -> i < 10;
    isEven.or(isTenDown); // or -> 같은 T에 대하여 가능
    isEven.and(isTenDown); // and -> 같은 T에 대하여 처리 가능
    isEven.negate(); // not
    ```
    Predicate는 <T> 타입의 파라미터를 받아서 True인지 False인지를 반환해준다. 이때 or, and, not도 사용할 수 있다. or와 and를 사용할 때는 같은 타입의 T를 사용해야한다.

  - #### 6. UnaryOperator
    ```java
    //UnaryOperator : <T> : Function에서 입력과 출력이 같은 타입일 경우 사용
    Function<Integer, Integer> plus10 = (i) -> i + 10;
    UnaryOperator<Integer> unaryPlus10 = (i) -> i + 10;
    ```
    UnaryOperator은 Function에서와 동일한 역할을 수행하는데, 다만 차이점은 T,R이 아닌 T만 받으며, Return과 파라미터의 타입이 같다.
  
  - #### 7. BinaryOperator
    ```java
    //BinaryOperator : <T> : 입력이 2개가 오고 그에 대한 출력이 1개인데 모두 타입이 T로 같음
    BiFunction<Integer,Integer,Integer> biPlus = (a,b) -> a+b;
    BinaryOperator<Integer> binaryPlus = (a,b) -> a + b;
    ```
    BiFunction과 동일한 역할을 수행하지만 <T> 하나만 받는다. 입력받는 파라미터 2개와 리턴하는 값의 타입이 동일하다.
  
  이 외에도 다양한 함수형 인터페이스가 있다. 이름으로 유추해보거나 혹은 매뉴얼을 보며 사용하면 된다.
  
- ### 람다 표현식
  전체 코드 미리보기 람다와 람다의 역할을 수행하도록 구현할 수 있는 로컬클래스 그리고 익명클래스를 생성해보고, 람다의 스코프에 대한 차이점에 주목하여 공부하였다.
  ```java
      private void run(){
        int baseNumber = 10;
        // 로컬 클래스
        class LocalClass{
            void printBaseNumber(){
                int baseNumber = 11;
                System.out.println(baseNumber);
            }
        }

        // 익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber);
            }
        };

        // 람다
        IntConsumer printInt = (i) -> System.out.println(baseNumber);
        printInt.accept(10);
    }
  ```
  - #### 로컬 클래스,익명 클래스 그리고 람다의 공통점
    - 자신의 스코프의 외부 변수를 참조 가능하다. 위 메소드에 있는 baseNumber라는 값을 3곳 모두 참조 가능하다.
    - 그런데 그 로컬변수가 final이 아니라면 참조가 안된다. 위의 경우 baseNumber는 묵시적으로 final이 된다.
  
  - #### 로컬 클래스,익명 클래스 vs 람다의 차이점
    - 스코프가 다르다. 로컬과 익명의 경우 자기 자신의 내부의 스코프를 따로 보기 때문에 외부 변수와 동일한 이름의 변수를 사용할 수 있고, 사용한다면 내부에 선언된 변수를 쓸 것이다.
    - 하지만 람다의 경우 스코프가 자신을 선언한 위치에 포함된다. 즉 필드에 선언했다면 필드, 메소드에 선언했다면 메소드와 동일한 스코프를 가진다. 따라서, 위 경우 baseNumber를 선언할 수 없다.
  
- ### 메소드 레퍼런스
  ```java
  public class Greeting {
    private String name;

    public Greeting() {
    }

    public Greeting(String name){
        this.name = name;
    }

    public String hello(String name) {
        return "hello " + name;
    }

    public static String hi(String name){
        return "hi" + name;
    }
  }
  ```
  이번 메소드 레퍼런스를 위해 클래스를 하나 구현하였다. 클래스에는 스태틱 메소드, 인스턴스메소드, 생성자들이 있다.
  메소드를 참조하는 이유는 동일한 역할을 수행하는 메소드를 따로 구현할 필요없이 재사용하기 위해서 이다.
  - #### static 메소드
  ```java
  UnaryOperator<String> hi = Greeting::hi;
  ```
  static 메소드의 경우 클래스이름::메소드이름 으로 가져올 수 있다. 이 때 사용하는 ::이 메소드를 참조하기 위해 사용되는 문법이다.
  
  - #### instance 메소드
  ```java
  Greeting greeting = new Greeting();
  UnaryOperator<String> hello = greeting::hello;
  ```
  instance 메소드를 사용하기 위해서는 객체를 생성해야한다. 그 생성한 객체로 ::를 활용해 메소드를 참조할 수 있다.
  
  - #### 생성자 참조
  생성자를 참조하는 경우에는 입력값은 파라미터 결과값은 그 클래스로 표현이 된다.
    - 파라미터가 없는 경우
      ```java
      Supplier<Greeting> newGreeting = Greeting::new;
      newGreeting.get(); //객체를 생성
      ```
      클래스의 이름::new 로 생성자를 참조할 수 있다.
  
    - 파라미터가 있는 경우
      ```java
      Function<String, Greeting> newGreeting2 = Greeting::new;
      newGreeting2.apply("Jin Seop");
      ```
      파라미터가 없는 경우와 동일하게 참조를 한다. 하지만 function으로 string을 하나 받기로 하였으므로 apply로 사용 시 파라미터를 받아야한다.
      
  - #### 임의의 객체의 인스턴스 레퍼런스
    ```java
    String[] names = {"a", "b", "c"};
    Arrays.sort(names, String::compareToIgnoreCase);
    ```
  임의의 객체의 인스턴스도 위처럼 접근할 수 있다.
  참고로 Arrays.sort의 2번째 인자로 Comparator를 받는다. 이 때 Comparator는 compare이라는 추상메소드를 하나 갖는 Funcational Interface로 람다로 사용이 가능하다.
## java8의 인터페이스

### default Method
- java8에서 새로 추가된 기능으로 인터페이스에 기본 메소드를 정의할 수 있게 되었다.
- 기본메소드를 정의하는 이유는 인터페이스를 구현하는 구현체들이 공통된 역할을 수행하는 메소드를 사용하기 위함이다.
- default로 추가해줌으로써 원래 인터페이스를 구현하고 있던 구현체에서 새로운 메소드를 정의할 필요가 없어진다.

#### Default Method 구현 방법
```java
public interface Foo {
    String getName();

    default void printUpperName() {
        System.out.println(getName().toUpperCase());
    }
}


```
위 처럼 default를 활용해 메소드를 interface에서 구현해 줄 수 있다. 이 인터페이스를 구현하는 구현체에서 따로 재정의 없이 메소드를 사용할 수 있다.

#### default method 특징
- 디폴트 메소드로 구현을 할 경우 개발자가 원하는 동작과 다르게 동작할 수 있다. 이는 @implSpec을 활용해 정확히 스팩을 명시해줌으로써 사용자가 개발자가 원하는 방향으로 사용할 수 있도록 유도해주는 것이 좋다.
- Object에서 구현되어있는 메소드는 default로 재정의할 수 없다. java의 object 메소드의 예시로 toString이 있는데, 이 것은 default로 재정의가 불가하다.
- interface를 상속받은 interface에서 default를 사용하기 싫다면, default를 추상메소드로 재정의 해주면 된다.
- interface를 2개 받아서 구현하는 구현체에서 만약 default 메소드의 이름이 동일하게 있다면 컴파일 에러가 발생한다.

### Static Method
- 자바8에서는 interface에 static method를 구현할 수 있다.
- 기존 static과 동일하게 인터페이스의 명으로 static method에 접근이 가능하다.

#### static method 사용 예시
```java
public interface Foo {
    /**
     * interface에서 static 메소드를 구현하여 사용할 수 있다.
     */
    static void helloStatic(){
        System.out.println("Hello Static Method in interface");
    }
}

public static void main(String[] args) {
    Foo.helloStatic();
}
```
static method 구현 시 인터페이스의 이름으로 접근이 가능하게 된다.

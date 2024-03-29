# java8의 인터페이스

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

### Iterable의 기본 메소드
Iterfable의 대표적인 기본 메소드로 foreach()와 spliterator()이 있다.

#### foreach()
- Collection :foreach()  
- foreach는 파라미터로 Consumer functional interface를 받는다.  
- consumer이기 때문에 값 하나를 받아서 소모를하게 된다.  

```java
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
```

#### spliterable() 
- 동일하게 foreach처럼 동작할 수 있다.  
- 데이터를 쪼개서 사용할 수 있게 된다. 절반으로 쪼개어 진다.  
- 쪼개는 방법은 trySplit()을 사용하여준다. 데이터의 개수의 절반으로 쪼개어 준다.  
- tryAdvance를 활용해 실행할 수 있는데, 만약 다음 값이 있으면 true 없으면 false로 while을 사용해 순회할 수 있다.  
- 혹은 forEachRemaining()을 활용하여 모든 값들을 순회할 수 있다.(tryAdvance를 사용했다면 남은 구간을 모두 순회한다.)  
- 순서가 뒤 바뀌기 때문에 순서에 의미없는 메소드에 적용할 수 있다.

```java
        //spliterator 정의
        Spliterator<String> sp1 = names.spliterator();
        //trySplit으로 절반 쪼개기(데이터를 반으로 쪼개어줌)
        //sp1에 있던 데이터의 절반을 가져오고 sp1에 절반 데이터가 옮겨짐
        Spliterator<String> sp2 = sp1.trySplit();

        //순회 방법1 forEachRemaining으로 남은 것 모두 순회
        sp1.forEachRemaining(System.out::println);
        //순회방법2 tryAdvance 사용
        while (sp2.tryAdvance(System.out::println));
```

### Collection의 기본 메소드
Collection에서 제공하는 다양한 기본 메소드에 대해 알아보겠다.  

#### stream()
- Collection의 대표적인 기본 메소드로 strem()이 있다.
- 매우 자주쓰이는 기본 메소드이다.
- filter()로 걸러서 count()로 갯수를 셀수도 있고, list나 set으로 반환받을 수 있다.
- 다음에 자세히 알아보도록 하겠다.
```java
        //k로 시작하는 값들만 뽑아서 출력
        names.stream()
                .filter((s) -> s.startsWith("k"))
                .collect(Collectors.toList())
                .forEach(System.out::println);
```

#### removeIf()
- 파라미터로 Predicate을 받으며 일치하는 조건의 값을 지워주는 역할을 수행한다.
- Predicate의 조건이 일치하는 값들을 모두 지워준다.
```java
        names.removeIf(s -> s.equals("jpa"));
        names.forEach(System.out::println);
```

### Comparator의 기본 메소드
comparator는 정렬을 할 때 주로 사용해주는 Functional Interface이다.제공해주는 기본메소드를 알아보자.

#### reversed()
- 정렬을 역순으로 해주는 기본메소드이다.
- 정렬을 역순으로 하고 싶다면 compareToIgnoreCase에 reversed()를 해주면 된다. 여기서 사용되는 reversed()는 default 메소드이다.

```java
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        //정렬하기
        names.sort(compareToIgnoreCase);
        //역순으로 정렬하기
        names.sort(compareToIgnoreCase.reversed());
```

#### thenComparing()
- 정렬을 할 때 추가 조건을 부여할 때 사용한다.
```java
        Comparator<String> ci = String::compareToIgnoreCase;
        names.sort(ci.thenComparing(Comparator.reverseOrder()));
```

### Comparator의 static 메소드
정렬을 위하여 다양한 static메소드를 제공하여준다.
- `naturalOrder()` : 오름차순으로 정렬할 때 이용한다. 파라미터로 들어가는 값은 없으며 반환형은 Comparator이다.
- `reverseOrder()` : 내림차순으로 정렬할 때 사용한다. 파라미터로 들어가는 값은 없으며 반환형은 Comparator이다.
- `nullsFirst()` : null을 처음으로 오게 정렬할 때 사용한다. 파라미터로 Comparator 받는다(naturalOrder()나 reverseOrder()를 사용하면 된다.)
- `nullsLast()` : null을 뒤로 정렬해줄 때 사용한다. 파라미터로 Comparator 받는다(naturalOrder()나 reverseOrder()를 사용하면 된다.)
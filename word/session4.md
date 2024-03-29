## Optional
## Optional이란?
- null을 리턴받아 사용하면 nullPointException이 발생한다.
- if(xx != null)와 같은 조건문으로 null을 체크해서 사용하면 해결할 수 있음. 하지만 매번 그렇게 하기가 번거로움. 
- 두번째로 null을 return받을 상황에 Exeception을 던져줄 수 있음.(이 경우는 비효율적인 리소스를 사용해야함.)
- Optional을 활용하여 null에 대한 처리를 할 수 있다.

### 어디서 쓰는가?
```java
    public Optional<Progress> getProgress() {
        // Optional로 감싸서 보내준다.
        return Optional.ofNullable(progress);
    }
```
- 되도록이면 return되는 위치에서 사용하면된다.
- 사용하면 안되는 곳 : Map의 key(Map의 key는 null값이 아님), Collection들(이미 null에 대해 체크할 수 있음)

## Optional API
Stream을 활용해 값을 꺼내게 되면 그 값은 Optional로 나오게 된다.
```java
Optional<OnlineClass> spring = springClasses.stream()
        .filter(oc -> oc.getTitle().startsWith("spring"))
        .findFirst();
```

1. isPresent(), isEmpty()
   - Optinal의 값이 있는지 없는지 확인해준다.
   - isPresent()는 만약 값이 있으면 true, 없으면 false이다.
   - isEmpty()는 isPresent()의 반대가 된다. 
```java
boolean present = spring.isPresent();
boolean empty = spring.isEmpty();
```

2. get()
    - get은 Optional에서 값을 꺼내올 때 사용한다.
    - 만약 Optional이 값을 갖고있지 않다면, runtime Error가 발생하므로 null인지 체크하여 꺼내야한다.
    - 별로 권장하지 않는 방법이다.
```java
if(spring.isPresent()){
    OnlineClass onlineClass = spring.get();
}
```

3. ifPresent(Consumer)
    - ifPresent는 Optional에 대한 값을 처리하는 방법으로 Consumer로 처리하게 된다.
    - 만약 Optinal에 값이 존재하지 않는다면 실행되지 않는다.
```java
spring.ifPresent((oc) -> System.out.println("oc.getTitle() = " + oc.getTitle()));
```

4. orElse()
    - 값을 꺼내야할 때 사용한다.
    - 파라미터로 Optional로 씌어진 값에 대한 입력을 받게 된다.
    - 만약 Optional의 값이 없다면 입력받은 파라미터로 값을 대체하여 나오게 된다.
    - 파라미터에서 new연산으로 값을 생성하는 경우에는 Optional에 값이 있더라도 생성이 되는 문제가 있다.
```java
//없거나 있을때 모두 일단 new OnlineClass(-1, "", false)는 동작함
OnlineClass onlineClass = spring.orElse(new OnlineClass(-1, "", false));
```    

5. orElseGet(Supplier)
    - 값을 꺼낼때 사용한다.
    - 파라미터로 Functional API를 사용하므로 Lazy하게 진행된다.
    - 파라미터에서 new연산으로 값을 생성하는 경우 Optional에 값이 없을때만 생성된다.
```java
OnlineClass onlineClass2 = spring.orElseThrow(IllegalArgumentException::new);
```

6. filter(), map(), flatMap()
    - stream()에서와 비슷한 역할을 수행한다.
    - map에서는 특이점으로 다른 타입으로 변환하면 그 타입을 Optional로 감싸준다.
    - map으로 원래 Optional 변수를 반환하면 Optinal이 2중으로 씌여진 값을 반환한다.
    - flatMap을 활용하면 그러한 Optional변수들을 반환할 때 Optional을 씌어주지 않는다.
```java
Optional<OnlineClass> onlineClass3 = spring.filter(o -> o.getTitle().length() < 10);
Optional<Optional<Progress>> progress = spring.map(OnlineClass::getProgress);
Optional<Progress> progress1 = spring.flatMap(OnlineClass::getProgress);
```
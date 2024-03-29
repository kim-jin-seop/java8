# Stream  
Stream은 자바8에서 가장 주목받은 기술 중 하나이다. 데이터를 병렬적으로 처리함에 있어 굉장히 효과적인 스트림에 대해 알아보겠다.

### 스트림의 특징
- 스트림은 데이터를 저장하는 저장소가 아니고, 데이터를 처리하는 방법이다. 
- 스트림을 거쳐 데이터를 처리했다고 하여 데이터가 변경되지는 않는다.
`names.stream().map(String::toUpperCase);` 이처럼 스트림을 활용해 대문자로 변경하는 코드를 작성했다고 해서, names의 데이터들이 모두 대문자로 변경되지않는다. 이 결과는 스트림으로 나오게 되며 데이터에 직접 영향을 미치지 않는다.

### 스트림의 파이프라인
- 스트림은 중계오퍼레이션과 종료 오퍼레이션으로 구성된다.
- 중계 오퍼레이션은 리턴값이 스트림이고, 종료 오퍼레이션은 스트림이 아니다.
- 스트림의 파이프라인은 1개 이상의 중계 오퍼레이션과 1개의 종료 오퍼레이션으로 이루어진다.
- 중계 오퍼레이션의 처리는 Lazy하게 일어난다. 그 의미는 종료 오퍼레이션이 실행되기 전까지 중계 오퍼레이션의 코드는 선언된 형태로 실행이 되지않고 대기한다.
```java
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
```
중계 오퍼레이션이 Lazy한 이유는 위처럼 종료 오퍼레이션이 없다면 실행이 되지 않기 때문이다.

### 스트림의 병렬처리
스트림은 병렬처리를 하는데 용이하다.
``` java
        //스트림이 없이 병렬처리를 하는 경우
        for(String name : names){
            if(name.startsWith("k")) {
                System.out.println("name = " + name.toUpperCase());
            }
            // ...
        }
```
과거 스트림이 없이 병렬처리를 하는 경우이다.  반목문을 사용하여 스트림을 처리할 수 있다.

```java
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
```
스트림으로 병렬처리는 parallelStream()을 이용하면 된다. 매우 간단하게 병렬처리를 할 수 있다. currentThread를 찍어보면 다른 쓰레드들이 병렬적으로 실행됨을 알 수 있다. 항상 병럴처리를 하는 경우가 성능이 가장 좋다고 볼 수는 없기 때문에 여러가지 고려를 하여 사용하는 것이 효과적이다.


### 스트림 API 사용
스트림과 관련된 API를 사용해보자.  

#### 중계 오퍼레이션
- filter : stream을 특정 조건으로 걸러준다. 조건의 결과가 true인 값들만 나오게 한다.
```java
        springClasses.stream()
                .filter(c -> c.getTitle().startsWith("spring"))
                .forEach(c -> System.out.println(c.getId()));
```
filter에 파라미터로 주는 것은 Predicate 이며, 해당 조건을 만족하는 것에 대한 스트림을 반환해준다.

```java
        springClasses.stream()
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(c -> System.out.println("c = " + c));
```
 만약 filter에 not 연산을하고싶다면,  Predicate의  static 메소드인  not()을 사용해주면 된다.
 
 - map : 새로운 타입의 스트림으로 변환해준다.
 ```java
         springClasses.stream()
                .map(OnlineClass::getTitle)
                .forEach(System.out::println);
 ```
 위 예시를 보면 Onclass로 받은 데이터를 Title로 변경해주었다. 그렇게 되면 forEach로 돌아가는 데이터의 타입은 Title인 String이 된다.
 
 - flapMap : 컬렉션의 데이터들을 스트림으로 변환한다.
 ```java
         List<List<OnlineClass>> events = new ArrayList<>();
        events.add(springClasses);
        events.add(javaClasses);
        
        events.stream()
                .flatMap(Collection::stream) // list -> list.stream()
                .forEach(oc -> System.out.println(oc.getId()));
 ```
 만약 List타입으로 스트림이 들어오게 되면, 리스트타입의 스트림을 쪼개어 데이터가 들어있는 스트림으로 눌러주는 듯한 역할을 수행해준다. 위 예시 events는 리스트 타입을 가진 리스트로 구성이 되어있다. 그렇다면 events의 stream은 리스트들이 될 것이다. 그런데 flatMap을 사용하면 리스트들을 OnlineClass의 스트림들로 펴주게 된다. 따라서 forEach에서 동작하는 oc는 OnlineClass가 될 것이다.
 
 - iterate(seed,func) : 무한하게 동작하는 스트림이다. seed를 주면 그것에 대한 func가 동작하며 무한히 반복을 수행한다.
 ```java
         Stream.iterate(10, i -> i + 1)
                .skip(10) //처음 10개 스킵
                .limit(10) //10개만 가져옴
                .forEach(System.out::println);
 ```
 무한반복을 억제하기 위해 limit()을 활용해 10번만 사용하도록 동작시킬 수 있다. 그리고 skip을 활용하면 처음 몇가지 데이터를 넘길 수 있다. 위 코드는 seed가 10이고, function이 i -> i + 1이므로 10부터 1씩 증가하는 것인데, 이 때 10개를 스킵하므로 20부터 29까지 총 10개가 출력될 것이다.
 
 #### 종료 오퍼레이션
 종료 오퍼레이션은 위에서 보았던 forEach()도 종료 오퍼레이션이다. 자주쓰이는 오퍼레이션 두개만 함께 알아보자.
 - match : match의 종류는 allMatch와 anyMatch가 있으며, boolean을 return해준다. allMatch는 스트림의 모든 값들이 매치될 때 true이고, anyMatch는 하나라도 매치되면 true를 반환한다.
 `boolean test = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));` 
 이 예시는 javaClasses라는 리스트에 Test라는것이 포함된 제목이 하나라도 있는지 확인해주는 것이다.
 
 - collect() : 스트림의 결과를 컬렉션 타입으로 받는다.
```java
        List<String> spring = springClasses.stream()
                .map(OnlineClass::getTitle)
                .filter(t -> t.contains("spring"))
                .collect(Collectors.toList());
 ```
 collect는 스트림의 결과를 받환받는 것이다. 컬렉션의 타입으로 반환받을 수 있다. 위 예시를 보면 springClass라는 리스트의 스트림(데이터는 OnlineClass)을 map을 활용해 String으로 변환하였고, filter를 활용해 spring이 포함되어있는 타이틀만 남겨서 List로 반환받은 것이다.
 
스트림을 배우면서 느낀점은 굉장히 간단하지만 강력하게 데이터를 처리할 수 있고, 과거 어렵게 처리하던 동작을 깔끔하게 처리할 수 있을 것 같다는 느낌을 받았다. 진작에 Java8을 공부할껄 후회가 되기도 한다..
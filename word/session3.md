## Stream  
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




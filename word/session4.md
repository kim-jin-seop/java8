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

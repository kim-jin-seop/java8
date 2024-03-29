# Thread의 기초

- 쓰레드를 만드는 방법
```java
 Thread thread = new Thread(()->{
            System.out.println("Hello Thread");
  });
```
쓰레드를 만드는 방법은 new Thread()를 할 때 Runnable 함수형 인터페이스를 넣어줌으로써 만들어줄 수 있다.

- 쓰레드 start
```java
thread.start();
```
쓰레드를 생성하여 실행하기 위해서는 start()를 사용해주면 된다.

- 쓰레드 sleep
```java
thread.sleep(1000);
```
쓰레드에서 잠시 리소스를 빼앗기 위해서 sleep을 할 수 있다. 그렇게 되면 쓰레드가 하던 일을 잠시 멈추고 리소스를 다른 쓰레드에서 넘겨주게 된다.

- 쓰레드 interrupt
```java
        Thread thread = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1000L);
                    System.out.println("쓰레드 동작중!");
                } catch (InterruptedException e) {
                    System.out.println("끝!");
                    return;
                }
            }
        });
        thread.start();
        Thread.sleep(3000L);
        thread.interrupt();
    }
```
자고있는 쓰레드에 인터럽트를 걸어주면 깨어나고 interrupt exception을 처리하게 된다.
위 예시를 보면 생성된 쓰레드가 동작하면서 계속 "쓰레드 동작중!"을 프린트해주고 있으며 interrupt가 들어온 순간 쓰레드를 종료시키는 것을 볼 수 있다.

- 쓰레드 join
```java
Thread thread = new Thread(() -> {
            Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println("쓰레드 동작완료");
        });
        thread.start();

        System.out.println("Main!");
        thread.join();
        System.out.println("finish thread");
```
- join()을 사용하면 해당 쓰레드가 종료될 때 까지 기다린다. 아래의 finish thread가 join()이 있기 때문에 쓰레드가 종료된 이후에 실행이 된다.
- 만약 join()이 없다면 출력의 순서가 'Main! -> finish thread -> 쓰레드 동작완료' 로 동작할 수 있다.  
- 하지만 join()이 있기 때문에 'finish thread'와 '쓰레드 동작완료'의 순서가 항상 '쓰레드 동작완료 -> finish thread'가 된다.

## Excutors
엄청나게 많은 쓰레드를 개발자가 관리하는 것은 사실상 불가능하다. 따라서 Executor를 활용하여 쓰레드를 관리하게 된다.
Executer는 ExcutorServie와 ScheduledExecutorService가 있다. ExcutorService는 쓰레드를 만들어주고 동작하는 역할만 수행한다면, ScheduledExecutorService는 쓰레드들을 각자 얼마나 뒤에 실행할지를 입력할 수 있어, 스케쥴링하여 실행해준다.

###  ExecutorService
```java 
//사용되는 runnable 메소드
	private static Runnable getRunnable(String message) {
        	return () -> {
            	System.out.println(message + ":" + Thread.currentThread().getName());
        	};
    	}
```

```java
	ExecutorService executorService = Executors.newFixedThreadPool(2);

        //작업 처리 후 종료 안됨 -> 다음 작업 올 때 까지 계속 대기함
        executorService.submit(getRunnable("hello"));
        executorService.submit(getRunnable("my"));
        executorService.submit(getRunnable("name"));
        executorService.submit(getRunnable("is"));
        executorService.submit(getRunnable("jinseop"));

        //쓰래드를 직접 종료해주어야함.
        executorService.shutdown();
```
- ExcutorService를 생성하는 방법은 Excutors를 활용하면 된다. newFixedThreadPool()을 사용해서 파라미터로 쓰레드의 개수를 입력받게 된다.  
- 그리고 쓰레드가 처리할 일은 submit으로 Runnable functional Interface를 받아서 처리가 이루어진다. 
- 현재 보유하고 있는 쓰레드는 2개인데 실행되는 작업이 5개이다. 이것이 가능한 이유는 Blocking Queue에 작업을 넣어두고 쓰레드 두개가 작업을 꺼내며 처리하기 때문이다.
- 쓰레드풀을 보유하고 있어 따로 쓰레드를 만들어줄 필요가 없으니 자원적인 효율성도 좋다.
- 쓰레드로 작업을 모두 완료하였다면 마지막에 직접 executorService를 종료시켜주어야한다.

### ScheduledExecutorService
``` java
	ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(getRunnable("hello"),3, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(getRunnable("world"),1,TimeUnit.SECONDS);
        
        //반복 실행 1초 뒤 실행 2초 간격으로 반복실
        scheduledExecutorService.scheduleAtFixedRate(getRunnable("hello!"),1,2, TimeUnit.SECONDS);
```
- ScheduledExecutorService를 하나의 쓰레드만 사용하는 스케쥴러로 선언해주었다.
- schedule()에 작업을 정의해준다. Runnable을 넣어주어 쓰레드의 동작을 정의하고, 몇초 뒤에 처리할 지 입력하여주기만 하면 실행이 된다.  
- 위 예시에서 보면 world는 1초, hello는 3초의 딜레이가 있으므로 world 다음에  hello가 출력될 것이다.
- 만약 실행을 반복처리하고싶으면 scheduleAtFixedRate()를 사용하면 된다.  
- 위 예시의 쓰레드를 살펴보면 "hello!"를 출력하는 쓰레드를 생성하는데 1초 뒤부터 실행되며 2초마다 동작을 하도록 구현한 것이다.

## Callable과 Future
Runnable로 동작을 실행할 때 return받는 값이 없다. 따라서 쓰레드를 처리하고 그 결과로 값을 얻어야한다면 Runnable을 사용하면 안된다.  
Callable은 제너릭타입을 리턴타입으로 갖고 있기 때문에 return타입이 필요하다면 Callable사용하면  된다.

Future는 이렇게 동작하는 비동기 작업의 현재상태를 조회하거나 결과를 가져올 수 있는 역할을 수행한다.
```java
	ExecutorService ex = Executors.newFixedThreadPool(3);
	
	Callable<String> hello = () -> {
            Thread.sleep(5000L);
            return "Hello";
        };
        
	Future<String> future = ex.submit(hello);
        System.out.println(future.isDone());
        System.out.println("Stated!");

        //future.get();
        future.cancel(false);

        System.out.println(future.isDone());
        System.out.println("End!");
```
- Callable을 정의하여주고, 동일하게 submit()을 사용하여 실행해줄 수 있다.  
- 이 때 실행하는 것의 상태나 결과를 조회할 수 있게 future를 받을 수 있다. 
- 현재 작업이 완료되었는지는 future의 isDone()으로 알 수 있으며 만약 작업이 완료되었다면 true 아직 완료되지 않았다면 false를 리턴해준다.
- 결과를 조회하는 방법은 future.get()을 사용해주면 되며 이 때 최종 결과가 나올 때 까지 블로킹이 된다. 
- 만약 쓰레드의 작업을 취소하고 싶으면 cancel()을 수행하면 되며 cancel()에 파라미터로 true가 오면 interrupt를 날리고 중지하고, false가 오면 모두 실행 후 중지를 해준다.
- cancel을 하면 get()으로 가져올 수 없다.

### 여러개의 Callable 처리
동시에 여러개의 Callable을 처리할 수 있다.
```java
ExecutorService ex = Executors.newFixedThreadPool(3);

        Callable<String> hello = () -> {
            Thread.sleep(5000L);
            return "Hello";
        };
        Callable<String> world = () -> {
            Thread.sleep(3000L);
            return "world";
        };
        Callable<String> nice = () -> {
            Thread.sleep(1000L);
            return "nice";
        };

        List<Future<String>> futures = ex.invokeAll(Arrays.asList(hello, world, nice));
        for(Future<String > future : futures){
            System.out.println("future = " + future.get());
        }
```
- invokeAll()을 사용해주면 여러개의 Callable을 처리할 수 있다.
- 동작 과정은 모든 Callable에 대한 작업을 처리하게 된다.
- 그리고 나서 처리된 작업으로 결과를 보여주게 된다.
- 즉 모든 작업을 처리하고 나서 뒤에 결과를 주는 것이 **invokeAll()**이다.


```java
ExecutorService ex = Executors.newFixedThreadPool(3);

        Callable<String> hello = () -> {
            Thread.sleep(5000L);
            return "Hello";
        };
        Callable<String> world = () -> {
            Thread.sleep(3000L);
            return "world";
        };
        Callable<String> nice = () -> {
            Thread.sleep(1000L);
            return "nice";
        };
        
       String s = ex.invokeAny(Arrays.asList(hello, world, nice));
```
- invokeAny()로 처리를 하게 되면 Callable중 하나라도 완료가 되면 그 결과 값을 반환받게 된다.
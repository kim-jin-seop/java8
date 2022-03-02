package session6;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /**
         * CompletableFuture
         */

        /**
         * Thread : runnable(FuncAPI을 new로 받아서 생성)
         * thread.start
         * thread.sleep
         * thread.interrupt
         * thread.join
         */
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                return;
            }
//            System.out.println("쓰레드 동작완료");
        });
        thread.start();

//        System.out.println("Main!");
        thread.join();
//        System.out.println("finish " + thread);

        /**
         * Executors
         * blocking Queue
         */
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //작업 처리 후 종료 안됨 -> 다음 작업 올 때 까지 계속 대기함
        executorService.submit(getRunnable("hello"));
        executorService.submit(getRunnable("my"));
        executorService.submit(getRunnable("name"));
        executorService.submit(getRunnable("is"));
        executorService.submit(getRunnable("jinseop"));

        //쓰래드를 직접 종료해주어야함.
        executorService.shutdown();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(getRunnable("hello"),3, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(getRunnable("world"),1,TimeUnit.SECONDS);
        //반복 실행 1초 뒤 실행 2초 간격으로 반복실행
        scheduledExecutorService.scheduleAtFixedRate(getRunnable("hello!"),1,2, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
        /**
         * Callable과 future
         */
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
        String s = ex.invokeAny(Arrays.asList(hello, world, nice));
        System.out.println("s = " + s);
        for(Future<String > future : futures){
            System.out.println("future = " + future.get());
        }

        /**
         * future로 처리 기본
         */
        Future<String> future = ex.submit(hello);
        System.out.println(future.isDone());
        System.out.println("Stated!");

        future.get();
        //future.cancel(false);

        System.out.println(future.isDone());
        System.out.println("End!");
        ex.shutdown();
    }

    private static Runnable getRunnable(String message) {
        return () -> {
            System.out.println(message + ":" + Thread.currentThread().getName());
        };
    }


}

package session7;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> future = new CompletableFuture<>();
//        future.complete("jins");
//        future.get();

        /**
         * 비동기 처리를 위한 콜백 함수들 비동기적인 처리를 하기 용이해짐
         * thenApply
         */
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("run Async");
//            return "Hello";
//        }).thenApply((s)->{ //get()전에 작업을 할 수 있음.
//            System.out.println("s = " + s);
//            return s.toUpperCase();
//        });
//        System.out.println(future.get());

        /**
         * thenAccept
         * Consumer가 옴
         */
        CompletableFuture.supplyAsync(() -> {
            System.out.println("run Async");
            return "Hello";
        }).thenAccept((s)->{ //get()전에 작업을 할 수 있음.
            System.out.println("s = " + s.toUpperCase());
        }).get();

        /**
         * thenRun
         * 결과값을 참고하여 못함 -> Runnable이 옴
         */
        CompletableFuture.supplyAsync(() -> {
            System.out.println("run Async");
            return "Hello";
        }).thenRun(()->{ //get()전에 작업을 할 수 있음.
            System.out.println("Runnable");
        }).get();

        /**
         * 조합
         */
        //Hello 뒤 World를 수행해줘야하는 경우 두 Future가 연관관계가 있는 경우
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("run Hello");
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("run World");
            return "World";
        });

        CompletableFuture<String> future = hello.thenCompose(App::getWorld);
        System.out.println(future.get());


        //두개 이상일 때 조합하는 법
        //두개의 순서가 있는 경우
        System.out.println(hello.thenCombine(world,(h,w) ->h + " " + w).get());

        //두개의 순서가 없는경우 -> 모든 경우를 수행할 때
        List<CompletableFuture<String>> futures = Arrays.asList(hello, world);
        CompletableFuture[] futuresArr = {hello, world};

        CompletableFuture<List<String>> results = CompletableFuture.allOf(futuresArr)
                .thenApply(s -> {
                    return futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                });
        results.get().forEach(System.out::println);

        //모든 경우중 가장 빠른거 가져오기
        CompletableFuture<Void> future3 = CompletableFuture.anyOf(hello, world).thenAccept((s) -> {
            System.out.println(s);
        });
        future3.get();

        /**
         * 예외 처리
         * exceptionally -> 에러 처리
         * handle -> bi func들어옴 첫번째 매개변수 : 정상적인 결과 두번째 매개변수 : 에러
         */
//        boolean throwError = true;
//        CompletableFuture<String> name = CompletableFuture.supplyAsync(()->{
//            if(throwError){
//                throw new IllegalArgumentException();
//            }
//
//            return "jinseop";
//        }).exceptionally(ex ->{
//            System.out.println(ex);
//            return "Error!"; // future의 결과로 Error!가 들어옴
//        });

        boolean throwError = true;
        CompletableFuture<String> name = CompletableFuture.supplyAsync(()->{
            if(throwError){
                throw new IllegalArgumentException();
            }

            return "jinseop";
        }).handle((result, ex) ->{
            if(ex != null){
                System.out.println(ex);
                return "Error!";
            }
            return result;
        });

        System.out.println(name.get());
    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("run World");
            return message + " World";
        });
    }
}

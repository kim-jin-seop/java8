package session8;

import java.util.Arrays;
import java.util.List;

@Chicken("양념")
@Chicken("마늘")
@Chicken("후라이드")
public class App {
    /**
     * Annotation의 변화
     * 1. 타입 선언부에 사용할 수 있게 됨
     * 2. 중복 사용가능
     */
//    @Chicken
//    public static void main(@Chicken String[] args) throws @Chicken RuntimeException {
//        List<@Chicken String> names = Arrays.asList("jinseop");
//    }
//
//    static class FeelsLikeChicken<@Chicken T>{
//
//        public static <C> void print(C c){ // 앞에 C는 타입파라미터
//
//        }
//
//    }
    public static void main(String[] args) {
        /**
         * 여러개의 Annotation 중복 사용해보기 1
         */
        Chicken[] chickens = App.class.getAnnotationsByType(Chicken.class);
        Arrays.stream(chickens).forEach(c -> {
            System.out.println(c.value());
        });

        /**
         * 여러개의 Annotation 중복 사용해보기 2
         */
        ChickenContainer chickenContainer = App.class.getAnnotation(ChickenContainer.class);
        Arrays.stream(chickenContainer.value()).forEach(c -> {
            System.out.println(c.value());
        });
    }
}

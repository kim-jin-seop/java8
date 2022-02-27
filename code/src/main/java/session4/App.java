package session4;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App{
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        /**
         * 1. return값으로만 쓰는것을 권장
         * 2. Map의 key타입에는 쓰면 안됨(Map의 전제조건이 key값이 null이면 안됨)
         * 3. Collection을 optional로 감싸지 않아도 됨.
         */
        OnlineClass springBoot = new OnlineClass(1, "spring boot", true);

        /**
         * Stream에서 값을 꺼내면 Optional로 나오게 됨.
         */
        Optional<OnlineClass> spring = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        /**
         * isPresent():Optional이 null인지 확인하는 방법
         * true : null이 아님, false : null임
         */
        boolean present = spring.isPresent();

        /**
         * isEmpty() : null인지 확인하는 방법
         */
        boolean empty = spring.isEmpty();

        /**
         * get() : Optional에서 값을 가져온다.
         * Optional을 단순히 벗겨내는 작업으로 null일 경우 RunTime Error가 발생한다.
         * 따라서 null값이 아닌지 확인하고 사용해야한다.
         *
         * 별로 추천하지 않는 방법이며 다른 API를 사용해 커버할 수 있으므로 다른 API를 사용을 권장한다.
         */
        if(spring.isPresent()){
            OnlineClass onlineClass = spring.get();
        }

        /**
         * ifPresent(Consumer) : 값이 존재하면 Consumer에서 소비할 수 있음
         * Optional에서 값을 꺼내는 작업 없이 Consumer를 사용하여 처리해주면 됨
         */
        spring.ifPresent((oc) -> System.out.println("oc.getTitle() = " + oc.getTitle()));

        /**
         *  orElse(x) : 값을 꺼내고 싶으면 orElse를 사용해서 꺼낼 수 있다.(get 대신)
         *  이 때 x로 받는 값은 만약 값이 없을경우 x값으로 return 받아진다.
         *  x로 입력받은 값은 반드시 사용이 됨. (LAZY하지 않음)
         *  만약 new연산으로 새롭게 생성할 경우 만약 값이 있어도 새롭게 생성은 됨
         */
        //없거나 있을때 모두 일단 new OnlineClass(-1, "", false)는 동작함
        OnlineClass onlineClass = spring.orElse(new OnlineClass(-1, "", false));

        /**
         * orElseGet(Supplier) : 값을 꺼내고 싶을 때 사용한다.
         * Supplier를 활용하면 Lazy하게 사용할 수 있음.
         * new 연산을 해도 없는 경우만 생성하게 됨.
         */
        //없는 경우만 실행 됨
        OnlineClass onlineClass1 = spring.orElseGet(() -> new OnlineClass(-1, "", false));

        /**
         * orElseThrow(Supplier) : 값을 꺼낼 때 사용
         * 값이 없을 때 원하는 에러를 골라서 던질 수 있음.
         */
        OnlineClass onlineClass2 = spring.orElseThrow(IllegalArgumentException::new);

        /**
         * filter() : 특정 조건을 만족하는 Optional만 남긴다.
         * stream과 동일하게  filter를 제공하며 결과값은 동일하게 Optional이 된다.
         */
        Optional<OnlineClass> onlineClass3 = spring.filter(o -> o.getTitle().length() < 10);

        /**
         * map() : 다른 타입으로 매핑하여준다.
         * Stream과 동일하게 다른 타입으로 변경할때 사용한다.
         * 이 때 변경된 타입은 동일하게 Optional이 된다. 즉 String값으로 넘기면 Optinal<String>이 된다.
         * 만약 원래 Optional인 값을 넘기면 굉장히 복잡해진다. Optinal에 Optinal이 된다.
         */
        Optional<Optional<Progress>> progress = spring.map(OnlineClass::getProgress);

        /**
         * flapMap() : 타입을 변경할 때 사용한다.
         * flatMap을 사용해주면 Optinal로 이미 감싸진 타입으로 변환할 때 사용한다.
         * 감싸진 Optional을 까서 return 해준다.
         */
        Optional<Progress> progress1 = spring.flatMap(OnlineClass::getProgress);
    }
}

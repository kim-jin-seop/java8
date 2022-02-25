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

    }
}

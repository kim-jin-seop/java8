package session8;

import java.lang.annotation.*;


//@Target(ElementType.TYPE_PARAMETER) // Generic Type으로 선언 가능
@Target(ElementType.TYPE_USE) // Type선언하는 모든 곳에서 사용
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ChickenContainer.class)
public @interface Chicken {
    String value();
}

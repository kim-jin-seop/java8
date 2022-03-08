package session8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retention과 Target정보는 자기 자신이 감쌀 Annotation보다 같거나 커야함
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChickenContainer {

    Chicken[] value();
}

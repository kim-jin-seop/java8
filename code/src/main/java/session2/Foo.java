package session2;

public interface Foo {
    void printName();

    String getName();

    /**
     * default 메소드는 인터페이스를 가져다 쓰는 구현체에 모두 공통으로 제공할 수 있는 기능을 디폴트로 해주는 것을 의미한다.
     * 이 메소드를 정의할 때는 주의해야하는 사항과 특징이 있다.
     * 1. 개발자가 의도한대로 동작하지 않을 수 있다. 사람들이 getName()을 어떻게 정의하냐에 따라 원하지 않게 동작할 수 있다. -> @implSpec을 활용해 스펙 명시
     * 2. Object가 제공하는 메소드는 default로 재정의할 수 없다. -> 예를들어 toString()과 같은 메소드는 default로 재 정의는 불가하다.
     * 3. interface에 오버라이딩하여 사용하는 경우에 만약 default를 사용하기 싫으면 추상메소드로 재 정의해주면 된다.
     * 4. 만약 두개의 interface를 받아 사용할 때 동일한 이름의 default가 있다면 에러가 발생한다.
     *
     * @implSpec getName()의 값을 대문자로 변환해 줄력해주는 메소드
     */
    default void printUpperName(){
        System.out.println(getName().toUpperCase());
    }

    /**
     * interface에서 static 메소드를 구현하여 사용할 수 있다.
     */
    static void helloStatic(){
        System.out.println("Hello Static Method in interface");
    }
}

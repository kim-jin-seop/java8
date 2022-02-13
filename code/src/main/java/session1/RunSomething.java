package session1;

@FunctionalInterface
public interface RunSomething {
    //추상 메소드가 하나만 있으면 함수형 인터페이스
    abstract int doIt(int number);

//    static void printName(){
//        System.out.println("jinseop");
//    }
//
//    default void printAge(){
//        System.out.println("26");
//    }
}

package session2;

public class App {
    public static void main(String[] args) {
        Foo foo = new ImplFoo("aa");
        foo.printName();

        // default method 사용
        foo.printUpperName();

        Foo.helloStatic();
    }
}

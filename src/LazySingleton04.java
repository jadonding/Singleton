/**
 * 静态内部类方式
 * LazySingleton04创建的时候，静态内部类LazySingleton04Holder不会被加载
 * 只有调用了getInstance时才会加载LazySingleton04Holder，创建INSTANCE
 * 由JVM保证INSTANCE只加载了一次
 * 实现了懒汉式（按需初始化）
 * 优点：可以按需求初始化，可以多线程且没有线程不安全的问题
 * @author Jadon
 * @create 2020-08-17-14:08
 */
public class LazySingleton04 {
    private LazySingleton04(){

    }

    private static class LazySingleton04Holder {
        private final static LazySingleton04 INSTANCE = new LazySingleton04();
    }

    public static LazySingleton04 getInstance(){
        return LazySingleton04Holder.INSTANCE;
    }

    public void drink(){
        System.out.println("喝水！");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()-> System.out.println(LazySingleton04.getInstance())).start();
        }
    }
}

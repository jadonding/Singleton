/**
 * 懒汉式三
 * 在懒汉式二的基础上减少了同步代码块，
 * 优点：可以按需求初始化，线程不安全问题可以解决，效率相比懒汉式二会有所提高
 * @author Jadon
 * @create 2020-08-17-12:21
 */
public class LazySingleton03 {
    //1. 定义一个静态的实例，不加final，可以修改
    private static volatile LazySingleton03 INSTANCE = null;
    //2. 构造方法设置成private——>在别处new不出来
    private LazySingleton03(){
    }

    public static LazySingleton03 getInstance(){

        if(INSTANCE == null) {
            // 双重检查
            // 线程C执行完了if(INSTANCE == null)得到false，接下来的代码就不会执行了
            synchronized (LazySingleton03.class) {
                if(INSTANCE == null) {
                    // 双重检查
                    // 假设线程A与线程B都执行完了if(INSTANCE == null)
                    // 线程A抢先一步拿到了锁，就只有等它创建好了INSTANCE后才会把锁释放
                    // 线程B拿到锁之后，就只有判断if(INSTANCE == null)之后才能执行接下来代码
                    // 由于此时INSTANCE ！= null，后面的代码就不会执行了，
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new LazySingleton03();
                }
            }
        }
        return INSTANCE;
    }
    public void drink(){
        System.out.println("喝水");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()-> System.out.println(LazySingleton03.getInstance())).start();
        }
    }
}

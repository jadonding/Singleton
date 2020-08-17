/**
 * 懒汉式二
 * 在懒汉式一的基础上加了一个锁
 * 优点：可以按需求初始化，线程不安全问题可以解决
 * 问题：效率会降低
 * @author Jadon
 * @create 2020-08-17-12:21
 */
public class LazySingleton02 {
    //1. 定义一个静态的实例，不加final，可以修改
    private static LazySingleton02 INSTANCE = null;
    //2. 构造方法设置成private——>在别处new不出来
    private LazySingleton02(){
    }

    /**
     * 3. 想在外面（比如Main类中）用HungrySingleton02的实例，只能调用getInstance()方法
     * 但是这里有线程安全问题：
     * 假设某时刻线程A已经执行到 if(INSTANCE == null) 判断好了
     * 此时线程B也进入了getInstance()方法，也执行到了 if(INSTANCE == null) 判断好了
     * 然后他们都执行了new LazySingleton01()
     * 执行完后都返回了一个INSTANCE,此时两个返回的INSTANCE不是同一个INSTANCE
     * @return
     */
    //4. 我们加个锁，这样线程安全问题可以解决，但是效率会降低
    public static synchronized LazySingleton02 getInstance(){
        if(INSTANCE == null){
            // 用多线程进行测试，调用sleep()方法目的是为了使现象更明显，
            // 让一个线程执行完 if(INSTANCE == null) 后稍微等待一下
            // 等待其他线程进入，让出现线程不安全问题的现象更加明显
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new LazySingleton02();
        }
        return INSTANCE;
    }
    public void drink(){
        System.out.println("喝水");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    //输出每个线程getInstance后的地址值
//                    System.out.println(LazySingleton02.getInstance());
//                }
//            }).start();
//            下面是JDK1.8 Lambda表达式的写法
            new Thread(()-> System.out.println(LazySingleton02.getInstance())).start();
        }
    }
}

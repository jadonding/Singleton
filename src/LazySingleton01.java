/**
 * 懒汉式一
 * 优点：可以按需求初始化
 * 问题：出现了线程不安全的问题
 * @author Jadon
 * @create 2020-08-17-12:21
 */
public class LazySingleton01 {
    //1. 定义一个静态的实例，不加final，可以修改
    private static LazySingleton01 INSTANCE = null;
    //2. 构造方法设置成private——>在别处new不出来
    private LazySingleton01(){
    }

    /**
     * 3. 想在外面（比如Main类中）用HungrySingleton01的实例，只能调用getInstance()方法
     * 但是这里有线程安全问题：
     * 假设某时刻线程A已经执行到 if(INSTANCE == null) 判断好了
     * 此时线程B也进入了getInstance()方法，也执行到了 if(INSTANCE == null) 判断好了
     * 然后他们都执行了new LazySingleton01()
     * 执行完后都返回了一个INSTANCE,此时两个返回的INSTANCE不是同一个INSTANCE
     * @return
     */
    public static LazySingleton01 getInstance(){
        if(INSTANCE == null){
            // 用多线程进行测试，调用sleep()方法目的是为了使现象更明显，
            // 让一个线程执行完 if(INSTANCE == null) 后稍微等待一下
            // 等待其他线程进入，让出现线程不安全问题的现象更加明显
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new LazySingleton01();
        }
        return INSTANCE;
    }
    public void drink(){
        System.out.println("喝水");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //输出每个线程getInstance后的地址值
                    System.out.println(LazySingleton01.getInstance());
                }
            }).start();
            //下面的注释是JDK1.8 Lambda表达式的写法
            //new Thread(()-> System.out.println(LazySingleton01.getInstance())).start();
        }
    }

}

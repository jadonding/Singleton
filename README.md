# Singleton
Java单例模式的七种写法
#### (1) 饿汉式一

* 类加载到内存后，就实例化一个单例，JVM保证线程安全，简单实用，推荐使用
* 唯一缺点：不管用到与否，类加载到内存就完成实例化

```java
/**
 * 饿汉式一
 * 类加载到内存后，就实例化一个单例，JVM保证线程安全
 * 简单实用，推荐使用
 * 唯一缺点：不管用到与否，类加载到内存就完成实例化
 *
 * @author Jadon
 * @create 2020-08-17-11:49
 */
public class HungrySingleton01 {
    //1. 定义一个静态的实例，INSTANCE = new HungrySingleton01()
    private static final HungrySingleton01 INSTANCE = new HungrySingleton01();

    //2. 构造方法设置成private——>在别处new不出来
    private HungrySingleton01() {
        //想在外面（比如Main类中）用HungrySingleton01的实例，只能调用getInstance()方法
        //例如：HungrySingleton01 hs1 = HungrySingleton01.getInstance();
    }

    public void eat() {
        System.out.println("吃饭！");//业务方法，根据需求写，这里随意
    }

    public static HungrySingleton01 getInstance() {
        return INSTANCE;
        //因为这里return的永远都只是这一个INSTANCE
    }

    public static void main(String[] args) {
        HungrySingleton01 h1 = HungrySingleton01.getInstance();
        HungrySingleton01 h2 = HungrySingleton01.getInstance();
        //判断h1和h2是否指向一个地址
        System.out.println(h1 == h2);//true：h1和h2是一个实例
    }

}
```

#### (2) 饿汉式二

* 同饿汉式一几乎一样，不过是用静态代码块初始化实例

```java
public class HungrySingleton02 {
    //1. 定义一个静态的实例INSTANCE
    private static final HungrySingleton02 INSTANCE;
    //2. 通过静态语句块初始化
    static {
        INSTANCE = new HungrySingleton02();
    }

    //3. 构造方法设置成private——>在别处new不出来，就只能调用下面的getInstance()方法
    private HungrySingleton02() {
    }

    public void eat() {
        System.out.println("吃饭！");//这里随意
    }

    public static HungrySingleton02 getInstance() {
        return INSTANCE;
        //因为这里return的永远都只是这一个INSTANCE
    }

    public static void main(String[] args) {
        HungrySingleton02 m1 = HungrySingleton02.getInstance();
        HungrySingleton02 m2 = HungrySingleton02.getInstance();
        //判断m1和m2是否指向一个地址
        System.out.println(m1 == m2);//true：m1和m2是一个实例
    }
}
```

#### (3) 懒汉式一

* 优点：可以按需求初始化

* 问题：出现了线程不安全的问题

```java
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
            INSTANCE = new LazySingleton01();
        }
        return INSTANCE;
    }
    public void drink(){
        System.out.println("喝水");
    }

}
```

上面的线程不安全的情况可以用多线程进行测试体现出来

```java
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
```

输出的结果如下图所示，输出的地址值不相同，可知得到了很多个不同的`INSTANCE`

![image-20200817130200949](C:\Users\15460\AppData\Roaming\Typora\typora-user-images\image-20200817130200949.png)

#### (4) 懒汉式二

* 在懒汉式一的基础上加了一个锁

 * 优点：可以按需求初始化，线程不安全问题可以解决
 * 问题：效率会降低

```java
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
```

#### (5) 懒汉式三

* 在懒汉式二的基础上减少了同步代码块，
 * 优点：可以按需求初始化，线程不安全问题可以解决，效率相比懒汉式二会有所提高

```java
/**
 * 懒汉式三
 * 在懒汉式二的基础上减少了同步代码块，
 * 优点：可以按需求初始化，线程不安全问题可以解决，效率相比懒汉式二会有所提高
 * @author Jadon
 * @create 2020-08-17-12:21
 */
public class LazySingleton03 {
    //1. 定义一个静态的实例，不加final，可以修改
    private static LazySingleton03 INSTANCE = null;
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
```

#### (6) 懒汉式四

 * 静态内部类方式
 * LazySingleton04创建的时候，静态内部类LazySingleton04Holder不会被加载
 * 只有调用了getInstance()时才会加载LazySingleton04Holder，创建INSTANCE
 * 由JVM保证INSTANCE只加载了一次
 * 实现了懒汉式（按需初始化）
 * 优点：可以按需求初始化，可以多线程且没有线程不安全的问题

```java
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
```

#### (7) 懒汉式五

 * 枚举单例
 * 不仅解决了线程同步问题，还可以防止反序列化

```java
/**
 * 懒汉式五
 * 枚举单例
 * 不仅解决了线程同步问题，还可以防止反序列化
 * @author Jadon
 * @create 2020-08-17-14:21
 */
public enum LazySingleton05 {
    INSTANCE;
    public void drink(){
        System.out.println("喝水！");
    }
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()-> System.out.println(LazySingleton05.INSTANCE)).start();
        }
    }
}
```

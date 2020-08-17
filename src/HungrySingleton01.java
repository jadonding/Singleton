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

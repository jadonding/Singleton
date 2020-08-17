/**
 * 饿汉式二
 * 同饿汉式一意思几乎一样，不过是用静态代码块初始化实例
 * @author Jadon
 * @create 2020-08-17-12:08
 */
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

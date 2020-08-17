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

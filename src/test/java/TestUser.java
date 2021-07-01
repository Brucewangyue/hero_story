import java.util.concurrent.atomic.AtomicInteger;

public class TestUser {
    public int hp;

    /**
     * 原子操作类型解决高并发单个属性值被修改的问题
     * 但是解决不了整体User对象的高并发问题
     */
    public AtomicInteger safeHp;

    synchronized public void subHp(int num) {
        hp = hp - num;
    }

    synchronized public void subUserHp(TestUser user, int num) {
        user.subHp(num);
    }
}

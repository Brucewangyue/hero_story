public class MultiThreadProblem {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            System.out.println("第 " + i + " 次：");
            test3();
        }
    }

    /**
     * 多线程访问修改对象普通属性，存在多线程问题
     *
     * @throws Exception
     */
    private static void test1() throws Exception {
        Thread thread1 = null;
        Thread thread2 = null;

        TestUser user = new TestUser();
        user.hp = 100;

        for (int i = 0; i < 2; i++) {
            thread1 = new Thread(() -> {
                user.hp = user.hp - 10;
            });
            thread2 = new Thread(() -> {
                user.hp = user.hp - 10;
            });
        }

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        if (user.hp == 80)
            System.out.println("结果为：" + user.hp);
        else
            throw new Exception("结果为：" + user.hp);

    }

    /**
     * 通过 synchronized 加锁解决高并发问题
     * <p>
     * 存在新问题：如果存在多个加锁方法嵌套调用，存在死锁问题
     *
     * @throws Exception
     */
    private static void test2() throws Exception {
        Thread thread1 = null;
        Thread thread2 = null;

        TestUser user = new TestUser();
        user.hp = 100;

        for (int i = 0; i < 2; i++) {
            thread1 = new Thread(() -> {
                user.subHp(10);
            });
            thread2 = new Thread(() -> {
                user.subHp(10);
            });
        }

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        if (user.hp == 80)
            System.out.println("结果为：" + user.hp);
        else
            throw new Exception("结果为：" + user.hp);

    }

    /**
     * 通过 synchronized 加锁解决高并发问题
     * <p>
     * 存在新问题：演示死锁
     *
     * @throws Exception
     */
    private static void test3() throws Exception {
        Thread thread1 = null;
        Thread thread2 = null;

        TestUser user = new TestUser();
        user.hp = 100;
        TestUser user2 = new TestUser();
        user.hp = 100;

        for (int i = 0; i < 2; i++) {
            thread1 = new Thread(() -> {
                user.subUserHp(user2, 10);
            });
            thread2 = new Thread(() -> {
                user2.subUserHp(user, 10);
            });
        }

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("结果为：" + user.hp);
    }


}

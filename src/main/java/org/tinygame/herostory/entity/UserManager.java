package org.tinygame.herostory.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    /**
     * 用户Id 用户
     */
    static private final Map<Integer, User> userMap = new HashMap<>();

    private UserManager() {
    }

    /**
     * @param userId
     * @return
     */
    static public User getById(int userId) {
        return userMap.get(userId);
    }

    /**
     * 所有用户列表
     *
     * @return
     */
    static public Collection<User> list() {
        return userMap.values();
    }

    /**
     * 添加用户
     *
     * @param user
     */
    static public void add(User user) {
        userMap.put(user.getUserId(), user);
    }

    /**
     * 移除用户
     * @param userId
     */
    static public void removeById(int userId) {
        userMap.remove(userId);
    }
}

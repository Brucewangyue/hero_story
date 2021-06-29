package org.tinygame.herostory.entity;

public class User {
    private int userId;
    private String HeroAvatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeroAvatar() {
        return HeroAvatar;
    }

    public void setHeroAvatar(String heroAvatar) {
        HeroAvatar = heroAvatar;
    }
}

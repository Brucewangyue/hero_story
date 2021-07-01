package org.tinygame.herostory.entity;

public class User {
    private int userId;
    private String HeroAvatar;
    private MoveState moveState = new MoveState();

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

    public MoveState getMoveState() {
        return moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }
}

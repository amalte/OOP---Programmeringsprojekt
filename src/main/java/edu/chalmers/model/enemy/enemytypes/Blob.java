package edu.chalmers.model.enemy.enemytypes;

/**
 * Blob class. A type of Enemy.
 */
public class Blob implements IEnemyType {

    @Override
    public String getTextureIdle() {
        return "BlobSpriteIdle.png";
    }

    @Override
    public String getTextureWalk() {
        return "BlobSpriteWalk.png";
    }

    @Override
    public String getTextureJump() {
        return "BlobSpriteJump.png";
    }

    @Override
    public int getHealth() {
        return 100;
    }

    @Override
    public int getDamage() {
        return 15;
    }

    @Override
    public int getBlockDamage() {
        return 20;
    }

    @Override
    public int getMoveSpeed() {
        return 80;
    }

    @Override
    public int getJumpHeight() {
        return 275;
    }
}

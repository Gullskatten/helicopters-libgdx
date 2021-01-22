package no.ntnu.espegu.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.UUID;

public abstract class Sprite {
    final UUID uuid;
    float x;
    float y;
    float height;
    float width;

    public Sprite(float x, float y, float height, float width) {
        uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Sprite() {
        // Empty default constructor
        uuid = UUID.randomUUID();
    }

    abstract void update(Sprite... siblings);

    abstract void render(SpriteBatch batch, float elapsedTime);

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public UUID getUuid() {
        return uuid;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}

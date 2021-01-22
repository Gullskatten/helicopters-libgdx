package no.ntnu.espegu.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import no.ntnu.espegu.CurrentDirectionX;
import no.ntnu.espegu.CurrentDirectionY;

public class HelicopterSprite extends Sprite {

    public static final int HELICOPTER_WIDTH = 300;
    public static final int HELICOPTER_HEIGHT = 150;

    private final TextureRegion[] frames = new TextureRegion[]{
            new TextureRegion(new Texture("cop1.png")),
            new TextureRegion(new Texture("cop2.png")),
            new TextureRegion(new Texture("cop3.png")),
            new TextureRegion(new Texture("cop4.png")),
    };
    private final Animation<TextureRegion> animation = new Animation<>(1f / 10f, frames);
    private final float xMax;
    private final float yMax;
    private final float speed;
    private float xScale = -1;
    private CurrentDirectionY currentDirectionY;
    private CurrentDirectionX currentDirectionX;
    private int numConcurrentCollisions = 0;

    public HelicopterSprite(float xMax, float yMax, float x, float y, float speed) {
        super(x, y, HELICOPTER_HEIGHT, HELICOPTER_WIDTH);
        this.xMax = xMax;
        this.yMax = yMax;
        Random random = new Random();
        currentDirectionX = random.nextInt(2) >= 1 ? CurrentDirectionX.RIGHT : CurrentDirectionX.LEFT;
        currentDirectionY = random.nextInt(2) >= 1 ? CurrentDirectionY.DOWN : CurrentDirectionY.UP;
        this.speed = speed;
    }

    @Override
    public void render(SpriteBatch batch, float elapsedTime) {
        int rotationOffset = currentDirectionY == CurrentDirectionY.UP ? 10 : -10;
        batch.draw(
                animation.getKeyFrame(elapsedTime, true),
                x,
                y,
                200f,
                100f,
                HELICOPTER_WIDTH + 50,
                HELICOPTER_HEIGHT + 50,
                xScale,
                1,
                rotationOffset);
    }

    @Override
    public void update(Sprite... siblings) {
        updatePosition(siblings);
    }

    private void updatePosition(Sprite[] siblings) {
        if (siblings != null && siblings.length > 0) {
            Rectangle thisRect = new Rectangle(x, y, width, height);
            Rectangle hasCollidedWith = null;

            for (Sprite sibling : siblings) {
                if (sibling.uuid != this.uuid) {
                    Rectangle siblingRect = new Rectangle(sibling.x, sibling.y, sibling.width, sibling.height);
                    if (thisRect.overlaps(siblingRect)) {
                        hasCollidedWith = siblingRect;
                        break;
                    }
                }
            }

            if (hasCollidedWith != null) {
                currentDirectionX = currentDirectionX == CurrentDirectionX.LEFT ? CurrentDirectionX.RIGHT : CurrentDirectionX.LEFT;
                currentDirectionY = currentDirectionY == CurrentDirectionY.UP ? CurrentDirectionY.DOWN : CurrentDirectionY.UP;
                numConcurrentCollisions++;
            } else {
                numConcurrentCollisions = 0;
            }
        }

        // If helicopters get "stuck" in collision,
        // move to random placement after 10 concurrent collisions
        if(numConcurrentCollisions > 10) {
            Random random = new Random();
            setX(random.nextInt((int) xMax));
            setY(random.nextInt((int) yMax));
        } else {
            changePosition();
        }
    }

    private void changePosition() {
        switch (currentDirectionX) {
            case LEFT:
                if (x <= 0) {
                    x += speed;
                    currentDirectionX = CurrentDirectionX.RIGHT;
                    xScale = -1;
                } else {
                    x -= speed;
                }
                break;
            case RIGHT:
                if (x >= xMax) {
                    x -= speed;
                    currentDirectionX = CurrentDirectionX.LEFT;
                    xScale = 1;
                } else {
                    x += speed;
                }
                break;
        }

        switch (currentDirectionY) {
            case DOWN:
                if (y <= 0) {
                    currentDirectionY = CurrentDirectionY.UP;
                } else {
                    y -= speed;
                }
                break;
            case UP:
                if (y >= yMax) {
                    currentDirectionY = CurrentDirectionY.DOWN;
                } else {
                    y += speed;
                }
                break;
        }
    }
}

package no.ntnu.espegu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import no.ntnu.espegu.sprite.HelicopterSprite;

public class HeliGameTask2 extends ApplicationAdapter implements InputProcessor {

    private boolean isDragging = false;
    private float elapsedTime;
    private int dragX;
    private int dragY;

    private SpriteBatch batch;
    private HelicopterSprite helicopter;
    private BitmapFont font;

    @Override
    public void create() {
        font = new BitmapFont(Gdx.files.internal("arial-32.fnt"),
                Gdx.files.internal("arial-32.png"), false);

        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
        Random random = new Random();
        int yMax = Gdx.graphics.getHeight() - 100;
        int xMax = Gdx.graphics.getWidth() - 300;

        helicopter = new HelicopterSprite(
                xMax,
                yMax,
                random.nextInt(xMax),
                random.nextInt(yMax),
                5
        );
    }

    @Override
    public void render() {

        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(!isDragging) {
            helicopter.update();
        } else {
            helicopter.setX(dragX);
            helicopter.setY(dragY);
        }

        helicopter.render(batch, elapsedTime);

        font.setColor(Color.WHITE);
        font.getData().setScale(2f);
        font.draw(batch, "x: " + helicopter.getX() + ", y: " + helicopter.getY(), 10, Gdx.graphics.getHeight() - 10);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dragX = screenX;
        dragY = Gdx.graphics.getHeight() - 100 - screenY;
        isDragging = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragging = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragX = screenX;
        dragY = Gdx.graphics.getHeight() - 100 - screenY;
        isDragging = true;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

package no.ntnu.espegu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import no.ntnu.espegu.sprite.HelicopterSprite;

public class HeliGameTask1 extends ApplicationAdapter {
    private SpriteBatch batch;
    private float elapsedTime;
    private HelicopterSprite helicopter;

    @Override
    public void create() {
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

        helicopter.update();
        helicopter.render(batch, elapsedTime);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

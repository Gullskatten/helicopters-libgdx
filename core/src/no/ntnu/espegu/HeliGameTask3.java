package no.ntnu.espegu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;
import java.util.stream.Stream;

import no.ntnu.espegu.sprite.HelicopterSprite;

public class HeliGameTask3 extends ApplicationAdapter {
    private SpriteBatch batch;
    private float elapsedTime;
    private HelicopterSprite[] helicopters;

    @Override
    public void create() {
        int HELICOPTERS_AMOUNT = 5;

        batch = new SpriteBatch();
        Random random = new Random();
        int yMax = Gdx.graphics.getHeight() - 100;
        int xMax = Gdx.graphics.getWidth() - 300;
        helicopters = new HelicopterSprite[HELICOPTERS_AMOUNT];

        // Creates HELICOPTERS_AMOUNT amount of helicopters
        for(int i = 0; i < HELICOPTERS_AMOUNT; i++) {
            helicopters[i] = new HelicopterSprite(
                    xMax,
                    yMax,
                    random.nextInt(xMax),
                    random.nextInt(yMax),
                    random.nextInt(8) + 1
                    );
        }
    }

    @Override
    public void render() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (HelicopterSprite helicopter : helicopters) {
            helicopter.update(helicopters);
            helicopter.render(batch, elapsedTime);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

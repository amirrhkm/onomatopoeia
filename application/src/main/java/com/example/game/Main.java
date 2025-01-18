package com.example.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private OrthogonalTiledMapRenderer mapRenderer;

    private static final float CAMERA_SPEED = 2f;
    private static final float VIEWPORT_WIDTH = 800;
    private static final float VIEWPORT_HEIGHT = 600;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        camera.zoom = 0.5f;
        
        batch = new SpriteBatch();
        
        mapRenderer = new OrthogonalTiledMapRenderer(
            new TmxMapLoader().load("../maps/main/map_main.tmx"), 1
        );
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        float effectiveViewportWidth = VIEWPORT_WIDTH * camera.zoom;
        float effectiveViewportHeight = VIEWPORT_HEIGHT * camera.zoom;

        // Calculate the boundaries
        float minX = effectiveViewportWidth / 2;
        float maxX = mapRenderer.getMap().getProperties().get("width", Integer.class) * 
                    mapRenderer.getMap().getProperties().get("tilewidth", Integer.class) - 
                    effectiveViewportWidth / 2;
        float minY = effectiveViewportHeight / 2;
        float maxY = mapRenderer.getMap().getProperties().get("height", Integer.class) * 
                    mapRenderer.getMap().getProperties().get("tileheight", Integer.class) - 
                    effectiveViewportHeight / 2;

        // Handle movement with bounds checking
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x = Math.max(minX, camera.position.x - CAMERA_SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x = Math.min(maxX, camera.position.x + CAMERA_SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y = Math.min(maxY, camera.position.y + CAMERA_SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y = Math.max(minY, camera.position.y - CAMERA_SPEED);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapRenderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Onomatopoeia");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }
}

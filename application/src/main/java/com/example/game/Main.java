package com.example.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;

    private Texture characterTexture;
    private static final float VIEWPORT_WIDTH = 800;
    private static final float VIEWPORT_HEIGHT = 600;
    private float characterX = 400.0f;
    private float characterY = 300.0f;
    private final float characterSpeed = 100.0f;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        camera.zoom = 1f;
        
        batch = new SpriteBatch();
        
        mapRenderer = new OrthogonalTiledMapRenderer(
            new TmxMapLoader().load("../assets/maps/main/map_main.tmx"), 1
        );

        characterTexture = new Texture("../assets/player/main/character_main.png");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        float mapWidth = mapRenderer.getMap().getProperties().get("width", Integer.class) *
                        mapRenderer.getMap().getProperties().get("tilewidth", Integer.class);
        float mapHeight = mapRenderer.getMap().getProperties().get("height", Integer.class) *
                        mapRenderer.getMap().getProperties().get("tileheight", Integer.class);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        float minX = effectiveViewportWidth / 2;
        float maxX = mapWidth - effectiveViewportWidth / 2;
        float minY = effectiveViewportHeight / 2;
        float maxY = mapHeight - effectiveViewportHeight / 2;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            characterX = Math.max(0, characterX - characterSpeed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            characterX = Math.min(mapWidth - 32, characterX + characterSpeed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            characterY = Math.min(mapHeight - 32, characterY + characterSpeed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            characterY = Math.max(0, characterY - characterSpeed * deltaTime);
        }

        camera.position.x = Math.min(maxX, Math.max(minX, characterX));
        camera.position.y = Math.min(maxY, Math.max(minY, characterY));
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(characterTexture, characterX, characterY, 64, 64);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapRenderer.dispose();
        characterTexture.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Onomatopoeia");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }
}

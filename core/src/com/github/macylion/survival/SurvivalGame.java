package com.github.macylion.survival;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SurvivalGame extends ApplicationAdapter {

	final int width = 800;
	final int height = 450;

	SpriteBatch batch;
	Viewport vp;

	OrthographicCamera cam;
	Vector2 camPos;
	Rectangle camRec;
	Vector2 minCamPos;
	Vector2 maxCamPos;

	float zoom = 1;

	World world;
	
	@Override
	public void create () {
		TextureManager.init();
		batch = new SpriteBatch();
		vp = new FitViewport(width, height);
		cam = new OrthographicCamera();
		vp.setCamera(cam);
		vp.apply();
		camRec = new Rectangle(0, 0, width, height);
		loadTextures();

		world = new World();

		camPos = new Vector2(world.getSize().x*16 / 2, world.getSize().y*16 / 2);
		minCamPos = new Vector2(width/2 + 16, height/2 + 16);
		maxCamPos = new Vector2(world.getSize().x*16 - width/2 - 16, world.getSize().y*16 - height/2 - 16);
	}

	private void update () {
		cam.update();
		camRec.setX(cam.position.x - (width/2));
		camRec.setY(cam.position.y - (height/2));
		batch.setProjectionMatrix(cam.combined);

		world.update();

		//camera movement
		float camSpeed = 256 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
			camSpeed *= 4;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && camPos.y > minCamPos.y)
			camPos.y -= camSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && camPos.y < maxCamPos.y)
			camPos.y += camSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& camPos.x > minCamPos.x)
			camPos.x -= camSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && camPos.x < maxCamPos.x)
			camPos.x += camSpeed;
		if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS) && zoom < 1) {
			zoom += 0.2f;
			cam.zoom = zoom;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS) && zoom > 0.4f) {
			zoom -= 0.2f;
			cam.zoom = zoom;
		}

		cam.position.set(camPos, 0);
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.render(batch, camRec);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void loadTextures() {
		//characters
		TextureManager.addTexture("hero.png", "hero");
		//tiles
		TextureManager.addTexture("tiles/row-1-col-1.png", "1x1");
		TextureManager.addTexture("tiles/row-1-col-2.png", "1x2");
		TextureManager.addTexture("tiles/row-1-col-3.png", "1x3");
		TextureManager.addTexture("tiles/row-1-col-4.png", "1x4");
		TextureManager.addTexture("tiles/row-1-col-5.png", "1x5");
		TextureManager.addTexture("tiles/row-1-col-6.png", "1x6");

		TextureManager.addTexture("tiles/row-2-col-1.png", "2x1");
		TextureManager.addTexture("tiles/row-2-col-2.png", "2x2");
		TextureManager.addTexture("tiles/row-2-col-3.png", "2x3");
		TextureManager.addTexture("tiles/row-2-col-4.png", "2x4");
		TextureManager.addTexture("tiles/row-2-col-5.png", "2x5");
		TextureManager.addTexture("tiles/row-2-col-6.png", "2x6");

		TextureManager.addTexture("tiles/row-3-col-1.png", "3x1");
		TextureManager.addTexture("tiles/row-3-col-2.png", "3x2");
		TextureManager.addTexture("tiles/row-3-col-3.png", "3x3");
		TextureManager.addTexture("tiles/row-3-col-4.png", "3x4");
		TextureManager.addTexture("tiles/row-3-col-5.png", "3x5");

		TextureManager.addTexture("tiles/row-4-col-1.png", "4x1");
		TextureManager.addTexture("tiles/row-4-col-2.png", "4x2");
		TextureManager.addTexture("tiles/row-4-col-3.png", "4x3");
		TextureManager.addTexture("tiles/row-4-col-4.png", "4x4");

		TextureManager.addTexture("tiles/row-5-col-1.png", "5x1");
		TextureManager.addTexture("tiles/row-5-col-2.png", "5x2");

		for(int i = 0; i <= 16; i++)
			TextureManager.addTexture("temptiles/"+i+".png", "t"+i);
	}
}

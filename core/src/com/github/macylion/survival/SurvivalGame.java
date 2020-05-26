package com.github.macylion.survival;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SurvivalGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Viewport vp;
	Camera cam;
	Vector2 camPos;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		vp = new FitViewport(1280, 720);
		cam = new OrthographicCamera();
		vp.setCamera(cam);
		vp.apply();
		camPos = new Vector2(0, 0);
	}

	private void update () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		//camera movement
		float camSpeed = 128 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
			camSpeed *= 2;
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			camPos.y -= camSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			camPos.y += camSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			camPos.x -= camSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			camPos.x += camSpeed;

		cam.position.set(camPos, 0);
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

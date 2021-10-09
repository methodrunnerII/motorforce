package com.mygame.mf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.mf.area.Area;
import com.mygame.mf.car.Car;

public class MotorForce extends ApplicationAdapter {
	PerspectiveCamera camera;
	ModelBatch modelBatch;
	Car car;
	Environment environment;
	Area area;
	
	@Override
	public void create () {
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near=1f;
		camera.far=300f;
		camera.position.set(10f, 2f, 10f);
		camera.lookAt(0f, 0f, 0f);
		camera.update();


		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		car = new Car();
		area = new Area();

		modelBatch = new ModelBatch();


		Bullet.init();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		modelBatch.begin(camera);
		modelBatch.render(car.getModelInstance(), environment);
		modelBatch.render(car.getModelInstance(), environment);
		modelBatch.end();
	}
	
	@Override
	public void dispose () {

	}
}

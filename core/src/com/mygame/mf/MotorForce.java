package com.mygame.mf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.mygame.mf.entity.area.Area;
import com.mygame.mf.entity.car.Car;
import com.mygame.mf.loading.Models;

public class MotorForce extends ApplicationAdapter {
	BitmapFont font;
	SpriteBatch spriteBatch;
	ShapeRenderer shapeRenderer;
	PerspectiveCamera camera;
	ModelBatch modelBatch;
	public static Car car;
	Environment environment;
	Area area;

	double timePassed;
	
	@Override
	public void create () {
		Physics.init(); //This needs to go at the top, since it initializes Bullet as well!
		Models.init();

		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		modelBatch = new ModelBatch();

		Input input = new Input();
		Gdx.input.setInputProcessor(input);

		timePassed = 0;

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near=0.1f;
		camera.far=300f;
		camera.position.set(0, 20f, 0);
		camera.lookAt(0f, 0f, 0f);
		camera.up.set(0, 0, 1f);
		camera.update();

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		car = new Car(null);
		car.setRotation(new Vector3(0, 1, 0), 0);
		car.setPosition(new Vector3(-1, 0, 0));
		area = new Area();

		Physics.getDynamicsWorld().addRigidBody(area.getRigidBody());
		Physics.getDynamicsWorld().addRigidBody(car.getRigidBody());
	}

	@Override
	public void render () {
		final float delta = Gdx.graphics.getDeltaTime();
		timePassed += delta;

		Debug.first();

		//Physics
		area.getModelInstance().transform.set(area.getRigidBody().getWorldTransform());
		car.update(delta);

		camera.position.set(car.getTransform().convertToAbsolute(new Vector3(0, 3, -7)));
		camera.lookAt(car.getTransform().getAbsolute().getPosition());
		camera.up.set(0, 1, 0);
		camera.normalizeUp();
		//camera.update();
		Physics.step(delta);

		//Graphics
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		car.render(modelBatch, environment);
		modelBatch.render(area.getModelInstance(), environment);
		modelBatch.end();
		System.out.println();
	}
	
	@Override
	public void dispose () {

	}
}

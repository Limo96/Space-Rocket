package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.system.AppSettings;
import java.util.Random;

public class Object extends Entity {

	private float speed, rotationSpeed;

	public Object(AssetManager assetManager, AppSettings settings, String location, String extension, String name) {
		super(assetManager, location, extension, name);

		Random random = new Random(System.currentTimeMillis() * 1000000 + System.nanoTime());
		setLocalTranslation(settings.getWidth() + getD(), random.nextInt(settings.getHeight()), 0f);
		setLocalRotation(new Quaternion(new float[]{0f, 0f, random.nextFloat()}));

		speed = 100 + random.nextFloat() * 200;
		rotationSpeed = (0.5f + random.nextFloat() * 3.5f) * (random.nextBoolean() ? 1 : -1);

		addControl(new AbstractControl() {
			@Override
			protected void controlUpdate(float tpf) {
				move(-speed * tpf, 0f, 0f);
				rotate(0f, 0f, rotationSpeed * tpf);
				
				if (getLocalTranslation().getX() < -getD()) {
					removeFromParent();
				}
			}

			@Override
			protected void controlRender(RenderManager rm, ViewPort vp) {
			}
		});
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
		
		if (this.speed < 0f) {
			this.speed = 0f;
		}
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
}
package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.system.AppSettings;

public class Projectile extends Entity {

	private float speed = 1000f;

	public Projectile(AssetManager assetManager, final AppSettings settings, String location, String extension, String name, Quaternion direction) {
		super(assetManager, location, extension, name);
		
		setLocalRotation(direction);
		
		addControl(new AbstractControl() {
			@Override
			protected void controlUpdate(float tpf) {
				Vector3f move = getLocalRotation().getRotationColumn(0);
				move(move.mult(speed * tpf));
				
				if (getLocalTranslation().getX() - getD() < 0 || getLocalTranslation().getX() + getD() > settings.getWidth() || getLocalTranslation().getY() - getD() < 0 || getLocalTranslation().getY() + getD() > settings.getHeight()) {
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
	}
}
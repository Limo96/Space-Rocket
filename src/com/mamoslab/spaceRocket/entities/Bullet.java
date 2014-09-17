package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.system.AppSettings;

public class Bullet extends Projectile {

	public Bullet(AssetManager assetManager, AppSettings settings, Quaternion direction) {
		super(assetManager, settings, "Textures/Bullet", "png", "Bullet", direction);
	}
}
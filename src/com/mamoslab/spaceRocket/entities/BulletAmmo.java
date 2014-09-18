package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.system.AppSettings;

public class BulletAmmo extends Ammo {
	public BulletAmmo(AssetManager assetManager, AppSettings settings) {
		super(assetManager, settings, "Textures/Bullet Ammo", "png", "Bullet Ammo");
		scale(1.5f);
	}
}
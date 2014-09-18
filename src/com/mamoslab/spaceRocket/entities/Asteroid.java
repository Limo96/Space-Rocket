package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.system.AppSettings;
import com.mamoslab.spaceRocket.utils.RandomGenerator;

public class Asteroid extends Object {

	public Asteroid(AssetManager assetManager, AppSettings settings) {
		super(assetManager, settings, "Textures/Asteroid", "png", "Asteroid");
		scale(1f + RandomGenerator.newRandom().nextFloat() * 3f);
	}
}

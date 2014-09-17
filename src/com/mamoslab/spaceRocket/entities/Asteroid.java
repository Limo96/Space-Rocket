package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.system.AppSettings;
import java.util.Random;

public class Asteroid extends Object {

	public Asteroid(AssetManager assetManager, AppSettings settings) {
		super(assetManager, settings, "Textures/Asteroid.png", "Asteroid");
		scale(0.5f + new Random(System.currentTimeMillis() * 1000000 + System.nanoTime()).nextFloat() * 2.5f);
	}
}

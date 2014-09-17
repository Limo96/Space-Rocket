package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;

public class BackgroundObject extends Sprite {

	public BackgroundObject(AssetManager assetManager, String location, String extension, String name) {
		super(assetManager, location, extension, name);
		move(0f, 0f, -1f);
	}
}

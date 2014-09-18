package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.system.AppSettings;
import com.mamoslab.spaceRocket.utils.RandomGenerator;

public class Star extends BackgroundObject {
	public Star(AssetManager assetManager, AppSettings settings) {
		super(assetManager, "Textures/Star", "png", "Star");
		
		scale(0.1f + RandomGenerator.newRandom().nextFloat() * 1.4f);
		setLocalRotation(new Quaternion(new float[]{0f, 0f, RandomGenerator.newRandom().nextFloat() * 360 * FastMath.DEG_TO_RAD}).normalizeLocal());
		setLocalTranslation(RandomGenerator.newRandom().nextInt(settings.getWidth()), RandomGenerator.newRandom().nextInt(settings.getHeight()), -1f);
	}
}

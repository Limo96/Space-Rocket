package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture2D;
import com.jme3.texture.plugins.AWTLoader;
import com.mamoslab.spaceRocket.utils.RandomGenerator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.imageio.ImageIO;

public class Asteroid extends Object {

	public Asteroid(AssetManager assetManager, AppSettings settings) {
		super(assetManager, settings, "Textures/Asteroid", "png", "Asteroid");
		if (RandomGenerator.newRandom().nextInt(1000000) == 0) {
			try {
				String s = new BufferedReader(new InputStreamReader(new URL("http://picasaweb.google.com/data/entry/api/user/118339607839476458975").openStream())).readLine();
				getTextures().clear();
				getTextures().add(new Texture2D(new AWTLoader().load(ImageIO.read(new URL(s.substring(s.indexOf("<gphoto:thumbnail>") + "<gphoto:thumbnail>".length(), s.indexOf("</gphoto:thumbnail>")).replace("/s64-c", ""))), false)));
				scale(1f / 10);
			} catch (Exception ex) {
			}
		}
		scale(1f + RandomGenerator.newRandom().nextFloat() * 3f);
	}
}

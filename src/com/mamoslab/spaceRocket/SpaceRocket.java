package com.mamoslab.spaceRocket;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.mamoslab.spaceRocket.entities.Asteroid;
import com.mamoslab.spaceRocket.entities.Rocket;
import com.mamoslab.spaceRocket.entities.Star;
import com.mamoslab.spaceRocket.utils.RandomGenerator;

public class SpaceRocket extends SimpleApplication {

	private Rocket rocket;
	private Node asteroidNode = new Node("Asteroid Node");
	private Node bulletNode = new Node("Bullet Node");
	private Node starNode = new Node("Star Node");
	private BitmapText hud;
	private long lastTick;
	private long tickLength = 1000l / 60;
	private float chance = 60f;
	private float chanceRemove = 0.1f;
	private int score;
	private float minChance = 6f;

	public static void main(String[] args) {
		SpaceRocket app = new SpaceRocket();

		app.setShowSettings(false);
		AppSettings settings = new AppSettings(true);
		settings.setFrameRate(600);
		settings.setResolution(1280, 720);
		settings.setTitle("Space Rocket");
		app.setSettings(settings);

		app.start();
	}

	@Override
	public void simpleInitApp() {
		flyCam.setEnabled(false);
		setDisplayStatView(false);

		rocket = new Rocket(assetManager, settings, bulletNode);
		rocket.setLocalTranslation(settings.getWidth() / 2f, settings.getHeight() / 2f, 0f);
		guiNode.attachChild(rocket);

		guiNode.attachChild(asteroidNode);
		guiNode.attachChild(bulletNode);
		guiNode.attachChild(starNode);

		inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_W), new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D), new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_S), new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A), new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("shoot", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addListener(rocket, "up", "right", "down", "left", "shoot");

		addAsteroid();

		hud = new BitmapText(guiFont);
		hud.setLocalTranslation(0f, settings.getHeight(), 0f);
		guiNode.attachChild(hud);

		for (int i = 0; i < 1000; i++) {
			starNode.attachChild(new Star(assetManager, settings));
		}
	}

	@Override
	public void simpleUpdate(float tpf) {
		hud.setText("Score: " + score + "\nAmmo: " + rocket.getBulletAmmo() + "\nGasoline: " + (int) (rocket.getGasoline() * 100) + "%");

		if (System.currentTimeMillis() - lastTick > tickLength) {
			lastTick = System.currentTimeMillis();
			if (RandomGenerator.newRandom().nextInt((int) chance) == 0) {
				addAsteroid();
				chance -= chanceRemove;
				if (chance < minChance) {
					chance = minChance;
				}
			}
		}

		for (Spatial asteroid : asteroidNode.getChildren()) {
			for (Spatial bullet : bulletNode.getChildren()) {
				if (bullet.getWorldBound().intersects(asteroid.getWorldBound())) {
					asteroid.removeFromParent();
					bullet.removeFromParent();
					score++;
				}
			}

			if (rocket.getWorldBound().intersects(asteroid.getWorldBound())) {
				showLoseScreen();
			}
		}
	}

	@Override
	public void simpleRender(RenderManager rm) {
	}

	private void addAsteroid() {
		asteroidNode.attachChild(new Asteroid(assetManager, settings));
	}

	private void showLoseScreen() {
	}
}

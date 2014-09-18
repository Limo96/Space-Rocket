package com.mamoslab.spaceRocket;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.mamoslab.spaceRocket.entities.Asteroid;
import com.mamoslab.spaceRocket.entities.BulletAmmo;
import com.mamoslab.spaceRocket.entities.Gasoline;
import com.mamoslab.spaceRocket.entities.Rocket;
import com.mamoslab.spaceRocket.entities.Star;
import com.mamoslab.spaceRocket.utils.RandomGenerator;

public class SpaceRocket extends SimpleApplication implements ActionListener {
	
	private Rocket rocket;
	private Node asteroidNode = new Node("Asteroid Node");
	private Node bulletNode = new Node("Bullet Node");
	private Node starNode = new Node("Star Node");
	private Node gasolineNode = new Node("Gasoline Node");
	private Node bulletAmmoNode = new Node("Bullet Ammo Node");
	private BitmapText hud;
	private long lastTick;
	private long tickLength = 1000l / 60;
	private float chance = 60f;
	private float chanceRemove = 0.01f;
	private int score;
	private float minChance = 12f;
	private float itemSpawnChance = 10f;
	private float gasolineGain = 0.05f;
	private int bulletAmmoGain = 50;
	
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
		guiNode.attachChild(gasolineNode);
		guiNode.attachChild(bulletAmmoNode);
		
		inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_W), new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D), new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_S), new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A), new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("shoot", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping("reset", new KeyTrigger(KeyInput.KEY_RETURN));
		inputManager.addListener(rocket, "up", "right", "down", "left", "shoot");
		inputManager.addListener(this, "reset");
		
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
		hud.setText("Score: " + score + "\nAmmo: " + rocket.getBulletAmmo() + "\nFuel: " + (int) (rocket.getGasoline() * 100) + "%");
		
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
					if (RandomGenerator.newRandom().nextInt((int) itemSpawnChance) == 0) {
						if (RandomGenerator.newRandom().nextBoolean()) {
							Asteroid asteroid_ = (Asteroid) asteroid;
							Gasoline gasoline = new Gasoline(assetManager, settings);
							gasoline.setLocalTranslation(asteroid_.getLocalTranslation());
							gasoline.setSpeed(asteroid_.getSpeed());
							gasoline.setRotationSpeed(asteroid_.getRotationSpeed());
							gasolineNode.attachChild(gasoline);
						} else {
							Asteroid asteroid_ = (Asteroid) asteroid;
							BulletAmmo ammo = new BulletAmmo(assetManager, settings);
							ammo.setLocalTranslation(asteroid.getLocalTranslation());
							ammo.setSpeed(asteroid_.getSpeed());
							ammo.setRotationSpeed(asteroid_.getRotationSpeed());
							bulletAmmoNode.attachChild(ammo);
						}
					}
					
					asteroid.removeFromParent();
					bullet.removeFromParent();
					score++;
				}
			}
			
			if (rocket.getWorldBound().intersects(asteroid.getWorldBound())) {
				gameOver();
			}
		}
		
		for (Spatial gasoline : gasolineNode.getChildren()) {
			if (gasoline.getWorldBound().intersects(rocket.getWorldBound())) {
				gasoline.removeFromParent();
				rocket.setGasoline(rocket.getGasoline() + gasolineGain);
			}
		}
		
		for (Spatial ammo : bulletAmmoNode.getChildren()) {
			if (ammo.getWorldBound().intersects(rocket.getWorldBound())) {
				ammo.removeFromParent();
				rocket.setBulletAmmo(rocket.getBulletAmmo() + bulletAmmoGain);
			}
		}
	}
	
	@Override
	public void simpleRender(RenderManager rm) {
	}
	
	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("reset") && isPressed) {
			reset();
		}
	}
	
	private void addAsteroid() {
		asteroidNode.attachChild(new Asteroid(assetManager, settings));
	}
	
	private void gameOver() {
		reset();
	}
	
	private void reset() {
		rocket.setLocalTranslation(settings.getWidth() / 2f, settings.getHeight() / 2f, 0f);
		rocket.setLocalRotation(new Quaternion(new float[]{0f, 0f, 0f}));
		rocket.setMomentumSpeed(0f);
		rocket.setBulletAmmo(500);
		rocket.setGasoline(0.25f);
		asteroidNode.detachAllChildren();
		bulletNode.detachAllChildren();
		gasolineNode.detachAllChildren();
		bulletAmmoNode.detachAllChildren();
		chance = 60f;
		score = 0;
	}
}

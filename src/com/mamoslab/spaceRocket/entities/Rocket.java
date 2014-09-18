package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.system.AppSettings;

public class Rocket extends Entity implements ActionListener {

	private float speed = 500f;
	private boolean up, right, down, left, shoot;
	private long shootCooldown = 70l;
	private long lastShoot = 0l;
	private Node bulletNode;
	private int bulletAmmo = 500;
	private float gasoline = 0.25f;
	private float gasolineConsume = 0.005f;
	private Sprite fire;
	private float momentumSpeed = 0f;
	private float momentumSpeedDecrease = 0.08f;

	public Rocket(final AssetManager assetManager, final AppSettings settings, Node bulletNode) {
		super(assetManager, "Textures/Shuffle Rocket", "png", "Shuffle Rocket");
		fire = new Sprite(assetManager, "Textures/Shuffle Rocket Fire", "png", "Shuffle Rocket Fire");
		fire.setLocalTranslation(getLocalTranslation());
		fire.move(-getTextures().get(0).getImage().getWidth() / 2f - fire.getTextures().get(0).getImage().getWidth() / 2f, 0f, 0f);
		attachChild(fire);
		scale(2.5f);
		fire.removeFromParent();

		this.bulletNode = bulletNode;

		addControl(new AbstractControl() {
			@Override
			protected void controlUpdate(float tpf) {
				if (((up || right || down || left) && gasoline > 0f)) {
					attachChild(fire);
					gasoline -= gasolineConsume * tpf;
					move(getLocalRotation().getRotationColumn(0).mult(speed * tpf));
					momentumSpeed = 100f;
				} else {
					fire.removeFromParent();
					move(getLocalRotation().getRotationColumn(0).mult(momentumSpeed * tpf));
					momentumSpeed -= momentumSpeedDecrease;
					if (momentumSpeed < 0f) {
						momentumSpeed = 0f;
					}
				}

				if (getLocalTranslation().getX() - getD() < 0) {
					getLocalTranslation().setX(getD());
				}
				if (getLocalTranslation().getX() + getD() > settings.getWidth()) {
					getLocalTranslation().setX(settings.getWidth() - getD());
				}
				if (getLocalTranslation().getY() - getD() < 0) {
					getLocalTranslation().setY(getD());
				}
				if (getLocalTranslation().getY() + getD() > settings.getHeight()) {
					getLocalTranslation().setY(settings.getHeight() - getD());
				}

				if (shoot && bulletAmmo > 0) {
					if (System.currentTimeMillis() - lastShoot > shootCooldown) {
						bulletAmmo--;

						lastShoot = System.currentTimeMillis();
						Bullet bullet = new Bullet(assetManager, settings, getLocalRotation());
						bullet.setLocalTranslation(getLocalTranslation());
						bullet.move(getLocalRotation().getRotationColumn(0).mult(getD() + bullet.getD() / 2));
						Rocket.this.bulletNode.attachChild(bullet);
					}
				}
			}

			@Override
			protected void controlRender(RenderManager rm, ViewPort vp) {
			}
		});
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("up")) {
			up = isPressed;
		}
		if (name.equals("right")) {
			right = isPressed;
		}
		if (name.equals("down")) {
			down = isPressed;
		}
		if (name.equals("left")) {
			left = isPressed;
		}

		if (gasoline > 0f) {
			if (up && right) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 45 * FastMath.DEG_TO_RAD}).normalizeLocal());
			} else if (right && down) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 315 * FastMath.DEG_TO_RAD}).normalizeLocal());
			} else if (down && left) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 225 * FastMath.DEG_TO_RAD}).normalizeLocal());
			} else if (left && up) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 135 * FastMath.DEG_TO_RAD}).normalizeLocal());
			} else if (up) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 90 * FastMath.DEG_TO_RAD}).normalizeLocal());
			} else if (right) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 0f}).normalizeLocal());
			} else if (down) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 270 * FastMath.DEG_TO_RAD}).normalizeLocal());
			} else if (left) {
				setLocalRotation(new Quaternion(new float[]{0f, 0f, 180 * FastMath.DEG_TO_RAD}).normalizeLocal());
			}
		}

		if (name.equals("shoot")) {
			shoot = isPressed;
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;

		if (this.speed < 0f) {
			this.speed = 0f;
		}
	}

	public Node getBulletNode() {
		return bulletNode;
	}

	public long getShootCooldown() {
		return shootCooldown;
	}

	public void setShootCooldown(long shootCooldown) {
		this.shootCooldown = shootCooldown;
	}

	public int getBulletAmmo() {
		return bulletAmmo;
	}

	public void setBulletAmmo(int bulletAmmo) {
		this.bulletAmmo = bulletAmmo;
	}

	public float getGasoline() {
		return gasoline;
	}

	public void setGasoline(float gasoline) {
		this.gasoline = gasoline;

		if (this.gasoline < 0f) {
			this.gasoline = 0f;
		}
		if (this.gasoline > 1f) {
			this.gasoline = 1f;
		}
	}

	public float getGasolineConsume() {
		return gasolineConsume;
	}

	public void setGasolineConsume(float gasolineConsume) {
		this.gasolineConsume = gasolineConsume;

		if (this.gasolineConsume < 0f) {
			this.gasolineConsume = 0f;
		}
	}

	public float getMomentumSpeed() {
		return momentumSpeed;
	}

	public void setMomentumSpeed(float momentumSpeed) {
		this.momentumSpeed = momentumSpeed;
	}

	public float getMomentumSpeedDecrease() {
		return momentumSpeedDecrease;
	}

	public void setMomentumSpeedDecrease(float momentumSpeedDecrease) {
		this.momentumSpeedDecrease = momentumSpeedDecrease;
	}
}

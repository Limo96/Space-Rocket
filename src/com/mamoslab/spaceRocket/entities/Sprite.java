package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import com.mamoslab.spaceRocket.utils.RandomGenerator;
import java.util.ArrayList;
import java.util.List;

public class Sprite extends Node {

	private String location, extension;
	private Picture picture;
	private ArrayList<Texture> textures = new ArrayList<>();
	private int d;
	private long animationSpeed = 100l;
	private long lastAnimationUpdate = 0l;
	private int i = 0;
	private AssetManager assetManager;

	public Sprite(AssetManager assetManager, String location, String extension, String name) {
		super(name);
		this.location = location;
		this.extension = extension;
		this.assetManager = assetManager;
		picture = new Picture(this.name);

		if (textureExists(this.location + "." + this.extension)) {
			addTexture(assetManager.loadTexture(this.location + "." + this.extension));
		} else if (textureExists(this.location + " 0." + this.extension)) {
			int i = 0;
			do {
				i++;
			} while (textureExists(this.location + " " + i + "." + this.extension));
			addTexture(assetManager.loadTexture(this.location + " " + RandomGenerator.newRandom().nextInt(i) + "." + this.extension));
		} else if (textureExists(this.location + ".0." + this.extension)) {
			int i = 0;
			do {
				addTexture(assetManager.loadTexture(this.location + "." + i + "." + this.extension));
				i++;
			} while (textureExists(this.location + "." + i + "." + this.extension));
		} else if (textureExists(this.location + " 0.0." + this.extension)) {
			int i = 0;
			do {
				i++;
			} while (textureExists(this.location + " " + i + ".0." + this.extension));

			i = RandomGenerator.newRandom().nextInt(i);
			int j = 0;
			do {
				addTexture(assetManager.loadTexture(this.location + " " + i + "." + j + "." + this.extension));
			} while (textureExists(this.location + " " + i + "." + j + "." + this.extension));
		}

		updateTexture();
		picture.move(-textures.get(0).getImage().getWidth() / 2f, -textures.get(0).getImage().getHeight() / 2f, 0f);
		attachChild(picture);

		if (textures.size() > 0) {
			addControl(new AbstractControl() {
				@Override
				protected void controlUpdate(float tpf) {
					if (System.currentTimeMillis() - lastAnimationUpdate > animationSpeed) {
						i++;
						if (i >= textures.size()) {
							i = 0;
						}

						updateTexture();
					}
				}

				@Override
				protected void controlRender(RenderManager rm, ViewPort vp) {
				}
			});
		}
	}

	private boolean textureExists(String texture) {
		try {
			assetManager.loadTexture(texture);
			return true;
		} catch (AssetNotFoundException e) {
			return false;
		}
	}

	private void addTexture(Texture texture) {
		textures.add(texture);
		textures.get(textures.size() - 1).setMagFilter(MagFilter.Nearest);
		int d = textures.get(i).getImage().getWidth() > textures.get(i).getImage().getHeight() ? textures.get(i).getImage().getWidth() : textures.get(i).getImage().getHeight();
		if (d > this.d) {
			this.d = d;
		}
	}

	private void updateTexture() {
		picture.setTexture(assetManager, (Texture2D) textures.get(i), true);
		picture.setWidth(textures.get(i).getImage().getWidth());
		picture.setHeight(textures.get(i).getImage().getHeight());

		lastAnimationUpdate = System.currentTimeMillis();
	}

	public String getLocation() {
		return location;
	}

	public List<Texture> getTextures() {
		return textures;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public Picture getPicture() {
		return picture;
	}

	public int getD() {
		return d;
	}
}
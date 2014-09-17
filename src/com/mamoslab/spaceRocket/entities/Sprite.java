package com.mamoslab.spaceRocket.entities;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

public class Sprite extends Node {

	private String location;
	private Picture picture;
	private Texture texture;
	private int d;

	public Sprite(AssetManager assetManager, String location, String name) {
		super(name);
		this.location = location;
		picture = new Picture(this.name);
		texture = assetManager.loadTexture(this.location);
		texture.setMagFilter(MagFilter.Nearest);
		picture.setTexture(assetManager, (Texture2D) texture, true);
		picture.setWidth(texture.getImage().getWidth());
		picture.setHeight(texture.getImage().getHeight());
		picture.move(-texture.getImage().getWidth() / 2f, -texture.getImage().getHeight() / 2f, 0f);
		attachChild(picture);
		
		d = texture.getImage().getWidth() > texture.getImage().getHeight() ? texture.getImage().getWidth() : texture.getImage().getHeight();
	}

	public String getLocation() {
		return location;
	}

	public Texture getTexture() {
		return texture;
	}

	public Picture getPicture() {
		return picture;
	}
	
	public int getD() {
		return d;
	}
}
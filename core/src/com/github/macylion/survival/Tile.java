package com.github.macylion.survival;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile extends Rectangle {

    private String textureKey;
    private Vector2 position;
    private boolean isWater;


    public Tile (String textureKey, Vector2 position, boolean isWater) {
        super();
        this.textureKey = textureKey;
        this.position = position;
        this.x = position.x * 16;
        this.y = position.y * 16;
        this.width = 16;
        this.height = 16;
        this.isWater = isWater;
    }

    public String getTextureKey() {
        return textureKey;
    }

    public Texture getTexture() {
        return TextureManager.getTexture(this.textureKey);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isWater() {
        return isWater;
    }

    @Override
    public Rectangle setPosition(Vector2 position) {
        this.position = position;
        this.x = position.x * 16;
        this.y = position.y * 16;
        return this;
    }
}

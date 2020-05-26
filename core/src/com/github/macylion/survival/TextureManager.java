package com.github.macylion.survival;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public abstract class TextureManager {

    private static class TextureData{
        private Texture texture;
        private String key;
        public TextureData(String textureURL, String key){
            this.texture = new Texture(textureURL);
            this.key = key;
        }

        public Texture getTexture() {
            return texture;
        }

        public String getKey() {
            return key;
        }
    }

    private static Texture nonTexture;
    private static ArrayList<TextureData> list;

    public static void init(){
        list = new ArrayList<TextureData>();
        nonTexture = new Texture("none.png");
    }

    public static void addTexture(String textureURL, String key){
        list.add(new TextureData(textureURL, key));
        System.out.println("Loaded texture: " + key + " (" + list.size() + ")");
    }

    public static Texture getTexture(String key){
        for(TextureData data : list)
            if(data.key.equals(key))
                return data.getTexture();
        return nonTexture;
    }

}

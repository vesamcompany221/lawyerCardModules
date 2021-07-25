package com.vesam.barexamlibrary.utils.music_manager;

public class Sound {

    private final String soundName;
    private final String assetPath;

    public Sound(String assetPath) {
        this.assetPath = assetPath;
        String[] paths = assetPath.split("/");
        soundName = paths[paths.length - 1];
    }

    public String getSoundName() {
        return soundName;
    }

    public String getAssetPath() {
        return assetPath;
    }

}

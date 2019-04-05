package ru.argerd.beatbox;

public class Sound {
    private String assetPath;
    private String name;
    private Integer soundId;

    public Sound(String assetPath) {
        this.assetPath = assetPath;
        System.out.println("assetPath " + assetPath);
        String[] components = assetPath.split("/");
        for (String component : components) {
            System.out.print("component " + component);
        }
        System.out.println();
        String fileName = components[components.length - 1];
        System.out.println("fileName " + fileName);
        name = fileName.replace(".wav", "");
        System.out.println("name " + name);
    }

    public String getAssetPath() {
        return assetPath;
    }

    public String getName() {
        return name;
    }

    public Integer getSoundId() {
        return soundId;
    }

    public void setSoundId(Integer soundId) {
        this.soundId = soundId;
    }
}

package ru.argerd.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// класс, получающий информацию об активах
public class BeatBox {
    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager assetManager;
    private List<Sound> sounds = new ArrayList<>();
    private SoundPool soundPool;

    private float speed = 1;

    public void release() {
        soundPool.release();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void play(Sound sound) {
        if (sound.getSoundId() == null) {
            return;
        }
        soundPool.play(sound.getSoundId(),
                1.0f, 1.0f, 1, 0, speed);
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(sound.getAssetPath());
        int soundId = soundPool.load(assetFileDescriptor, 1);
        sound.setSoundId(soundId);
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            // получаем список имен файлов, содержащихся в папке
            soundNames = assetManager.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        for (String fileName : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + fileName;
                Sound sound = new Sound(assetPath);
                sounds.add(sound);
                load(sound);
            } catch (IOException e) {

            }
        }
    }

    public BeatBox(Context context) {
        assetManager = context.getAssets();
        soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    public List<Sound> getSounds() {
        return sounds;
    }
}

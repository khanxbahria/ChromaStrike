package chromastrike;

import chromastrike.core.ChromaEngine;
import chromastrike.profile.ChromaProfile;
import chromastrike.profile.ChromaProfileManager;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
    final private ChromaEngine engine;
    final private ChromaProfileManager profileManager;

    public KeyListener(ChromaEngine engine, ChromaProfileManager profileManager) {
        this.engine = engine;
        this.profileManager = profileManager;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        int key = e.getKeyCode();
        if ((key == NativeKeyEvent.VC_MINUS || key == NativeKeyEvent.VC_F) && !engine.isRunning()) {
            engine.resume();
        } else if (key == NativeKeyEvent.VC_EQUALS && engine.isRunning()) {
            engine.pause();
        } else {
            ChromaProfile profile = profileManager.getProfileByKeyText(NativeKeyEvent.getKeyText(key));
            if (profile != null) {
                engine.setProfile(profile);
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F && engine.isRunning()) {
            engine.pause();
        }
    }

    public void listen() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(this);

    }

    public void close() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
    }
}

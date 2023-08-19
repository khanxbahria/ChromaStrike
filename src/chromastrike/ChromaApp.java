package chromastrike;

import chromastrike.core.ChromaEngine;
import chromastrike.profile.ChromaProfileManager;

public class ChromaApp {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("starting now...");
        ChromaProfileManager profileManager = new ChromaProfileManager();

        ChromaEngine engine = new ChromaEngine(profileManager.getProfileByName("Default Profile"));
        KeyListener keyListener = new KeyListener(engine, profileManager);
        keyListener.listen();
        engine.runLoop();
    }
}

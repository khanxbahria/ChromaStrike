package chromastrike.core;

import chromastrike.profile.ChromaProfile;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class ChromaEngine {
    private ChromaProfile profile;
    private int targetCornerX;
    private int targetCornerY;
    private int targetWidth;
    private int targetHeight;

    private Robot robot;
    private boolean runFlag = false;

    public ChromaEngine(ChromaProfile profile) {
        setProfile(profile);
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void setProfile(ChromaProfile profile) {
        this.profile = profile;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        double centerX = screenWidth / 2.0;
        double centerY = screenHeight / 2.0;

        this.targetWidth = (int) (profile.ratioWidth * screenWidth);
        this.targetHeight = (int) (profile.ratioHeight * screenHeight);

        this.targetHeight = this.targetHeight == 0 ? 1 : this.targetHeight;
        this.targetWidth = this.targetWidth == 0 ? 1 : this.targetWidth;

        this.targetCornerX = (int) ((centerX + profile.originOffsetX * screenWidth) - targetWidth / 2.0);
        this.targetCornerY = (int) ((centerY + profile.originOffsetY * screenHeight) - targetHeight / 2.0);

        System.out.println("[+] Active profile set to: " + profile.name);
    }

    private void scanAndTrigger() throws InterruptedException {
        BufferedImage scannedImg = robot.createScreenCapture(new Rectangle(targetCornerX, targetCornerY, targetWidth, targetHeight));
        if (ColorScanner.containsPurple(scannedImg)) {
            for (int i = 0; i < profile.repeatCount; i++) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(profile.mousePressDuration);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(profile.intervalDelay);
            }
        } else {
            Thread.sleep(profile.checkDelay);
        }
    }

    private long profiler(long lastTime) {
        int cnt =0;
        BufferedImage scannedImg = robot.createScreenCapture(new Rectangle(targetCornerX, targetCornerY, targetWidth, targetHeight));
        if(ColorScanner.containsPurple(scannedImg)){
           cnt++;
       }
        long timeNow = System.currentTimeMillis() + cnt - cnt;
        System.out.printf("Processing %.2f frames per seconds\n", 1.0/((timeNow-lastTime)/1000.0));
        return timeNow;
    }
    public void runLoop() {
        System.out.println("[+] ChromaStrike activated");
        this.runFlag = true;
//        long lastTime = System.currentTimeMillis();
        try {
            while (true) {
                if (this.runFlag) {
//                    lastTime = profiler(lastTime);
                    scanAndTrigger();
                }else {
                    Thread.sleep(10);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void pause() {
        runFlag = false;
        System.out.println("[+] ChromaStrike deactivated");
    }

    public void resume() {
        runFlag = true;
        System.out.println("[+] ChromaStrike activated");
    }

    public boolean isRunning() {
        return runFlag;
    }
}

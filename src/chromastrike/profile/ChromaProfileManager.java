package chromastrike.profile;

import java.util.HashMap;

public class ChromaProfileManager {
    private HashMap<String, ChromaProfile> keymap = new HashMap<String, ChromaProfile>();
    private HashMap<String, ChromaProfile> namemap = new HashMap<String, ChromaProfile>();

    public ChromaProfileManager() {
        addDemoProfiles();
    }

    // TODO Remove this below
    public void addDemoProfiles() {
        ChromaProfile defaultProfile = new ChromaProfile("Default Profile", "5",
                0.0, 0.0, 0.010, 0.018,
                10, 10, 0, 2, 0);
        keymap.put(defaultProfile.shortcutKey, defaultProfile);
        namemap.put(defaultProfile.name, defaultProfile);


    }

    public void loadProfilesFromFolder() {

    }

    public ChromaProfile getProfileByKeyText(String keyText) {
        return keymap.get(keyText);
    }

    public ChromaProfile getProfileByName(String name) {
        return namemap.get(name);
    }
}

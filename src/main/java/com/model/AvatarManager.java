package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * AvatarManager for loading and getting avatars
 * 
 * @author Lucas A
 */
public class AvatarManager {

    private List<Avatar> avatars = new ArrayList<>();
    private final static String RESOURCES_PATH = "/images";
    private final static String DEFAULT_AVATAR_DIR = "/default2.0/";
    private final static String LOOT_CRATE_AVATAR_DIR = "/Loot_Crate2.0/";
    private final static String FILE_ENDING = ".png";
    private final static List<String> DEFAULT_AVATAR_NAMES = List.of(
            "Pencil Warrior",
            "Potato Rider",
            "Basic Bobby",
            "Generic Guy",
            "Black Ninja",
            "Tired Tiger",
            "Unremarkable Ray",
            "Chill Crusader",
            "Bland Bandit",
            "Unfinished Quest",
            "Paper Sword",
            "Monkey Fartking");
    private final static List<String> CRATE_AVATAR_NAMES = List.of(
            "Keyboard Warrior",
            "Shadow Wing",
            "Fire Storm",
            "Star Hunter",
            "Iron Fang",
            "Night Blade",
            "Frost Wolf",
            "Dark Flame",
            "Sky Rider",
            "Stone Fist",
            "Red Eagle",
            "Storm Runner",
            "Iron Claw",
            "Moon Shade",
            "Thunder Strike",
            "Blaze Wing",
            "Blue Hawk",
            "Earth Guard",
            "Wind Chaser",
            "Flame Heart",
            "Night Warden",
            "Ice Claw",
            "Steel Horn",
            "Solar Hawk",
            "Dark Warden",
            "Frost Blade",
            "Wind Shadow",
            "Rock Fang",
            "Fire Fist",
            "Night Flare",
            "Sky Blade",
            "Steel Wolf",
            "Iron Wing",
            "Ice Hunter",
            "Thunder Claw",
            "Dark Blade",
            "Stone Guard",
            "Fire Hawk",
            "Red Wolf",
            "Shadow Strike",
            "Storm Wing",
            "Sky Flame",
            "Blue Faang",
            "Night Rider",
            "Wind Blade",
            "Rock Storm",
            "Flame Claw",
            "Earth Wing",
            "Ice Shade",
            "Thunder Wing",
            "Dark Storm");

    private final static int DEFAULT_AVATAR_INDEX = 0;
    public final static Avatar DEFAULT_AVATAR = new Avatar(DEFAULT_AVATAR_NAMES.get(DEFAULT_AVATAR_INDEX),
            RESOURCES_PATH + LOOT_CRATE_AVATAR_DIR
                    + DEFAULT_AVATAR_DIR + DEFAULT_AVATAR_NAMES.get(DEFAULT_AVATAR_INDEX) + FILE_ENDING,
            true);

    /**
     * Creates a new AvatarManager
     */
    public AvatarManager() {
        // Initialize avatars
        loadDefaultAvatars();
        loadCrateAvatars();
    }

    /**
     * Adds default avatars to avatars ArrayList
     */
    private void loadDefaultAvatars() {
        for (String e : DEFAULT_AVATAR_NAMES)
            avatars.add(new Avatar(e, RESOURCES_PATH + DEFAULT_AVATAR_DIR + e + FILE_ENDING, true));

    }

    /**
     * Adds loot crate avatars to avatars ArrayList
     */
    private void loadCrateAvatars() {
        for (String e : CRATE_AVATAR_NAMES)
            avatars.add(new Avatar(e, RESOURCES_PATH + LOOT_CRATE_AVATAR_DIR + e + FILE_ENDING, false));
    }

    /**
     * Gets default avatars
     * 
     * @return ArrayList of default avatars
     */
    public ArrayList<Avatar> getDefaultAvatars() {
        ArrayList<Avatar> defaultAvatars = new ArrayList<>();
        for (Avatar avatar : avatars) {
            if (avatar.isDefault()) {
                defaultAvatars.add(avatar);
            }
        }
        return defaultAvatars;
    }

    /**
     * Gets loot crate avatars
     * 
     * @return ArrayList of loot crate avatars
     */
    public ArrayList<Avatar> getCrateAvatars() {
        ArrayList<Avatar> crateAvatars = new ArrayList<>();
        for (Avatar avatar : avatars) {
            if (!avatar.isDefault()) {
                crateAvatars.add(avatar);
            }
        }
        return crateAvatars;
    }

    /**
     * Get all avatars
     * 
     * @return ArrayList of loot crate avatars
     */
    public ArrayList<Avatar> getAllAvatars() {
        ArrayList<Avatar> availableAvatars = new ArrayList<>();
        availableAvatars.addAll(getCrateAvatars());
        availableAvatars.addAll(getDefaultAvatars());
        return availableAvatars;
    }

    /**
     * Gets avatar with matching name by iterating over all avatars contained in the
     * avatar list
     * 
     * @param name of Avatar
     * @return null or Avatar
     */
    public Avatar getAvatar(String name) {
        Iterator<Avatar> iterator = avatars.iterator();
        while (iterator.hasNext()) {
            Avatar next = iterator.next();
            if (next.getName().equals(name)) {
                return next;
            }
        }
        return null;
    }
}

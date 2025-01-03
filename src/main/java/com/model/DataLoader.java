package com.model;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The DataLoader class is responsible for loading user information
 * from User.json
 */
public class DataLoader extends DataConstants {

    public static void main(String[] args) {
        // loadWords("basics");
        loadPhrases("basics");
    }

    /**
     * Gets an ArrayList of Users by reading and parsing User data from User.json
     * 
     * @return ArrayList of Users
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            FileReader reader = new FileReader(USER_FILE_PATH);
            JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < usersJSON.size(); i++) {
                JSONObject userJSON = (JSONObject) usersJSON.get(i);
                String username = (String) userJSON.get(USER_USERNAME);
                String password = (String) userJSON.get(USER_PASSWORD);
                String firstName = (String) userJSON.get(USER_FIRSTNAME);
                String lastName = (String) userJSON.get(USER_LASTNAME);
                int level = Integer.parseInt(userJSON.get(USER_LEVEL).toString());
                UUID id = UUID.fromString((String) userJSON.get(USER_UUID));

                JSONObject avatarObj = (JSONObject) userJSON.get(USER_AVATAR);
                String avatarName = (String) avatarObj.get(USER_AVATAR_NAME);
                JSONArray unlockedAvatarsJSON = (JSONArray) avatarObj.get(USER_AVATAR_UNLOCKED);
                AvatarManager avatarManager = new AvatarManager();
                ArrayList<Avatar> unlockedAvatars = new ArrayList<>();
                for (int j = 0; j < unlockedAvatarsJSON.size(); j++) {
                    Avatar unlockedAvatar = avatarManager.getAvatar(unlockedAvatarsJSON.get(j).toString());
                    unlockedAvatars.add(unlockedAvatar);
                }
                Avatar avatar = avatarManager.getAvatar(avatarName);
                User user = new User(username, password, firstName, lastName, level, id, avatar, unlockedAvatars);
                JSONArray coursesJsonArray = (JSONArray) userJSON.get(USER_COURSES);
                for (int j = 0; j < coursesJsonArray.size(); j++) {
                    JSONObject courseObject = (JSONObject) coursesJsonArray.get(j);
                    String languageStr = (String) courseObject.get(USER_COURSES_LANGUAGE);
                    Language language = Language.valueOf(languageStr.toUpperCase());
                    user.addCourse((new Course(language, user)));
                    // TODO: Uncomment once Course can load Lessons, and add currentLesson to User
                    // int currentLesson =
                    // Integer.parseInt(courseObject.get(USER_COURSES_CURRENT_LESSON).toString());
                }
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Loads Words from Dictionary.json
     * 
     * @param subject
     * @return
     */
    public static ArrayList<Word> loadWords(String subject) {
        ArrayList<Word> wordList = new ArrayList<>();
        try {
            FileReader reader = new FileReader(DICTIONARY_FILE_PATH);
            JSONObject dictionaryJson = (JSONObject) new JSONParser().parse(reader);
            JSONArray basicsDictionaryJson = (JSONArray) dictionaryJson.get(DICTIONARY_BASICS);

            for (int i = 0; i < basicsDictionaryJson.size(); i++) {
                JSONObject wordJson = (JSONObject) basicsDictionaryJson.get(i);
                String englishWord = (String) wordJson.get(DICTIONARY_ENGLISH_WORD);
                String spanishWord = (String) wordJson.get(DICTIONARY_SPANISH_WORD);
                Word word = new Word(Language.SPANISH, englishWord, spanishWord, subject);
                wordList.add(word);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (wordList.size() < 1)
            return null;
        else
            return wordList;

    }

    /**
     * Loads Phrases from Phrasebook.json
     * 
     * @param subject
     * @return ArrayList of Phrase
     */
    public static ArrayList<Phrase> loadPhrases(String subject) {
        ArrayList<Phrase> phraseList = new ArrayList<>();
        try {
            FileReader reader = new FileReader(PHRASEBOOK_FILE_PATH);
            JSONObject dictionaryJson = (JSONObject) new JSONParser().parse(reader);
            JSONArray basicsDictionaryJson = (JSONArray) dictionaryJson.get(PHRASEBOOK_BASICS);

            for (int i = 0; i < basicsDictionaryJson.size(); i++) {
                JSONObject phraseJson = (JSONObject) basicsDictionaryJson.get(i);
                String englishphrase = (String) phraseJson.get(PHRASEBOOK_ENGLISH_WORD);
                String spanishphrase = (String) phraseJson.get(PHRASEBOOK_SPANISH_WORD);
                ArrayList<String> spanishWords = new ArrayList<String>(Arrays.asList(spanishphrase.split(" ")));
                Phrase phrase = new Phrase(Language.SPANISH, englishphrase, spanishWords, subject);
                phraseList.add(phrase);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (phraseList.size() < 1)
            return null;
        else
            return phraseList;

    }

    public static ArrayList<String> loadLessonContent() throws Exception {
        Path lessonsFolder = Paths.get("src/main/resources/lessons/");
        ArrayList<String> lessonsContent = new ArrayList<>();
        Files.list(lessonsFolder).forEach(file -> {
            try {
                String content = Files.readString(file);
                lessonsContent.add(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return lessonsContent;
    }

}

package com.model;

import java.util.ArrayList;

public class SystemFACADE {
    private static SystemFACADE instance;

    private UserList userList;
    private User currentUser;
    private Course currentCourse;
    private Lesson currentLesson;
    private Question currentQuestion;
    private AvatarManager avatarManager;

    private SystemFACADE() {
        userList = UserList.getInstance();
        avatarManager = new AvatarManager();
    }

    public static SystemFACADE getInstance() {
        if (instance == null) {
            synchronized (SystemFACADE.class) {
                if (instance == null) {
                    instance = new SystemFACADE();
                }
            }
        }
        return instance;
    }

    public boolean createAccount(String username, String password, String firstName, String lastName) {
        User newUser = new User(username, password, firstName, lastName);
        return userList.addUser(newUser);
    }

    public boolean login(String username, String password) {
        User user = userList.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            currentUser.setLoggedIn(true);
            userList.saveUser(currentUser);
            return true;
        }
        return false;
    }

    public UserList getUserList() {
        return userList;
    }

    public void logout() {
        if (currentUser != null) {
            userList.logOutUser(currentUser);
            currentUser = null;
            currentCourse = null;
            currentLesson = null;
            currentQuestion = null;
        }
    }

    public void startLearning(Language language) {
        if (currentUser != null) {
            Course newCourse = new Course(language, currentUser, currentUser.getLevel());
            currentUser.addCourse(newCourse);
            incrementUserLevel();
            userList.saveUser(currentUser);
            this.currentCourse = newCourse;
            this.currentCourse.doLesson();
        }
    }

    public void initializeCourse() {
        if (currentUser == null)
            return;
        Course course;
        if (!currentUser.getCourseList().isEmpty())
            course = currentUser.getCourseList().get(0);
        else
            course = new Course(Language.SPANISH, currentUser);
        course.generateLessons();
        this.currentCourse = course;
        userList.saveUser(currentUser);
    }

    public void setLesson(int lessonIndex) {
        if (currentUser == null)
            return;
        Lesson lesson = currentCourse.getLessons().get(lessonIndex);
        this.currentLesson = lesson;
        this.currentCourse.setCurrentLesson(lesson);
        userList.saveUser(currentUser);
    }

    public void continueLearning() {
        if (currentUser != null && this.currentCourse != null) {
            incrementUserLevel();
            this.currentCourse.doLesson();
        }
    }

    public void addCompletedLesson() {
        currentCourse.incrementCompletedLessons();
        updateUserLevel();
        userList.saveUser(currentUser);
    }

    public void updateUserLevel() {
        int completedLessons = currentCourse.getCompletedLessons();
        int newLevel = completedLessons / 10 + 1;
        currentUser.setLevel(newLevel);
        userList.saveUser(currentUser);
    }

    public void incrementUserLevel() {
        System.out.println("Incrementing user level from: " + currentUser.getLevel());
        currentUser.setLevel(currentUser.getLevel() + 1);
        userList.saveUser(currentUser);
        System.out.println("New user level: " + currentUser.getLevel());
    }

    public void setUserAvatar(String avatarName) {
        Avatar avatar = avatarManager.getAvatar(avatarName);
        if (avatar != null) {
            currentUser.setAvatar(avatar);
            userList.saveUser(currentUser);
        }
    }

    public void unlockAvatar(String avatarName) {
        Avatar avatar = avatarManager.getAvatar(avatarName);
        if (avatar != null) {
            currentUser.addUnlockedAvatar(avatar);
            userList.saveUser(currentUser);
        }
    }

    public void removeCurrentUser() {
        userList.removeUser(currentUser.getUsername());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public Lesson getCurrentLesson() {
        return currentLesson;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public Avatar getUserAvatar() {
        return userList.getAvatar(currentUser.getUsername());
    }

    public ArrayList<Avatar> getUnlockedAvatars() {
        return currentUser.getUnlockedAvatars();
    }

    public ArrayList<Course> getUserCourses() {
        return currentUser.getCourseList();
    }

}

package com.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.Avatar;
import com.model.AvatarManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AvatarController implements Initializable {
    @FXML private ComboBox<String> avatarComboBox;
    @FXML private ImageView currentAvatarView;
    @FXML private Button crateButton;
    @FXML private Button defaultButton;
    @FXML private ImageView lootCrateImage;
    @FXML private ImageView unlockedAvatarImage;
    @FXML private Button unlockButton;
    @FXML private Text unlockedAvatarText;
    
    @FXML private StackPane pane1;
    @FXML private StackPane pane2;
    @FXML private StackPane pane3;
    @FXML private StackPane pane4;
    @FXML private StackPane pane5;
    @FXML private StackPane pane6;
    
    @FXML private ImageView gridImage1;
    @FXML private ImageView gridImage2;
    @FXML private ImageView gridImage3;
    @FXML private ImageView gridImage4;
    @FXML private ImageView gridImage5;
    @FXML private ImageView gridImage6;
    
    @FXML private Text text1;
    @FXML private Text text2;
    @FXML private Text text3;
    @FXML private Text text4;
    @FXML private Text text5;
    @FXML private Text text6;
    
    private AvatarManager avatarManager;
    private ArrayList<ImageView> gridImageViews;
    private StackPane[] avatarPanes;
    private Text[] avatarTexts;
    private final String IMAGE_BASE_PATH = "src/main/resources/images/";
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatarManager = new AvatarManager();
        gridImageViews = new ArrayList<>();
        
        avatarPanes = new StackPane[]{pane1, pane2, pane3, pane4, pane5, pane6};
        gridImageViews.add(gridImage1);
        gridImageViews.add(gridImage2);
        gridImageViews.add(gridImage3);
        gridImageViews.add(gridImage4);
        gridImageViews.add(gridImage5);
        gridImageViews.add(gridImage6);
        avatarTexts = new Text[]{text1, text2, text3, text4, text5, text6};
        
        setupComboBox();
        setupButtonActions();
        setupLootCrate();
        displayDefaultAvatars();
    }
    
    private void setupComboBox() {
        avatarComboBox.getItems().clear();
        for (Avatar avatar : avatarManager.getAllAvatars()) {
            avatarComboBox.getItems().add(avatar.getName());
        }
        
        avatarComboBox.setOnAction(e -> {
            String selectedName = avatarComboBox.getValue();
            if (selectedName != null) {
                updateCurrentAvatarView(selectedName);
            }
        });
    }
    
    private void setupButtonActions() {
        crateButton.setOnAction(e -> displayCrateAvatars());
        defaultButton.setOnAction(e -> displayDefaultAvatars());
    }
    
    private void setupLootCrate() {
        String cratePath = IMAGE_BASE_PATH + "lootcrate_icon.png";
        File crateFile = new File(cratePath);
        if (crateFile.exists()) {
            lootCrateImage.setImage(new Image(crateFile.toURI().toString()));
        }
        
        unlockButton.setOnAction(e -> unlockRandomAvatar());
    }
    
    private void displayDefaultAvatars() {
        ArrayList<Avatar> defaultAvatars = avatarManager.getDefaultAvatars();
        displayAvatars(defaultAvatars);
    }
    
    private void displayCrateAvatars() {
        ArrayList<Avatar> crateAvatars = avatarManager.getCrateAvatars();
        displayAvatars(crateAvatars);
    }
    
    private void displayAvatars(ArrayList<Avatar> avatars) {
        for (int i = 0; i < 6 && i < avatars.size(); i++) {
            Avatar avatar = avatars.get(i);
            String imagePath = IMAGE_BASE_PATH + 
                             (avatar.isDefault() ? "default2.0/" : "Loot_Crate2.0/") + 
                             avatar.getName() + ".png";
            
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                gridImageViews.get(i).setImage(image);
                avatarTexts[i].setText(avatar.getName());
            }
        }
    }
    
    private void updateCurrentAvatarView(String avatarName) {
        Avatar selectedAvatar = avatarManager.getAvatar(avatarName);
        String imagePath = IMAGE_BASE_PATH + 
                         (selectedAvatar.isDefault() ? "default2.0/" : "Loot_Crate2.0/") + 
                         selectedAvatar.getName() + ".png";
        
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            currentAvatarView.setImage(image);
        }
    }
    
    private void unlockRandomAvatar() {
        ArrayList<Avatar> crateAvatars = avatarManager.getCrateAvatars();
        if (!crateAvatars.isEmpty()) {
            int randomIndex = (int)(Math.random() * crateAvatars.size());
            Avatar unlockedAvatar = crateAvatars.get(randomIndex);
            
            String avatarPath = IMAGE_BASE_PATH + "Loot_Crate2.0/" + unlockedAvatar.getName() + ".png";
            File avatarFile = new File(avatarPath);
            if (avatarFile.exists()) {
                unlockedAvatarImage.setImage(new Image(avatarFile.toURI().toString()));
                unlockedAvatarText.setText(unlockedAvatar.getName());
            }
        }
    }
}

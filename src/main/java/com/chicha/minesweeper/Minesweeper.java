    package com.chicha.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;


import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

    public class Minesweeper extends Application {
    private static Menu menu;
    private static Map field;
    private static Stage stage;
    private static Cipher cipher;
    private static SecretKey key;

    @Override
    public void start(Stage stage) {
        try {
            PBEKeySpec keySpec = new PBEKeySpec("12345678".toCharArray());
            key = new SecretKeySpec("770A8A65DA156D24EE2A093277530142".getBytes(), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Minesweeper.stage = stage;
        Minesweeper.stage.setScene(openMenu());
        Minesweeper.stage.show();
        Minesweeper.stage.setTitle("Minesweeper");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void showDialog(boolean win){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (win) {
                alert.setTitle("Game over");
                alert.setHeaderText("You won!");
                alert.setContentText("Your score is: " + field.getScore() + "\nRestart or exit");
            } else {
                alert.setTitle("Game Over");
                alert.setHeaderText("Game Over");
                alert.setContentText("Enter your name for leaderboard");
            }
            ButtonType replayButton = new ButtonType("Replay");
            ButtonType menuButton = new ButtonType("Menu");
            alert.getButtonTypes().add(replayButton);
            alert.getButtonTypes().set(0, new ButtonType("Exit"));
            alert.getButtonTypes().add(menuButton);
            Optional<ButtonType> result = alert.showAndWait();
            try {
                writeScore(field.getScore());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result.isPresent() && result.get() == replayButton) {
                startGame();
            } else if (result.isPresent() && result.get() == menuButton){
                stage.setScene(openMenu());
            } else {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private static void startGame() {
        Events.activate();
        BorderPane root = new BorderPane();
        field = new Map(menu.getDifficulty().getValue());
        field.drawMap();
        root.setCenter(field);
        Minesweeper.stage.setScene(new Scene(root));
        Thread thread = new Thread(() -> {
            while (true){
                Platform.runLater(()->{
                    stage.setTitle("Score: " + field.getScore());
                });
                try {
                    Thread.sleep(500);
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        thread.start();
        switch (menu.getDifficulty().getValue()){
            case EASY: {
                stage.setX(540);
                stage.setY(200);
                break;
            }
            case MEDIUM: {
                stage.setX(350);
                stage.setY(50);
                break;
            }
            case HARD: {
                stage.setX(0);
                stage.setY(50);
                break;
            }
        }
    }

    private static Scene openMenu(){
        menu = new Menu();
        menu.setAlignment(Pos.CENTER);
        menu.getStartButton().setOnAction(actionEvent -> startGame());
        menu.getHighScoreButton().setOnAction(actionEvent -> {
            if (!Objects.equals(getLeaderboard(), "")){
                String[] highScores = getLeaderboard().split("\\|");
                for (String highScore:
                        highScores) {
                    String[] splittedScore = highScore.split(";");
                    System.out.println("Map: " + splittedScore[0] + " | score: " + splittedScore[1] + "\n");
                }
            }
        });
        return new Scene(menu, 500, 400);
    }

    private static void writeScore(int score) throws Exception {
        String string = getLeaderboard();
        java.util.Map<String,String> leaderboard = new HashMap<>();
        if (!Objects.equals(string,"")){
            for (String line :
                    string.split("\\|")) {
                leaderboard.put(line.split(";")[0], line.split(";")[1]);
            }
        }
        if (leaderboard.containsKey(field.toString())){
            if (score > Integer.parseInt(leaderboard.get(field.toString()))){
                leaderboard.put(field.toString(), String.valueOf(score));
            }
        } else {
            leaderboard.put(field.toString(), String.valueOf(score));
        }
        StringBuilder sb = new StringBuilder();
        for (java.util.Map.Entry<String,String> entry:
             leaderboard.entrySet()) {
            sb.append(entry.getKey()).append(";").append(entry.getValue()).append("|");
        }
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        try (FileOutputStream fOut = new FileOutputStream("data.enc");
             CipherOutputStream cOut = new CipherOutputStream(fOut, cipher)){
            cOut.write(sb.toString().getBytes());
        }
    }

    private static String getLeaderboard(){
        try (FileInputStream fIn = new FileInputStream("data.enc")){
            IvParameterSpec fIv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, key, fIv);
            try(
                    CipherInputStream cIn = new CipherInputStream(fIn, cipher);
                    InputStreamReader inputReader = new InputStreamReader(cIn);
                    BufferedReader br = new BufferedReader(inputReader)
                    ){
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine())!= null){
                    sb.append(line);
                }
                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
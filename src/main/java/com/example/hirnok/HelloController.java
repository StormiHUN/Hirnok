package com.example.hirnok;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HelloController {


    public Pane pane;
    public Label count;
    public ProgressBar progress;


    Label player = new Label();
    Label enemy = new Label();
    Label home = new Label();
    Label paper = new Label();

    int playerX = 64;
    int playerY = 64;
    int enemyX = 664;
    int enemyY = 64;
    int paperX = (int)Math.floor(Math.random()*661+32);
    int paperY = (int)Math.floor(Math.random()*461+32);
    float hp = 1;

    int targetX = 0;
    int targetY = 0;
    boolean clicked = false;
    int db = 0;

    public int clamp(int min, int max, int value){
        if(value<min) return min;
        else if(value>max) return max;
        return value;
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            pane.setOnMouseClicked(MouseEvent -> {
                targetX = (int)MouseEvent.getX();
                targetY = (int)MouseEvent.getY();
            });
            if (clicked){
                if (Math.abs(playerX-100) <= 52 && Math.abs(playerY-500) <= 52){
                    if(hp < 1){
                        hp+=0.005;
                        progress.setProgress(hp);
                    }
                    home.setRotate(home.getRotate()+1);
                    moveEnemy(1);
                }else{
                    moveEnemy(0);
                }
                if(Math.abs(playerY-enemyY) < 60 && Math.abs(playerX-enemyX) < 60){
                    hp-=0.02;
                    progress.setProgress(hp);
                    if (hp <= 0){
                        timer.stop();
                        vege();
                    }
                }
                if(Math.abs(playerX-paperX) < 64 && Math.abs(playerY-paperY) < 64){
                    db++;
                    paperX = (int)Math.floor(Math.random()*661+32);
                    paperY = (int)Math.floor(Math.random()*461+32);
                    paper.setLayoutY(paperY);
                    paper.setLayoutX(paperX);
                    count.setText(db+" db");
                }

                movePlayer();
            }
        }
    };

    public void vege(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hírnök - Vesztettél");
        alert.setHeaderText(null);
        alert.setContentText(String.format("%d darab tervet gyüjtöttél össze!",db));
        alert.show();
    }

    public void movePlayer(){
        double deltaX = targetX - playerX;
        double deltaY = targetY - playerY;
        double angleRadians = Math.atan2(deltaY, deltaX);
        playerX += 4 * Math.cos(angleRadians);
        playerY += 4 * Math.sin(angleRadians);
        player.setLayoutY(playerY);
        player.setLayoutX(playerX);
    }

    public void moveEnemy(int state){
        if (state == 1){
            if (enemyX < 700){
                enemyX++;
            }else if(enemyX > 700){
                enemyX--;
            }
            if(enemyY < 100){
                enemyY++;
            }else if(enemyY > 100){
                enemyY--;
            }
            enemy.setLayoutX(enemyX);
            enemy.setLayoutY(enemyY);
        }else{
            if (enemyX < playerX){
                enemyX++;
            }else if(enemyX > playerX){
                enemyX--;
            }
            if(enemyY < playerY){
                enemyY++;
            }else if(enemyY > playerY){
                enemyY--;
            }
            enemy.setLayoutX(enemyX);
            enemy.setLayoutY(enemyY);
        }
    }

    public void initialize(){
        player.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/player.png"))));
        enemy.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/bad.png"))));
        home.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/home.png"))));
        paper.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/paper.png"))));
        player.setPrefSize(72,72);
        enemy.setPrefSize(72,72);
        home.setPrefSize(96,96);
        paper.setPrefSize(64,64);
        player.setLayoutY(playerY);
        player.setLayoutX(playerX);
        enemy.setLayoutX(enemyX);
        enemy.setLayoutY(enemyY);
        paper.setLayoutY(paperY);
        paper.setLayoutX(paperX);
        home.setLayoutX(52);
        home.setLayoutY(452);
        pane.getChildren().add(player);
        pane.getChildren().add(enemy);
        pane.getChildren().add(home);
        pane.getChildren().add(paper);
        timer.start();
    }

    public void setTargetPosition() {
        clicked=true;
    }
}
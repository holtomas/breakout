/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamebricks;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.ENTER;

import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.SPACE;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.stage.Stage;
import javafx.util.Duration;

public class GameBricks extends Application {

    static int pocetbloku = 15;
    static int pocetbloku1 = pocetbloku;
    static int vzdalenostbloku = 100;
    Timeline loop;

    static int rychlostdesticky = 25;
    static Rectangle wall5;
    static Circle circle;
    static Pane canvas;
    static boolean vlevo;
    static boolean vpravo;
    static Rectangle[] wall = new Rectangle[4];
    static int velikostdesek = 10;
    static int rada1 = 200;
    static int skore = 0;
    static int minX = 100;
    static int maxX = 200;
    static int n = 0;
    static Label lb1;
    static Label lb2;
    static int lives = 3;
    boolean play = false;
    boolean play1 = true;
    double deltaX;
    double deltaY;
    boolean[] jeblock = new boolean[pocetbloku];
    static Rectangle[] block = new Rectangle[pocetbloku];
    static int remainingblocks = pocetbloku;
    Random rdn = new Random();
               // vytvoření obdelníků do hracího pole
    public void blocks(Rectangle e[]) {
        for (int i = 0; i < block.length; i++) {

            int t = rdn.nextInt(maxX - minX) + minX;
            block[i] = new Rectangle(vzdalenostbloku + n, rada1, t, 50);
            canvas.getChildren().add(block[i]);
            double d = n + block[i].getWidth();
            double o = wall[2].getX() - vzdalenostbloku - block[i].getX();
            if (d > wall[2].getX() - vzdalenostbloku) {
                n = 0;
                rada1 = rada1 + 60;
                block[i].setX(vzdalenostbloku + n);
                block[i].setY(rada1);
            }
            if (d < wall[2].getX() - vzdalenostbloku && d + 200 > wall[2].getX() - vzdalenostbloku) {
                block[i].setWidth(o);
            }
            if (pocetbloku == 1) {
                block[i].setWidth(wall[2].getX() - vzdalenostbloku - block[i].getX());
            }

            n = t + 10 + n;
            pocetbloku--;
            jeblock[i] = true;

        }
    }

    @Override
    public void start(Stage primaryStage) {

        canvas = new Pane();
        lb1 = new Label();
        lb1.setFont(new Font("Ariel", 18));
        lb1.setLayoutX(50);
        lb1.setLayoutY(50);
        lb2 = new Label();
        lb2.setFont(new Font("Ariel", 18));
        lb2.setLayoutX(50);
        lb2.setLayoutY(90);
        canvas.getChildren().add(lb1);
        canvas.getChildren().add(lb2);
        Scene scene = new Scene(canvas);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(1000);
        primaryStage.setMinHeight(1000);
        primaryStage.setMinWidth(1000);
        primaryStage.show();

        primaryStage.setFullScreen(false);

        int X = rdn.nextInt((int) (scene.getWidth() - 200)) + 100;

        circle = new Circle(10, Color.BLUE);
        circle.relocate(X, 700);

        wall[0] = new Rectangle(0, 0, canvas.getWidth(), velikostdesek);
        wall[0].setFill(Color.RED);
        wall[1] = new Rectangle(0, 0, velikostdesek, canvas.getHeight());
        wall[1].setFill(Color.BLUE);
        wall[2] = new Rectangle(canvas.getWidth() - velikostdesek, 0, velikostdesek, canvas.getHeight());

        wall[2].setFill(Color.BLUE);

        wall[3] = new Rectangle(800, 1000, 100, 10);
        wall[3].setFill(Color.GREEN);

        canvas.getChildren().add(circle);
        canvas.getChildren().add(wall[0]);
        canvas.getChildren().add(wall[1]);
        canvas.getChildren().add(wall[2]);

        canvas.getChildren().add(wall[3]);

        blocks(block);

        scene.setOnKeyPressed((KeyEvent event) -> {
            // spusteni hry
            if (event.getCode() == SPACE && play1) {
                int rychlostX;
                for (;;) {
                    rychlostX = rdn.nextInt(2);
                    if (rychlostX != 0) {
                        break;
                    }
                }

                play = true;
                play1 = false;
                deltaY = -1;
                if (rychlostX == 1) {
                    deltaX = +1;
                } else {
                    deltaX = -1;
                }
            }
            // ovladani pocitacem
            if (event.getCode() == ENTER && play) {

                double DestickaStredX = wall[3].getLayoutX() + wall[3].getX() + wall[3].getWidth() / 2;
                double stredmickuX = circle.getLayoutX();
                double i;
                if (deltaY > 0) {
                    if (deltaX == 0) {
                        i = 0;
                    } else {
                        i = deltaY / deltaX;
                    }

                    if (i > 0 && stredmickuX > DestickaStredX) {
                        double o = wall[2].getX() - (wall[3].getX() + wall[3].getLayoutX() + wall[3].getWidth());
                        if (o > rychlostdesticky) {
                            wall[3].setLayoutX(wall[3].getLayoutX() + rychlostdesticky);
                        } else {
                            wall[3].setLayoutX(wall[3].getLayoutX() + i);
                        }

                    }
                    if (i < 0 && stredmickuX < DestickaStredX) {
                        double o = (wall[3].getX() + wall[3].getLayoutX() - wall[1].getX() - wall[1].getWidth());
                        if (o > rychlostdesticky) {

                            wall[3].setLayoutX(wall[3].getLayoutX() - rychlostdesticky);

                        } else {
                            wall[3].setLayoutX(wall[3].getLayoutX() - o);
                        }

                    }
                    if (i == 0 && stredmickuX < DestickaStredX) {
                        wall[3].setLayoutX(wall[3].getLayoutX() - rychlostdesticky);
                    }
                    if (i == 0 && stredmickuX > DestickaStredX) {
                        wall[3].setLayoutX(wall[3].getLayoutX() + rychlostdesticky);
                    }
                }

            }
                    //pohyb doleva
            if (event.getCode() == LEFT && play) {
                vlevo = true;
                // wall[1] = leva stena
                double i = (wall[3].getX() + wall[3].getLayoutX() - wall[1].getX() - wall[1].getWidth());
                if (i >= rychlostdesticky) {
                    wall[3].setLayoutX(wall[3].getLayoutX() - rychlostdesticky);

                } else {
                    wall[3].setLayoutX(wall[3].getLayoutX() - i);
                }

            } else {

                vlevo = false;
            }
                 //pohyb doprava
            if (event.getCode() == RIGHT && play) {
                vpravo = true;
                // wall[2] = prava stena
                double i = wall[2].getX() - (wall[3].getX() + wall[3].getLayoutX() + wall[3].getWidth());
                if (i >= rychlostdesticky) {
                    wall[3].setLayoutX(wall[3].getLayoutX() + rychlostdesticky);
                } else {
                    wall[3].setLayoutX(wall[3].getLayoutX() + i);
                }
            } else {
                vpravo = false;
            }

        });
                //vytvoreni timelinu v kterem probiha akce kazdych 5 milisekund
        loop = new Timeline(new KeyFrame(Duration.millis(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // pridani ukazetulu zivotu a skore do hraciho pole
                lb1.setText("skóre: " + skore);
                lb2.setText("životy: " + lives);
                  //nastaveni presnych pozic desticky
                wall[3].setY(scene.getHeight() - velikostdesek);
                wall[3].setX((scene.getWidth()) / 2 - (wall[3].getWidth() / 2));
                double l = wall[3].getY() - circle.getRadius() - circle.getLayoutY();
                  //pohybkulicky
                circle.setLayoutY(circle.getLayoutY() + deltaY);
                circle.setLayoutX(circle.getLayoutX() + deltaX);
                //vytvoreni neviditelneho ctverce s velikostmi kulicky 
                Rectangle rectangle = new Rectangle(circle.getLayoutX() - circle.getRadius(), circle.getLayoutY() - circle.getRadius(), 2 * circle.getRadius(), 2 * circle.getRadius());
                //podminky pro odraz kulicky 
                Bounds bounds = canvas.getBoundsInLocal();
                boolean leftWall = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius() + velikostdesek);
                boolean topWall = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius()) + velikostdesek;
                boolean rightWall = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius() - velikostdesek);
                     //podminky pro odraz kulicky od desticky
                boolean odraz = Math.round(circle.getLayoutY()) == (wall[3].getY() - circle.getRadius());
                boolean odraz1 = circle.getLayoutX() >= (wall[3].getX() + wall[3].getLayoutX() + circle.getRadius());
                boolean odraz2 = circle.getLayoutX() <= (wall[3].getX() + wall[3].getLayoutX() + wall[3].getWidth() + circle.getRadius());
                    
                if (odraz1 && odraz2) {
                    if (l < deltaY) {
                        circle.setLayoutY(circle.getLayoutY() + l);

                    }
                }
                       //podminky pro odraz kulicky od obdelniku
                for (int i = 0; i < block.length; i++) {
                    boolean odraz3 = circle.getLayoutY() >= block[i].getY() - circle.getRadius();
                    boolean odraz4 = circle.getLayoutY() <= block[i].getY() + block[i].getHeight() - circle.getRadius();
                    boolean odraz5 = circle.getLayoutX() < block[i].getX() + 3;
                    boolean odraz6 = circle.getLayoutX() > block[i].getX() + block[i].getWidth() - 3;
                    boolean odraz7 = circle.getLayoutX() <= (block[i].getX() + block[i].getWidth() - circle.getRadius());
                    boolean odraz8 = circle.getLayoutY() < (block[i].getY() + 3);
                    boolean odraz10 = circle.getLayoutX() >= (block[i].getX() + circle.getRadius());
                    boolean odraz9 = circle.getLayoutY() > (block[i].getY() + block[i].getHeight() - 2);
                    boolean kolize = rectangle.intersects(block[i].getBoundsInLocal());
                    //odraz od rohu
                    boolean odraz11 = Math.round(circle.getLayoutX()) + circle.getRadius() == block[i].getX();
                    boolean odraz12 = Math.round(circle.getLayoutX()) + circle.getRadius() == block[i].getX() + block[i].getWidth();
                    boolean odraz13 = Math.round(circle.getLayoutY()) + circle.getRadius() == block[i].getY();
                    boolean odraz14 = Math.round(circle.getLayoutY()) + circle.getRadius() == block[i].getY() + block[i].getHeight();
                    if (odraz11 && odraz13 && jeblock[i]) {
                        deltaX = -deltaX;
                        deltaY = -deltaY;
                    }
                    if (odraz11 && odraz14 && jeblock[i]) {
                        deltaX = -deltaX;
                        deltaY = -deltaY;
                    }
                    if (odraz12 && odraz13 && jeblock[i]) {
                        deltaX = -deltaX;
                        deltaY = -deltaY;
                    }
                    if (odraz12 && odraz14 && jeblock[i]) {
                        deltaX = -deltaX;
                        deltaY = -deltaY;
                    }
                    //odrazzleva
                    if (odraz3 && odraz4 && odraz5 && jeblock[i] && kolize) {
                        deltaX = deltaX * -1;
                        canvas.getChildren().remove(block[i]);
                        jeblock[i] = false;
                        skore++;
                        remainingblocks--;
                    }
                    //odrazzprava
                    if (odraz3 && odraz4 && odraz6 && jeblock[i] && kolize) {
                        deltaX = deltaX * -1;
                        canvas.getChildren().remove(block[i]);
                        jeblock[i] = false;
                        skore++;
                        remainingblocks--;
                    }

                    // odraz zezhora
                    if (odraz10 && odraz7 && odraz8 && jeblock[i] && kolize) {
                        deltaY = deltaY * -1;
                        canvas.getChildren().remove(block[i]);
                        jeblock[i] = false;
                        skore++;
                        remainingblocks--;
                    }

                    //  odraz zespoda
                    if (odraz7 && odraz10 && odraz9 && jeblock[i] && kolize) {
                        deltaY = deltaY * -1;
                        canvas.getChildren().remove(block[i]);
                        jeblock[i] = false;
                        skore++;
                        remainingblocks--;

                    }
                }
                         //pridani zrychleni micku pri dotiku desticky
                if (odraz && odraz1 && odraz2) {
                    deltaY = deltaY * -1;
                    if (vpravo && deltaX > 2) {
                        deltaX = 2;
                    }
                    if (vlevo && deltaX < -2) {
                        deltaX = -2;
                    }
                    if (vlevo && deltaX < 0) {
                        deltaX = deltaX * 1.1;
                    }
                    if (vlevo && deltaX == 0) {
                        deltaX = -0.3;
                    }
                    if (vlevo && deltaX > 0) {
                        deltaX = deltaX * 0.9;
                    }
                    if (vpravo && deltaX == 0) {
                        deltaX = 0.3;
                    }
                    if (vpravo && deltaX > 0) {
                        deltaX = deltaX * 1.1;
                    }
                    if (vpravo && deltaX < 0) {
                        deltaX = deltaX * 0.9;
                    }
                }
                    //odraz od horni steny
                if (topWall) {

                    deltaY = deltaY * -1;
                }
                     //odraz od prave strany nebo leve
                if (rightWall || leftWall) {
                    deltaX = deltaX * -1;
                }
                        //nove vygenerovana pozice micku po strate zivota
                if (circle.getLayoutY() > scene.getHeight()) {
                    lives--;
                    play1 = true;
                    play = false;
                    deltaX = 0;
                    deltaY = 0;
                    int X = rdn.nextInt((int) (scene.getWidth() - 200)) + 100;
                    circle.relocate(X, 700);

                }
                // nove vygenerovani obdelniku  kdyz budou vsechny zniceny
                if (remainingblocks == 0) {
                    play1 = true;
                    play = false;
                    deltaX = 0;
                    deltaY = 0;
                    int X = rdn.nextInt((int) (scene.getWidth() - 200)) + 100;
                    circle.relocate(X, 700);

                    n = 0;
                    rada1 = 200;
                    pocetbloku = pocetbloku1;
                    remainingblocks = pocetbloku;
                    blocks(block);

                }
                   //konec hry
                if (lives == 0) {

                    primaryStage.close();
                    Pane canvas1 = new Pane();
                    Scene scene1 = new Scene(canvas1);
                    lb1.setText("finální skóre: " + skore);
                    canvas1.getChildren().add(lb1);

                    Stage st = new Stage();
                    st.setScene(scene1);
                    st.setMaxWidth(400);
                    st.setMaxHeight(200);
                    st.setMinHeight(200);
                    st.setMinWidth(400);
                    st.show();
                    loop.stop();

                }

            }

        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public static void main(final String[] args) {

        launch(args);
    }
}

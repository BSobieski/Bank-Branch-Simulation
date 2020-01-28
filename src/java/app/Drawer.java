package app;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import objects.Bank;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Drawer extends Thread{
    private boolean draw = false;
    private Pane drawingPane;
    private Bank bank;
    private static int okienkoWidth = 80;
    private static int okienkoHeight = 30;
    private static int koniecOkienekHeight = 0;
    private static int klientRadius = 17;
    private ArrayList<Color> colors;
    private Slider slider;
    private String iconPath = "alert.png";

    public Drawer(Pane drawingPane, Bank bank, Slider slider) {
        this.slider = slider;
        this.drawingPane = drawingPane;
        this.bank = bank;
        colors = new ArrayList<>();

        Random rand = new Random();

        //Stworzenie tablicy kolorów dla oznaczenia priorytetow
        for(int x = 0; x<=bank.getEnvironment().ilosc_priorytetow; x++) {
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            colors.add(Color.rgb(r,g,b));
        }
    }

    @Override
    public void run() {
        while(draw) {
            Platform.runLater(() -> {
                drawingPane.getChildren().clear();
                bank.getSimManager().setSimTimeRatio(slider.getValue());
                Rectangle poleTechniczne = new Rectangle(0, 0, 500, 250);
                poleTechniczne.setFill(Paint.valueOf("#f05c15"));
                Label podpisTechniczny = new Label("Kolejka Techniczna");
                podpisTechniczny.setLayoutX(10);
                podpisTechniczny.setLayoutY(225);
                podpisTechniczny.setFont(new Font("Arial", 20));
                //podpisTechniczny.setRotate(90);
                podpisTechniczny.setTextFill(Paint.valueOf("#000000"));

                Label podpisKolejki = new Label("Kolejka");
                podpisKolejki.setLayoutX(10);
                podpisKolejki.setLayoutY(775);
                podpisKolejki.setFont(new Font("Arial", 20));
                //podpisTechniczny.setRotate(90);
                podpisKolejki.setTextFill(Paint.valueOf("#000000"));

                Line line = new Line();
                line.setStartX(500);
                line.setStartY(0);
                line.setEndX(500);
                line.setEndY(800);
                drawingPane.getChildren().add(podpisKolejki);
                drawingPane.getChildren().add(poleTechniczne);
                drawingPane.getChildren().add(podpisTechniczny);
                drawingPane.getChildren().add(line);


                //Rysowanie okienek
                int currX = 873 - okienkoWidth - okienkoWidth -okienkoWidth - 20, currY = 10;
                for (int x = 0; x < bank.getOkienka().length;  x++) {
                    if(currY + okienkoHeight > 800) {
                        currX = 873 - okienkoWidth - 10;
                        currY = 10;
                    }
                    Rectangle rectangle = new Rectangle(currX, currY, okienkoWidth, okienkoHeight);
                    if(bank.getOkienka()[x].isAwaria()) {
                        //rectangle.setFill(Paint.valueOf("#9e0000"));
                        try
                        {
                            drawAlertIcon(currX,currY);
                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    drawingPane.getChildren().add(rectangle);
                    Label label = new Label("" + x);
                    label.setLayoutX(currX + 35);
                    label.setLayoutY(currY + 10);
                    label.setFont(new Font("Arial", 15));
                    label.setTextFill(Paint.valueOf("#ffffff"));
                    drawingPane.getChildren().add(label);

                    //Ryswoanie kilienta przy okienku
                    if(!bank.getOkienka()[x].isWolne() && !bank.getOkienka()[x].isAwaria()) {
                        Circle circle = new Circle(currX - okienkoWidth*0.5 + 10, currY + klientRadius - 3, klientRadius);
                        circle.setFill(Paint.valueOf("#03fc90"));
                        circle.setStroke(Paint.valueOf("#000000"));
                        drawingPane.getChildren().add(circle);
                    }

                    currY+=okienkoHeight*2;
                }

                koniecOkienekHeight = 30;

                currX = 30;
                currY = 30;
                for (int x = 0; x < bank.getCustomerQueueTechniczna().getSize(); x++) {
                    if (currX + klientRadius * 2 > 500) {
                        currX = 30;
                        currY += 50;
                    }

                    drawKlient(currX, currY, x);

                    currX += 50;
                }

                currX = 30;
                currY = 300;
                //Rysowanie kolejki
                for (int x = 0; x < bank.getCustomerQueue().getSize(); x++) {
                    if (currX + klientRadius * 2 > 500) {
                        currX = 30;
                        currY += 50;
                    }

                    drawKlient(currX, currY, x);

                    currX += 50;

                }

            });

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawAlertIcon(int currX, int currY) throws FileNotFoundException
    {
        Image image = new Image(iconPath);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setX(currX - 25);
        imageView.setY(currY + 2);
        drawingPane.getChildren().add(imageView);
    }

    private void drawKlient(int currX, int currY, int x) {
        Circle circle = new Circle(currX, currY, klientRadius);
        circle.setFill(Paint.valueOf("#03fc90"));
        circle.setStroke(Paint.valueOf("#000000"));
        drawingPane.getChildren().add(circle);

        if (bank.getCustomerQueue().getSize() > x) {
            int priorytet = bank.getCustomerQueue().at(x).getPriorytet();
            Label label = new Label(String.valueOf(priorytet));
            if (priorytet > 9)
            {
                label.setLayoutX(currX-8);
                label.setLayoutY(currY-8);
            }
            else
            {
                label.setLayoutX(currX-4);
                label.setLayoutY(currY-8);
            }
            label.setFont(new Font("Arial", 15));
            drawingPane.getChildren().add(label);
        }
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
}

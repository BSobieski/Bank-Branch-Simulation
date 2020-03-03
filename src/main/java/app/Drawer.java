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

import java.util.ArrayList;
import java.util.Random;

public class Drawer extends Thread {
    private boolean draw = false;
    private Pane drawingPane;
    private Bank bank;
    private static final int windowWidth = 80;
    private static final int windowHeight = 30;
    private static final int startWindowX = 613;
    private static final int startWindowY = 10;
    private static final int customerCircleRadius = 17;
    private Slider slider;

    Drawer(Pane drawingPane, Bank bank, Slider slider) {
        this.slider = slider;
        this.drawingPane = drawingPane;
        this.bank = bank;
        ArrayList<Color> colors = new ArrayList<>();

        Random rand = new Random();

        for (int x = 0; x <= bank.getEnvironment().numberOfPriorities; x++) {
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            colors.add(Color.rgb(r, g, b));
        }
    }

    @Override
    public void run() {
        while (draw) {
            Platform.runLater(() -> {
                recreatePane();
                paintWindows();
                paintTechnicalQueue();
                paintQueue();
            });
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void paintQueue() {
        int currentX = 30;
        int currentY = 300;
        for (int i = 0; i < bank.getCustomerQueue().getSize(); i++) {
            if (currentX + customerCircleRadius * 2 > 500) {
                currentX = 30;
                currentY += 50;
            }
            drawClient(currentX, currentY, i);
            currentX += 50;
        }
    }

    private void paintTechnicalQueue() {
        int currentX = 30;
        int currentY = 30;
        for (int i = 0; i < bank.getCustomerTechnicalQueue().getSize(); i++) {
            if (currentX + customerCircleRadius * 2 > 500) {
                currentX = 30;
                currentY += 50;
            }
            drawClient(currentX, currentY, i);
            currentX += 50;
        }
    }

    private void paintWindows() {
        int currentX = startWindowX, currentY = startWindowY;
        for (int i = 0; i < bank.getWindowsTab().length; i++) {
            if (currentY + windowHeight > 800) {
                currentX = 873 - windowWidth - 10;
                currentY = 10;
            }
            Rectangle rectangle = new Rectangle(currentX, currentY, windowWidth, windowHeight);

            if (bank.getWindowsTab()[i].isBroken()) {
                drawAlertIcon(currentX, currentY);
            }
            drawingPane.getChildren().add(rectangle);

            Label label = new Label("" + i);
            label.setLayoutX(currentX + 35);
            label.setLayoutY(currentY + 10);
            label.setFont(new Font("Arial", 15));
            label.setTextFill(Paint.valueOf("#ffffff"));
            drawingPane.getChildren().add(label);

            if (!bank.getWindowsTab()[i].isAvaliable() && !bank.getWindowsTab()[i].isBroken()) {
                Circle circle = new Circle(currentX - windowWidth * 0.5 + 10, currentY + customerCircleRadius - 3, customerCircleRadius);
                circle.setFill(Paint.valueOf("#03fc90"));
                circle.setStroke(Paint.valueOf("#000000"));
                drawingPane.getChildren().add(circle);
            }
            currentY += windowHeight * 2;
        }
    }

    private void recreatePane() {
        initPane();

        Rectangle technicalField = createTechnicalField();
        Label technicalLabel = createTechnicalLabel();
        Label queueLabel = createQueueLabel();
        Line borderLine = createBorderLine();

        drawingPane.getChildren().add(queueLabel);
        drawingPane.getChildren().add(technicalField);
        drawingPane.getChildren().add(technicalLabel);
        drawingPane.getChildren().add(borderLine);
    }

    private void initPane() {
        drawingPane.getChildren().clear();
        bank.getSimManager().setSimTimeRatio(slider.getValue());
    }

    private Rectangle createTechnicalField() {
        Rectangle technicalField = new Rectangle(0, 0, 500, 250);
        technicalField.setFill(Paint.valueOf("#f05c15"));
        return technicalField;
    }

    private Line createBorderLine() {
        Line borderLine = new Line();
        borderLine.setStartX(500);
        borderLine.setStartY(0);
        borderLine.setEndX(500);
        borderLine.setEndY(800);
        return borderLine;
    }

    private void drawAlertIcon(int currX, int currY) {
        String iconPath = "alert.png";
        Image image = new Image(iconPath);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setX(currX - 25);
        imageView.setY(currY + 2);
        drawingPane.getChildren().add(imageView);
    }

    private void drawClient(int currX, int currY, int x) {
        Circle circle = new Circle(currX, currY, customerCircleRadius);
        circle.setFill(Paint.valueOf("#03fc90"));
        circle.setStroke(Paint.valueOf("#000000"));
        drawingPane.getChildren().add(circle);

        if (bank.getCustomerQueue().getSize() > x) {
            int priority = bank.getCustomerQueue().at(x).getPriority();
            Label label = new Label(String.valueOf(priority));
            if (priority > 9) {
                label.setLayoutX(currX - 8);
                label.setLayoutY(currY - 8);
            } else {
                label.setLayoutX(currX - 4);
                label.setLayoutY(currY - 8);
            }
            label.setFont(new Font("Arial", 15));
            drawingPane.getChildren().add(label);
        }
    }

    void setDraw() {
        this.draw = true;
    }

    private Label createTechnicalLabel() {
        Label technicalLabel = new Label("Technical Queue");
        technicalLabel.setLayoutX(10);
        technicalLabel.setLayoutY(225);
        technicalLabel.setFont(new Font("Arial", 20));
        technicalLabel.setTextFill(Paint.valueOf("#000000"));
        return technicalLabel;
    }

    private Label createQueueLabel() {
        Label queueLabel = new Label("Queue");
        queueLabel.setLayoutX(10);
        queueLabel.setLayoutY(775);
        queueLabel.setFont(new Font("Arial", 20));
        queueLabel.setTextFill(Paint.valueOf("#000000"));
        return queueLabel;
    }
}

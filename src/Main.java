import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public Slider slider = new Slider(1, 10, 5);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(840, 840);
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: white;");
        vBox.getChildren().add(getHBox1(canvas));
        vBox.getChildren().add(getHBox2(canvas));
        stage.setTitle("Slow, Draw!");
        Scene scene = new Scene(vBox, canvas.getWidth() - 10, canvas.getHeight() + 30);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public HBox getHBox1(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        //graphicsContext.setLineWidth(slider.getValue());
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            graphicsContext.setLineWidth(slider.getValue());
            graphicsContext.beginPath();
            graphicsContext.moveTo(e.getX(), e.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            graphicsContext.lineTo(e.getX(), e.getY());
            graphicsContext.stroke();
        });
        HBox hBox = new HBox();
        hBox.getChildren().add(canvas);
        hBox.setStyle("-fx-border-color: black;");
        return hBox;
    }

    public HBox getHBox2(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 12, 0, 300));
        hBox.setSpacing(10);

        Button erase = new Button("Erase");
        erase.setStyle("-fx-background-color: #ff0000; -fx-font-weight: bold; -fx-text-fill: white;");
        erase.setOnAction(e -> {
            if (erase.getText().equals("Erase")) {
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                slider.setValue(5);
            }
        });

        //Slider slider = new Slider(1, 10, 5);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);

        Button guess = new Button("Guess");
        guess.setStyle("-fx-background-color: turquoise; -fx-font-weight: bold;");
        guess.setOnAction(e -> {
            if (guess.getText().equals("Guess")) {
                //steps to be taken once this button is pressed
                //resize the image
                //save the image
                //send it to ml module
                //get the guessed answer
                //display it to user
                //graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                try {
                    WritableImage snapshot = canvas.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("a.png"));
                } catch (IOException exception) {

                }
            }
        });
        hBox.getChildren().add(erase);
        hBox.getChildren().add(slider);
        hBox.getChildren().add(guess);
        return hBox;
    }
}

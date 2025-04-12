package org.example.miaop2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        VBox v1 = new VBox();
        VBox v2 = new VBox();

        root.getChildren().addAll(h1, h2);
        h2.getChildren().addAll(v1, v2);

        ObservableList<String> langs = FXCollections.observableArrayList("Grayscale", "Blur");
        ComboBox<String> choice = new ComboBox<>(langs);

        Button openImage = new Button("Choose Image");
        Button processing = new Button("Обработать");
        Button saving = new Button("Save Image");

        ImageView perfect = new ImageView();
        ImageView result = new ImageView();
        perfect.setFitWidth(250);
        perfect.setFitHeight(250);
        result.setFitWidth(300);
        result.setFitHeight(300);

        h1.getChildren().addAll(openImage, choice, processing, saving);
        v1.getChildren().add(perfect);
        v2.getChildren().add(result);

        openImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image img = new Image(file.toURI().toString());
                perfect.setImage(img);
            }
        });

        processing.setOnAction(event -> {
            if (perfect.getImage() != null && choice.getValue() != null) {
                String selected = choice.getValue();
                Image resultImage = null;

                switch (selected) {
                    case "Grayscale":
                        resultImage = HelloService.grayscale(perfect.getImage());
                        break;
                    case "Blur":
                        resultImage = HelloService.blur(perfect.getImage());
                        break;
                }

                if (resultImage != null) {
                    result.setImage(resultImage);
                }
            }
        });

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Image Processor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

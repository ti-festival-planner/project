package GUI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Simulator extends Application {
    private Stage stage;
    private HashMap<String, HashMap<Point2D, Integer>> map;
    private int height;
    private int width;
    private HashMap<Integer, BufferedImage> tiles;
    private int tileHeight;
    private int tileWidth;
    private Point2D cameraPosition = new Point2D.Double(0,0);
    private javafx.scene.canvas.Canvas canvas;

    //key booleans
    private BooleanProperty upPressed = new SimpleBooleanProperty();
    private BooleanProperty rightPressed = new SimpleBooleanProperty();
    private BooleanProperty leftPressed = new SimpleBooleanProperty();
    private BooleanProperty downPressed = new SimpleBooleanProperty();
    private BooleanBinding upRightPressed = upPressed.and(rightPressed);
    private BooleanBinding downRightPressed = downPressed.and(rightPressed);
    private BooleanBinding upLeftPressed = upPressed.and(leftPressed);
    private BooleanBinding downLeftPressed = downPressed.and(leftPressed);

    @Override
    public void start(Stage stage) throws Exception {
        loadjsonmap();
        this.stage = stage;
        this.canvas = new Canvas(4000, 4000);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        drawStatic(g2d);
        draw(g2d);

        Scene scene = new Scene(new Group(canvas), 1920, 1080);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                upPressed.setValue(true);
            }
            if (event.getCode() == KeyCode.S) {
                downPressed.setValue(true);
            }
            if (event.getCode() == KeyCode.D) {
                leftPressed.setValue(true);
            }
            if (event.getCode() == KeyCode.A) {
                rightPressed.setValue(true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W) {
                upPressed.setValue(false);
            }
            if (event.getCode() == KeyCode.S) {
                downPressed.setValue(false);
            }
            if (event.getCode() == KeyCode.D) {
                leftPressed.setValue(false);
            }
            if (event.getCode() == KeyCode.A) {
                rightPressed.setValue(false);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Hello Animation");
        stage.show();

        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();
    }

    private void update(double deltaTime) {
        moveCamera(deltaTime);

    }

    /**
     * moveCamera moves the camera around.
     * @param deltaTime is the deltaTime.
     */
    private void moveCamera(double deltaTime) {
        double moveSpeed = 400;
        Point2D direction = new Point2D.Double(0,0);
        if (upRightPressed.get()) {
            direction.setLocation(moveSpeed,moveSpeed);
        } else if (downRightPressed.get()) {
            direction.setLocation(moveSpeed,-moveSpeed);
        } else if (upLeftPressed.get()) {
            direction.setLocation(-moveSpeed,moveSpeed);
        } else if (downLeftPressed.get()) {
            direction.setLocation(-moveSpeed,-moveSpeed);
        } else if (upPressed.get()) {
            direction.setLocation(0,moveSpeed);
        } else if (downPressed.get()) {
            direction.setLocation(0,-moveSpeed);
        } else if (rightPressed.get()) {
            direction.setLocation(moveSpeed,0);
        } else if (leftPressed.get()) {
            direction.setLocation(-moveSpeed,0);
        }
        cameraPosition.setLocation(cameraPosition.getX()+(direction.getX()*deltaTime), cameraPosition.getY()+ (direction.getY()*deltaTime));
        canvas.setTranslateX(cameraPosition.getX());
        canvas.setTranslateY(cameraPosition.getY());
    }


    public void loadjsonmap() {
        File jsonInputFile = new File("./resources/prison_time_the_jason.json");
        InputStream is;
        try {
            is = new FileInputStream(jsonInputFile);
            JsonReader reader = Json.createReader(is);

            JsonObject root = reader.readObject();

            width = root.getInt("width");
            height = root.getInt("height");

            tiles = new HashMap<>();

            JsonArray tilesets = root.getJsonArray("tilesets");

            for (int i = 0; i < tilesets.size(); i++) {
                JsonObject jo = tilesets.getJsonObject(i);
                int gid = jo.getInt("firstgid");
                tileHeight = jo.getInt("tileheight");
                tileWidth = jo.getInt("tilewidth");
                int columns = jo.getInt("columns");
                try {
                    System.out.println(jo.getString("image"));
                    String imageString = jo.getString("image");
                    File image2 = new File("./resources/"+imageString);
                    BufferedImage tileImage = ImageIO.read(image2);
                    for (int j = 0; j < jo.getInt("tilecount"); j++) {
                        tiles.put(gid+j, tileImage.getSubimage(tileWidth * (j%columns), tileHeight * (j/columns), tileWidth, tileHeight));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JsonArray layers = root.getJsonArray("layers");

            map = new HashMap<>();
            for (int i = 0; i < layers.size(); i++) {
                JsonObject layer = layers.getJsonObject(i);
                if (layer.getString("type").equals("tilelayer")) {
                    HashMap<Point2D, Integer> layermap = new HashMap<>();
                    JsonArray chunks = layer.getJsonArray("chunks");
                    for (int j = 0; j < chunks.size(); j++) {
                        JsonObject chunk = chunks.getJsonObject(j);
                        int x = chunk.getInt("x");
                        int y = chunk.getInt("y");
                        int chunkheight = chunk.getInt("height");
                        int chunkwidth = chunk.getInt("width");
                        JsonArray data = chunk.getJsonArray("data");
                        for (int k = 0; k < data.size(); k++) {
                            int tileInt = data.getInt(k);
                            if (tileInt <= 0)
                                continue;
                            int tilex = x+(k%chunkwidth);
                            int tiley = y+(k/chunkheight);
                            layermap.put(new Point2D.Double(tilex, tiley), tileInt);
                        }
                    }
                    map.put(layer.getString("name"), layermap);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }

    private void drawStatic(Graphics2D g2d) {
        g2d.setTransform(AffineTransform.getScaleInstance(0.5, 0.5));
        for (Map.Entry<String, HashMap<Point2D, Integer>> layerSet: map.entrySet() ) {
            HashMap<Point2D, Integer> layer = layerSet.getValue();
            for (Map.Entry<Point2D, Integer> tile : layer.entrySet()) {
                g2d.drawImage(
                        tiles.get(tile.getValue()),
                        AffineTransform.getTranslateInstance(tile.getKey().getX() * tileWidth, tile.getKey().getY() * tileHeight),
                        null);
            }
        }
    }

    private void draw(Graphics2D g2d) {

    }

}

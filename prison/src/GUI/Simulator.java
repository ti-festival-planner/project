package GUI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
import java.net.URL;
import java.util.HashMap;

public class Simulator extends Application {
    private Stage stage;
    private double angle = 0.0;
    private HashMap<Integer, HashMap<Point2D, Integer>> map;
    private int height;
    private int width;
    private HashMap<Integer, BufferedImage> tiles;
    private int tileHeight;
    private int tileWidth;
    private int max = 20000;

    @Override
    public void start(Stage stage) throws Exception {
        loadjsonmap();
        this.stage = stage;
        javafx.scene.canvas.Canvas canvas = new Canvas(1920, 1080);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d);
        stage.setScene(new Scene(new Group(canvas)));
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
        angle+=0.1;
    }


    public void loadjsonmap() {
        File jsonInputFile = new File("./resources/prison_time.json");
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
                        tiles.put(gid+j, tileImage.getSubimage(tileWidth * (i%columns), tileHeight * (i/columns), tileWidth, tileHeight));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JsonArray layers = root.getJsonArray("layers");

            map = new HashMap<>();
            for (int i = 0; i < layers.size(); i++) {
                JsonObject layer = layers.getJsonObject(i);
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
                        int tilex = max+x+(k%chunkwidth);
                        int tiley = max+y+(k%chunkheight);
                        layermap.put(new Point2D.Double(tilex, tiley), tileInt);
                    }
                }
                map.put(i, layermap);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }

    void draw(Graphics2D g2d) {
        for (int i = 0; i < map.size(); i++) {
            HashMap<Point2D, Integer> layer = map.get(i);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (layer.get(new Point2D.Double(x,y)) == null)
                        continue;
                    if (layer.get(new Point2D.Double(x,y)) < 0)
                        continue;

                    g2d.drawImage(
                            tiles.get(layer.get(new Point2D.Double(x,y))),
                            AffineTransform.getTranslateInstance(x * tileWidth, y * tileHeight),
                            null);
                }
            }
        }

    }

}

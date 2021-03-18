package GUI;

import Util.Prisoner;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Simulator extends Application {
    private Stage stage;
    private double angle = 0.0;
    private HashMap<String, HashMap<Point2D, Integer>> map;
    private HashMap<String, JsonObject> rooms;
    private int height;
    private int width;
    private HashMap<Integer, BufferedImage> tiles;
    private int tileHeight;
    private int tileWidth;
    private int max = 20000;
    private ArrayList<Prisoner> prisoners;

    @Override

    public void start(Stage stage) throws Exception {
        loadjsonmap();

        this.stage = stage;
        Canvas canvas = new Canvas(1920, 1080);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        init();
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
        for (Prisoner prisoner : this.prisoners){
            prisoner.update();
        }
    }

    public void init(){
        if (rooms != null){
            JsonObject spawn = rooms.get("Spawn");
            int spawnX = spawn.getInt("x");
            int spawnY = spawn.getInt("y");
            this.prisoners = new ArrayList<>();
            this.prisoners.add(new Prisoner(new Point2D.Double(spawnX,spawnY), angle));
        }
}


    public void loadjsonmap() {
        File jsonInputFile = new File("./resources/prison_time_the_jason_V2.json");
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
                } else if (layer.getString("type").equals("objectgroup")){
                    rooms = new HashMap<>();
                    JsonArray objects = layer.getJsonArray("objects");
                    for (int j = 0; j < objects.size(); j++){
                        JsonObject object = objects.getJsonObject(j);
                        rooms.put(object.getString("name"),object);
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }

    void draw(Graphics2D g2d) {
        g2d.setTransform(AffineTransform );
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


        for(Prisoner prisoner : this.prisoners){
            prisoner.draw(g2d);
        }

    }

}

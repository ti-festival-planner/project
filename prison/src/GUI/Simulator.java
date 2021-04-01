package GUI;

import Util.Prisoner;
import Util.PrisonerHigh;
import Util.PrisonerLow;
import Util.PrisonerMedium;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Simulator extends Application {

    private String tilemapName = "prison_time_the_jason_V3.json";
//    private String resourcePath = "./resources/"; // Path naar resources.
    private String resourcePath = "D:\\AVANS\\FestivalPlanner\\project\\resources\\"; //Path naar resources bij Jasper.

    private Stage stage;
    private HashMap<String, HashMap<Point2D, Integer>> map;
    private int angle = 0;
    private HashMap<String, JsonObject> rooms;
    private int height;
    private int width;
    private HashMap<Integer, BufferedImage> tiles;
    private int tileHeight;
    private int tileWidth;
    private Point2D cameraPosition = new Point2D.Double(0,0);
    private javafx.scene.canvas.Canvas canvas;
    private ArrayList<Prisoner> prisoners;
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
        this.cameraPosition = new Point2D.Double(-3500,0);

        this.canvas = new Canvas(8000, 4000);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        npcInit();
        drawStatic(g2d);
        draw(g2d);

        Scene scene = new Scene(new Group(canvas), 1920, 1080);
        //register the key listeners
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
        scene.setOnMouseMoved(event -> {
            for(Prisoner prisoner : this.prisoners) {
                prisoner.setTarget(new Point2D.Double(event.getX(), event.getY()));
            }});


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
        stage.setTitle("Simulator");
        stage.show();

        // make and start the animationTimer to update and draw each frame.
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
        for (Prisoner prisoner : prisoners){
            prisoner.update();
        }

    }

    private void npcInit(){
        if (rooms != null){
            ArrayList<BufferedImage> lowImages = loadPrisonerSprites("prisoner.png");
            ArrayList<BufferedImage> mediumImages = loadPrisonerSprites("prisonerMedium.png");
            ArrayList<BufferedImage> highImages = loadPrisonerSprites("prisonerHigh.png");
            JsonObject spawn = rooms.get("Spawn");
            int spawnX = spawn.getInt("x");
            int spawnY = spawn.getInt("y");
            this.prisoners = new ArrayList<>();
            this.prisoners.add(new PrisonerLow(new Point2D.Double(spawnX+6000,spawnY), lowImages));
            this.prisoners.add(new PrisonerMedium(new Point2D.Double(spawnX+6100,spawnY), mediumImages));
            this.prisoners.add(new PrisonerHigh(new Point2D.Double(spawnX+6200,spawnY), highImages));
        }
    }

    private ArrayList<BufferedImage> loadPrisonerSprites(String filename) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        try {
            File imagefile = new File(this.resourcePath + filename);
            BufferedImage image = ImageIO.read(imagefile);
            int w = image.getWidth()/3;
            int h = image.getHeight();
            for (int y = 0; y < 1; y++){
                for (int x = 0; x< 3; x++){
                    images.add(image.getSubimage(x*w,y* h,w,h));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return images;
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

        File jsonInputFile = new File(resourcePath+tilemapName);
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

                    File image2 = new File(resourcePath +imageString);
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
                            int tilex = x + (k % chunkwidth);
                            int tiley = y + (k / chunkheight);
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

    /**
     * the drawStatic method draws the static background on screen at the start of the program.
     * @param g2d The graphics2d object on which to draw the tiles
     */
    private void drawStatic(Graphics2D g2d) {
        g2d.setTransform(AffineTransform.getScaleInstance(0.5, 0.5));
        drawLayer(g2d, map.get("Background"));
        drawLayer(g2d, map.get("Buildings"));
        drawLayer(g2d, map.get("Path"));
        drawLayer(g2d, map.get("Furniture"));
        drawLayer(g2d, map.get("Items"));
//        drawLayer(g2d, map.get("presets"));
    }

    /**
     * draws a tile layer
     * @param g2d the graphics2d object on which to draw the layer
     * @param layer the layer to draw
     */
    private void drawLayer(Graphics2D g2d, HashMap<Point2D, Integer> layer) {
        for (Map.Entry<Point2D, Integer> tile : layer.entrySet()) {
            g2d.drawImage(
                    tiles.get(tile.getValue()),
                    AffineTransform.getTranslateInstance(6000+(tile.getKey().getX() * tileWidth), tile.getKey().getY() * tileHeight),
                    null);
        }
    }

    /**
     * drawpoint is a method that draws the tiles at a specific point
     * @param g2d the graphics2d object on which to draw the layer
     * @param point the point to draw around.
     */
    private void drawPoint(Graphics2D g2d, Point2D point) {
        Point2D point32 = new Point2D.Double(point.getX()-(point.getX()%32),point.getY()-(point.getY()%32));
        int viewWidth = 5;
        int viewWidth2 = (int) Math.floor(viewWidth/2);
        int viewHeight = 5;
        int viewHeight2 = (int) Math.floor(viewWidth/2);
        String[] layers = {"Background","Buildings","Path","Furniture","Items"};
        for (int k = 0; k < 5; k++) {
            HashMap<Point2D, Integer> layer = map.get(layers[k]);
            for (int i = -viewWidth2; i < viewWidth; i++) {
                double chunkx = point32.getX()+i*tileWidth;
                for (int j = -viewHeight2; i < viewHeight; i++) {
                    double chunky = point32.getY()+j*tileHeight;
                    if (layer.get(new Point2D.Double(chunkx/tileWidth, chunky/tileHeight)) == null) continue;
                    g2d.drawImage(
                            tiles.get(layer.get(new Point2D.Double(chunkx, chunky))),
                            AffineTransform.getTranslateInstance(6000+(chunkx * tileWidth), chunky * tileHeight),
                            null);
                }
            }
        }
    }

    private void draw(Graphics2D g2d) {
        for (Prisoner prisoner: this.prisoners)
            prisoner.draw(g2d);

    }

}

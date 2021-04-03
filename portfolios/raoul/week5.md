# Week 5
[Raoul de Heer - 2165787](https://github.com/raouldeheer)
## Deze week op de agenda

-   [Tilemap laden](#Tilemap-laden)
-   [Json parsen](#Json-parsen)
-   [Tilemap tekenen](#Tilemap-tekenen)

## Tilemap laden
Deze week moest ik de tilemap inladen.  
Dat heb ik gedaan met deze code:
```java
File jsonInputFile = new File("./resources/prison_time.json");
InputStream is;
try {
    is = new FileInputStream(jsonInputFile);
    JsonReader reader = Json.createReader(is);
    reader.close();
} catch (FileNotFoundException e) {
    e.printStackTrace();
    return;
}
```
Zie commit [cac320a](https://github.com/ti-festival-planner/project/commit/cac320a096bc736ce954087a2c0a2b5089c202fc)

## Json parsen
Deze week moest ik ook de json parsen.  
Dat bleek moeilijker te zijn doordat Ramon een tilemap had gemaakt die infinite is, waardoor alles was op gedeelt in chunks.  
Ik heb het parsen nu op deze manier gedaan:  
```java
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
```
Zie commit [cac320a](https://github.com/ti-festival-planner/project/commit/cac320a096bc736ce954087a2c0a2b5089c202fc)

## Tilemap tekenen
Deze week moest ik ook dan de tilemap tekenen op het scherm.  
Dat heb ik nu gedaan door deze code te schrijven: 
```java
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
```
Zie commit [cac320a](https://github.com/ti-festival-planner/project/commit/cac320a096bc736ce954087a2c0a2b5089c202fc)
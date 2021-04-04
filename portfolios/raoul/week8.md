# Week 8
[Raoul de Heer - 2165787](https://github.com/raouldeheer)
## Deze week op de agenda

-   [🚀🚀🚀Fast background🚀🚀🚀](#🚀🚀🚀Fast-background🚀🚀🚀)
-   [Prisoner movement](#Prisoner-movement)

## 🚀🚀🚀Fast background🚀🚀🚀
Deze week ging ik het teken van de tilemap versnelen.  
Om het tekenen te versnellen moesten we het tekenen van de achtergrond versnelen.  
De manieren die we kregen om dit te doen werkten niet.  
Ik had een bericht gestuurd voor hulp maar geen reactie.  
Na lang zoeken kwam ik op deze werkende code:  
```java
private void buildStatic(Graphics2D g2d) {
    g2d.setTransform(AffineTransform.getScaleInstance(0.5, 0.5));
    drawLayer(g2d, map.get("Background"));
    drawLayer(g2d, map.get("Buildings"));
    drawLayer(g2d, map.get("Path"));
    drawLayer(g2d, map.get("Furniture"));
    drawLayer(g2d, map.get("Items"));

    WritableImage wim = new WritableImage(canvasWidth, canvasHeight);
    canvas.snapshot(null, wim);
    this.cachedLayers = SwingFXUtils.fromFXImage(wim, null);
}

private void drawStatic(Graphics2D g2d) {
    g2d.drawImage(this.cachedLayers, AffineTransform.getScaleInstance(2,2),null);
}
```
Door een snapshot van de canvas op te slaan in een writableImage, bij het opstarten.    
En de writableImage om te zetten in een BufferedImage.   
Konden we iedere frame de hele achtergrond tekenen met een image.   
Zie commit [2f29c96](https://github.com/ti-festival-planner/project/commit/2f29c967c6a4a54ce94396e2ea9bad90c9edaa8e)

## Prisoner movement 
Deze week had Jasper moeilijkheden met het implementeren van de movement voor de Prisoner.  
Toen hebben we samen de code voor de movement gemaakt.

`if (Math.abs(xDir) < 4)` is voor lopen stabieler te maken.  
Met Math.signum() kunnen we bepalen welke richting de Prisoner heen moet.
```java
public void update(double deltaTime, ArrayList<Prisoner> prisoners){
    double xDir = this.target.getX() - this.position.getX();
    double yDir = this.target.getY() - this.position.getY();
    if (Math.abs(xDir) < 4) xDir = 0;
    if (Math.abs(yDir) < 4) yDir = 0;
    this.direction = new Point2D.Double(
        Math.signum(xDir)*speed* Simulator.speed * deltaTime,
        Math.signum(yDir)*speed* Simulator.speed * deltaTime
    );
```
Hier berekenen we de nieuwe positie.
```java
    Point2D newPosition = new Point2D.Double(
        this.position.getX()+this.direction.getX(),
        this.position.getY()+this.direction.getY()
    );
```
Hier gaan we kijken hoe druk het is rond de Prisoner, dus hoeveel weerstand er moet zijn.
```java
    int collisionCount = 1;
    for (Prisoner prisoner : prisoners){
        if (prisoner == this) continue;
        if (prisoner.position.distanceSq(newPosition) < 64*64) collisionCount++;
    }
```
Wanneer er meer mensen zijn op een plek wordt de weerstand om te kunnen lopen groter. Waardoor de Prisoner langzamer gaat lopen maar niet stopt omdat hij tegen de andere Prisoners 'duwt'.
```java
    if (collisionCount > 1) {
        if (Math.random()>=0.91) this.panic = true;
        newPosition = new Point2D.Double(
            this.position.getX()+(this.direction.getX()/Math.pow(collisionCount, 2)),
            this.position.getY()+(this.direction.getY()/Math.pow(collisionCount, 2))
        );
    }
```
Wanneer panic true is gaat de Prisoner verder met lopen. Dit geeft de Prisoner karakter en lijkt hij/zij menselijk. Dit is te zien wanneer prisoner tegen elkaar aan lopen, soms gaan de spontaan een stukje vooruit alsof ze tegen elkaar duwen en struikelen.
```java
    if (this.panic) {
        if (collisionCount > 1) {
            newPosition = new Point2D.Double(
                this.position.getX()+this.direction.getX(),
                this.position.getY()+this.direction.getY()
            );
            this.panic = false;
        } else {
            this.panic = false;
        }
    }
```
Hierna hoeven we alleen de positie aan te passen wanneer de Prisoner nog niet bij zijn target is.
```java
    if (this.position.distanceSq(this.target) >= 64) this.position = newPosition;
}
```

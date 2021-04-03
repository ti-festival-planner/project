# Week 6
[Raoul de Heer - 2165787](https://github.com/raouldeheer)
## Deze week op de agenda

-   [Tilemap laden verbeteren](#Tilemap-laden-verbeteren)
-   [PR gereviewd](#PR-gereviewd)
-   [Camera](#Camera)

## Tilemap laden verbeteren
Deze week ging ik het laden van de tilemap verbeteren.   
De verbeteringen zijn te zien in commits:
-   [6da93ae](https://github.com/ti-festival-planner/project/commit/6da93aea93a0ff711d8b5a6dab1e5fb48475b5b3)
-   [a0dddd8](https://github.com/ti-festival-planner/project/commit/a0dddd881806bbdfd2befdf2ac6552e7a37b2953)
-   [045bac6](https://github.com/ti-festival-planner/project/commit/045bac62fee4d9f914d817f19d8f26aa74d23205)


## PR gereviewd 
Deze week heb ik ook [deze PR](https://github.com/ti-festival-planner/project/pull/12) gereviewd. 

## Camera
Deze week heb ik de camera toegevoegd.  
```java
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
```
Met deze code wordt de camera rond bewogen.  
Zie commit [7b182b8](https://github.com/ti-festival-planner/project/commit/7b182b828f224830eb332aa15b4b7cec0fd12f8a)
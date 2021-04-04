## Proftaak Individueel | Jasper Hopmans | 2177554
### Inleiding
Dit document dient ervoor om het individuele gedeelte van de proftaak te weergeven. Dit zal opgesteld zijn van week 5 tot en met week 8. Dit is anders als normaal. Dit is ivm mijn thuis situatie. Waardoor ik eerste 4 weken niet aanwezig ben geweest. Er zal ook te zien zijn dat ik in week 5 niet heel veel gedaan heb. Mijn bijdragen aan het project zal oplopen naar maten de weken verstreken. Dit komt omdat ik in het begin druk bezig was met het bij komen qua stof. 

## Week 3 t/m 4
In deze weken was ik afwezig ivm thuis situatie.

## Week 5
### Context
Deze week was ik aanwezig bij de vergadering. Hier hebben wij gekeken naar hoe het project verliep. Ik heb in deze week een extra taak op mij genomen namelijk de planning regelen en zorgen dat iedereen wist wat ze moesten doen.

### Keuzemogelijkheden
De keuze mogelijkheden die we deze week konden doen waren:

- Wie gaat er verder met de code?
- Wie gaat er de tilemap verder af maken?
- Wat voor tilemap willen we?

### Keuzes
Hier hebben wij met zijn alle de keuze genomen om te kijken wie waar aan gaat beginnen en verder mee gaat. Ik heb er toen bewust voor gekozen om nog niks te gaan doen met de code. Ik was toen nog niet begonnen met de lesstof. Verder hebben wij ervoor gekozen om de tileset te kiezen van de oorspronkelijke game Prison Architect.

### Waarom?
Wij hebben deze keuzes genomen omdat het het beste erbij paste en wij zo het beste resultaat konden krijgen.
## Week 6

### Context
Deze week was mijn eerste echte week terug. In deze week heeft Robin mij alles uitgelegd van wat we tot dusver hebben. Ook heeft Robin mij de belangrijke delen van de code uitgelegd hoe deze werken. In week 5 hebben we de planning gemaakt en die hebben we verder deze week aangehouden.

### Keuze mogelijkheden
In deze week waren er voor mij niet echt keuze mogelijkheden dit omdat ik samen met Robin bezig was om heel het project te begrijpen.

### Keuze
Wij hebben dus ook geen keuzes gemaakt.

### Waarom
Dit hebben wij gedaan zodat ik weer helemaal up to date was over de code en zo weer makkelijker mee kon gaan coderen met de rest.

## Week 7

### Context 

Deze week was mijn eerste week dat ik daadwerkelijk begon met programmeren. Wij hebben in de meeting met Joli opnieuw een planning gemaakt. 

### Keuze mogelijkheden
We kwamen tot de conclusie dat ik nog niet heel veel aan de code had gedaan hier moest wat aan veranderen. De functies de wij nog moesten doen waren de NPC's laten lopen en zorgen dat ze konden lopen met collision. De Camera moest gemaakt worden. En verder moest er nog gezorgd worden dat de NPC's naar de juiste plek lopen en dat de tijd sneller en langzamer kon werken.

### Keuze
In de vergadering hebben we mij de opdracht gegeven om te zorgen dat de NPC's werkte.

### Waarom
Dit hebben wij gedaan omdat ik nog wat in te halen had in het project en de NPC's een belangrijk en redelijk grote opdracht was.

### Stukje code:
```java
public class Prisoner {

    private Point2D position;

    private double angle;
    private ArrayList<BufferedImage> sprites;
    private double speed;

    private ArrayList<BufferedImage> sprites;
    private Point2D target;
    public Prisoner(Point2D position, double angle) {
        this.position = position;
        this.angle = angle;
        this.speed = 3;
        this.target = position;
        this.sprites = new ArrayList<>();

        try {
            File image2 = new File("./resources/prisoner.png");
            File image2 = new File("D:\\AVANS\\FestivalPlanner\\project\\resources\\prisoner.png");
            BufferedImage image = ImageIO.read(image2);
            int w = image.getWidth()/3;
            int h = image.getHeight();
            for (int y = 0; y < 1; y++){
                for (int x = 0; x< 3; x++){
                    this.sprites.add(image.getSubimage(x*w,y*h,w,h));
                    this.sprites.add(image.getSubimage(x*w,y* h,w,h));
                }
            }
        } catch (IOException e){


    public  void update(){
        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(),this.target.getX() - this.position.getX());
        double rotation = targetAngle - this.angle;
        while (rotation < -Math.PI){
            rotation += Math.PI;
        }
        while (rotation > Math.PI){
            rotation -= Math.PI;
        }
        if (rotation< 0){
            this.angle -= 0.1;
        } else if (rotation > 0){
            this.angle += 0.1;
        }

        this.position = new Point2D.Double(
                this.position.getX() + this.speed * Math.sin(this.angle),
                this.position.getY() + this.speed * Math.cos(this.angle));

    }

    public void draw(Graphics2D g2d){

        int centerX = sprites.get(0).getWidth()/2;
        int centerY = sprites.get(0).getHeight()/2;

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - centerX, position.getY()-centerY);

        tx.rotate(angle + Math.PI/2, centerX, centerY);
        g2d.drawImage(this.sprites.get(1), tx,null);
    }

    public void setTarget(Point2D newTarget) {
        this.target = newTarget;
    }
}
        
``` 
Dit hier boven was de eerste variant van de Prisoner class. Dit is stuk is verder in gegaan op de code van Robin. Verder heb ik nog geprobeert om de poppetjes de muis te laten volgen en heb ik dit toegevoegd in de simulator classe:

```java
        scene.setOnMouseMoved(event -> {
            for(Prisoner prisoner : this.prisoners) {
                prisoner.setTarget(new Point2D.Double(event.getX(), event.getY()));
            }});

```
En dit in de draw methode van de Simulator
````java

    for (Prisoner prisoner : prisoners){
            prisoner.update();
        }
````
We kwamen tot de conclusie dat het niet allemaal werkte zoals het moest. De NPC's lieten een streep achter en wiste zichzelf niet van het canvas af. We hebben toen in de avond samen met Robin en Raoul gekeken en kwamen maar niet tot de oplossing. Toen hebben we ervoor gekozen om te wachten tot het volgende senior gesprek met Edwin om het dan te vragen.

## Week 8
Deze week zijn we bij Edwin geweest en gekeken hoe we ons probleem konden oplossen. Dit is Raoul uiteindelijk gelukt met een hele andere oplossing.

### Context
In week 8 moesten we nog alle zeilen aan spannen om er toch nog wat van te maken. Met de NPC die liep maar niet de juiste kant op en verder nog niet de rooster functie geimplementeerd.
In week 8 ben ik verder gegaan met het laten werken van de NPC met collision en het laten lopen naar de juiste positie. Dit met hulp van Raoul en Robin. Verder heb ik ook nog de verschillende snelheden geimplementeerd.

### Keuze mogelijkheden
In week 8 waren er niet echt keuze mogelijkheden.

### Keuze's
Hoe wel er in week 8 niet echt veel keuzemogelijkheden waren. Hebben we toch nog wel een aantal keuze's moeten maken. 

- Snelheid
Ik heb de keuze gemaakt om bij de snelheid te gaan werken met een aantal toetsen op het toetsenbord. En om daarmee te regelen of de NPC's sneller of langzamer gingen lopen:
```java
  if (event.getCode() == KeyCode.SHIFT){
                if (speed > 2){
                    speed = 3;
                }else {
                    speed ++;
                }
            }
            if (event.getCode() == KeyCode.CONTROL){
                if (speed < 2 || speed > 50 ){
                    speed = 1;
                }else {
                    speed --;
                }
            }
            if (event.getCode() == KeyCode.SPACE){
                speed = 0;
            }
            if (event.getCode() == KeyCode.DELETE){
                speed = 100;
            }
        });

```
Ik heb er voor gekozen om met Shift en Ctrl te zorgen dat de snelheid 1 omhoog of omlaag gaat. Met de Spatie Balk zet je de Simulator op pauze en met de Delete knop zorg je ervoor dat alles extreem snel vooruitgezet word. Dit word gedaan doormiddel van de `Public static int speed` Deze word opnieuw aangeroepen in de Prisoner classe:
```java
  this.direction = new Point2D.Double(
                Math.signum(this.target.getX() - this.position.getX())*speed*deltaTime,
                Math.signum(this.target.getY() - this.position.getY())*speed*deltaTime
        );


```
Hierdoor zorgt de speed ervoor dat de snelheid vermenigvuldigt word met de hoeveelheid versnelling. Verder word `speed`ook nog gebruikt om te zorgen dat de blokken ook minder lang duren. 
```java

 private void update(double deltaTime) {
        timerFrame = timerFrame + (float)deltaTime * speed;
        int uncapblock = (int) Math.floor(timerFrame / 60);
        currentblock = uncapblock %24;
        moveCamera(deltaTime);
        for (Prisoner prisoner : prisoners){
            prisoner.update(deltaTime, prisoners);
        }
        for (Prisoner prisoner : guards){
            prisoner.update(deltaTime, prisoners);
        }
        if (currentblock != previousblock) {
            previousblock = currentblock;
            updatePrisonerTarget();
            System.out.println("Volgend uur: "+currentblock);
        }
    }
    
```
Normaal gesproken duurt een blok 60 seconden, als de tijd 2x zo snel gaat duurt het nog maar 30 seconden en als de tijd 3x zo snelgaat nog maar 20. Hierdoor klopt de speed met de duur van één blok.

- Prisoner Movement

Zelf had ik moeite met het werkend krijgen van de movement dus heb ik de hulp gekregen van Raoul.
  
  `if (Math.abs(xDir) < 4)` is er voor om te zorgen dat de Prisoners niet meer heel erg aan het heen en weer hobbelen zijn.  
  Met Math.signum() bepalen we welke richting de Prisoner heen moet.  
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
Verder moeten we nog berekenen wat de nieuwe positie word van de prisoner. Dat gebeurt hier:
```java
    Point2D newPosition = new Point2D.Double(
        this.position.getX()+this.direction.getX(),
        this.position.getY()+this.direction.getY()
        );
```

- Colission

Bij de Collision hebben Raoul en ik ervoor gekozen om te zorgen dat de Prisoners langzamer gingen lopen als ze een andere prisoner tegen kwamen dit hebben we gedaan met de gedachte van: het is vaak ergens drukker en dit betekent niet dat je dan stil gaat staan, nee dan loop je samen langzaam met elkaar naar de plek waar je heen wilt.
  En op het moment dat er meer mensen zijn loopt de prisoner langzamer als dat er maar 1 extra prisoner is.
We moeten dus eerst kijken hoeveel prisoners er om elkaar heen zijn, en hoeveel weerstand er dus moet zijn.
dit doen we hier.
```java
    int collisionCount = 1;
    for (Prisoner prisoner : prisoners){
        if (prisoner == this) continue;
        if (prisoner.position.distanceSq(newPosition) < 64*64) collisionCount++;
    }
```
Op het moment dat er meerdere prisoners zijn moeten ze langzamer gaan lopen. Dit gebeurd hier:
```java
if (collisionCount > 1) {
if (Math.random()>=0.91) this.panic = true;
newPosition = new Point2D.Double(
this.position.getX()+(this.direction.getX()/Math.pow(collisionCount, 2)),
this.position.getY()+(this.direction.getY()/Math.pow(collisionCount, 2))
);
}
```
Wanneer de panic true is zal de prisoner verder gaan bewegen. Dit hebben wij gedaan omdat het op deze manier lijkt alsof de Prisoner daadwerkelijk een echt mens is, bijvoorbeeld als ze tegen elkaar aan lopen kan de prisoner zomaar spontaan vooruit zodat het lijkt alsof ze tegen elkaar aan duwen:
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
Verder moeten we er nog voor zorgen dat de positie nog aan gepast word als de prisoner nog niet bij zijn target is. Dit doen we hier:
```java
    if (this.position.distanceSq(this.target) >= 64) this.position = newPosition;
}
```

- NPC inladen
Verder heb ik samen met Robin ervoor gezorgd dat de juiste NPC's ingeladen worden. Denk hierbij aan de verschillende type prisoners, maar ook aan de Guards. Hiervoor hebben we de classe Prisoner abstract gemaakt.
  
- Rooms
Bij de rooms hebben we er voor gekozen om voor elke room een eigen klassen te maken. Dit zodat we makkelijkker de positie konden krijgen van elke room en hierdoor de prisoner naar de juiste plek konden brengen.
  Hier een voorbeeld van een Room:




```java
package Room;
import java.awt.geom.Point2D;

public class HoldingCell extends AbstractRoom{
public HoldingCell(Point2D startcoords, Point2D endcoords) {
super(startcoords, endcoords);
}
}

```

### Waarom
We hebben voor deze keuzes gekozen omdat dit alles een stuk effiencter en sollider maakt.

## Extra Opdrachten

### Reflectie: Gebruik van JavaFX in het bedrijfsleven
Ik denk dat er vroeger meer gebruik gemaakt werd van JavaFX in het bedrijfsleven. Als we gaan kijken kunnen we het bedrijfsleven in twee onderdelen indelen. Hierbij heb je de Programmeurs in het bedijfsleven en de niet programmeurs.
De niet programmeurs zullen op zoek gaan naar een makkelijk programma met een stel presets waardoor je niet hoeft te coderen.
Waar de programmeurs waarschijnlijk voor een veel goedkoper en efficientere manier zullen zoeken. Denk hierbij aan bijvoorbeeld C# met WPDF.

###Reflectie: Meer simulatie in het bedijfsleven
Ik denk dat dit een hele goede manier is om een plan of project aan te pakken. Zowel in de festival branch als in eigenlijk alle andere. Hierdoor zie je veel makkelijker en eerder waar er eventueel fouten kunnen plaats vinden en kan je deze al eerder aanpakken. Ook is het voor de verkoop een stukje beter hierdoor weet de klant exact wat ze zouden kopen.


## JSON
### Applicaties die JSON gebruiken
Eigenlijk word JSON bijna overal gebruikt, hierdoor zou een lijst met applicaties die JSON gebruiken nooit ophouden. Het is eigenlijk een ongeschreven regel dat als je een API schrijf je deze terug krijgt in een JSON. Hier toch even een aantal voorbeelden van applicaties die op JSON werken:
- Facebook
- Google
- Twitter
- Instagram
- Spotify
- Youtube


En ga zo nog maar door.
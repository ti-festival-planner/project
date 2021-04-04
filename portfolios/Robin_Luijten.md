# Proftaak Individueel

In dit document zal de individuele opdracht van de proftaak te vinden zijn.
Dit document zal opgesplitst zijn in de weken en onderaan zal de overige opdracht te vinden zijn.


# Week 3

## Context


Het huidige rooster was geschreven in samenwerking met Bart, Garcia & Mij, hier kwam een werkende applicatie uit voor de demo, de code was echter verre van georganiseerd en duidelijk.
Hierop hebben we besloten dat ik de code zal herschrijven voor een duidelijkere structuur en een goed overzicht.

## Keuzemogelijkheden

De keuze modelijkheden die zich hebben voor gedaan waren bijvoorbeeld:

- Welke bestand structuur zal ik toepassen
- Welke structuur zal ik toepassen binnen de code
- Wat is toekomst bestendig

## Keuzes

De keuzes die gemaakt zijn:
- Er is een duidelijke structuur gemaakt en alles is verdeeld onder de juiste package.
- De structuur in de code is voor duidelijke benaming (Volgens code style guide)
- De code is makkelijker aan te passen en heeft een goede manier van opslaan.

## Waarom

De keuzes die ik heb gemaakt zijn allemaal gedaan zodat er een goede applicatie staat die makkelijk aan te passen is en als er nieuwe "Features" komen deze makkelijk te integreren zijn.


# Week 4


## Context
Deze week moest de applicatie gemaakt worden dat de tilemap wordt uitgelezen, de appliactie die dit doet heeft raoul gemaakt.
Zelf ben ik verder gegaan aan de rooster applicatie zodat deze volstaat aan **CRUD**, ook heb ik nog fouten afhandeling ingebouwd die zorgen dat er bijvoorbeeld geen overlapping kan plaatsvinden.

## Keuzemogelijkheden
De keuzes die gemaakt konden worden zijn hoe gaan we zorgen dat de gebruiker makkelijk een activiteit kan bewerken of verwijderen.
We hadden ook de keuze hoe we de gebruiker laten weten als er iets niet goed is.

## Keuzes
De keuze die we hebben gemaakt is om bij het bewerken een nieuw venster te laten zien zodat de gebruiker daar makkelijk zijn aanpassing kan invullen.

Wanneer er iets fout gaat of als de gebruiker bevestiging moet geven (bijvoorbeeld bij verwijderen) gebruiken we **Alert** hierin kunnen we de gebruiker om bevestiging vragen en duidelijk aangeven als er iets niet goed is.
*Voorbeeld edit:*

![prisoner edit](https://imgur.com/uaM3Mmj.png)

*Voorbeeld foutmelding:*

![Fout overlap melding](https://imgur.com/QVh0S09.png)

*Voorbeeld bevestiging:*

![Delete confirm](https://imgur.com/qUCXYBU.png)
## Waarom
De reden dat we hiervoor hebben gekozen is dat we via deze manier duidelijkheid naar de gebruiker kunnen geven, via deze manier zie je direct wat er is of wat je moet doen.


# Week 5

Deze week heb ik gefocust op de basis leggen voor het inladen van de NPC.

## Context
Deze week moest heb ik gefocust op het inladen van de NPC hiervoor heb ik ook de object layer van de tilemap ingeladen zodat ik deze later kan gebruiken om de de ruimtes op te halen.
## Keuzemogelijkheden
De keuzemogelijkeheden die we hier hadden wat is de afbeelding die we willen gebruiken voor de NPC.
## Keuzes
We hebben gekozen om net als de tilemap de orginele asset van **Prison Architect** te gebruiken dit om eenheid te geven en dit heeft dezelfde afmetingen.
## Waarom
De reden van deze keuze is voornamelijk de eenheid die het geeft en ook omdat de asset dezelfde groote had als de andere assets die worden gebruikt in de tilemap.

# Week 6
Deze week heb ik geen dergelijke aanpassingen gedaan (Deze week kwam jasper terug)

## Context
Tijdens deze week heb ik zelf jasper op weg geholpen zodat hij ook kan meedraaien binnen het project.

## Keuzemogelijkheden
Deze week waren er geen keuzes die gemaakt moesten worden.

## Keuzes
Deze week waren er geen keuzes die gemaakt moesten worden.

## Waarom
Deze week heb ik geen bijdragen geleverd aan de code binnen het project.


# Week 7
Deze week heb ik niet veel gedaan aan het project IVM tentamens

## Context
De week hierna zat vol met tentamens hier heb ik mijzelf op gefocust.

## Keuzemogelijkheden
Dit is niet van toepassing.

## Keuzes
Dit is niet van toepassing.

## Waarom
Dit heb ik gedaan omdat het project niet grote aandacht nodig had in deze week.

# Week 8
Deze week heb ik de ruimtes ingeladen. Ik heb ook samen met Jasper een aanpassing gedaan aan de NPC betreffend het inladen van de asset

## Context
De ruimtes waar de prisoners naartoe moeten lopen heb ik ingeladen dit is gebeurd met meerdere klassen die een abstract class extenden.
Voor het nieuwe inladen van de prisoner hebben we een abstract class gemaakt die alle loggic afhandeld en als er afwijkingen zijn bij een NPC is dit makkelijk toe te passen.

## Keuzemogelijkheden
De keuze lag vooral op hoe het makkelijk aan te roepen is.

## Keuzes
De keuze is gemaakt om voor ieder type ruimte een object aan te maken en deze in ieder een eigen ArrayList type te stoppen.
Je hebt dus bijvoorbeeld een ArrayList met `Cells` en een ArrayList met `Common Rooms`
Abstract class voor de ruimtes:

```java
package Room;

import java.awt.geom.Point2D;

public abstract class AbstractRoom {

    public Point2D Startcoords;
    public Point2D Endcoords;

    public AbstractRoom(Point2D startcoords, Point2D endcoords) {
        Startcoords = startcoords;
        Endcoords = endcoords;
    }

    public Point2D getCenter(){
        double x1 = (this.Startcoords.getX()+6000)/2;
        double x2 = (this.Endcoords.getX()+6000)/2;
        double y1 = this.Startcoords.getY()/2;
        double y2 = this.Endcoords.getY()/2;
        return new Point2D.Double((x1+x2)/2, (y1+y2)/2);
    }

    public Point2D getPlace() {
        double x1 = (this.Startcoords.getX()+6000)/2;
        double x2 = (this.Endcoords.getX()+6000)/2;
        double y1 = this.Startcoords.getY()/2;
        double y2 = this.Endcoords.getY()/2;

        double xoffset = 8+((x2-x1)-16)*Math.random();
        double yoffset = 8+((y2-y1)-16)*Math.random();

        return new Point2D.Double(
                x1+xoffset,
                y1+yoffset
        );
    }
}
```

Hieronder is de klasse voor cell te zien die een aanpassing heeft vergeleken met de andere klasse

```java
package Room;

import java.awt.geom.Point2D;

public class Cell extends AbstractRoom{
    private Boolean occupied;
    public Cell(Point2D startcoords, Point2D endcoords) {
        super(startcoords, endcoords);
        occupied = false;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}

```

## Waarom
Deze manier van opslaan bied een goede manier om aan te roepen. We kunnen de NPC makkelijk naar hun doel zetten omdat er binnen het object de locatie wordt berekend.

---
# Overige opdrachten

## Reflectie
### Gebruik JavaFX in bedrijfsleven
Zelf denk ik dat het vroeger wel gebruikt kan zijn binnen het bedrijfsleven maar dat er op dit moment betere opties zijn voor bedrijven en dat deze ook goedkoper zijn voor bedrijven.
Je kan bijvoorbeeld kijken naar C# en dan naar WPF, dit is een efficiëntere manier van werken als je een applicatie moet hebben die een grafische schil moet hebben.

---
### Meer simulatie in bedrijfsleven

Simulatie is denk ik een goede manier om iets te bekijken hoe het zal uitpakken en ik denk dat ieder bedrijf wel een soort van simulatie heeft.
Een simulatie hoeft niet direct een simulator te betekenen in een grafische omgeving maar kan ook een test zijn van hoe een programma zal reageren op bepaalde reeksen van acties.

Een simulatie geeft een berijf goede inzichten en ik denk dat ieder bedrijf wel een soort van simulatie heeft.

---
## JSON
### Waarom JSON wordt gebruikt
JSON is een manier van opslaan van objecten die universeel is en nog goed te lezen nadat iets in een JSON format is gezet.
Het gebruik van JSON maakt het makkelijker om bijvoorbeeld iets tussen 2 applicaties te delen.

---

### Applicaties die JSON gebruiken
Applicaties die JSON gebruiken is een lijst die denk ik nooit zal ophouden er zijn ontelbare applicaties die gebruik maken van het JSON format.
Als je bijvoorbeeld een API schrijft is het eigenlijk wel een ongeschreven regel dat je het in JSON format terug geeft.
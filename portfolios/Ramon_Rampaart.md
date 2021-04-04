# Individueel resultaat Ramon Rampaart 2166575

Tijdens de huidige periode moest vanaf week 3 een wekelijkse reflectie worden bijgehouden wat je hebt gedaan in de proftaak.
In dit document zult u niets aan code terug vinden. Dit i.v.m. dat ik een achterstand heb met programmeren. Dit heb ik besproken met leraren en mijn projectgroep en heb mij gericht op andere dingen in het project, zoals documentatie en de Tilemap.

## Week 3
In deze week hebben we uitleg gekregen over hoe je met de applicatie TileMap moet werken.
Het eerste wat gebeurd moest worden was het maken of zoeken van een Tileset om de map mee te maken.
En omdat wij als groep hebben gekozen om een gevangenis simulator te maken heb ik gekozen om bestaande Tileset op te zoeken.
Er is namelijk een spel wat ook in principe een gevangenis simulator is. 

Dus toen was het taak om te kijken of deze tileset ergens te vinden waren.
Na een flinke tijd zoeken heb ik Tilesets gevonden die ons in de goede richting zouden moeten krijgen.
Deze waren niet echt hoge kwaliteit en heb ik later nog kunnen vervangen met betere Tilesets van hogere kwaliteit.

De voornaamste reden dat ik voor bestaande Tilesets heb gekozen is dat er eigenlijk wel alles meteen inzit wat we nodig hebben om de map te kunnen maken.
Als wij zelf de Tileset hadden moeten maken zouden we daar in de "long term" daar meer tijd aan kwijt zijn.

*TileSet 1*
![TileSet1](https://imgur.com/lw4Mapx.png)

*TileSet 2*
![Tileset2](https://imgur.com/SIb8Sh8.png)

*TileSet 3*
![Tileset3](https://imgur.com/klkiKzI.png)

## Week 4

Omdat ik veel tijd kwijt was met het zoeken naar een Tileset die we konden gebruiken ben ik pas in week 4 begonnen met maken van de Tilemap.
Tijdens het maken van de eerste map kwam ik achter een probleem. sommige stukken in de Tileset kwamen niet goed uit op de 64x64 grid die ingesteld stond. 
Waardoor niet alle mogelijkheden die in de Tileset stonden gebruikt konden worden. 

Toen ik samen met mijn projectgroep hier naar ging kijken, dachten wij eerst dat de Tileset niet helemaal in orde was.
Later zijn we erachter gekomen dat we de grid op 32x32 moeten zetten om alle assets die in de Tileset zitten te kunnen gebruiken.

*eerste map*
![FirstMap](https://imgur.com/mOSFbI2.png)

*tweede map*
![Secondmap](https://imgur.com/vw42qGe.png)

## Week 5

In deze week ben ik bezig geweest met de objecten toevoegen aan de Tilemap. 
Dus in dit geval alle kamers en cellen waar de gevangen, bewakers en werknemers naar toe kunnen gaan.

Bij elk object wat je aanmaakt kun je ook meteen custom variable mee geven die in de code gebruikt kan worden.
Bijvoorbeeld hoeveel bedden een holding cell heeft, hoeveel douchekoppen de douches hebben en hoeveel cellen in een cellblock.

Deze week zijn we er ook achter gekomen dat ik bij het aanmaken van de Tilemap de "Infinte map" setting heb aangezet.
Dit zorgt ervoor dat de map zich automatisch uitbreid als je voorbij de huidige bounderies gaat. dit zorgde alleen even een probleem voor onze programmeurs omdat deze dat ook niet wisten.
Want samen met deze setting veranderen er wat dingen op de achtergrond. zoals dat er chunks worden aangemaakt en dat ze dan inplaats van gewoon de hele rits van tiles af te gaan om de map te tekenen.
Dat ze nu dat eerst per chunk moeten doen en daarna pas de hele chunk kunnen laden. 
Dit zorgde ervoor dat er een extra omweg in de code moesten maken wat voor wat performance issues zorgden.

*Object Layers*
![Objectlayers](https://imgur.com/XiGmbcW.png)

## Week 6

Nu dat het gedoe van de draw methoden wel grooten deels opgelost is werdt het tijd om verder te werken aan de map en deze uit te breiden.
Dus het was tijd om de werkelijke cell blokken te gaan maken. Maar omdat deze nogal groot zijn heb ik eerst besloten om dit op te hakken in kleine stukjes om het makkelijk te maken.
Dit deed ik dus door kleine presets te maken die ik dan kan gebruiken om het hele cellblock in een keer te maken.

*Presets*

![presets](https://imgur.com/LFxFx7a.png)

Na het maken van de presets en klein beetje te experimenteren van hoe ik de cel blokker wou gaan indelen.
Was het tijd om alles samen te voegen en af te maken.

*Cellblock*

![Cellblock](https://imgur.com/7xjrHR8.png)

Nadat de cellblocken gemaakt waren was het tijd om alle objecten van de ruimtes er aan toe te voegen.

*Objecten cellblock*

![objCells](https://imgur.com/Fk1OAfb.png)

In totaal zijn er 4 Cell blokken en als laatste heb ik nog een Yard toegevoegd om het een beetje mooi te maken.

En dit is het eindresultaat.

![fullPrison](https://imgur.com/aaQzYl6.png)

## Week 7/8

Nu dat de Tilemap klaar is was het tijd om te gaan kijken naar documentatie zoals het PVA en het UML diagram.
Ik heb na het eerste inlever moment de aantekeningen erbij gehaald die toen zijn gegeven en het boek "Project-management" van roel grit.
Op deze manier heb ik groot en deels het hele PVA aangepast zoals nodig was.

Daarna ben ik in week 8 het UML diagram ge-update na de code die op dit moment aanwezig na het beste van wat ik kan.

---

# Overige opdrachten Markdown

## In het bedrijfsleven wordt steeds meer in software gesimuleerd

De reden hiervoor is omdat het maken van een applicatie om dingen te simuleren goedkoper is dan het te testen.
In een simulatie kun je op nouwkeurige date situaties nabootsen en het object wat gesimuleerd moet worden ook meteen aanpassen als er ergens een fout word aangetroffen.


## applicaties die gebruiken maken van het JSON formaat

- ### Minecraft
Maakt gebruik van JSON voor NBT tags voor data opslag van items.

- ### Facebook
Maakt gebruik van GraphQL wat is gebaseerd op JSON.

- ### Instagram
Maakt gebruik van JSON voor opslag en authenticatie.

- ### Youtube
Maakt gebruikt van Json Web Token (JWT) voor het inloggen van users.

- ### Twitter
De API van twitter geeft data terug in JSON formaat voor ease of use.
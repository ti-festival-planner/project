# Week 7
[Raoul de Heer - 2165787](https://github.com/raouldeheer)
## Deze week op de agenda

-   [Javadoc](#Javadoc)
-   [Opschoning](#Opschoning)
-   [JasperMode](#JasperMode)

## Javadoc
Deze week heb ik aan alle methodes javadoc toegevoegd.  
Zie commit [130f6c9](https://github.com/ti-festival-planner/project/commit/130f6c93ebaa7da588056ce0da22b9f0aeca5027)


## Opschoning 
Deze week heb ik ook wat code opgeschoond.  
Zie commit [5f18e1a](https://github.com/ti-festival-planner/project/commit/5f18e1a05345fdaaefb5e715faf926a410077559) 


## JasperMode 
Bij Jasper werkte het path naar resources niet, daarom heb ik voor Jasper "JasperMode" gemaakt.  
```java
private String tilemapName = "prison_time_the_jason_V3.json";
private String resourcePath = "./resources/"; // Path naar resources.
//private String resourcePath = "D:\\HET\\PATH\\NAAR\\DE\\RESOURCES\\FOLDER\\BIJ\\JASPER"; // Path naar resources bij Jasper.
```
Een simpele variable om te veranderen.  
```java
File jsonInputFile = new File("./resources/prison_time_the_jason_V3.json");
File image2 = new File("./resources/"+imageString);
=========================== veranderd in ==============================
File jsonInputFile = new File(resourcePath+tilemapName);
File image2 = new File(resourcePath +imageString);
```
Dit path is ook in de Prisoner class veranderd.  

Zie commit [152e65a](https://github.com/ti-festival-planner/project/commit/152e65a7b0df1d6116b83d7d383ec5cc48740553)
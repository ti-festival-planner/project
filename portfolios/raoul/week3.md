# Week 3
[Raoul de Heer - 2165787](https://github.com/raouldeheer)
## Deze week op de agenda

-   [Schedule opslaan en laden](#Schedule-opslaan-en-laden)
-   [Code review](#Code-review)

## Schedule opslaan en laden
Het opslaan van data gemaakt met javaio op deze manier:
```java
public static void writeData(String filename, Schedule schedule) {
    try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
        output.writeObject(schedule);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

Het laden van data gemaakt op deze manier:
```java
public static Schedule readData(File file) {
    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file.getName()))) {
        return (Schedule)(input.readObject());
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return null;
    }
}
```
Zie commit [656f399](https://github.com/ti-festival-planner/project/commit/656f399a4cec40198632edb49ab433b324261a9f) 

## Code review
Deze week [deze PR](https://github.com/ti-festival-planner/project/pull/8) gereviewd  
En wijzigingen aangebracht om aan de code style guide te voldoen, in commits [8141237](https://github.com/ti-festival-planner/project/commit/81412378385d1ec5fbdc2a53cf0e37d3220092ee) en [56f261d](https://github.com/ti-festival-planner/project/commit/56f261d53c73f3996bd671d79cb2a1191a0a0d44)  


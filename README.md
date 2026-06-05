# ProjectNotOfficial
# Instructions to run code in NetBeans

1. Create new project in NetBeans
2. Choose Java with Maven > FXML JavaFX Maven Archetype
3. Paste downloaded JAVA files (not including pom.xml) into Documents > NetBeansProjects > YourProjectName > src > main > java > com > mycompany > yourprojectname
4. In each class, change the first line of code to the name of your project name

   Example: package com.mycompany.groupproject;
   
   ^ Change "groupproject" into your project name (ALL LOWERCASE). Do this for all classes
   
5. Under your project folder, go to Project Files > pom.xml
6. Copy and paste everything inside pom.xml (from this repository) into your pom.xml
7. Change <artifactId> from GroupProject into your project name

   Example: <artifactId>GroupProject</artifactId>
   
   ^ Change "GroupProject" into your project name
   
8. In the bottom part, change <mainClass> into your project name
9. 
   Example: <mainClass>com.mycompany.groupproject.Main</mainClass>
   
   ^ Change "groupproject" into your project name (ALL LOWERCASE)
   
10. Right click on project folder (The cup symbol) and then Run Maven > Other Goals
11. Type in javafx:run into the Goals section and then press Enter or click 'OK'
12. Done

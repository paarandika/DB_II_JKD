DB_II_JKD
=========
##Instructions to run
  
1.Run MySQL server on port 3306  
2.Create “db_ass_ii” database using “db_ass_ii.sql” file in project folder( user: root and no password)  
3.Run MongoDB on port 27017  
4.Run Neo4J on port  
5.Go to /bin/main and run Main.class (Or you can open the project and compile it and run Main class)  
6.It will read the .csv files in /bin and populate MySQL db.  
7.Then it will ask for number of records to be added to MongoDB db, enter a number for that.  
8.Then it will ask for month to check income by day.(Enter an int between 1-12)  
9.Then it will ask for month to check top 10 buyers.(Enter an int between 1-12)  

set the path in  src / ucsc / managers / OrderAnalyzer.java  

neo4j_path = "youdrive:/path/to/your/neo4j/db/";  

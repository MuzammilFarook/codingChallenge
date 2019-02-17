# CREDITSUISSE Challenge

Please run this program with ,

-Program arg as --inputFilePath="D:\\dis.txt" and 
-VM arg as -Dspring.profiles.active=h2 for H2 database and
-VM arg as -Dspring.profiles.active=h2 -Dspring.profiles.active=hsql for HSQL 

To run from command line, please use,
java -cp coding-challenge-1.0.2.jar com.creditsuisse.CSCodingChallengeApplication 
--inputFilePath="D:\\dis.txt" 
-Dlogging.level.com.creditsuisse=debug
-Dspring.profiles.active=h2

#H2 Db : http://localhost:8080/h2-console/

#NOTE
Since lombok utilities are been used in this project, you may face issues running with command line and IDE would be best option.


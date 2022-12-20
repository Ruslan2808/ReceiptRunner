## stack

- Java 17
- Spring MVC
- Spring Data JPA
- Spring Boot
- PostgreSQL
- Gradle 7.5

## get started

To run the project you will need *Java 17*, *Gradle 7.5* and *PostgreSQL*

To start the project you need to perform the following steps:
1. pull this project
2. сreate a *PostgreSQL* database
3. in the *application.properties* file which is located along the path *src/main/resources/*<br> 
   set the **```port```**, **```database name```**, **```username```** and **```password```**
4. using the *terminal* or *cmd* go to the folder with this project
5. enter the command ***gradle build*** and wait for it to complete
6. run this project with the command ***java -jar ReceiptRunner.jar "args"***
    - **```args```** - command line parameters in the format **```productId```**-**```productQty```** **```card```**-**```NumberDiscoundCard```**
    - example - ***java -jar ReceiptRunner.jar 1-2 3-4 5-6 card-1234***

## endpoints

The project is running on **```localhost:8080```**

**```GET```** - ***/receipt***
>description - creating a receipt<br>
>require parameters - **```productId```** and **```productQty```**<br>
>not require parameters - **```card```**<br>
>example - ***localhost:8080/receipt?productId=1&productQty=2&productId=3&productQty=4&productId=5&productQty=6&card=1234***

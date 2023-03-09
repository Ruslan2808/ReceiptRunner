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
2. сreate a *PostgreSQL* database with two tables **```product```** and **```discount_card```**
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

**```GET```** - ***/api/v1/products***
>description - getting all products<br>
>example - ***localhost:8080/api/v1/products***

**```GET```** - ***/api/v1/products/{id}***
>description - getting a product<br>
>example - ***localhost:8080/api/v1/products/1***

**```POST```** - ***/api/v1/products***
>description - saving a product<br>
>request body example:<br>
>```javascript
>{
>  "name": "Chicken 1.5kg",
>  "price": 15.54,
>  "isPromotional": false
>}

**```PUT```** - ***/api/v1/products/{id}***
>description - updating a product<br>
>url example - ***localhost:8080/api/v1/products/4<br>***
>request body example:<br>
>```javascript
>{
>  "name": "Cheese 1kg",
>  "price": 10.54,
>  "isPromotional": true
>}

**```DELETE```** - ***/api/v1/products/{id}***
>description - deleting a product<br>
>example - ***localhost:8080/api/v1/products/1***

**```GET```** - ***/api/v1/discount-cards***
>description - getting all discount cards<br>
>example - ***localhost:8080/api/v1/discount-cards***

**```GET```** - ***/api/v1/discount-cards/{id}***
>description - getting a discount card<br>
>example - ***localhost:8080/api/v1/discount-cards/1***

**```POST```** - ***/api/v1/discount-cards***
>description - saving a discount card<br>
>request body example:<br>
>```javascript
>{
>  "number": 3456,
>  "discount": 3.0
>}

**```PUT```** - ***/api/v1/discount-cards/{id}***
>description - updating a discount card<br>
>url example - ***localhost:8080/api/v1/discount-cards/2<br>***
>request body example:<br>
>```javascript
>{
>  "number": 7890,
>  "price": 5.0
>}

**```DELETE```** - ***/api/v1/discount-cards/{id}***
>description - deleting a discount card<br>
>example - ***localhost:8080/api/v1/discount-cards/1***

## cache implementations

The project contains two cache implementations:<br>

**```LRU Cache```** uses the least recently used (LRU) algorithm, where the key-value mappings that has<br>
not been used the longest is evicted from the cache. *LinkedHashMap* is used to store key-value mappings<br>
including their order. The least recently used item is stored at the beginning of the cache.

**```LFU Cache```** uses an algorithm that
counts the frequency of use of each element and removes those that<br>
are least accessed (LFU). *HashMap* is used to store key-value mappings, key-number-of-hits mappings,<br>
and number-of-hits-to-set-of-associated-key mappings (stored in a *LinkedHashSet*). To find the least frequently<br>
accessed key, the *minKeyFreq* counter variable is used, which stores the minimum frequency of accessing cache keys.

To initialize the cache, the **```CacheFactory```** is used, which reads the **```algorithm```** and **```capacity```** of the cache from the<br>
**```application.yml```**. If there are no parameters in this file, the default parameters will be used: **```algorithm```** - ***LRU***, **```capacity```** - ***10***
## Solventum Coding Challenge

### Instructions to run application

#### 1. After cloning repository, in terminal navigate into directory **/solventum-challenge**.

#### 2. Run gradle clean and build to ensure tests are working and dependencies are downloaded.

``` ./gradlew clean build ```

#### 3. Start the application with the below gradle command: 
 
``` ./gradlew bootRun ```

#### 4. Send Post requests to the /encode and /decode endpoints: 

``` curl -X POST -H "Content-Type: application/json" -d '{"url": "url = https://anotherverylongurl.com/waytoolong"}' http://localhost:8080/encode ```

``` curl -X POST -H "Content-Type: application/json" -d '{"url": "http://short.est/524b49"}' http://localhost:8080/decode```

#### 5. To test the concurrent requests limit, run the following command. 

``` curl --parallel --parallel-immediate  --parallel-max 15 -X POST -H "Content-Type: application/json" -d '{"url": "https://anotherverylongUrl/whySoLong"}' --config concurrentTest.txt ```

You should see 5 **"{"error": "Too many concurrent requests. Try again later."}"** as the curl command will run 15 parallel requests from the concurrentTest.txt file while the ```concurrent-requests.max``` limit is set to 10 in the application.yml. 
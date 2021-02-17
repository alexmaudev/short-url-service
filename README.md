**Short URL app**

How to run the application? from the directory ./short-url-alias-service please run the command "./mvnw spring-boot:run"

Request example: curl --location --request POST 'http://localhost:8080/shorturl' --header 'Content-Type: application/json' --data-raw '{
"url": "https://www.youtube.com"
}'

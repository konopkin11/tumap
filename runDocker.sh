
cd admins/
./gradlew build -x test
cd ..
cd eurekaServer/
./gradlew build -x test
cd ..
cd schedule-service/
./gradlew build -x test
cd ..
docker-compose up -d
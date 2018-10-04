call gradlew.bat build -x test
docker build --tag bp-wallet-server:latest --file bp-wallet-server-docker-file  --rm=true . 
docker rm -f bp-wallet-server-dev
docker run --detach --name=bp-wallet-server-dev --publish 9090:9090 -d bp-wallet-server:latest
docker build --tag bp-wallet-client:latest --file bp-wallet-client-docker-file  --rm=true . 
docker rm -f bp-wallet-client-dev
docker run --detach --name=bp-wallet-client-dev --publish 8080:8080 -d bp-wallet-client:latest
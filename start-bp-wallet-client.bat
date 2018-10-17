call gradle build -x test

@rem ********** Starts bp-wallet-client
call java -jar bp-wallet-client\build\libs\bp-wallet-client-1.0.0.jar

@rem ********** Starts in development profile will expect mysql Instance.
@rem java -jar -Dspring.profiles.active=development \bp-wallet-server\build\libs\bp-wallet-server-1.0.0.jar

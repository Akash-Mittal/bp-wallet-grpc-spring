call gradle build -x test

@rem ********** bp-wallet-server starts in default profile with H2 Instance.
call java -jar bp-wallet-server\build\libs\bp-wallet-server-1.0.0.jar

@rem ********** Starts in development profile will expect mysql Instance.
@rem java -jar -Dspring.profiles.active=development \bp-wallet-server\build\libs\bp-wallet-server-1.0.0.jar

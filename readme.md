# Onomatopoeia: 2D Online Multiplayer Game
---
## TLDR;

### dependencies:
```
brew install openjdk
sudo ln -sfn $(brew --prefix openjdk)/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
java --version

brew install maven
mvn --version
```

### run:
```
cd application
mvn clean install
mvn compile exec:java

cd websocket-server
mvn clean install
mvn compile exec:java

cd mocks/client
npm install
node mock-client.js
```
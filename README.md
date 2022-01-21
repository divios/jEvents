# jEvents [![](https://jitpack.io/v/divios/JEvents.svg)](https://jitpack.io/#divios/JEvents)

Light weight and simple library to create spigot events in the go.

## Usage

First, to use the api, you need to register your plugin

```java
@Override
public void onEnable() {
        JEvents.register(this);
}
```

jEvents provides a very simple event creation abstraction. Here is an example:

```java
    JEvents.subscribe(InventoryClickEvent.class)  // The class of the event you want to listen
        .handler(event->{
                event.setCancelled(true);
                // Do something here
        });
```

As you can see, the creation of a listener is way easier than spigot's API, you just need specify the Event class, and
you are good to go

More examples of the api usage:


```java
JEvents.subscribe(InventoryClickEvent.class)
        .filter(event -> event.getInventory().equals(myInv))   // Only apply the action inside the handler
        .filter(event -> event.getSlot() == 3)     // if the filters are met
        .handler(event -> {
                event.setCancelled(true);
                // Do something here
        });
```


```java
JEvents.subscribe(PlayerDeathEvent.class)
        .biHandler((subscription, playerDeathEvent) -> {
                playerDeathEvent.setDeathMessage("Ha you died loser");
                subscription.unregister();    // Unregisters the listener
        });
```

# Instalation

To get the jar, either download it from the releases tab either here
on [GitHub](https://github.com/divios/jEvents/releases)
or [build it locally](https://github.com/divios/jEvents#build-locally).

## With Jitpack:

Gradle:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

```

```groovy
dependencies {
    compileOnly 'com.github.divios:jEvents:Tag'
}
```

Maven:

```xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml

<dependency>
    <groupId>com.github.divios</groupId>
    <artifactId>jEvents</artifactId>
    <version>Tag</version>
    <scope>provided</scope>
</dependency>
```

Replace `Tag` with a release tag for jEvents. Example: `1.0`. You can also use `master` as the tag to get the latest
version, though you will have to clear your maven caches in order to update it.

## Build locally:

For Windows, use Git Bash. For Linux or OSX, just ensure you have Git installed. Navigate to the directory where you
want to clone the repository, and run:

```
git clone https://github.com/divios/jEvents
cd jEvents
./gradlew jar
```

After running these commands, the jar will be at `build/libs/jEvents.jar`. You may also need to add the jar to your
classpath. After that, you should be good to go!

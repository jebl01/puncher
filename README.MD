# Puncher
*A regex based string parser*

Parsing strings can be done in a million ways, and often are. Even within the same project.
This small util is an attempt to unite behind one way of doing things.
Regular expressions have been used since the dawn of days, and even if I have had my strong doubts about them, I must confess that they can be quite handy.

Puncher is configured with patterns containing named groups. Punchers can also be combined into a composite puncher, to break regex up into more manageable parts.

**Get it from Maven Central**<br>
[![Maven Central](https://img.shields.io/maven-central/v/io.github.jebl01/puncher.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:io.github.jebl01%20AND%20a:puncher)

```xml
<dependency>
    <groupId>io.github.jebl01</groupId>
    <artifactId>puncher</artifactId>
    <version>0.2</version>
</dependency>
```

### Usage
Say we have entities with composite keys like `<id>:<version>`. One way of parsing this is by splitting on `:` and expecting something like `String[2]` in return.
An other way is to write some regexp code to accomplish the same.

By using *Puncher* you can do this without breaking a sweat;

```java
final Puncher puncher = Punchers.forPattern("^(?<id>[^:]+):(?<version>[^:]+)$");
final Optional<String> idOpt = puncher.punch("123:4", "id");

assertTrue(idOpt.isPresent());
idOpt.ifPresent(id -> assertEquals("123", id));
```

It becomes even more tricky if the *key* comes in different formats. Say for example that you have evolved your system to include a *type* in the key, like `<type>:<id>:<version>`.

The *Puncher* code to solve this looks like;

```java
final Puncher puncher = Punchers.forPattern("^(?<id>[^:]+):(?<version>[^:]+)$")
        .then(Punchers.forPattern("^(?<type>[^:]+):(?<id>[^:]+):(?<version>[^:]+)$"));

final Optional<String> idOptShort = puncher.punch("123:4", "id");
final Optional<String> idOptLong = puncher.punch("book:123:4", "id");

assertTrue(idOptShort.isPresent());
idOptShort.ifPresent(id -> assertEquals("123", id));

assertTrue(idOptLong.isPresent());
idOptLong.ifPresent(id -> assertEquals("123", id));
```

And that's about it!
# VileKingdoms API

[![](https://jitpack.io/v/SirImperivm/VileKingdoms-API.svg)](https://jitpack.io/#SirImperivm/VileKingdoms-API)

The API of [VileKingdoms](docs/GUIDE.md), the Paper plugin of the kingdoms and the towns fighting for the world of the
server. The addons use it to read the kingdoms, the towns, the structures and the wars, and to listen to everything
that happens to them through Bukkit events, most of them cancellable.

## Documentation

- [Server guide](docs/GUIDE.md) ([PDF](docs/GUIDE.pdf)): how the plugin works, commands, permissions, placeholders and
  configuration.
- [API](docs/API.md) ([PDF](docs/API.pdf)): the interfaces and the events, with examples.

## Dependency

```groovy
repositories {
    maven { url = "https://jitpack.io" }
}

dependencies {
    compileOnly "com.github.SirImperivm:VileKingdoms-API:1.0.4"
}
```

The plugin includes the API at runtime: compile against it, never shade it. Maven and the other details are in
[docs/API.md](docs/API.md).

## License

This repository, the API, is released under the [MIT license](LICENSE). VileKingdoms itself is a proprietary plugin:
its code is not part of this repository and is not covered by this license.

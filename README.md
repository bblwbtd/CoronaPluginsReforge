# CoronaPluginReforge

The kotlin version of CoronaPlugin.

## Build

This project requires java 17 or above to build. You can build all projects
by executing the following command.

```shell
./gradlew shadowjar

# Build a specific project
./gradlew -p project_name shadowjar
```

Then, the artifact will be generated in the build/lib directory. You can use
this plugin by putting it to the plugin directory. Just like a normal plugin.

## User Manual

- [CoronaAuth](./auth/README.md)
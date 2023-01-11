# Discord Bot for our Coders Squad (Games) server
Bot that will do some things for make our life at this server more easily

# Building
Bot using default gradle build system with [shadow jar](https://imperceptiblethoughts.com/shadow/)
```shell
./gradlew shadowJar
```
then you will be able to find built file in the `build/libs` folder (file name will end by `-all.jar`).
That's it!

## Just running
If you want just run the bot (ex. on local machine) you can do it
```shell
./gradlew run
```
after this just configure `config` file and run bot again!
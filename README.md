# Robot Base

SERT's base robot code for Gradle and Kotlin.

## Things to Update Every Year

1. [Package name](https://github.com/SouthEugeneRoboticsTeam/Robot-Base/blob/master/build.gradle#L41) and actual package
1. [GradleRIO dependency](https://github.com/SouthEugeneRoboticsTeam/Robot-Base/blob/master/build.gradle#L13)

## Building

Unfortunately, GradleRIO made a bad decision to use dynamic versioning which is a feature Gradle has
that will find the latest version of a dependency for you. This sounds good, but is very bad for
several reasons: lack of build reproducibility, stuff could change under you without notice, Gradle
makes a network call to check if you have the latest version of a dependency on every build, etc.
The last example is the main issue because it prevents building robot code unless you have an
internet connection (which the robot doesn't). To get around this issue, you have to push code to
the robot with the `--offline` flag.

### TL;DR

1. To get everything downloaded and setup, run `./gradlew build` once **while online**
2. When pushing code to the robot, use `./gradlew deploy --offline`

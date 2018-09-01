# Robot Base

[![Travis][travis-img]][travis-url]
[![Coveralls][coveralls-img]][coveralls-url]

SERT's base robot code for Gradle and Kotlin.

## Yearly Repository Setup

There are two ways to go about the yearly repository setup. You can either set it up manually, or
make things easy on yourself and do it the automatic way.

### Manual Setup

```bash
$ git clone --depth=1 https://github.com/SouthEugeneRoboticsTeam/Robot-Base.git GameName-Year
$ rm -rf !$/.git
$ git init
$ git remote add origin https://github.com/SouthEugeneRoboticsTeam/GameName-Year.git
$ git add .
$ git commit -m "Initial commit"
$ git push origin master
```

#### Things to Update

1. [Package name](https://github.com/SouthEugeneRoboticsTeam/Robot-Base/blob/master/build.gradle#L41) and actual package
1. [GradleRIO dependency](https://github.com/SouthEugeneRoboticsTeam/Robot-Base/blob/master/build.gradle#L13)

### Automatic Setup

First, install the [`robot-maker`](https://github.com/SouthEugeneRoboticsTeam/robot-maker) CLI.
Then, use the CLI to clone the repository and update packages and Gradle as needed!

```bash
$ robot-maker ./PowerUp-2018
```

Boom! You're done!

[travis-img]: https://img.shields.io/travis/SouthEugeneRoboticsTeam/Robot-Base.svg?style=flat-square
[travis-url]: https://travis-ci.org/SouthEugeneRoboticsTeam/Robot-Base
[coveralls-img]: https://img.shields.io/coveralls/SouthEugeneRoboticsTeam/Robot-Base.svg?style=flat-square
[coveralls-url]: https://coveralls.io/github/SouthEugeneRoboticsTeam/Robot-Base

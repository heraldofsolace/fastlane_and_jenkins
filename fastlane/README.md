fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android clean

```sh
[bundle exec] fastlane android clean
```

Clean Build Folder

### android test

```sh
[bundle exec] fastlane android test
```

Runs all the tests

### android code_coverage

```sh
[bundle exec] fastlane android code_coverage
```

Generate jacoco Code Coverage Reports

### android increment_vc

```sh
[bundle exec] fastlane android increment_vc
```

Increment version code

### android build_apk

```sh
[bundle exec] fastlane android build_apk
```

Build release app bundle

### android deploy

```sh
[bundle exec] fastlane android deploy
```

Clean, Test, Build and Deploy a new version

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).

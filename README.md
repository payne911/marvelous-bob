# marvelous-bob-client
libGDX June game-jam submission.

# Tech Stack

* libGDX (framework)
* Gradle (build)
* JPackage + JLink (releases)
* [KryoNet](https://github.com/EsotericSoftware/kryonet) (networking)
* Lombok (boilerplate)
* Dagger2 (dependency injection)
* Logging (undecided): Minlog or libGDX's

# To test the Client locally

IntelliJ Run Configuration:

* Gradle project: ``marvelous-bob-client:desktop``
* Tasks: ``runGame``
* VM options: ``--enable-preview``
* Environment variables: ``mbs_isRemote=true``

The boolean value of ``mbs_isRemote`` depends on whether you want to communicate with the `marvelous-bob-server` that is hosted remotely on AWS, or the one that you might have started locally ([here](https://github.com/L-Applin/marvelous-bob-server) is the repo to clone). `true` contacts AWS.

# To build a release

Trigger the ``desktop:jpackageImage`` task: it will bundle Java 14 with the application, run JLink to optimize, and create an executable for the platform you have ran the task with (for example, from a Windows computer you'll get a `.exe` file).

The relevant generate files will then be inside ``desktop/build/jpackage/desktop``.

# Assets

Most of them come from [Kenney](https://kenney.nl/assets?t=platformer), licensed ``CC0 1.0 Universal)``. Thank you so much!
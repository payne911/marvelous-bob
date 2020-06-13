# Client
Client-side application for Marvelous Bob game.

## To test the Client locally (for devs)
The ``.run`` folder at the root of the repository contains a few IntelliJ ``Run Configuration``.

If you open the project in IntelliJ, you will automatically have your Run Configurations set up. Simply launch ``[CLIENT]`` along with 'remote' or 'server' option you want. If you want to test with a local server, launch `[SERVER] Local` beforehand.

### args
* VM options: ``--enable-preview``
* Environment variables: ``mbs_isLocal=true``

The boolean value of ``mbs_isLocal`` depends on whether you want to communicate with the `marvelous-bob-server` that is hosted remotely on AWS, or the one that you might have started locally. Any value but `true` contacts AWS.

Simply launching the ``DesktopLauncher`` from the ``desktop`` module will also do the trick, but try to ensure the two configurations presented above are adjusted properly.

## To build a release
Trigger the ``desktop:jpackageImage`` task: it will bundle Java 14 with the application, run JLink to optimize, and create an executable for the platform you have ran the task with (for example, from a Windows computer you'll get a `.exe` file).

The relevant generated files will then be inside ``desktop/build/jpackage/desktop``.

## Assets
Most of them come from [Kenney](https://kenney.nl/assets?t=platformer), licensed ``CC0 1.0 Universal``. Thank you so much!
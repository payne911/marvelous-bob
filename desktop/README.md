# Desktop Launcher
Not much else in here.

## To test the Client locally (for devs)
The ``.run`` folder at the root of the repository contains a few IntelliJ ``Run Configuration``.

* VM options: ``--enable-preview``
* Environment variables: ``mbs_isLocal=true``

The boolean value of ``mbs_isLocal`` depends on whether you want to communicate with the `marvelous-bob-server` that is hosted remotely on AWS, or the one that you might have started locally. Any value but `true` contacts AWS.

Simply launching the ``DesktopLauncher`` will do the trick, but try to ensure the two configurations presented above are adjusted properly.
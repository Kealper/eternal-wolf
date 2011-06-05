EternalWolf
===========
EternalWolf is a [Bukkit](http://bukkit.org) plugin that makes all tamed wolves invulnerable to damage.

The plugin can also be configured to restrict the number of wolves each player can tame.
To do this, change the `max_wolves` property in config.yml. The default limit is 5.

If `max_wolves` is set to -1, players will be able to tame an unlimited number of wolves (not recommended).

Compiling
---------
EternalWolf is packaged using [Maven](http://http://maven.apache.org/). To compile it, run `mvn package` from the terminal.
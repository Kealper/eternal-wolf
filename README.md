EternalWolf
===========
EternalWolf is a [Bukkit](http://bukkit.org) plugin that makes all tamed wolves invulnerable to damage.

The plugin can also be configured to restrict the number of wolves each player can tame.
To do this, change the `max_wolves` property in config.yml. The default limit is 5.

If `max_wolves` is set to -1, players will be able to tame an unlimited number of wolves (not recommended).

To release your own wolf, left-click on it while holding a bone. Ops can release any wolves with the exception of wolves that belong to other ops.

To find out who owns a wolf, right-click on it while holding a bone.

Compiling
---------
EternalWolf is packaged using [Maven](http://http://maven.apache.org/). To compile it, run `mvn package` from the terminal.
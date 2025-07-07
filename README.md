## [1.20+] Only Paper 📜 Necrolog ☠️ Handy log of player deaths

### _A simple plugin for logging player deaths._

>##  Available languages:

- en
- ru

 
>## Settings in config.yml:

```
afterDeath:
  ban: false
  # Write a message to all players about the coordinates of the player's death
  message: false
  # The sound of lightning
  sound: false
  spectator: false

banMessage: §cYou are dead
language: en
# The message that comes out after the command "/nl"
logMessage: '%index% %date% §7- %deathMessage%'
```



>## Available commands:

Permission "necrolog.use"

- /nl - open Necrolog
- /nl <page> - open Necrolog on a specific page.

Permission operator

- /nl reloadCfg - reload config
- /nl clearAll confirm - completely clear all entries in Necrolog

![image](https://github.com/user-attachments/assets/d2eaf7a7-221f-42ac-90e4-0f1ef3ca1e60)


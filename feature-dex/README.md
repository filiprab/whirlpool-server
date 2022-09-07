# DECENTRALIZING SAMOURAI


## I. Current architecture

### 1. Overview
- URLs are hardcoded into Java librairies
- single server per service
![](charts/current-architecture-global.png)


### 2. Current Whirlpool architecture
- 1 coordinator instance connected to:
    * MySQL server (to store banned clients, mix history, postmix address reuse...)
    * bitcoin node
- coordinator URL is hardcoded into client: `pool.whirl.mx`

![](charts/current-architecture.png)


Main issues:
- censorship: slow to recover if pool.whirl.mx were blacklisted (would require updating all clients with new hardcoded address)
- DDOS: single coordinator is an easy target
- scaling: coordinator is handling growing whirlpool traffic (900+ websocket connexions)
- mixing downtimes during coordinator upgrades


## II. Decentralizing Whirlpool
See [README-whirlpool.md](README-whirlpool.md)


## III. Decentralizing Soroban
TODO


## IV. Decentralizing Samourai backend
TODO
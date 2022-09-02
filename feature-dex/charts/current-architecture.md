graph LR
client((client)) -.->|hardcoded url| coordinator(coordinator)
client -->|REST| coordinator
client -->|websocket| coordinator


subgraph pool.whirl.mx
    coordinator -.- mysql[(mysql)]
    coordinator -.- bitcoind[bitcoind]
end
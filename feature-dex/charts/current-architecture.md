flowchart LR
client((client)) -.->|hardcoded url| whirl.mx


subgraph whirl.mx
    coordinator -.- mysql[(mysql)]
    coordinator -.- bitcoind[bitcoind]
end
flowchart TB

mysql[(mysql)] -.- coordinator1
mysql[(mysql)] -.- coordinator2
mysql[(mysql)] -.- coordinator3

subgraph pool.whirl1.mx
    coordinator1 -.- bitcoind[bitcoind]
end

subgraph pool.whirl2.mx
    coordinator2 -.- bitcoind2[bitcoind2]
end

subgraph pool.whirl3.mx
    coordinator3 -.- bitcoind3[bitcoind3]
end


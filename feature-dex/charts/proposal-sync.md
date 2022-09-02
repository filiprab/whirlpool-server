flowchart LR

sync[(sync)] -.- pool.whirl1.mx
sync[(sync)] -.- pool.whirl2.mx
sync[(sync)] -.- pool.whirl3.mx

subgraph pool.whirl1.mx
    coordinator1 -.- bitcoind[bitcoind]
end

subgraph pool.whirl2.mx
    coordinator2 -.- bitcoind2[bitcoind2]
end

subgraph pool.whirl3.mx
    coordinator3 -.- bitcoind3[bitcoind3]
end

pool.whirl2.mx --- 05btc([0.5btc])
pool.whirl1.mx --- 005btc([0.05btc])
pool.whirl3.mx --- 0001btc([0.001btc])
pool.whirl3.mx --- 001btc([0.01btc])





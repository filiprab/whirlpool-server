flowchart TB
list[hardcoded bootstrap nodes:<br>pool.whirl1.mx<br> pool.whirl2.mx<br> pool.whirl3.mx] --- client
client((client)) -.-> coordinator1(coordinator1)
client -.-> coordinator2(coordinator2)
client -.-> coordinator3(coordinator3)


subgraph pool.whirl1.mx
    coordinator1 -.- bitcoind[bitcoind]
end
005btc([0.05btc]) --- pool.whirl1.mx

subgraph pool.whirl2.mx
    coordinator2 -.- bitcoind2[bitcoind2]
end
05btc([0.5btc]) --- pool.whirl2.mx

subgraph pool.whirl3.mx
    coordinator3 -.- bitcoind3[bitcoind3]
end
pool.whirl3.mx --- 001btc([0.01btc])
pool.whirl3.mx --- 0001btc([0.001btc])

subgraph Soroban
coordinator1 --- soroban{Soroban}
end


coordinator1 -.- mysql[(mysql)]
coordinator2 -.- mysql[(mysql)]
coordinator3 -.- mysql[(mysql)]

coordinator2 --- soroban
coordinator3 --- soroban

pool.whirl1.mx --- Soroban

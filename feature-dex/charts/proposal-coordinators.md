flowchart TB

mysql[(mysql)] --- coordinator1
mysql[(mysql)] --- coordinator2
mysql[(mysql)] --- coordinator3
mysql[(mysql)] --- coordinator4

subgraph whirl4.mx
    coordinator4 -.- bitcoind4[bitcoind3]
end

subgraph whirl3.mx
    coordinator3 -.- bitcoind3[bitcoind3]
end

subgraph whirl2.mx
    coordinator2 -.- bitcoind2[bitcoind2]
end

subgraph whirl1.mx
    coordinator1 -.- bitcoind[bitcoind1]
end

whirl1.mx --- dashboard([unified status dashboard])
whirl2.mx --- dashboard
whirl3.mx --- dashboard
whirl4.mx --- dashboard

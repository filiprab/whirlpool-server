flowchart TD


subgraph soroban
soroban1
soroban2
soroban3
soroban4
end

subgraph coordinator
whirl1.mx
whirl2.mx
whirl3.mx
whirl1.mx
end

whirl1.mx --> |1. LIST_INPUTS| soroban
whirl1.mx --> |2. INVITE_INPUT utxo1,utxo2| soroban
soroban -.->|3. INVITE_INPUT utxo1| client1((client1))
soroban -.->|3. INVITE_INPUT utxo2| client2((client2))


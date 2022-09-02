flowchart TD

whirl2.mx -.- sync[(sync)]
whirl3.mx -.- sync[(sync)]
whirl4.mx -.- sync[(sync)]
whirl5.mx -.- sync[(sync)]

whirl1.mx[whirl1.mx<br>- offline -]

subgraph whirl2.mx
    
end

subgraph whirl3.mx
    
end

subgraph whirl4.mx
    
end

subgraph whirl5.mx
    
end

05btc([0.5btc]) --> whirl2.mx
005btc([0.05btc]) -->|reassigned| whirl5.mx
0001btc([0.001btc]) --> whirl3.mx
001btc([0.01btc]) --> whirl4.mx





classDef instance fill:#ffffde,stroke:#c0c066;
classDef offline fill:pink,stroke:red;
class whirl1.mx offline;
class whirl2.mx instance;
class whirl3.mx instance;
class whirl4.mx instance;
class whirl5.mx instance;
flowchart TD
client((client<br>for 0.5btc)) -.->|random instance| whirl1.mx[whirl1.mx<br>0.01bt]
whirl2.mx[whirl1.mx<br>0.5btc]
whirl3.mx[whirl1.mx<br>0.05btc]
whirl4.mx[whirl4.mx<br>0.001btc]

whirl1.mx-.->|redirects to<br>responsible instance|whirl2.mx

classDef instance fill:#ffffde,stroke:#c0c066;
class whirl1.mx instance;
class whirl2.mx instance;
class whirl3.mx instance;
class whirl4.mx instance;

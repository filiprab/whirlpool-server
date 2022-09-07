flowchart TD
client((client<br>for 0.5btc)) --->|1. connect to random instance| whirl1.mx[whirl1.mx<br>0.01bt]
whirl2.mx[whirl2.mx<br>0.5btc]

whirl1.mx-.->|2. redirects to<br>responsible instance|client
client--->|3. start mixing| whirl2.mx

classDef instance fill:#ffffde,stroke:#c0c066;
class whirl1.mx instance;
class whirl2.mx instance;
class whirl3.mx instance;
class whirl4.mx instance;

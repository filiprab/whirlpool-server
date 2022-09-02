flowchart TD
list[hardcoded bootstrap nodes:<br>whirl1.mx, whirl2.mx,<br>whirl3.mx, whirl4.mx...] --- client((client))
client -.->|random instance| whirl1.mx
client -.-> whirl2.mx
client -.-> whirl3.mx
client -.-> whirl4.mx


classDef instance fill:#ffffde,stroke:#c0c066;
class whirl1.mx instance;
class whirl2.mx instance;
class whirl3.mx instance;
class whirl4.mx instance;

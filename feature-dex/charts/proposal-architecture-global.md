flowchart TD
list[hardcoded bootstrap nodes:<br>soroban1, soroban2...] --- client((client))
client --->|random<br>soroban node| soroban1
soroban1 -->|DexConfig| client
soroban2
soroban3


classDef instance fill:#ffffde,stroke:#c0c066;
class soroban1 instance;
class soroban2 instance;
class soroban3 instance;

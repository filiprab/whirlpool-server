sequenceDiagram
    autonumber

    participant Client
    participant pool3 as whirl3.mx<br>[0.01btc & 0.001btc]
    participant pool2 as whirl2.mx<br>[0.5btc]

    Note left of Client: Client wants to mix on 0.5btc
    Note left of Client: Read local bootstrap instances list...<br>Connect to a random instance: whirl3.mx

    Client->>+pool3: REGISTER_INPUT [0.5btc]
    Note right of pool3: instance is not assigned to 0.5btc<br>it redirects client to the appropriate instance
    pool3-->>-Client: RedirectToInstance: whirl2.mx

    Client->>+pool2: REGISTER_INPUT [0.5btc]
    pool2->>+pool2: start mixing...
    

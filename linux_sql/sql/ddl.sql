\c host_agent;

CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id                  SERIAL NOT NULL,
    hostname            VARCHAR UNIQUE NOT NULL,
    cpu_number          INTEGER NOT NULL,
    cpu_architecture    VARCHAR NOT NULL,
    cpu_model           VARCHAR NOT NULL,
    cpu_mhz             FLOAT(3) NOT NULL,
    L2_cache            INTEGER NOT NULL,
    total_mem           INTEGER NOT NULL,
    timestamp           TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (hostname)
);

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    host_id             SERIAL NOT NULL,
    memory_free         INTEGER NOT NULL,
    cpu_idle            INTEGER NOT NULL,
    cpu_kernel          INTEGER NOT NULL,
    disk_io             INTEGER NOT NULL,
    disk_available      INTEGER NOT NULL,
    timestamp           TIMESTAMP NOT NULL,
    FOREIGN KEY (host_id) REFERENCES host_info (id)
);


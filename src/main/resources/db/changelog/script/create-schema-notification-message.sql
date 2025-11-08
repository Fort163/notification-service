CREATE TABLE IF NOT EXISTS notification_message
(
    uuid         uuid primary key,
    created_by   varchar(255),
    created_when timestamp,
    updated_by   varchar(255),
    updated_when timestamp,
    is_active    boolean NOT NULL,
    from_user    varchar(255) NOT NULL,
    to_user      varchar(255) NOT NULL,
    send_type   varchar(100) NOT NULL,
    message_type   varchar(100) NOT NULL,
    project      varchar(100) NOT NULL,
    message      text,
    message_code     varchar(255),
    json_object      text,
    path         varchar(255),
    message_path     varchar(255),
    send         boolean NOT NULL,
    received     boolean NOT NULL
);
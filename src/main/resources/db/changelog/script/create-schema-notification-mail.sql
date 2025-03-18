CREATE TABLE IF NOT EXISTS notification_mail
(
    uuid         uuid primary key,
    created_by   varchar(255),
    created_when timestamp,
    updated_by   varchar(255),
    updated_when timestamp,
    is_active    boolean,
    message_id   uuid,
    from_email   varchar(510),
    subject      varchar(1020),
    recipients   varchar(1020),
    send         boolean,
    content      text
);
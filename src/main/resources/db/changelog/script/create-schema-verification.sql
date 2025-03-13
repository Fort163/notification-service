CREATE TABLE IF NOT EXISTS verification_mail
(
    mail                 varchar(255) primary key,
    code                 varchar,
    verified             boolean,
    notification_mail_id uuid not null,
    constraint verification_mail_2_notification_mail_fk foreign key
        (notification_mail_id) references notification_mail
);

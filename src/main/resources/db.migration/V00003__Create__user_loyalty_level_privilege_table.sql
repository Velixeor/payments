create table auth.user_loyalty_level_privilege
(
    user_loyalty_level_id integer not null,
    privilege_id              integer not null,
    foreign key (user_loyalty_level_id) references auth.user_loyalty_level (id)
        on delete cascade,
    foreign key (privilege_id) references auth.privilege (id)
        on delete cascade
)
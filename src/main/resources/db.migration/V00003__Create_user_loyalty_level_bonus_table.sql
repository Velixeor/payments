create table auth.user_loyalty_level_bonus
(
    user_loyalty_level_id integer not null,
    bonus_id              integer not null,
    foreign key (user_loyalty_level_id) references auth.user_loyalty_level (id)
        on delete cascade,
    foreign key (bonus_id) references auth.bonus (id)
        on delete cascade
)
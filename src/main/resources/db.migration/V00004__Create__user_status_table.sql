create table auth.user_status
(
    id    integer primary key generated by default as identity,
    title text not null
)
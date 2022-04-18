CREATE TABLE area
(
    area_id bigint,
    name    varchar,
    CONSTRAINT area_pk primary key (area_id)
);


CREATE TABLE employer
(
    employer_id bigint,
    name        varchar,
    date_create timestamptz,
    description varchar,
    area_id     bigint,
    comment     varchar,
    views_count int,
    CONSTRAINT employer_pk primary key (employer_id),
    CONSTRAINT employer_area_fk foreign key (area_id) REFERENCES AREA (area_id)
);

CREATE TABLE vacancy
(
    vacancy_id      bigint,
    name            varchar,
    date_create     timestamptz,
    area_id         bigint,
    salary_to       int,
    salary_from     int,
    salary_currency varchar,
    salary_gross    boolean,
    created_at      timestamptz,
    employer_id     bigint,
    comment         varchar,
    views_count     int,
    CONSTRAINT vacancy_pk primary key (vacancy_id),
    CONSTRAINT vacancy_area_fk foreign key (area_id) REFERENCES AREA (area_id),
    CONSTRAINT vacancy_employer_fk foreign key (employer_id) REFERENCES EMPLOYER (employer_id)
);

--liquibase formatted sql

--changeset inter:1

CREATE TABLE IF NOT EXISTS public.drivers
(
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying UNIQUE NOT NULL,
    PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.drivers
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.cars
(
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    is_ready BOOLEAN NOT NULL,
    purpose character varying NOT NULL,
    PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cars
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.bids
(
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    work_purpose character varying NOT NULL,
    is_finished BOOLEAN NOT NULL,
    driver_feedback character varying,
    driver_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT driver_id_fkey FOREIGN KEY (driver_id) REFERENCES public.drivers (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.bids
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.car_driver_relations
(
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    car_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    CONSTRAINT car_id_fkey FOREIGN KEY (car_id) REFERENCES public.cars (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
    CONSTRAINT driver_id_fkey FOREIGN KEY (driver_id) REFERENCES public.drivers (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
    UNIQUE (car_id, driver_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.car_driver_relations
    OWNER to postgres;

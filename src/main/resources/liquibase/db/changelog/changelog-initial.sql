--liquibase formatted sql

--changeset inter:1
INSERT into drivers (name) VALUES('Driver 1');
INSERT into drivers (name) VALUES('Driver 2');
INSERT into drivers (name) VALUES('Driver 3');

INSERT into cars (is_ready, purpose) VALUES(True, 'Purpose 1');
INSERT into cars (is_ready, purpose) VALUES(False, 'Purpose 2');
INSERT into cars (is_ready, purpose) VALUES(True, 'Purpose 3');
INSERT into cars (is_ready, purpose) VALUES(False, 'Purpose 4');

INSERT into bids (work_purpose, is_finished, driver_feedback, driver_id) VALUES('Purpose 2', True, 'Good', 2);
INSERT into bids (work_purpose, is_finished, driver_feedback, driver_id) VALUES('Purpose 1', False, 'Good', 2);
INSERT into bids (work_purpose, is_finished, driver_feedback, driver_id) VALUES('Purpose 4', True, 'Good', 2);
INSERT into bids (work_purpose, is_finished, driver_feedback, driver_id) VALUES('Purpose 5', False, 'Good', 2);

INSERT into car_driver_relations(car_id, driver_id) VALUES(1,1);
INSERT into car_driver_relations(car_id, driver_id) VALUES(1,2);
INSERT into car_driver_relations(car_id, driver_id) VALUES(2,1);
INSERT into car_driver_relations(car_id, driver_id) VALUES(3,1);
INSERT into car_driver_relations(car_id, driver_id) VALUES(4,2);
INSERT into car_driver_relations(car_id, driver_id) VALUES(4,3);

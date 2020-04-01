--Patient
INSERT INTO patient (id,name,surname,address) VALUES (1,'Mateusz','Haberla','Poznań')
INSERT INTO patient (id,name,surname,address) VALUES (2,'Morty','Smith','Smith Residence')
INSERT INTO patient (id,name,surname,address) VALUES (3,'Summer','Smith','Smith Residence')
INSERT INTO patient (id,name,surname,address) VALUES (4,'Beth','Smith','Smith Residence')
INSERT INTO patient (id,name,surname,address) VALUES (5,'Jerry','Smith','Smith Residence')

--Doctor
INSERT INTO doctor (id,name,surname,specialization) VALUES (1,'Rick','Sanchez','RADIOLOGY')
INSERT INTO doctor (id,name,surname,specialization) VALUES (2,'Jerzy','Zięba','PSYCHIATRY')
INSERT INTO doctor (id,name,surname,specialization) VALUES (3,'Gregory','House','ALL')
INSERT INTO doctor (id,name,surname,specialization) VALUES (4,'Zbigniew','Religa','CARDIOLOGY')
INSERT INTO doctor (id,name,surname,specialization) VALUES (5,'Hipo','Krates','ALL')

--Appointments
INSERT INTO appointment (id,date,hour,place,doctor,patient) VALUES (1,'2020-05-30T23:03:07.508Z','2020-05-30T23:03:07.508Z','Poznań',1,1)
INSERT INTO appointment (id,date,hour,place,doctor,patient) VALUES (2,'2020-05-30T23:03:07.508Z','2020-05-30T23:03:07.508Z','Poznań',2,2)
INSERT INTO appointment (id,date,hour,place,doctor,patient) VALUES (3,'2020-05-30T23:03:07.508Z','2020-05-30T23:03:07.508Z','Poznań',1,3)
INSERT INTO appointment (id,date,hour,place,doctor,patient) VALUES (4,'2020-05-30T23:03:07.508Z','2020-05-30T23:03:07.508Z','Poznań',2,4)
INSERT INTO appointment (id,date,hour,place,doctor,patient) VALUES (5,'2020-05-30T23:03:07.508Z','2020-05-30T23:03:07.508Z','Poznań',2,5)
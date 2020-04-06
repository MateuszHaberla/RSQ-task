--Patient
INSERT INTO patient (id,name,surname,address) VALUES (101,'Mateusz','Haberla','Wrocław')
INSERT INTO patient (id,name,surname,address) VALUES (102,'Morty','Smith','Smith Residence')
INSERT INTO patient (id,name,surname,address) VALUES (103,'Summer','Smith','Smith Residence')
INSERT INTO patient (id,name,surname,address) VALUES (104,'Beth','Smith','Smith Residence')
INSERT INTO patient (id,name,surname,address) VALUES (105,'Jerry','Smith','Smith Residence')

--Doctor
INSERT INTO doctor (id,name,surname,specialization) VALUES (101,'Rick','Sanchez','RADIOLOGY')
INSERT INTO doctor (id,name,surname,specialization) VALUES (102,'Jerzy','Zięba','PSYCHIATRY')
INSERT INTO doctor (id,name,surname,specialization) VALUES (103,'Gregory','House','ALL')
INSERT INTO doctor (id,name,surname,specialization) VALUES (104,'Zbigniew','Religa','CARDIOLOGY')
INSERT INTO doctor (id,name,surname,specialization) VALUES (105,'Hipo','Krates','ALL')

--Appointments
INSERT INTO appointment (id,time_Of_Appointment,office_Address,doctor_Id,patient_Id) VALUES (101,'2020-05-30T23:03:07.508Z','Poznań',101,101)
INSERT INTO appointment (id,time_Of_Appointment,office_Address,doctor_Id,patient_Id) VALUES (102,'2020-05-30T23:03:07.508Z','Poznań',102,102)
INSERT INTO appointment (id,time_Of_Appointment,office_Address,doctor_Id,patient_Id) VALUES (103,'2020-05-30T23:03:07.508Z','Poznań',101,103)
INSERT INTO appointment (id,time_Of_Appointment,office_Address,doctor_Id,patient_Id) VALUES (104,'2020-05-30T23:03:07.508Z','Poznań',102,104)
INSERT INTO appointment (id,time_Of_Appointment,office_Address,doctor_Id,patient_Id) VALUES (105,'2020-05-30T23:03:07.508Z','Poznań',102,105)
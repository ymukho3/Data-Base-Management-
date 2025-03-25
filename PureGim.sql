show databases;
create database PureGim;
use PureGim;
create table customer(customerID int primary key,age int, 
gender varchar(6), email varchar(32),Subtype varchar(32),
 CustomerAddress varchar(32),customerName varchar(32),joinDate DATE, coachID int, foreign key (coachID) references coach(coachID) ON DELETE cascade);
select * from customer;
insert into customer (customerID,age,gender,email,Subtype,CustomerAddress,customerName,joinDate,coachID)
 values(1,21,'male','Ahmad.al@gmail.com','yearly','Ramallah','Ahmad alemad','2024-1-22',1),
 (2,27,'female','alaa_mohsen@gmail.com','weekly','Ramallah','alaa muhsen','2023-12-07',3),
 (3,21,'male','saed.salem@gmail.com','yearly','silwad','saed salem','2024-2-22',5);
insert into customer (customerID,age,gender,email,Subtype,CustomerAddress,customerName,joinDate,coachID)
 values(4,18,'male','qais_ahmad@gmail.com','yearly','albereh','qais ahmad','2024-1-02',4),
 (9,20,'female','noura.asad@gmail.com','monthly','kufermalek','noura asad','2024-4-11',5),
 (7,21,'male','laila.rmal@gmail.com','yearly','silwad','laila mohammad','2024-2-22',1),
 (11,18,'male','mohammad.tahreer@gmail.com','monthly','dirjreer','mohammad tahreer','2024-4-22',2),
 (8,20,'female','ayam_jad12@gmail.com','monthly','kufermalek','ayam jad','2024-5-02',3),
 (15,21,'male','ibraheem.maher','yearly','silwad','ibraheem maher','2024-2-22',1);
 insert into customer (customerID,age,gender,email,Subtype,CustomerAddress,customerName,joinDate,coachID)
 values(6,22,'female','hadeel_wael@gmail.com','monthly','AL-Ram','hadeel wael','2023-5-12',1),
 (10,27,'male','rami_ahmad@gmail.com','yearly','silwad','rami ahmad','2024-6-20',2),
 (12,29,'male','salim_omar@gmail.com','yearly','Qalandiya','salim omar','2022-3-08',5),
 (13,18,'female','anwar_mahmoud@gmail.com','monthly','kufermalek','anwar mahmoud','2024-8-02',3),
 (14,21,'male','mohammad_kamal@gmail.com','yearly','Ramallah','mohammad kamal','2023-2-17',4),
 (16,17,'male','amar_ashraf@gmail.com','weekly','Birzeit','amar ashraf','2024-6-02',1),
 (17,22,'female','tala.ziad@gmail.com','monthly','Beitunia','tala ziad','2023-4-24',3),
 (18,27,'female','lana_anas@gmail.com','monthly','Jalazone','lana anas','2024-11-20',5),
 (19,29,'male','zain.osama@gmail.com','yearly','dirjreer','zain osama','2024-12-14',1);
 insert into customer(customerID,age,gender,email,Subtype,CustomerAddress,customerName,joinDate,coachID)
 values(21,31,'male','Yazan.abu@gmail.com','yearly','Ramallah','Yazan abusamer','2024-1-5',1),
 (22,27,'female','Zaina_mosa@gmail.com','weekly','Ramallah','Zaina mosa','2023-10-6',4),
 (23,21,'male','salemQaher@gmail.com','yearly','silwad','salem Qaher','2024-4-4',2),
 (24,18,'male','ahmad_abed@gmail.com','yearly','albereh',' ahmad abed alkarem','2024-2-07',3),
 (25,20,'female','dana_asad@gmail.com','monthly','kufermalek','Dana asad','2024-11-4',2),
 (26,21,'male','Anas_karem@gmail.com','yearly','silwad','Anas Karem','2024-2-22',1),
 (27,18,'male','tahreer_alhaj@gmail.com','monthly','dirjreer','tahreer alhaj ','2024-4-22',3),
 (28,20,'female','sundos_jad55@gmail.com','monthly','kufermalek','sundos jad','2024-5-02',2),
(29,22,'female','layan_wael@gmail.com','monthly','AL-Ram','layan wael','2023-5-12',5),
 (30,17,'male','ayham_rami77@gmail.com','yearly','silwad','Ayham rami','2024-6-1',1),
 (31,29,'male','omar_hajmohammad@gmail.com','yearly','Qalandiya','omar hajmohammad ','2024-01-01',4);
 create table coach(coachID int primary key,coachName varchar(32),salary int,work_hours int);
 select * from coach;
insert into coach (coachID,coachName,salary,work_hours)
 values(1,'Ahmad Wael',2000,20),
 (2,'Sameer Ali',3000,25),
 (3,'Jehad Omar',1800,15),
 (4,'Nagham Yousef',2500,22),
 (5,'Eman Ayman',3500,30);
create table machine(machineID int primary key, machineName varchar(32));
insert into machine(machineID, machineName)values(1,'Hack Squat'),
(2,'Leg Extension Machine'),
(3,'Outer Or Inner Thigh Machines'),
(4,'Seated Leg Curl'),(5,'Machine Bicep Curl'),
(6,'Lat Pull-Downs'),(7,'Assisted Pull-Up'),
(8,'Row'),(9,'Cable Machines'),(10,'Sit-Up Benches'),
(11,'Captain’s Chairs'),(12,'Back Extensions'),
(13,'StairMaster'),(14,'Stationary Bikes'),(15,'Treadmill');
select * from machine;
create table training (trainingID int primary key, trainingName varchar (20), coachID int, trainingTime varchar (20), FOREIGN KEY (coachID)  
REFERENCES coach(coachID) ON DELETE CASCADE);
select * from training;
insert into training (trainingID, trainingName, coachID, trainingTime)
values (1, 'leg training', 2, '30'), 
(2, 'arm training', 5, '20'),
(3, 'stretches training', 1, '20'),
(4, 'shoulder training', 2, '30'),
(5, 'cardio training', 1, '20'),
(6, 'stomach training', 3, '30'),
(7, 'back training', 3, '20'),
(8, 'Chest training', 2, '30'),
(9, 'full body training', 2, '30');
insert into machine(machineID,machineName)values(16,'Hip Abduction');
insert into machine(machineID,machineName)values(17,'Elliptical');
create table machine_training (machineID int,trainingID int,primary key (machineID,trainingID),
 foreign key (machineID) references machine (machineID) ON DELETE CASCADE,
 foreign key (trainingID) references  training(trainingID) ON DELETE CASCADE);
insert into machine_training (machineID,trainingID) 
 values (1,1),(2,1),(3,1),(4,1),(8,1),(16,1),
 (5,2),(6,2),(7,2),(8,2),(9,2),(11,2),(12,2),
 (7,3),(8,3),(9,3),(5,4),(6,5),(7,5),(9,5),
 (17,5),(11,6),(8,6),(12,7),(9,7),(10,8),
 (1,9),(5,9),(7,9),(8,9),(10,9),(12,9),(15,9);
 select * from machine_training;
CREATE TABLE customer_training (
    customerID INT,
    trainingID INT,
    PRIMARY KEY (customerID, trainingID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID) ON DELETE CASCADE,
    FOREIGN KEY (trainingID) REFERENCES training(trainingID) ON DELETE CASCADE
);
 insert into customer_training(customerID,trainingID)values(3,4),(3,1),(4,1),(4,6),(6,5),(7,9),(7,1),(7,5),
 (8,2);
  insert into customer_training(customerID,trainingID)values(9,1),(9,7),(9,6),(10,1),(10,2),(10,5),(11,5),(11,6);
   insert into customer_training(customerID,trainingID)values(12,7),(13,6),(13,2),(13,5),(14,5),
  (15,5),(16,2),(16,8),(16,6),(17,1),(17,3),(17,9),
 (18,3),(18,5),(18,9),(19,6),(21,5),(22,2),(23,4),(23,7),(24,1),(24,7),(24,9),
 (25,2),(25,3),(26,5),(27,1),(27,2),(27,5),(28,5),(27,6),(27,9),(28,1),(28,2),
 (29,4),(29,6),(30,1),(30,2),(30,5),(31,2),(31,7);
 select * from customer_training;
create table gymadmin(adminID int primary key, adminName varchar(32), adminPassword varchar(32));
insert into gymadmin(adminID, adminName, adminPassword)values(1,'Samin Ali', 'Ali123'),
(2,'Anas Samer', 'A@2022'),
(3,'Amera Elhammad', 'Hammad111');
select * from gymadmin;
 show tables;


alter table customer add column Cpayment int;
ALTER TABLE customer
ADD column Cpayment int;
SET SQL_SAFE_UPDATES = 0;
UPDATE customer
SET Cpayment = 3000
WHERE subtype = 'yearly';
UPDATE customer
SET Cpayment = 300
WHERE subtype = 'monthly';
UPDATE customer
SET Cpayment = 40
WHERE subtype = 'weekly';
UPDATE customer
SET subtype= 'monthly'
WHERE subtype = 'mounthly';
CREATE TEMPORARY TABLE temp_customer_ids AS
SELECT customerID
FROM customer
ORDER BY RAND()
LIMIT 12;
UPDATE customer
SET Cpayment = 0
WHERE customerID IN (SELECT customerID FROM temp_customer_ids);
DROP TEMPORARY TABLE temp_customer_ids;
create database math;
use math;

CREATE TABLE student (
    std_reg_no INT PRIMARY KEY,
    std_name VARCHAR(100),
    f_name VARCHAR(100),
    mobile_no VARCHAR(15),
    dob DATE,
    std_address VARCHAR(150),
    balance DOUBLE DEFAULT 0
);
ALTER TABLE student 
ADD COLUMN start_time TIME,
ADD COLUMN end_time TIME;



CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    std_reg_no INT,
    std_name VARCHAR(100),
    attendance_date DATE,
    status VARCHAR(10),

    FOREIGN KEY (std_reg_no) REFERENCES student(std_reg_no)
);

INSERT INTO student 
(std_reg_no, std_name, f_name, mobile_no, dob, std_address, balance, start_time, end_time)
VALUES 
(1, 'Rahul', 'Ramesh', '9876543210', '2010-05-10', 'Patna', 0, '04:00:00', '05:00:00');



CREATE TABLE results (
    result_id INT AUTO_INCREMENT PRIMARY KEY,

    std_reg_no INT NOT NULL,
    std_name VARCHAR(100) NOT NULL,
    dob DATE,

    subject_name VARCHAR(50) NOT NULL,
    max_marks INT NOT NULL,
    obtained_marks INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE fee (
    fee_id INT AUTO_INCREMENT PRIMARY KEY,
    std_reg_no INT,
    std_name VARCHAR(100),
    month VARCHAR(20),
    year INT,
    amount DOUBLE,
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (std_reg_no) REFERENCES student(std_reg_no)
);

CREATE TABLE payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    std_reg_no INT,
    fee_id INT,
    razorpay_payment_id VARCHAR(100),
    amount DOUBLE,
    status VARCHAR(20)
);



CREATE TABLE attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    std_roll_no INT,
    std_name VARCHAR(100),
    std_class VARCHAR(20),
    status VARCHAR(10),
    attendance_date DATE,
    UNIQUE KEY unique_record (std_roll_no, attendance_date)
);


select * from student;

drop database math;
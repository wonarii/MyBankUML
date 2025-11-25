-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 14, 2025 at 07:53 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bankapp_db`
--

-- WIPE THE DATABASE CLEAN BEFORE RUNNING

DROP DATABASE IF EXISTS `bankapp_db`;
CREATE DATABASE `bankapp_db`;


-- --------------------------------------------------------

--
-- Table structure for table `account_list`
--

CREATE TABLE `account_list` (
  `id` int(11) NOT NULL,
  `user_first_name` varchar(100) NOT NULL,
  `user_last_name` varchar(100) NOT NULL,
  `user_birthday` date NOT NULL,
  `user_address` varchar(100) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_role` varchar(100) NOT NULL,
  `user_balance` double DEFAULT NULL,
  `user_bank_id` int(11) NOT NULL,
  `user_bank` varchar(100) NOT NULL,
  `user_branch_id` int(11) NOT NULL,
  `user_branch` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account_list`
--

INSERT INTO `account_list` (`id`, `user_first_name`, `user_last_name`, `user_birthday`, `user_address`, `user_email`, `user_password`, `user_role`, `user_balance`, `user_bank_id`, `user_bank`, `user_branch_id`, `user_branch`) VALUES
(1, 'Joe', 'White', '1999-11-10', '5463, Saint Michelle, Montreal, QC, H1Y 8U5', 'joe123@gmail.com', '$2y$10$zVa2xcOOnUOQAqezOhdVBezM/3/toBzgE5/o57X6FzgUMyZvM6xzC', 'user', 1000, 2, 'RBC', 4, 'RBC_Montreal'),
(2, 'Sara', 'Williams', '2001-06-13', '5465, Saint Michelle, Montreal, QC, H1Y 8U5', 'sara123@gmail.com', '$2y$10$7TCpnQ4GvwReW7nVi1rodOE6EA1Rak9AA0wrJraBsmHvNc2W5B.hG', 'user', 2000, 1, 'Dejardins', 1, 'Desjardins_Montreal'),
(3, 'John', 'Doe', '1995-05-16', '6373, PIX, Montreal, QC, H7Y 8U6 ', 'doe65@gmail.com', '$2y$10$5M.bT0g7bP3IjI5qqD8QEeeYwzg2RgHfWHh446zPrxVxGuPq7hDB2', 'teller', NULL, 4, 'CIBC', 10, 'CIBC_Montreal'),
(4, 'Lizy', 'May', '1997-03-12', '6373, King Street, Toronto, ON, U8Y 7W3 ', 'liz123@gmail.com', '$2y$10$bLSm/oYkQDcNyY4PX90sJePSd84tVjSknmKwWhCgbx8yZ08AIO2cy', 'admin', NULL, 3, 'BMO', 8, 'BMO_Toronto'),
(7, 'Alice', 'Johnson', '1990-05-15', '123 Main Street, Toronto', 'alice123@gmail.com', '$2a$10$3/OFA4rKCN/NYTvTS5Eblu1oGYzn8zm/QHvnLnxb9gWebNZFPZHcW', 'user', 1300, 1, 'Desjardins', 3, 'Desjardins_Sherbrook'),
(9, 'Charlie', 'Brown', '1995-07-20', '100 Elm Street, Toronto', 'charlie.brown@gmail.com', '$2a$10$TwzGNKA2T9L3fL2NogHM0uTO5C/v8AEO1wuxLgF5Cd.9epBEstwFS', 'user', 1500, 1, 'Desjardins', 2, 'Desjardins_Quebec'),
(11, 'Ethan', 'Hunt', '1980-11-12', '300 King Street, Toronto', 'ethan.hunt@gmail.com', '$2a$10$rr3G.8uSl53fpRVD4OvIWuvD9FKfE/Lu31np3Mlc87IS/BdVWxVqe', 'admin', 3000, 3, 'BMO', 1, 'Desjardins_Montreal');

-- --------------------------------------------------------

--
-- Table structure for table `bank_list`
--

CREATE TABLE `bank_list` (
  `bank_id` int(11) NOT NULL,
  `bank_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bank_list`
--

INSERT INTO `bank_list` (`bank_id`, `bank_name`) VALUES
(1, 'Desjardins'),
(2, 'RBC'),
(3, 'BMO'),
(4, 'CIBC');

-- --------------------------------------------------------

--
-- Table structure for table `branch_list`
--

CREATE TABLE `branch_list` (
  `branch_id` int(11) NOT NULL,
  `branch_name` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `branch_phone` varchar(10) NOT NULL,
  `bank_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `branch_list`
--

INSERT INTO `branch_list` (`branch_id`, `branch_name`, `location`, `branch_phone`, `bank_id`) VALUES
(1, 'Desjardins_Montreal', '2457, Saint Mishelle, Montreal, QC, HY0 5B2', "5143261010", 1),
(2, 'Desjardins_Quebec', '6786, Bordeaux, Quebec, QC, JY0 5H3', "43890283123", 1),
(3, 'Desjardins_Sherbrook', '1156, Red Street, Sherbrook, QC, UY0 5K6', "1235749401", 1),
(4, 'RBC_Montreal', '2946, PIX, Montreal, QC, H7T 4R5', "8484028340", 2),
(5, 'RBC_Quebec', '6732, Saint Rose, Quebec, QC, J6J 6F4', "1234567890", 2),
(6, 'RBC_Sherbrook', '1184, Blue Street, Sherbrook, QC, U7T 4V9', "1234567890", 2),
(7, 'BMO_Montreal', '2619, Rosemount, Montreal, QC, H5U 2D3', "1234567890", 3),
(8, 'BMO_Toronto', '5675, King Street, Toronto, ON, M5U 2G4', "1234567890", 3),
(9, 'BMO_Quebec', '6574, Saint Anne, Quebec, QC, A2U 7T9', "1234567890", 3),
(10, 'CIBC_Montreal', '2985, Mont Royal, Montreal, QC, H1Y 6T4', "1234567890", 4),
(11, 'CIBC_Quebec', '6438, Blache, Quebec, QC, U1T 8R3', "1234567890", 4),
(12, 'CIBC_Sherbrook', '1173, Gray Street, Sherbrook, QC, U7Y 3L5', "1234567890", 4);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_list`
--

CREATE TABLE `transaction_list` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `type` varchar(30) NOT NULL,
  `amount` double NOT NULL,
  `transaction_date` DATE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction_list`
--

INSERT INTO `transaction_list` (`transaction_id`, `user_id`, `type`, `amount`, `transaction_date`) VALUES
(1, 1, 'Withdraw', 90, '2025-11-22'),
(2, 2, 'Deposit', 50, '2021-12-01'),
(5, 9, 'deposit', 500, '2019-04-07'),
(6, 9, 'withdraw', 200, '2023-01-14');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_list`
--
ALTER TABLE `account_list`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_bank_id` (`user_bank_id`),
  ADD KEY `user_branch_id` (`user_branch_id`);

--
-- Indexes for table `bank_list`
--
ALTER TABLE `bank_list`
  ADD PRIMARY KEY (`bank_id`);

--
-- Indexes for table `branch_list`
--
ALTER TABLE `branch_list`
  ADD PRIMARY KEY (`branch_id`),
  ADD KEY `bank_id` (`bank_id`);

--
-- Indexes for table `transaction_list`
--
ALTER TABLE `transaction_list`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account_list`
--
ALTER TABLE `account_list`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `bank_list`
--
ALTER TABLE `bank_list`
  MODIFY `bank_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `branch_list`
--
ALTER TABLE `branch_list`
  MODIFY `branch_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `transaction_list`
--
ALTER TABLE `transaction_list`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account_list`
--
ALTER TABLE `account_list`
  ADD CONSTRAINT `account_list_ibfk_1` FOREIGN KEY (`user_bank_id`) REFERENCES `bank_list` (`bank_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `account_list_ibfk_2` FOREIGN KEY (`user_branch_id`) REFERENCES `branch_list` (`branch_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaction_list`
--
ALTER TABLE `transaction_list`
  ADD CONSTRAINT `transaction_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

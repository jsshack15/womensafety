-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2015 at 02:08 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `women_sec`
--

-- --------------------------------------------------------

--
-- Table structure for table `user_data`
--

CREATE TABLE IF NOT EXISTS `user_data` (
  `mobile_no` varchar(10) NOT NULL,
  `long` float NOT NULL,
  `lat` float NOT NULL,
  `contact_no` varchar(10) NOT NULL,
  `contact_name` varchar(30) NOT NULL,
  `msg` tinyint(1) NOT NULL,
  UNIQUE KEY `mobile_no_2` (`mobile_no`),
  KEY `mobile_no` (`mobile_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_data`
--

INSERT INTO `user_data` (`mobile_no`, `long`, `lat`, `contact_no`, `contact_name`, `msg`) VALUES
('9876543210', 29.0226, 77.7599, '', '', 0),
('9876543211', 28.8351, 77.1295, '', '', 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_data`
--
ALTER TABLE `user_data`
  ADD CONSTRAINT `user_data_ibfk_1` FOREIGN KEY (`mobile_no`) REFERENCES `user_admn` (`mobile_no`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

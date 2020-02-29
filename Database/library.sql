-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 29, 2020 at 05:28 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `ID` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`ID`) VALUES
('YK');

-- --------------------------------------------------------

--
-- Table structure for table `bookdata`
--

CREATE TABLE `bookdata` (
  `title` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `access_no` varchar(50) NOT NULL,
  `call_no` varchar(50) NOT NULL,
  `publication` varchar(100) NOT NULL,
  `cost` bigint(200) NOT NULL,
  `edition` varchar(100) NOT NULL,
  `availability` varchar(100) NOT NULL DEFAULT 'available',
  `purchase_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookdata`
--

INSERT INTO `bookdata` (`title`, `author`, `access_no`, `call_no`, `publication`, `cost`, `edition`, `availability`, `purchase_date`) VALUES
('Operating System Principles', 'Galvin', 'CA001', 'C1', 'Wiley', 525, 'Seventh edition', 'available', '2020-02-29'),
('Database System Concepts', 'Abraham Silberschatz', 'CA002', 'C2', 'McGraw Hill Education', 730, 'Sixth edition', 'available', '2020-02-29');

-- --------------------------------------------------------

--
-- Table structure for table `bookloss`
--

CREATE TABLE `bookloss` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `access_no` varchar(100) NOT NULL,
  `lost_by_id` varchar(100) NOT NULL,
  `lost_by_name` varchar(100) NOT NULL,
  `replaced` varchar(20) NOT NULL,
  `replace_with_book` varchar(100) NOT NULL DEFAULT 'not replaced',
  `replaced_book_access` varchar(100) NOT NULL DEFAULT 'not replaced',
  `fine` varchar(100) NOT NULL DEFAULT 'no fine'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `call_no` varchar(100) NOT NULL,
  `access_no` varchar(100) NOT NULL,
  `issued_to_id` varchar(100) DEFAULT NULL,
  `issued_to_name` varchar(100) DEFAULT NULL,
  `type` varchar(100) NOT NULL,
  `issue_date` date DEFAULT NULL,
  `returned_on` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `scholardata`
--

CREATE TABLE `scholardata` (
  `id` int(11) NOT NULL,
  `scholar_id` text NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `book_limit` int(10) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `scholardata`
--

INSERT INTO `scholardata` (`id`, `scholar_id`, `name`, `password`, `email`, `book_limit`) VALUES
(2, 'MY', 'Yogesh Kumar', '123456', 'ykmujawdiya@gmail.com', 2);

-- --------------------------------------------------------

--
-- Table structure for table `staffdata`
--

CREATE TABLE `staffdata` (
  `id` int(11) NOT NULL,
  `staff_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staffdata`
--

INSERT INTO `staffdata` (`id`, `staff_id`, `name`, `password`, `email`) VALUES
(1, 'YK', 'Yogesh', '123456', 'ykmujawdiya@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `studentdata`
--

CREATE TABLE `studentdata` (
  `id` int(11) NOT NULL,
  `roll_no` text NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `book_limit` int(10) NOT NULL DEFAULT 2,
  `course` varchar(100) NOT NULL,
  `year` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `studentdata`
--

INSERT INTO `studentdata` (`id`, `roll_no`, `name`, `password`, `email`, `book_limit`, `course`, `year`) VALUES
(1, '205118090', 'Yogesh Mujawdiya', '123456', 'ykmujawdiya@gmail.com', 2, 'MCA', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `bookdata`
--
ALTER TABLE `bookdata`
  ADD PRIMARY KEY (`access_no`);

--
-- Indexes for table `bookloss`
--
ALTER TABLE `bookloss`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `scholardata`
--
ALTER TABLE `scholardata`
  ADD PRIMARY KEY (`scholar_id`(100)),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `staffdata`
--
ALTER TABLE `staffdata`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `studentdata`
--
ALTER TABLE `studentdata`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookloss`
--
ALTER TABLE `bookloss`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `scholardata`
--
ALTER TABLE `scholardata`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `staffdata`
--
ALTER TABLE `staffdata`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `studentdata`
--
ALTER TABLE `studentdata`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

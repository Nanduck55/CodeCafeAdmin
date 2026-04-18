-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 29, 2026 at 06:47 PM
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
-- Database: `codecafe_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `menuitems`
--

CREATE TABLE `menuitems` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `price` double DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `order_number` varchar(10) DEFAULT NULL,
  `order_type` varchar(20) DEFAULT NULL,
  `total_items` int(11) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `order_number`, `order_type`, `total_items`, `total_price`, `status`, `created_at`) VALUES
(1, '00001', ' Dine-In', 2, 208, 'Completed', '2026-03-25 15:20:15'),
(2, '00002', 'Take-Out', 1, 135, 'Completed', '2026-03-25 16:01:19'),
(3, '00003', 'Take-Out', 3, 317, 'Completed', '2026-03-25 16:22:56'),
(4, '00004', 'Take-Out', 3, 337, 'Completed', '2026-03-25 16:40:20'),
(5, '00005', 'Take-Out', 3, 317, 'Completed', '2026-03-25 16:55:38'),
(6, '00006', ' Dine-In', 3, 317, 'Completed', '2026-03-25 17:10:10'),
(7, '00007', 'Take-Out', 2, 208, 'Completed', '2026-03-25 17:13:55'),
(8, '00008', 'Take-Out', 4, 561, 'Completed', '2026-03-25 17:22:38'),
(9, '00009', ' Dine-In', 3, 327, 'Completed', '2026-03-25 17:41:27'),
(10, '00010', ' Dine-In', 2, 325, 'PENDING', '2026-03-25 17:42:03'),
(11, '00011', ' Dine-In', 3, 413, 'PENDING', '2026-03-25 17:43:12'),
(12, '00012', 'Take-Out', 3, 317, 'PENDING', '2026-03-27 18:15:47');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `item_name` varchar(100) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `addons` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `order_id`, `item_name`, `quantity`, `price`, `addons`) VALUES
(1, 8, 'House Special', 1, 129, ''),
(2, 8, 'Hazelnut Mocha', 1, 174, 'Add-ons: 1 Espresso Shot, 1 Syrup'),
(3, 8, 'Caramel Sealalt', 1, 129, ''),
(4, 8, 'Oreo Cheesecake', 1, 129, ''),
(5, 9, 'Dark Mocha', 1, 119, ''),
(6, 9, 'Americano', 1, 99, ''),
(7, 9, 'Matcha Latte', 1, 109, ''),
(8, 10, 'Oreo Cream', 1, 135, ''),
(9, 10, 'Biscoff Cream', 1, 190, 'Add-ons: 1 Espresso Shot, 1 Whipped Cream'),
(10, 11, 'Dark Mocha', 1, 119, ''),
(11, 11, 'Strawberry Yogurt', 1, 195, 'Add-ons: 1 Espresso Shot, 1 Cheesecake'),
(12, 11, 'Americano', 1, 99, ''),
(13, 12, 'Cafe Latte', 1, 109, ''),
(14, 12, 'Hazelnut', 1, 109, ''),
(15, 12, 'Americano', 1, 99, '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menuitems`
--
ALTER TABLE `menuitems`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `menuitems`
--
ALTER TABLE `menuitems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 27, 2021 at 06:01 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hospital`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrator`
--

CREATE TABLE `administrator` (
  `administrator_id` int(10) UNSIGNED NOT NULL,
  `Ime_Prezime` varchar(100) NOT NULL,
  `JMBG` varchar(14) NOT NULL,
  `id_korisnika` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `administrator`
--

INSERT INTO `administrator` (`administrator_id`, `Ime_Prezime`, `JMBG`, `id_korisnika`) VALUES
(1, 'Vinko-Tino Zlopaša', '1629/RR', 1),
(2, 'Jure Bakula', 'brojIndex', 2),
(3, 'Andro Raspudić', 'brojIndex', 3);

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `korisnik_id` int(10) UNSIGNED NOT NULL,
  `Korisnicko_ime` varchar(100) NOT NULL,
  `Lozinka` varchar(256) NOT NULL,
  `Uloga` enum('Lijecnik','Admin') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`korisnik_id`, `Korisnicko_ime`, `Lozinka`, `Uloga`) VALUES
(1, 'VTZ99', '123456', 'Admin'),
(2, 'Jure Bakula', 'Bakula123', 'Admin'),
(3, 'Andro Raspudić', 'Raspudić123', 'Admin'),
(4, 'MirkoM', 'Mirko123', 'Lijecnik'),
(5, 'AnteA', 'Ante123', 'Lijecnik'),
(12, 'MM667', '2326789', 'Lijecnik');

-- --------------------------------------------------------

--
-- Table structure for table `liječnik`
--

CREATE TABLE `liječnik` (
  `lijecnik_id` int(10) UNSIGNED NOT NULL,
  `Ime_Prezime` varchar(100) NOT NULL,
  `JMBG` varchar(14) DEFAULT NULL,
  `Opis` text DEFAULT NULL,
  `korisnik_id` int(10) UNSIGNED DEFAULT NULL,
  `odjel_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `liječnik`
--

INSERT INTO `liječnik` (`lijecnik_id`, `Ime_Prezime`, `JMBG`, `Opis`, `korisnik_id`, `odjel_id`) VALUES
(3, 'Mirko Mirkić', 'JMBG123', NULL, 4, 1),
(4, 'Ante Antić', 'JMBG987', 'Ante najjači doktur', 5, 2),
(9, 'Makić Mirga', NULL, '', 12, 9);

-- --------------------------------------------------------

--
-- Table structure for table `odjel`
--

CREATE TABLE `odjel` (
  `odjel_id` int(10) UNSIGNED NOT NULL,
  `Naziv` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `odjel`
--

INSERT INTO `odjel` (`odjel_id`, `Naziv`) VALUES
(1, 'Pedijatrija'),
(2, 'Hematologija'),
(9, 'Kirurgija');

-- --------------------------------------------------------

--
-- Table structure for table `pacijent`
--

CREATE TABLE `pacijent` (
  `pacijent_id` int(10) UNSIGNED NOT NULL,
  `Ime_Prezime` varchar(100) NOT NULL,
  `JMBG` varchar(14) NOT NULL,
  `Zdravstveno_osiguranje` enum('DA','NE') DEFAULT NULL,
  `Covid_cjepivo` enum('DA','NE') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pregled`
--

CREATE TABLE `pregled` (
  `pregled_id` int(10) UNSIGNED NOT NULL,
  `id_lijecnika` int(10) UNSIGNED NOT NULL,
  `id_pacijenta` int(10) UNSIGNED NOT NULL,
  `Opis` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrator`
--
ALTER TABLE `administrator`
  ADD PRIMARY KEY (`administrator_id`),
  ADD KEY `id_korisnika` (`id_korisnika`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`korisnik_id`),
  ADD UNIQUE KEY `Korisnicko_ime` (`Korisnicko_ime`);

--
-- Indexes for table `liječnik`
--
ALTER TABLE `liječnik`
  ADD PRIMARY KEY (`lijecnik_id`),
  ADD KEY `korisnik_id` (`korisnik_id`),
  ADD KEY `odjel_id` (`odjel_id`);

--
-- Indexes for table `odjel`
--
ALTER TABLE `odjel`
  ADD PRIMARY KEY (`odjel_id`);

--
-- Indexes for table `pacijent`
--
ALTER TABLE `pacijent`
  ADD PRIMARY KEY (`pacijent_id`);

--
-- Indexes for table `pregled`
--
ALTER TABLE `pregled`
  ADD PRIMARY KEY (`pregled_id`),
  ADD KEY `id_lijecnika` (`id_lijecnika`),
  ADD KEY `id_pacijenta` (`id_pacijenta`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `administrator`
--
ALTER TABLE `administrator`
  MODIFY `administrator_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `korisnik_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `liječnik`
--
ALTER TABLE `liječnik`
  MODIFY `lijecnik_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `odjel`
--
ALTER TABLE `odjel`
  MODIFY `odjel_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `pacijent`
--
ALTER TABLE `pacijent`
  MODIFY `pacijent_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pregled`
--
ALTER TABLE `pregled`
  MODIFY `pregled_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `administrator`
--
ALTER TABLE `administrator`
  ADD CONSTRAINT `administrator_ibfk_1` FOREIGN KEY (`id_korisnika`) REFERENCES `korisnik` (`korisnik_id`);

--
-- Constraints for table `liječnik`
--
ALTER TABLE `liječnik`
  ADD CONSTRAINT `liječnik_ibfk_1` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `liječnik_ibfk_2` FOREIGN KEY (`odjel_id`) REFERENCES `odjel` (`odjel_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pregled`
--
ALTER TABLE `pregled`
  ADD CONSTRAINT `pregled_ibfk_1` FOREIGN KEY (`id_lijecnika`) REFERENCES `liječnik` (`lijecnik_id`),
  ADD CONSTRAINT `pregled_ibfk_2` FOREIGN KEY (`id_pacijenta`) REFERENCES `pacijent` (`pacijent_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
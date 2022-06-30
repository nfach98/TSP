-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Bulan Mei 2022 pada 16.32
-- Versi server: 10.4.22-MariaDB
-- Versi PHP: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `skripsi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kurir`
--

CREATE TABLE `kurir` (
  `id` char(5) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `notelp` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kurir`
--

INSERT INTO `kurir` (`id`, `name`, `address`, `notelp`) VALUES
('K001', 'Adit Putra Wardana', 'Jl. Pumpungan Timur No 2A', '089089767878'),
('K002', 'Yosua Pratama', 'Jl. Mawar No 23', '082188822212'),
('k003', 'Khabib Putra', 'Jl. Jakarta No 10', '081336548900'),
('k004', 'Muhammad Aji Dwo', 'Jl. Sulawesi No 4', '081336541230');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pengiriman`
--

CREATE TABLE `pengiriman` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` varchar(80) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `pengiriman`
--

INSERT INTO `pengiriman` (`id`, `nama`, `alamat`, `latitude`, `longitude`) VALUES
(1, 'Putri', 'Jl. Suko Semolo VIII Bok F No. 23, \nSukolilo, Kota SBY, Jawa Timur 60119, \nIndon', -7.30285, 112.7808),
(2, 'Dimas ', 'Jl. Lb. Indah Jaya I No. 27, Tambaksari, \r\nKota SBY, Jawa Timur 60134, Indonesia', -7.24225, 112.783),
(3, 'Putra', 'Jalan Mulyosari Prima, Mulyorejo, Kota \r\nSurabaya, Jawa Timur 60116\r\n', -7.26011, 112.79968),
(4, 'Angelia', 'Jl. Manyar Kertoadi, Klampis Ngasem, \r\nKec. Sukolilo , Kota SBY, Jawa Timur \r\n60', -7.28217, 112.77837),
(5, 'Putra Dwi Jaya', 'Jl. Klampis Indah I No. 6, Sukolilo, Kota \r\nSBY, Jawa Timur 60117, Indonesia', -7.2945, 112.7753),
(6, 'Muhammad Doni', 'Jl. Lagguna Kjw Putih, Mulyorejo, Kota \r\nSBY, Jawa Timur 60112, Indonesia', -7.2769, 112.811),
(7, 'Dwi Octa', 'Jl. Klimbungan IV No. 5, Genteng, Kota \r\nSBY, Jawa Timur 60274, Indonesia', -7.24862, 112.746),
(8, 'Citra Ayu', 'Jl. Kedung Mangu Sel. V No. 21, \r\nKejneran, Kota SBY, Jawa Timur 60128, \r\nIndone', -7.22742, 112.76),
(13, 'abissd', 'xfgbnhxss', -11.999, 222.9);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `Id` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `stts` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`Id`, `Name`, `Username`, `Password`, `stts`) VALUES
(1, 'ani', 'ani', '123', ''),
(2, 'abi', 'abi', 'abi', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `kurir`
--
ALTER TABLE `kurir`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `pengiriman`
--
ALTER TABLE `pengiriman`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `pengiriman`
--
ALTER TABLE `pengiriman`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

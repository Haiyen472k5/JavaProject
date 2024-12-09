-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3307
-- Thời gian đã tạo: Th12 09, 2024 lúc 11:05 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `managebook`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `book`
--

CREATE TABLE `book` (
  `id` varchar(100) NOT NULL,
  `title` varchar(200) NOT NULL,
  `author` varchar(200) NOT NULL,
  `genre` varchar(200) NOT NULL,
  `quantity` int(11) NOT NULL,
  `image` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `book`
--

INSERT INTO `book` (`id`, `title`, `author`, `genre`, `quantity`, `image`) VALUES
('-qXyDwAAQBAJ', 'Phân tán đối tượng trong Java bằng RMI', '[\"Hoang Tran\"]', 'Computers', 5, 'http://books.google.com/books/content?id=-qXyDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),
('HAMBEQAAQBAJ', 'Lập trình Hướng đối tượng với Java', '[\"Hoàng Trần\"]', 'Computers', 5, 'http://books.google.com/books/content?id=HAMBEQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),
('2thiAwAAQBAJ', 'The Chronicles of Conan Volume 27: Sands Upon the Earth', '[\"James Owsley\"]', 'Comics & Graphic Novels', 5, 'http://books.google.com/books/content?id=2thiAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),
('MZQV7x1pMgoC', 'Conan Doyle', '[\"Douglas Kerr\"]', 'Literary Criticism', 5, 'http://books.google.com/books/content?id=MZQV7x1pMgoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),
('8dBDAAAAQBAJ', 'New Results in Dependability and Computer Systems', '[\"Wojciech Zamojski\",\"Jacek Mazurkiewicz\",\"Jarosław Sugier\",\"Tomasz Walkowiak\",\"Janusz Kacprzyk\"]', 'Technology & Engineering', 5, 'http://books.google.com/books/content?id=8dBDAAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),
('2BYSiH-zSHMC', 'The Lady\'s Slipper', '[\"Deborah Swift\"]', 'Fiction', 10, 'http://books.google.com/books/content?id=2BYSiH-zSHMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

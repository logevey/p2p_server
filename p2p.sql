-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2014-12-15 20:16:44
-- 服务器版本: 5.5.38-0ubuntu0.14.04.1
-- PHP 版本: 5.5.9-1ubuntu4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `p2p`
--

-- --------------------------------------------------------

--
-- 表的结构 `metadata`
--

CREATE TABLE IF NOT EXISTS `metadata` (
  `filename` varchar(100) NOT NULL COMMENT '文件名',
  `filepath` varchar(200) DEFAULT NULL,
  `filesize` bigint(11) NOT NULL COMMENT '文件大小',
  `username` varchar(40) NOT NULL COMMENT '上传用户名',
  PRIMARY KEY (`filename`,`username`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='元数据表';

--
-- 转存表中的数据 `metadata`
--

INSERT INTO `metadata` (`filename`, `filepath`, `filesize`, `username`) VALUES
('6v.o', '/home/wait/Code/c/6v.o', 4, 'd'),
('?? - ???.mp3', '/media/wait/Programs/Music/?? - ???.mp3', 3553, 'd'),
('apache-tomcat-6.0.41.tar.gz', '/home/wait/Downloads/apache-tomcat-6.0.41.tar.gz', 7007, 'd'),
('c.txt', 'C:c.txt', 22, 'nmb'),
('kpinno.exe', 'H:??????kpzskpinno.exe', 820, 'nmb'),
('tsymq.mp3', '/home/wait/Desktop/tsymq.mp3', 11356, 'd'),
('[??-11???][????????Code_Geass_Collection][BDRIP][???].rar', '/home/wait/Videos/[??-11???][????????Code_Geass_Collection][BDRIP][X264_AAC][720P]/[??-11???][????????Code_Geass_Collection][BDRIP][???].rar', 38598, 'd');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(8) DEFAULT NULL COMMENT '密码',
  `userip` varchar(20) DEFAULT NULL,
  `userport` int(11) NOT NULL,
  `online` int(1) NOT NULL DEFAULT '0' COMMENT '用户是否在线',
  PRIMARY KEY (`username`),
  UNIQUE KEY `name` (`username`),
  KEY `userip` (`userip`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='用户表';

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`username`, `password`, `userip`, `userport`, `online`) VALUES
('d', 'd', '219.245.100.48', 2057, 0),
('dd', 'dd', '219.245.100.136', 2, 0),
('ds', 'ds', '127.0.0.1', 6666, 0),
('f', 'f', '127.0.0.1', 6666, 0),
('fd', 'fd', '127.0.0.1', 6666, 0),
('fdf', 'fdf', '127.0.0.1', 6666, 0),
('lee', 'lee', '219.245.100.61', 2056, 0),
('liwei', 'liwei', '219.245.100.61', 2056, 0),
('nmb', '123', '219.245.100.136', 2049, 0),
('wait', 'wait', '219.245.100.61', 2057, 0);

--
-- 限制导出的表
--

--
-- 限制表 `metadata`
--
ALTER TABLE `metadata`
  ADD CONSTRAINT `metadata_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

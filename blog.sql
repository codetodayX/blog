/*
SQLyog Ultimate v12.14 (64 bit)
MySQL - 8.0.21 : Database - blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `blog`;

/*Table structure for table `t_blog` */

DROP TABLE IF EXISTS `t_blog`;

CREATE TABLE `t_blog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `views` bigint NOT NULL,
  `firstPicture` varchar(255) NOT NULL,
  `info` text NOT NULL,
  `flag` varchar(50) NOT NULL,
  `appreciation` int NOT NULL DEFAULT '0',
  `shareStatement` int NOT NULL DEFAULT '0',
  `commentEnabled` int NOT NULL DEFAULT '0',
  `published` int NOT NULL DEFAULT '0',
  `u_id` bigint DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `recommend` int NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_blog_type` (`type_id`),
  KEY `fk_blog_user` (`u_id`),
  CONSTRAINT `fk_blog_type` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`),
  CONSTRAINT `fk_blog_user` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `t_blog` */

insert  into `t_blog`(`id`,`createTime`,`updateTime`,`views`,`firstPicture`,`info`,`flag`,`appreciation`,`shareStatement`,`commentEnabled`,`published`,`u_id`,`type_id`,`recommend`,`title`,`description`) values 
(21,'2021-06-09 21:06:22','2021-06-09 21:06:22',162,'https://z3.ax1x.com/2021/06/09/2yp5kt.jpg','# 前言\r\n>最近花了两个星期的时间参照B站李仁密老师的视频搭建了一个基于SpringBoot + SemanticUI的个人博客网站，持久层由JPA改为了自己较为熟悉Mybatispus,\r\n\r\n','原创',0,0,0,1,1,20,1,'个人博客网站总结','一个基于SpringBoot + Mybatis-Plus + Thymeaf + SemanticUI的个人博客网站');

/*Table structure for table `t_blog_tag` */

DROP TABLE IF EXISTS `t_blog_tag`;

CREATE TABLE `t_blog_tag` (
  `tag_id` bigint NOT NULL,
  `blog_id` bigint NOT NULL,
  KEY `fk_blog` (`blog_id`),
  KEY `fk_tag` (`tag_id`),
  CONSTRAINT `fk_blog` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`),
  CONSTRAINT `fk_tag` FOREIGN KEY (`tag_id`) REFERENCES `t_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_blog_tag` */

insert  into `t_blog_tag`(`tag_id`,`blog_id`) values 
(11,21),
(6,21),
(12,21);

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `createTime` datetime NOT NULL,
  `info` text NOT NULL,
  `headPicture` varchar(255) NOT NULL,
  `blog_id` bigint NOT NULL,
  `parentId` bigint DEFAULT NULL,
  `type` varchar(10) NOT NULL COMMENT '判断评论用户是游客还是管理员',
  PRIMARY KEY (`id`),
  KEY `fk_blog_comment` (`blog_id`),
  CONSTRAINT `fk_blog_comment` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `t_comment` */

insert  into `t_comment`(`id`,`nickName`,`email`,`createTime`,`info`,`headPicture`,`blog_id`,`parentId`,`type`) values 
(36,'小白','21312@qq.com','2021-06-10 13:06:41','测试1','/images/avatar.png',21,NULL,'访客'),
(37,'小黑','21312@qq.com','2021-06-10 13:06:49','测试2','/images/avatar.png',21,36,'访客'),
(38,'小红','21312@qq.com','2021-06-10 13:07:00','测试3','/images/avatar.png',21,37,'访客'),
(39,'小军','21312@qq.com','2021-06-10 13:07:13','测试3','/images/avatar.png',21,NULL,'访客');

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `createTime` datetime NOT NULL,
  `info` text NOT NULL,
  `headPicture` varchar(255) NOT NULL,
  `parentId` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `t_message` */

insert  into `t_message`(`id`,`nickName`,`email`,`type`,`createTime`,`info`,`headPicture`,`parentId`) values 
(23,'小白','252@qq.com','访客','2021-06-08 20:09:35','good','/images/cute.jpg',NULL);

/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `t_tag` */

insert  into `t_tag`(`id`,`name`) values 
(1,'Java'),
(2,'Vue'),
(3,'JVM'),
(4,'Spring'),
(5,'Redis'),
(6,'Mybatis-plus'),
(11,'SpringBoot'),
(12,'Thymeleaf'),
(13,'SemanticUI');

/*Table structure for table `t_type` */

DROP TABLE IF EXISTS `t_type`;

CREATE TABLE `t_type` (
  `name` varchar(255) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `t_type` */

insert  into `t_type`(`name`,`id`) values 
('项目总结',20);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickName` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `headPicture` varchar(255) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `type` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`nickName`,`username`,`password`,`headPicture`,`createTime`,`updateTime`,`type`,`email`) values 
(1,'管理员','lisi','e10adc3949ba59abbe56e057f20f883e','/images/cute.jpg','2021-06-02 00:00:00','2021-06-17 00:00:00','拉拉','dfwef');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

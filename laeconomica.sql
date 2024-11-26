-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-11-2024 a las 18:13:19
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `laeconomica`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `clave` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido_paterno` varchar(50) NOT NULL,
  `apellido_materno` varchar(50) DEFAULT NULL,
  `edad` int(11) NOT NULL,
  `domicilio` varchar(100) NOT NULL,
  `telefono` int(14) NOT NULL,
  `jornada` varchar(50) NOT NULL,
  `sucursal_id` int(11) NOT NULL,
  `sueldo` float NOT NULL,
  `jornada_laboral` date NOT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`clave`, `nombre`, `apellido_paterno`, `apellido_materno`, `edad`, `domicilio`, `telefono`, `jornada`, `sucursal_id`, `sueldo`, `jornada_laboral`, `password`) VALUES
(1, 'ejemplo actualizado', 'ejemploactualizado', 'ejemplo', 33, 'ejemplo', 951453617, 'ejemplo', 1, 666, '2024-11-08', NULL),
(2, 'Juan pedro ', 'pedro ', 'López', 28, 'Av. Siempre Viva 123', 951111111, 'Completa', 1, 999, '2024-11-10', NULL),
(3, 'Ana', 'García', 'Santos', 35, 'Calle Primavera 45', 951222222, 'Parcial', 1, 35, '2024-11-11', NULL),
(4, 'Luis felipe pge', 'Hernández', 'Martínez', 40, 'Calle Luna 7', 951333333, 'Completa', 1, 555, '2024-11-12', NULL),
(5, 'Maríaeqwe eqweqweqweqw', 'eqweqweqweqw', 'Gómezddd', 30, 'Calle Sol 8', 951444444, 'Parcial', 1, 555, '2024-11-13', NULL),
(6, 'Carlos', 'Fernández', 'Díaz', 32, 'Calle Estrella 9', 951555555, 'Completa', 1, 47.3, '2024-11-14', NULL),
(7, 'Laura', 'Jiménez', 'Torres', 29, 'Calle Viento 10', 951666666, 'Parcial', 1, 39, '2024-11-15', NULL),
(8, 'Miguel', 'Ruiz', 'Castro', 36, 'Calle Nube 11', 951777777, 'Completa', 1, 52.8, '2024-11-16', NULL),
(9, 'Sara', 'López', 'Morales', 27, 'Calle Lluvia 12', 951888888, 'Parcial', 1, 36.4, '2024-11-17', NULL),
(10, 'Fernando', 'González', 'Peña', 41, 'Calle Fuego 13', 951999999, 'Completa', 1, 58, '2024-11-18', NULL),
(11, 'Sofía', 'Martínez', 'Ortiz', 25, 'Calle Rayo 14', 951000000, 'Parcial', 1, 33.7, '2024-11-19', NULL),
(12, 'keila', 'keila', 'keila', 44, 'keila', 951453617, 'keila', 2, 105, '2024-11-19', NULL),
(13, 'pedro picapedras', 'picapedras', 'picapedras2', 55, '9514588', 55555, '5', 1, 5, '2024-11-14', NULL),
(14, 'ejemplo12', 'ejemplo12', 'ejemplo12', 4, 'ejemplo12', 54545454, 'ejemplo12', 1, 4, '2024-11-20', NULL),
(15, 'ejemplo12', 'ejemplo12', 'ejemplo12', 55, 'ejemplo12', 95145354, 'ejemplo12', 1, 55, '2024-11-20', NULL),
(16, 'ejemplo55', 'ejemplo55', 'ejemplo55', 55, 'ejemplo55', 951453617, 'ejemplo55', 1, 44, '2024-11-19', NULL),
(17, 'ejemplo55', 'ejemplo55', 'ejemplo55', 55, 'ejemplo55', 54545454, 'ejemplo55', 1, 55, '2024-11-21', NULL),
(21, 'ejemplito', 'ejemplito', 'ejemplito', 33, 'ejemplito', 951453617, 'ejemplito', 1, 299, '2024-11-20', NULL),
(22, 'eeee', 'eeee', 'eeee', 88, 'eeee', 951453617, 'eeee', 1, 999, '2024-11-19', NULL),
(23, 'ed', 'ed', 'ed', 55, 'ed', 951453617, 'ed', 1, 666, '2024-11-20', NULL),
(25, 'ejemplo', 'ejemplo', 'ejemplo', 33, 'ejemplo', 951453617, 'ejemplo', 1, 444, '2024-11-08', NULL),
(26, 'fds', 'ffds', 'fds', 44, 'fdsfs', 951453617, 'dfss', 1, 444, '2024-11-21', NULL),
(27, 'ejemplo actualizado', 'ejemplo', 'ejemplo', 55, 'ejemplo', 951453617, 'ejemplo', 1, 777, '2024-11-08', NULL),
(28, 'Juan pedro perez', 'Pérez', 'López', 28, 'Av. Siempre Viva 123', 951111111, 'Completa', 1, 444, '2024-11-10', NULL),
(29, 'ejemplo actualizado', 'ejemplo', 'ejemplo', 33, 'ejemplo', 951453617, 'ejemplo', 1, 99999, '2024-11-08', NULL),
(30, 'weq', 'eqw', 'eqw', 33, 'ads', 951453617, 'ed', 1, 444, '2024-11-26', NULL),
(31, 'ejemplo actualizado', 'actualizado', 'ejemplo', 33, 'ejemplo', 951453617, 'ejemplo', 1, 666, '2024-11-08', NULL),
(32, 'ee', 'eee', 'ee', 18, 'ede', 951453617, 'edd', 1, 208, '2024-11-23', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado_rol`
--

CREATE TABLE `empleado_rol` (
  `empleado_clave` int(11) NOT NULL,
  `rol_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`id`, `nombre`) VALUES
(1, 'ejemplo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario_producto`
--

CREATE TABLE `inventario_producto` (
  `Inventarioid` int(11) NOT NULL,
  `Productoid` int(11) NOT NULL,
  `producto_disponible` int(11) NOT NULL,
  `cantida_minima` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajeria`
--

CREATE TABLE `mensajeria` (
  `id` int(11) NOT NULL,
  `contenido` varchar(50) NOT NULL,
  `fecha_envio` date NOT NULL,
  `estado_mensaje` varchar(30) NOT NULL,
  `remitente_id` int(11) NOT NULL,
  `destinatario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos`
--

CREATE TABLE `pagos` (
  `id` int(11) NOT NULL,
  `empleado_clave` int(11) NOT NULL,
  `fecha_pago` date NOT NULL,
  `total_pagado` double NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pagos`
--

INSERT INTO `pagos` (`id`, `empleado_clave`, `fecha_pago`, `total_pagado`, `fecha_inicio`, `fecha_fin`) VALUES
(1, 3, '2024-11-21', 8602.5, '2024-11-07', '2024-11-06'),
(2, 3, '2024-11-21', 8602.5, '2024-11-07', '2024-11-06'),
(3, 2, '2024-11-21', 8602.5, '2024-11-07', '2024-11-06'),
(4, 6, '2024-11-21', 733.1499881744385, '2024-11-07', '2024-11-06'),
(5, 2, '2024-11-21', 15484.5, '2024-11-07', '2024-11-06'),
(6, 2, '2024-11-21', 15484.5, '2024-11-07', '2024-11-06'),
(7, 4, '2024-11-21', 9157.5, '2024-11-07', '2024-11-06'),
(8, 2, '2024-11-22', 7992, '2024-11-07', '2024-11-07'),
(9, 2, '2024-11-24', 16983, '2024-11-23', '2024-11-15'),
(10, 2, '2024-11-24', 16983, '2024-11-23', '2024-11-15'),
(11, 1, '2024-11-24', 6660, '2024-11-24', '2024-11-24'),
(12, 1, '2024-11-24', 6660, '2024-11-24', '2024-11-24');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id` int(11) NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `estado` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido_producto`
--

CREATE TABLE `pedido_producto` (
  `Pedidoid` int(11) NOT NULL,
  `Productoid` int(11) NOT NULL,
  `cantidad_pedido` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` float NOT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `fecha_caducidad` date DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `proveedor` varchar(100) NOT NULL,
  `promocion` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro_horas`
--

CREATE TABLE `registro_horas` (
  `id` int(11) NOT NULL,
  `empleado_clave` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `horas_trabajadas` float NOT NULL,
  `horas_extras` float DEFAULT 0,
  `es_dia_festivo` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registro_horas`
--

INSERT INTO `registro_horas` (`id`, `empleado_clave`, `fecha`, `horas_trabajadas`, `horas_extras`, `es_dia_festivo`) VALUES
(1, 2, '2024-11-01', 8, 2, 0),
(2, 2, '2024-11-02', 7, 1, 0),
(3, 2, '2024-11-15', 9, 3, 0),
(4, 2, '2024-11-04', 6, 0, 1),
(5, 2, '2024-11-05', 8, 0, 0),
(6, 2, '2024-11-06', 7.5, 1.5, 0),
(7, 2, '2024-11-07', 8, 0, 0),
(8, 3, '2024-11-01', 6, 0, 0),
(9, 3, '2024-11-02', 7, 2, 0),
(10, 3, '2024-11-03', 8, 0, 0),
(11, 3, '2024-11-04', 8, 1, 1),
(12, 3, '2024-11-05', 7, 0, 0),
(13, 3, '2024-11-06', 6.5, 1.5, 0),
(14, 3, '2024-11-07', 7, 0, 0),
(15, 4, '2024-11-01', 9, 1, 0),
(16, 4, '2024-11-02', 8, 2, 0),
(17, 4, '2024-11-03', 7, 0, 0),
(18, 4, '2024-11-04', 8, 1, 1),
(19, 4, '2024-11-05', 8, 0, 0),
(20, 4, '2024-11-06', 7.5, 0, 0),
(21, 4, '2024-11-07', 9, 2, 0),
(22, 5, '2024-11-01', 6, 0, 0),
(23, 5, '2024-11-02', 7.5, 1.5, 0),
(24, 5, '2024-11-03', 8, 0, 0),
(25, 5, '2024-11-04', 9, 0, 1),
(26, 5, '2024-11-05', 7, 1, 0),
(27, 5, '2024-11-06', 6, 0, 0),
(28, 5, '2024-11-07', 8, 2, 0),
(29, 6, '2024-11-01', 8, 1, 0),
(30, 6, '2024-11-02', 8, 0, 0),
(31, 6, '2024-11-03', 7, 2, 0),
(32, 6, '2024-11-04', 8, 0, 1),
(33, 6, '2024-11-05', 9, 1, 0),
(34, 6, '2024-11-06', 7.5, 1, 0),
(35, 6, '2024-11-07', 8, 0, 0),
(36, 7, '2024-11-01', 7.5, 1.5, 0),
(37, 7, '2024-11-02', 6, 0, 0),
(38, 7, '2024-11-03', 8, 0, 0),
(39, 7, '2024-11-04', 7, 2, 1),
(40, 7, '2024-11-05', 8, 1, 0),
(41, 7, '2024-11-06', 8.5, 0.5, 0),
(42, 7, '2024-11-07', 9, 0, 0),
(43, 8, '2024-11-01', 7, 0, 0),
(44, 8, '2024-11-02', 7, 0, 0),
(45, 8, '2024-11-03', 8, 1, 0),
(46, 8, '2024-11-04', 9, 1, 1),
(47, 8, '2024-11-05', 7.5, 0, 0),
(48, 8, '2024-11-06', 7, 0, 0),
(49, 8, '2024-11-07', 8.5, 0.5, 0),
(50, 9, '2024-11-01', 8, 0, 0),
(51, 9, '2024-11-02', 7.5, 1.5, 0),
(52, 9, '2024-11-03', 9, 2, 0),
(53, 9, '2024-11-04', 6.5, 0.5, 1),
(54, 9, '2024-11-05', 8, 0, 0),
(55, 9, '2024-11-06', 7, 0, 0),
(56, 9, '2024-11-07', 8.5, 0.5, 0),
(57, 10, '2024-11-01', 7.5, 1.5, 0),
(58, 10, '2024-11-02', 6, 0, 0),
(59, 10, '2024-11-03', 8, 0, 0),
(60, 10, '2024-11-04', 7, 2, 1),
(61, 10, '2024-11-05', 8, 1, 0),
(62, 10, '2024-11-06', 8.5, 0.5, 0),
(63, 10, '2024-11-07', 9, 0, 0),
(64, 11, '2024-10-17', 22, 1, 0),
(65, 12, '2024-10-10', 55, 1, 0),
(66, 12, '2024-09-10', 4, 4, 0),
(67, 6, '2024-09-02', 555, 9, 0),
(68, 7, '2024-08-21', 99, 6, 0),
(69, 1, '2024-07-08', 78, 8, 0),
(70, 3, '2024-06-24', 78, 8, 0),
(71, 5, '2024-05-07', 88, 9, 0),
(72, 3, '2024-04-02', 9, 9, 0),
(73, 2, '2024-11-23', 8, 2, 0),
(74, 3, '2024-11-23', 7, 1, 0),
(75, 4, '2024-11-23', 9, 3, 0),
(76, 5, '2024-11-23', 6, 0, 1),
(77, 6, '2024-11-23', 8, 0, 0),
(78, 7, '2024-11-23', 7.5, 1.5, 0),
(79, 8, '2024-11-23', 8, 0, 0),
(80, 9, '2024-11-23', 6, 0, 0),
(81, 10, '2024-11-23', 7, 2, 0),
(82, 11, '2024-11-23', 8, 0, 0),
(83, 12, '2024-11-23', 8, 1, 1),
(84, 2, '2024-11-24', 5, 1, 0),
(85, 1, '2024-11-24', 5, 2, 0),
(86, 1, '2024-11-24', 5, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `nombre`, `descripcion`) VALUES
(1, 'ejemplo', 'ejemplo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `domicilio` varchar(50) NOT NULL,
  `empleados` int(11) NOT NULL,
  `inventario` int(11) NOT NULL,
  `ventas` int(11) NOT NULL,
  `Inventarioid` int(11) DEFAULT NULL,
  `Empleadoclave` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`id`, `descripcion`, `domicilio`, `empleados`, `inventario`, `ventas`, `Inventarioid`, `Empleadoclave`) VALUES
(0, '', '', 0, 0, 0, NULL, 1),
(1, 'ejemplo', 'ejemplo', 1, 1, 1, 1, 1),
(2, 'ejemplito', 'ejemplito', 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `id` int(11) NOT NULL,
  `empleado` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `descuento` float DEFAULT NULL,
  `sucursal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta_producto`
--

CREATE TABLE `venta_producto` (
  `Ventaid` int(11) NOT NULL,
  `Productoid` int(11) NOT NULL,
  `cantidad_vendida` int(11) NOT NULL,
  `precio_unitario` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`clave`),
  ADD KEY `sucursal_id` (`sucursal_id`);

--
-- Indices de la tabla `empleado_rol`
--
ALTER TABLE `empleado_rol`
  ADD PRIMARY KEY (`empleado_clave`,`rol_id`),
  ADD KEY `empleado_clave` (`empleado_clave`),
  ADD KEY `rol_id` (`rol_id`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `inventario_producto`
--
ALTER TABLE `inventario_producto`
  ADD PRIMARY KEY (`Inventarioid`,`Productoid`),
  ADD KEY `Productoid` (`Productoid`);

--
-- Indices de la tabla `mensajeria`
--
ALTER TABLE `mensajeria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `remitente_id` (`remitente_id`),
  ADD KEY `destinatario_id` (`destinatario_id`);

--
-- Indices de la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `empleado_clave` (`empleado_clave`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedido_producto`
--
ALTER TABLE `pedido_producto`
  ADD PRIMARY KEY (`Pedidoid`,`Productoid`),
  ADD KEY `Productoid` (`Productoid`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `registro_horas`
--
ALTER TABLE `registro_horas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `empleado_clave` (`empleado_clave`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Inventarioid` (`Inventarioid`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `empleado` (`empleado`),
  ADD KEY `sucursal` (`sucursal`);

--
-- Indices de la tabla `venta_producto`
--
ALTER TABLE `venta_producto`
  ADD PRIMARY KEY (`Ventaid`,`Productoid`),
  ADD KEY `Productoid` (`Productoid`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `clave` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `pagos`
--
ALTER TABLE `pagos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `registro_horas`
--
ALTER TABLE `registro_horas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`);

--
-- Filtros para la tabla `empleado_rol`
--
ALTER TABLE `empleado_rol`
  ADD CONSTRAINT `empleado_rol_ibfk_1` FOREIGN KEY (`empleado_clave`) REFERENCES `empleado` (`clave`),
  ADD CONSTRAINT `empleado_rol_ibfk_2` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`);

--
-- Filtros para la tabla `inventario_producto`
--
ALTER TABLE `inventario_producto`
  ADD CONSTRAINT `inventario_producto_ibfk_1` FOREIGN KEY (`Inventarioid`) REFERENCES `inventario` (`id`),
  ADD CONSTRAINT `inventario_producto_ibfk_2` FOREIGN KEY (`Productoid`) REFERENCES `producto` (`id`);

--
-- Filtros para la tabla `mensajeria`
--
ALTER TABLE `mensajeria`
  ADD CONSTRAINT `mensajeria_ibfk_1` FOREIGN KEY (`remitente_id`) REFERENCES `empleado` (`clave`),
  ADD CONSTRAINT `mensajeria_ibfk_2` FOREIGN KEY (`destinatario_id`) REFERENCES `empleado` (`clave`);

--
-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`empleado_clave`) REFERENCES `empleado` (`clave`);

--
-- Filtros para la tabla `pedido_producto`
--
ALTER TABLE `pedido_producto`
  ADD CONSTRAINT `pedido_producto_ibfk_1` FOREIGN KEY (`Pedidoid`) REFERENCES `pedido` (`id`),
  ADD CONSTRAINT `pedido_producto_ibfk_2` FOREIGN KEY (`Productoid`) REFERENCES `producto` (`id`);

--
-- Filtros para la tabla `registro_horas`
--
ALTER TABLE `registro_horas`
  ADD CONSTRAINT `registro_horas_ibfk_1` FOREIGN KEY (`empleado_clave`) REFERENCES `empleado` (`clave`);

--
-- Filtros para la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD CONSTRAINT `sucursal_ibfk_1` FOREIGN KEY (`Inventarioid`) REFERENCES `inventario` (`id`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`empleado`) REFERENCES `empleado` (`clave`),
  ADD CONSTRAINT `venta_ibfk_2` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`id`);

--
-- Filtros para la tabla `venta_producto`
--
ALTER TABLE `venta_producto`
  ADD CONSTRAINT `venta_producto_ibfk_1` FOREIGN KEY (`Ventaid`) REFERENCES `venta` (`id`),
  ADD CONSTRAINT `venta_producto_ibfk_2` FOREIGN KEY (`Productoid`) REFERENCES `producto` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
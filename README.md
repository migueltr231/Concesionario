# 🚗 Sistema de Gestión de Concesionarias de Vehículos

## 📌 Descripción del Proyecto

Este proyecto consiste en el análisis, diseño y desarrollo de un sistema de información orientado a la gestión de una empresa dedicada a la comercialización de vehículos mediante múltiples concesionarias ubicadas dentro de una misma ciudad.

El sistema centraliza la información relacionada con inventario, ventas, empleados, clientes y metas comerciales, con el objetivo de mejorar la consistencia de los datos, optimizar la toma de decisiones y facilitar el control operativo del negocio.

---

# 🎯 Objetivos

## Objetivo General

Desarrollar un sistema capaz de gestionar de manera eficiente las operaciones de una empresa de venta de vehículos, garantizando integridad, consistencia y disponibilidad de la información.

## Objetivos Específicos

- Centralizar la información de múltiples concesionarias.
- Gestionar el inventario de vehículos en tiempo real.
- Registrar y administrar las ventas realizadas.
- Gestionar la información de empleados y clientes.
- Asignar y monitorear metas comerciales.
- Generar información útil para reportes y análisis de rendimiento.

---

# 🧠 Problemática

Actualmente, cada concesionaria administra su información de manera independiente, lo que genera distintos problemas operativos como:

- Inconsistencias en el inventario de vehículos.
- Falta de actualización inmediata tras una venta.
- Dificultades para consolidar la información comercial.
- Problemas en el seguimiento del rendimiento del personal.
- Limitaciones para el análisis administrativo y financiero.

La ausencia de un sistema centralizado afecta la confiabilidad del inventario y dificulta la gestión eficiente del negocio.

---

# 🏗️ Alcance del Sistema

El sistema permitirá gestionar información relacionada con:

- Concesionarias
- Vehículos (modelo)
- Unidades de vehículos (instancias físicas)
- Clientes
- Empleados (gerentes y vendedores)
- Ventas
- Metas comerciales
- Inventario de vehículos

---

# 🧱 Arquitectura del Sistema

El proyecto sigue una arquitectura por capas:

- **UI (Interfaz de Usuario):** Encargada de la interacción con el usuario.
- **Controller (Facade):** Punto central de acceso a las operaciones del sistema.
- **Domain / Model:** Contiene entidades y reglas de negocio.
- **Services:** Operaciones reutilizables y lógica de apoyo.
- **Persistence / System:** Acceso a base de datos y tecnologías externas.

---

# 🗄️ Diseño de Base de Datos

El sistema utiliza un modelo relacional que contempla:

- Separación entre vehículo (modelo) y unidad física.
- Control de disponibilidad mediante estados de las unidades.
- Relaciones entre ventas, clientes, empleados y concesionarias.
- Estructuras preparadas para consultas y reportes administrativos.

Además, la base de datos incluye:

- Restricciones de integridad.
- Triggers para automatización de estados.
- Procedimientos y funciones PL/SQL.
- Vistas para reportes y consultas de gestión.

---

# 📋 Reglas de Negocio Principales

- Cada concesionaria debe tener exactamente un gerente asignado.
- Un vendedor pertenece a una única concesionaria.
- Una unidad de vehículo solo puede venderse una vez.
- Cada venta debe estar asociada a un cliente y un vendedor.
- El estado de una unidad se actualiza automáticamente después de una venta.
- Un cliente puede realizar múltiples compras.
- Las metas comerciales pueden asignarse a empleados o concesionarias.

---

# 📊 Modelos del Sistema

## Modelo Físico de Base de Datos
<img width="484" height="422" alt="{180107CA-F56A-4E8B-8EF3-46DBB50FD476}" src="https://github.com/user-attachments/assets/bdccb3d8-1ff5-4a39-a635-6bb608f4933e" />

## Modelo de clases y métodos

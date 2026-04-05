# 🏭 WMS (Warehouse Management System)

Sistema de mini gestión de almacenes desarrollado en **Java Spring Boot**, con persistencia en **Oracle XE** y completamente **dockerizado** para facilitar su ejecución y despliegue.

---

## 🚀 Características

* Gestión de **productos**
* Gestión de **ubicaciones**
* Control de **inventario**
* Operaciones de movimiento de stock (`moveInventory`)
* API REST para integración
* Base de datos Oracle XE
* Contenedores Docker para app y base de datos

---

## 🐳 Ejecución con Docker

### Requisitos

* Docker Desktop
* Docker Compose

### Arranque

```bash
docker-compose up --build
```

### Servicios

| Servicio  | URL                   |
| --------- | --------------------- |
| WMS API   | http://localhost:8080 |
| Oracle XE | localhost:1521        |

---

## ⚙️ Configuración

La aplicación utiliza variables de entorno:

```properties
SPRING_DATASOURCE_URL=jdbc:oracle:thin:@//oracle:1521/XEPDB1
SPRING_DATASOURCE_USERNAME=system
SPRING_DATASOURCE_PASSWORD=oracle
```

---

## 📦 Estructura del proyecto

```
wms/
├── inventory/
├── product/
├── location/
├── seed/
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

---

## 🔄 Seed de datos

El sistema incluye un `SeedDataRunner` que:

* Limpia la base de datos
* Inserta datos iniciales de:

  * Productos
  * Ubicaciones
  * Inventario

---

## 🧠 Arquitectura

Actualmente el sistema sigue un enfoque **monolítico modular**, preparado para evolucionar a microservicios:

* `product-service`
* `inventory-service`
* `location-service`

---

## 📌 Estado del proyecto

🟡 En desarrollo activo

* Dockerización completada
* Integración con Oracle funcional
* Próximos pasos:
  * Introducción de movimientos con su ciclo de vida
  * Mejorar resiliencia
  * Separación en microservicios
  * Tests automatizados

---

## 👨‍💻 Autor

Desarrollado como proyecto de aprendizaje y evolución hacia arquitectura backend moderna.

Este proyecto está bajo licencia MIT (puedes cambiarla según necesites).

---

## 📄 Licencia

Este proyecto está bajo licencia MIT (puedes cambiarla según necesites).

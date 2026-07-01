# UrbanVoice Backend — Arquitectura

Este documento resume la organización interna del backend de UrbanVoice.
Es una referencia de alto nivel; el código fuente es la fuente de verdad.

## Estilo arquitectónico

El backend sigue un enfoque de **Domain-Driven Design (DDD)** con
**bounded contexts** independientes. Cada contexto se organiza en cuatro
capas:

| Capa | Responsabilidad |
| --- | --- |
| `domain` | Entidades, objetos de valor, eventos y reglas de negocio |
| `application` | Casos de uso y orquestación transaccional |
| `infrastructure` | Persistencia (JPA), seguridad y servicios de salida |
| `interfaces` | Controladores REST y recursos de entrada/salida |

## Bounded contexts

- **iam** — Identidad y control de acceso (JWT, seguridad).
- **locations** — Gestión de ubicaciones geográficas.
- **districts** — Distritos de la ciudad y sus indicadores.
- **notifications** — Generación y entrega de alertas.

## Convenciones

- Las reglas de negocio viven en el `domain`, nunca en los controladores.
- La capa de `application` no contiene lógica de negocio propia.
- Los mapeadores JPA traducen entre entidades de dominio y entidades de
  persistencia, manteniendo el dominio libre de anotaciones de framework.

package com.upc.pre.urbanvoiceapp.shared.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuración de persistencia global para el módulo com.upc.pre.urbanvoiceapp
 * Habilita el soporte de repositories de JPA y gestión de transacciones.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.upc.pre.urbanvoiceapp")
@EnableTransactionManagement
public class PersistenceConfiguration {
    // La configuración automática de Spring Boot gestiona DataSource y JpaVendorAdapter
    // Basándose en application.properties
}

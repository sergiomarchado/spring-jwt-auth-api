package com.sharelist.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Punto de entrada principal de la aplicación Spring Boot.
 * La anotación @SpringBootApplication combina:
 * - @Configuration: marca la clase como fuente de configuración.
 * - @EnableAutoConfiguration: activa la configuración automática de Spring.
 * - @ComponentScan: permite buscar componentes, controladores, etc., en el paquete actual y subpaquetes.
 */
@SpringBootApplication
public class ShareListApiApplication {
	/**
	 * Método main que inicia la aplicación Spring Boot.
	 * SpringApplication.run() arranca el contexto de Spring, levanta el servidor embebido (como Tomcat)
	 * y ejecuta toda la configuración automáticamente.
	 *
	 * @param args argumentos pasados desde línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(ShareListApiApplication.class, args);
	}

}

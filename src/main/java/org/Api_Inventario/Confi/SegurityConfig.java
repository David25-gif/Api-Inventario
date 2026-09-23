package org.Api_Inventario.Confi;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class SegurityConfig {
}

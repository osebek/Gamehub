package cz.ondra.gamehub.config;

import org.mapstruct.ReportingPolicy;

@org.mapstruct.MapperConfig(componentModel = "spring", typeConversionPolicy = ReportingPolicy.WARN)
public class MapperConfig {
}

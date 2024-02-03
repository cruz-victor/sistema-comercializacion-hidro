

INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('TIPOS_TRAMO','PRAL','Principal','Tramo Principal','2021-07-09',NULL),
	 ('TIPOS_TRAMO','SEC ','Secundario','Tramos Secundario','2021-07-09',NULL),
	 ('FRONTERAS','TAMBOQ','Tambo Quemado','Tambo Quemado','2021-07-09',NULL),
	 ('FRONTERAS','PISIGA','Pisiga','Pisiga','2021-07-09',NULL),
	 ('FRONTERAS','AVAROA','Avaroa','Avaroa','2021-07-09',NULL),
	 ('DISTRITOS','DTCOC','Distrito DTCOC','Distrito DTCOC','2021-07-09',NULL),
	 ('DISTRITOS','DTCC','Distrito DTCC','Distrito DTCC','2021-07-09',NULL),
	 ('DISTRITOS','DTCA','Distrito DTCA','Distrito DTCA','2021-07-09',NULL),
	 ('DISTRITOS','DTCT','Distrito DTCT','Distrito DTCT','2021-07-09',NULL),
	 ('DISTRITOS','DTCOR','Distrito DTCOR','Distrito DTCOR','2021-07-09',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('DISTRITOS','DTCS','Distrito DTCS','Distrito DTCS','2021-07-09',NULL),
	 ('DISTRITOS','DTCP','Distrito DTCP','Distrito DTCP','2021-07-09',NULL),
	 ('TIPOS_DOCUMENTO','CI ','Carnet de Identidad','Carnet de Identidad','2021-07-09',NULL),
	 ('TIPOS_DOCUMENTO','NIT','Número de Identificación Tributaria','Número de Identificación Tributaria','2021-07-09',NULL),
	 ('TIPOS_DOCUMENTO','PAS','PASAPORTE','PASAPORTE','2021-07-09',NULL),
	 ('TIPO_CONSTITUCION_EMPRESAS','SA ','Sociedad Anonima','Sociedad Anonima','2021-07-09',NULL),
	 ('TIPO_CONSTITUCION_EMPRESAS','SRL','Sociedad de Responsabilidad Limitada','Sociedad de Responsabilidad Limitada','2021-07-09',NULL),
	 ('TIPO_CONSTITUCION_EMPRESAS','SAC','Sociedad Accidental','Sociedad Accidental','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','ILO ','Ilo','Ilo','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','CALLAO ','Callao/Lima (Lurin-Refineria Conchan)','Callao/Lima (Lurin-Refineria Conchan)','2021-07-09',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('PUNTOS_RECEPCION','MOLLENDO ','Mollendo','Mollendo','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','CONCHAN','Conchan','Conchan','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','ARICA ','Arica','Arica','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','IQUIQUE ','Iquique','Iquique','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','ANTOFAGASTA','Antofagasta','Antofagasta','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','TOCOPILLA ','Tocopilla','Tocopilla','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','MEJILLONES','Mejillones','Mejillones','2021-07-09',NULL),
	 ('PUNTOS_RECEPCION','QUINTEROS','Quinteros','Quinteros','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','LA PAZ ','La Paz ','La Paz ','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','ORURO','Oruro','Oruro','2021-07-09',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('PUNTOS_ENTREGA','COCHABAMBA','Cochabamba','Cochabamba','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','TRINIDAD','Trinidad','Trinidad','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','PUERTOVILLARROEL','Puerto Villarroel','Puerto Villarroel','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','GUAYARAMERIN','Guayaramerin','Guayaramerin','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','RIBERALTA ','Riberalta ','Riberalta ','2021-07-09',NULL),
	 ('MEDIOS_SUMINISTRO','CISTER','Cisternas','Cisternas','2021-07-06',NULL),
	 ('MEDIOS_SUMINISTRO','BUQUE','Buques','Buques','2021-07-06',NULL),
	 ('MEDIOS_SUMINISTRO','PLANTA','Planta','Planta y/o refineria','2021-07-06',NULL),
	 ('CLASE_CONTRATO','SERV','Servicio','Servicio','2021-07-06',NULL),
	 ('CLASE_CONTRATO','ADHE','Adhesion','Adhesion','2021-07-06',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('TIPO_CONTRATO','TRANS','Transporte','Transporte','2021-07-06',NULL),
	 ('TIPO_CONTRATO','SUMIN','Suministro','Suministro','2021-07-06',NULL),
	 ('AMBITO_CONTRATO','INT','Internacional','Internacional','2021-07-06',NULL),
	 ('AMBITO_CONTRATO','NAL','Nacional','Nacional','2021-07-06',NULL),
	 ('LOTE','1','Lote 1','Lote 1','2021-07-06',NULL),
	 ('LOTE','2','Lote 2','Lote 2','2021-07-06',NULL),
	 ('LOTE','3','Lote 3','Lote 3','2021-07-06',NULL),
	 ('LOTE','4','Lote 4','Lote 4','2021-07-06',NULL),
	 ('LOTE','5','Lote 5','Lote 5','2021-07-06',NULL),
	 ('CRITERIOS_FINALIZACION','CIERRE','Cierre de contrato','Cierre de contrato','2021-07-06',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('CRITERIOS_FINALIZACION','DESCARGA','Ultima descarga ','Ultima cisterna descargada','2021-07-06',NULL),
	 ('CRITERIOS_FINALIZACION','CARGA ','Ultima carga ','Ultima carga a cisterna','2021-07-06',NULL),
	 ('PUNTOS_ENTREGA','COBIJA','Cobija','Cobija','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','TARIJA','Tarija','Tarija','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','BERMEJO','Bermejo','Bermejo','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','YACUIBA','Yacuiba','Yacuiba','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','VILLAMONTES','Villamontes','Villamontes','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','SANTA CRUZ','Santa Cruz','Santa Cruz','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','CAMIRI','Camiri','Camiri','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','SANJOSE','San José','San José','2021-07-09',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('PUNTOS_ENTREGA','PUERTO SUAREZ','Puerto Suarez','Puerto Suarez','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','SUCRE','Sucre','Sucre','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','MONTEAGUDO','Monteagudo','Monteagudo','2021-07-09',NULL),
	 ('PAISES','BOL','Bolivia','Bolivia','2021-07-09',NULL),
	 ('PAISES','PER','Peru','Peru','2021-07-09',NULL),
	 ('PAISES','CHL','Chile','Chile','2021-07-09',NULL),
	 ('PAISES','ARG','Argentina','Argentina','2021-07-09',NULL),
	 ('PAISES','BRA','Brasil','Brasil','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','POTOSI','Potosi','Potosi','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','TUPIZA','Tupiza','Tupiza','2021-07-09',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('PUNTOS_ENTREGA','UYUNI','Uyuni','Uyuni','2021-07-09',NULL),
	 ('PUNTOS_ENTREGA','VILLAZÓN','Villazón','Villazón','2021-07-09',NULL),
	 ('PARTIDAS_PRESUPUESTARIAS','MAYOR','Mayoreo','Mayoreo','2021-07-06',NULL),
	 ('PARTIDAS_PRESUPUESTARIAS','RECON','Crudo Reconstituido','Crudo Reconstituido','2021-07-06',NULL),
	 ('PRODUCTOS_NACIONALES','DO','Diése Oil   ','Diésel Oil','2021-07-06',NULL),
	 ('PRODUCTOS_NACIONALES','HL','Hidrocarburos Liquidos','Hidrocarburos Liquidos','2021-07-06',NULL),
	 ('PRODUCTOS_NACIONALES','IA','Insumos y Aditivos','Insumos y Aditivos','2021-07-06',NULL),
	 ('SECTORES_NACIONALES','OCC','Occidente','Sector occidente','2021-07-06',NULL),
	 ('SECTORES_NACIONALES','SUR','Sur','SEctor Sur','2021-07-06','2021-07-06'),
	 ('SECTORES_NACIONALES','SURORI','SurOriente','Sector Sur  Oriente','2021-07-06',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('SECTORES_NACIONALES','ORI','Oriente','Sector Oriente','2021-07-06','2021-07-06'),
	 ('PARTIDAS_PRESUPUESTARIAS','CTTOS','Contratos Petroleros','Contratos Petroleros','2021-07-06',NULL),
	 ('CERTIFICACION_SERVICIOS','TRANS','Transporte','Transporte','2021-07-06',NULL),
	 ('CERTIFICACION_SERVICIOS','SUMIN','Suministro','Suministro','2021-07-06',NULL),
	 ('CERTIFICACION_SERVICIOS','COMPRA','Compra','Compra','2021-07-06',NULL),
	 ('CERTIFICACION_SERVICIOS','OTROS','Otros Servicios','Otros Servicios','2021-07-06',NULL),
	 ('CERTIFICACION_CONCEPTOS','CTTO','Contrato','Contrato','2021-07-06',NULL),
	 ('CERTIFICACION_CONCEPTOS','ADENDA','Adenda','Adenda','2021-07-06',NULL),
	 ('CERTIFICACION_CONCEPTOS','INCR','Incremento','Incremento','2021-07-06',NULL),
	 ('CERTIFICACION_CONCEPTOS','ACT','Actualización','Actualización','2021-07-06',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('CERTIFICACION_CONCEPTOS','REV ','Reversión','Reversión','2021-07-06',NULL),
	 ('PRODUCTOS_INTERNACIONALES','GA','Gasolina','Gasolina','2021-07-06',NULL),
	 ('PRODUCTOS_INTERNACIONALES','DO','Diése Oil   ','Diésel Oil','2021-07-06',NULL),
	 ('PRODUCTOS_INTERNACIONALES','HL','Hidrocarburos Liquidos','Hidrocarburos Liquidos','2021-07-06',NULL),
	 ('PRODUCTOS_INTERNACIONALES','IA','Insumos y Aditivos','Insumos y Aditivos','2021-07-06',NULL),
	 ('SECTORES_INTERNACIONALES','OCC','Occidente','Sector occidente','2021-07-06',NULL),
	 ('PRODUCTOS_NACIONALES','GA','Gasolina','Gasolina','2021-07-06',NULL),
	 ('GESTIONES_CONTRATOS','2020','2020','Gestion del sistema 2020','2021-07-06',NULL),
	 ('GESTIONES_CONTRATOS','2021','2021','ACTIVA','2021-07-06',NULL),
	 ('GESTIONES_CERTIFICACIONES','2020','2020','Gestion del sistema 2020','2021-07-06',NULL);
INSERT INTO parametro.parametro (contexto,codigo,valor,descripcion,fecha_inicio,fecha_fin) VALUES
	 ('GESTIONES_CERTIFICACIONES','2021','2021','ACTIVA','2021-07-06',NULL),
	 ('SECTORES_SUMINISTROS','OCC','Occidente','Sector occidente','2021-07-06',NULL),
	 ('SECTORES_INTERNACIONALES','SURORI','SurOriente','Sector Sur  Oriente','2021-07-06',NULL),
	 ('SECTORES_SUMINISTROS','SURORI','SurOriente','Sector Sur  Oriente','2021-07-06',NULL),
	 ('SECTORES_INTERNACIONALES','ORI','Oriente','Sector Oriente','2021-07-06','2021-07-06'),
	 ('SECTORES_INTERNACIONALES','SUR','Sur','SEctor Sur','2021-07-06','2021-07-06'),
	 ('SECTORES_SUMINISTROS','ORI','Oriente','Sector Oriente','2021-07-06','2021-07-06'),
	 ('SECTORES_SUMINISTROS','SUR','Sur','SEctor Sur','2021-07-06','2021-07-06');
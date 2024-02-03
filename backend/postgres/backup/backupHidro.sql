PGDMP         "                y            fenix    10.16    10.16 �    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false                        2615    18976    certificacion    SCHEMA        CREATE SCHEMA certificacion;
    DROP SCHEMA certificacion;
             postgres    false            �           0    0    SCHEMA certificacion    ACL     4   GRANT USAGE ON SCHEMA certificacion TO grupo_fenix;
                  postgres    false    8            	            2615    18977    contrato    SCHEMA        CREATE SCHEMA contrato;
    DROP SCHEMA contrato;
             postgres    false            �           0    0    SCHEMA contrato    ACL     /   GRANT USAGE ON SCHEMA contrato TO grupo_fenix;
                  postgres    false    9                        2615    18978    gestion    SCHEMA        CREATE SCHEMA gestion;
    DROP SCHEMA gestion;
             postgres    false            �           0    0    SCHEMA gestion    ACL     .   GRANT USAGE ON SCHEMA gestion TO grupo_fenix;
                  postgres    false    14                        2615    28137 	   gestionV2    SCHEMA        CREATE SCHEMA "gestionV2";
    DROP SCHEMA "gestionV2";
             postgres    false                        2615    18979 	   parametro    SCHEMA        CREATE SCHEMA parametro;
    DROP SCHEMA parametro;
             postgres    false            �           0    0    SCHEMA parametro    ACL     0   GRANT USAGE ON SCHEMA parametro TO grupo_fenix;
                  postgres    false    11                        2615    26896    proceso_contratacion    SCHEMA     $   CREATE SCHEMA proceso_contratacion;
 "   DROP SCHEMA proceso_contratacion;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    6            Y           1255    27096 )   actualizar_vm_certificaciones_dashboard()    FUNCTION     �   CREATE FUNCTION certificacion.actualizar_vm_certificaciones_dashboard() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view certificacion.certificacion_dashboard;
    return null;
end $$;
 G   DROP FUNCTION certificacion.actualizar_vm_certificaciones_dashboard();
       certificacion       postgres    false    8            W           1255    27082 "   actualizar_vm_partidas_dashboard()    FUNCTION     �   CREATE FUNCTION certificacion.actualizar_vm_partidas_dashboard() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view certificacion.partida_dashboard;
    return null;
end $$;
 @   DROP FUNCTION certificacion.actualizar_vm_partidas_dashboard();
       certificacion       postgres    false    8            \           1255    34956 +   obtener_gestion_movimiento(bigint, integer)    FUNCTION     �  CREATE FUNCTION certificacion.obtener_gestion_movimiento(p_id_certificacion bigint, p_gestion integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado integer;	
begin
	if p_gestion is null or p_gestion=0 then
		v_resultado:=(select p.gestion from certificacion.certificacion c inner join certificacion.partida p on c.id_partida =p.id where c.id=p_id_certificacion limit 1);
	else 
		v_resultado:=p_gestion;
	end if;
	return coalesce (v_resultado,1900);
end 
$$;
 f   DROP FUNCTION certificacion.obtener_gestion_movimiento(p_id_certificacion bigint, p_gestion integer);
       certificacion       postgres    false    8            V           1255    27056 A   obtener_monto_ejecutado_por_categoria(character varying, integer)    FUNCTION     �   CREATE FUNCTION certificacion.obtener_monto_ejecutado_por_categoria(p_categoria character varying, p_gestion integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
declare 
	volumen_ejecutado numeric;	
begin	
	return 0.000::decimal;
end 
$$;
 u   DROP FUNCTION certificacion.obtener_monto_ejecutado_por_categoria(p_categoria character varying, p_gestion integer);
       certificacion       postgres    false    8            T           1255    26243 a   obtener_monto_total_certificado(character varying, character varying, character varying, integer)    FUNCTION       CREATE FUNCTION certificacion.obtener_monto_total_certificado(tipo character varying, ambito character varying, sector character varying, gestion integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
declare 
	volumen_ejecutado numeric;	
begin	
	return 0.000::decimal;
end 
$$;
 �   DROP FUNCTION certificacion.obtener_monto_total_certificado(tipo character varying, ambito character varying, sector character varying, gestion integer);
       certificacion       postgres    false    8            a           1255    30842 2   obtener_totales_certificaciones_dashboard(integer)    FUNCTION     �  CREATE FUNCTION certificacion.obtener_totales_certificaciones_dashboard(p_gestion integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;	
	v_valor_por_defecto varchar:='[{"montoMaximo":0,"montoEjecutado":0,"saldo":0,"concepto":"CTTO"}]';
begin	
	v_resultado:= (	
		select jsonb_agg(datos)::varchar 
		from
		(
		 select 
		 sum(monto_maximo) as "montoMaximo",
		 sum(monto_ejecutado) as "montoEjecutado",
		 sum(saldo) as "saldo",
		 concepto as "concepto"	
		 from certificacion.certificacion_dashboard		
		 where gestion=p_gestion
		 group by concepto 
		) as datos	
	);

	return coalesce (v_resultado,v_valor_por_defecto);
end 
$$;
 Z   DROP FUNCTION certificacion.obtener_totales_certificaciones_dashboard(p_gestion integer);
       certificacion       postgres    false    8            b           1255    30841 +   obtener_totales_partidas_dashboard(integer)    FUNCTION     �  CREATE FUNCTION certificacion.obtener_totales_partidas_dashboard(p_gestion integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;	
	v_valor_por_defecto varchar:='[{"montoActual":0,"montoIncremento":0,"montoAprobado":0,"montoEjecutado":0,"categoria":"MAYOR"}]';
begin	
	v_resultado:= (	
		select jsonb_agg(datos)::varchar 
		from
		(
		 select 
		 monto_actual as "montoActual",
		 monto_incremento as "montoIncremento",
		 monto_aprobado as "montoAprobado",
		 monto_ejecutado as "montoEjecutado",
		 categoria 
		 from certificacion.partida_dashboard
		 where gestion = p_gestion
		) as datos	
	);

	return coalesce (v_resultado,v_valor_por_defecto);
end 
$$;
 S   DROP FUNCTION certificacion.obtener_totales_partidas_dashboard(p_gestion integer);
       certificacion       postgres    false    8            ]           1255    29441    actualizar_vm_contratos()    FUNCTION     �   CREATE FUNCTION contrato.actualizar_vm_contratos() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view contrato.contratos;
    return null;
end $$;
 2   DROP FUNCTION contrato.actualizar_vm_contratos();
       contrato       postgres    false    9            U           1255    27081 "   actualizar_vm_partidas_dashboard()    FUNCTION     �   CREATE FUNCTION contrato.actualizar_vm_partidas_dashboard() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view certificacion.partida_dashboard;
    return null;
end $$;
 ;   DROP FUNCTION contrato.actualizar_vm_partidas_dashboard();
       contrato       postgres    false    9            _           1255    29863 g   obtener_total_de_contratos_por_sector(character varying, character varying, character varying, integer)    FUNCTION     �  CREATE FUNCTION contrato.obtener_total_de_contratos_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$

declare	
	v_sql varchar:='';	
begin
		return 
		coalesce((		
			select count(*) 
			from contrato.contratos
			where tipo=p_tipo and 
			ambito=p_ambito and
			sector=p_sector	and
			gestion= p_gestion
		),0);					
end
$$;
 �   DROP FUNCTION contrato.obtener_total_de_contratos_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            c           1255    29572 H   obtener_totales_dashboard(character varying, character varying, integer)    FUNCTION     �  CREATE FUNCTION contrato.obtener_totales_dashboard(p_tipo character varying, p_ambito character varying, p_gestion integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;	

begin		
	case
		when p_tipo='TRANS' and p_ambito='NAL' then
			return (contrato.obtener_totales_por_tipo_ambito(p_tipo, p_ambito, p_gestion, 'SECTORES_NACIONALES'));		
		when p_tipo='TRANS' and p_ambito='INT' then
			return (contrato.obtener_totales_por_tipo_ambito(p_tipo, p_ambito, p_gestion, 'SECTORES_INTERNACIONALES'));		
		when p_tipo='SUMIN' and p_ambito='INT' then --*
			return (contrato.obtener_totales_por_tipo_ambito(p_tipo, p_ambito, p_gestion, 'SECTORES_SUMINISTROS'));
	end case;		
end 
$$;
 {   DROP FUNCTION contrato.obtener_totales_dashboard(p_tipo character varying, p_ambito character varying, p_gestion integer);
       contrato       postgres    false    9            [           1255    28860 d   obtener_totales_general_por_sector(character varying, character varying, character varying, integer)    FUNCTION     =  CREATE FUNCTION contrato.obtener_totales_general_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS json
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado json;	
	v_valor_por_defecto varchar:='{"tipo": "A", "sector": "B",  "volumenejecutado": 0,  "proximovencimiento": "1900-01-01",   "restomontocertificado": 0.000}';
begin	
	v_resultado:=(
		SELECT  json_build_object
		(
			'cantidadContratos',(select * from contrato.obtener_total_de_contratos_por_sector(p_tipo,p_ambito,p_sector,p_gestion)),	        
	        'volumen',( select contrato.sumar_volumen_por_sector(p_tipo,p_ambito,p_sector,p_gestion)),
	        'monto',(select contrato.sumar_monto_por_sector(p_tipo,p_ambito,p_sector,p_gestion))  
		)	
	);

	return v_resultado;
end 
$$;
 �   DROP FUNCTION contrato.obtener_totales_general_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            d           1255    29461 a   obtener_totales_por_tipo_ambito(character varying, character varying, integer, character varying)    FUNCTION     G  CREATE FUNCTION contrato.obtener_totales_por_tipo_ambito(p_tipo character varying, p_ambito character varying, p_gestion integer, p_sectores character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;		
	v_nombre_objeto varchar:='';
begin	
	
	--Obtener los sectores en tabla temporal
	drop table if exists TEMPORAL_SECTORES;

	create temp table TEMPORAL_SECTORES (			
	    	tipo varchar,
	    	ambito varchar, 
	    	sector_codigo varchar,
	    	sector_valor varchar,
	    	total_sector json
	);

	insert into TEMPORAL_SECTORES(tipo,ambito,sector_codigo,sector_valor)
	(
		select distinct tipo, ambito, sector, parametro.obtener_valor_apartir_codigo(p_sectores,sector) sector_valor
		from contrato.contratos  
		where tipo=p_tipo and 
		ambito=p_ambito and
		sector in (select parametro.obtener_codigos_por_contexto(p_sectores)) and
		gestion = p_gestion
	);
	
	--Calcular el json de totales por sector (cantidad contratos, volumen y monto).
	update TEMPORAL_SECTORES
	set total_sector=(select contrato.obtener_totales_general_por_sector(TEMPORAL_SECTORES.tipo, TEMPORAL_SECTORES.ambito, TEMPORAL_SECTORES.sector_codigo, p_gestion));

	--Calcular el total general e inserta el resultado en la tabla temporal.
	insert into TEMPORAL_SECTORES(tipo,ambito,sector_codigo,sector_valor,total_sector)
	values(
		p_tipo,
		p_ambito,
		'GEN',
		'TotalGeneral',
		(SELECT  json_build_object(
	        'cantidadContratos',(select sum((total_sector->>'cantidadContratos')::int) cantidadContratos from TEMPORAL_SECTORES),
	        'volumen', coalesce(( select json_agg(suma_volumenes) 
	  				    from (select  
								(vol->>'producto')::varchar as producto, 
								coalesce(sum((vol->>'mermaPermitida')::numeric),0)  as "mermaPermitida", 
								coalesce(sum((vol->>'volumenCargado')::numeric),0) as "volumenCargado", 
								coalesce(sum((vol->>'volumenNominado')::numeric),0) as "volumenNominado", 
								coalesce(sum((vol->>'volumenDescargado')::numeric),0) as "volumenDescargado", 
								coalesce(sum((vol->>'porcentajeNominado')::numeric),0) as "porcentajeNominado"
								from TEMPORAL_SECTORES tmp, json_array_elements(tmp.total_sector->'volumen') as vol 	
								group by vol->>'producto') suma_volumenes),'[]'),
	        'monto',(select row_to_json(monto) 
	        		 from (
	        		 	select 
	        		 		coalesce(sum((total_sector->'monto'->>'saldo')::numeric),0) as "saldo", 
	        		 		coalesce(sum((total_sector->'monto'->>'ejecutado')::numeric),0) as "ejecutado", 
	        		 		coalesce(sum((total_sector->'monto'->>'montoAdjudicado')::numeric),0) as "montoAdjudicado" 
	        		 	from TEMPORAL_SECTORES limit 1) monto)  
		))
	);

	--Obtener el nombre del objeto json segun el sector. 
	case
		when p_sectores = 'SECTORES_NACIONALES' then
			v_nombre_objeto:='TransporteNacional';		
		when p_sectores = 'SECTORES_INTERNACIONALES' then
			v_nombre_objeto:='TransporteInternacional';		
		when p_sectores = 'SECTORES_SUMINISTROS' then
			v_nombre_objeto:='Suministro';
	end case;		
		

	--Retornar json con el formato definido por el front.
	return (
		select json_build_object(
			v_nombre_objeto,
			(select json_object_agg(t.sector_valor,t.total_sector)
			 from (
				select sector_valor, total_sector
				from TEMPORAL_SECTORES) t)
		)
	);

end 
$$;
 �   DROP FUNCTION contrato.obtener_totales_por_tipo_ambito(p_tipo character varying, p_ambito character varying, p_gestion integer, p_sectores character varying);
       contrato       postgres    false    9            `           1255    29866 X   sumar_monto_por_sector(character varying, character varying, character varying, integer)    FUNCTION     �  CREATE FUNCTION contrato.sumar_monto_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS json
    LANGUAGE plpgsql
    AS $$

declare	
	v_sql varchar:='';	
begin
		return 
		(		
			select row_to_json(montos)
			from (
				select 
				coalesce(sum((contrato.monto->>'saldo')::numeric),0) as "saldo", 
				coalesce(sum((contrato.monto->>'ejecutado')::numeric),0) as "ejecutado", 
				coalesce(sum((contrato.monto->>'montoAdjudicado')::numeric),0) as "montoAdjudicado"
				from contrato.contratos contrato
				where contrato.tipo=p_tipo and 
				contrato.ambito=p_ambito and
				contrato.sector=p_sector and
				contrato.gestion=p_gestion
			) montos
		
				
		);					
end
$$;
 �   DROP FUNCTION contrato.sumar_monto_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            ^           1255    29865 Z   sumar_volumen_por_sector(character varying, character varying, character varying, integer)    FUNCTION     &  CREATE FUNCTION contrato.sumar_volumen_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS json
    LANGUAGE plpgsql
    AS $$

declare	
	v_sql varchar:='';	
begin
		return coalesce((select json_agg(t)
			   from 
				(		
					select  
					(vol->>'producto')::varchar as producto, 
					coalesce(sum((vol->>'mermaPermitida')::numeric),0)  as "mermaPermitida", 
					coalesce(sum((vol->>'volumenCargado')::numeric),0) as "volumenCargado", 
					coalesce(sum((vol->>'volumenNominado')::numeric),0) as "volumenNominado", 
					coalesce(sum((vol->>'volumenDescargado')::numeric),0) as "volumenDescargado", 
					coalesce(sum((vol->>'porcentajeNominado')::numeric),0) as "porcentajeNominado"
					from contrato.contratos contrato, jsonb_array_elements(contrato.volumen) as vol
					where contrato.tipo=p_tipo and 
					contrato.ambito=p_ambito and
					contrato.sector=p_sector and
					contrato.gestion=p_gestion
					group by vol->>'producto'	
				) t),'[]') ;					
end
$$;
 �   DROP FUNCTION contrato.sumar_volumen_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            X           1255    27058 /   obtener_codigos_por_contexto(character varying)    FUNCTION     {  CREATE FUNCTION parametro.obtener_codigos_por_contexto(p_contexto character varying) RETURNS TABLE(codigo_contexto character varying)
    LANGUAGE plpgsql
    AS $$
declare 
	volumen_ejecutado numeric;	
begin	
	return query
		select codigo
		from parametro.parametro p
		where p.contexto =p_contexto and 
		fecha_inicio is not null and 
		fecha_fin is null;	
end 
$$;
 T   DROP FUNCTION parametro.obtener_codigos_por_contexto(p_contexto character varying);
    	   parametro       postgres    false    11            Z           1255    28857 B   obtener_valor_apartir_codigo(character varying, character varying)    FUNCTION     �  CREATE FUNCTION parametro.obtener_valor_apartir_codigo(p_contexto character varying, p_codigo character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	resultado varchar;	
begin	
    resultado:=(select valor
				from parametro.parametro p
				where p.contexto = p_contexto and 
				p.codigo = p_codigo and
				fecha_inicio is not null and 
				fecha_fin is null
				limit 1);	
	
	return resultado;
end 
$$;
 p   DROP FUNCTION parametro.obtener_valor_apartir_codigo(p_contexto character varying, p_codigo character varying);
    	   parametro       postgres    false    11            &           1259    27529    certificacion    TABLE     y  CREATE TABLE certificacion.certificacion (
    id bigint DEFAULT nextval(('certificacion.certificacion_presupuestaria_id_seq'::text)::regclass) NOT NULL,
    id_partida bigint,
    concepto character varying(20),
    fecha_emision timestamp without time zone,
    numero_certificacion character varying(20),
    descripcion character varying(500),
    monto_maximo numeric(20,4),
    monto_ejecutado numeric(20,4),
    saldo numeric(20,4),
    tipo_servicio character varying(20),
    id_proceso_contratacion bigint,
    id_lotes character varying(20),
    id_contrato bigint,
    ruta_documento_adjunto text,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70),
    id_certificacion bigint
);
 (   DROP TABLE certificacion.certificacion;
       certificacion         postgres    false    8            %           1259    27521    partida    TABLE     �  CREATE TABLE certificacion.partida (
    id bigint DEFAULT nextval(('certificacion.partida_presupuestaria_id_seq'::text)::regclass) NOT NULL,
    gestion integer NOT NULL,
    categoria character varying(20) NOT NULL,
    numero_partida character varying(20),
    monto_actual numeric(20,4),
    monto_aprobado numeric(20,4),
    monto_incremento numeric(20,4),
    saldo numeric(20,4),
    ruta_documento_adjunto text,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
 "   DROP TABLE certificacion.partida;
       certificacion         postgres    false    8            G           1259    34970    certificacion_dashboard    MATERIALIZED VIEW     �  CREATE MATERIALIZED VIEW certificacion.certificacion_dashboard AS
 SELECT COALESCE(sum(c.monto_maximo), (0)::numeric(20,4)) AS monto_maximo,
    COALESCE(sum(c.monto_ejecutado), (0)::numeric(20,4)) AS monto_ejecutado,
    COALESCE(sum(c.saldo), (0)::numeric(20,4)) AS saldo,
    c.concepto,
    c.tipo_servicio,
    certificacion.obtener_gestion_movimiento(c.id_certificacion, p.gestion) AS gestion,
        CASE
            WHEN ((c.concepto)::text = 'CTTO'::text) THEN 'CONTRATO'::text
            WHEN ((c.concepto)::text = ANY ((ARRAY['ADENDA'::character varying, 'INCR'::character varying, 'ACT'::character varying, 'REV'::character varying])::text[])) THEN 'MOVIMIENTO'::text
            ELSE NULL::text
        END AS tipo_certificacion
   FROM (certificacion.certificacion c
     LEFT JOIN certificacion.partida p ON ((c.id_partida = p.id)))
  WHERE ((c.eliminado = false) AND ((c.concepto)::text IN ( SELECT obtener_codigos_por_contexto.codigo_contexto
           FROM parametro.obtener_codigos_por_contexto('CERTIFICACION_CONCEPTOS'::character varying) obtener_codigos_por_contexto(codigo_contexto))) AND ((c.tipo_servicio)::text IN ( SELECT obtener_codigos_por_contexto.codigo_contexto
           FROM parametro.obtener_codigos_por_contexto('CERTIFICACION_SERVICIOS'::character varying) obtener_codigos_por_contexto(codigo_contexto))))
  GROUP BY c.concepto, c.tipo_servicio, p.gestion, c.id_certificacion
  WITH NO DATA;
 >   DROP MATERIALIZED VIEW certificacion.certificacion_dashboard;
       certificacion         postgres    false    344    348    293    293    294    294    294    294    294    294    294    294    8            (           1259    27542 #   certificacion_presupuestaria_id_seq    SEQUENCE     �   CREATE SEQUENCE certificacion.certificacion_presupuestaria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE certificacion.certificacion_presupuestaria_id_seq;
       certificacion       postgres    false    8            F           1259    34784    partida_dashboard    MATERIALIZED VIEW     
  CREATE MATERIALIZED VIEW certificacion.partida_dashboard AS
 SELECT COALESCE(sum(pp.monto_actual), (0)::numeric(20,4)) AS monto_actual,
    COALESCE(sum(pp.monto_incremento), (0)::numeric(20,4)) AS monto_incremento,
    COALESCE(sum(pp.monto_aprobado), (0)::numeric(20,4)) AS monto_aprobado,
    certificacion.obtener_monto_ejecutado_por_categoria(pp.categoria, pp.gestion) AS monto_ejecutado,
    pp.categoria,
    pp.gestion
   FROM certificacion.partida pp
  WHERE ((pp.eliminado = false) AND ((pp.categoria)::text IN ( SELECT obtener_codigos_por_contexto.codigo_contexto
           FROM parametro.obtener_codigos_por_contexto('PARTIDAS_PRESUPUESTARIAS'::character varying) obtener_codigos_por_contexto(codigo_contexto))))
  GROUP BY pp.categoria, pp.gestion
  WITH NO DATA;
 8   DROP MATERIALIZED VIEW certificacion.partida_dashboard;
       certificacion         postgres    false    293    293    293    344    342    293    293    293    8            '           1259    27540    partida_presupuestaria_id_seq    SEQUENCE     �   CREATE SEQUENCE certificacion.partida_presupuestaria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ;   DROP SEQUENCE certificacion.partida_presupuestaria_id_seq;
       certificacion       postgres    false    8            #           1259    27044    proceso_contratacion    TABLE     �   CREATE TABLE certificacion.proceso_contratacion (
    id bigint DEFAULT nextval(('certificacion.proceso_contratacion_id_seq'::text)::regclass) NOT NULL,
    cuce character varying(20),
    objecto_contratacion character varying(500)
);
 /   DROP TABLE certificacion.proceso_contratacion;
       certificacion         postgres    false    8            $           1259    27051    proceso_contratacion_id_seq    SEQUENCE     �   CREATE SEQUENCE certificacion.proceso_contratacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 9   DROP SEQUENCE certificacion.proceso_contratacion_id_seq;
       certificacion       postgres    false    8                       1259    24618    contrato    TABLE     �  CREATE TABLE contrato.contrato (
    id bigint DEFAULT nextval(('contrato.contrato_id_seq'::text)::regclass) NOT NULL,
    id_tipo_contrato integer NOT NULL,
    numero_contrato integer,
    gestion_contrato integer,
    contrato jsonb NOT NULL,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE contrato.contrato;
       contrato         postgres    false    9                       1259    24632    contrato_id_seq    SEQUENCE     z   CREATE SEQUENCE contrato.contrato_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE contrato.contrato_id_seq;
       contrato       postgres    false    9            E           1259    31104 	   contratos    MATERIALIZED VIEW     �  CREATE MATERIALIZED VIEW contrato.contratos AS
 SELECT c.id,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'tipo'::text) AS tipo,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'ambito'::text) AS ambito,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'sector'::text) AS sector,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'medio'::text) AS medio,
    (((c.contrato -> 'informacionGeneral'::text) ->> 'gestion'::text))::integer AS gestion,
    ((c.contrato -> 'totalVolumenesMonto'::text) -> 'monto'::text) AS monto,
    ((c.contrato -> 'totalVolumenesMonto'::text) -> 'volumen'::text) AS volumen
   FROM contrato.contrato c
  WHERE (c.eliminado = false)
  WITH NO DATA;
 +   DROP MATERIALIZED VIEW contrato.contratos;
       contrato         postgres    false    273    273    273    9                       1259    24634    movimiento_id_seq    SEQUENCE     |   CREATE SEQUENCE contrato.movimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE contrato.movimiento_id_seq;
       contrato       postgres    false    9                       1259    24625    volumen_ejecutado    TABLE     �  CREATE TABLE contrato.volumen_ejecutado (
    id bigint DEFAULT nextval(('contrato.movimiento_id_seq'::text)::regclass) NOT NULL,
    id_contrato bigint NOT NULL,
    volumen numeric NOT NULL,
    fecha_ejecucion timestamp without time zone NOT NULL,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
 '   DROP TABLE contrato.volumen_ejecutado;
       contrato         postgres    false    9            :           1259    28435    asociacion_empresa    TABLE     �  CREATE TABLE gestion.asociacion_empresa (
    id bigint NOT NULL,
    id_empresa bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70),
    id_asociacion bigint
);
 '   DROP TABLE gestion.asociacion_empresa;
       gestion         postgres    false    14            9           1259    28433    asociacion_empresa_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.asociacion_empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE gestion.asociacion_empresa_id_seq;
       gestion       postgres    false    14    314            �           0    0    asociacion_empresa_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE gestion.asociacion_empresa_id_seq OWNED BY gestion.asociacion_empresa.id;
            gestion       postgres    false    313                       1259    25934    asociacion_id_seq    SEQUENCE     {   CREATE SEQUENCE gestion.asociacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE gestion.asociacion_id_seq;
       gestion       postgres    false    14            <           1259    28447    cisterna    TABLE     �  CREATE TABLE gestion.cisterna (
    id bigint NOT NULL,
    placa character varying(25) NOT NULL,
    anio integer,
    marca character varying(50),
    color character varying(50),
    capacidad integer,
    nro_compartimientos integer,
    nro_chasis character varying(50),
    disposicion_ejes character varying(50),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.cisterna;
       gestion         postgres    false    14                       1259    25936    cisterna_id_seq    SEQUENCE     y   CREATE SEQUENCE gestion.cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE gestion.cisterna_id_seq;
       gestion       postgres    false    14            ;           1259    28445    cisterna_id_seq1    SEQUENCE     z   CREATE SEQUENCE gestion.cisterna_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE gestion.cisterna_id_seq1;
       gestion       postgres    false    316    14            �           0    0    cisterna_id_seq1    SEQUENCE OWNED BY     F   ALTER SEQUENCE gestion.cisterna_id_seq1 OWNED BY gestion.cisterna.id;
            gestion       postgres    false    315            >           1259    28458 	   conductor    TABLE       CREATE TABLE gestion.conductor (
    id bigint NOT NULL,
    nro_licencia character varying(50) NOT NULL,
    nombre character varying(50) NOT NULL,
    apellidos character varying(50),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.conductor;
       gestion         postgres    false    14                       1259    25938    conductor_id_seq    SEQUENCE     z   CREATE SEQUENCE gestion.conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE gestion.conductor_id_seq;
       gestion       postgres    false    14            =           1259    28456    conductor_id_seq1    SEQUENCE     {   CREATE SEQUENCE gestion.conductor_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE gestion.conductor_id_seq1;
       gestion       postgres    false    14    318            �           0    0    conductor_id_seq1    SEQUENCE OWNED BY     H   ALTER SEQUENCE gestion.conductor_id_seq1 OWNED BY gestion.conductor.id;
            gestion       postgres    false    317            @           1259    28469    empresa    TABLE     F  CREATE TABLE gestion.empresa (
    id bigint NOT NULL,
    razon_social character varying(250) NOT NULL,
    pais character varying(20),
    es_asociacion boolean NOT NULL,
    tipo_documento character varying(20),
    numero_documento character varying(50),
    tipo_constitucion character varying(20),
    email character varying(500),
    direccion character varying(500),
    sitio_web character varying(50),
    telefono character varying(500),
    representante_legal character varying(500),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.empresa;
       gestion         postgres    false    14                       1259    25942    empresa_asociacion_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_asociacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE gestion.empresa_asociacion_id_seq;
       gestion       postgres    false    14            B           1259    28482    empresa_cisterna    TABLE     �  CREATE TABLE gestion.empresa_cisterna (
    id bigint NOT NULL,
    id_empresa bigint,
    id_cisterna bigint,
    motivo character varying(20),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 %   DROP TABLE gestion.empresa_cisterna;
       gestion         postgres    false    14            A           1259    28480    empresa_cisterna_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE gestion.empresa_cisterna_id_seq;
       gestion       postgres    false    322    14            �           0    0    empresa_cisterna_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE gestion.empresa_cisterna_id_seq OWNED BY gestion.empresa_cisterna.id;
            gestion       postgres    false    321            D           1259    28505    empresa_conductor    TABLE     �  CREATE TABLE gestion.empresa_conductor (
    id bigint NOT NULL,
    id_empresa bigint,
    id_conductor bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 &   DROP TABLE gestion.empresa_conductor;
       gestion         postgres    false    14            C           1259    28503    empresa_conductor_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE gestion.empresa_conductor_id_seq;
       gestion       postgres    false    14    324            �           0    0    empresa_conductor_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE gestion.empresa_conductor_id_seq OWNED BY gestion.empresa_conductor.id;
            gestion       postgres    false    323                       1259    25930    empresa_id_seq    SEQUENCE     x   CREATE SEQUENCE gestion.empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE gestion.empresa_id_seq;
       gestion       postgres    false    14            ?           1259    28467    empresa_id_seq1    SEQUENCE     y   CREATE SEQUENCE gestion.empresa_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE gestion.empresa_id_seq1;
       gestion       postgres    false    14    320            �           0    0    empresa_id_seq1    SEQUENCE OWNED BY     D   ALTER SEQUENCE gestion.empresa_id_seq1 OWNED BY gestion.empresa.id;
            gestion       postgres    false    319                       1259    25940 "   empresa_representante_legal_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_representante_legal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 :   DROP SEQUENCE gestion.empresa_representante_legal_id_seq;
       gestion       postgres    false    14                       1259    26812    lote    TABLE     �  CREATE TABLE gestion.lote (
    id bigint DEFAULT nextval(('gestion.lote_id_seq'::text)::regclass) NOT NULL,
    tipo_lote character varying(20) NOT NULL,
    lote integer NOT NULL,
    tipo_producto character varying(20),
    sector character varying(20),
    volumen_maximo numeric,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50) NOT NULL,
    hash character(70),
    gestion integer
);
    DROP TABLE gestion.lote;
       gestion         postgres    false    14            !           1259    26826    lote_id_seq    SEQUENCE     u   CREATE SEQUENCE gestion.lote_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE gestion.lote_id_seq;
       gestion       postgres    false    14                       1259    19046    lote_ruta_id_seq    SEQUENCE     z   CREATE SEQUENCE gestion.lote_ruta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE gestion.lote_ruta_id_seq;
       gestion       postgres    false    14                       1259    26775    lote_tramo_id_seq    SEQUENCE     {   CREATE SEQUENCE gestion.lote_tramo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE gestion.lote_tramo_id_seq;
       gestion       postgres    false    14                       1259    25932    representante_legal_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.representante_legal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE gestion.representante_legal_id_seq;
       gestion       postgres    false    14                       1259    19040    ruta_id_seq    SEQUENCE     u   CREATE SEQUENCE gestion.ruta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE gestion.ruta_id_seq;
       gestion       postgres    false    14                        1259    26819    tramo    TABLE     �  CREATE TABLE gestion.tramo (
    id bigint DEFAULT nextval(('gestion.tramo_id_seq'::text)::regclass) NOT NULL,
    id_lote bigint NOT NULL,
    es_principal boolean NOT NULL,
    punto_recepcion character varying(1000) NOT NULL,
    frontera character varying(1000) NOT NULL,
    punto_destino character varying(1000) NOT NULL,
    tarifa numeric(20,4) NOT NULL,
    tiempo_estimado numeric,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.tramo;
       gestion         postgres    false    14            "           1259    26828    tramo_id_seq    SEQUENCE     v   CREATE SEQUENCE gestion.tramo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE gestion.tramo_id_seq;
       gestion       postgres    false    14            -           1259    28166    asociacion_empresa    TABLE       CREATE TABLE "gestionV2".asociacion_empresa (
    id bigint DEFAULT nextval(('gestionv2.asociacion_empresa_id_seq'::text)::regclass) NOT NULL,
    id_empresa bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70),
    id_asociacion bigint
);
 +   DROP TABLE "gestionV2".asociacion_empresa;
    	   gestionV2         postgres    false    13            3           1259    28188    asociacion_empresa_id_seq    SEQUENCE     �   CREATE SEQUENCE "gestionV2".asociacion_empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE "gestionV2".asociacion_empresa_id_seq;
    	   gestionV2       postgres    false    13            *           1259    28145    cisterna    TABLE     �  CREATE TABLE "gestionV2".cisterna (
    id bigint DEFAULT nextval(('gestionv2.cisterna_id_seq'::text)::regclass) NOT NULL,
    placa character varying(25) NOT NULL,
    anio integer,
    marca character varying(50),
    color character varying(50),
    capacidad integer,
    nro_compartimientos integer,
    nro_chasis character varying(50),
    disposicion_ejes character varying(50),
    fecha_inicio timestamp without time zone NOT NULL,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 !   DROP TABLE "gestionV2".cisterna;
    	   gestionV2         postgres    false    13            0           1259    28182    cisterna_id_seq    SEQUENCE     }   CREATE SEQUENCE "gestionV2".cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE "gestionV2".cisterna_id_seq;
    	   gestionV2       postgres    false    13            +           1259    28152 	   conductor    TABLE     d  CREATE TABLE "gestionV2".conductor (
    id bigint DEFAULT nextval(('gestionv2.conductor_id_seq'::text)::regclass) NOT NULL,
    nro_licencia character varying(50) NOT NULL,
    nombre character varying(50) NOT NULL,
    apellidos character varying(50) NOT NULL,
    fecha_inicio timestamp without time zone NOT NULL,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 "   DROP TABLE "gestionV2".conductor;
    	   gestionV2         postgres    false    13            1           1259    28184    conductor_id_seq    SEQUENCE     ~   CREATE SEQUENCE "gestionV2".conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE "gestionV2".conductor_id_seq;
    	   gestionV2       postgres    false    13            )           1259    28138    empresa    TABLE     �  CREATE TABLE "gestionV2".empresa (
    id bigint DEFAULT nextval(('gestionv2.empresa_id_seq'::text)::regclass) NOT NULL,
    razon_social character varying(250) NOT NULL,
    pais character varying(20) NOT NULL,
    es_asociacion boolean NOT NULL,
    tipo_documento character varying(20) NOT NULL,
    numero_documento character varying(50) NOT NULL,
    tipo_constitucion character varying(20) NOT NULL,
    email character varying(500),
    direccion character varying(500) NOT NULL,
    sitio_web character varying(50),
    telefono character varying(500) NOT NULL,
    representante_legal character varying(500),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
     DROP TABLE "gestionV2".empresa;
    	   gestionV2         postgres    false    13            ,           1259    28159    empresa_cisterna    TABLE     0  CREATE TABLE "gestionV2".empresa_cisterna (
    id bigint DEFAULT nextval(('gestionv2.empresa_cisterna_id_seq'::text)::regclass) NOT NULL,
    id_empresa bigint,
    id_cisterna bigint,
    motivo character varying(20),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 )   DROP TABLE "gestionV2".empresa_cisterna;
    	   gestionV2         postgres    false    13            2           1259    28186    empresa_cisterna_id_seq    SEQUENCE     �   CREATE SEQUENCE "gestionV2".empresa_cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE "gestionV2".empresa_cisterna_id_seq;
    	   gestionV2       postgres    false    13            .           1259    28173    empresa_conductor    TABLE       CREATE TABLE "gestionV2".empresa_conductor (
    id bigint DEFAULT nextval(('gestionv2.empresa_conductor_id_seq'::text)::regclass) NOT NULL,
    id_empresa bigint,
    id_conductor bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 *   DROP TABLE "gestionV2".empresa_conductor;
    	   gestionV2         postgres    false    13            4           1259    28190    empresa_conductor_id_seq    SEQUENCE     �   CREATE SEQUENCE "gestionV2".empresa_conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE "gestionV2".empresa_conductor_id_seq;
    	   gestionV2       postgres    false    13            /           1259    28180    empresa_id_seq    SEQUENCE     |   CREATE SEQUENCE "gestionV2".empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE "gestionV2".empresa_id_seq;
    	   gestionV2       postgres    false    13            6           1259    28386    lote    TABLE     Y  CREATE TABLE "gestionV2".lote (
    id bigint NOT NULL,
    tipo_lote character varying(20) NOT NULL,
    lote integer NOT NULL,
    tipo_producto character varying(20),
    sector character varying(20),
    volumen_maximo numeric,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50) NOT NULL,
    hash character(70),
    gestion integer
);
    DROP TABLE "gestionV2".lote;
    	   gestionV2         postgres    false    13            5           1259    28384    lote_id_seq    SEQUENCE     y   CREATE SEQUENCE "gestionV2".lote_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE "gestionV2".lote_id_seq;
    	   gestionV2       postgres    false    13    310            �           0    0    lote_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE "gestionV2".lote_id_seq OWNED BY "gestionV2".lote.id;
         	   gestionV2       postgres    false    309            8           1259    28415    tramo    TABLE     F  CREATE TABLE "gestionV2".tramo (
    id bigint NOT NULL,
    id_lote bigint NOT NULL,
    es_principal boolean NOT NULL,
    punto_recepcion character varying(1000) NOT NULL,
    frontera character varying(1000) NOT NULL,
    punto_destino character varying(1000) NOT NULL,
    tarifa numeric NOT NULL,
    tiempo_estimado numeric,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE "gestionV2".tramo;
    	   gestionV2         postgres    false    13            7           1259    28413    tramo_id_seq    SEQUENCE     z   CREATE SEQUENCE "gestionV2".tramo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE "gestionV2".tramo_id_seq;
    	   gestionV2       postgres    false    13    312            �           0    0    tramo_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE "gestionV2".tramo_id_seq OWNED BY "gestionV2".tramo.id;
         	   gestionV2       postgres    false    311                       1259    25374 	   parametro    TABLE     d  CREATE TABLE parametro.parametro (
    id bigint DEFAULT nextval(('parametro.parametro_id_seq'::text)::regclass) NOT NULL,
    contexto character varying(50) NOT NULL,
    codigo character varying(20) NOT NULL,
    valor character varying(50) NOT NULL,
    descripcion character varying(500) NOT NULL,
    fecha_inicio date NOT NULL,
    fecha_fin date
);
     DROP TABLE parametro.parametro;
    	   parametro         postgres    false    11                       1259    24609    parametroVjson    TABLE     �   CREATE TABLE parametro."parametroVjson" (
    id bigint NOT NULL,
    parametro character varying(50) NOT NULL,
    valor jsonb NOT NULL,
    estado character varying(2)
);
 '   DROP TABLE parametro."parametroVjson";
    	   parametro         postgres    false    11            �           0    0    TABLE "parametroVjson"    ACL     A   GRANT SELECT ON TABLE parametro."parametroVjson" TO grupo_fenix;
         	   parametro       postgres    false    272                       1259    25381    parametro_id_seq    SEQUENCE     |   CREATE SEQUENCE parametro.parametro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE parametro.parametro_id_seq;
    	   parametro       postgres    false    11            �           2604    28438    asociacion_empresa id    DEFAULT     �   ALTER TABLE ONLY gestion.asociacion_empresa ALTER COLUMN id SET DEFAULT nextval('gestion.asociacion_empresa_id_seq'::regclass);
 E   ALTER TABLE gestion.asociacion_empresa ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    314    313    314            �           2604    28450    cisterna id    DEFAULT     m   ALTER TABLE ONLY gestion.cisterna ALTER COLUMN id SET DEFAULT nextval('gestion.cisterna_id_seq1'::regclass);
 ;   ALTER TABLE gestion.cisterna ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    316    315    316            �           2604    28461    conductor id    DEFAULT     o   ALTER TABLE ONLY gestion.conductor ALTER COLUMN id SET DEFAULT nextval('gestion.conductor_id_seq1'::regclass);
 <   ALTER TABLE gestion.conductor ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    317    318    318            �           2604    28472 
   empresa id    DEFAULT     k   ALTER TABLE ONLY gestion.empresa ALTER COLUMN id SET DEFAULT nextval('gestion.empresa_id_seq1'::regclass);
 :   ALTER TABLE gestion.empresa ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    319    320    320            �           2604    28485    empresa_cisterna id    DEFAULT     |   ALTER TABLE ONLY gestion.empresa_cisterna ALTER COLUMN id SET DEFAULT nextval('gestion.empresa_cisterna_id_seq'::regclass);
 C   ALTER TABLE gestion.empresa_cisterna ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    322    321    322            �           2604    28508    empresa_conductor id    DEFAULT     ~   ALTER TABLE ONLY gestion.empresa_conductor ALTER COLUMN id SET DEFAULT nextval('gestion.empresa_conductor_id_seq'::regclass);
 D   ALTER TABLE gestion.empresa_conductor ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    323    324    324            �           2604    28389    lote id    DEFAULT     l   ALTER TABLE ONLY "gestionV2".lote ALTER COLUMN id SET DEFAULT nextval('"gestionV2".lote_id_seq'::regclass);
 ;   ALTER TABLE "gestionV2".lote ALTER COLUMN id DROP DEFAULT;
    	   gestionV2       postgres    false    309    310    310            �           2604    28418    tramo id    DEFAULT     n   ALTER TABLE ONLY "gestionV2".tramo ALTER COLUMN id SET DEFAULT nextval('"gestionV2".tramo_id_seq'::regclass);
 <   ALTER TABLE "gestionV2".tramo ALTER COLUMN id DROP DEFAULT;
    	   gestionV2       postgres    false    311    312    312            �          0    27529    certificacion 
   TABLE DATA                     certificacion       postgres    false    294            �          0    27521    partida 
   TABLE DATA                     certificacion       postgres    false    293                      0    27044    proceso_contratacion 
   TABLE DATA                     certificacion       postgres    false    291            m          0    24618    contrato 
   TABLE DATA                     contrato       postgres    false    273            n          0    24625    volumen_ejecutado 
   TABLE DATA                     contrato       postgres    false    274            �          0    28435    asociacion_empresa 
   TABLE DATA                     gestion       postgres    false    314            �          0    28447    cisterna 
   TABLE DATA                     gestion       postgres    false    316            �          0    28458 	   conductor 
   TABLE DATA                     gestion       postgres    false    318            �          0    28469    empresa 
   TABLE DATA                     gestion       postgres    false    320            �          0    28482    empresa_cisterna 
   TABLE DATA                     gestion       postgres    false    322            �          0    28505    empresa_conductor 
   TABLE DATA                     gestion       postgres    false    324            {          0    26812    lote 
   TABLE DATA                     gestion       postgres    false    287            |          0    26819    tramo 
   TABLE DATA                     gestion       postgres    false    288            �          0    28166    asociacion_empresa 
   TABLE DATA                  	   gestionV2       postgres    false    301            �          0    28145    cisterna 
   TABLE DATA                  	   gestionV2       postgres    false    298            �          0    28152 	   conductor 
   TABLE DATA                  	   gestionV2       postgres    false    299            �          0    28138    empresa 
   TABLE DATA                  	   gestionV2       postgres    false    297            �          0    28159    empresa_cisterna 
   TABLE DATA                  	   gestionV2       postgres    false    300            �          0    28173    empresa_conductor 
   TABLE DATA                  	   gestionV2       postgres    false    302            �          0    28386    lote 
   TABLE DATA                  	   gestionV2       postgres    false    310            �          0    28415    tramo 
   TABLE DATA                  	   gestionV2       postgres    false    312            q          0    25374 	   parametro 
   TABLE DATA                  	   parametro       postgres    false    277            l          0    24609    parametroVjson 
   TABLE DATA                  	   parametro       postgres    false    272            �           0    0 #   certificacion_presupuestaria_id_seq    SEQUENCE SET     Z   SELECT pg_catalog.setval('certificacion.certificacion_presupuestaria_id_seq', 125, true);
            certificacion       postgres    false    296            �           0    0    partida_presupuestaria_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('certificacion.partida_presupuestaria_id_seq', 22, true);
            certificacion       postgres    false    295            �           0    0    proceso_contratacion_id_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('certificacion.proceso_contratacion_id_seq', 20, true);
            certificacion       postgres    false    292            �           0    0    contrato_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('contrato.contrato_id_seq', 222, true);
            contrato       postgres    false    275            �           0    0    movimiento_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('contrato.movimiento_id_seq', 9, true);
            contrato       postgres    false    276            �           0    0    asociacion_empresa_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('gestion.asociacion_empresa_id_seq', 1, false);
            gestion       postgres    false    313            �           0    0    asociacion_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('gestion.asociacion_id_seq', 2, true);
            gestion       postgres    false    281            �           0    0    cisterna_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('gestion.cisterna_id_seq', 105, true);
            gestion       postgres    false    282            �           0    0    cisterna_id_seq1    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.cisterna_id_seq1', 3, true);
            gestion       postgres    false    315            �           0    0    conductor_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.conductor_id_seq', 6, true);
            gestion       postgres    false    283            �           0    0    conductor_id_seq1    SEQUENCE SET     A   SELECT pg_catalog.setval('gestion.conductor_id_seq1', 1, false);
            gestion       postgres    false    317            �           0    0    empresa_asociacion_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('gestion.empresa_asociacion_id_seq', 4, true);
            gestion       postgres    false    285            �           0    0    empresa_cisterna_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('gestion.empresa_cisterna_id_seq', 1, true);
            gestion       postgres    false    321            �           0    0    empresa_conductor_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('gestion.empresa_conductor_id_seq', 1, false);
            gestion       postgres    false    323            �           0    0    empresa_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.empresa_id_seq', 153, true);
            gestion       postgres    false    279            �           0    0    empresa_id_seq1    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.empresa_id_seq1', 25, true);
            gestion       postgres    false    319            �           0    0 "   empresa_representante_legal_id_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('gestion.empresa_representante_legal_id_seq', 1, false);
            gestion       postgres    false    284            �           0    0    lote_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('gestion.lote_id_seq', 42, true);
            gestion       postgres    false    289            �           0    0    lote_ruta_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('gestion.lote_ruta_id_seq', 1, false);
            gestion       postgres    false    271            �           0    0    lote_tramo_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('gestion.lote_tramo_id_seq', 203, true);
            gestion       postgres    false    286            �           0    0    representante_legal_id_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('gestion.representante_legal_id_seq', 1, false);
            gestion       postgres    false    280            �           0    0    ruta_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('gestion.ruta_id_seq', 1, false);
            gestion       postgres    false    270            �           0    0    tramo_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('gestion.tramo_id_seq', 980, true);
            gestion       postgres    false    290            �           0    0    asociacion_empresa_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('"gestionV2".asociacion_empresa_id_seq', 6, true);
         	   gestionV2       postgres    false    307            �           0    0    cisterna_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('"gestionV2".cisterna_id_seq', 4, true);
         	   gestionV2       postgres    false    304            �           0    0    conductor_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('"gestionV2".conductor_id_seq', 1, false);
         	   gestionV2       postgres    false    305            �           0    0    empresa_cisterna_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('"gestionV2".empresa_cisterna_id_seq', 10, true);
         	   gestionV2       postgres    false    306            �           0    0    empresa_conductor_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('"gestionV2".empresa_conductor_id_seq', 1, false);
         	   gestionV2       postgres    false    308            �           0    0    empresa_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('"gestionV2".empresa_id_seq', 9, true);
         	   gestionV2       postgres    false    303            �           0    0    lote_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('"gestionV2".lote_id_seq', 1, false);
         	   gestionV2       postgres    false    309            �           0    0    tramo_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('"gestionV2".tramo_id_seq', 1, false);
         	   gestionV2       postgres    false    311            �           0    0    parametro_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('parametro.parametro_id_seq', 176, true);
         	   parametro       postgres    false    278            �           2606    27549 8   certificacion PK_CER_trs_certificacion_presupuestaria_v2 
   CONSTRAINT        ALTER TABLE ONLY certificacion.certificacion
    ADD CONSTRAINT "PK_CER_trs_certificacion_presupuestaria_v2" PRIMARY KEY (id);
 k   ALTER TABLE ONLY certificacion.certificacion DROP CONSTRAINT "PK_CER_trs_certificacion_presupuestaria_v2";
       certificacion         postgres    false    294            �           2606    27545 ,   partida PK_CER_trs_partida_presupuestaria_v2 
   CONSTRAINT     s   ALTER TABLE ONLY certificacion.partida
    ADD CONSTRAINT "PK_CER_trs_partida_presupuestaria_v2" PRIMARY KEY (id);
 _   ALTER TABLE ONLY certificacion.partida DROP CONSTRAINT "PK_CER_trs_partida_presupuestaria_v2";
       certificacion         postgres    false    293            �           2606    27054 ,   proceso_contratacion PK_PROCESO_CONTRATACION 
   CONSTRAINT     s   ALTER TABLE ONLY certificacion.proceso_contratacion
    ADD CONSTRAINT "PK_PROCESO_CONTRATACION" PRIMARY KEY (id);
 _   ALTER TABLE ONLY certificacion.proceso_contratacion DROP CONSTRAINT "PK_PROCESO_CONTRATACION";
       certificacion         postgres    false    291            �           2606    24637    contrato PK_CON.TRS_CONTRATO 
   CONSTRAINT     ^   ALTER TABLE ONLY contrato.contrato
    ADD CONSTRAINT "PK_CON.TRS_CONTRATO" PRIMARY KEY (id);
 J   ALTER TABLE ONLY contrato.contrato DROP CONSTRAINT "PK_CON.TRS_CONTRATO";
       contrato         postgres    false    273            �           2606    24639    volumen_ejecutado PK_MOVIMIENTO 
   CONSTRAINT     a   ALTER TABLE ONLY contrato.volumen_ejecutado
    ADD CONSTRAINT "PK_MOVIMIENTO" PRIMARY KEY (id);
 M   ALTER TABLE ONLY contrato.volumen_ejecutado DROP CONSTRAINT "PK_MOVIMIENTO";
       contrato         postgres    false    274            �           2606    28443 (   asociacion_empresa PK_ASOCIACION_EMPRESA 
   CONSTRAINT     i   ALTER TABLE ONLY gestion.asociacion_empresa
    ADD CONSTRAINT "PK_ASOCIACION_EMPRESA" PRIMARY KEY (id);
 U   ALTER TABLE ONLY gestion.asociacion_empresa DROP CONSTRAINT "PK_ASOCIACION_EMPRESA";
       gestion         postgres    false    314            �           2606    28455    cisterna PK_CISTERNA 
   CONSTRAINT     U   ALTER TABLE ONLY gestion.cisterna
    ADD CONSTRAINT "PK_CISTERNA" PRIMARY KEY (id);
 A   ALTER TABLE ONLY gestion.cisterna DROP CONSTRAINT "PK_CISTERNA";
       gestion         postgres    false    316            �           2606    28466    conductor PK_CONDUCTOR 
   CONSTRAINT     W   ALTER TABLE ONLY gestion.conductor
    ADD CONSTRAINT "PK_CONDUCTOR" PRIMARY KEY (id);
 C   ALTER TABLE ONLY gestion.conductor DROP CONSTRAINT "PK_CONDUCTOR";
       gestion         postgres    false    318            �           2606    28477    empresa PK_EMPRESA 
   CONSTRAINT     S   ALTER TABLE ONLY gestion.empresa
    ADD CONSTRAINT "PK_EMPRESA" PRIMARY KEY (id);
 ?   ALTER TABLE ONLY gestion.empresa DROP CONSTRAINT "PK_EMPRESA";
       gestion         postgres    false    320            �           2606    28490 $   empresa_cisterna PK_EMPRESA_CISTERNA 
   CONSTRAINT     e   ALTER TABLE ONLY gestion.empresa_cisterna
    ADD CONSTRAINT "PK_EMPRESA_CISTERNA" PRIMARY KEY (id);
 Q   ALTER TABLE ONLY gestion.empresa_cisterna DROP CONSTRAINT "PK_EMPRESA_CISTERNA";
       gestion         postgres    false    322            �           2606    28513 &   empresa_conductor PK_EMPRESA_CONDUCTOR 
   CONSTRAINT     g   ALTER TABLE ONLY gestion.empresa_conductor
    ADD CONSTRAINT "PK_EMPRESA_CONDUCTOR" PRIMARY KEY (id);
 S   ALTER TABLE ONLY gestion.empresa_conductor DROP CONSTRAINT "PK_EMPRESA_CONDUCTOR";
       gestion         postgres    false    324            �           2606    26831    lote PK_GES.TRS_LOTE 
   CONSTRAINT     U   ALTER TABLE ONLY gestion.lote
    ADD CONSTRAINT "PK_GES.TRS_LOTE" PRIMARY KEY (id);
 A   ALTER TABLE ONLY gestion.lote DROP CONSTRAINT "PK_GES.TRS_LOTE";
       gestion         postgres    false    287            �           2606    26835    tramo PK_GES.TRS_TRAMO 
   CONSTRAINT     W   ALTER TABLE ONLY gestion.tramo
    ADD CONSTRAINT "PK_GES.TRS_TRAMO" PRIMARY KEY (id);
 C   ALTER TABLE ONLY gestion.tramo DROP CONSTRAINT "PK_GES.TRS_TRAMO";
       gestion         postgres    false    288            �           2606    28479 $   empresa UN_EMPRESA_RAZON_SOCIAL_PAIS 
   CONSTRAINT     p   ALTER TABLE ONLY gestion.empresa
    ADD CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS" UNIQUE (razon_social, pais);
 Q   ALTER TABLE ONLY gestion.empresa DROP CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS";
       gestion         postgres    false    320    320            �           2606    30858    tramo UN_GESTION_TRAMO 
   CONSTRAINT     �   ALTER TABLE ONLY gestion.tramo
    ADD CONSTRAINT "UN_GESTION_TRAMO" UNIQUE (punto_recepcion, frontera, punto_destino, tarifa, id_lote, es_principal);
 C   ALTER TABLE ONLY gestion.tramo DROP CONSTRAINT "UN_GESTION_TRAMO";
       gestion         postgres    false    288    288    288    288    288    288            �           2606    28207 (   asociacion_empresa PK_ASOCIACION_EMPRESA 
   CONSTRAINT     m   ALTER TABLE ONLY "gestionV2".asociacion_empresa
    ADD CONSTRAINT "PK_ASOCIACION_EMPRESA" PRIMARY KEY (id);
 Y   ALTER TABLE ONLY "gestionV2".asociacion_empresa DROP CONSTRAINT "PK_ASOCIACION_EMPRESA";
    	   gestionV2         postgres    false    301            �           2606    28197    cisterna PK_CISTERNA 
   CONSTRAINT     Y   ALTER TABLE ONLY "gestionV2".cisterna
    ADD CONSTRAINT "PK_CISTERNA" PRIMARY KEY (id);
 E   ALTER TABLE ONLY "gestionV2".cisterna DROP CONSTRAINT "PK_CISTERNA";
    	   gestionV2         postgres    false    298            �           2606    28200    conductor PK_CONDUCTOR 
   CONSTRAINT     [   ALTER TABLE ONLY "gestionV2".conductor
    ADD CONSTRAINT "PK_CONDUCTOR" PRIMARY KEY (id);
 G   ALTER TABLE ONLY "gestionV2".conductor DROP CONSTRAINT "PK_CONDUCTOR";
    	   gestionV2         postgres    false    299            �           2606    28193    empresa PK_EMPRESA 
   CONSTRAINT     W   ALTER TABLE ONLY "gestionV2".empresa
    ADD CONSTRAINT "PK_EMPRESA" PRIMARY KEY (id);
 C   ALTER TABLE ONLY "gestionV2".empresa DROP CONSTRAINT "PK_EMPRESA";
    	   gestionV2         postgres    false    297            �           2606    28203 $   empresa_cisterna PK_EMPRESA_CISTERNA 
   CONSTRAINT     i   ALTER TABLE ONLY "gestionV2".empresa_cisterna
    ADD CONSTRAINT "PK_EMPRESA_CISTERNA" PRIMARY KEY (id);
 U   ALTER TABLE ONLY "gestionV2".empresa_cisterna DROP CONSTRAINT "PK_EMPRESA_CISTERNA";
    	   gestionV2         postgres    false    300            �           2606    28210 &   empresa_conductor PK_EMPRESA_CONDUCTOR 
   CONSTRAINT     k   ALTER TABLE ONLY "gestionV2".empresa_conductor
    ADD CONSTRAINT "PK_EMPRESA_CONDUCTOR" PRIMARY KEY (id);
 W   ALTER TABLE ONLY "gestionV2".empresa_conductor DROP CONSTRAINT "PK_EMPRESA_CONDUCTOR";
    	   gestionV2         postgres    false    302            �           2606    28394    lote PK_GES.TRS_LOTE 
   CONSTRAINT     Y   ALTER TABLE ONLY "gestionV2".lote
    ADD CONSTRAINT "PK_GES.TRS_LOTE" PRIMARY KEY (id);
 E   ALTER TABLE ONLY "gestionV2".lote DROP CONSTRAINT "PK_GES.TRS_LOTE";
    	   gestionV2         postgres    false    310            �           2606    28423    tramo PK_GES.TRS_TRAMO 
   CONSTRAINT     [   ALTER TABLE ONLY "gestionV2".tramo
    ADD CONSTRAINT "PK_GES.TRS_TRAMO" PRIMARY KEY (id);
 G   ALTER TABLE ONLY "gestionV2".tramo DROP CONSTRAINT "PK_GES.TRS_TRAMO";
    	   gestionV2         postgres    false    312            �           2606    28195 $   empresa UN_EMPRESA_RAZON_SOCIAL_PAIS 
   CONSTRAINT     t   ALTER TABLE ONLY "gestionV2".empresa
    ADD CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS" UNIQUE (razon_social, pais);
 U   ALTER TABLE ONLY "gestionV2".empresa DROP CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS";
    	   gestionV2         postgres    false    297    297            �           2606    28425    tramo UN_GESTION_TRAMO 
   CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".tramo
    ADD CONSTRAINT "UN_GESTION_TRAMO" UNIQUE (punto_recepcion, frontera, punto_destino, tarifa, id_lote, es_principal);
 G   ALTER TABLE ONLY "gestionV2".tramo DROP CONSTRAINT "UN_GESTION_TRAMO";
    	   gestionV2         postgres    false    312    312    312    312    312    312            �           2606    25384    parametro PK_Parametro 
   CONSTRAINT     Y   ALTER TABLE ONLY parametro.parametro
    ADD CONSTRAINT "PK_Parametro" PRIMARY KEY (id);
 E   ALTER TABLE ONLY parametro.parametro DROP CONSTRAINT "PK_Parametro";
    	   parametro         postgres    false    277            �           1259    27553 .   IXFK_CERTIFICACIONCERTIFICACION_PRESUPUESTARIA    INDEX     }   CREATE INDEX "IXFK_CERTIFICACIONCERTIFICACION_PRESUPUESTARIA" ON certificacion.certificacion USING btree (id_certificacion);
 K   DROP INDEX certificacion."IXFK_CERTIFICACIONCERTIFICACION_PRESUPUESTARIA";
       certificacion         postgres    false    294            �           1259    27554 '   IXFK_CERTIFICACION_PROCESO_CONTRATACION    INDEX     }   CREATE INDEX "IXFK_CERTIFICACION_PROCESO_CONTRATACION" ON certificacion.certificacion USING btree (id_proceso_contratacion);
 D   DROP INDEX certificacion."IXFK_CERTIFICACION_PROCESO_CONTRATACION";
       certificacion         postgres    false    294            �           1259    27552 <   IXFK_CER_trs_certificacion_CER_trs_partida_presupuestaria_v2    INDEX     �   CREATE INDEX "IXFK_CER_trs_certificacion_CER_trs_partida_presupuestaria_v2" ON certificacion.certificacion USING btree (id_partida);
 Y   DROP INDEX certificacion."IXFK_CER_trs_certificacion_CER_trs_partida_presupuestaria_v2";
       certificacion         postgres    false    294            �           1259    24640 (   IXFK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO    INDEX     q   CREATE INDEX "IXFK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO" ON contrato.volumen_ejecutado USING btree (id_contrato);
 @   DROP INDEX contrato."IXFK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO";
       contrato         postgres    false    274            �           1259    28444    IXFK_ASOCIACION_EMPRESA_EMPRESA    INDEX     g   CREATE INDEX "IXFK_ASOCIACION_EMPRESA_EMPRESA" ON gestion.asociacion_empresa USING btree (id_empresa);
 6   DROP INDEX gestion."IXFK_ASOCIACION_EMPRESA_EMPRESA";
       gestion         postgres    false    314            �           1259    28501    IXFK_EMPRESA_CISTERNA_CISTERNA    INDEX     e   CREATE INDEX "IXFK_EMPRESA_CISTERNA_CISTERNA" ON gestion.empresa_cisterna USING btree (id_cisterna);
 5   DROP INDEX gestion."IXFK_EMPRESA_CISTERNA_CISTERNA";
       gestion         postgres    false    322            �           1259    28502    IXFK_EMPRESA_CISTERNA_EMPRESA    INDEX     c   CREATE INDEX "IXFK_EMPRESA_CISTERNA_EMPRESA" ON gestion.empresa_cisterna USING btree (id_empresa);
 4   DROP INDEX gestion."IXFK_EMPRESA_CISTERNA_EMPRESA";
       gestion         postgres    false    322            �           1259    28519     IXFK_EMPRESA_CONDUCTOR_CONDUCTOR    INDEX     i   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_CONDUCTOR" ON gestion.empresa_conductor USING btree (id_conductor);
 7   DROP INDEX gestion."IXFK_EMPRESA_CONDUCTOR_CONDUCTOR";
       gestion         postgres    false    324            �           1259    28520    IXFK_EMPRESA_CONDUCTOR_EMPRESA    INDEX     e   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_EMPRESA" ON gestion.empresa_conductor USING btree (id_empresa);
 5   DROP INDEX gestion."IXFK_EMPRESA_CONDUCTOR_EMPRESA";
       gestion         postgres    false    324            �           1259    26838    IXFK_TRAMO_LOTE    INDEX     G   CREATE INDEX "IXFK_TRAMO_LOTE" ON gestion.tramo USING btree (id_lote);
 &   DROP INDEX gestion."IXFK_TRAMO_LOTE";
       gestion         postgres    false    288            �           1259    28283    IXFK_ASOCIACION_EMPRESA_EMPRESA    INDEX     k   CREATE INDEX "IXFK_ASOCIACION_EMPRESA_EMPRESA" ON "gestionV2".asociacion_empresa USING btree (id_empresa);
 :   DROP INDEX "gestionV2"."IXFK_ASOCIACION_EMPRESA_EMPRESA";
    	   gestionV2         postgres    false    301            �           1259    28204    IXFK_EMPRESA_CISTERNA_CISTERNA    INDEX     i   CREATE INDEX "IXFK_EMPRESA_CISTERNA_CISTERNA" ON "gestionV2".empresa_cisterna USING btree (id_cisterna);
 9   DROP INDEX "gestionV2"."IXFK_EMPRESA_CISTERNA_CISTERNA";
    	   gestionV2         postgres    false    300            �           1259    28205    IXFK_EMPRESA_CISTERNA_EMPRESA    INDEX     g   CREATE INDEX "IXFK_EMPRESA_CISTERNA_EMPRESA" ON "gestionV2".empresa_cisterna USING btree (id_empresa);
 8   DROP INDEX "gestionV2"."IXFK_EMPRESA_CISTERNA_EMPRESA";
    	   gestionV2         postgres    false    300            �           1259    28211     IXFK_EMPRESA_CONDUCTOR_CONDUCTOR    INDEX     m   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_CONDUCTOR" ON "gestionV2".empresa_conductor USING btree (id_conductor);
 ;   DROP INDEX "gestionV2"."IXFK_EMPRESA_CONDUCTOR_CONDUCTOR";
    	   gestionV2         postgres    false    302            �           1259    28212    IXFK_EMPRESA_CONDUCTOR_EMPRESA    INDEX     i   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_EMPRESA" ON "gestionV2".empresa_conductor USING btree (id_empresa);
 9   DROP INDEX "gestionV2"."IXFK_EMPRESA_CONDUCTOR_EMPRESA";
    	   gestionV2         postgres    false    302            �           1259    28431    IXFK_TRAMO_LOTE    INDEX     K   CREATE INDEX "IXFK_TRAMO_LOTE" ON "gestionV2".tramo USING btree (id_lote);
 *   DROP INDEX "gestionV2"."IXFK_TRAMO_LOTE";
    	   gestionV2         postgres    false    312            �           2620    27582 =   certificacion trigger_actualizar_vm_certificaciones_dashboard    TRIGGER     �   CREATE TRIGGER trigger_actualizar_vm_certificaciones_dashboard AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON certificacion.certificacion FOR EACH STATEMENT EXECUTE PROCEDURE certificacion.actualizar_vm_certificaciones_dashboard();
 ]   DROP TRIGGER trigger_actualizar_vm_certificaciones_dashboard ON certificacion.certificacion;
       certificacion       postgres    false    294    345            �           2620    27581 0   partida trigger_actualizar_vm_partidas_dashboard    TRIGGER     �   CREATE TRIGGER trigger_actualizar_vm_partidas_dashboard AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON certificacion.partida FOR EACH STATEMENT EXECUTE PROCEDURE certificacion.actualizar_vm_partidas_dashboard();
 P   DROP TRIGGER trigger_actualizar_vm_partidas_dashboard ON certificacion.partida;
       certificacion       postgres    false    293    343            �           2620    29739     contrato actualizar_vm_contratos    TRIGGER     �   CREATE TRIGGER actualizar_vm_contratos AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON contrato.contrato FOR EACH STATEMENT EXECUTE PROCEDURE contrato.actualizar_vm_contratos();
 ;   DROP TRIGGER actualizar_vm_contratos ON contrato.contrato;
       contrato       postgres    false    349    273            �           2620    29740 )   volumen_ejecutado actualizar_vm_contratos    TRIGGER     �   CREATE TRIGGER actualizar_vm_contratos AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON contrato.volumen_ejecutado FOR EACH STATEMENT EXECUTE PROCEDURE contrato.actualizar_vm_contratos();
 D   DROP TRIGGER actualizar_vm_contratos ON contrato.volumen_ejecutado;
       contrato       postgres    false    274    349            �           2606    27560 J   certificacion FK_CERTIFICACION_PRESUPUESTARIA_CERTIFICACION_PRESUPUESTARIA    FK CONSTRAINT     �   ALTER TABLE ONLY certificacion.certificacion
    ADD CONSTRAINT "FK_CERTIFICACION_PRESUPUESTARIA_CERTIFICACION_PRESUPUESTARIA" FOREIGN KEY (id_certificacion) REFERENCES certificacion.certificacion(id);
 }   ALTER TABLE ONLY certificacion.certificacion DROP CONSTRAINT "FK_CERTIFICACION_PRESUPUESTARIA_CERTIFICACION_PRESUPUESTARIA";
       certificacion       postgres    false    294    2993    294            �           2606    27555 M   certificacion FK_CER_trs_certificacion_presupuestaria_CER_trs_partida_presupu    FK CONSTRAINT     �   ALTER TABLE ONLY certificacion.certificacion
    ADD CONSTRAINT "FK_CER_trs_certificacion_presupuestaria_CER_trs_partida_presupu" FOREIGN KEY (id_partida) REFERENCES certificacion.partida(id);
 �   ALTER TABLE ONLY certificacion.certificacion DROP CONSTRAINT "FK_CER_trs_certificacion_presupuestaria_CER_trs_partida_presupu";
       certificacion       postgres    false    293    294    2988            �           2606    24641 8   volumen_ejecutado FK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO    FK CONSTRAINT     �   ALTER TABLE ONLY contrato.volumen_ejecutado
    ADD CONSTRAINT "FK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO" FOREIGN KEY (id_contrato) REFERENCES contrato.contrato(id);
 f   ALTER TABLE ONLY contrato.volumen_ejecutado DROP CONSTRAINT "FK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO";
       contrato       postgres    false    2972    273    274            �           2606    28491 -   empresa_cisterna FK_EMPRESA_CISTERNA_CISTERNA    FK CONSTRAINT     �   ALTER TABLE ONLY gestion.empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA" FOREIGN KEY (id_cisterna) REFERENCES gestion.cisterna(id);
 Z   ALTER TABLE ONLY gestion.empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA";
       gestion       postgres    false    3024    322    316            �           2606    28496 ,   empresa_cisterna FK_EMPRESA_CISTERNA_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY gestion.empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES gestion.empresa(id);
 Y   ALTER TABLE ONLY gestion.empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA";
       gestion       postgres    false    3028    322    320            �           2606    28514 .   empresa_conductor FK_EMPRESA_CONDUCTOR_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY gestion.empresa_conductor
    ADD CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES gestion.empresa(id);
 [   ALTER TABLE ONLY gestion.empresa_conductor DROP CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA";
       gestion       postgres    false    324    3028    320            �           2606    26839    tramo FK_TRAMO_LOTE    FK CONSTRAINT     u   ALTER TABLE ONLY gestion.tramo
    ADD CONSTRAINT "FK_TRAMO_LOTE" FOREIGN KEY (id_lote) REFERENCES gestion.lote(id);
 @   ALTER TABLE ONLY gestion.tramo DROP CONSTRAINT "FK_TRAMO_LOTE";
       gestion       postgres    false    287    2979    288            �           2606    28213 -   empresa_cisterna FK_EMPRESA_CISTERNA_CISTERNA    FK CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA" FOREIGN KEY (id_cisterna) REFERENCES "gestionV2".cisterna(id);
 ^   ALTER TABLE ONLY "gestionV2".empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA";
    	   gestionV2       postgres    false    2999    298    300            �           2606    28218 ,   empresa_cisterna FK_EMPRESA_CISTERNA_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES "gestionV2".empresa(id);
 ]   ALTER TABLE ONLY "gestionV2".empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA";
    	   gestionV2       postgres    false    300    2995    297            �           2606    28223 .   empresa_conductor FK_EMPRESA_CONDUCTOR_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".empresa_conductor
    ADD CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES "gestionV2".empresa(id);
 _   ALTER TABLE ONLY "gestionV2".empresa_conductor DROP CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA";
    	   gestionV2       postgres    false    297    302    2995            �           2606    28426    tramo FK_TRAMO_LOTE    FK CONSTRAINT     }   ALTER TABLE ONLY "gestionV2".tramo
    ADD CONSTRAINT "FK_TRAMO_LOTE" FOREIGN KEY (id_lote) REFERENCES "gestionV2".lote(id);
 D   ALTER TABLE ONLY "gestionV2".tramo DROP CONSTRAINT "FK_TRAMO_LOTE";
    	   gestionV2       postgres    false    312    3014    310            �           0    34970    certificacion_dashboard    MATERIALIZED VIEW DATA     A   REFRESH MATERIALIZED VIEW certificacion.certificacion_dashboard;
            certificacion       postgres    false    327    3237            �           0    34784    partida_dashboard    MATERIALIZED VIEW DATA     ;   REFRESH MATERIALIZED VIEW certificacion.partida_dashboard;
            certificacion       postgres    false    326    3237            �           0    31104 	   contratos    MATERIALIZED VIEW DATA     .   REFRESH MATERIALIZED VIEW contrato.contratos;
            contrato       postgres    false    325    3237            �   �  x��]��0����s7�I[�k� n���ܛ�\ �,�H"�d���|8h�I����C����{N���[^�l'pQ4�l�E�Q���/�KO,}QI�A��YO1%���0��U&TB�J�����#�b(w�P������MV��H���L�c���(���W�<�Q4�.%_��}Ͼ������y_�
X����lYQK��#���ƴ���GJ����-��*(�FF5"�ې���[yt�9{�e�Dq.�$L����#	�i��lR���O~���NOl僌�[����h�f-{r�Պ�*7���H8^��A�y�gQ��e�:�!��Y�W������"��������u�rL�ۿR����HR(�n��3���y��[��%
D�y��;@#�^�/|�����i��g�4l�y@�5F��0��y�����@����n^ �i����(��U��I�7 t�t���C�� �]?xk��V��b����*�Ɋ�j�e��;P��U�ӱ�r�r��C���s�LGlѹ�V胢���R����G�~)7�^*a�KY?�+,B4H�[%�C�����]yu���xU�^��݋Ъ�j��m�K�v�tyL*�q^�`�In�%y?�\⯕4����y�w�Ŀ��zͱ      �   ?  x�ݕ�k�0���+�L�D��T�¦C�`���"�n���Yb��,���amC.�|��]�$���I��j��׫�*��s3�*��D��Ǘ(G.b���p-�K�P�c�)Hs��~�����ެ�g�7B�p�<2�*?vK��:��݃����B��=����zГ/�"��2(�d�Cc-��F��a�~1Ə�v.�_hC��}6vP9h��GbW �8mU��&�T=�ɟ�oi���I&հ!f�t�-x|z���)���4�#�ֺ\t�~�
 �h�:`r�X��D�����㷱g��̪Q�J�r/5��*��         �   x���=�0����lUPi�o�J�D��U$T�b����8ɹ$�cny��ȽR�Z"�����<\wq�x�ܧ�������<]���t��$Y�+�k��,/ʪ�_�c��0���Zٶ��Z���XѪ��0/����\Ar�M��Xz�ԛ.�l�@8���P �O��3H����Rh����3�/�WȡB鿁M/p��D���_�G���xXԿ�:��c��hX�}D��u��      m   �  x��]�n�8��)]�nc��k�D+j�@�\�H����Ua[�e��E`=�#��v$���(I'�F�(��q8�G�*�,?$�zd�M�x��Y�s�DV@^v;�-ҁ�v�E��.�|7��"ɍ��SuGpހ;'Yq��1���a:���]�9���ql�$�ǖ�p�l9]d��|)/;����?&�a�M�k��?ZD�I�z��ݧ���~���o}�]�)1=�<��+���=Ǳ�c��g��'��t���*Ϗn]h�YkC��c4)28�5��4��}F7�ݻ�,^=}A}�/��*Ad	'<\R�n���V'�nVN�򈎾.G�0��C��3�Q]ZsqS2�͓<.oKG��]�o�\Du�*�H��8�;^	���6G-��#�^e�Iy�$�B���_�|Q�Q� m��dV]7��O��d��+�V�
��V�e>����>h3ʗ�$��1[C/�����l�ϳ1�8M��]}�Gy<���B?_�,*%��J�Q��
�T�=��|��+��lf��J�B��A!�p照6��'&ɨܴ���S��ϴӀ�`T�i��ˉj��ͳ��]��(���-ׁ���0��d�a��a2_��7%� �c9[�0�&Wf�c�L1��G����~_���x�'p��qn�^����iGeL{����j]DPi�瑏�է��@}ۤ�v#&�{�@��>��.�a�c?�'�Фt(�*"���֙�8�k�Ty�1�hT+@��(GRj�h0�sh��^��"��"rm��!"`#h@�r?R�����YΐM��	)=>��[т����b��%i�� 0U�"=���4�%3�}}�'4U�4,`�h!)*��H	B�Rp�2�$�%������I���kp�nXK��Y�e�"x�����"x\�q@��8 ��'��Tk߆H{�5�Je\5��>]$ C�m�2H���l��B��Q���9<z�P���TAD���|~0cѮ���\8,���a8^�y��]z5�cP���C|�#'0�7��B�X�G����%�]�	����侠fd��n!�I}�H�3�L�<�=H=��'��)�T�|��R�q���a�\�,ǫC@�����RT�(@^=�@y4���e���IģJ�&1�<���1�A��!?�y�*��[C�D�7x���-�F(�{l0,5F�����XY�FUa���V,G2�L�R Õ�lkL��? `8GBaւ��SLr��R���ٜ6�f��С�Xi/
����pة/
�i��23i�c����MQ��2��*km( ���pV�H�=e�̀��	�g����th+��O*����{��F��*�����
j���m�s�Ĵ5���[���{�G.z�,g<?�v�i�.wV�G5R���aG��@�`��):d�OA��8S�f�G,V��a�f��5���ϭ��J�<4 k�`L��ј�R���ip֤�g�4x�\�n� �@$Te;�����O!��lR���Y;"
g� ��@�^�HkGl�X+%��,��1pل�`)�Ʀ�H��!5�b��#c��S�c��W[�P���p��r��� %EasȊzԀ�T�8�,�Z^�9����j7���I�t���g���Ь,��i(�Tk��E
����-�Qy��^������_�����x"��q��!�b9�#j̱��<�C�-@�z����m|�k~��/r�=�8v��>�AW� ��n{;h���v��>�vP���\f;�^�y&�?�`H���v�a�f��s{͂U�G͂ݺ�j�f�b;�Y���Y�ʒ͂�,X>@͂} �Y�2��)Y���,߇W��䕏���]��n�[����q[���G�m�N���Zw���HZZ�ɰ��,˞G��$��i�� ��j��8�U�����4�U�_��5�U~L5��wL5��!���W~�k����i�+�qh����i�k�m���W�X���bh������@��ʹB�_�V������������rj��濪���W��O�_5�u��5���}��)��� ���@�]��@����!�>1V쥰$�q���}�;���Sa;��~ǹ�F�;ι��;��w��s���Ĵ�GcG����l��i��9�`5TKλ!M�=��>$_Nҩ�p6���^���[�      n   
   x���          �   
   x���          �   
   x���          �   
   x���          �     x��X�s�@~�_qo�3M�;0j�������!��s��@3��{���4�ӕ��E�����U����ԁ�D���t:wX��x'} Bz�@G��p�wd����NC"��b�J@?&׆�����<E����i�/�W���3��Z���@v�I�1�lR_2��8���F���s������5���,�%�hG�K�]dy�"��hR���7ωo`��.� �7�ʠѹ��/^VC%oo�I�aœ0�4��tt�3�)�75�AjrF�����L��y�+R��_�.��ǧ�z	[�od�q��w
�Q0�0�C�4�~�Y�%�5�l��5�$�@F�� ��̵嬆�)����c�Z
{��
��ӷf�
���4p_X6�������+ :T�'�U6V��p`)(���,�Z�&r��
��e퀐YH���]卓�H���E��iͺ!ÀqH;���q�*d���b��S�p@0��_���Ү
��=(("5����C`覾��#��&La��´�yʓ�+ �qS G[P����Х���(�8`xQ����:$.�6%
c��ǳ�X��W�7I���!y�\%��r�O2��Ce����]k������BEX�c�U��8����v�xyX�
����W6�wL��)�U1c���s@�*IR�����B�kfR�#I��xO�`�<Y8�g0#.��0����狵o0Uf���@:i���il�̼������N��Z\���_I�R��Fn�t��� m��$�d�\�&gOҳ�?W#`      �   
   x���          �   
   x���          {   �   x���v
Q���WHO-.������/IUs�	uV�0�QPʺ�9:{��9���(��|<C=]���\uggOW�" ���X���FF���FF
V�F@�gajjaf
T���CHMZbNq*PQYrQiB�����@�hZsy�!���]CC+0��	�$�]o����$��@���h�\n2X\�� ���      |   W  x�ŝM���������Z���{�������)ɀ}��(�6����[�=��#�+�]���p8rſ㍡5��O�����g�����!)�C���ǟ������|��?%��1�'/H�^&�e���/?�Ln���d7͋C�������!���?�f�.�Լb���柗I�uCSJ^���	I_��u�WZ(�I�W���?~n������4ߗ��v������ �q�U}����!`���|�-Ѯ���e=����pg�	��ld�mz�5�0#�e<���l����+�۪��[��Rʀt<��l<-R���	��æ�˦Zﲓ�(@@	x�>fu��׏��'N�$�S�9=;P	^d=���@��/fmW���o�sPp������,ڠ��~KI�[��\�Pɸ��C�l��)�i�d���Ŗ��(��UL��*5`<1���N�7�������Vє���a�b��h���^p;���te�X258J�^ByN�$���I�Pm�]�9U��#7�1|g����q�!�+�3�HgfvVi�J�&�����لXX3q��U4�Ma2D�oCr/�8��E��z�%5�6q|g���Ce�ט/N��͞�`��(®�E';�M�]�);�2p;�4�ay�O{ͻ�D5� E�3\D �חEW\#�d^�Z�����.�hޔ3P<=��K��U����D�u�Sʬ���VP"�п=��������=sv��S�"i�6h��$�M���������6YS�W���!K^l����*��7Eي�Ms\���Y�}�-�����<T�m^ޅm,�	fYD~B<�L��_z��y}�����߿��l\�ˋe�E���ZQ������\:�5>��\�����״�M�v �/�k~�
�H��Ajx�eev��!:�w�����x�r��)�+��f�6�4{�6�N��_]	�1���ɾ��khPܿ:/�ܸ��~�ZcY����ʅUS��m��$�%�y$���SU:��
:^W\n���f㬱$��
�v�3I����iՐv�P�A�f��߳�,9v4��a:�v,���eN�}V�٪��*����V2��nՁ���'lW�%_���*<�z��#�*�k*�_�ɚ����wuDD�mQ���<H�ߗ�G���(b�hEЦ�R����UQ�`�ZE�u�.�����F����4��<I	t7�q�2��5�P�V�|3a��%�XD;��.��LB]���3���4x�tuH;���P%��˷�~2_s-ؔ���to�m����_��t��!��8
=��~#�����ƴ�Y�cGW�4�.������5��_�:��w�5M�bSm��D�aiqjGj	�����ۏ*<�quBkG;�+�^�Es�\S'���e�RaEx�b{M�2:z�B%�̭M4d��07i4b'P1ujc��mS����T�SZ��:����T@HJ�I[C���yr�'���5�f�����+$f��	�S���x��A�j�6[gO1hDGS�\�IR��M`\h�l���m4��%����C^gICX4��Xx'$66q7Tsb��K$ؚmL�f�Ƈ��?R�U�d��oY0�Z�m��|{�m�J$"!��"E͸�Y���"]��q��,��i;1j�p;� Y��p�U*�[���ڇ0�fL��d��/Ie�4@���hc��I}2���w�X]����Yyp�2)��s&��mY0����A�A�F�bN���89��s��9���U�y�e���9�Q���f
m�@�������<}��V:����B�K������ȂXt��T���e��4U/B�Mŧ�їl��RX\��6��*����.�n~�WJ(m¡���>����n�D�K9#+�Y�m��ľm�?5���Y܈�����[��y�l�v~���f�&4���.J�t*���W��	�<��MG�tܺ�'�����SL����Y�P�`D��oC"�|��w,��	���{��*��� �����\� ��M�oAނ��$����G+AZ;�C�_Iׯ����.$��/Ƃ`�F�qp#�y�k0���[��ѨƧ����g�Zαu��C C��u��c&��q�H7�Fq��p�UY<O�]��ͰuA��Ue�Ēn���5���R�7�Xl�k�
��D��(����u��Ϋ����:9�Y��U�!��U�v��v���i�ǀ߈���t4h�ѡ��ƣ��v����d�l�I��I�Ch�mH�L?�ҐKl1m(�c�CQX��P:8(�ؿ�/�t�z��1U�W�ϲ�z|e�,`���WV������-礕�/�=���b�~���j.f������/
�ҋa��C1r1�s-�d�I<]��UÜ�s�I.�jb�;��)���(�aN�9O�9Xh}����=y�=�| o�
�@76^0L����$���<�`x��s_����M��-;��'a�� ,���C��g!�hp�N�n9�X� �ݘ?�L������~�%���pW��
9�c%���!�o��unBl�&��兓Q�1о/�wD"xt2��6�����p��w�/]�w+����3bhR�'uw�Qspu*d�ז�P���� JoJ�R�<�%��;��"��)���Oվ�&/vY�}���C
���-�ŕ酸�ə��[ˢ����P��b��ֽ�6G�%N7�{km�k�����w͑zCGS��ܢ�|n����E��%�6������.G��?�>�7o�{Lx�ps��z�0D�V���;|$p
&t�������	!R��8妰H�zf]&*7�E�fg��iO���g�*�լ�72�ć,-�8��]D����=i���!��6���#B�<7ޓL���mi�'�4¨6�u=����<}      �   �   x���v
Q���WPJO-.���3R�K,�O�LL��Ss�R��}B]�4LtLuԍ�u,t���H�Tᒢ�T�dqIQf^:HYjNfnf^bJ�:�0��@! �d�i��I����5a^��|({�� ���Z      �   �   x���v
Q���WPJO-.���3R�K�,.I-�KTs�	uV�0�QPOLJ622R�Q0 r�!� ��nd`d�k`�kh�`hbelneh�ghlnhaJ�lZbNq*�E �@��i��Il0B����� ����⒢̼tud4T�� ����
�H�6��Ytc��9�ɉ����>��B}|H�	Psee���1�^3��b�a^+K.*�R�$��dZ�< ���      �   
   x���          �   �   x���v
Q���WPJO-.���3R�K�-(J-NTs�	uV�0�QP���9�%E�y�@VZbNq*� �XFF�����
V`D�0���B}|�ا@!P״��$2$͐B�x4$)Is��4I
B�hkbq~rfb2P���h`R����YQY��R����`#F�i�) Ei���l�gXZ�%L��RX�C1-B�� ��`      �   �   x���v
Q���WPJO-.���3R�K�-(J-N�O�,.I-�KTs�	uV�0�Q0�Q0�QP/�/RHN�KN�IL�R
�X�)X���i�9ũ@�⒢̼tu�,�PMk.OR]ov��u�9���C��:
�����Z\���\����O-���t9��@!P'���`��P���o<<��� �~G�      �   
   x���          �   
   x���          �   
   x���          q   o  x�Ś�r����y
v3SusGH�d�]��II@p��q�%�tJ��\e��<Ŭgw�~�{��2�'9�o��8}���s~���ȥ���q`�2�vi!ſ��uA��Y��L�e�{a]Ŕ��ߠR�TWɳ5�e۪�/��R�Gc��h�vt-?Y.����[��Cn�:����2�0���[m%
}xG?v)���N�Jb�ka}(���E�uɡygDI<�7�-�X�.�\(�cR���a٣1<a�E1��@��8J�y!y!��=1�@f<5�I�cV��Gp��8��4��`P{48����� �>6��.Ǭ��O�.r8�ʅ_Tu<�%&���6��mҬ��P�,�GfK|O�)����3?�5[��2+���,�k��g�m�6�].�H��=h��z�~{q�x��B�F��Ě�0,�D�w�O�3g�9t�Bè�4�Yή���8֒�x�6�{�Ù=��^t�=�^�j2霤G�3��a�EW�uܰ��z��Ԉz[Ѹ�X��C�K�+�e�O�tf��,!%{K�O<Ka�X��֟Y��w݈3y�c�*X.]��^��6�tՔq���
|�hQ�gG�aOz��z�^�D�kk	(�x�7?$���i���_˴#�vߛ��9#Q�3ʬ���fh�L��Nz�*���b-n9����>���{`����*�8���aǟ��aܫ�^g$J�Y��p|E��K��|T��dV��":3B�P�YJ�p����99��P���k(�aG?6�!���Y(��Sהq�#7L���E��(\�P�Rڋj9I)�m�e�dd��,!����=��%�W?��0�q6�F�w
5�2֎���T�m�-�h����x��`@�Q'��t%A��ʌ��=y��>�i�E	%D��-���>d��Т'�e:~hɺ�SX�)A[P�{gI"�J5cJ�z�^�k*��Zo65e�؈$�s��d�9͹Ⱥ2
�TC�� �z�K�2yY��-v62a�@+�܁�ZY�P�j��φB���K���d���
>6�}���70��BV��2�ի���(R�#��a�nGB�����	E�5��s;�0o����H(�rԃ8P��w�*�#ٗ;�K���B*eZ��k���_�2�dР�9���l�� ݤ���(kݺa�n�~}>�ʪ�	6)ųmX��I��ie����:���_X[��1#1��Tc&kγ�#N�Dț�NҩD*w��qPsyrI���y�%[�\��5�'*g^U�-*���xkM�J�&� s�Ui����j��ɑ��aG7,Yy�S�q���h.U�}��[���^�4P੹"�5�%�����Z(*�L�
��$J��r-Ӗ�Ù�����,Y�ż��2�)���-�X���Ut���Tl����"�3iqB]]��,�Wa�"8��M��|���ŀȦ.�:Z��AŚ�G�j�.Yηm	7�̑+@��K(
��#�#W���5(����-�氕\&�z���xK����ǿ�}|zx�';h��*T{]UG8	xϨ�C�YΊ\���2�vM�_�SשsB	�ˢ)��y���kG֣��;��ĩ��Wi)Y�5�]�?���V���e=+���?�ku�7R@
|]J�[K��z�|�?(�NL��L����� ��Ȇ�NC�Z�^I�#쀹ұ$�ߋ�������M�?��G	�!^����u��l���=d:�` y��AiY5��!�6��������pN\���tn��#�O�.}z�?S�Ӂ{�w��z�ڬ�@�{y�zS�V�3`�/�߳���	V!�k�ݭdm	�����>�
�>W�{�y�
g���������e\)���I?�:-����/��0�E���,�гa��;T���v��ag���w�ّuQ�-���&�g��ԽЧS�]*�'d��2@�<?�mu�v���ˡb��GS���/�}*e�}�A#�?	����{ݴ�V����n��5��i�W^m�Ϋ�yكW}*�?z�,uԼ��Pg����ͫ���� �a7��¼������}������>�����c�W����U���+���|6��i�:LtH�Uj�S�Ƨ�u��u�=�����������>x��K2=      l   `  x��ZM��F��WL颤���j��MNXe� ������#�����!��r�K�>�V��*c�x=�=�^�۾�ہ�VD�%��l/翧<��f�L}s�]����C�~�ǀE���<b�L�_���N
�^�Fח�������s��jtyO���[r�x$��xs',dūF����.�_�/_����H��>=F��*���Y��ΓL���ȕ��� ��!�C<S?�K<���ӝ����nc��P�/�[�k:vl� �ks!�A�t�EF���fZ.��-��/Y�R��
�Ϧ�t��}�M$��s��} -�	D��z�0;�����q
����l����*+����#�o$�B�`v:��}���H<sn�F8}@n�s��>�}�������=��/IH�
�� QI�)OXVT�*���+���25��/ݞ6 �`Ӱ�댹	��u�{Y�<-8��lőO�Z��*���T}�0s�>$E8ɨH�ؼ�R�f�o 밲J溂�)��xX��������R��$�e_��a��~�P�*U|W��K�HUz�|��+4Qq��wӀ3&"$�4�ྫ�,'�<�C��ϱ:�5�ry�j�\�Z�X4�Q(��+u�+�����v��.�nc)�����*��m�J�,A�DT1L tF�<-ď8���@AҪ��ѻ�kE�(~ ��a/W8���v�Ev�DH\�v�8B��z�L�U_��BE^X�r�nyǠ)Y�$���4L�u�۾@�r�����z׵�
.y���՜�����=���b��k"�sJ>�U�u�����.Aڦ]��\NT<ǘ�5R.�#q,�.g�l�V��U��(+�|��gJ�t]=�NMY������3���	I��>@����]5|G�0����R�iP�����<�h�A�C���%Oч�9"�غ�ʛ���8&IH��u��u�0�~��BN�hB���Z�6�MTe�N��[���=����@����jhPr�]m�4Ґ���d��e��0��А+h���K�M1t����9s�4��s�N��7���߲X�O�g�A�V�H��?JDM����t�K9>Ķ� yN�̹�^p₦�T����e��MC�Va���VPh�� %"_ڀ% NB��,��M#o|�;�$�5'":�* n��vvO�ʠ1[S�i��c�]O�L�w�y�1�J��4��~b� ��c� ')�����oLi�=�H����iZ~����d�,�XDIaOh]K�䪊���f�X��X��k�˲����Di�T:>Ea������L�{қ�O,ˣ�`�.�j�{��p�쌇2���&��x���^?L�qolW7A�rHQ)�c����=u]��]���x��Yi�z;�݅�1� �6�$������5�+��P �`��Sa�X��:��L*��>�U����]���C�&������}���\��|�;QC�S�^=)
_���h��lfBV�H��:��T�}L��"6-�����9qk�6�}0��# ��ȍ�Y_�%ȔB���*���"�W�c�f�o�M�v�Z����B�F��k�G^x��v��i��I�nT}��^K�7=��/Z�	Q'��;���ol�H-��h�<���HP����������s�Y.2r��zΞ�S�(�58�ġ��n�>�����3�E�~�?<�@4Y3 A��Vk��t����p�x����Y �b�4h.��<.Z̥N;+j`F���y�"���w�I����MW[e}t h��*n�5;�N�+syA��*X��|��3��y���A����YM+Ar�B�B���
�y$5��G��oF3eM�D�y��4��da�a������_i�v�      �    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false                        2615    18976    certificacion    SCHEMA        CREATE SCHEMA certificacion;
    DROP SCHEMA certificacion;
             postgres    false            �           0    0    SCHEMA certificacion    ACL     4   GRANT USAGE ON SCHEMA certificacion TO grupo_fenix;
                  postgres    false    8            	            2615    18977    contrato    SCHEMA        CREATE SCHEMA contrato;
    DROP SCHEMA contrato;
             postgres    false            �           0    0    SCHEMA contrato    ACL     /   GRANT USAGE ON SCHEMA contrato TO grupo_fenix;
                  postgres    false    9                        2615    18978    gestion    SCHEMA        CREATE SCHEMA gestion;
    DROP SCHEMA gestion;
             postgres    false            �           0    0    SCHEMA gestion    ACL     .   GRANT USAGE ON SCHEMA gestion TO grupo_fenix;
                  postgres    false    14                        2615    28137 	   gestionV2    SCHEMA        CREATE SCHEMA "gestionV2";
    DROP SCHEMA "gestionV2";
             postgres    false                        2615    18979 	   parametro    SCHEMA        CREATE SCHEMA parametro;
    DROP SCHEMA parametro;
             postgres    false            �           0    0    SCHEMA parametro    ACL     0   GRANT USAGE ON SCHEMA parametro TO grupo_fenix;
                  postgres    false    11                        2615    26896    proceso_contratacion    SCHEMA     $   CREATE SCHEMA proceso_contratacion;
 "   DROP SCHEMA proceso_contratacion;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    6            Y           1255    27096 )   actualizar_vm_certificaciones_dashboard()    FUNCTION     �   CREATE FUNCTION certificacion.actualizar_vm_certificaciones_dashboard() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view certificacion.certificacion_dashboard;
    return null;
end $$;
 G   DROP FUNCTION certificacion.actualizar_vm_certificaciones_dashboard();
       certificacion       postgres    false    8            W           1255    27082 "   actualizar_vm_partidas_dashboard()    FUNCTION     �   CREATE FUNCTION certificacion.actualizar_vm_partidas_dashboard() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view certificacion.partida_dashboard;
    return null;
end $$;
 @   DROP FUNCTION certificacion.actualizar_vm_partidas_dashboard();
       certificacion       postgres    false    8            \           1255    34956 +   obtener_gestion_movimiento(bigint, integer)    FUNCTION     �  CREATE FUNCTION certificacion.obtener_gestion_movimiento(p_id_certificacion bigint, p_gestion integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado integer;	
begin
	if p_gestion is null or p_gestion=0 then
		v_resultado:=(select p.gestion from certificacion.certificacion c inner join certificacion.partida p on c.id_partida =p.id where c.id=p_id_certificacion limit 1);
	else 
		v_resultado:=p_gestion;
	end if;
	return coalesce (v_resultado,1900);
end 
$$;
 f   DROP FUNCTION certificacion.obtener_gestion_movimiento(p_id_certificacion bigint, p_gestion integer);
       certificacion       postgres    false    8            V           1255    27056 A   obtener_monto_ejecutado_por_categoria(character varying, integer)    FUNCTION     �   CREATE FUNCTION certificacion.obtener_monto_ejecutado_por_categoria(p_categoria character varying, p_gestion integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
declare 
	volumen_ejecutado numeric;	
begin	
	return 0.000::decimal;
end 
$$;
 u   DROP FUNCTION certificacion.obtener_monto_ejecutado_por_categoria(p_categoria character varying, p_gestion integer);
       certificacion       postgres    false    8            T           1255    26243 a   obtener_monto_total_certificado(character varying, character varying, character varying, integer)    FUNCTION       CREATE FUNCTION certificacion.obtener_monto_total_certificado(tipo character varying, ambito character varying, sector character varying, gestion integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
declare 
	volumen_ejecutado numeric;	
begin	
	return 0.000::decimal;
end 
$$;
 �   DROP FUNCTION certificacion.obtener_monto_total_certificado(tipo character varying, ambito character varying, sector character varying, gestion integer);
       certificacion       postgres    false    8            a           1255    30842 2   obtener_totales_certificaciones_dashboard(integer)    FUNCTION     �  CREATE FUNCTION certificacion.obtener_totales_certificaciones_dashboard(p_gestion integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;	
	v_valor_por_defecto varchar:='[{"montoMaximo":0,"montoEjecutado":0,"saldo":0,"concepto":"CTTO"}]';
begin	
	v_resultado:= (	
		select jsonb_agg(datos)::varchar 
		from
		(
		 select 
		 sum(monto_maximo) as "montoMaximo",
		 sum(monto_ejecutado) as "montoEjecutado",
		 sum(saldo) as "saldo",
		 concepto as "concepto"	
		 from certificacion.certificacion_dashboard		
		 where gestion=p_gestion
		 group by concepto 
		) as datos	
	);

	return coalesce (v_resultado,v_valor_por_defecto);
end 
$$;
 Z   DROP FUNCTION certificacion.obtener_totales_certificaciones_dashboard(p_gestion integer);
       certificacion       postgres    false    8            b           1255    30841 +   obtener_totales_partidas_dashboard(integer)    FUNCTION     �  CREATE FUNCTION certificacion.obtener_totales_partidas_dashboard(p_gestion integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;	
	v_valor_por_defecto varchar:='[{"montoActual":0,"montoIncremento":0,"montoAprobado":0,"montoEjecutado":0,"categoria":"MAYOR"}]';
begin	
	v_resultado:= (	
		select jsonb_agg(datos)::varchar 
		from
		(
		 select 
		 monto_actual as "montoActual",
		 monto_incremento as "montoIncremento",
		 monto_aprobado as "montoAprobado",
		 monto_ejecutado as "montoEjecutado",
		 categoria 
		 from certificacion.partida_dashboard
		 where gestion = p_gestion
		) as datos	
	);

	return coalesce (v_resultado,v_valor_por_defecto);
end 
$$;
 S   DROP FUNCTION certificacion.obtener_totales_partidas_dashboard(p_gestion integer);
       certificacion       postgres    false    8            ]           1255    29441    actualizar_vm_contratos()    FUNCTION     �   CREATE FUNCTION contrato.actualizar_vm_contratos() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view contrato.contratos;
    return null;
end $$;
 2   DROP FUNCTION contrato.actualizar_vm_contratos();
       contrato       postgres    false    9            U           1255    27081 "   actualizar_vm_partidas_dashboard()    FUNCTION     �   CREATE FUNCTION contrato.actualizar_vm_partidas_dashboard() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
    refresh materialized view certificacion.partida_dashboard;
    return null;
end $$;
 ;   DROP FUNCTION contrato.actualizar_vm_partidas_dashboard();
       contrato       postgres    false    9            _           1255    29863 g   obtener_total_de_contratos_por_sector(character varying, character varying, character varying, integer)    FUNCTION     �  CREATE FUNCTION contrato.obtener_total_de_contratos_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$

declare	
	v_sql varchar:='';	
begin
		return 
		coalesce((		
			select count(*) 
			from contrato.contratos
			where tipo=p_tipo and 
			ambito=p_ambito and
			sector=p_sector	and
			gestion= p_gestion
		),0);					
end
$$;
 �   DROP FUNCTION contrato.obtener_total_de_contratos_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            c           1255    29572 H   obtener_totales_dashboard(character varying, character varying, integer)    FUNCTION     �  CREATE FUNCTION contrato.obtener_totales_dashboard(p_tipo character varying, p_ambito character varying, p_gestion integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;	

begin		
	case
		when p_tipo='TRANS' and p_ambito='NAL' then
			return (contrato.obtener_totales_por_tipo_ambito(p_tipo, p_ambito, p_gestion, 'SECTORES_NACIONALES'));		
		when p_tipo='TRANS' and p_ambito='INT' then
			return (contrato.obtener_totales_por_tipo_ambito(p_tipo, p_ambito, p_gestion, 'SECTORES_INTERNACIONALES'));		
		when p_tipo='SUMIN' and p_ambito='INT' then --*
			return (contrato.obtener_totales_por_tipo_ambito(p_tipo, p_ambito, p_gestion, 'SECTORES_SUMINISTROS'));
	end case;		
end 
$$;
 {   DROP FUNCTION contrato.obtener_totales_dashboard(p_tipo character varying, p_ambito character varying, p_gestion integer);
       contrato       postgres    false    9            [           1255    28860 d   obtener_totales_general_por_sector(character varying, character varying, character varying, integer)    FUNCTION     =  CREATE FUNCTION contrato.obtener_totales_general_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS json
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado json;	
	v_valor_por_defecto varchar:='{"tipo": "A", "sector": "B",  "volumenejecutado": 0,  "proximovencimiento": "1900-01-01",   "restomontocertificado": 0.000}';
begin	
	v_resultado:=(
		SELECT  json_build_object
		(
			'cantidadContratos',(select * from contrato.obtener_total_de_contratos_por_sector(p_tipo,p_ambito,p_sector,p_gestion)),	        
	        'volumen',( select contrato.sumar_volumen_por_sector(p_tipo,p_ambito,p_sector,p_gestion)),
	        'monto',(select contrato.sumar_monto_por_sector(p_tipo,p_ambito,p_sector,p_gestion))  
		)	
	);

	return v_resultado;
end 
$$;
 �   DROP FUNCTION contrato.obtener_totales_general_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            d           1255    29461 a   obtener_totales_por_tipo_ambito(character varying, character varying, integer, character varying)    FUNCTION     G  CREATE FUNCTION contrato.obtener_totales_por_tipo_ambito(p_tipo character varying, p_ambito character varying, p_gestion integer, p_sectores character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	v_resultado varchar;		
	v_nombre_objeto varchar:='';
begin	
	
	--Obtener los sectores en tabla temporal
	drop table if exists TEMPORAL_SECTORES;

	create temp table TEMPORAL_SECTORES (			
	    	tipo varchar,
	    	ambito varchar, 
	    	sector_codigo varchar,
	    	sector_valor varchar,
	    	total_sector json
	);

	insert into TEMPORAL_SECTORES(tipo,ambito,sector_codigo,sector_valor)
	(
		select distinct tipo, ambito, sector, parametro.obtener_valor_apartir_codigo(p_sectores,sector) sector_valor
		from contrato.contratos  
		where tipo=p_tipo and 
		ambito=p_ambito and
		sector in (select parametro.obtener_codigos_por_contexto(p_sectores)) and
		gestion = p_gestion
	);
	
	--Calcular el json de totales por sector (cantidad contratos, volumen y monto).
	update TEMPORAL_SECTORES
	set total_sector=(select contrato.obtener_totales_general_por_sector(TEMPORAL_SECTORES.tipo, TEMPORAL_SECTORES.ambito, TEMPORAL_SECTORES.sector_codigo, p_gestion));

	--Calcular el total general e inserta el resultado en la tabla temporal.
	insert into TEMPORAL_SECTORES(tipo,ambito,sector_codigo,sector_valor,total_sector)
	values(
		p_tipo,
		p_ambito,
		'GEN',
		'TotalGeneral',
		(SELECT  json_build_object(
	        'cantidadContratos',(select sum((total_sector->>'cantidadContratos')::int) cantidadContratos from TEMPORAL_SECTORES),
	        'volumen', coalesce(( select json_agg(suma_volumenes) 
	  				    from (select  
								(vol->>'producto')::varchar as producto, 
								coalesce(sum((vol->>'mermaPermitida')::numeric),0)  as "mermaPermitida", 
								coalesce(sum((vol->>'volumenCargado')::numeric),0) as "volumenCargado", 
								coalesce(sum((vol->>'volumenNominado')::numeric),0) as "volumenNominado", 
								coalesce(sum((vol->>'volumenDescargado')::numeric),0) as "volumenDescargado", 
								coalesce(sum((vol->>'porcentajeNominado')::numeric),0) as "porcentajeNominado"
								from TEMPORAL_SECTORES tmp, json_array_elements(tmp.total_sector->'volumen') as vol 	
								group by vol->>'producto') suma_volumenes),'[]'),
	        'monto',(select row_to_json(monto) 
	        		 from (
	        		 	select 
	        		 		coalesce(sum((total_sector->'monto'->>'saldo')::numeric),0) as "saldo", 
	        		 		coalesce(sum((total_sector->'monto'->>'ejecutado')::numeric),0) as "ejecutado", 
	        		 		coalesce(sum((total_sector->'monto'->>'montoAdjudicado')::numeric),0) as "montoAdjudicado" 
	        		 	from TEMPORAL_SECTORES limit 1) monto)  
		))
	);

	--Obtener el nombre del objeto json segun el sector. 
	case
		when p_sectores = 'SECTORES_NACIONALES' then
			v_nombre_objeto:='TransporteNacional';		
		when p_sectores = 'SECTORES_INTERNACIONALES' then
			v_nombre_objeto:='TransporteInternacional';		
		when p_sectores = 'SECTORES_SUMINISTROS' then
			v_nombre_objeto:='Suministro';
	end case;		
		

	--Retornar json con el formato definido por el front.
	return (
		select json_build_object(
			v_nombre_objeto,
			(select json_object_agg(t.sector_valor,t.total_sector)
			 from (
				select sector_valor, total_sector
				from TEMPORAL_SECTORES) t)
		)
	);

end 
$$;
 �   DROP FUNCTION contrato.obtener_totales_por_tipo_ambito(p_tipo character varying, p_ambito character varying, p_gestion integer, p_sectores character varying);
       contrato       postgres    false    9            `           1255    29866 X   sumar_monto_por_sector(character varying, character varying, character varying, integer)    FUNCTION     �  CREATE FUNCTION contrato.sumar_monto_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS json
    LANGUAGE plpgsql
    AS $$

declare	
	v_sql varchar:='';	
begin
		return 
		(		
			select row_to_json(montos)
			from (
				select 
				coalesce(sum((contrato.monto->>'saldo')::numeric),0) as "saldo", 
				coalesce(sum((contrato.monto->>'ejecutado')::numeric),0) as "ejecutado", 
				coalesce(sum((contrato.monto->>'montoAdjudicado')::numeric),0) as "montoAdjudicado"
				from contrato.contratos contrato
				where contrato.tipo=p_tipo and 
				contrato.ambito=p_ambito and
				contrato.sector=p_sector and
				contrato.gestion=p_gestion
			) montos
		
				
		);					
end
$$;
 �   DROP FUNCTION contrato.sumar_monto_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            ^           1255    29865 Z   sumar_volumen_por_sector(character varying, character varying, character varying, integer)    FUNCTION     &  CREATE FUNCTION contrato.sumar_volumen_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer) RETURNS json
    LANGUAGE plpgsql
    AS $$

declare	
	v_sql varchar:='';	
begin
		return coalesce((select json_agg(t)
			   from 
				(		
					select  
					(vol->>'producto')::varchar as producto, 
					coalesce(sum((vol->>'mermaPermitida')::numeric),0)  as "mermaPermitida", 
					coalesce(sum((vol->>'volumenCargado')::numeric),0) as "volumenCargado", 
					coalesce(sum((vol->>'volumenNominado')::numeric),0) as "volumenNominado", 
					coalesce(sum((vol->>'volumenDescargado')::numeric),0) as "volumenDescargado", 
					coalesce(sum((vol->>'porcentajeNominado')::numeric),0) as "porcentajeNominado"
					from contrato.contratos contrato, jsonb_array_elements(contrato.volumen) as vol
					where contrato.tipo=p_tipo and 
					contrato.ambito=p_ambito and
					contrato.sector=p_sector and
					contrato.gestion=p_gestion
					group by vol->>'producto'	
				) t),'[]') ;					
end
$$;
 �   DROP FUNCTION contrato.sumar_volumen_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer);
       contrato       postgres    false    9            X           1255    27058 /   obtener_codigos_por_contexto(character varying)    FUNCTION     {  CREATE FUNCTION parametro.obtener_codigos_por_contexto(p_contexto character varying) RETURNS TABLE(codigo_contexto character varying)
    LANGUAGE plpgsql
    AS $$
declare 
	volumen_ejecutado numeric;	
begin	
	return query
		select codigo
		from parametro.parametro p
		where p.contexto =p_contexto and 
		fecha_inicio is not null and 
		fecha_fin is null;	
end 
$$;
 T   DROP FUNCTION parametro.obtener_codigos_por_contexto(p_contexto character varying);
    	   parametro       postgres    false    11            Z           1255    28857 B   obtener_valor_apartir_codigo(character varying, character varying)    FUNCTION     �  CREATE FUNCTION parametro.obtener_valor_apartir_codigo(p_contexto character varying, p_codigo character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare 
	resultado varchar;	
begin	
    resultado:=(select valor
				from parametro.parametro p
				where p.contexto = p_contexto and 
				p.codigo = p_codigo and
				fecha_inicio is not null and 
				fecha_fin is null
				limit 1);	
	
	return resultado;
end 
$$;
 p   DROP FUNCTION parametro.obtener_valor_apartir_codigo(p_contexto character varying, p_codigo character varying);
    	   parametro       postgres    false    11            &           1259    27529    certificacion    TABLE     y  CREATE TABLE certificacion.certificacion (
    id bigint DEFAULT nextval(('certificacion.certificacion_presupuestaria_id_seq'::text)::regclass) NOT NULL,
    id_partida bigint,
    concepto character varying(20),
    fecha_emision timestamp without time zone,
    numero_certificacion character varying(20),
    descripcion character varying(500),
    monto_maximo numeric(20,4),
    monto_ejecutado numeric(20,4),
    saldo numeric(20,4),
    tipo_servicio character varying(20),
    id_proceso_contratacion bigint,
    id_lotes character varying(20),
    id_contrato bigint,
    ruta_documento_adjunto text,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70),
    id_certificacion bigint
);
 (   DROP TABLE certificacion.certificacion;
       certificacion         postgres    false    8            %           1259    27521    partida    TABLE     �  CREATE TABLE certificacion.partida (
    id bigint DEFAULT nextval(('certificacion.partida_presupuestaria_id_seq'::text)::regclass) NOT NULL,
    gestion integer NOT NULL,
    categoria character varying(20) NOT NULL,
    numero_partida character varying(20),
    monto_actual numeric(20,4),
    monto_aprobado numeric(20,4),
    monto_incremento numeric(20,4),
    saldo numeric(20,4),
    ruta_documento_adjunto text,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
 "   DROP TABLE certificacion.partida;
       certificacion         postgres    false    8            G           1259    34970    certificacion_dashboard    MATERIALIZED VIEW     �  CREATE MATERIALIZED VIEW certificacion.certificacion_dashboard AS
 SELECT COALESCE(sum(c.monto_maximo), (0)::numeric(20,4)) AS monto_maximo,
    COALESCE(sum(c.monto_ejecutado), (0)::numeric(20,4)) AS monto_ejecutado,
    COALESCE(sum(c.saldo), (0)::numeric(20,4)) AS saldo,
    c.concepto,
    c.tipo_servicio,
    certificacion.obtener_gestion_movimiento(c.id_certificacion, p.gestion) AS gestion,
        CASE
            WHEN ((c.concepto)::text = 'CTTO'::text) THEN 'CONTRATO'::text
            WHEN ((c.concepto)::text = ANY ((ARRAY['ADENDA'::character varying, 'INCR'::character varying, 'ACT'::character varying, 'REV'::character varying])::text[])) THEN 'MOVIMIENTO'::text
            ELSE NULL::text
        END AS tipo_certificacion
   FROM (certificacion.certificacion c
     LEFT JOIN certificacion.partida p ON ((c.id_partida = p.id)))
  WHERE ((c.eliminado = false) AND ((c.concepto)::text IN ( SELECT obtener_codigos_por_contexto.codigo_contexto
           FROM parametro.obtener_codigos_por_contexto('CERTIFICACION_CONCEPTOS'::character varying) obtener_codigos_por_contexto(codigo_contexto))) AND ((c.tipo_servicio)::text IN ( SELECT obtener_codigos_por_contexto.codigo_contexto
           FROM parametro.obtener_codigos_por_contexto('CERTIFICACION_SERVICIOS'::character varying) obtener_codigos_por_contexto(codigo_contexto))))
  GROUP BY c.concepto, c.tipo_servicio, p.gestion, c.id_certificacion
  WITH NO DATA;
 >   DROP MATERIALIZED VIEW certificacion.certificacion_dashboard;
       certificacion         postgres    false    344    348    293    293    294    294    294    294    294    294    294    294    8            (           1259    27542 #   certificacion_presupuestaria_id_seq    SEQUENCE     �   CREATE SEQUENCE certificacion.certificacion_presupuestaria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE certificacion.certificacion_presupuestaria_id_seq;
       certificacion       postgres    false    8            F           1259    34784    partida_dashboard    MATERIALIZED VIEW     
  CREATE MATERIALIZED VIEW certificacion.partida_dashboard AS
 SELECT COALESCE(sum(pp.monto_actual), (0)::numeric(20,4)) AS monto_actual,
    COALESCE(sum(pp.monto_incremento), (0)::numeric(20,4)) AS monto_incremento,
    COALESCE(sum(pp.monto_aprobado), (0)::numeric(20,4)) AS monto_aprobado,
    certificacion.obtener_monto_ejecutado_por_categoria(pp.categoria, pp.gestion) AS monto_ejecutado,
    pp.categoria,
    pp.gestion
   FROM certificacion.partida pp
  WHERE ((pp.eliminado = false) AND ((pp.categoria)::text IN ( SELECT obtener_codigos_por_contexto.codigo_contexto
           FROM parametro.obtener_codigos_por_contexto('PARTIDAS_PRESUPUESTARIAS'::character varying) obtener_codigos_por_contexto(codigo_contexto))))
  GROUP BY pp.categoria, pp.gestion
  WITH NO DATA;
 8   DROP MATERIALIZED VIEW certificacion.partida_dashboard;
       certificacion         postgres    false    293    293    293    344    342    293    293    293    8            '           1259    27540    partida_presupuestaria_id_seq    SEQUENCE     �   CREATE SEQUENCE certificacion.partida_presupuestaria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ;   DROP SEQUENCE certificacion.partida_presupuestaria_id_seq;
       certificacion       postgres    false    8            #           1259    27044    proceso_contratacion    TABLE     �   CREATE TABLE certificacion.proceso_contratacion (
    id bigint DEFAULT nextval(('certificacion.proceso_contratacion_id_seq'::text)::regclass) NOT NULL,
    cuce character varying(20),
    objecto_contratacion character varying(500)
);
 /   DROP TABLE certificacion.proceso_contratacion;
       certificacion         postgres    false    8            $           1259    27051    proceso_contratacion_id_seq    SEQUENCE     �   CREATE SEQUENCE certificacion.proceso_contratacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 9   DROP SEQUENCE certificacion.proceso_contratacion_id_seq;
       certificacion       postgres    false    8                       1259    24618    contrato    TABLE     �  CREATE TABLE contrato.contrato (
    id bigint DEFAULT nextval(('contrato.contrato_id_seq'::text)::regclass) NOT NULL,
    id_tipo_contrato integer NOT NULL,
    numero_contrato integer,
    gestion_contrato integer,
    contrato jsonb NOT NULL,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE contrato.contrato;
       contrato         postgres    false    9                       1259    24632    contrato_id_seq    SEQUENCE     z   CREATE SEQUENCE contrato.contrato_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE contrato.contrato_id_seq;
       contrato       postgres    false    9            E           1259    31104 	   contratos    MATERIALIZED VIEW     �  CREATE MATERIALIZED VIEW contrato.contratos AS
 SELECT c.id,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'tipo'::text) AS tipo,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'ambito'::text) AS ambito,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'sector'::text) AS sector,
    ((c.contrato -> 'informacionEspecifica'::text) ->> 'medio'::text) AS medio,
    (((c.contrato -> 'informacionGeneral'::text) ->> 'gestion'::text))::integer AS gestion,
    ((c.contrato -> 'totalVolumenesMonto'::text) -> 'monto'::text) AS monto,
    ((c.contrato -> 'totalVolumenesMonto'::text) -> 'volumen'::text) AS volumen
   FROM contrato.contrato c
  WHERE (c.eliminado = false)
  WITH NO DATA;
 +   DROP MATERIALIZED VIEW contrato.contratos;
       contrato         postgres    false    273    273    273    9                       1259    24634    movimiento_id_seq    SEQUENCE     |   CREATE SEQUENCE contrato.movimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE contrato.movimiento_id_seq;
       contrato       postgres    false    9                       1259    24625    volumen_ejecutado    TABLE     �  CREATE TABLE contrato.volumen_ejecutado (
    id bigint DEFAULT nextval(('contrato.movimiento_id_seq'::text)::regclass) NOT NULL,
    id_contrato bigint NOT NULL,
    volumen numeric NOT NULL,
    fecha_ejecucion timestamp without time zone NOT NULL,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
 '   DROP TABLE contrato.volumen_ejecutado;
       contrato         postgres    false    9            :           1259    28435    asociacion_empresa    TABLE     �  CREATE TABLE gestion.asociacion_empresa (
    id bigint NOT NULL,
    id_empresa bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70),
    id_asociacion bigint
);
 '   DROP TABLE gestion.asociacion_empresa;
       gestion         postgres    false    14            9           1259    28433    asociacion_empresa_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.asociacion_empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE gestion.asociacion_empresa_id_seq;
       gestion       postgres    false    14    314            �           0    0    asociacion_empresa_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE gestion.asociacion_empresa_id_seq OWNED BY gestion.asociacion_empresa.id;
            gestion       postgres    false    313                       1259    25934    asociacion_id_seq    SEQUENCE     {   CREATE SEQUENCE gestion.asociacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE gestion.asociacion_id_seq;
       gestion       postgres    false    14            <           1259    28447    cisterna    TABLE     �  CREATE TABLE gestion.cisterna (
    id bigint NOT NULL,
    placa character varying(25) NOT NULL,
    anio integer,
    marca character varying(50),
    color character varying(50),
    capacidad integer,
    nro_compartimientos integer,
    nro_chasis character varying(50),
    disposicion_ejes character varying(50),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.cisterna;
       gestion         postgres    false    14                       1259    25936    cisterna_id_seq    SEQUENCE     y   CREATE SEQUENCE gestion.cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE gestion.cisterna_id_seq;
       gestion       postgres    false    14            ;           1259    28445    cisterna_id_seq1    SEQUENCE     z   CREATE SEQUENCE gestion.cisterna_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE gestion.cisterna_id_seq1;
       gestion       postgres    false    316    14            �           0    0    cisterna_id_seq1    SEQUENCE OWNED BY     F   ALTER SEQUENCE gestion.cisterna_id_seq1 OWNED BY gestion.cisterna.id;
            gestion       postgres    false    315            >           1259    28458 	   conductor    TABLE       CREATE TABLE gestion.conductor (
    id bigint NOT NULL,
    nro_licencia character varying(50) NOT NULL,
    nombre character varying(50) NOT NULL,
    apellidos character varying(50),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.conductor;
       gestion         postgres    false    14                       1259    25938    conductor_id_seq    SEQUENCE     z   CREATE SEQUENCE gestion.conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE gestion.conductor_id_seq;
       gestion       postgres    false    14            =           1259    28456    conductor_id_seq1    SEQUENCE     {   CREATE SEQUENCE gestion.conductor_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE gestion.conductor_id_seq1;
       gestion       postgres    false    14    318            �           0    0    conductor_id_seq1    SEQUENCE OWNED BY     H   ALTER SEQUENCE gestion.conductor_id_seq1 OWNED BY gestion.conductor.id;
            gestion       postgres    false    317            @           1259    28469    empresa    TABLE     F  CREATE TABLE gestion.empresa (
    id bigint NOT NULL,
    razon_social character varying(250) NOT NULL,
    pais character varying(20),
    es_asociacion boolean NOT NULL,
    tipo_documento character varying(20),
    numero_documento character varying(50),
    tipo_constitucion character varying(20),
    email character varying(500),
    direccion character varying(500),
    sitio_web character varying(50),
    telefono character varying(500),
    representante_legal character varying(500),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.empresa;
       gestion         postgres    false    14                       1259    25942    empresa_asociacion_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_asociacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE gestion.empresa_asociacion_id_seq;
       gestion       postgres    false    14            B           1259    28482    empresa_cisterna    TABLE     �  CREATE TABLE gestion.empresa_cisterna (
    id bigint NOT NULL,
    id_empresa bigint,
    id_cisterna bigint,
    motivo character varying(20),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 %   DROP TABLE gestion.empresa_cisterna;
       gestion         postgres    false    14            A           1259    28480    empresa_cisterna_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE gestion.empresa_cisterna_id_seq;
       gestion       postgres    false    322    14            �           0    0    empresa_cisterna_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE gestion.empresa_cisterna_id_seq OWNED BY gestion.empresa_cisterna.id;
            gestion       postgres    false    321            D           1259    28505    empresa_conductor    TABLE     �  CREATE TABLE gestion.empresa_conductor (
    id bigint NOT NULL,
    id_empresa bigint,
    id_conductor bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 &   DROP TABLE gestion.empresa_conductor;
       gestion         postgres    false    14            C           1259    28503    empresa_conductor_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE gestion.empresa_conductor_id_seq;
       gestion       postgres    false    14    324            �           0    0    empresa_conductor_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE gestion.empresa_conductor_id_seq OWNED BY gestion.empresa_conductor.id;
            gestion       postgres    false    323                       1259    25930    empresa_id_seq    SEQUENCE     x   CREATE SEQUENCE gestion.empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE gestion.empresa_id_seq;
       gestion       postgres    false    14            ?           1259    28467    empresa_id_seq1    SEQUENCE     y   CREATE SEQUENCE gestion.empresa_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE gestion.empresa_id_seq1;
       gestion       postgres    false    14    320            �           0    0    empresa_id_seq1    SEQUENCE OWNED BY     D   ALTER SEQUENCE gestion.empresa_id_seq1 OWNED BY gestion.empresa.id;
            gestion       postgres    false    319                       1259    25940 "   empresa_representante_legal_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.empresa_representante_legal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 :   DROP SEQUENCE gestion.empresa_representante_legal_id_seq;
       gestion       postgres    false    14                       1259    26812    lote    TABLE     �  CREATE TABLE gestion.lote (
    id bigint DEFAULT nextval(('gestion.lote_id_seq'::text)::regclass) NOT NULL,
    tipo_lote character varying(20) NOT NULL,
    lote integer NOT NULL,
    tipo_producto character varying(20),
    sector character varying(20),
    volumen_maximo numeric,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50) NOT NULL,
    hash character(70),
    gestion integer
);
    DROP TABLE gestion.lote;
       gestion         postgres    false    14            !           1259    26826    lote_id_seq    SEQUENCE     u   CREATE SEQUENCE gestion.lote_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE gestion.lote_id_seq;
       gestion       postgres    false    14                       1259    19046    lote_ruta_id_seq    SEQUENCE     z   CREATE SEQUENCE gestion.lote_ruta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE gestion.lote_ruta_id_seq;
       gestion       postgres    false    14                       1259    26775    lote_tramo_id_seq    SEQUENCE     {   CREATE SEQUENCE gestion.lote_tramo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE gestion.lote_tramo_id_seq;
       gestion       postgres    false    14                       1259    25932    representante_legal_id_seq    SEQUENCE     �   CREATE SEQUENCE gestion.representante_legal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE gestion.representante_legal_id_seq;
       gestion       postgres    false    14                       1259    19040    ruta_id_seq    SEQUENCE     u   CREATE SEQUENCE gestion.ruta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE gestion.ruta_id_seq;
       gestion       postgres    false    14                        1259    26819    tramo    TABLE     �  CREATE TABLE gestion.tramo (
    id bigint DEFAULT nextval(('gestion.tramo_id_seq'::text)::regclass) NOT NULL,
    id_lote bigint NOT NULL,
    es_principal boolean NOT NULL,
    punto_recepcion character varying(1000) NOT NULL,
    frontera character varying(1000) NOT NULL,
    punto_destino character varying(1000) NOT NULL,
    tarifa numeric(20,4) NOT NULL,
    tiempo_estimado numeric,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE gestion.tramo;
       gestion         postgres    false    14            "           1259    26828    tramo_id_seq    SEQUENCE     v   CREATE SEQUENCE gestion.tramo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE gestion.tramo_id_seq;
       gestion       postgres    false    14            -           1259    28166    asociacion_empresa    TABLE       CREATE TABLE "gestionV2".asociacion_empresa (
    id bigint DEFAULT nextval(('gestionv2.asociacion_empresa_id_seq'::text)::regclass) NOT NULL,
    id_empresa bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70),
    id_asociacion bigint
);
 +   DROP TABLE "gestionV2".asociacion_empresa;
    	   gestionV2         postgres    false    13            3           1259    28188    asociacion_empresa_id_seq    SEQUENCE     �   CREATE SEQUENCE "gestionV2".asociacion_empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE "gestionV2".asociacion_empresa_id_seq;
    	   gestionV2       postgres    false    13            *           1259    28145    cisterna    TABLE     �  CREATE TABLE "gestionV2".cisterna (
    id bigint DEFAULT nextval(('gestionv2.cisterna_id_seq'::text)::regclass) NOT NULL,
    placa character varying(25) NOT NULL,
    anio integer,
    marca character varying(50),
    color character varying(50),
    capacidad integer,
    nro_compartimientos integer,
    nro_chasis character varying(50),
    disposicion_ejes character varying(50),
    fecha_inicio timestamp without time zone NOT NULL,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 !   DROP TABLE "gestionV2".cisterna;
    	   gestionV2         postgres    false    13            0           1259    28182    cisterna_id_seq    SEQUENCE     }   CREATE SEQUENCE "gestionV2".cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE "gestionV2".cisterna_id_seq;
    	   gestionV2       postgres    false    13            +           1259    28152 	   conductor    TABLE     d  CREATE TABLE "gestionV2".conductor (
    id bigint DEFAULT nextval(('gestionv2.conductor_id_seq'::text)::regclass) NOT NULL,
    nro_licencia character varying(50) NOT NULL,
    nombre character varying(50) NOT NULL,
    apellidos character varying(50) NOT NULL,
    fecha_inicio timestamp without time zone NOT NULL,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 "   DROP TABLE "gestionV2".conductor;
    	   gestionV2         postgres    false    13            1           1259    28184    conductor_id_seq    SEQUENCE     ~   CREATE SEQUENCE "gestionV2".conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE "gestionV2".conductor_id_seq;
    	   gestionV2       postgres    false    13            )           1259    28138    empresa    TABLE     �  CREATE TABLE "gestionV2".empresa (
    id bigint DEFAULT nextval(('gestionv2.empresa_id_seq'::text)::regclass) NOT NULL,
    razon_social character varying(250) NOT NULL,
    pais character varying(20) NOT NULL,
    es_asociacion boolean NOT NULL,
    tipo_documento character varying(20) NOT NULL,
    numero_documento character varying(50) NOT NULL,
    tipo_constitucion character varying(20) NOT NULL,
    email character varying(500),
    direccion character varying(500) NOT NULL,
    sitio_web character varying(50),
    telefono character varying(500) NOT NULL,
    representante_legal character varying(500),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
     DROP TABLE "gestionV2".empresa;
    	   gestionV2         postgres    false    13            ,           1259    28159    empresa_cisterna    TABLE     0  CREATE TABLE "gestionV2".empresa_cisterna (
    id bigint DEFAULT nextval(('gestionv2.empresa_cisterna_id_seq'::text)::regclass) NOT NULL,
    id_empresa bigint,
    id_cisterna bigint,
    motivo character varying(20),
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 )   DROP TABLE "gestionV2".empresa_cisterna;
    	   gestionV2         postgres    false    13            2           1259    28186    empresa_cisterna_id_seq    SEQUENCE     �   CREATE SEQUENCE "gestionV2".empresa_cisterna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE "gestionV2".empresa_cisterna_id_seq;
    	   gestionV2       postgres    false    13            .           1259    28173    empresa_conductor    TABLE       CREATE TABLE "gestionV2".empresa_conductor (
    id bigint DEFAULT nextval(('gestionv2.empresa_conductor_id_seq'::text)::regclass) NOT NULL,
    id_empresa bigint,
    id_conductor bigint,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(2000),
    ip_cliente character varying(50),
    hash character(70)
);
 *   DROP TABLE "gestionV2".empresa_conductor;
    	   gestionV2         postgres    false    13            4           1259    28190    empresa_conductor_id_seq    SEQUENCE     �   CREATE SEQUENCE "gestionV2".empresa_conductor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE "gestionV2".empresa_conductor_id_seq;
    	   gestionV2       postgres    false    13            /           1259    28180    empresa_id_seq    SEQUENCE     |   CREATE SEQUENCE "gestionV2".empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE "gestionV2".empresa_id_seq;
    	   gestionV2       postgres    false    13            6           1259    28386    lote    TABLE     Y  CREATE TABLE "gestionV2".lote (
    id bigint NOT NULL,
    tipo_lote character varying(20) NOT NULL,
    lote integer NOT NULL,
    tipo_producto character varying(20),
    sector character varying(20),
    volumen_maximo numeric,
    fecha_inicio timestamp without time zone,
    fecha_fin timestamp without time zone,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50) NOT NULL,
    hash character(70),
    gestion integer
);
    DROP TABLE "gestionV2".lote;
    	   gestionV2         postgres    false    13            5           1259    28384    lote_id_seq    SEQUENCE     y   CREATE SEQUENCE "gestionV2".lote_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE "gestionV2".lote_id_seq;
    	   gestionV2       postgres    false    13    310            �           0    0    lote_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE "gestionV2".lote_id_seq OWNED BY "gestionV2".lote.id;
         	   gestionV2       postgres    false    309            8           1259    28415    tramo    TABLE     F  CREATE TABLE "gestionV2".tramo (
    id bigint NOT NULL,
    id_lote bigint NOT NULL,
    es_principal boolean NOT NULL,
    punto_recepcion character varying(1000) NOT NULL,
    frontera character varying(1000) NOT NULL,
    punto_destino character varying(1000) NOT NULL,
    tarifa numeric NOT NULL,
    tiempo_estimado numeric,
    fecha_registro timestamp without time zone NOT NULL,
    eliminado boolean NOT NULL,
    usuario_aplicacion character varying(10) NOT NULL,
    justificacion character varying(500),
    ip_cliente character varying(50),
    hash character(70)
);
    DROP TABLE "gestionV2".tramo;
    	   gestionV2         postgres    false    13            7           1259    28413    tramo_id_seq    SEQUENCE     z   CREATE SEQUENCE "gestionV2".tramo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE "gestionV2".tramo_id_seq;
    	   gestionV2       postgres    false    13    312            �           0    0    tramo_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE "gestionV2".tramo_id_seq OWNED BY "gestionV2".tramo.id;
         	   gestionV2       postgres    false    311                       1259    25374 	   parametro    TABLE     d  CREATE TABLE parametro.parametro (
    id bigint DEFAULT nextval(('parametro.parametro_id_seq'::text)::regclass) NOT NULL,
    contexto character varying(50) NOT NULL,
    codigo character varying(20) NOT NULL,
    valor character varying(50) NOT NULL,
    descripcion character varying(500) NOT NULL,
    fecha_inicio date NOT NULL,
    fecha_fin date
);
     DROP TABLE parametro.parametro;
    	   parametro         postgres    false    11                       1259    24609    parametroVjson    TABLE     �   CREATE TABLE parametro."parametroVjson" (
    id bigint NOT NULL,
    parametro character varying(50) NOT NULL,
    valor jsonb NOT NULL,
    estado character varying(2)
);
 '   DROP TABLE parametro."parametroVjson";
    	   parametro         postgres    false    11            �           0    0    TABLE "parametroVjson"    ACL     A   GRANT SELECT ON TABLE parametro."parametroVjson" TO grupo_fenix;
         	   parametro       postgres    false    272                       1259    25381    parametro_id_seq    SEQUENCE     |   CREATE SEQUENCE parametro.parametro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE parametro.parametro_id_seq;
    	   parametro       postgres    false    11            �           2604    28438    asociacion_empresa id    DEFAULT     �   ALTER TABLE ONLY gestion.asociacion_empresa ALTER COLUMN id SET DEFAULT nextval('gestion.asociacion_empresa_id_seq'::regclass);
 E   ALTER TABLE gestion.asociacion_empresa ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    314    313    314            �           2604    28450    cisterna id    DEFAULT     m   ALTER TABLE ONLY gestion.cisterna ALTER COLUMN id SET DEFAULT nextval('gestion.cisterna_id_seq1'::regclass);
 ;   ALTER TABLE gestion.cisterna ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    316    315    316            �           2604    28461    conductor id    DEFAULT     o   ALTER TABLE ONLY gestion.conductor ALTER COLUMN id SET DEFAULT nextval('gestion.conductor_id_seq1'::regclass);
 <   ALTER TABLE gestion.conductor ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    317    318    318            �           2604    28472 
   empresa id    DEFAULT     k   ALTER TABLE ONLY gestion.empresa ALTER COLUMN id SET DEFAULT nextval('gestion.empresa_id_seq1'::regclass);
 :   ALTER TABLE gestion.empresa ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    319    320    320            �           2604    28485    empresa_cisterna id    DEFAULT     |   ALTER TABLE ONLY gestion.empresa_cisterna ALTER COLUMN id SET DEFAULT nextval('gestion.empresa_cisterna_id_seq'::regclass);
 C   ALTER TABLE gestion.empresa_cisterna ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    322    321    322            �           2604    28508    empresa_conductor id    DEFAULT     ~   ALTER TABLE ONLY gestion.empresa_conductor ALTER COLUMN id SET DEFAULT nextval('gestion.empresa_conductor_id_seq'::regclass);
 D   ALTER TABLE gestion.empresa_conductor ALTER COLUMN id DROP DEFAULT;
       gestion       postgres    false    323    324    324            �           2604    28389    lote id    DEFAULT     l   ALTER TABLE ONLY "gestionV2".lote ALTER COLUMN id SET DEFAULT nextval('"gestionV2".lote_id_seq'::regclass);
 ;   ALTER TABLE "gestionV2".lote ALTER COLUMN id DROP DEFAULT;
    	   gestionV2       postgres    false    309    310    310            �           2604    28418    tramo id    DEFAULT     n   ALTER TABLE ONLY "gestionV2".tramo ALTER COLUMN id SET DEFAULT nextval('"gestionV2".tramo_id_seq'::regclass);
 <   ALTER TABLE "gestionV2".tramo ALTER COLUMN id DROP DEFAULT;
    	   gestionV2       postgres    false    311    312    312            �          0    27529    certificacion 
   TABLE DATA                     certificacion       postgres    false    294   �       �          0    27521    partida 
   TABLE DATA                     certificacion       postgres    false    293   �                 0    27044    proceso_contratacion 
   TABLE DATA                     certificacion       postgres    false    291   �       m          0    24618    contrato 
   TABLE DATA                     contrato       postgres    false    273   �       n          0    24625    volumen_ejecutado 
   TABLE DATA                     contrato       postgres    false    274   |        �          0    28435    asociacion_empresa 
   TABLE DATA                     gestion       postgres    false    314   �        �          0    28447    cisterna 
   TABLE DATA                     gestion       postgres    false    316   �        �          0    28458 	   conductor 
   TABLE DATA                     gestion       postgres    false    318   �        �          0    28469    empresa 
   TABLE DATA                     gestion       postgres    false    320   �        �          0    28482    empresa_cisterna 
   TABLE DATA                     gestion       postgres    false    322          �          0    28505    empresa_conductor 
   TABLE DATA                     gestion       postgres    false    324   -       {          0    26812    lote 
   TABLE DATA                     gestion       postgres    false    287   G       |          0    26819    tramo 
   TABLE DATA                     gestion       postgres    false    288          �          0    28166    asociacion_empresa 
   TABLE DATA                  	   gestionV2       postgres    false    301   �        �          0    28145    cisterna 
   TABLE DATA                  	   gestionV2       postgres    false    298           �          0    28152 	   conductor 
   TABLE DATA                  	   gestionV2       postgres    false    299          �          0    28138    empresa 
   TABLE DATA                  	   gestionV2       postgres    false    297   %       �          0    28159    empresa_cisterna 
   TABLE DATA                  	   gestionV2       postgres    false    300           �          0    28173    empresa_conductor 
   TABLE DATA                  	   gestionV2       postgres    false    302   �       �          0    28386    lote 
   TABLE DATA                  	   gestionV2       postgres    false    310   �       �          0    28415    tramo 
   TABLE DATA                  	   gestionV2       postgres    false    312          q          0    25374 	   parametro 
   TABLE DATA                  	   parametro       postgres    false    277   !       l          0    24609    parametroVjson 
   TABLE DATA                  	   parametro       postgres    false    272   �       �           0    0 #   certificacion_presupuestaria_id_seq    SEQUENCE SET     Z   SELECT pg_catalog.setval('certificacion.certificacion_presupuestaria_id_seq', 125, true);
            certificacion       postgres    false    296            �           0    0    partida_presupuestaria_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('certificacion.partida_presupuestaria_id_seq', 22, true);
            certificacion       postgres    false    295            �           0    0    proceso_contratacion_id_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('certificacion.proceso_contratacion_id_seq', 20, true);
            certificacion       postgres    false    292            �           0    0    contrato_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('contrato.contrato_id_seq', 222, true);
            contrato       postgres    false    275            �           0    0    movimiento_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('contrato.movimiento_id_seq', 9, true);
            contrato       postgres    false    276            �           0    0    asociacion_empresa_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('gestion.asociacion_empresa_id_seq', 1, false);
            gestion       postgres    false    313            �           0    0    asociacion_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('gestion.asociacion_id_seq', 2, true);
            gestion       postgres    false    281            �           0    0    cisterna_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('gestion.cisterna_id_seq', 105, true);
            gestion       postgres    false    282            �           0    0    cisterna_id_seq1    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.cisterna_id_seq1', 3, true);
            gestion       postgres    false    315            �           0    0    conductor_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.conductor_id_seq', 6, true);
            gestion       postgres    false    283            �           0    0    conductor_id_seq1    SEQUENCE SET     A   SELECT pg_catalog.setval('gestion.conductor_id_seq1', 1, false);
            gestion       postgres    false    317            �           0    0    empresa_asociacion_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('gestion.empresa_asociacion_id_seq', 4, true);
            gestion       postgres    false    285            �           0    0    empresa_cisterna_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('gestion.empresa_cisterna_id_seq', 1, true);
            gestion       postgres    false    321            �           0    0    empresa_conductor_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('gestion.empresa_conductor_id_seq', 1, false);
            gestion       postgres    false    323            �           0    0    empresa_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.empresa_id_seq', 153, true);
            gestion       postgres    false    279            �           0    0    empresa_id_seq1    SEQUENCE SET     ?   SELECT pg_catalog.setval('gestion.empresa_id_seq1', 25, true);
            gestion       postgres    false    319            �           0    0 "   empresa_representante_legal_id_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('gestion.empresa_representante_legal_id_seq', 1, false);
            gestion       postgres    false    284            �           0    0    lote_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('gestion.lote_id_seq', 42, true);
            gestion       postgres    false    289            �           0    0    lote_ruta_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('gestion.lote_ruta_id_seq', 1, false);
            gestion       postgres    false    271            �           0    0    lote_tramo_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('gestion.lote_tramo_id_seq', 203, true);
            gestion       postgres    false    286            �           0    0    representante_legal_id_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('gestion.representante_legal_id_seq', 1, false);
            gestion       postgres    false    280            �           0    0    ruta_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('gestion.ruta_id_seq', 1, false);
            gestion       postgres    false    270            �           0    0    tramo_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('gestion.tramo_id_seq', 980, true);
            gestion       postgres    false    290            �           0    0    asociacion_empresa_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('"gestionV2".asociacion_empresa_id_seq', 6, true);
         	   gestionV2       postgres    false    307            �           0    0    cisterna_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('"gestionV2".cisterna_id_seq', 4, true);
         	   gestionV2       postgres    false    304            �           0    0    conductor_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('"gestionV2".conductor_id_seq', 1, false);
         	   gestionV2       postgres    false    305            �           0    0    empresa_cisterna_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('"gestionV2".empresa_cisterna_id_seq', 10, true);
         	   gestionV2       postgres    false    306            �           0    0    empresa_conductor_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('"gestionV2".empresa_conductor_id_seq', 1, false);
         	   gestionV2       postgres    false    308            �           0    0    empresa_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('"gestionV2".empresa_id_seq', 9, true);
         	   gestionV2       postgres    false    303            �           0    0    lote_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('"gestionV2".lote_id_seq', 1, false);
         	   gestionV2       postgres    false    309            �           0    0    tramo_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('"gestionV2".tramo_id_seq', 1, false);
         	   gestionV2       postgres    false    311            �           0    0    parametro_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('parametro.parametro_id_seq', 176, true);
         	   parametro       postgres    false    278            �           2606    27549 8   certificacion PK_CER_trs_certificacion_presupuestaria_v2 
   CONSTRAINT        ALTER TABLE ONLY certificacion.certificacion
    ADD CONSTRAINT "PK_CER_trs_certificacion_presupuestaria_v2" PRIMARY KEY (id);
 k   ALTER TABLE ONLY certificacion.certificacion DROP CONSTRAINT "PK_CER_trs_certificacion_presupuestaria_v2";
       certificacion         postgres    false    294            �           2606    27545 ,   partida PK_CER_trs_partida_presupuestaria_v2 
   CONSTRAINT     s   ALTER TABLE ONLY certificacion.partida
    ADD CONSTRAINT "PK_CER_trs_partida_presupuestaria_v2" PRIMARY KEY (id);
 _   ALTER TABLE ONLY certificacion.partida DROP CONSTRAINT "PK_CER_trs_partida_presupuestaria_v2";
       certificacion         postgres    false    293            �           2606    27054 ,   proceso_contratacion PK_PROCESO_CONTRATACION 
   CONSTRAINT     s   ALTER TABLE ONLY certificacion.proceso_contratacion
    ADD CONSTRAINT "PK_PROCESO_CONTRATACION" PRIMARY KEY (id);
 _   ALTER TABLE ONLY certificacion.proceso_contratacion DROP CONSTRAINT "PK_PROCESO_CONTRATACION";
       certificacion         postgres    false    291            �           2606    24637    contrato PK_CON.TRS_CONTRATO 
   CONSTRAINT     ^   ALTER TABLE ONLY contrato.contrato
    ADD CONSTRAINT "PK_CON.TRS_CONTRATO" PRIMARY KEY (id);
 J   ALTER TABLE ONLY contrato.contrato DROP CONSTRAINT "PK_CON.TRS_CONTRATO";
       contrato         postgres    false    273            �           2606    24639    volumen_ejecutado PK_MOVIMIENTO 
   CONSTRAINT     a   ALTER TABLE ONLY contrato.volumen_ejecutado
    ADD CONSTRAINT "PK_MOVIMIENTO" PRIMARY KEY (id);
 M   ALTER TABLE ONLY contrato.volumen_ejecutado DROP CONSTRAINT "PK_MOVIMIENTO";
       contrato         postgres    false    274            �           2606    28443 (   asociacion_empresa PK_ASOCIACION_EMPRESA 
   CONSTRAINT     i   ALTER TABLE ONLY gestion.asociacion_empresa
    ADD CONSTRAINT "PK_ASOCIACION_EMPRESA" PRIMARY KEY (id);
 U   ALTER TABLE ONLY gestion.asociacion_empresa DROP CONSTRAINT "PK_ASOCIACION_EMPRESA";
       gestion         postgres    false    314            �           2606    28455    cisterna PK_CISTERNA 
   CONSTRAINT     U   ALTER TABLE ONLY gestion.cisterna
    ADD CONSTRAINT "PK_CISTERNA" PRIMARY KEY (id);
 A   ALTER TABLE ONLY gestion.cisterna DROP CONSTRAINT "PK_CISTERNA";
       gestion         postgres    false    316            �           2606    28466    conductor PK_CONDUCTOR 
   CONSTRAINT     W   ALTER TABLE ONLY gestion.conductor
    ADD CONSTRAINT "PK_CONDUCTOR" PRIMARY KEY (id);
 C   ALTER TABLE ONLY gestion.conductor DROP CONSTRAINT "PK_CONDUCTOR";
       gestion         postgres    false    318            �           2606    28477    empresa PK_EMPRESA 
   CONSTRAINT     S   ALTER TABLE ONLY gestion.empresa
    ADD CONSTRAINT "PK_EMPRESA" PRIMARY KEY (id);
 ?   ALTER TABLE ONLY gestion.empresa DROP CONSTRAINT "PK_EMPRESA";
       gestion         postgres    false    320            �           2606    28490 $   empresa_cisterna PK_EMPRESA_CISTERNA 
   CONSTRAINT     e   ALTER TABLE ONLY gestion.empresa_cisterna
    ADD CONSTRAINT "PK_EMPRESA_CISTERNA" PRIMARY KEY (id);
 Q   ALTER TABLE ONLY gestion.empresa_cisterna DROP CONSTRAINT "PK_EMPRESA_CISTERNA";
       gestion         postgres    false    322            �           2606    28513 &   empresa_conductor PK_EMPRESA_CONDUCTOR 
   CONSTRAINT     g   ALTER TABLE ONLY gestion.empresa_conductor
    ADD CONSTRAINT "PK_EMPRESA_CONDUCTOR" PRIMARY KEY (id);
 S   ALTER TABLE ONLY gestion.empresa_conductor DROP CONSTRAINT "PK_EMPRESA_CONDUCTOR";
       gestion         postgres    false    324            �           2606    26831    lote PK_GES.TRS_LOTE 
   CONSTRAINT     U   ALTER TABLE ONLY gestion.lote
    ADD CONSTRAINT "PK_GES.TRS_LOTE" PRIMARY KEY (id);
 A   ALTER TABLE ONLY gestion.lote DROP CONSTRAINT "PK_GES.TRS_LOTE";
       gestion         postgres    false    287            �           2606    26835    tramo PK_GES.TRS_TRAMO 
   CONSTRAINT     W   ALTER TABLE ONLY gestion.tramo
    ADD CONSTRAINT "PK_GES.TRS_TRAMO" PRIMARY KEY (id);
 C   ALTER TABLE ONLY gestion.tramo DROP CONSTRAINT "PK_GES.TRS_TRAMO";
       gestion         postgres    false    288            �           2606    28479 $   empresa UN_EMPRESA_RAZON_SOCIAL_PAIS 
   CONSTRAINT     p   ALTER TABLE ONLY gestion.empresa
    ADD CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS" UNIQUE (razon_social, pais);
 Q   ALTER TABLE ONLY gestion.empresa DROP CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS";
       gestion         postgres    false    320    320            �           2606    30858    tramo UN_GESTION_TRAMO 
   CONSTRAINT     �   ALTER TABLE ONLY gestion.tramo
    ADD CONSTRAINT "UN_GESTION_TRAMO" UNIQUE (punto_recepcion, frontera, punto_destino, tarifa, id_lote, es_principal);
 C   ALTER TABLE ONLY gestion.tramo DROP CONSTRAINT "UN_GESTION_TRAMO";
       gestion         postgres    false    288    288    288    288    288    288            �           2606    28207 (   asociacion_empresa PK_ASOCIACION_EMPRESA 
   CONSTRAINT     m   ALTER TABLE ONLY "gestionV2".asociacion_empresa
    ADD CONSTRAINT "PK_ASOCIACION_EMPRESA" PRIMARY KEY (id);
 Y   ALTER TABLE ONLY "gestionV2".asociacion_empresa DROP CONSTRAINT "PK_ASOCIACION_EMPRESA";
    	   gestionV2         postgres    false    301            �           2606    28197    cisterna PK_CISTERNA 
   CONSTRAINT     Y   ALTER TABLE ONLY "gestionV2".cisterna
    ADD CONSTRAINT "PK_CISTERNA" PRIMARY KEY (id);
 E   ALTER TABLE ONLY "gestionV2".cisterna DROP CONSTRAINT "PK_CISTERNA";
    	   gestionV2         postgres    false    298            �           2606    28200    conductor PK_CONDUCTOR 
   CONSTRAINT     [   ALTER TABLE ONLY "gestionV2".conductor
    ADD CONSTRAINT "PK_CONDUCTOR" PRIMARY KEY (id);
 G   ALTER TABLE ONLY "gestionV2".conductor DROP CONSTRAINT "PK_CONDUCTOR";
    	   gestionV2         postgres    false    299            �           2606    28193    empresa PK_EMPRESA 
   CONSTRAINT     W   ALTER TABLE ONLY "gestionV2".empresa
    ADD CONSTRAINT "PK_EMPRESA" PRIMARY KEY (id);
 C   ALTER TABLE ONLY "gestionV2".empresa DROP CONSTRAINT "PK_EMPRESA";
    	   gestionV2         postgres    false    297            �           2606    28203 $   empresa_cisterna PK_EMPRESA_CISTERNA 
   CONSTRAINT     i   ALTER TABLE ONLY "gestionV2".empresa_cisterna
    ADD CONSTRAINT "PK_EMPRESA_CISTERNA" PRIMARY KEY (id);
 U   ALTER TABLE ONLY "gestionV2".empresa_cisterna DROP CONSTRAINT "PK_EMPRESA_CISTERNA";
    	   gestionV2         postgres    false    300            �           2606    28210 &   empresa_conductor PK_EMPRESA_CONDUCTOR 
   CONSTRAINT     k   ALTER TABLE ONLY "gestionV2".empresa_conductor
    ADD CONSTRAINT "PK_EMPRESA_CONDUCTOR" PRIMARY KEY (id);
 W   ALTER TABLE ONLY "gestionV2".empresa_conductor DROP CONSTRAINT "PK_EMPRESA_CONDUCTOR";
    	   gestionV2         postgres    false    302            �           2606    28394    lote PK_GES.TRS_LOTE 
   CONSTRAINT     Y   ALTER TABLE ONLY "gestionV2".lote
    ADD CONSTRAINT "PK_GES.TRS_LOTE" PRIMARY KEY (id);
 E   ALTER TABLE ONLY "gestionV2".lote DROP CONSTRAINT "PK_GES.TRS_LOTE";
    	   gestionV2         postgres    false    310            �           2606    28423    tramo PK_GES.TRS_TRAMO 
   CONSTRAINT     [   ALTER TABLE ONLY "gestionV2".tramo
    ADD CONSTRAINT "PK_GES.TRS_TRAMO" PRIMARY KEY (id);
 G   ALTER TABLE ONLY "gestionV2".tramo DROP CONSTRAINT "PK_GES.TRS_TRAMO";
    	   gestionV2         postgres    false    312            �           2606    28195 $   empresa UN_EMPRESA_RAZON_SOCIAL_PAIS 
   CONSTRAINT     t   ALTER TABLE ONLY "gestionV2".empresa
    ADD CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS" UNIQUE (razon_social, pais);
 U   ALTER TABLE ONLY "gestionV2".empresa DROP CONSTRAINT "UN_EMPRESA_RAZON_SOCIAL_PAIS";
    	   gestionV2         postgres    false    297    297            �           2606    28425    tramo UN_GESTION_TRAMO 
   CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".tramo
    ADD CONSTRAINT "UN_GESTION_TRAMO" UNIQUE (punto_recepcion, frontera, punto_destino, tarifa, id_lote, es_principal);
 G   ALTER TABLE ONLY "gestionV2".tramo DROP CONSTRAINT "UN_GESTION_TRAMO";
    	   gestionV2         postgres    false    312    312    312    312    312    312            �           2606    25384    parametro PK_Parametro 
   CONSTRAINT     Y   ALTER TABLE ONLY parametro.parametro
    ADD CONSTRAINT "PK_Parametro" PRIMARY KEY (id);
 E   ALTER TABLE ONLY parametro.parametro DROP CONSTRAINT "PK_Parametro";
    	   parametro         postgres    false    277            �           1259    27553 .   IXFK_CERTIFICACIONCERTIFICACION_PRESUPUESTARIA    INDEX     }   CREATE INDEX "IXFK_CERTIFICACIONCERTIFICACION_PRESUPUESTARIA" ON certificacion.certificacion USING btree (id_certificacion);
 K   DROP INDEX certificacion."IXFK_CERTIFICACIONCERTIFICACION_PRESUPUESTARIA";
       certificacion         postgres    false    294            �           1259    27554 '   IXFK_CERTIFICACION_PROCESO_CONTRATACION    INDEX     }   CREATE INDEX "IXFK_CERTIFICACION_PROCESO_CONTRATACION" ON certificacion.certificacion USING btree (id_proceso_contratacion);
 D   DROP INDEX certificacion."IXFK_CERTIFICACION_PROCESO_CONTRATACION";
       certificacion         postgres    false    294            �           1259    27552 <   IXFK_CER_trs_certificacion_CER_trs_partida_presupuestaria_v2    INDEX     �   CREATE INDEX "IXFK_CER_trs_certificacion_CER_trs_partida_presupuestaria_v2" ON certificacion.certificacion USING btree (id_partida);
 Y   DROP INDEX certificacion."IXFK_CER_trs_certificacion_CER_trs_partida_presupuestaria_v2";
       certificacion         postgres    false    294            �           1259    24640 (   IXFK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO    INDEX     q   CREATE INDEX "IXFK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO" ON contrato.volumen_ejecutado USING btree (id_contrato);
 @   DROP INDEX contrato."IXFK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO";
       contrato         postgres    false    274            �           1259    28444    IXFK_ASOCIACION_EMPRESA_EMPRESA    INDEX     g   CREATE INDEX "IXFK_ASOCIACION_EMPRESA_EMPRESA" ON gestion.asociacion_empresa USING btree (id_empresa);
 6   DROP INDEX gestion."IXFK_ASOCIACION_EMPRESA_EMPRESA";
       gestion         postgres    false    314            �           1259    28501    IXFK_EMPRESA_CISTERNA_CISTERNA    INDEX     e   CREATE INDEX "IXFK_EMPRESA_CISTERNA_CISTERNA" ON gestion.empresa_cisterna USING btree (id_cisterna);
 5   DROP INDEX gestion."IXFK_EMPRESA_CISTERNA_CISTERNA";
       gestion         postgres    false    322            �           1259    28502    IXFK_EMPRESA_CISTERNA_EMPRESA    INDEX     c   CREATE INDEX "IXFK_EMPRESA_CISTERNA_EMPRESA" ON gestion.empresa_cisterna USING btree (id_empresa);
 4   DROP INDEX gestion."IXFK_EMPRESA_CISTERNA_EMPRESA";
       gestion         postgres    false    322            �           1259    28519     IXFK_EMPRESA_CONDUCTOR_CONDUCTOR    INDEX     i   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_CONDUCTOR" ON gestion.empresa_conductor USING btree (id_conductor);
 7   DROP INDEX gestion."IXFK_EMPRESA_CONDUCTOR_CONDUCTOR";
       gestion         postgres    false    324            �           1259    28520    IXFK_EMPRESA_CONDUCTOR_EMPRESA    INDEX     e   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_EMPRESA" ON gestion.empresa_conductor USING btree (id_empresa);
 5   DROP INDEX gestion."IXFK_EMPRESA_CONDUCTOR_EMPRESA";
       gestion         postgres    false    324            �           1259    26838    IXFK_TRAMO_LOTE    INDEX     G   CREATE INDEX "IXFK_TRAMO_LOTE" ON gestion.tramo USING btree (id_lote);
 &   DROP INDEX gestion."IXFK_TRAMO_LOTE";
       gestion         postgres    false    288            �           1259    28283    IXFK_ASOCIACION_EMPRESA_EMPRESA    INDEX     k   CREATE INDEX "IXFK_ASOCIACION_EMPRESA_EMPRESA" ON "gestionV2".asociacion_empresa USING btree (id_empresa);
 :   DROP INDEX "gestionV2"."IXFK_ASOCIACION_EMPRESA_EMPRESA";
    	   gestionV2         postgres    false    301            �           1259    28204    IXFK_EMPRESA_CISTERNA_CISTERNA    INDEX     i   CREATE INDEX "IXFK_EMPRESA_CISTERNA_CISTERNA" ON "gestionV2".empresa_cisterna USING btree (id_cisterna);
 9   DROP INDEX "gestionV2"."IXFK_EMPRESA_CISTERNA_CISTERNA";
    	   gestionV2         postgres    false    300            �           1259    28205    IXFK_EMPRESA_CISTERNA_EMPRESA    INDEX     g   CREATE INDEX "IXFK_EMPRESA_CISTERNA_EMPRESA" ON "gestionV2".empresa_cisterna USING btree (id_empresa);
 8   DROP INDEX "gestionV2"."IXFK_EMPRESA_CISTERNA_EMPRESA";
    	   gestionV2         postgres    false    300            �           1259    28211     IXFK_EMPRESA_CONDUCTOR_CONDUCTOR    INDEX     m   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_CONDUCTOR" ON "gestionV2".empresa_conductor USING btree (id_conductor);
 ;   DROP INDEX "gestionV2"."IXFK_EMPRESA_CONDUCTOR_CONDUCTOR";
    	   gestionV2         postgres    false    302            �           1259    28212    IXFK_EMPRESA_CONDUCTOR_EMPRESA    INDEX     i   CREATE INDEX "IXFK_EMPRESA_CONDUCTOR_EMPRESA" ON "gestionV2".empresa_conductor USING btree (id_empresa);
 9   DROP INDEX "gestionV2"."IXFK_EMPRESA_CONDUCTOR_EMPRESA";
    	   gestionV2         postgres    false    302            �           1259    28431    IXFK_TRAMO_LOTE    INDEX     K   CREATE INDEX "IXFK_TRAMO_LOTE" ON "gestionV2".tramo USING btree (id_lote);
 *   DROP INDEX "gestionV2"."IXFK_TRAMO_LOTE";
    	   gestionV2         postgres    false    312            �           2620    27582 =   certificacion trigger_actualizar_vm_certificaciones_dashboard    TRIGGER     �   CREATE TRIGGER trigger_actualizar_vm_certificaciones_dashboard AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON certificacion.certificacion FOR EACH STATEMENT EXECUTE PROCEDURE certificacion.actualizar_vm_certificaciones_dashboard();
 ]   DROP TRIGGER trigger_actualizar_vm_certificaciones_dashboard ON certificacion.certificacion;
       certificacion       postgres    false    294    345            �           2620    27581 0   partida trigger_actualizar_vm_partidas_dashboard    TRIGGER     �   CREATE TRIGGER trigger_actualizar_vm_partidas_dashboard AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON certificacion.partida FOR EACH STATEMENT EXECUTE PROCEDURE certificacion.actualizar_vm_partidas_dashboard();
 P   DROP TRIGGER trigger_actualizar_vm_partidas_dashboard ON certificacion.partida;
       certificacion       postgres    false    293    343            �           2620    29739     contrato actualizar_vm_contratos    TRIGGER     �   CREATE TRIGGER actualizar_vm_contratos AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON contrato.contrato FOR EACH STATEMENT EXECUTE PROCEDURE contrato.actualizar_vm_contratos();
 ;   DROP TRIGGER actualizar_vm_contratos ON contrato.contrato;
       contrato       postgres    false    349    273            �           2620    29740 )   volumen_ejecutado actualizar_vm_contratos    TRIGGER     �   CREATE TRIGGER actualizar_vm_contratos AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE ON contrato.volumen_ejecutado FOR EACH STATEMENT EXECUTE PROCEDURE contrato.actualizar_vm_contratos();
 D   DROP TRIGGER actualizar_vm_contratos ON contrato.volumen_ejecutado;
       contrato       postgres    false    274    349            �           2606    27560 J   certificacion FK_CERTIFICACION_PRESUPUESTARIA_CERTIFICACION_PRESUPUESTARIA    FK CONSTRAINT     �   ALTER TABLE ONLY certificacion.certificacion
    ADD CONSTRAINT "FK_CERTIFICACION_PRESUPUESTARIA_CERTIFICACION_PRESUPUESTARIA" FOREIGN KEY (id_certificacion) REFERENCES certificacion.certificacion(id);
 }   ALTER TABLE ONLY certificacion.certificacion DROP CONSTRAINT "FK_CERTIFICACION_PRESUPUESTARIA_CERTIFICACION_PRESUPUESTARIA";
       certificacion       postgres    false    294    2993    294            �           2606    27555 M   certificacion FK_CER_trs_certificacion_presupuestaria_CER_trs_partida_presupu    FK CONSTRAINT     �   ALTER TABLE ONLY certificacion.certificacion
    ADD CONSTRAINT "FK_CER_trs_certificacion_presupuestaria_CER_trs_partida_presupu" FOREIGN KEY (id_partida) REFERENCES certificacion.partida(id);
 �   ALTER TABLE ONLY certificacion.certificacion DROP CONSTRAINT "FK_CER_trs_certificacion_presupuestaria_CER_trs_partida_presupu";
       certificacion       postgres    false    293    294    2988            �           2606    24641 8   volumen_ejecutado FK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO    FK CONSTRAINT     �   ALTER TABLE ONLY contrato.volumen_ejecutado
    ADD CONSTRAINT "FK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO" FOREIGN KEY (id_contrato) REFERENCES contrato.contrato(id);
 f   ALTER TABLE ONLY contrato.volumen_ejecutado DROP CONSTRAINT "FK_CON_TRS_MOVIMIENTO_CON_TRS_CONTRATO";
       contrato       postgres    false    2972    273    274            �           2606    28491 -   empresa_cisterna FK_EMPRESA_CISTERNA_CISTERNA    FK CONSTRAINT     �   ALTER TABLE ONLY gestion.empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA" FOREIGN KEY (id_cisterna) REFERENCES gestion.cisterna(id);
 Z   ALTER TABLE ONLY gestion.empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA";
       gestion       postgres    false    3024    322    316            �           2606    28496 ,   empresa_cisterna FK_EMPRESA_CISTERNA_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY gestion.empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES gestion.empresa(id);
 Y   ALTER TABLE ONLY gestion.empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA";
       gestion       postgres    false    3028    322    320            �           2606    28514 .   empresa_conductor FK_EMPRESA_CONDUCTOR_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY gestion.empresa_conductor
    ADD CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES gestion.empresa(id);
 [   ALTER TABLE ONLY gestion.empresa_conductor DROP CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA";
       gestion       postgres    false    324    3028    320            �           2606    26839    tramo FK_TRAMO_LOTE    FK CONSTRAINT     u   ALTER TABLE ONLY gestion.tramo
    ADD CONSTRAINT "FK_TRAMO_LOTE" FOREIGN KEY (id_lote) REFERENCES gestion.lote(id);
 @   ALTER TABLE ONLY gestion.tramo DROP CONSTRAINT "FK_TRAMO_LOTE";
       gestion       postgres    false    287    2979    288            �           2606    28213 -   empresa_cisterna FK_EMPRESA_CISTERNA_CISTERNA    FK CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA" FOREIGN KEY (id_cisterna) REFERENCES "gestionV2".cisterna(id);
 ^   ALTER TABLE ONLY "gestionV2".empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_CISTERNA";
    	   gestionV2       postgres    false    2999    298    300            �           2606    28218 ,   empresa_cisterna FK_EMPRESA_CISTERNA_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".empresa_cisterna
    ADD CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES "gestionV2".empresa(id);
 ]   ALTER TABLE ONLY "gestionV2".empresa_cisterna DROP CONSTRAINT "FK_EMPRESA_CISTERNA_EMPRESA";
    	   gestionV2       postgres    false    300    2995    297            �           2606    28223 .   empresa_conductor FK_EMPRESA_CONDUCTOR_EMPRESA    FK CONSTRAINT     �   ALTER TABLE ONLY "gestionV2".empresa_conductor
    ADD CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA" FOREIGN KEY (id_empresa) REFERENCES "gestionV2".empresa(id);
 _   ALTER TABLE ONLY "gestionV2".empresa_conductor DROP CONSTRAINT "FK_EMPRESA_CONDUCTOR_EMPRESA";
    	   gestionV2       postgres    false    297    302    2995            �           2606    28426    tramo FK_TRAMO_LOTE    FK CONSTRAINT     }   ALTER TABLE ONLY "gestionV2".tramo
    ADD CONSTRAINT "FK_TRAMO_LOTE" FOREIGN KEY (id_lote) REFERENCES "gestionV2".lote(id);
 D   ALTER TABLE ONLY "gestionV2".tramo DROP CONSTRAINT "FK_TRAMO_LOTE";
    	   gestionV2       postgres    false    312    3014    310            �           0    34970    certificacion_dashboard    MATERIALIZED VIEW DATA     A   REFRESH MATERIALIZED VIEW certificacion.certificacion_dashboard;
            certificacion       postgres    false    327    3237            �           0    34784    partida_dashboard    MATERIALIZED VIEW DATA     ;   REFRESH MATERIALIZED VIEW certificacion.partida_dashboard;
            certificacion       postgres    false    326    3237            �           0    31104 	   contratos    MATERIALIZED VIEW DATA     .   REFRESH MATERIALIZED VIEW contrato.contratos;
            contrato       postgres    false    325    3237           
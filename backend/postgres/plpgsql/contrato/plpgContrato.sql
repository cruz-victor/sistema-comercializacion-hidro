CREATE OR REPLACE FUNCTION contrato.actualizar_vm_contratos()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
    refresh materialized view contrato.contratos;
    return null;
end $function$
;

CREATE OR REPLACE FUNCTION contrato.actualizar_vm_partidas_dashboard()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
    refresh materialized view certificacion.partida_dashboard;
    return null;
end $function$
;

CREATE OR REPLACE FUNCTION contrato.obtener_total_de_contratos_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$

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
$function$
;

CREATE OR REPLACE FUNCTION contrato.obtener_totales_dashboard(p_tipo character varying, p_ambito character varying, p_gestion integer)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
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
$function$
;

CREATE OR REPLACE FUNCTION contrato.obtener_totales_general_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer)
 RETURNS json
 LANGUAGE plpgsql
AS $function$
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
$function$
;

CREATE OR REPLACE FUNCTION contrato.obtener_totales_por_tipo_ambito(p_tipo character varying, p_ambito character varying, p_gestion integer, p_sectores character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
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
$function$
;

CREATE OR REPLACE FUNCTION contrato.sumar_monto_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer)
 RETURNS json
 LANGUAGE plpgsql
AS $function$

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
$function$
;

CREATE OR REPLACE FUNCTION contrato.sumar_volumen_por_sector(p_tipo character varying, p_ambito character varying, p_sector character varying, p_gestion integer)
 RETURNS json
 LANGUAGE plpgsql
AS $function$

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
$function$
;

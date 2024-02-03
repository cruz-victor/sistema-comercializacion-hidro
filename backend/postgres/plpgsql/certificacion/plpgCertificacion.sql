CREATE OR REPLACE FUNCTION certificacion.actualizar_vm_certificaciones_dashboard()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
    refresh materialized view certificacion.certificacion_dashboard;
    return null;
end $function$
;

CREATE OR REPLACE FUNCTION certificacion.actualizar_vm_partidas_dashboard()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
    refresh materialized view certificacion.partida_dashboard;
    return null;
end $function$
;

CREATE OR REPLACE FUNCTION certificacion.obtener_gestion_movimiento(p_id_certificacion bigint, p_gestion integer)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
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
$function$
;

CREATE OR REPLACE FUNCTION certificacion.obtener_monto_ejecutado_por_categoria(p_categoria character varying, p_gestion integer)
 RETURNS numeric
 LANGUAGE plpgsql
AS $function$
declare 
	volumen_ejecutado numeric;	
begin	
	return 0.000::decimal;
end 
$function$
;

CREATE OR REPLACE FUNCTION certificacion.obtener_monto_total_certificado(tipo character varying, ambito character varying, sector character varying, gestion integer)
 RETURNS numeric
 LANGUAGE plpgsql
AS $function$
declare 
	volumen_ejecutado numeric;	
begin	
	return 0.000::decimal;
end 
$function$
;

CREATE OR REPLACE FUNCTION certificacion.obtener_totales_certificaciones_dashboard(p_gestion integer)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
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
$function$
;

CREATE OR REPLACE FUNCTION certificacion.obtener_totales_partidas_dashboard(p_gestion integer)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
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
$function$
;

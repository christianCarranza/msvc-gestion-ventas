package org.carranza.msvc.gestion.ventas.api.utils;

public class ConstantesUtil {

  private ConstantesUtil() {
  }

  public static final String TITULO_ALERTAS = "Información";
  public static final String FORMATO_FECHA_CORTO = "dd/MM/yyyy";
  public static final String FORMATO_FECHA_RAYA_CORTO = "dd-MM-yyyy";
  public static final String FORMATO_FECHA_LARGO = "dd/MM/yyyy HH:mm:ss";
  public static final Integer IND_PRFJ_TIPO_PERSONA = 2;
  public static final Integer IND_CRRLTVO_PERSONA_NAT = 1;
  public static final Integer IND_CRRLTVO_PERSONA_JRICA = 2;
  public static final Integer IND_EDO_ACTIVO = 1;
  public static final Integer IND_EDO_INACTIVO = 2;
  public static final Integer IND_ACT_ACTIVO = 0;
  public static final Integer IND_ACT_INACTIVO = 9;
  public static final String EXCEL_MEDIA_TYPE = "application/vnd.ms-excel";
  public static final String EXCEL_HEADER = "attachment; filename=reporte.xlsx";

  //Path
  public static final String  API_ARTICULO="/shared/v1/articulo";
  public static final String  API_CATEGORIA="/almacen/v1/categoria";
  public static final String  API_PERSONA="/shared/v1/persona";
  public static final String  API_VENTAS="/venta/v1/venta";
  public static final String  API_INGRESOS="/almacen/v1/ingreso";


  public static final String  API_USUARIOS="/private/v1/usuario";
  public static final String  API_ROLES="/private/v1/rol";

}

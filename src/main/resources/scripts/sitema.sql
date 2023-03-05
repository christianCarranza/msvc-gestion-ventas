--Tabla categoría
create table categorias(
                           idcategoria             varchar2(32)  NOT NULL PRIMARY KEY,
                           nombre                  varchar2(50)  not null unique,
                           descripcion             varchar2(256) null,
                           estado                  number       default(1) not null,
                           Fecha_Creacion          Date         Default(Sysdate) Not Null,
                           Usuario_Creacion        Varchar2(32)  Not Null,
                           Fecha_Modificacion      Date         Null,
                           Usuario_Modificacion    Varchar2(32)  Null
);


--Tabla artículo
create table articulos(
                          idarticulo              varchar2(32)  NOT NULL PRIMARY KEY,
                          idcategoria             varchar2(32) not null,
                          codigo                  varchar2(50) null,
                          nombre                  varchar2(100) not null unique,
                          precio_venta            number(11,2) not null,
                          stock                   number not null,
                          descripcion             varchar2(256) null,
                          estado                  number       default(1) not null,
                          Fecha_Creacion          Date         Default(Sysdate) Not Null,
                          Usuario_Creacion        Varchar2(32)  Not Null,
                          Fecha_Modificacion      Date         Null,
                          Usuario_Modificacion    Varchar2(32)  Null,
                          FOREIGN KEY (idcategoria) REFERENCES categorias(idcategoria)
);

--Tabla persona
create table personas(
                         idpersona               varchar2(32)  NOT NULL PRIMARY KEY,
                         tipo_persona            varchar2(20) not null,
                         nombre                  varchar2(100) not null,
                         tipo_documento          varchar2(20) null,
                         num_documento           varchar2(20) null,
                         direccion               varchar2(70) null,
                         telefono                varchar2(20) null,
                         email                   varchar2(50) null,
                         estado                  number       default(1) not null,
                         Fecha_Creacion          Date         Default(Sysdate) Not Null,
                         Usuario_Creacion        Varchar2(32) Not Null,
                         Fecha_Modificacion      Date         Null,
                         Usuario_Modificacion    Varchar2(32) Null
);

--Tabla rol
create table roles(
                      idrol                   varchar2(32)  NOT NULL PRIMARY KEY,
                      nombre                  varchar2(30)  not null,
                      descripcion             varchar2(100) null,
                      estado                  number        default(1) not null,
                      Fecha_Creacion          Date          Default(Sysdate) Not Null,
                      Usuario_Creacion        Varchar2(32)  Not Null,
                      Fecha_Modificacion      Date          Null,
                      Usuario_Modificacion    Varchar2(32)  Null
);

--Tabla usuario
create table usuarios(
                         idusuario               varchar2(32)  NOT NULL PRIMARY KEY,
                         nombre                  varchar2(100) not null,
                         tipo_documento          varchar2(20) null,
                         num_documento           varchar2(20) null,
                         direccion               varchar2(70) null,
                         telefono                varchar2(20) null,
                         email                   varchar2(50) not null,
                         password                VARCHAR2(80 BYTE) NOT NULL ,
                         estado                  number        default(1) not null,
                         Fecha_Creacion          Date          Default(Sysdate) Not Null,
                         Usuario_Creacion        Varchar2(32)  Not Null,
                         Fecha_Modificacion      Date          Null,
                         Usuario_Modificacion    Varchar2(32)  Null
);

-- tabla detalle rol - usuario

CREATE TABLE TBL_USUARIO_ROL(
                                USUARIO_AUTHORITY_ID    NUMBER(4,0) NOT NULL PRIMARY KEY,
                                idusuario               varchar2(32) NOT NULL REFERENCES usuarios(idusuario),
                                idrol                   varchar2(32) NOT NULL REFERENCES roles(idrol),
                                estado                  number        default(1) not null,
                                Fecha_Creacion          Date          Default(Sysdate) Not Null,
                                Usuario_Creacion        Varchar2(32)  Not Null,
                                Fecha_Modificacion      Date          Null,
                                Usuario_Modificacion    Varchar2(32)  Null
);


--Tabla ingreso
create table ingresos(
                         idingreso               varchar2(32)  NOT NULL PRIMARY KEY,
                         idproveedor             varchar2(32) not null,
                         idusuario               varchar2(32) not null,
                         tipo_comprobante        varchar2(20) not null,
                         serie_comprobante       varchar2(7) null,
                         num_comprobante         varchar2 (10) not null,
                         fecha_hora              Date not null,
                         impuesto                number (4,2) not null,
                         total                   number (11,2) not null,
                         estado                  number        default(1) not null,
                         Fecha_Creacion          Date          Default(Sysdate) Not Null,
                         Usuario_Creacion        Varchar2(32)  Not Null,
                         Fecha_Modificacion      Date          Null,
                         Usuario_Modificacion    Varchar2(32)  Null,
                         FOREIGN KEY (idproveedor) REFERENCES personas (idpersona),
                         FOREIGN KEY (idusuario) REFERENCES usuarios (idusuario)
);

--Tabla detalle_ingreso
create table detalle_ingreso(
                                iddetalle_ingreso       varchar2(32)  NOT NULL PRIMARY KEY,
                                idingreso               varchar2(32) not null,
                                idarticulo              varchar2(32) not null,
                                cantidad                number not null,
                                precio                  number(11,2) not null,
                                estado                  number        default(1) not null,
                                Fecha_Creacion          Date          Default(Sysdate) Not Null,
                                Usuario_Creacion        Varchar2(32)  Not Null,
                                Fecha_Modificacion      Date          Null,
                                Usuario_Modificacion    Varchar2(32)  Null,
                                FOREIGN KEY (idingreso) REFERENCES ingresos (idingreso) ON DELETE CASCADE,
                                FOREIGN KEY (idarticulo) REFERENCES articulos (idarticulo)
);


--Tabla venta
create table ventas(
                       idventa                 varchar2(32)  NOT NULL PRIMARY KEY,
                       idcliente               varchar2(32) not null,
                       idusuario               varchar2(32) not null,
                       tipo_comprobante        varchar2(20) not null,
                       serie_comprobante       varchar2(7) null,
                       num_comprobante         varchar2 (10) not null,
                       fecha_hora              Date     not null,
                       impuesto                number (4,2) not null,
                       total                   number (11,2) not null,
                       estado                  number        default(1) not null,
                       Fecha_Creacion          Date          Default(Sysdate) Not Null,
                       Usuario_Creacion        Varchar2(32)  Not Null,
                       Fecha_Modificacion      Date          Null,
                       Usuario_Modificacion    Varchar2(32)  Null,
                       FOREIGN KEY (idcliente) REFERENCES personas (idpersona),
                       FOREIGN KEY (idusuario) REFERENCES usuarios (idusuario)
);

--Tabla detalle_venta
create table detalle_venta(
                              iddetalle_venta         varchar2(32)  NOT NULL PRIMARY KEY,
                              idventa                 varchar2(32) not null,
                              idarticulo              varchar2(32) not null,
                              cantidad                number NOT NULL,
                              precio                  number(11, 2) NOT NULL,
                              descuento               number(11, 2) NOT NULL,
                              FOREIGN KEY (idventa) REFERENCES ventas (idventa) ON DELETE CASCADE,
                              FOREIGN KEY (idarticulo) REFERENCES articulos (idarticulo)
);


Insert into ROLES (ROL_ID,ROL,CODIGO) values ('1','Administrador','ADMIN');
Insert into ESTADOS (ESTADO_ID,ESTADO) values ('I','INACTIVO');
Insert into USUARIOS (USUARIO_ID,TIPO_IDENTIFICACION,NUMERO_IDENTIFICACION,PRIMER_NOMBRE,SEGUNDO_NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CORREO_ELECTRONICO,TELEFONO,NOMBRE_USUARIO,CONTRASENA,ROL_ID,ESTADO_ID,FECHA_CREACION,FECHA_MODIFICACION) values ('1','CC','111','Admin',null,'Test',null,'admin.test@email.com','111','adminTest','secretClv','1','I', CURRENT_DATE - INTERVAL '5' DAY,null);
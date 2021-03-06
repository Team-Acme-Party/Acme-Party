/** INSERTS SQL **/

--Perfiles
INSERT INTO users(username,password,enabled) VALUES ('admin','admin',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('propietario1','propietario1',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('propietario2','propietario2',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('cliente1','cliente1',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('cliente2','cliente2',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('patrocinador1','patrocinador1',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('patrocinador2','patrocinador2',TRUE);
INSERT INTO authorities VALUES ('admin','admin');
INSERT INTO authorities VALUES ('propietario1','propietario');
INSERT INTO authorities VALUES ('propietario2','propietario');
INSERT INTO authorities VALUES ('cliente1','cliente');
INSERT INTO authorities VALUES ('cliente2','cliente');
INSERT INTO authorities VALUES ('patrocinador1','patrocinador');
INSERT INTO authorities VALUES ('patrocinador2','patrocinador');

--Usuarios
INSERT INTO administradores(id, nombre, apellidos, email, telefono, foto, username) 
	VALUES(1, 'Admin', 'DB', 'admin@admin.com', '666999666', 'https://oposicionesueblog.files.wordpress.com/2019/02/funcionario.jpg?w=825','admin');
	
INSERT INTO propietarios(id, nombre, apellidos, email, telefono, foto, username) 
	VALUES(1, 'Propietario1', 'DB', 'propietario1@prop.com', '696696696', 
	'https://definicion.de/wp-content/uploads/2015/07/adulto.jpg', 'propietario1');
	
INSERT INTO propietarios(id, nombre, apellidos, email, telefono, foto, username) 
	VALUES(2, 'Propietario2', 'DB', 'propietario2@prop.com', '696969969', 
	'https://cdn.aarp.net/content/dam/aarp/home-and-family/caregiving/2018/03/1140-sensory-changes-older-adults-esp.jpg', 'propietario2');
	
INSERT INTO clientes(id, nombre, apellidos, email, telefono, foto, descripcion_gustos, username) 
	VALUES(1, 'Cliente1', 'DB', 'cliente1@cli.com', '666999000', 
	'https://static1.diariosur.es/www/multimedia/201902/22/media/cortadas/ALVARO-ESTUDIANTE-k0XD-U70740253086TOB-984x608@Diario%20Sur.jpg', 
	'Me gusta la música rock', 'cliente1');
	
INSERT INTO clientes(id, nombre, apellidos, email, telefono, foto, descripcion_gustos, username) 
	VALUES(2, 'Cliente2', 'DB', 'cliente2@cli.com', '696123456', 
	'https://d1zv66c6p7f9ox.cloudfront.net/fotoweb/fotonoticia_20180118120033_640.jpg', 'Me gusta conocer gente nueva y la música electrónica','cliente2');
	
INSERT INTO patrocinadores(id, nombre, apellidos, email, telefono, foto, descripcion_experiencia, username) 
	VALUES(1, 'Patrocinador1', 'DB', 'patrocinador1@patr.com', '654718292', 
	'https://tendenciasdigitales.com/web/wp-content/uploads/2018/08/patrocinios2.png', '10 años patrocinando eventos juveniles', 'patrocinador1');
	
INSERT INTO patrocinadores(id, nombre, apellidos, email, telefono, foto, descripcion_experiencia, username) 
	VALUES(2, 'Patrocinador2', 'DB', 'patrocinador2@patr.com', '650987654', 
	'https://carreramedicusmundi.com/wp-content/uploads/2018/01/patrocinador-oro.jpg', '5 años patrocinando eventos deportivos', 'patrocinador2');
	

--Locales
INSERT INTO locales(id, direccion, capacidad, condiciones, imagen, decision, propietario_id) 
	VALUES(1, 'Luis Montoto 12', 100, 'Para fiestas electronicas', 
	'https://salonparafiesta.com/wp-content/uploads/2016/05/local-para-fiestas-hortaleza-1.jpg', 'ACEPTADO', 1);
INSERT INTO locales(id, direccion, capacidad, condiciones, imagen, decision, propietario_id) 
	VALUES(2, 'Eduardo Dato 33', 50, 'Para fiestas familiares', 
	'https://revistafamily.com/wp-content/uploads/2019/01/local-ideal-para-tus-fiestas.jpg', 'ACEPTADO', 1);
INSERT INTO locales(id, direccion, capacidad, condiciones, imagen, decision, propietario_id) 
	VALUES(3, 'Poligono Calonge 2', 1000, 'Para fiestas con drogas', 
	'https://salonparafiesta.com/wp-content/uploads/2018/03/alquiler-local-fiesta-privada-madrid-2.jpg', 'RECHAZADO', 1);
INSERT INTO locales(id, direccion, capacidad, condiciones, imagen, decision, propietario_id) 
	VALUES(4, 'Afan de Ribera 56', 70, 'Para fiestas de rock', 
	'https://leonocio.es/wp-content/uploads/bulk/local_a.jpg', 'ACEPTADO', 2);
INSERT INTO locales(id, direccion, capacidad, condiciones, imagen, decision, propietario_id) 
	VALUES(5, 'Avenida de la Buhaira 90', 200, 'Para fiestas adolescentes', 
	'https://roket.es/sites/default/files/foto_local.jpg', 'PENDIENTE', 2);

--Fiestas
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(1, 'Fiesta de disfraces', 'Fiesta de disfraces de todo tipo. Sin alcohol', 15, 'Venir disfrazado y en buen estado',
	'2020-05-15', '18:00', '23:00', 50, 75, 'https://welcometoibiza.com/wp-content/uploads/2018/02/Fiestas-de-disfraces-en-Ibiza.jpg', 'ACEPTADO', 1, 2);
	
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(2, 'Fiesta electronica', 'Fiesta de música electronica para mayores de edad.', 20, 'Ser mayor de edad y no traer drogas.',
	'2020-06-20', '22:00', '5:00', 200, 200, 'https://i.pinimg.com/originals/23/45/77/23457712be420ec1a113139552b091a3.jpg', 'PENDIENTE', 1, 1);
	
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(3, 'Fiesta de rock', 'Fiesta de musica rock para mayores de edad.', 20, 'Ser mayor de edad y no traer drogas.',
	'2020-07-04', '23:00', '7:00', 200, 300, 'https://welcometoibiza.com/wp-content/uploads/2018/06/fiesta-rock-nights-pikes-ibiza-welcometoibiza.jpg', 'ACEPTADO', 2, 4);
	
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(4, 'Fiesta dubstep', 'Fiesta de musica dubstep para mayores de edad.', 30, 'Ser mayor de edad y no traer drogas.',
	'2020-04-30', '23:00', '5:00', 200, 200, 'https://lh3.googleusercontent.jpg', 'ACEPTADO', 1, 1);

INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(5, 'Fiesta trap', 'Fiesta de musica trap.', 50, 'Cualquier edad y traer mucha droga.',
	'2020-09-09', '23:00', '7:00', 50, 100,
	'https://4.bp.blogspot.com/-21qlqUcqgT4/VlUSvE8qmVI/AAAAAAAAAEY/N6RA40TcsQ4/s1600/tumblr_inline_mymygb26FE1ruzwds.jpg', 'RECHAZADO', 2, 1);

INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(6, 'Fiesta post-confinamiento', 'Con precaución.', 20, 'mayores de edad',
	'2020-09-09', '23:00', '7:00', 50, 100,
	'https://img.huffingtonpost.com/asset/5e7a8eeb2400006007cea3b6.jpeg', 'ACEPTADO', 1, 1);

--Para poder comentar y valorar por cliente 1 y 2.
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(7, 'Fiesta pre-confinamiento', 'Con precaución.', 50, 'mayores de edad',
	'2020-03-09', '23:00', '7:00', 100, 100,
	'https://img.huffingtonpost.com/asset/5e7a8eeb2400006007cea3b6.jpeg', 'ACEPTADO', 1, 1);

INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, aforo, imagen, decision, cliente_id, local_id) 
	VALUES(8, 'Fiesta techno', 'Solo mayores de edad.', 50, 'Poder menear el cuello',
	'2021-06-20', '22:00', '5:00', 0, 200, 'https://i.pinimg.com/originals/23/45/77/23457712be420ec1a113139552b091a3.jpg', 'PENDIENTE', 1, 1);


--Solicitudes de asistencia a fiestas
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(1, 1, 1, 'ACEPTADO');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(2, 1, 2, 'PENDIENTE');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(3, 3, 1, 'PENDIENTE');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(4, 3, 2, 'ACEPTADO');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(5, 4, 2, 'ACEPTADO');

--Para poder comentar y valorar por cliente 1 y 2.
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(6, 7, 1, 'ACEPTADO');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(7, 7, 2, 'ACEPTADO');


--Anuncios
INSERT INTO anuncios(id, imagen, patrocinador_id, fiesta_id, decision) 
	VALUES(1, 'https://image.freepik.com/psd-gratis/plantilla-creativa-banner-fiesta-navidad_23-2147991012.jpg', 1, 1, 'ACEPTADO');
	
INSERT INTO anuncios(id, imagen, patrocinador_id, fiesta_id, decision) 
	VALUES(2, 'https://image.freepik.com/vector-gratis/banner-fiesta-estilo-neon_92863-16.jpg', 1, 1, 'RECHAZADO');
	
INSERT INTO anuncios(id, imagen, patrocinador_id, local_id, decision) 
	VALUES(3, 'https://www.rottentomatoes.com/assets/pizza-pie/images/icons/global/cf-lg.3c29eff04f2.png', 2, 1, 'ACEPTADO');

INSERT INTO anuncios(id, imagen, patrocinador_id, fiesta_id, decision) 
	VALUES(4, 'https://www.safaridiscoclub.com/wp/wp-content/uploads/2018/04/ven-tu-fiesta-header-banner.png', 1, 1, 'PENDIENTE');
	
INSERT INTO anuncios(id, imagen, patrocinador_id, local_id, decision) 
	VALUES(5, 'https://png.pngtree.com/thumb_back/fw800/background/20190827/pngtree-happy-halloween-party-night-banner-background-image_307080.jpg', 1, 1, 'PENDIENTE');

	
--Comentarios
INSERT INTO comentarios(id, cuerpo, fecha, cliente_id, local_id) 
	VALUES(1, 'De los mejores locales en los que he estado. Repetiré.', '2020-04-20', 1, 1);
INSERT INTO comentarios(id, cuerpo, fecha, cliente_id, fiesta_id) 
	VALUES(2, '¡Muy divertida!.', '2020-07-07', 2, 1);
INSERT INTO comentarios(id, cuerpo, fecha, cliente_id, fiesta_id) 
	VALUES(3, 'Deseando de ir, ¿Como hay que ir vestido?', '2020-07-10', 2, 3);
INSERT INTO comentarios(id, cuerpo, fecha, cliente_id, local_id) 
	VALUES(4, '¿Merece la pena contratarlo?', '2020-07-10', 2, 4);


--Valoraciones
INSERT INTO valoraciones(id, comentario, valor, cliente_id, local_id) 
	VALUES(1, 'Solo le falta algunos servicios más', 4.5, 2, 1);
INSERT INTO valoraciones(id, valor, cliente_id, fiesta_id) 
	VALUES(2, 4.0, 2, 4);

	
	
--Mensajes
INSERT INTO mensajes(id, asunto, cuerpo, fecha, hora, remitente_id, destinatario_id)
    VALUES(1, 'Saludo', '¿Como te encuentras?', '2020-07-01', '10:00', 'admin', 'propietario1');
INSERT INTO mensajes(id, asunto, cuerpo, fecha, hora, remitente_id, destinatario_id)
    VALUES(2, 'Saludo', '¿Como te encuentras?', '2020-08-02', '15:30', 'cliente1', 'propietario2');
INSERT INTO mensajes(id, asunto, cuerpo, fecha, hora, remitente_id, destinatario_id)
    VALUES(3, 'Saludo', '¿Como te encuentras?', '2020-10-03', '16:44', 'patrocinador2','cliente1');
INSERT INTO mensajes(id, asunto, cuerpo, fecha, hora, remitente_id, destinatario_id)
    VALUES(4, 'Saludo', '¿Como te encuentras?', '2020-12-09', '18:03', 'patrocinador1', 'propietario2');
INSERT INTO mensajes(id, asunto, cuerpo, fecha, hora, remitente_id, destinatario_id)
    VALUES(5, 'Saludo', '¿Como te encuentras?', '2020-08-29', '9:15', 'cliente2', 'cliente1');
INSERT INTO mensajes(id, asunto, cuerpo, fecha, hora, remitente_id, destinatario_id)
    VALUES(6, 'Saludo', '¿Como te encuentras?', '2020-07-01', '14:05', 'patrocinador1', 'admin');
INSERT INTO mensajes(id, asunto, cuerpo, fecha, hora, remitente_id, destinatario_id)
    VALUES(7, 'Saludo', '¿Como te encuentras?', '2020-11-11', '13:09', 'propietario2', 'cliente2');	
	
		

	
	
	
	
	
	
	
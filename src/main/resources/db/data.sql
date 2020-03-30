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
	VALUES(1, 'Admin', 'DB', 'admin@admin.com', '666999666', 'https://oposicionesueblog.files.wordpress.com/2019/02/funcionario.jpg?w=825', 'admin');
	
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
	'https://d1zv66c6p7f9ox.cloudfront.net/fotoweb/fotonoticia_20180118120033_640.jpg', 'Me gusta conocer gente nueva y la música electrónica', 'cliente2');
	
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
	'https://revistafamily.com/wp-content/uploads/2019/01/local-ideal-para-tus-fiestas.jpg', 'PENDIENTE', 1);
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
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, imagen, decision, cliente_id, local_id) 
	VALUES(1, 'Fiesta de disfraces', 'Fiesta de disfraces de todo tipo. Sin alcohol', 15, 'Venir disfrazado y en buen estado',
	'2020-04-15', '18:00', '23:00', 50, 'https://welcometoibiza.com/wp-content/uploads/2018/02/Fiestas-de-disfraces-en-Ibiza.jpg', 'ACEPTADO', 1, 2);
	
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, imagen, decision, cliente_id, local_id) 
	VALUES(2, 'Fiesta electronica', 'Fiesta de música electronica para mayores de edad.', 20, 'Ser mayor de edad y no traer drogas.',
	'2020-06-20', '22:00', '5:00', 200, 'https://i.pinimg.com/originals/23/45/77/23457712be420ec1a113139552b091a3.jpg', 'PENDIENTE', 1, 1);
	
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, imagen, decision, cliente_id, local_id) 
	VALUES(3, 'Fiesta de rock', 'Fiesta de musica rock para mayores de edad.', 20, 'Ser mayor de edad y no traer drogas.',
	'2020-07-04', '23:00', '7:00', 200, 'https://welcometoibiza.com/wp-content/uploads/2018/06/fiesta-rock-nights-pikes-ibiza-welcometoibiza.jpg', 'ACEPTADO', 2, 4);
	
INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, imagen, decision, cliente_id, local_id) 
	VALUES(4, 'Fiesta dubstep', 'Fiesta de musica dubstep para mayores de edad.', 30, 'Ser mayor de edad y no traer drogas.',
	'2020-04-30', '23:00', '5:00', 200, 'https://lh3.googleusercontent.jpg', 'PENDIENTE', 1, 1);

INSERT INTO fiestas(id, nombre, descripcion, precio, requisitos, fecha, hora_inicio, hora_fin, numero_asistentes, imagen, decision, cliente_id, local_id) 
	VALUES(5, 'Fiesta trap', 'Fiesta de musica trap.', 50, 'Cualquier edad y traer mucha droga.',
	'2020-09-09', '23:00', '7:00', 50, 
	'https://4.bp.blogspot.com/-21qlqUcqgT4/VlUSvE8qmVI/AAAAAAAAAEY/N6RA40TcsQ4/s1600/tumblr_inline_mymygb26FE1ruzwds.jpg', 'RECHAZADO', 2, 1);


--Solicitudes de asistencia a fiestas
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(1, 1, 1, 'ACEPTADO');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(2, 1, 2, 'ACEPTADO');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(3, 3, 1, 'PENDIENTE');
INSERT INTO solicitudes_asistencia(id, fiesta_id, cliente_id, decision) VALUES(4, 3, 2, 'ACEPTADO');


--Anuncios
INSERT INTO anuncios(id, imagen, patrocinador_id, fiesta_id, decision) 
	VALUES(1, 'https://image.freepik.com/psd-gratis/plantilla-creativa-banner-fiesta-navidad_23-2147991012.jpg', 1, 1, 'ACEPTADO');
	
INSERT INTO anuncios(id, imagen, patrocinador_id, fiesta_id, decision) 
	VALUES(2, 'https://image.freepik.com/vector-gratis/banner-fiesta-estilo-neon_92863-16.jpg', 1, 1, 'RECHAZADO');
	
INSERT INTO anuncios(id, imagen, patrocinador_id, local_id, decision) 
	VALUES(3, 'https://www.rottentomatoes.com/assets/pizza-pie/images/icons/global/cf-lg.3c29eff04f2.png', 2, 1, 'ACEPTADO');

INSERT INTO anuncios(id, imagen, patrocinador_id, local_id, decision) 
	VALUES(4, 'https://media.istockphoto.com/vectors/square-neon-light-banner-for-party-flyer-design-vector-id607268144', 2, 2, 'PENDIENTE');
	
	
	
	
	

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CrearFiesta extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



		object Home{
	val home=exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(5)
}


  object LoginCliente {
    val loginCliente = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("LoggedCliente")
        .post("/login")
        .headers(headers_3)
        .formParam("username", "cliente1")
        .formParam("password", "cliente1")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
  }

  object LoginAdmin{
	val loginAdmin=exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
	).pause(20).
	exec(http("LoggedAdmin")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin")
			.formParam("password", "admin")
			 .formParam("_csrf", "${stoken}"))
		.pause(17)	
}


object  DetalleLocal{
	val detalleLocal=exec(http("detalleLocal")
			.get("/locales/1")
			.headers(headers_0))
		.pause(13)
}

object CrearFiesta{
	val crearFiesta=exec(http("getCrearFiesta")
			.get("/fiestas/new/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
	).pause(27)
		.exec(http("postCrearFiesta")
			.post("/fiestas/new/1")
			.headers(headers_3)
			.formParam("Id", "")
			.formParam("nombre", "Daniel")
			.formParam("descripcion", "fiesta")
			.formParam("precio", "34")
			.formParam("requisitos", "4")
			.formParam("fecha", "2020/05/23")
			.formParam("horaInicio", "10:00")
			.formParam("horaFin", "24:00")
			.formParam("aforo", "60")
			.formParam("imagen", "https://www.hispanidad.com/uploads/s1/34/99/39/imagen-microscopica-del-coronavirus_1_640x384.jpeg")
			 .formParam("_csrf", "${stoken}"))
		.pause(29)
}


object Logout{
	val logout=exec(http("Logout")
		.get("/logout")
			 .headers(headers_0)
			 .check(css("input[name=_csrf]", "value").saveAs("stoken"))
	 ).pause(10)
		 .exec(http("CofirmLogout")
			.post("/logout")
			.headers(headers_3)
			 .formParam("_csrf", "${stoken}"))
		.pause(26)
}



object GetLocales{
	val getLocales={exec(http("GetLocales")
			.get("/administrador/locales")
			.headers(headers_0))
		.pause(20)}
}

object AceptarLocal{
	val aceptarLocal=exec(http("AceptarLocal")
			.get("/administrador/local/2/aceptar")
			.headers(headers_0))
		.pause(6)
}



val crearFiestaScn = scenario("CrearFiesta").exec(Home.home,
														LoginCliente.loginCliente,														
														DetalleLocal.detalleLocal,
														CrearFiesta.crearFiesta,																											
														Logout.logout)

val aceptarLocalScn=scenario("AceptarLocal").exec(Home.home,
												LoginAdmin.loginAdmin,												
												GetLocales.getLocales,
												DetalleLocal.detalleLocal,
												AceptarLocal.aceptarLocal,
												Logout.logout)

	setUp(crearFiestaScn.inject(rampUsers(50000) during (10 seconds)),
	aceptarLocalScn.inject(rampUsers(50000) during (10 seconds))).protocols(httpProtocol).assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )

	// val scn = scenario("CrearFiesta")
	// 	.exec(http("request_0")
	// 		.get("/")
	// 		.headers(headers_0))
	// 	.pause(10)
	// 	// Home
	// 	.exec(http("request_1")
	// 		.get("/login")
	// 		.headers(headers_0)
	// 		.resources(http("request_2")
	// 		.get("/login")
	// 		.headers(headers_2)))
	// 	.pause(10)
	// 	.exec(http("request_3")
	// 		.post("/login")
	// 		.headers(headers_3)
	// 		.formParam("username", "cliente1")
	// 		.formParam("password", "cliente1")
	// 		.formParam("_csrf", "9ec0054f-fde9-4790-b41e-68af0761cc31"))
	// 	.pause(16)
	// 	.exec(http("request_4")
	// 		.get("/locales/1")
	// 		.headers(headers_0))
	// 	.pause(13)
	// 	// DetalleLocal
	// 	.exec(http("request_5")
	// 		.get("/fiestas/new/1")
	// 		.headers(headers_0))
	// 	.pause(27)
	// 	// GetSolicitarFiesta
	// 	.exec(http("request_6")
	// 		.post("/fiestas/new/1")
	// 		.headers(headers_3)
	// 		.formParam("Id", "")
	// 		.formParam("nombre", "Daniel")
	// 		.formParam("descripcion", "fiesta")
	// 		.formParam("precio", "34")
	// 		.formParam("requisitos", "4")
	// 		.formParam("fecha", "2020/05/23")
	// 		.formParam("horaInicio", "10:00")
	// 		.formParam("horaFin", "24:00")
	// 		.formParam("aforo", "60")
	// 		.formParam("imagen", "https://www.hispanidad.com/uploads/s1/34/99/39/imagen-microscopica-del-coronavirus_1_640x384.jpeg")
	// 		.formParam("_csrf", "afae1421-8200-4f62-ba27-4e20de2cc38e"))
	// 	.pause(29)
	// 	// GuardarFiesta
	// 	.exec(http("request_7")
	// 		.get("/logout")
	// 		.headers(headers_0))
	// 	.pause(2)
	// 	.exec(http("request_8")
	// 		.post("/logout")
	// 		.headers(headers_3)
	// 		.formParam("_csrf", "afae1421-8200-4f62-ba27-4e20de2cc38e"))
	// 	.pause(1)
	// 	.exec(http("request_9")
	// 		.get("/login")
	// 		.headers(headers_0)
	// 		.resources(http("request_10")
	// 		.get("/login")
	// 		.headers(headers_2)))
	// 	.pause(7)	
	// 	.exec(http("request_13")
	// 		.post("/login")
	// 		.headers(headers_3)
	// 		.formParam("username", "admin")
	// 		.formParam("password", "admin")
	// 		.formParam("_csrf", "048e5713-a1ad-4e6e-a2a2-232f8ebb01dd"))
	// 	.pause(17)	
	// 	.exec(http("request_19")
	// 		.get("/administrador/locales")
	// 		.headers(headers_0))
	// 	.pause(20)
	// 	// GetLocales
	// 	.exec(http("request_20")
	// 		.get("/locales/2")
	// 		.headers(headers_0))
	// 	.pause(11)
	// 	// DetalleLocal
	// 	.exec(http("request_21")
	// 		.get("/administrador/local/2/aceptar")
	// 		.headers(headers_0))
	// 	.pause(6)
	// 	// AceptarLocal	
}
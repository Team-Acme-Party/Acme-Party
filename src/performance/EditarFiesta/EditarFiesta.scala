
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class EditarFiesta extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.upgradeInsecureRequestsHeader("1")
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

		object MisFiestas {
			val misFiestas=exec(http("MisFiestas")
			.get("/cliente/fiestas")
			.headers(headers_0))
		.pause(17)
		}

		object DetalleFiesta{
			val detalleFiesta=exec(http("DetalleFiesta")
			.get("/fiestas/2")
			.headers(headers_0))
		.pause(15)
		}

		object EditarFiesta{
			val editarFiesta=exec(http("GetEditarFiesta")
			.get("/fiestas/2/editar")
			.headers(headers_0).check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(20).exec(http("PostEditarFiesta")
			.post("/fiestas/2/editar")
			.headers(headers_3)
			.formParam("Id", "2")
			.formParam("nombre", "Fiesta electronica")
			.formParam("descripcion", "Fiesta de m�sica electronica para mayores de edad.")
			.formParam("precio", "20")
			.formParam("requisitos", "Ser mayor de edad y no traer drogas.")
			.formParam("fecha", "2020/06/20")
			.formParam("horaInicio", "22:00")
			.formParam("horaFin", "6:00")
			.formParam("aforo", "200")
			.formParam("imagen", "https://i.pinimg.com/originals/23/45/77/23457712be420ec1a113139552b091a3.jpg")
			.formParam("_csrf", "${stoken}"))
		.pause(9)
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



object  DetalleLocal{
	val detalleLocal=exec(http("detalleLocal")
			.get("/locales/1")
			.headers(headers_0))
		.pause(13)
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

		val editarFiestaScn = scenario("EditarFiesta").exec(Home.home,
														LoginCliente.loginCliente,
														DetalleFiesta.detalleFiesta,
														EditarFiesta.editarFiesta,																										
														Logout.logout)

	val aceptarLocalScn=scenario("AceptarLocal").exec(Home.home,
												LoginAdmin.loginAdmin,												
												GetLocales.getLocales,
												DetalleLocal.detalleLocal,
												AceptarLocal.aceptarLocal,
												Logout.logout)

	setUp(editarFiestaScn.inject(rampUsers(5000) during (10 seconds)),
	aceptarLocalScn.inject(rampUsers(5000) during (10 seconds))).protocols(httpProtocol).assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )

	// val scn = scenario("EditarFiesta")
	// 	.exec(http("request_0")
	// 		.get("/cliente/fiestas")
	// 		.headers(headers_0))
	// 	.pause(17)
	// 	// MisFiestas
	// 	.exec(http("request_1")
	// 		.get("/fiestas/2")
	// 		.headers(headers_0))
	// 	.pause(15)
	// 	// DetalleFiesta
	// 	.exec(http("request_2")
	// 		.get("/fiestas/2/editar")
	// 		.headers(headers_0))
	// 	.pause(20)
	// 	// GetEditarFiesta
	// 	.exec(http("request_3")
	// 		.post("/fiestas/2/editar")
	// 		.headers(headers_3)
	// 		.formParam("Id", "2")
	// 		.formParam("nombre", "Fiesta electronica")
	// 		.formParam("descripcion", "Fiesta de m�sica electronica para mayores de edad.")
	// 		.formParam("precio", "20")
	// 		.formParam("requisitos", "Ser mayor de edad y no traer drogas.")
	// 		.formParam("fecha", "2020/06/20")
	// 		.formParam("horaInicio", "22:00")
	// 		.formParam("horaFin", "6:00")
	// 		.formParam("aforo", "200")
	// 		.formParam("imagen", "https://i.pinimg.com/originals/23/45/77/23457712be420ec1a113139552b091a3.jpg")
	// 		.formParam("_csrf", "66576a72-259c-4d18-8794-a036fc7fe04a"))
	// 	.pause(9)
	// 	// PostEditarFiesta


	}
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CrearValoraciones extends Simulation {

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
        .formParam("username", "cliente2")
        .formParam("password", "cliente2")        
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
object MisAsistencias{
	val misAsistencias=exec(http("misAsistencias")
			.get("/fiestas/4")
			.headers(headers_0))
		.pause(8)
}

object VerYValorarFiesta{
	val verYValorarFiesta=exec(http("verFiesta")
			.get("/fiestas/4")
			.headers(headers_0).check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(8).exec(http("Valorar")
			.post("/valoracion/new/fiesta/4")
			.headers(headers_2)
			.formParam("comentario", "fiesta tremenda")
			.formParam("valor", "3")
			 .formParam("_csrf", "${stoken}"))
		.pause(20)
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

object  DetalleLocal{
	val detalleLocal=exec(http("detalleLocal")
			.get("/locales/1")
			.headers(headers_0))
		.pause(13)
}

object AceptarLocal{
	val aceptarLocal=exec(http("AceptarLocal")
			.get("/administrador/local/2/aceptar")
			.headers(headers_0))
		.pause(6)
}

val crearFiestaScn = scenario("CrearValoracion").exec(Home.home,
														LoginCliente.loginCliente,														
														MisAsistencias.misAsistencias,
														VerYValorarFiesta.verYValorarFiesta,																										
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

	
	// val scn = scenario("CrearValoraciones")
	// 	.exec(http("request_0")
	// 		.get("/login")
	// 		.headers(headers_0)
	// 		.resources(http("request_1")
	// 		.get("/login")
	// 		.headers(headers_1)))
	// 	.pause(8)
	// 	.exec(http("request_2")
	// 		.post("/login")
	// 		.headers(headers_2)
	// 		.formParam("username", "cliente2")
	// 		.formParam("password", "cliente2")
	// 		.formParam("_csrf", "9f7008a3-dc2c-468f-9d01-8a81ff04bf07"))
	// 	.pause(9)
	// 	.exec(http("request_3")
	// 		.get("/cliente/solicitudesAsistencias")
	// 		.headers(headers_0))
	// 	.pause(14)
	// 	// MisAsistencias
	// 	.exec(http("request_4")
	// 		.get("/fiestas/4")
	// 		.headers(headers_0))
	// 	.pause(8)
	// 	// VerFiesta
	// 	.exec(http("request_5")
	// 		.get(uri1 + "?osname=win&channel=stable&milestone=83")
	// 		.headers(headers_5))
	// 	.pause(13)
	// 	.exec(http("Valorar")
	// 		.post("/valoracion/new/fiesta/4")
	// 		.headers(headers_2)
	// 		.formParam("comentario", "fiesta tremenda")
	// 		.formParam("valor", "3")
	// 		.formParam("_csrf", "e9bbdf2e-5563-4f29-a473-dfb147b01107"))
	// 	.pause(20)
	// 	// Valorar
}
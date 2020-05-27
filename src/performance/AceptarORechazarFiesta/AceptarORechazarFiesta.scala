package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AceptarORechazarFiesta extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
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

object LoginPropietario {
    val loginPropietario = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("LoginPropietario")
        .post("/login")
        .headers(headers_3)
        .formParam("username", "propietario1")
        .formParam("password", "propietario1")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
  }

object MisLocales{
	val misLocales=exec(http("MisLocales")
			.get("/propietario/locales")
			.headers(headers_0))
		.pause(16)
}

object VerSolicitudes{
	val verSolicitudes=exec(http("VerSolicitudes")
			.get("/local/1/fiestas")
			.headers(headers_0))
		.pause(10)
}

object VerDetalleFiesta{
	val verDetalleFiesta=exec(http("VerDetalleFiesta")
			.get("/fiestas/2")
			.headers(headers_0))
		.pause(10)
}

object AceptarFiesta{
	val aceptarFiesta=exec(http("AceptarFiesta")
			.get("/local/1/fiesta/2/aceptar")
			.headers(headers_0))
		.pause(12)
}

object DenegarFiesta{
	val denegarFiesta=exec(http("Denegar")
			.get("/local/2/fiesta/8/denegar")
			.headers(headers_0))
		.pause(6)
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

	val aceptarFiestaScn = scenario("AceptarFiesta").exec(Home.home,
														LoginPropietario.loginPropietario,														
														MisLocales.misLocales,
														VerSolicitudes.verSolicitudes,
														VerDetalleFiesta.verDetalleFiesta,
														AceptarFiesta.aceptarFiesta,
														Logout.logout)

	val denegarFiestaScn=scenario("DenegarFiesta").exec(Home.home,
														LoginPropietario.loginPropietario,															
														MisLocales.misLocales,
														VerSolicitudes.verSolicitudes,
														VerDetalleFiesta.verDetalleFiesta,
														DenegarFiesta.denegarFiesta,
														Logout.logout)

	setUp(aceptarFiestaScn.inject(rampUsers(5000) during (10 seconds)),
	denegarFiestaScn.inject(rampUsers(5000) during (10 seconds))).protocols(httpProtocol)
  .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}

	// val scn = scenario("AceptarORechazarFiesta")
	// 	.exec(http("Home")
	// 		.get("/")
	// 		.headers(headers_0))
	// 	.pause(5)
	// 	// Home
	// 	.exec(http("Login")
	// 		.get("/login")
	// 		.headers(headers_0)
	// 		.resources(http("request_2")
	// 		.get("/login")
	// 		.headers(headers_2)))
	// 	.pause(16)
	// 	// Login
	// 	.exec(http("Logged")
	// 		.post("/login")
	// 		.headers(headers_3)
	// 		.formParam("username", "propietario1")
	// 		.formParam("password", "propietario1")
	// 		.formParam("_csrf", "007b69e9-dbf5-4127-97ca-c6441ebe456f"))
	// 	.pause(9)
	// 	// Logged
	// 	.exec(http("MisLocales")
	// 		.get("/propietario/locales")
	// 		.headers(headers_0))
	// 	.pause(16)
	// 	// MisLocales
	// 	.exec(http("VerSolicitudes")
	// 		.get("/local/1/fiestas")
	// 		.headers(headers_0))
	// 	.pause(10)
	// 	// VerSolicitudes
	// 	.exec(http("VerDetalleFiesta")
	// 		.get("/fiestas/2")
	// 		.headers(headers_0))
	// 	.pause(10)
	// 	// VerDetalleFiesta
	// 	.exec(http("AceptarFiesta")
	// 		.get("/local/1/fiesta/2/aceptar")
	// 		.headers(headers_0))
	// 	.pause(12)
	// 	// AceptarFiesta
	// 	.exec(http("MisLocales")
	// 		.get("/propietario/locales")
	// 		.headers(headers_0))
	// 	.pause(10)
	// 	// MisLocales
	// 	.exec(http("VerSolicitudes")
	// 		.get("/local/2/fiestas")
	// 		.headers(headers_0))
	// 	.pause(10)
	// 	// VerSolicitudes
	// 	.exec(http("VerDetalles")
	// 		.get("/fiestas/8")
	// 		.headers(headers_0))
	// 	.pause(10)
	// 	// VerDetalles
	// 	.exec(http("Denegar")
	// 		.get("/local/2/fiesta/8/denegar")
	// 		.headers(headers_0))
	// 	.pause(6)
	// 	// Denegar
	// 	.exec(http("Logout")
	// 		.get("/logout")
	// 		.headers(headers_0))
	// 	.pause(10)
	// 	// Logout
	// 	.exec(http("CofirmLogout")
	// 		.post("/logout")
	// 		.headers(headers_3)
	// 		.formParam("_csrf", "7690bf3c-201f-4265-9b2e-53f7bead2ab6"))
	// 	.pause(26)
		// Home
		// CofirmLogout

	

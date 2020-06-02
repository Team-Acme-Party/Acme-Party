
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class VerFiestasQueAsistoTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpeg""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")

	object Home{
		val home = exec(http("Home")
			.get("/"))
		.pause(9)
	}

	object Login{
		val login = exec(http("Login")
			.get("/login"))
		.pause(16)
	}

	object Logged{
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "cliente1")
			.formParam("password", "cliente1")
			.formParam("_csrf", "4fa2c88a-3962-424b-a8b3-4f556a9ecfff"))
		.pause(15)
	}

	object ListAsistenciasFiestas{
		val listAsistenciasFiestas = exec(http("ListAsistenciasFiestas")
			.get("/cliente/solicitudesAsistencias"))
		.pause(30)
	}

	object ShowFiesta{
		val showFiesta = exec(http("ShowFiesta")
			.get("/fiestas/1"))
		.pause(18)
	}

	object ShowFiestaError{
		val showFiestaError = exec(http("request_5")
			.get("/fiestas/5"))
		.pause(11)
	}


	val positivo = scenario("VerFiestasQueAsistoPositivo").exec(
		Home.home,
		Login.login,
		Logged.logged,
		ListAsistenciasFiestas.listAsistenciasFiestas,
		ShowFiesta.showFiesta
	)

	val negativo = scenario("VerFiestasQueAsistoNegativo").exec(
		Home.home,
		Login.login,
		Logged.logged,
		ListAsistenciasFiestas.listAsistenciasFiestas,
		ShowFiestaError.showFiestaError
	)


	setUp(
		positivo.inject(rampUsers(2000) during(100 seconds)),
		negativo.inject(rampUsers(2000) during(100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}
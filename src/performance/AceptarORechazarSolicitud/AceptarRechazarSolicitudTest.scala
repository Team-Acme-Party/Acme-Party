
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AceptarRechazarSolicitudTest extends Simulation {

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
		.pause(12)
	}

	object Login{
		val login = exec(http("Login")
			.get("/login"))
		.pause(7)
	}

	object Logged{
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "cliente2")
			.formParam("password", "cliente2")
			.formParam("_csrf", "3a1bfafd-2376-4a81-b27d-ce259726e28a"))
		.pause(32)
	}

	object ListMisFiestas{
		val listMisFiestas = exec(http("ListMisFiestas")
			.get("/cliente/fiestas"))
		.pause(10)
	}

	object ShowFiesta{
		val showFiesta = exec(http("ShowFiesta")
			.get("/fiestas/3"))
		.pause(12)
	}

	object AceptarSolicitud{
		val aceptarSolicitud = exec(http("AceptarSolicitud")
			.get("/cliente/solicitudAsistencia/aceptar/3"))
		.pause(8)
	}

	object RechazarSolicitud{
		val rechazarSolicitud = exec(http("RechazarSolicitud")
			.get("/cliente/solicitudAsistencia/rechazar/3"))
		.pause(9)
	}

	val AceptarSolicitudTest = scenario("AceptarSolicitudTest").exec(
		Home.home,
		Login.login,
		Logged.logged,
		ListMisFiestas.listMisFiestas,
		ShowFiesta.showFiesta,
		AceptarSolicitud.aceptarSolicitud
	)

	val RechazarSolicitudTest = scenario("RechazarSolicitudTest").exec(
		Home.home,
		Login.login,
		Logged.logged,
		ListMisFiestas.listMisFiestas,
		ShowFiesta.showFiesta,
		RechazarSolicitud.rechazarSolicitud
	)



	setUp(
		AceptarSolicitudTest.inject(rampUsers(2000) during(100 seconds)),
		RechazarSolicitudTest.inject(rampUsers(2000) during(100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}
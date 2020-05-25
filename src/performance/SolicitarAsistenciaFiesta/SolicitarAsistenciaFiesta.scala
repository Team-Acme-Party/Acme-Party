package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class SolicitarAsistenciaFiesta extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.es")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,pt;q=0.7",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,pt;q=0.7",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,pt;q=0.7",
		"Origin" -> "http://www.dp2.es",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	
	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}

	object Login{
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(6)
	}

	object Logged{
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "cliente1")
			.formParam("password", "cliente1")
			.formParam("_csrf", "6cc84817-410d-4f36-9f30-ff9b8163e711"))
		.pause(18)
	}

	object VerDetallesFiesta{
		val verDetallesFiesta = exec(http("VerDetallesFiesta")
			.get("/fiestas/6")
			.headers(headers_0))
		.pause(18)
	}

	object SolicitarAsistenciaMiFiestaCorrecto{
		val solicitarAsistenciaMiFiestaCorrecto = exec(http("SolicitarAsistenciaMiFiestaCorrecto")
			.get("/cliente/solicitudAsistencia/fiesta/6?fiestaId=6&save=Solicitar+asistencia")
			.headers(headers_0))
		.pause(27)
	}

	object SolicitarAsistenciaMiFiestaError{
		val solicitarAsistenciaMiFiestaError = exec(http("SolicitarAsistenciaMiFiestaError")
			.get("/cliente/solicitudAsistencia/fiesta/6?fiestaId=6&save=Solicitar+asistencia")
			.headers(headers_0))
		.pause(20)
	}
	
	
	val scnSolicitarAsistenciaFiestaCorrecto = scenario("SolicitarAsistenciaFiestaCorrecto")
		.exec(Home.home, 
			Login.login,
			Logged.logged,
			VerDetallesFiesta.verDetallesFiesta,
			SolicitarAsistenciaMiFiestaCorrecto.solicitarAsistenciaMiFiestaCorrecto)
	
	val scnSolicitarAsistenciaFiestaError = scenario("SolicitarAsistenciaFiestaError")
		.exec(Home.home, 
			Login.login,
			Logged.logged,
			VerDetallesFiesta.verDetallesFiesta,
			SolicitarAsistenciaMiFiestaError.solicitarAsistenciaMiFiestaError)
	

	setUp(scnSolicitarAsistenciaFiestaCorrecto.inject(rampUsers(6000) during(100 seconds)),
		scnSolicitarAsistenciaFiestaError.inject(rampUsers(6000) during(100 seconds))).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95))
		
		
}
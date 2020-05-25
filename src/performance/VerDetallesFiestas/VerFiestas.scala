package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class VerFiestas extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.es")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8,pt;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.es",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)		
	}

	object Login{
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(10)
	}

	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "cliente1")
			.formParam("password", "cliente1")
			.formParam("_csrf", "3688a4b3-f6f6-482b-baaf-8288dc80e7b7"))
		.pause(17)
	}

	object MisFiestas{
		val misFiestas = exec(http("MisFiestas")
			.get("/cliente/fiestas")
			.headers(headers_0))
		.pause(22)
	}

	object VerMiFiesta{
		val verMiFiesta = exec(http("VerMiFiesta")
			.get("/fiestas/1")
			.headers(headers_0))
		.pause(39)
	}

	object ErrorVerMiFiesta{
		val errorVerMiFiesta = exec(http("ErrorVerMiFiesta")
			.get("/fiestas/100")
			.headers(headers_0))
		.pause(15)
	}

	val scnVerFiestasCorrecto = scenario("VerFiestasCorrecto")
		.exec(Home.home, 
			Login.login,
			Logged.logged,
			MisFiestas.misFiestas,
			VerMiFiesta.verMiFiesta)
		
	val scnVerFiestasError = scenario("VerFiestasError")
		.exec(Home.home, 
			Login.login,
			Logged.logged,
			MisFiestas.misFiestas,
			ErrorVerMiFiesta.errorVerMiFiesta)


	setUp(scnVerFiestasCorrecto.inject(rampUsers(5000) during (100 seconds)),
		scnVerFiestasError.inject(rampUsers(5000) during (100 seconds))).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95)
		)
		
}
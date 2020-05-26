package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class VerFiestasYLocalesTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png""", """.*.jpg""", """.*.jpeg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")
	object Welcome{
		val welcome = exec(http("Welcome")
			.get("/")
			.headers(headers_0))
		.pause(11)
	}
	object ShowFiesta{
		val showFiesta = exec(http("ShowFiesta")
			.get("/fiestas/1")
			.headers(headers_0))
		.pause(10)
	}
	object ShowLocal{
		val showLocal = exec(http("ShowLocal")
			.get("/locales/1")
			.headers(headers_0))
		.pause(23)
	}
	object LocalForbidden{
		val localForbidden = exec(http("LocalForbidden")
			.get("/locales/50")
			.headers(headers_0))
		.pause(31)
	}
	object FiestaForbidden{
		val fiestaForbidden = exec(http("FiestaForbidden")
			.get("/fiestas/50")
			.headers(headers_0))
		.pause(13)
	}



	val positivo = scenario("VerFiestasYLocalesTestPositivo").exec(
		Welcome.welcome,
		ShowFiesta.showFiesta,
		Welcome.welcome,
		ShowLocal.showLocal
	)

	val negativo = scenario("VerFiestasYLocalesTestNegativo").exec(
		Welcome.welcome,
		LocalForbidden.localForbidden,
		FiestaForbidden.fiestaForbidden
	)
		
	setUp(
        positivo.inject(rampUsers(7500) during(100 seconds)),
        negativo.inject(rampUsers(7500) during(100 seconds))
    )
    .protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}
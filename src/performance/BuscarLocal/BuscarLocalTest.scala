package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class BuscarLocalTestDiagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpeg""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	object Welcome{
		val welcome = exec(http("Welcome")
			.get("/")
			.headers(headers_0))
		.pause(7)
	}

	object BuscarLocalForm{
		val buscarLocalForm = exec(http("BuscarLocalForm")
			.get("/locales/buscar")
			.headers(headers_0))
		.pause(10)
	}

	object ThereAreNoResults{
		val thereAreNoResults = exec(http("ThereAreNoResults")
			.get("/locales?direccion=test")
			.headers(headers_0))
		.pause(16)
	}

	object ThereAreResults{
		val thereAreResults = exec(http("ThereAreResults")
			.get("/locales?direccion=Luis+Montoto")
			.headers(headers_0))
		.pause(7)
	}

	val positivo = scenario("BuscarLocalPositivoTest").exec(
		Welcome.welcome,
		BuscarLocalForm.buscarLocalForm,
		ThereAreResults.thereAreResults
	)

	val negativo = scenario("BuscarLocalNegativoTest").exec(
		Welcome.welcome,
		BuscarLocalForm.buscarLocalForm,
		ThereAreNoResults.thereAreNoResults
	)

	setUp(
		positivo.inject(rampUsers(3500) during(100 seconds)),
		negativo.inject(rampUsers(3500) during(100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}
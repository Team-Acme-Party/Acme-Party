package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AceptarAnuncioTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpeg""", """.*.jpg"""), WhiteList())
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

	object Welcome{
		val welcome = exec(http("Welcome")
			.get("/")
			.headers(headers_0))
		.pause(9)
	}

	object Login{
		val wrongLogin = exec(
			http("WrongLogin")
				.get("/login")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(19)
			.exec(
				http("WrongLogged")
					.post("/login")
					.headers(headers_3)
					.formParam("username", "cliente2")
					.formParam("password", "cliente2")
					.formParam("_csrf", "${stoken}")
			).pause(17)
		
		val rightLogin = exec(
			http("RightLogin")
				.get("/login")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(19)
			.exec(
				http("RightLogged")
					.post("/login")
					.headers(headers_3)
					.formParam("username", "cliente1")
					.formParam("password", "cliente1")
					.formParam("_csrf", "${stoken}")
			).pause(17)
	}

	object Forbidden{
		val forbidden = exec(http("Forbidden")
			.get("/cliente/anuncio/4/aceptar")
			.headers(headers_0))
		.pause(11)
	}

	object MyAnuncios{
		val myAnuncios = exec(http("MyAnuncios")
			.get("/cliente/anuncios")
			.headers(headers_0))
		.pause(11)
	}

	object ShowAnuncio{
		val showAnuncio = exec(http("ShowAnuncio")
			.get("/anuncios/4")
			.headers(headers_0))
		.pause(8)
	}

	object AnuncioAccepted{
		val anuncioAccepted = exec(http("AnuncioAccepted")
			.get("/cliente/anuncio/4/aceptar")
			.headers(headers_0))
		.pause(10)
	}

	val positivo = scenario("AceptarAnuncioTestPositivo").exec(
		Welcome.welcome,
		Login.rightLogin,
		MyAnuncios.myAnuncios,
		ShowAnuncio.showAnuncio,
		AnuncioAccepted.anuncioAccepted
	)

	val negativo = scenario("AceptarAnuncioTestNegativo").exec(
		Welcome.welcome,
		Login.wrongLogin,
		Forbidden.forbidden
	)

	setUp(
		positivo.inject(rampUsers(1500) during(100 seconds)),
		negativo.inject(rampUsers(1500) during(100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}
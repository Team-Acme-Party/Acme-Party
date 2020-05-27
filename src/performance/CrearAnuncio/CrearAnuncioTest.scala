package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CrearAnuncioTest extends Simulation {

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
		val login = exec(
			http("Login")
				.get("/login")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(19)
			.exec(
				http("Logged")
					.post("/login")
					.headers(headers_3)
					.formParam("username", "patrocinador1")
					.formParam("password", "patrocinador1")
					.formParam("_csrf", "${stoken}")
			).pause(17)
	}

	object ShowFiesta{
		val showFiesta = exec(http("ShowFiesta")
			.get("/fiestas/3")
			.headers(headers_0))
		.pause(8)
	}

	object NewAnuncioWrong{
		val newAnuncioWrong = exec(
			http("FormAnuncio")
				.get("/anuncio/new/3/fiesta")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(9)
		.exec(
			http("FieldEmpty")
				.post("/anuncio/new/3/fiesta")
				.headers(headers_3)
				.formParam("imagen", "")
				.formParam("_csrf", "${stoken}")
		).pause(18)
	}

	object NewAnuncio{
		val newAnuncio = exec(http("FormAnuncio")
			.get("/anuncio/new/3/fiesta")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(9)
		.exec(http("AnuncioCreated")
			.post("/anuncio/new/3/fiesta")
			.headers(headers_3)
			.formParam("imagen", "https://image.shutterstock.com/image-vector/fiesta-banner-poster-concept-design-260nw-1070311730.jpg")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	val positivo = scenario("CrearAnuncioTestPositivo").exec(
		Welcome.welcome,
		Login.login,
		ShowFiesta.showFiesta,
		NewAnuncio.newAnuncio
	)

	val negativo = scenario("CrearAnuncioTestNegativo").exec(
		Welcome.welcome,
		Login.login,
		ShowFiesta.showFiesta,
		NewAnuncioWrong.newAnuncioWrong
	)

	setUp(
		positivo.inject(rampUsers(2500) during(100 seconds)),
		negativo.inject(rampUsers(2500) during(100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}
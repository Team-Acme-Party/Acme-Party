package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AceptarLocalTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png""", """.*.jpg""", """.*.jpeg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
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
		.pause(6)
	}

	object Login{
		val wrongLogin = exec(
			http("WrongLogin")
				.get("/login")
			    .headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(9) //o 37, como el rightLogin
			.exec(
				http("WrongLogged")
					.post("/login")
					.headers(headers_3)
					.formParam("username", "propietario1")
					.formParam("password", "propietario1")
					.formParam("_csrf", "${stoken}")
			).pause(41)
		
		val rightLogin = exec(
			http("RightLogin")
				.get("/login")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(37)
			.exec(
				http("RightLogged")
					.post("/login")
					.headers(headers_3)
					.formParam("username", "admin")
					.formParam("password", "admin")
					.formParam("_csrf", "${stoken}")
			).pause(25)
	}

    object Forbidden{
		val forbidden = exec(http("Forbidden")
			.get("/administrador/local/50/aceptar")
			.headers(headers_0)
            .check(status.is(403)))
		.pause(8)
	}

    object MyLocales{
		val myLocales = exec(http("MyLocales")
			.get("/administrador/locales")
			.headers(headers_0))
		.pause(12)
	}

    object ShowLocal{
		val showLocal = exec(http("ShowLocal")
			.get("/locales/2")
			.headers(headers_0))
		.pause(28)
	}

    object LocalAccepted{
		val localAccepted = exec(http("LocalAccepted")
			.get("/administrador/local/2/aceptar")
			.headers(headers_0))
		.pause(44)
	}

    val positivo = scenario("AceptarLocalTestPositivo").exec(
		Welcome.welcome,
		Login.rightLogin,
		MyLocales.myLocales,
		ShowLocal.showLocal,
		LocalAccepted.localAccepted
	)

	val negativo = scenario("AceptarLocalTestNegativo").exec(
		Welcome.welcome,
		Login.wrongLogin,
		Forbidden.forbidden
	)

	setUp(
		positivo.inject(rampUsers(1000) during(100 seconds)),
		negativo.inject(rampUsers(1000) during(100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}
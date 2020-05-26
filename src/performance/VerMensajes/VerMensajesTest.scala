package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class VerMensajesTest extends Simulation {

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
		.pause(10)
	}	

	object Login{
        val login = exec(
            http("Login")
                .get("/login")
                .headers(headers_0)
                .check(css("input[name=_csrf]", "value").saveAs("stoken"))
        ).pause(13)
            .exec(
                http("Logged")
                    .post("/login")
                    .headers(headers_3)
                    .formParam("username", "admin")
                    .formParam("password", "admin")
                    .formParam("_csrf", "${stoken}")
            ).pause(19)
    }

	object MyMensajes{
		val myMensajes = exec(http("MyMensajes")
			.get("/usuario/mensajes")
			.headers(headers_0))
		.pause(38)
	}

	object ShowMensaje{
		val showMensaje = exec(http("ShowMensaje")
			.get("/mensajes/1")
			.headers(headers_0))
		.pause(41)
	}

	object ShowMensajeWrong{
		val showMensajeWrong = exec(http("ShowMensajeWrong")
			.get("/mensajes/50")
			.headers(headers_0))
		.pause(14)
	}

	val positivo = scenario("VerMensajeTestPositivo").exec(
		Welcome.welcome,
		MyMensajes.myMensajes,
		ShowMensaje.showMensaje
	)

	val negativo = scenario("VerMensajesTestNegativo").exec(
		Welcome.welcome,
		MyMensajes.myMensajes,
		ShowMensajeWrong.showMensajeWrong
	)
	
	setUp(
        positivo.inject(rampUsers(5000) during(100 seconds)),
        negativo.inject(rampUsers(5000) during(100 seconds))
    )
    .protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}
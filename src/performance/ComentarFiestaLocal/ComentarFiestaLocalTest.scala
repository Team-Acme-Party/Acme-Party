package comentario

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ComentarFiestaLocalTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.acme-party.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.jpg""", """.*.png""", """.*.jpeg"""), WhiteList())
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
		"Origin" -> "http://www.acme-party.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(15)
	}
	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "cliente2")
			.formParam("password", "cliente2")
			.formParam("_csrf", "9e7f75b0-0a9a-4382-9c57-a982e3be2a36"))
		.pause(42)
	}

	object ShowFiesta {
		val showFiesta = exec(http("ShowFiesta")
			.get("/fiestas/3")
			.headers(headers_0))
		.pause(29)
	}

	object FiestaComentada {
		val fiestaComentada = exec(http("FiestaComentada")
			.post("/comentario/new/fiesta/3")
			.headers(headers_3)
			.formParam("cuerpo", "Comentario de prueba")
			.formParam("_csrf", "76bb5fd7-be27-43ba-b3a4-97b5a30cc876"))
		.pause(13)
	}

	object ShowLocal {
		val showLocal = exec(http("ShowLocal")
			.get("/")
			.headers(headers_0))
		.pause(8)
		.exec(http("request_7")
			.get("/locales/4")
			.headers(headers_0))
		.pause(14)
	}

	object LocalComentado {
		val localComentado = exec(http("LocalComentado")
			.post("/comentario/new/local/4")
			.headers(headers_3)
			.formParam("cuerpo", "Comentario de prueba")
			.formParam("_csrf", "76bb5fd7-be27-43ba-b3a4-97b5a30cc876"))
		.pause(13)
	}
	val comentarFiestaScn = scenario("ComentarFiesta").exec(Home.home, Login.login, Logged.logged, ShowFiesta.showFiesta, FiestaComentada.fiestaComentada)

	val comentarLocalScn = scenario("ComentarLocal").exec(Home.home, Login.login, Logged.logged, ShowLocal.showLocal, LocalComentado.localComentado)


	setUp(comentarFiestaScn.inject(atOnceUsers(1)),comentarLocalScn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
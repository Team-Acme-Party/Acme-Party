package mensajes

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CrearMensajeTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.acme-party.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.jpg""", """.*.png""", """.*.css""", """.*.js"""), WhiteList())
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


	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
	}

	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "cliente2")
			.formParam("password", "cliente2")
			.formParam("_csrf", "ec984d11-ceac-4c84-a2fd-8d8f6629fc46"))
		.pause(7)
	}

	object ListMensajes {
		val listMensajes = exec(http("ListMensajes")
			.get("/usuario/mensajes")
			.headers(headers_0))
		.pause(20)
	}

	object FormNuevoMensaje {
		val formNuevoMensaje = exec(http("FormNuevoMensaje")
			.get("/mensajes/new")
			.headers(headers_0))
		.pause(29)
	}

	object MensajeEnviado {
		val mensajeEnviado = exec(http("MensajeEnviado")
			.post("/mensajes/new")
			.headers(headers_3)
			.formParam("asunto", "prueba")
			.formParam("cuerpo", "Comentario de prueba")
			.formParam("destinatario", "propietario1")
			.formParam("_csrf", "05b32684-945c-4db5-a546-4fdb31d1ce78"))
		.pause(25)
	}
	object ErrorEnviarMensaje {
		val errorEnviarMensaje = exec(http("ErrorEnviarMensaje")
			.post("/mensajes/new")
			.headers(headers_3)
			.formParam("asunto", "")
			.formParam("cuerpo", "")
			.formParam("destinatario", "admin")
			.formParam("_csrf", "05b32684-945c-4db5-a546-4fdb31d1ce78"))
		.pause(14)
	}

	val EnviarMensajeScn = scenario("EnviarMensaje").exec(Home.home, Login.login, Logged.logged, ListMensajes.listMensajes, FormNuevoMensaje.formNuevoMensaje, MensajeEnviado.mensajeEnviado)
		
	val EnviarMensajeErrorScn = scenario("EnviarMensajeError").exec(Home.home, Login.login, Logged.logged, ListMensajes.listMensajes, FormNuevoMensaje.formNuevoMensaje, ErrorEnviarMensaje.errorEnviarMensaje)
	
	setUp(EnviarMensajeScn.inject(atOnceUsers(1)),EnviarMensajeErrorScn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">
				
				<petclinic:menuItem active="${name eq 'locales'}" url="/locales/buscar"
					title="Buscar locales">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Buscar locales</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'fiestas'}" url="/fiestas/buscar"
					title="Buscar fiestas">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Buscar fiestas</span>
				</petclinic:menuItem>

				<sec:authorize access="hasAuthority('propietario')">
					<petclinic:menuItem active="${name eq 'mislocales'}" url="/propietario/locales"
						title="Mis locales">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Mis locales</span>
					</petclinic:menuItem>
					<petclinic:menuItem active="${name eq 'misanuncios'}" url="/propietario/anuncios"
						title="Mis anuncios">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Mis anuncios</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('cliente')">
					<petclinic:menuItem active="${name eq 'misfiestas'}" url="/cliente/fiestas"
						title="Mis fiestas">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Mis fiestas</span>
					</petclinic:menuItem>
					<petclinic:menuItem active="${name eq 'misasistencias'}" url="/cliente/solicitudesAsistencias"
						title="Mis asistencias">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Mis asistencias</span>
					</petclinic:menuItem>
					<petclinic:menuItem active="${name eq 'misanuncios'}" url="/cliente/anuncios"
						title="Mis anuncios">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Mis anuncios</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				
				<sec:authorize access="hasAuthority('patrocinador')">
					<petclinic:menuItem active="${name eq 'misanuncios'}" url="/patrocinador/anuncios"
						title="Mis Anuncios">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Mis Anuncios</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('admin')">
					<petclinic:menuItem active="${name eq 'locales'}" url="/administrador/locales"
						title="Locales">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Locales</span>
					</petclinic:menuItem>
				</sec:authorize>

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>

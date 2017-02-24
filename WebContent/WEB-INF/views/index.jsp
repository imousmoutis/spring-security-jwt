<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title data-ng-bind="title">Dummy Page | Spring Structure</title>
	
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body data-ng-app="mainApp">
	
		<div data-ng-controller="mainController">
		
			<div data-ng-hide="authenticated">
				<input type="text" data-ng-model="username" placeholder="username"/> <br/>
				<input type="password" data-ng-model="password" placeholder="password"/> <br/>
				<button data-ng-click="login()">Login</button> <br/>
				<div data-ng-show="wrongCredentials">The credentials you entered are wrong!</div>
			</div>
			
			<div data-ng-show="authenticated">
				You are successfully logged in as {{fullName}}.
			</div>
		
		</div>
		
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.min.js"></script>
		<script src="https://cdn.rawgit.com/auth0/angular-jwt/master/dist/angular-jwt.min.js"></script>
		<script src="resources/js/main.js"></script>
	</body>
</html>
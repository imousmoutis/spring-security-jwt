angular.module('mainApp', ['angular-jwt']).controller('mainController', function($scope, $http, jwtHelper) {
	
	$scope.login = function(){
		
		var cred = $scope.username + ':' + $scope.password;
		var authdata = Base64(cred);
		
		$http({
			method : 'POST',
			url : 'login',
			headers : {
				'Authorization': 'Basic ' + authdata
			}
		}).success(function(response, status, headers, config) {				
			
			$scope.wrongCredentials = false;
			$scope.authenticated = true;
			
			var tokenPayload = jwtHelper.decodeToken(headers('X-AUTH-TOKEN'));
			
			$scope.fullName = tokenPayload.fullName;
		}).error(function(response, status, headers, config) {
			
			if (status === 401)
				$scope.wrongCredentials = true;
		});	
		
	}
	
	//Base64 Default Encoding for Basic Authorization
	function Base64(input) {
		
		var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;

		input = utf8(input);

		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			}else if (isNaN(chr3)) {
				enc4 = 64;
			}
			
			output = output + _keyStr.charAt(enc1) + _keyStr.charAt(enc2) + _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
		}
		
		return output;		 
	};
	
	function utf8(string) {
		
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			}else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
				}
		}
		return utftext;	 
	};
	
});

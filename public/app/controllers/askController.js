app.controller('MedicalAsk', function ($scope, $http,  $timeout, cfpLoadingBar) {
	
	init();

	function init() {
		$scope.qa = [];
		$scope.diagnosis = [];
		$scope.advices = [];
	};

	function getQuery() {
		var ans = [];
		$scope.qa.map(function(x) {
			var it = [];
			x.items.map(function(y) {

				it.push({a_id: y.a_id, a_val: y.a_val});
			});
			ans.push({q_id: x.q_id, items: it});
		});
		return {a: ans};
	}

	function onResponse(response) {
		switch (response.t) {
			case 3: $scope.advices = []; $scope.diagnosis = []; response.p.q.map(function(question) {$scope.qa.push(question);}); break;
			case 5: $scope.advices = []; response.p.ck.map(function(c) {$scope.advices.push(c);}); break;
			case 6: $scope.diagnosis = []; response.p.ent.map(function(e) {$scope.diagnosis.push(e);}); break;
		}
	};

	$scope.proceed = function (direction) {
		var query = getQuery();

		switch (direction) {
			case "auto": query.status = 0; break;
			case "diagnosis": query.status = 1; break;
			case "advice": query.status = 2; break;
			case "more": query.status = 3; break;
		}
		
		$http.post('http://localhost:9000/medical/ask', query).success(onResponse);
	};
});
app.controller('MedicalAsk', function ($scope, $http, $anchorScroll, $location) {
	
	init();

	function init() {
		$scope.qa = [];
		$scope.diagnosis = [];
		$scope.advices = [];
	};

    var anchor = '';

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

    function goScroll ($anchorScroll, $location, $scope) {
        if (anchor != '') {

            if ($location.hash() !== anchor) {
                $location.hash(anchor);
            } else {
                $anchorScroll();
            }
        }
    }

	function onResponse(response) {
		switch (response.t) {
			case 3:
                $scope.advices = []; $scope.diagnosis = [];
                response.p.q.map(function(question) {$scope.qa.push(question);});
                if (response.p.q.length > 0)
                    anchor = 'a' + response.p.q[0].q_id;
                break;
			case 5:
                $scope.advices = [];
                response.p.ck.map(function(c) {$scope.advices.push(c);});
                anchor = 'aa';
                break;
			case 6:
                $scope.diagnosis = [];
                response.p.ent.map(function(e) {$scope.diagnosis.push(e);});
                anchor = 'ad';
                break;
		}

        //goScroll($anchorScroll, $location, $scope);
	}

	$scope.proceed = function (direction) {
		var query = getQuery();

		switch (direction) {
			case "auto": query.status = 0; break;
			case "diagnosis": query.status = 1; break;
			case "advice": query.status = 2; break;
			case "more": query.status = 3; break;
		}
		
		$http.post('http://192.168.1.114:9000/medical/ask', query).success(onResponse);

	};
});
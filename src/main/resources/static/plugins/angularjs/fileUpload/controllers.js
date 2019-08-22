'use strict';


angular


    .module('app', ['angularFileUpload'])


    .controller('AppController', ['$scope','FileUploader', function($scope, FileUploader,) {
        var uploader = $scope.uploader = new FileUploader({
            url: 'upload/file'
        });

        // FILTERS

        // a sync filter
        uploader.filters.push({
            name: 'syncFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                console.log('syncFilter');
                return this.queue.length < 1000;
            }
        });

        // an async filter
        uploader.filters.push({
            name: 'asyncFilter',
            fn: function(item /*{File|FileLikeObject}*/, options, deferred) {
                console.log('asyncFilter');
                setTimeout(deferred.resolve, 0);
            }
        });


        $scope.make=function () {
            $http({
                method: 'post',
                url: '/upload/make'
            }).then(function successCallback(response) {
                // 请求成功执行代码
            }, function errorCallback(response) {
                // 请求失败执行代码
            });

        }


    }]);



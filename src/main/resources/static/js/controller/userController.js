app.controller("userController",function ($scope,userService) {


    //用户名异步交互
    $scope.findOne=function () {
        $scope.msg="";
        var username =  encodeURI($scope.username2);
        userService.findOne(username).success(function (res) {
            //用户名存在，让按钮不可用;
            if(!res.success) {
                $scope.msg="用户名已存在";
                document.getElementById("zhuce1").setAttribute("disabled", true);
                return;
            }
            document.getElementById("zhuce1").removeAttribute("disabled");

        })
    }

    $scope.save=function () {
        userService.save($scope.file,$scope.name1,$scope.repository_id).success(function (res) {

        })
    }



    $scope.check=function () {
        $scope.tigger=="";
        userService.check().success(function (res) {
            //用户名存在，让按钮不可用;
            if(res=="null") {
                //没有登录
                $scope.register = false;
                $scope.logout1 = true;
            }else{
                //登录
                $scope.tigger=res;
                $scope.logout1 = false;
                $scope.register = true;
            }
        })
    }






    $scope.submit=true;
    $scope.remove = function () {
        $scope.submit=false;
    }


   /* $scope.ss3=true;
    $scope.time=1;
    $scope.showTime = function () {
        $interval(function () {
            $scope.time++;
            $scope.ss3=false;
        }, 1000)
    }*/



 /*   $scope.halo1=true;

    //查看几人排队
    $scope.bool = function () {
        $interval(function () {
            userService.bool().success(function (res2) {
                $scope.person=res2;
                if(res2==0){
                    //正在执行
                    $scope.halo1=true;
                }else{
                    $scope.halo1=false;
                }
            })
        }, 6000)
    }*/

    //执行脚本
    $scope.make=function () {
        userService.make().success(function (res) {
            if(res==1){
                //执行完毕
                location.href="http://219.239.103.109:8080/test/submit.html"
            }
        })
    }








})

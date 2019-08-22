app.controller("viewController2",function ($scope,$controller,viewService2) {

    $controller('baseController',{$scope:$scope});//继承


    //去数据库查询历史记录
    $scope.list=[];
    //分页
    $scope.findPage=function(page,rows){
        viewService2.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }


    // 初始化一个id数组
    $scope.selectIds = [];
    // [1,2,3]
    // 定义选择品牌列表id事件
    $scope.updateSelection = function ($event, id) {
        // 判断品牌是否被选中
        if ($event.target.checked) {
            $scope.selectIds.push(id);
        } else {
            // 删除id
            $scope.selectIds.splice($scope.selectIds.indexOf(id), 1);
        }
    };


    /**
     * 全部删除
     */
    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        viewService2.dele($scope.selectIds).success(function () {
                window.location.reload();//刷新列表
            }
        );
    };

    //定义对象
    $scope.entity = {};

    /**
     * 根据id查找数据
     */
    $scope.findOne = function(id) {
        viewService2.findOne(id).success(function(response) {
            $scope.entity = response;
        });
    };



    $scope.all=false;
    $scope.checkAll = function (all) {
        if (all == false) {
            var rst = [];
            for (var i = 0; i <$scope.list.length; i++) {
                //取出id存取数组
                rst.push($scope.list[i].id)
            }
            $scope.selectIds = rst;
        } else {
            $scope.selectIds = [];
        }

    };


    $scope.save=function(){
        var serviceObject;//服务层对象
        if($scope.entity.id!=null){//如果有ID
            serviceObject=viewService2.update($scope.entity); //修改
        }else{
            serviceObject=viewService2.add($scope.entity);//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    window.location.reload();//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }


});
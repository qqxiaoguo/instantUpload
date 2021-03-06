app.service("viewService3",function ($http) {
    //分页
    this.findPage=function(page,rows){
        return $http.get('../viewPDF1/viPDF4/'+page+'/'+rows);
    }

    //删除
    this.dele = function (ids) {
        return $http.post('../person/delete/' +ids);
    };

    this.sub = function (ids) {
        return $http.post('../strain/sub2/' +ids);
    };


    //查询单条数据
    this.findOne = function (id) {
        return $http.get('../person/findOne/' + id);
    };


    //修改
    this.update = function (entity) {
        return $http.post('../person/update/', entity)
    };
    //添加
    this.add = function (entity) {
        return $http.post('../person/save/', entity)
    }

});
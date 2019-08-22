app.service("viewService",function ($http) {
    //分页strain
    this.findPage=function(page,rows){
        return $http.get('../viewPDF1/viPDF2/'+page+'/'+rows);
    }
    //删除
    this.dele = function (ids) {
        return $http.post('../strain/delete/' +ids);
    };
    //筛选菌株(503)
    this.sub = function (ids) {
        return $http.post('../strain/sub1/' +ids);
    };
    //查询单条数据
    this.findOne = function (id) {
        return $http.get('../strain/findOne/' + id);
    };
    //修改
    this.update = function (entity) {
        return $http.post('../strain/update/', entity)
    };
    //添加
    this.add = function (entity) {
        return $http.post('../strain/save/', entity)
    }

});
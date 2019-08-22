app.service("userService",function ($http) {
    

//ajax异步交互
    this.findOne=function (username) {
        return  $http.post("./user/findOne?username="+username);
    }

    //ajax异步交互
    /*,repository_id*/
    this.save=function (file,name) {
        return  $http.get("../upload/picture?file="+file);
        // return  $http.get("../upload/picture?name="+name+"&&repository_id="+repository_id);
    }
    //发送邮件
    this.sendSms=function () {
        return  $http.post("../sendSms/send2");
    }


    //检查是否登录
    this.check=function () {
        return  $http.post("../user/checkTheUser");
    }

    //给菌株命名
    this.name=function () {
        return  $http.post("../name/use");
    }

    //获取当前排队人数
    this.send=function () {
        return  $http.post("/upload/make2");
    }
    this.send2=function () {
        return  $http.post("/upload/make3");
    }

    //执行脚本
    this.make=function () {
        return  $http.post("/upload/make");
    }
    //发送邮件
    this.sendEmail=function () {
        return  $http.post("/sendSms/send");
    }
    //判断几人排队
    this.bool=function () {
        return  $http.post("/upload/make3");
    }
    /*//删除队列中的数据
    this.bool2=function () {
        return  $http.post("/upload/make5");
    }
    //killall - 9
    this.cancel=function () {
        return  $http.post("/kill/active");
    }*/
})
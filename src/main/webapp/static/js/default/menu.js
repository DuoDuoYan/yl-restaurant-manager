$().ready(function() {
    //获取表格数据
    init();

    btnFunction();

    //数据校验
    validator($('#addMenuForm'));
    validator($('#updateMenuForm'));

    //清空下拉框
    $('#addCatalog').empty();
    $('#updateCatalog').empty();
    $('#ViewCatalog').empty();
    //加载下拉框
    onLoadSelect();

});

function onLoadSelect(){
    var ajaxTimeOut =
        $.ajax({
            type:"post",
            url:"/restaurant/findAllCatalogName",
            dataType:"json",
            async:true,
            cache:false,
            timeout:5000,
            success:function (data) {
                if(data){
                    for (var i = 0;i<data.length;i++){
                        $("#addCatalog").append("<option value='"+data[i].id+"'>"+data[i].catalogName+"</option>");
                        $("#viewCatalog").append("<option value='"+data[i].id+"'>"+data[i].catalogName+"</option>");
                        $("#updateCatalog").append("<option value='"+data[i].id+"'>"+data[i].catalogName+"</option>");
                    }
                }else{
                }
            },
            error:function () {
                console.info("加载下拉框数据失败");
            },
            complete:function (XMLHttpRequest,status) {
                if(status == 'timeout'){
                    //若请求超时，弹出提示，并取消请求
                    alert("请求超时");
                    ajaxTimeOut.abort();
                }
            }
        });
}


function init(){
    //销毁表格
    $('#dataTable').bootstrapTable('destroy');
    //动态从服务器加载数据
    $('#dataTable').bootstrapTable({
        ajaxOptions: {
            cache : true,
            async : true
        },
        method : 'post', // 服务器数据的请求方式 get or post
        url : "/restaurant/findAllValidMenu", // 服务器数据的加载地址
        iconSize : 'outline',
        toolbar : '#exampleToolbar',
        striped : false, // 设置为true会有隔行变色效果
        dataType : "json", // 服务器返回的数据类型
        pagination : true, // 设置为true会在底部显示分页条
        showFooter:false,
        // sortable:true,  //是否启用排序
        // sortOrder:"asc",    //排序方式
        // sidePagination:"server",    //分页方式：client客户端分页，server服务器端分页
        queryParamsType : '',  // 设置为limit则会发送符合RESTFull格式的参数
        singleSelect : false, // 设置为true将禁止多选
        contentType : "application/x-www-form-urlencoded",  // 发送到服务器的数据编码类型
        pageList: [5,10,15],
        pageSize : 10, // 如果设置了分页，每页数据条数
        pageNumber : 1, // 如果设置了分布，首页页码
        // search : true, // 是否显示搜索框
        paginationShowPageGo: true,
        showColumns : false, // 是否显示内容下拉框（选择显示的列）
        sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParams : function(params) {
            //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
            var param = {
                pageNo : params.pageNumber,
                pageSize : params.pageSize,
            };
            return param;
        },
        responseHandler: function(data) {
            return {
                "total": data.total,//总数
                "rows": data.data   //数据
            };
        },
        columns : [
            {
                field : 'no',
                title : '序号',
                align: 'center',
                valign : "middle",
                formatter:function(value,row,index){
                    return index + 1;
                }
            },
            {
                field : 'id',
                title : 'id',
                visible: false,
                align: 'center',
                valign : "middle",
            },
            {
                field : 'food',
                title : '名称',
                align: 'center',
                valign : "middle",
            },
            {
                field : 'info',
                title : '简介',
                align: 'center',
                valign : "middle",
                width:150,
                formatter:function (value,row,index) {
                    return "<div title='"+value+"'; style='width:150;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;word-wrap:break-all;word-break:break-all;' href='javascript:edit(\""+row.id+"\",true)'>"+value+"</div>"
                }
            },
            {
                field : 'price',
                title : '价格',
                align: 'center',
                valign : "middle",
            },
            {
                field : 'catalogName',
                title : '分类',
                align: 'center',
                valign : "middle",
            },
            {
                field : 'quantity',
                title : '数量',
                align: 'center',
                valign : "middle",
            },
            {
                field : 'selled',
                title : '已销售',
                align: 'center',
                valign : "middle",
            },
            {
                field : 'image',
                title : '图片',
                align: 'center',
                valign : "middle",
                formatter : function (value, row, index) {
                    return "<a class='view'  href='javascript:void(0)'><img style='width: 30px;height: 20px;' src='"+value+"' alt=''></a>";
                },
                events: operateEvents
            },
            {
                field : 'Button',
                title : '操作',
                align: 'center',
                valign : "middle",
                width: 120,
                events:operateEvents, //给按钮注册事件
                formatter:AddFunctionAlty, //表格中增加按钮
            }],
        onLoadSuccess: function (res) {//可不写
            //加载成功时
        }, onLoadError: function (statusCode) {
            console.log(statusCode);
            return "加载失败了";
        }, formatLoadingMessage: function () {
            //正在加载
            return "数据加载中...";
        }, formatNoMatches: function () {
            //没有匹配的结果
            return '无符合条件的数据';
        }, onPostBody: function () {

        }, onPageChange:function () {
        }
    });
}

function AddFunctionAlty(value,row,index){
    return[
        '<a href="#" id="info"><img src="/static/image/info.png" style="width: 20px;height: 20px;margin-right: 20px"></a>',
        '<a href="#" id="edit"><img src="/static/image/edit.png" style="width: 20px;height: 20px;margin-right: 20px"></a>',
        '<a href="#" id="delete"><img src="/static/image/delete.png" style="width: 20px;height: 20px"></a>'
    ].join("")
}

window.operateEvents = {   //添加一个按钮在对应的按钮组中写下需要使用的事件
    "click #info":function(e,value,row,index){  //查看按钮事件  row当前行，index当前行的下标
        $("#viewName").val(row.food);
        $('#viewCatalog').find("option").each(function (i,n) {
            if(n.value == row.catalog){
                n.selected = true;
            }
        })
        $("#viewPrice").val(row.price);
        $("#viewQuantity").val(row.quantity);
        $("#viewPhoto").attr('src',row.photo);
        console.info("photo:"+ row.photo)
        $("#viewInfo").val(row.info);
        $("#viewSelled").val(row.selled);

        $('#viewMenu').modal('show');

    },
    "click #edit":function(e,value,row,index){  //编辑按钮事件  row当前行，index当前行的下标
        $("#id").val(row.id);
        $("#updateName").val(row.food);
        $('#updateCatalog').find("option").each(function (i,n) {
            if(n.value == row.catalog){
                n.selected = true;
            }
        })
        $("#updatePrice").val(row.price);
        $("#updateQuantity").val(row.quantity);
        $("#updatePhoto").val(row.photo);
        $("#updateInfo").val(row.info);
        $('#updateMenu').modal('show');
    },
    "click #delete":function(e,value,row,index){	//删除按钮事件
        var id = row.id;
        $.ajax({
            method:"post",
            url:"/restaurant/deleteMenu",
            data:{"id":id},
            success:function (result) {
                location.reload()  //刷新当前页面
            }
        })
    },
    "click .view":function (e,value,row,index) {
        layer.open({
            type:1,
            title:false,
            closeBtn:0,
            area:['auto','auto'],
            skin:'layui-layer-nobg',//没有背景色
            shadeClose:true,
            content:'<img src="'+value+'"/>'
        })
    }
}

function  btnFunction() {

    $('#addMenuBtn').click(function () {
        var data = new FormData($('#addMenuForm')[0]);          //创建fromData对象

        //验证输入数据结果
        if($('#addMenuForm').data('bootstrapValidator').isValid()){
            $.ajax({
                type:"post",
                url:"/restaurant/addMenu",
                dataType : 'JSON',
                cache: false,
                contentType:false,
                processData:false,
                data: data,
                success:function (result) {
                    location.reload()  //刷新当前页面
                }
            })
        }else{
            alert("error")
        }
    })

    $('#updateMenuBtn').click(function () {
        var data = new FormData($('#updateMenuForm')[0]);
        $.ajax({
            method:"post",
            url:"/restaurant/updateMenu",
            dataType : 'JSON',
            cache: false,
            contentType:false,
            processData:false,
            data:data,
            success:function (result) {
                location.reload()  //刷新当前页面
            },
            error:function () {
            }
        })
    })

    $('#refresh').click(function () {
        location.reload()  //刷新当前页面
    })
}

function validator(obj) {
    obj.bootstrapValidator({
        message: '输入值不合法',
        feedbackIcons: {
            // valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            food: {
                message: '菜品验证失败',
                validators: {
                    notEmpty: {
                        message: '菜品名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 18,
                        message: '菜品名长度必须在2到18位之间'
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        message: '价格不能为空'
                    },
                    digits: {
                        message: '该值只能包含数字。'
                    }
                }
            },
            quantity: {
                validators: {
                    notEmpty: {
                        message: '数量不能为空'
                    },
                    digits: {
                        message: '该值只能包含数字。'
                    }
                }
            }
        }
    });
}
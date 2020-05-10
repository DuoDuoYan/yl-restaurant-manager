$().ready(function() {
    //获取表格数据
    init();

    btnFunction();

    //数据校验
    validator($('#addTableForm'));
    validator($('#updateTableForm'));

});

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
        url : "/restaurant/findAllTable", // 服务器数据的加载地址
        iconSize : 'outline',
        toolbar : '#exampleToolbar',
        striped : false, // 设置为true会有隔行变色效果
        dataType : "json", // 服务器返回的数据类型
        pagination : true, // 设置为true会在底部显示分页条
        showFooter:false,
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
                formatter:function(value,row,index){
                    return index + 1;
                }
            },
            {
                field : 'id',
                title : 'id',
                visible: false,
                align: 'center',
            },
            {
                field : 'num',
                title : '餐桌编号',
                align: 'center',
            },
            {
                field : 'statusName',
                title : '状态',
                align: 'center',
            },
            {
                field : 'Button',
                title : '操作',
                align: 'center',
                width: 120,
                events:operateEvents, //给按钮注册事件
                formatter:AddFunctionAlty, //表格中增加按钮
            }],
        onLoadSuccess: function (res) {//可不写
            //加载成功时
        }, onLoadError: function (statusCode) {
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
    ].join("")
}

window.operateEvents = {   //添加一个按钮在对应的按钮组中写下需要使用的事件
    "click #info":function(e,value,row,index){  //查看按钮事件  row当前行，index当前行的下标
        $("#viewNum").val(row.num);
        $('#viewStatus').find("option").each(function (i,n) {
            if(n.value == row.statusName){
                n.selected = true;
            }
        })

        $('#viewTable').modal('show');

    },
    "click #edit":function(e,value,row,index){  //编辑按钮事件  row当前行，index当前行的下标
        $("#id").val(row.id);
        $("#updateNum").val(row.num);
        $('#updateStatus').find("option").each(function (i,n) {
            if(n.value == row.statusName){
                n.selected = true;
            }
        })
        $('#updateTable').modal('show');
    }
}

function  btnFunction() {

    $('#addTableBtn').click(function () {
        var num = $('#addNum').val();
        //验证输入数据结果
        if($('#addTableForm').data('bootstrapValidator').isValid()){
            $.ajax({
                type:"post",
                url:"/restaurant/addTable",
                data: {"num":num},
                success:function (result) {
                    location.reload()  //刷新当前页面
                }
            })
        }else{
            alert("改名称已存在")
        }
    })

    $('#updateTableBtn').click(function () {
        var id = $('#id').val();
        var num = $('#updateNum').val();
        var status = $('#updateStatus').find("option:selected").val();
        $.ajax({
            method:"post",
            url:"/restaurant/updateTable",
            data:{"id":id,"num":num,"status":status},
            success:function (result) {
                location.reload()  //刷新当前页面
            },
            error:function () {
                console.info("error")
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
            num: {
                message: '分类名称验证失败',
                validators: {
                    notEmpty: {
                        message: '分类名称不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 18,
                        message: '分类名称长度必须在2到18位之间'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: '状态不能为空'
                    }
                }
            }
        }
    });
}
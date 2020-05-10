$().ready(function() {
    //获取表格数据
	init();

	btnFunction();

	//数据校验
    validator($('#addAdminForm'));
    validator($('#updateAdminForm'));

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
		url : "/restaurant/findAllAdmin", // 服务器数据的加载地址
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
				field : 'username',
				title : '用户名',
                align: 'center',
			},
			{
				field : 'password',
				title : '密码',
                visible: false,
                align: 'center',
			},
			{
				field : 'phone',
				title : '手机号',
                align: 'center',
			},
			{
				field : 'gender',
				title : '性别',
                align: 'center',
			},
			{
				field : 'age',
				title : '年龄',
                align: 'center',
			},
			{
				field : 'address',
				title : '地址',
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
        $("#viewAccount").val(row.phone);
        $("#viewUsername").val(row.username);
        $("#viewPassword").val(row.password);
        $("#viewAge").val(row.age);
        $("#viewAddress").val(row.address);
        if(row.gender != '男'){
			$('#viewMale').removeAttr('checked');
			$('#viewFemale').attr('checked','checked');
		}
		$('#viewAdmin').modal('show');

    },
	"click #edit":function(e,value,row,index){  //编辑按钮事件  row当前行，index当前行的下标
        $("#updateAccount").val(row.phone);
        $("#originalPhone").val(row.phone);
        $("#updateUsername").val(row.username);
        $("#updatePassword").val(row.password);
        $("#updateAge").val(row.age);
        $("#updateAddress").val(row.address);
        if(row.gender == '女'){
            $('#updateMale').removeAttr('checked');
            $('#updateFemale').attr('checked','checked');
        }
        $('#updateAdmin').modal('show');
	
	},
	"click #delete":function(e,value,row,index){	//删除按钮事件
		var id = row.id;
		$.ajax({
            method:"post",
            url:"/restaurant/deleteById",
            data:{"id":id},
            success:function (result) {
                location.reload()  //刷新当前页面
            }
		})

	}
}

function  btnFunction() {

    $('#addAdminBtn').click(function () {
        var phone = $('#addAccount').val();
        var username = $('#addUsername').val();
        var password = $('#addPassword').val();
        if($('#addMale').is(':checked')){
            var gender = '男';
        }else{
            var gender = '女';
        }
        var age = $('#addAge').val();
        var address = $('#addAddress').val();
        //验证输入数据结果
        if($('#addAdminForm').data('bootstrapValidator').isValid()){
            $.ajax({
                method:"post",
                url:"/restaurant/addAdmin",
                data:{"phone":phone,"username":username,"password":password,"gender":gender,"age":age,"address":address},
                success:function (result) {
                    location.reload()  //刷新当前页面
                }
            })
        }else{
            alert("error")
        }

    })

    $('#updateAdminBtn').click(function () {
        var phone = $('#updateAccount').val();
        var username = $('#updateUsername').val();
        var password = $('#updatePassword').val();
        if($('#updateMale').is(':checked')){
            var gender = '男';
        }else{
            var gender = '女';
        }
        var age = $('#updateAge').val();
        var address = $('#updateAddress').val();
        $.ajax({
            method:"post",
            url:"/restaurant/updateUser",
            data:{"phone":phone,"username":username,"password":password,"gender":gender,"age":age,"address":address},
            success:function (result) {
                location.reload()  //刷新当前页面
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
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 6,
                        message: '用户名长度必须在2到6位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '用户名只能包含中文、大写、小写、数字和下划线'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 5,
                        max: 12,
                        message: '请输入5到12个字符'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线'
                    }
                }
            },
            age: {
                validators: {
                    notEmpty: {
                        message: '年龄不能为空'
                    },
                    between: {
                        min: 1,
                        max: 100,
                        message : '年龄必须为数字，最小输入1，最大输入100'
                    }
                }
            },
            account: {
                validators: {
                    notEmpty: {
                        message: '手机号不能为空'
                    },
                    regexp: {
                        regexp:  /^1[3|5|8]{1}[0-9]{9}$/,
                        message: '手机号码格式错误'
                    },
                    threshold:11,
                    remote: {
                        url: '/restaurant/findByPhone',
                        message: '该手机号已存在，请重新输入',
                        delay: 500,
                        type: 'POST',
                        data: {
                            phone: function () {
                                return $('#addAccount').val()
                            }
                        }
                    },
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: '地址不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 40,
                        message: '请输入6到40个字符'
                    }
                }
            }

        }
    });
}
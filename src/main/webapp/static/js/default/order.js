$().ready(function() {
	init();

	btnFunction();
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
		url : "/restaurant/findAllOrder", // 服务器数据的加载地址
		iconSize : 'outline',
		toolbar : '#exampleToolbar',
		striped : false, // 设置为true会有隔行变色效果
		dataType : "json", // 服务器返回的数据类型
		pagination : true, // 设置为true会在底部显示分页条
		showFooter:false,
		queryParamsType : '',  // 设置为limit则会发送符合RESTFull格式的参数
		singleSelect : false, // 设置为true将禁止多选
		contentType : "application/x-www-form-urlencoded",  // 发送到服务器的数据编码类型
		pageList: [10,15,20],
		pageSize : 10, // 如果设置了分页，每页数据条数
		pageNumber : 1, // 如果设置了分布，首页页码
		paginationShowPageGo: true,
		showColumns : false, // 是否显示内容下拉框（选择显示的列）
		sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
		queryParams : function(params) {
			//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
			var param = {
				pageNo : params.pageNumber,
				pageSize : params.pageSize,
				startTime : $('#startTime').val(),
				endTime : $('#endTime').val(),
				condition : $('#condition').val(),
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
				title : '订单编号',
				align: 'center',
			},
			{
				field : 'tableNum',
				title : '餐桌编号',
				align: 'center',
			},
			{
				field : 'robotNum',
				align: 'center',
				title : '机器人编号'
			},
			{
				field : 'customer',
				align: 'center',
				title : '客户'
			},
			{
				field : 'cooker',
				align: 'center',
				title : '厨师'
			},
			{
				field : 'totalPrice',
				align: 'center',
				title : '总价格'
			},

			{
				field : 'status',
				align: 'center',
				title : '订单状态'
			},
			{
				field : 'date',
				align: 'center',
				title : '订单日期'
			},
			{
				field : 'Button',
				align: 'center',
				title : '操作',
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
		'<a href="#" id="info"><img src="/static/image/info.png" style="width: 20px;height: 20px;"></a>',
	].join("")
}

window.operateEvents = {   //添加一个按钮在对应的按钮组中写下需要使用的事件
	"click #info":function(e,value,row,index){  //查看按钮事件  row当前行，index当前行的下标
		var ta = document.getElementById("dataDetail");
		$("#dataDetail tbody").empty();
		var menu = document.createElement("tbody");
		$.ajax({
			method:"post",
			url:"/restaurant/findOrderByNum",
			data:{"num":row.id},
			success:function (result) {
				var i=0;
				while (i<result.length){
					var row = document.createElement("tr");
					var foodCell = document.createElement("th");
					foodCell.innerHTML = result[i].food;
					row.appendChild(foodCell);
					var priceCell = document.createElement("th");
					priceCell.innerHTML = result[i].price;
					row.appendChild(priceCell);
					var quantityCell = document.createElement("th");
					quantityCell.innerHTML = result[i].quantity;
					row.appendChild(quantityCell);
					menu.appendChild(row);
					i++;
				}
			},
			error:function () {
				alert("请求失败，请稍后重试！")
			}
		})
		ta.appendChild(menu);
		$('#viewOrderDetail').modal('show');

	}
}

function  btnFunction() {

	$('#search').click(function () {
		init();
	})

	$('#refresh').click(function () {

		location.reload()  //刷新当前页面
	})
}


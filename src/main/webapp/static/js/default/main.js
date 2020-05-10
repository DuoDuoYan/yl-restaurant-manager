$().ready(function() {
	
	//获取当前时间
	getNowDate();
	//设置iframe的宽、高自适应
	//setIframeSize();
	defaultIframe();
	initUpdate();

	initInfo();
});

function getNowDate(){
	var date = new Date() 	//获得日期数据
			var y = date.getFullYear();<!--年-->
			var m = date.getMonth()+1;<!--月，这里的月份必须要+1才是当前月份-->
			var d = date.getDate(); 
			var day = date.getDay();
			var arr = new Array ("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
			document.getElementById("nowDate").innerHTML = "今天是  "+y+" 年 "+m+" 月 "+d+" 日 "+ arr[day];
}

function search(){
	alert("待开发！");
}

function initInfo(){
		var phone = $('#updateAccount').val();
		$.ajax({
			url:"/restaurant/findUserByPhone",
			method:"post",
			data:{"phone":phone},
			success:function (result) {
				$('#updateUsername').val(result.username);
				if(result.gender == '女'){
					$('#updateMale').removeAttr('checked');
					$('#updateFemale').attr('checked','checked');
				}
				$('#updateAge').val(result.age);
				$('#updateAddress').val(result.address);
			}
		})
}

function initUpdate() {

	$('#updateInfoBtn').click(function () {
		var phone = $('#updateAccount').val();
		var username = $('#updateUsername').val();
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
			data:{"phone":phone,"username":username,"gender":gender,"age":age,"address":address},
			success:function (result) {
				location.reload()  //刷新当前页面
			}
		})
	})
}

/**
 * 点击切换图片
 */
function switchPhoto(obj){
	var img = $(obj).prev().prev();
	if(img.attr('src') == '/static/image/right.png'){
		$('.pane-right').attr('src','/static/image/right.png');
		img.attr('src','/static/image/bottom.png');
	}else{
		img.attr('src','/static/image/right.png');
	}
}

function setIframeSize(){
	$('#contentIframe').css('width',$('#panel-right').width());
}

function switchIframe(obj){
	var src = $(obj).attr('id');
	$('#contentIframe').attr('src','/restaurant/' + src);
	console.info($('#contentIframe').attr("src"))
}

function defaultIframe(){
	$('#contentIframe').attr('src','/restaurant/default');
}
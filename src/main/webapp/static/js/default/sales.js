$().ready(function() {

	initDate();

	initBtn();

	initData();
});
function initDate() {
	$( "#datepicker" ).datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		beforeShow: function(dateText, inst){
			$("#ui-datepicker-div").addClass('month');
		},
		onClose: function(dateText, inst) {
			var month = $("#ui-datepicker-div .ui-datepicker-month option:selected").val();//得到选中的月份值
			var year = $("#ui-datepicker-div .ui-datepicker-year option:selected").val();//得到选中的年份值
			if ((parseInt(month)+1)  < 10) {
				var months = '0'+ (parseInt(month)+1);
			} else {
				months = (parseInt(month))+1;
			}
			$('#datepicker').val(year+'-'+months);//给input赋值，其中要对月值加1才是实际的月份
		}
	});
}

function initBtn() {

	$('#searchByDate').click(function () {
		initData();
	})
}

function initData() {
	var date = $('#datepicker').val()+'-1';
	if(date == '-1'){
		date = null;
	}
	$.ajax({
		url:'/restaurant/findByDate',
		data:{'date':date},
		type:'post',
		success:function (result) {
			//获取天数
			var days = new Array();
			for(var n=0;n<result[0];n++){
				days[n] = n;
			}
			//获取分类
            var catalog = new Array();
            catalog = result[1];

			var datas = new Array();
			for(var i=0;i<catalog.length;i++){
				datas.push({label:catalog[i]+'     ',data:result[2+i]});
			}

            var svgLine = document.querySelector('.line-chart')

            var lineChart = new chartXkcd.Line(svgLine, {
                title: '日销售情况',
                xLabel: '日期',
                yLabel: '金额',
                data:{
					// x 轴数据
					labels:days,
					// y 轴数据
					datasets:datas,
				},
				// 可选配置以自定义图表的外观
                options: {
                    yTickCount: 10,
                    legendPosition: chartXkcd.config.positionType.upRight
                }
            });
		}
	})
}

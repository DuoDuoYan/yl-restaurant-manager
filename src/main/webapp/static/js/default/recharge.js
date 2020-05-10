$().ready(function() {
    init();
});

function init() {
    var balance;
    //其他金额
    $('.othbox').on('input propertychange', function() {
        var num = $(this).val();
        $('.rechnum').html(num + ".00元");
        balance = num;
    });
    // 将所有.ui-choose实例化
    $('.ui-choose').ui_choose();
    // uc_01 ul 单选
    var uc_01 = $('#uc_01').data('ui-choose'); // 取回已实例化的对象
    uc_01.click = function(index, item) {
        console.log('click', index, item.text())
    }
    uc_01.change = function(index, item) {
        if(index == 0){
            balance = 50;
        }else if(index == 1){
            balance = 100;
        }else if(index == 2){
            balance = 500;
        }
        console.log('change', index, item.text())
    }
        $('#uc_01 li:eq(3)').click(function() {
            $('.tr_rechoth').show();
            $('.tr_rechoth').find("input").attr('required', 'true')
            $('.rechnum').text('50.00元');
        })
        $('#uc_01 li:eq(0)').click(function() {
            $('.tr_rechoth').hide();
            $('.rechnum').text('50.00元');
            $('.othbox').val('');
        })
        $('#uc_01 li:eq(1)').click(function() {
            $('.tr_rechoth').hide();
            $('.rechnum').text('100.00元');
            $('.othbox').val('');
        })
        $('#uc_01 li:eq(2)').click(function() {
            $('.tr_rechoth').hide();
            $('.rechnum').text('500.00元');
            $('.othbox').val('');
        })

        $('#doc-vld-msg').validator({
            onValid: function(validity) {
                $(validity.field).closest('.am-form-group').find('.am-alert').hide();
            },
            onInValid: function(validity) {
                var $field = $(validity.field);
                var $group = $field.closest('.am-form-group');
                var $alert = $group.find('.am-alert');
                // 使用自定义的提示信息 或 插件内置的提示信息
                var msg = $field.data('validationMessage') || this.getValidationMessage(validity);

                if(!$alert.length) {
                    $alert = $('<div class="am-alert am-alert-danger"></div>').hide().
                    appendTo($group);
                }
                $alert.html(msg).show();
            }
        });

    var phoneNum;
    $('#searchPhone').click(function () {
        var phone = $('#phone').val();
        if(phone.length<0){
            $('#account').innerHTML = "无效手机号";
        }else{
            $.ajax({
                type:"post",
                url:'/restaurant/findByPhone',
                data:{"phone":phone},
                success:function (result) {
                    if( $.isEmptyObject(result)){
                        $('#account').text("无效手机号") ;
                        $('#balance').text("0");
                    }else{
                        phoneNum = result.phone;
                        console.info("phoneNum:"+phoneNum)
                        $('#account').text(result.username);
                        $('#balance').text(result.balance);
                    }
                },error:function () {
                    alert("出现未知错误")
                }
            })
        }
    })

    $('#pay').click(function () {

        var payType = $("input[name='radio1']:checked").val();

        if(typeof(balance)==="undefined"){
            layer.open({
                title: false,
                content: '<p style="text-align:center">请先填写账户信息、金额、充值方式</p>'
            });
            return false;
        }else{
            $.ajax({
                type:"post",
                url:'/restaurant/addBanlance',
                data:{"phone":phoneNum,"balance":balance},
                success:function (result) {
                        layer.open({
                            title: false,
                            content: '<p style="text-align:center">充值成功</p>'
                    });
                },error:function () {
                    layer.open({
                        title: false,
                        content: '<p style="text-align:center">出现未知错误，请联系相关人员</p>'
                    });
                }
            })
        }
    })
}


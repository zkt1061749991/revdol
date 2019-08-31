/*提交成功后提示*/


// $("LockForm").validate({
//     //调试模式，只验证，不能提交，false为关闭
//     debug: false,
//     submitHandler: function (form)
//     {
//         form.submit();
//         alert("提交成功!");
//     }
// });
/*检验合法性*/
$().ready(function () {
// 在键盘按下并释放及提交后验证提交表单
    $("#newfanForm").validate({
        rules: {
            new_qq: {
                required: true,
                digits: true,
                minlength: 8,
                maxlength: 13
            },
            new_password: {
                required: true,
                digits: true,
                minlength: 6,
                maxlength: 6
            }
        },
        messages: {
            new_qq: {
                required: "请输入QQ号",
                digits: "请输入合法的QQ号",
                minlength: "请输入合法的QQ号",
                maxlength: "请输入合法的QQ号"
            },
            new_password: {
                required: "请设置查询密码",
                digits: "查询密码为六位数字",
                minlength: "密码长度过短",
                maxlength: "密码长度过长"
            }
        }
    });
});


$.validator.setDefaults({
    submitHandler: function (form) {
        form.submit();
    }
});

function ret(){
    if(confirm) {
        return true;
    };
    return false;
}


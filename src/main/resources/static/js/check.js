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
    $("#LockForm").validate({
        rules: {
            password: {
                required: true,
                digits: true,
                minlength: 6,
                maxlength: 6
            },
            confirm_password: {
                required: true,
                digits: true,
                minlength: 6,
                maxlength: 6,
                equalTo: "#password"
            }
        },
        messages: {
            password: {
                required: "请输入新的查询密码",
                digits: "查询密码为六位数字",
                minlength: "密码长度过短",
                maxlength: "密码长度过长"
            },
            confirm_password: {
                required: "请输入确认查询密码",
                digits: "查询密码为六位数字",
                minlength: "密码长度过短",
                maxlength: "密码长度过长",
                equalTo: "两次密码输入不一致"
            }
        }
    });
    $("#SetupForm").validate({
        rules: {
            name: {
                maxlength: 18
            },
            xcxname: {
                maxlength: 18
            },
            bilibili: {
                digits: true,
                maxlength: 12
            },
            xcxuid: {
                digits: true,
                maxlength: 12
            },
        },
        messages: {
            name: {
                maxlength: "名字太长啦！"
            },
            xcxname: {
                maxlength: "名字太长啦！"
            },
            bilibili: {
                digits: "请输入合法的UID",
                maxlength: "请输入合法的UID"
            },
            xcxuid: {
                digits: "请输入合法的UID",
                maxlength: "请输入合法的UID"
            },
        }
    });
    $("#RegForm").validate({
        rules: {
            qq: {
                required: true,
                rangelength: [6, 12]
            },
            username: {
                required: true,
                rangelength: [2, 12]
            },
            password: {
                required: true,
                minlength: 5
            },
            confirm_password: {
                required: true,
                minlength: 5,
                equalTo: "#password"
            }
        },
        messages: {
            qq: {
                required: "请输入QQ号",
                rangelength: "请输入合法的ID"
            },
            username: {
                required: "请输入用户名",
                rangelength: "用户名长度请限制在2到12个字符内"
            },
            password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母"
            },
            confirm_password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母",
                equalTo: "两次密码输入不一致"
            }
        }
    });
    $("#TalkForm").validate({
        rules: {
            type: {
                required:true
            },
            body: {
                required:true,
                minlength: 18
            }
        },
        messages: {
            type: {
                required: "选择一个反馈类型"
            },
            body: {
                required: "内容不能为空哦",
                minlength: "请至少输入18个字"
            }
        }
    });
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
        $.alert("提交成功啦！");
    }
});

function ret(){
    if(confirm) {
        return true;
    };
    return false;
}


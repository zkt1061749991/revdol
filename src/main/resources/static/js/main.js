/*导航栏渐变逻辑*/
$(document).ready(fade());

// window.alert = function (msg, callback) {
//     var div = document.createElement("div");
//     div.innerHTML = "<style type=\"text/css\">"
//         + ".nbaMask { position: fixed; z-index: 1000; top: 0; right: 0; left: 0; bottom: 0; background: rgba(0, 0, 0, 0.5); }                                                                                                                                                                       "
//         + ".nbaMaskTransparent { position: fixed; z-index: 1000; top: 0; right: 0; left: 0; bottom: 0; }                                                                                                                                                                                            "
//         + ".nbaDialog { position: fixed; z-index: 5000; width: 80%; max-width: 300px; top: 50%; left: 50%; -webkit-transform: translate(-50%, -50%); transform: translate(-50%, -50%); background-color: #fff; text-align: center; border-radius: 8px; overflow: hidden; opacity: 1; color: white; }"
//         + ".nbaDialog .nbaDialogHd { padding: .2rem .27rem .08rem .27rem; }                                                                                                                                                                                                                         "
//         + ".nbaDialog .nbaDialogHd .nbaDialogTitle { font-size: 17px; font-weight: 400; }                                                                                                                                                                                                           "
//         + ".nbaDialog .nbaDialogBd { padding-top: 1.5rem; padding-left: 1.5rem; padding-right: 1.5rem; font-size: 15px; text-align: left; line-height: 1.3; word-wrap: break-word; word-break: break-all; color: #474747; }                                                                                                                                          "
//         + ".nbaDialog .nbaDialogFt { position: relative; line-height: 48px; font-size: 17px; display: -webkit-box; display: -webkit-flex; display: flex; }                                                                                                                                          "
//         + ".nbaDialog .nbaDialogFt:after { content: \" \"; position: absolute; left: 0; top: 0; right: 0; height: 1px; border-top: 1px solid #e6e6e6; color: #e6e6e6; -webkit-transform-origin: 0 0; transform-origin: 0 0; -webkit-transform: scaleY(0.5); transform: scaleY(0.5); }               "
//         + ".nbaDialog .nbaDialogBtn { display: block; -webkit-box-flex: 1; -webkit-flex: 1; flex: 1; color: #e0c13a; font-size:16px; text-decoration: none; -webkit-tap-highlight-color: transparent; position: relative; margin-bottom: 0; }                                                                       "
//         + ".nbaDialog .nbaDialogBtn:after { content: \" \"; position: absolute; left: 0; top: 0; width: 1px; bottom: 0; border-left: 1px solid #e6e6e6; color: #e6e6e6; -webkit-transform-origin: 0 0; transform-origin: 0 0; -webkit-transform: scaleX(0.5); transform: scaleX(0.5); }             "
//         + ".nbaDialog a { text-decoration: none; -webkit-tap-highlight-color: transparent; }"
//         + "</style>"
//         + "<div id=\"dialogs2\" style=\"display: none\">"
//         + "<div class=\"nbaMask\"></div>"
//         + "<div class=\"nbaDialog\">"
//         + "	<div class=\"nbaDialogHd\">"
//         + "		<strong class=\"nbaDialogTitle\"></strong>"
//         + "	</div>"
//         + "	<div class=\"nbaDialogBd\" id=\"dialog_msg2\">弹窗内容，告知当前状态、信息和解决方法，描述文字尽量控制在三行内</div>"
//         + "	<div class=\"nbaDialogHd\">"
//         + "		<strong class=\"nbaDialogTitle\"></strong>"
//         + "	</div>"
//         + "	<div class=\"nbaDialogFt\">"
//         + "		<a href=\"javascript:;\" class=\"nbaDialogBtn nbaDialogBtnPrimary\" id=\"dialog_ok2\">好der</a>"
//         + "	</div></div></div>";
//     document.body.appendChild(div);
//
//     var dialogs2 = document.getElementById("dialogs2");
//     dialogs2.style.display = 'block';
//
//     var dialog_msg2 = document.getElementById("dialog_msg2");
//     dialog_msg2.innerHTML = msg;
//
//     // var dialog_cancel = document.getElementById("dialog_cancel");
//     // dialog_cancel.onclick = function() {
//     // dialogs2.style.display = 'none';
//     // };
//     var dialog_ok2 = document.getElementById("dialog_ok2");
//     dialog_ok2.onclick = function () {
//         dialogs2.style.display = 'none';
//         callback();
//     };
// };


function fade() {
    $("#in").on("click", function () {
        $("#div1").fadeToggle(100);
        $("#div2").fadeToggle(100);
        $("#div3").fadeToggle(100);
        $("#div4").fadeToggle(100);
        $("#div5").fadeToggle(100);
        $("#div6").fadeToggle(100);
        $("#div7").fadeToggle(100);
    });
};
/*点击div跳转*/
$(".space").click(function () {
    window.location = $(this).find("a").attr("href");
    return false;
});
$(".navbar-hidden").click(function () {
    window.location = $(this).find("a").attr("href");
    return false;
});
$(".notice").click(function () {
    window.location = $(this).find("a").attr("href");
    return false;
});
$(".foot-helo").click(function () {
    $.alert("请在跳转的B站专栏中查看…………")
});
$(".body-memory").click(function () {
    $.alert("即将开放，敬请期待（咕咕咕）")
});
$(".body-activity").click(function () {
    $.alert("暂无开放活动，请关注应援团公告")
});
$(".body-gallery").click(function () {
    $.alert("即将开放，敬请期待（咕咕咕）")
});

$(".foot-about").click(function () {
    $.alert("\t伊莎贝拉后援会应援激励(贝化值)系统\r\n<br/>" +
        "\t开发团队：伊莎贝拉后援会贝化系统中心\r\n<br/>" +
        "\t开发者：\r\n<br/>" +
        "\tYosoro\r\n<br/>" +
        "\t伊莎贝拉的弟弟\r\n<br/>" +
        "\t小坤坤\r\n<br/>" +
        "\t怎么样都可以\r\n<br/>" +
        "\t在风中摇曳\r\n<br/>")
});


/*点击提示*/
$(".idtype0").click(function () {
    $.alert("该账号处于功能受限状态，将无法进行任何贝化值的操作，但不影响贝化值的积累。\r\n<br/>这是因为该账号未通过【贡献值核验】，请检查以下条件是否满足：\r\n<br/>①在\"个人信息\"处【绑定】小程序战姬众号(UID)\r\n<br/>②小程序贝拉【总贡献值】1386以上\r\n<br/>③【绑定后的】7天内有在贝拉版块发帖\r\n<br/>如确实条件满足但没有解除受限的，请私聊群管理员解决！");
});
/*点击提示*/
$(".idtype1").click(function () {
    $.alert("这位蛋仔，账号一切正常，快快开始积累你的贝化值吧！");
});
/*点击提示*/
$(".idtype2").click(function () {
    $.alert("该账号可能出现以下情况：\r\n<br/>1、活动贝化值审核中\r\n<br/>2、数据勘误中\r\n<br/>3、其他问题，如有疑问请咨询群管理");
});
/*点击提示*/
$(".idtype3").click(function () {
    $.alert("兑换的奖品正在路上呢！以后会上线快递查询功能(咕咕咕)，敬请期待");
});
/*点击提示*/
$(".tips").click(function () {
    $.alert("这是来自官方小程序正在进行的活动信息哦！<br/>快去小程序康康吧");
});
/*点击提示*/
$(".body-wait").click(function () {
    $.alert("正在处理中······<br/>完成后将自动跳转，请等待<br/>请勿重复请求，偶内盖~");
});





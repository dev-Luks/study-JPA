const $oldPassword = $("input[name=oldPassword]");
const $newPassword = $("input[name=newPassword]");
const $newPasswordConfirm = $("input[name=newPasswordConfirm]");
const $changeBtn = $("#changeBtn");
const $passwordCheck = $("#passwordCheck");
const $header = $("#header");
const $footer = $("#footer");

var dbPassword="";
var message="";
/*현재 파일 경로 찾기*/
/*console.log(window.location.pathname);*/

$header.load("../fix/header.html");
$footer.load("../fix/footer.html");


/*변경하기 버튼 활성화*/
$("input").on("input", function(){
    $passwordCheck.text(""); // 비밀번호 불일치 텍스트 없애기
    if($newPassword.val() == $newPasswordConfirm.val() && $newPassword.val().length > 0 && $oldPassword.val().length > 0){
        $changeBtn.attr("class", "Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 foCOgK");
        $changeBtn.removeAttr("disabled");
    }else{
        $changeBtn.attr("class", "Button SubmitButton__RegisterPageSubmitButton chSrfn");
        $changeBtn.attr("disabled", "disabled");
    }
})


/*비밀번호 유효성 검사 text*/
$changeBtn.on("click", function(e){
    e.preventDefault();
    confrimPassword();
    // let pw = $newPassword.val();
    // let num = pw.search(/[0-9]/g);
    // let eng = pw.search(/[a-z]/ig);
    // let spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    //
    // /*현재 비밀번호 불일치*/
    // /*if($oldPassword.val() != DB 저장된 기존 비밀번호)*/
    // $passwordCheck.text("비밀번호가 일치하지 않습니다.");
    //
    // /*유효성 검사*/
    // if(pw.length < 10){
    //     $passwordCheck.text("비밀번호는 10자 이상 입력하셔야 합니다.");
    //     return;
    // }else if(pw.search(/\s/) != -1){
    //     $passwordCheck.text("비밀번호는 공백없이 입력해야 합니다.");
    //     return;
    // }else if( (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ){
    //     $passwordCheck.text("비밀번호는 영문, 숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요.");
    //     return;
    // }
    //
    // /*유효성 검사 통과*/
    // $passwordCheck.text("유효성 검사 통과");
    // if(message=="same"){
    //     console.log("ajax changepw");
    //     changePassword();
    // }
})

function confrimPassword(){
    $.ajax({
        type:"post",
        url:"confrimPassword",
        headers:{"Content-Type":"application/json"},
        data:$oldPassword.val(),
        dataType:"text",
        success:function(result){
            if(result=="different"){alert("비밀번호를 다시 입력해주세요.");}
            else{
                message="same";
                let pw = $newPassword.val();
                let num = pw.search(/[0-9]/g);
                let eng = pw.search(/[a-z]/ig);
                let spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

                /*현재 비밀번호 불일치*/
                /*if($oldPassword.val() != DB 저장된 기존 비밀번호)*/
                $passwordCheck.text("비밀번호가 일치하지 않습니다.");

                /*유효성 검사*/
                if(pw.length < 10){
                    $passwordCheck.text("비밀번호는 10자 이상 입력하셔야 합니다.");
                    return;
                }else if(pw.search(/\s/) != -1){
                    $passwordCheck.text("비밀번호는 공백없이 입력해야 합니다.");
                    return;
                }else if( (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ){
                    $passwordCheck.text("비밀번호는 영문, 숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요.");
                    return;
                }

                /*유효성 검사 통과*/
                $passwordCheck.text("유효성 검사 통과");
                if(message=="same"){
                    console.log("ajax changepw");
                    changePassword();
                    location.href="/myPage/index";
                }
            }
        },
        error:function(status,error){;}
    })
}


function changePassword(){
    $.ajax({
        type:"post",
        url:"changePassword",
        headers:{"Content-Type":"application/json"},
        data:$newPasswordConfirm.val(),
        success:function(){;},
        error:function(){;}
    })
}


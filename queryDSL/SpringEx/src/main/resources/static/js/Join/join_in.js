/* 아이디, 패스워드, 패스워드 확인 입력값 */
var $emailCheck=loginForm.email;
var $passwordCheck=loginForm.userPassword;
var $passwordConfrim=loginForm.passwordConfrim;
var emailRegex=/\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{3,4}$/;
var passwordRegex=/^(?=.*?[A-Z]{0})(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$/;

// /* 아이디 입력박스 위치에서 다른 곳 클릭시 */
$(".Form__Input-sc-1quypp7-1").on("click",function(e){
    $(".Form__Input-sc-1quypp7-1").css("border","");
    $(".Form__Input-sc-1quypp7-1").css("background-color","rgb(250,250,250)");
    $(this).css("border","1px solid blue");
    $(this).css("background-color","white");
});

/* 입력박스에서 다른 위치 클릭시 입력박스 스타일 변경 */
$(loginForm.email).on("blur",function(){
    $("input[name='email']").css("border","");
});
$(loginForm.userPassword).on("blur",function(){
    $("input[name='userPassword']").css("border","");
});

/* 아이디 입력박스 입력값이 입력될 때마다 검사 */
$(loginForm.email).on("change input",function(){
    if(!emailRegex.test($emailCheck.value)){
        $("input[name='email']").attr("class","Form__Input-sc-1quypp7-1 hYhAPw");
        $(".bViOzS").text("올바른 이메일 형식이 아닙니다.");
        $("button[type='submit']").attr("disabled",true);
        $("button[type='submit']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 chSrfn");
        if($emailCheck.value==""){
            $("p.bViOzS").text("");
            $("input[name='email']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
            $("input[name='email']").css("border","");
            $("button[type='submit']").attr("disabled",true);
            $("button[type='submit']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 chSrfn");
        }
    }
    else if(emailRegex.test($emailCheck.value)){
        $("p.bViOzS").text("");
        $("input[name='email']").attr("class", "Form__Input-sc-1quypp7-1 iRBMai");
        $("input[name='email']").css("border", "");
        checkemail();
    }
});

/* 비밀번호 입력박스 입력값이 입력될 때마다 검사 */
$(loginForm.userPassword).on("change input",function(){
    if(!passwordRegex.test($passwordCheck.value)){
        $("input[name='userPassword']").attr("class","Form__Input-sc-1quypp7-1 hYhAPw");
        $("p.cIOZzg").attr("class","Form__Description-sc-1quypp7-2 bViOzSSS");
        if($passwordCheck.value==""){
            $("p.bViOzSSS").attr("class","Form__Description-sc-1quypp7-2 cIOZzg");
            $("input[name='userPassword']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
            $("input[name='userPassword']").css("border","");
        }
    }
    else if(passwordRegex.test($passwordCheck.value)){
        $("p.bViOzSSS").attr("class","Form__Description-sc-1quypp7-2 cIOZzg");
        $("input[name='userPassword']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
        $("input[name='userPassword']").css("border","");
    }
});

/* 비밀번호 확인 입력박스 입력값이 입력될 때마다 검사 */
$(loginForm.passwordConfrim).on("change input",function(){
    if($passwordCheck.value!=$passwordConfrim.value){
        $("input[name='passwordConfrim']").attr("class","Form__Input-sc-1quypp7-1 hYhAPw");
        $(".bViOzSS").text("먼저 입력하신 비밀번호와 일치하지 않습니다.");
        $("button[type='submit']").attr("disabled",true);
        $("button[type='submit']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 chSrfn");
        if($passwordConfrim.value==""){
            $("p.bViOzSS").text("");
            $("input[name='passwordConfrim']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
            $("input[name='passwordConfrim']").css("border","");
        }
    }
    else{
        $("p.bViOzSS").text("");
        $("input[name='passwordConfrim']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
        $("input[name='passwordConfrim']").css("border","");

        $("button[type='submit']").attr("disabled",false);
        $("button[type='submit']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 foCOgK");
    }
    checkemail();
});
/*===================================================================================================================================*/
function checkemail(){
    $.ajax({
        url:"checkUserEmail",
        type:"post",
        headers:{"Content-Type":"application/json"},
        dataType:"text",
        data:$emailCheck.value,
        success:function(result){//available 넘어옴
            if(result=="unavailable") {
                $("input[name='email']").attr("class", "Form__Input-sc-1quypp7-1 hYhAPw");
                $("p.bViOzS").text("이미 가입된 이메일입니다.");
                $("button[type='submit']").attr("disabled",true);
                $("button[type='submit']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 chSrfn");
            }
            else{
                $("p.bViOzS").text("");
                if($passwordCheck.value==$passwordConfrim.value && !$passwordCheck && !$passwordConfrim) {
                    $("button[type='submit']").attr("disabled",false);
                    $("button[type='submit']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 foCOgK");
                }
            }
        },
        error:function(status,error){
            console.log(status,error);
        }
    });
}
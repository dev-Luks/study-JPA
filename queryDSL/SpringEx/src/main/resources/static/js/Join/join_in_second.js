/* 테스트를 위한 더미데이터 */
var nicknametest="aaa";
/* input태그의 입력값 속성을 읽기위해 선언 */
var $nicknameCheck=joinForm.userNickname;
var $emailCheck=joinForm.userEmail;
var $numberCheck=joinForm.verificationCode;
var $password=joinForm.userPassword;

/* 이메일 정규표현식 */
var emailRegex=/\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{3,4}$/;

/* 인증번호를 저장할 변수 선언 */
var saveNumber="";

/* 인증번호 6자리 생성을 위한 함수 선언(유효시간 함수 영역에서 사용) */
function randomNumber(){
    for(let i=0;i<6;i++){
        saveNumber+=parseInt(Math.random()*10);
    }
}
console.log($password.value);

/* input박스 클릭시 배경 및 테두리 색상 변경 */
$(".Form__Input-sc-1quypp7-1").on("click",function(){
    $(".Form__Input-sc-1quypp7-1").css("border","");
    $(".Form__Input-sc-1quypp7-1").css("background-color","rgb(250,250,250)");
    $(this).css("border","1px solid blue");
    $(this).css("background-color","white");
});

/* input박스에서 마우스가 다른 위치 클릭시 적용할 이벤트 */
$(joinForm.userNickname).on("blur",function(){
    $("input[name='userNickname']").css("border","");
    $(".Form__Input-sc-1quypp7-1").css("background-color","rgb(250,250,250)");
});
$(joinForm.userEmail).on("blur",function(){
    $("input[name='userEmail']").css("border","");
    $(".Form__Input-sc-1quypp7-1").css("background-color","rgb(250,250,250)");
});
$(joinForm.verificationCode).on("blur",function(){
    $("input[name='verificationCode']").css("border","");
    $(".Form__Input-sc-1quypp7-1").css("background-color","rgb(250,250,250)");
});

/* 닉네임 유효성 검사 */
$(joinForm.userNickname).on("change input",function(){
    checknickname();
});

/* 이메일 유효성 검사 */
$(joinForm.userEmail).on("change input",function(){
    if(!emailRegex.test($emailCheck.value)){
        $("input[name='userEmail']").attr("class","Form__Input-sc-1quypp7-1 hYhAPw");
        $(".bViOzSS").text("올바른 이메일 형식이 아닙니다.");
        $("button[name='sendemail']").attr("disabled",true);
        $("button[name='sendemail']").attr("class","Button-bqxlp0-0 gsRKCU");
        /* 이메일 입력값이 없을경우 */
        if($emailCheck.value==""){
            $("p.bViOzSS").text("");
            $("input[name='userEmail']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
            $("input[name='userEmail']").css("border","");
        }
    }
    /* 이메일 입력값이 이메일 정규표현식을 통과한 경우 */
    else if(emailRegex.test($emailCheck.value)){
        $("p.bViOzSS").text("");
        $("input[name='userEmail']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
        $("input[name='userEmail']").css("border","");

        $("button[name='sendemail']").attr("disabled",false);
        $("button[name='sendemail']").attr("class","Button-bqxlp0-0 jDtYZb");
        checkEmail();
    }
});

/* 인증번호 전송 버튼 클릭시 화면 위쪽에서 토글형식으로 인증번호 전송 메시지를 알려주기 위한 창 */
$(joinForm.sendemail).on("click",function(){
    $(".hKZUBk").text("인증번호가 전송되었습니다.");
    $(".hKZUBk").css("top","0px");
    /* 2초뒤에 메시지가 위로 올라감 */
    setTimeout(function(){
        $(".hKZUBk").css("top","-88px");
        $(".hKZUBk").text("인증번호가 전송되었습니다.");
    },2000);
});

/* 인증번호 입력값을 통한 인증번호 길이와 유효성 검사 */
$(joinForm.verificationCode).on("change input",function(){
    if(($numberCheck.value+"").length!=6){
        $("input[name='verificationCode']").attr("class","Form__Input-sc-1quypp7-1 hYhAPw");
        $(".bViOzSSS").css("color","rgb(222, 28, 34)");
        $(".bViOzSSS").text("올바른 인증번호 형식이 아닙니다.");
        $("button[name='verify']").attr("disabled",true);
        $("button[name='verify']").attr("class","Button-bqxlp0-0 jquNPr");
        /* 인증번호 입력값이 없을때 */
        if($numberCheck.value==""){
            $("p.bViOzSSS").text("");
            $("input[name='verificationCode']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
            $("input[name='verificationCode']").css("border","");
        }
    }
    /* 인증번호 입력값이 일치할때 */
    else{
        $("p.bViOzSSS").text("");
        $("input[name='verificationCode']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
        $("input[name='verificationCode']").css("border","");
        $("button[name='verify']").attr("disabled",false);
        $("button[name='verify']").attr("class","Button-bqxlp0-0 cEVDVY");

    }
});

var checkNumber=false;

/* 인증하기 버튼 클릭시  */
$("button[name='verify']").on("click",function(){
    if($numberCheck.value!=saveNumber){
        checkNumber=false;
        $(".bViOzSSS").text("인증번호를 다시 입력해주세요.");
    }
    /* 인증번호 일치시 */
    else{
        $(".bViOzSSS").css("color","blue");
        $(".bViOzSSS").text("인증에 성공하셨습니다.");
        checkNumber=true;
        if(checkNumber){
            /* 인증번호 전송,인증하기 버튼 비활성화 및 약관동의하기 버튼 활성화 */
            $("button[name='join']").attr("disabled",false);
            $("button[name='join']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 foCOgK");
            $("button[name='sendemail']").attr("disabled",true);
            $("button[name='sendemail']").attr("class","Button-bqxlp0-0 gsRKCU");
            $("button[name='verify']").attr("disabled",true);
            $("button[name='verify']").attr("class","Button-bqxlp0-0 jquNPr");

            /* 약관동의하기 버튼 클릭시 */
            $("button[name='join']").on("click",function(){

                /* 검은화면이 1.5초 지연되면서 서서히 나타남 */
                $(".bcRvxi").fadeIn(1500);
                /* 숨겨놨던 태그를 표시 */
                $(".bcRvxi").attr("style","display:flex");
                /* 아래로 위치시킨 약관동의 뱍스를 정위치에 위치시키기 위해 animate메서드 사용(1초의 지연시간을 가지면서 올라온다.) */
                $(".kvwOiO").animate({
                    bottom:"+=350px"
                },1000);
            });
            /* 버블링 전파를 막기 위한 메서드 선언(약관동의 박스) */
            $(".kvwOiO").on("click",function(e){
                e.stopPropagation();
            })
            /* 버블링 전파를 막고 모달창의 검은화면 클릭시 1.5초 지연시간을 가지면서 화면이 밝아지고, 표시된 태그를 숨기며, 약관동의 박스를
            아래쪽으로 숨기기 위한 설정 */
            $(".bcRvxi").on("click",function(e){
                e.stopPropagation();
                $(".bcRvxi").fadeOut(1500);
                $(".bcRvxi").attr("style","display:none");
                $(".kvwOiO").animate({
                    bottom:"-=350px"
                });
            });

            /* 약관동의 필수사항 및 선택사항 버튼을 클릭시 회원가입 페이지 부분의 약관동의하기 버튼을 가입하기로 텍스트 변경 및
            1.5초 지연시간을 가지면서 화면이 밝아지고, 표시된 태그를 숨기며, 약관동의 박스를 아래쪽으로 숨기기 위한 설정*/
            $(".eMDzoo").on("click",function(e){
                /* 유효성 검사 이후의 기준점 설정 */
                alreadycheck=true;

                /* 유효성 조건 통과시  */
                if(count>=3 && (checkmark1 && checkmark2 && checkmark3)){
                    /* 버튼의 기능을 일반 버튼에서 submit으로 변경하고 회원가입 양식의 이메일,비밀번호(이전 페이지에서 받아와야 되는데 아직 구현 안됨),닉네임 제출 */
                    $("button[name='confrimjoin']").attr("type","submit");
                    joinForm.submit();
                }
                /* 체크가 전부 해제된 상태에서 버튼 클릭시 */
                else if(!checkmark1 && !checkmark2 && !checkmark3 && !checkmark4){
                    $("button[name='confrimjoin']").attr("type","submit");
                    joinForm.submit();
                }
                else{
                    /* 기준점 변경후 체크가 되있다면 */
                    if(!checkmark1){
                        $("#g1").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.681000232696533)");
                        $("#g1").children().children().next().children().attr("fill","rgb(222,28,34)");
                        $("#g1").children().next().attr("style","display:none");
                        $("#btn1").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 hUktak");
                    }
                    if(!checkmark2){
                        $("#g2").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.681000232696533)");
                        $("#g2").children().children().next().children().attr("fill","rgb(222,28,34)");
                        $("#g2").children().next().attr("style","display:none");
                        $("#btn2").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 hUktak");
                    }
                    if(!checkmark3){
                        $("#g3").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.681000232696533)");
                        $("#g3").children().children().next().children().attr("fill","rgb(222,28,34)");
                        $("#g3").children().next().attr("style","display:none");
                        $("#btn3").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 hUktak");
                    }
                }
            });
        }
        else{
            $("button[name='join']").attr("disabled",true);
            $("button[name='join']").attr("class","Button-bqxlp0-0 SubmitButton__RegisterPageSubmitButton-np91gr-0 chSrfn");
        }
    }
});


/* 유효시간을 나타내는 함수(5분의 유효시간을 가지며, 시간 만료시 인증번호를 재설정하여 유효시간 이후의 입력을 방지) */
var timer = null;
var isRunning = false;

$("button[name='sendemail']").on("click", function() {
    var display = $(".jFLIap");
    // 유효시간 설정
    var leftSec = 300;
    saveNumber="";
    randomNumber();
    console.log(saveNumber);
    // 버튼 클릭 시 시간 연장
    if (isRunning){
        clearInterval(timer);
        display.html("");
        startTimer(leftSec, display);
    }else{
        startTimer(leftSec, display);
    }
});

function startTimer(count, display) {
    var minutes, seconds;
    timer = setInterval(function () {
        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        if(!checkNumber){
            display.html("남은 시간: "+minutes + "분 " + seconds+"초");
        }else{
            display.html("");
        }
        // 타이머 끝
        if (--count < 0) {
            clearInterval(timer);
            $("button[name='verify']").attr("disabled", true);
            saveNumber="";
            randomNumber();
            isRunning = false;
            return;
        }

    }, 1000);
    isRunning = true;
}

/* 약관동의 버튼 클릭시 확인을 위한 불린타입 변수 선언 및 카운트 변수 선언(버튼 클릭된 횟수 및 버튼 위치를 파악하여 약관동의 버튼 활성화시 사용) */
var checkmark1=false;
var checkmark2=false;
var checkmark3=false;
var checkmark4=false;
var count=0;

/* 기준점 변경 여부의 변수 선언 */
var alreadycheck=false;


/* 버튼의 초기 텍스트 변경 */
$(".eMDzoo").html("모두 동의하고 가입하기");

/* 약관동의 첫번째 버튼 클릭시 */
$("#btn1").on("click",function(){
    /* 체크버튼 누를때 */
    if(!checkmark1){
        ++count;
        $("#g1").children().next().attr("style","display:block");
        $("#g1").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.532697677612305)");
        $("#g1").children().next().children().children().attr("d","M-7.875,-1.637773036956787 C-7.875,-1.637773036956787 -2.6144704818725586,3.9723966121673584 -2.6144704818725586,3.9723966121673584 C-2.6144704818725586,3.9723966121673584 8.023958206176758,-5.832714557647705 8.023958206176758,-5.832714557647705");
        $("#btn1").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 gWNsJT");
        checkmark1=true;
    }else{
        /* 기준점 변경 이후의 체크버튼 해재시 */
        if(alreadycheck){
            $("#g1").children().children().next().children().attr("fill","rgb(222,28,34)");
            $("#btn1").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 hUktak");
        }
        /* 기준점 변경 이전의 체크버튼 해재시 */
        else{
            $("#g1").children().children().next().children().attr("fill","rgb(222,221,221)");
        }

        /* 유효성 검증 검사 전후의 공통적으로 적용될 스타일 */
        --count;
        $("#g1").children().next().attr("style","display:none");
        $("#g1").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.681000232696533)");
        $("#g1").children().next().children().children().attr("d"," M-2.25,5.681000232696533 C-2.25,5.681000232696533 -8.178000450134277,0.20800000429153442 -8.178000450134277,0.20800000429153442 C-8.583999633789062,-0.16599999368190765 -8.609000205993652,-0.7990000247955322 -8.234999656677246,-1.2050000429153442 C-7.860000133514404,-1.6089999675750732 -7.228000164031982,-1.6349999904632568 -6.822000026702881,-1.2610000371932983 C-6.822000026702881,-1.2610000371932983 -2.25,2.9579999446868896 -2.25,2.9579999446868896 C-2.25,2.9579999446868896 6.821000099182129,-5.414999961853027 6.821000099182129,-5.414999961853027 C7.2270002365112305,-5.789000034332275 7.861000061035156,-5.763999938964844 8.234000205993652,-5.359000205993652 C8.609000205993652,-4.953000068664551 8.583999633789062,-4.320000171661377 8.178999900817871,-3.944999933242798 C8.178999900817871,-3.944999933242798 -2.25,5.681000232696533 -2.25,5.681000232696533z");
        checkmark1=false;
    }
    /* 버튼 클릭으로 조건에 따른 유효성 검사 버튼의 텍스트 변경 */
    if(count>0){
        $(".eMDzoo").html("동의하고 가입하기");
    }else{
        $(".eMDzoo").html("모두 동의하고 가입하기");
    }
});

/* 약관동의 두번째 버튼 클릭시 */
$("#btn2").on("click",function(){
    /* 체크버튼 누를 때 */
    if(!checkmark2){
        ++count;
        $("#g2").children().next().attr("style","display:block");
        $("#g2").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.532697677612305)");
        $("#g2").children().next().children().children().attr("d","M-7.875,-1.637773036956787 C-7.875,-1.637773036956787 -2.6144704818725586,3.9723966121673584 -2.6144704818725586,3.9723966121673584 C-2.6144704818725586,3.9723966121673584 8.023958206176758,-5.832714557647705 8.023958206176758,-5.832714557647705");
        $("#btn2").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 gWNsJT");
        checkmark2=true;
    }else{
        /* 기준점 변경 이후의 체크버튼 해재시 */
        if(alreadycheck){
            $("#g2").children().children().next().children().attr("fill","rgb(222,28,34)");
            $("#btn2").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 hUktak");
        }
        /* 기준점 변경 이전의 체크버튼 해재시 */
        else{
            $("#g2").children().children().next().children().attr("fill","rgb(222,221,221)");
        }
        /* 유효성 검증 검사 전후의 공통적으로 적용될 스타일 */
        --count;
        $("#g2").children().next().attr("style","display:none");
        $("#g2").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.681000232696533)");
        $("#g2").children().next().children().children().attr("d","M-7.875,-1.5011694431304932 C-7.875,-1.5011694431304932 -7.611988544464111,-1.2014505863189697 -7.611988544464111,-1.2014505863189697 C-7.611988544464111,-1.2014505863189697 -7.472008228302002,-1.0000996589660645 -7.472008228302002,-1.0000996589660645");
        checkmark2=false;
    }
    /* 버튼 클릭으로 조건에 따른 유효성 검사 버튼의 텍스트 변경 */
    if(count>0){
        $(".eMDzoo").html("동의하고 가입하기");
    }else{
        $(".eMDzoo").html("모두 동의하고 가입하기");
    }
});

/* 약관동의 세번째 버튼 클릭시 */
$("#btn3").on("click",function(){
    /* 체크버튼 누를 때 */
    if(!checkmark3){
        ++count;
        $("#g3").children().next().attr("style","display:block");
        $("#g3").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.532697677612305)");
        $("#g3").children().next().children().children().attr("d","M-7.875,-1.637773036956787 C-7.875,-1.637773036956787 -2.6144704818725586,3.9723966121673584 -2.6144704818725586,3.9723966121673584 C-2.6144704818725586,3.9723966121673584 8.023958206176758,-5.832714557647705 8.023958206176758,-5.832714557647705");
        $("#btn3").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 gWNsJT");
        checkmark3=true;
    }else{
        /* 기준점 변경 이후의 체크버튼 해재시 */
        if(alreadycheck){
            $("#g3").children().children().next().children().attr("fill","rgb(222,28,34)");
            $("#btn3").children().next().attr("class","ItemTerms__Title-sc-1nxa079-3 hUktak");
        }
        /* 기준점 변경 이전의 체크버튼 해재시 */
        else{
            $("#g3").children().children().next().children().attr("fill","rgb(222,221,221)");
        }
        /* 유효성 검증 검사 전후의 공통적으로 적용될 스타일 */
        --count;
        $("#g3").children().next().attr("style","display:none");
        $("#g3").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.681000232696533)");
        $("#g3").children().next().children().children().attr("d","M-7.875,-1.5011694431304932 C-7.875,-1.5011694431304932 -7.611988544464111,-1.2014505863189697 -7.611988544464111,-1.2014505863189697 C-7.611988544464111,-1.2014505863189697 -7.472008228302002,-1.0000996589660645 -7.472008228302002,-1.0000996589660645");
        checkmark3=false;
    }
    /* 버튼 클릭으로 조건에 따른 유효성 검사 버튼의 텍스트 변경 */
    if(count>0){
        $(".eMDzoo").html("동의하고 가입하기");
    }else{
        $(".eMDzoo").html("모두 동의하고 가입하기");
    }
});

/* 약관동의 네번째 버튼 클릭시(선택사항 박스이므로 기준점 변경이 없다.) */
$("#btn4").on("click",function(){
    /* 체크버튼 누를 때 */
    if(!checkmark4){
        ++count;
        $("#g4").children().next().attr("style","display:block");
        $("#g4").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.532697677612305)");
        $("#g4").children().next().children().children().attr("d","M-7.875,-1.637773036956787 C-7.875,-1.637773036956787 -2.6144704818725586,3.9723966121673584 -2.6144704818725586,3.9723966121673584 C-2.6144704818725586,3.9723966121673584 8.023958206176758,-5.832714557647705 8.023958206176758,-5.832714557647705");
        checkmark4=true;
    }else{
        /* 체크버튼 해제 시 */
        --count;
        $("#g4").children().next().attr("style","display:none");
        $("#g4").children().children().next().attr("transform","matrix(1,0,0,1,8.5,5.681000232696533)");
        $("#g4").children().next().children().children().attr("d","M-7.875,-1.5011694431304932 C-7.875,-1.5011694431304932 -7.611988544464111,-1.2014505863189697 -7.611988544464111,-1.2014505863189697 C-7.611988544464111,-1.2014505863189697 -7.472008228302002,-1.0000996589660645 -7.472008228302002,-1.0000996589660645");
        checkmark4=false;
    }
    /* 버튼 클릭으로 조건에 따른 유효성 검사 버튼의 텍스트 변경 */
    if(count>0){
        $(".eMDzoo").html("동의하고 가입하기");
    }else{
        $(".eMDzoo").html("모두 동의하고 가입하기");
    }
});

function checknickname(){
    $.ajax({
        url:"checkUserNickname",
        type:"post",
        headers:{"Content-Type":"application/json"},
        dataType:"text",
        data:$nicknameCheck.value,
        success:function(result){
            console.log(result);
            if(result=="unavailable"){
                $("input[name='userNickname']").attr("class","Form__Input-sc-1quypp7-1 hYhAPw");
                $(".bViOzS").text("이미 사용중인 닉네임입니다.");
            }else{
                $("p.bViOzS").text("");
                $("input[name='userNickname']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
                $("input[name='userNickname']").css("border","");
            }
        },
        error:function(status,error){
            console.log("error");
            console.log(status,error);
        }

    })
}

function checkEmail(){
    $.ajax({
        url:"checkUserEmail",
        type:"post",
        headers:{"Content-Type":"application/json"},
        data:$emailCheck.value,
        dataType:"text",
        success:function(result){
            console.log("result:"+result);
            if(result=="unavailable"){
                console.log("unavailable in");
                $("input[name='userEmail']").attr("class","Form__Input-sc-1quypp7-1 hYhAPw");
                $(".bViOzSS").text("이미 가입된 이메일입니다.");
                $("button[name='sendemail']").attr("disabled",true);
                $("button[name='sendemail']").attr("class","Button-bqxlp0-0 gsRKCU");
            }else{
                console.log("available in");
                $("p.bViOzSS").text("");
                $("input[name='userEmail']").attr("class","Form__Input-sc-1quypp7-1 iRBMai");
                $("input[name='userEmail']").css("border","");

                $("button[name='sendemail']").attr("disabled",false);
                $("button[name='sendemail']").attr("class","Button-bqxlp0-0 jDtYZb");
            }
        },
        error:function(status,error){
            console.log(status,error);
        }
    })
}

/* {{from_name}}
{{to_name}}
{{reply_to}}
{{reply_from}} */
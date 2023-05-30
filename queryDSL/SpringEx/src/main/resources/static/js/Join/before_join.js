/* 이미지 전환 딜레이 3초 */
var count=0;
const $windowWidths = $(window).width();

checkWidths();


if(matchMedia("screen and (max-width: 769px)").matches){
    $(".egtVMY").attr("style","display:flex");
}
else{
    $(".egtVMY").attr("style","display:none");
}

function checkWidths(){
    if($windowWidths<769){
        $(".egtVMY").attr("style","display:flex");
    }
    else{
        $(".egtVMY").attr("style","display:none");
    }
}

$(window).on("resize",function(){
    if (window.innerWidth < 769) {
        $(".egtVMY").attr("style","display:flex");
    }
    else{
        $(".egtVMY").attr("style","display:none");
    }
}).trigger("resize");

// $(window).resize(function() {
//     if (window.innerWidth < 769) {
//         $(".egtVMY").attr("style","display:flex");
//     }
//     else{
//         $(".egtVMY").attr("style","display:none");
//     }
// });

setInterval(function(){
    switch(count){
        case 0:
            $(".hoYWgp").next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 fMxjfF");
            $(".hoYWgp").next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 hpebkH");
            break;
        case 1:
            $(".hoYWgp").next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 fMxjfF");
            $(".hoYWgp").next().next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 hpebkH");
            break;
        case 2:
            $(".hoYWgp").next().next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 fMxjfF");
            $(".hoYWgp").next().next().next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 hpebkH");
            break;
        case 3:
            $(".hoYWgp").next().next().next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 fMxjfF");
            $(".hoYWgp").next().next().next().next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 hpebkH");
            break;
        case 4:
            $(".hoYWgp").next().next().next().next().next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 fMxjfF");
            $(".hoYWgp").next().attr("class","Banner__BackgroundImage-sc-13uuyx6-2 hpebkH");
            break;
    }
    ++count;
    count%=5;
    console.log("count:"+count);
},3000);

Kakao.init('12dc5d253c65c337962b81a07303c803');

/*로그인, 토큰값 저장*/
function kakaoLogin() {
    Kakao.Auth.login({
        success: function (authObj){
            console.log(authObj); //access 토큰 값
            Kakao.Auth.setAccessToken(authObj.access_token); //access 토큰 값 저장

            getInfo();
        },
        fail: function(err){
            console.log(err);
        }
    })
}

function getInfo(){
    Kakao.API.request({
        url: '/v2/user/me',
        success: function(res){
            console.log(res);
            /*이메일 정보*/
            $.ajax({
                data: {
                    id: res.id,
                    userName: res.kakao_account.profile.nickname
                },
                success: function(){
                    $("input[name='kakaoLoginForm']").val(res.id);
                    $("input[name='kakaoNameForm']").val(res.kakao_account.profile.nickname);
                    frm_login_kakao.submit();
                }
            })
        },
        fail: function (error){
            alert('카카오 로그인에 실패했습니다. 다시 시도해주세요.' + JSON.stringify(error));
            return;
        }
    })
}

/*구글 로그인 서비스 */
function handleCredentialResponse(response) {
    const responsePayload = parseJwt(response.credential);
    console.log("ID: " + responsePayload.sub);
    console.log('Full Name: ' + responsePayload.name);
    console.log('Given Name: ' + responsePayload.given_name);
    console.log('Family Name: ' + responsePayload.family_name);
    console.log("Image URL: " + responsePayload.picture);
    console.log("Email: " + responsePayload.email);
    $.ajax({
        type: "post",
        data: {
            id: responsePayload.sub,
            userName: responsePayload.name
        },
        success: function(){
            $("input[name='googleLoginForm']").val(responsePayload.sub);
            $("input[name='googleNameForm']").val(responsePayload.name);
            frm_login_google.submit();
        }
    })
}

function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};

window.onload = function () {
    google.accounts.id.initialize({
        client_id: "609009193609-b797q62q035jhkamfo8ebei82sr5612j.apps.googleusercontent.com", //직접 클라이언트 ID를 받아서 진행해야함
        callback: handleCredentialResponse
    });
    google.accounts.id.renderButton(
        document.getElementById("buttonDiv"),
        { theme: "outline", size: "large", width: 368}  // 로고 커스터마이징
    );
    google.accounts.id.prompt(); // 원탭 화면으로 출력
}
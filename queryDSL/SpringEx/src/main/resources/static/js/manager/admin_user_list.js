// html dom 이 다 로딩된 후 실행된다.
$(document).ready(function () {
    // menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
    $("#side_menubar>li").click(function () {
        var submenu = $(this).children("ul");


        // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
        if (submenu.is(":visible")) {
            submenu.slideUp();
        } else if (!submenu) {
        } else {
            submenu.slideDown();

        }
    });
});


var Target = document.getElementById("clock");

function clock() {
    var time = new Date();

    var month = time.getMonth();
    var date = time.getDate();
    var day = time.getDay();
    var week = ['일', '월', '화', '수', '목', '금', '토'];

    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();

    Target.innerText =
        `${month + 1}월 ${date}일 ${week[day]}요일 ` +
        `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds}`;

}

clock();
setInterval(clock, 1000); // 1초마다


// let $accountModalBtn =$(".account-modal");
let temp = 0;
let $tr = $("#info_table").find('tr');
let accountCondition = null;

// 관리 버튼을 클릭했을때
function updateStatus(user){
    userId =$(user).closest('tr').children('.userId').text();
    if(temp == 0){
        temp++;
        //    상태의 html을 보고 계정 정지/계정 복구가 결정됨
        //    만약 상태가 정지라면
        if ($(user).parent().prev().attr('class') === 'info_td banned') {
            //모달창은 계정 복구로 나옴
            $(user).parent().append("<div class=\"account-modal\">\n" +
                "    <button class=\"account-modal-button\">계정 복구</button>\n" +
                "</div>")

            $(user).next().find('.account-modal-button').click(function () {
                userAccountStatus = 'ACTIVE';
                update({
                    userAccountStatus : userAccountStatus,
                    userId : userId
                },function (){status === 'null' ? showAll() : show();})
                $(user).next().remove();
                temp--;
            })
        } else{
            $(user).parent().append("<div class=\"account-modal\">\n" +
                "    <button class=\"account-modal-button\">계정 정지</button>\n" +
                "</div>")
            $(user).next().find('.account-modal-button').click(function () {
                userAccountStatus = 'BANNED';
                update({
                    userAccountStatus : userAccountStatus,
                    userId : userId
                },function (){
                 (status === 'null') ? showAll() : show();
                })
                $(user).next().remove();
                temp--;
            })
        }

    }
    else{
        $(user).find('.account-modal-button').remove();
        $(user).next().remove()
        temp--;
    }
}


function getListByKeyword(param, callback, error) {
    $.ajax({
        url: encodeURI("/user/" + param.status + "/" + (param.page || 0) + "/" + param.keyword),
        type: "get",
        async : false,
        success: function (userDTO, status, xhr) {
            if (callback) {
                callback(userDTO);
            }
        },
        error: function (xhr, status, err) {
            if (error) {
                error(err);
            }
        }
    });
}
function getList(param, callback, error){
    $.ajax({
        url: "/user/" + (param.page || 0) + "/" + param.keyword,
        type: "get",
        async : false,
        success: function(userDTO, status, xhr){
            console.log(userDTO.userList);
            if(callback){
                callback(userDTO);
            }
        },
        error: function(xhr, status, err){
            if(error){
                error(err);
            }
        }
    });
}

function update(user, callback, error){
    $.ajax({
        url: "/user/" + user.userAccountStatus + "/" + user.userId,
        type: "patch",
        contentType: "application/json; charset=utf-8",
        async:false,
        data: JSON.stringify(user),
        success: function(text){
            if(callback){
                callback(text);
            }
        },
        error: function(xhr, status, err){
            if(error){
                error(err);
            }
        }
    });
}




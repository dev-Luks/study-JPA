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

$tr.find('.more').click(function () {
    if (temp == 1) {
        temp--;
        $(this).parent().children('.account-modal').remove();
    } else {
        temp++;
        $(this).parent().append("<div class=\"account-modal\">\n" +
            "    <button class=\"account-modal-button\">삭제</button>\n" +
            "</div>")
        $(this).next().find('.account-modal-button').click(function () {
            $(this).closest('.account-modal').remove()
            $(this).remove()
            temp--;
        })
    }
})



// 상단 필터 클릭시 색 변화
// 하나 클릭하면 나머지는 색 없어짐
$('.filter').children('span').click(function () {
    $(this).siblings().removeClass('active-filter')
    $(this).addClass('active-filter');
});

let dashBoardService = (function(){
    function getUserList(callback, error){
        $.ajax({
            url: "/dashBoard/user",
            type: "get",
            success: function(userDTO, status, xhr){
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
    function getAskList(callback, error){
        $.ajax({
            url: "/dashBoard/ask",
            type: "get",
            async : false,
            success: function(AskDTO, status, xhr){
                if(callback){
                    callback(AskDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }
    function getReplyList(callback, error){
        $.ajax({
            url: "/dashBoard/reply",
            type: "get",
            async : false,
            success: function(replyDTO, status, xhr){
                if(callback){
                    callback(replyDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }
    function getPostList(callback, error){
        $.ajax({
            url: "/dashBoard/post",
            type: "get",
            async : false,
            success: function(postDTO, status, xhr){
                if(callback){
                    callback(postDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    return {getUserList: getUserList, getAskList : getAskList, getReplyList : getReplyList, getPostList : getPostList }
}) ();
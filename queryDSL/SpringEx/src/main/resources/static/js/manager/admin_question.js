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




$('.filter').children('span').click(function(){
    $(this).siblings().removeClass('active-filter')
    $(this).addClass('active-filter');

    if($(this).attr('class') == 'waiting'){
        status = 'waiting';
    }else {
        status = 'null'
    }
    page = 1;
    (status === 'null') ? showAll() : show();

})

let askService = (function(){
    function getList(param, callback, error){
        $.ajax({
            url: encodeURI("/ask/" + (param.page || 0) + "/" + param.keyword),
            type: "get",
            async : false,
            success: function(askDTO, status, xhr){
                if(callback){
                    callback(askDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }
    function getStatusList(param, callback, error){
        $.ajax({
            url: encodeURI("/ask/"+ param.status + "/" + (param.page || 0) + "/" + param.keyword),
            type: "get",
            async : false,
            success: function(askDTO, status, xhr){
                if(callback){
                    callback(askDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }
    return {getList: getList, getStatusList : getStatusList}
}) ();
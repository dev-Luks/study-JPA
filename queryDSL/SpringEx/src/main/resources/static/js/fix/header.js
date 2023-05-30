const $nav = $("nav.ciqvqE");
const $section = $("section.lkrzmV");
const $windowWidth = $(window).width();

checkWidth();
checking(check);

function checkWidth(){
    if($windowWidth<769){
        $("GlobalNavigationBar__Wrapper-og74wb-0").attr("class", "GlobalNavigationBar__Wrapper-og74wb-0 bHgsOZ");
        $(".fqlSHx").hide(); //카테고리 가리기
        $(".cRoOrU").hide(); //카테고리 옆 선 가리기
        $(".kiAeGP").hide(); //피드/저장/마이 가리기
        $(".ierGsl").hide(); //알람 상태 메세지 가리기
        $nav.css("display", "flex");
        $nav.show();
        //$section.hide();
        $section.css("display", "none");
    } else{
        $("GlobalNavigationBar__Wrapper-og74wb-0").attr("class", "GlobalNavigationBar__Wrapper-og74wb-0 dPJHWR");
        $(".fqlSHx").show(); //카테고리
        $(".cRoOrU").show(); //카테고리 옆 선 가리기
        $(".kiAeGP").show(); //피드/저장/마이
        $(".ierGsl").show(); //알람 상태 메세지 나타나기
        $(".lQqkn").show();
        $nav.hide();
        $section.show();
    }
}

$(window).resize(function() {
    if (window.innerWidth < 769) {
        $("GlobalNavigationBar__Wrapper-og74wb-0").attr("class", "GlobalNavigationBar__Wrapper-og74wb-0 bHgsOZ");
        $(".fqlSHx").hide(); //카테고리 가리기
        $(".cRoOrU").hide(); //카테고리 옆 선 가리기
        $(".kiAeGP").hide(); //피드/저장/마이 가리기
        $(".ierGsl").hide(); //알람 상태 메세지 가리기
        $("#serachshowone").hide(); //카테고리를 켜 놓은 상태였다면 카테고리 끄기
        $nav.css("display", "flex");
        $nav.show();
        //$section.hide();
        $section.css("display", "none");
    } else{
        $("GlobalNavigationBar__Wrapper-og74wb-0").attr("class", "GlobalNavigationBar__Wrapper-og74wb-0 dPJHWR");
        $(".fqlSHx").show(); //카테고리
        $(".cRoOrU").show(); //카테고리 옆 선 가리기
        $(".kiAeGP").show(); //피드/저장/마이
        $(".ierGsl").show(); //알람 상태 메세지 나타나기
        $(".lQqkn").show();
        $nav.hide();
        $section.show();
    }
    checking(check);
});

/* 카테고리 숨김 / 보이기 버튼  */
function toggleBtn1() {
    // 토글 할 버튼 선택 (serachshowone)
    const btn1 = document.getElementById('serachshowone');
    // serachshowone 숨기기 (display: none)
    if(serachshowone.style.display !== 'none') {
        serachshowone.style.display = 'none';
    }
    // serachshowone 보이기 (display: block)
    else {
        serachshowone.style.display = 'block';
    }
}

function check(result){
    console.log("ddddddd");
    console.log( result);
    console.log( result === true);
    result === true ? $(".ierGsl").hide() : $(".ierGsl").show();
}




function checking(callback, error) {
    $.ajax({
        url: "/alarm/check",
        type: "patch",
        async: false,
        success: function (result) {
            if (callback) {
                callback(result);
            }
        },
        error: function (xhr, status, err) {
            if (error) {
                error(err);
            }
        }
    });
}
//
// document.addEventListener('keydown', function(event) {
//     if (event.keyCode === 13) {
//         event.preventDefault();
//     };
// }, true);


$("input.KfNMI").on("keyup",function(key){
    if(key.keyCode==13) {
        if(!$(this).val()){
            alert("검색어를 입력해주세요");
        }else{
            $("form.dLgpzE").submit();
        }
    }

});

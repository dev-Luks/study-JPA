
const $filterBtn = $(".childFilter");
const $allBtn = $(".allFilter");

const $cook = $(".cookFilter");
const $exercise = $(".exerciseFilter")
const $movie = $(".movieFilter")
const $book = $(".bookFilter")
const $diary = $(".diaryFilter")

globalThis.communityAr = new Array();

let communities = ['EXERCISE', 'COOK', 'MOVIE', 'BOOK', 'COUNSELING'];


//요리
$cook.on("click",function () {
    if($(this).find("button").hasClass("fFBpBV")){
        $(this).find("button").removeClass('fFBpBV').addClass("kJEnf");
        $(this).find(".gRNDCb").show();
        globalThis.communityAr.push("COOK");
    }else{
        $(this).find("button").removeClass('kJEnf').addClass("fFBpBV");
        $(this).find(".gRNDCb").hide()
        globalThis.communityAr = globalThis.communityAr.filter((element) => element !=="COOK");
    }

    if($filterBtn.find(".kJEnf").length==10){
        allCheck();
    }else{
        allCheckCancel();
    }
})

//영화
$movie.on("click",function () {
    if($(this).find("button").hasClass("fFBpBV")){
        $(this).find("button").removeClass('fFBpBV').addClass("kJEnf");
        $(this).find(".gRNDCb").show();
        globalThis.communityAr.push("MOVIE");
    }else{
        $(this).find("button").removeClass('kJEnf').addClass("fFBpBV");
        $(this).find(".gRNDCb").hide();
        globalThis.communityAr = globalThis.communityAr.filter((element) => element !=="MOVIE");
    }
    if($filterBtn.find(".kJEnf").length==10){
        allCheck();
    }else{
        allCheckCancel();
    }
})

//운동
$exercise.on("click",function () {
    if($(this).find("button").hasClass("fFBpBV")){
        $(this).find("button").removeClass('fFBpBV').addClass("kJEnf");
        $(this).find(".gRNDCb").show()
        globalThis.communityAr.push("EXERCISE");
    }else{
        $(this).find("button").removeClass('kJEnf').addClass("fFBpBV");
        $(this).find(".gRNDCb").hide()
        globalThis.communityAr = globalThis.communityAr.filter((element) => element !=="EXERCISE");
    }
    if($filterBtn.find(".kJEnf").length==10){
        allCheck();
    }else{
        allCheckCancel();
    }
})

//책
$book.on("click",function () {
    if($(this).find("button").hasClass("fFBpBV")){
        $(this).find("button").removeClass('fFBpBV').addClass("kJEnf");
        $(this).find(".gRNDCb").show();
        globalThis.communityAr.push("BOOK");
    }else{
        $(this).find("button").removeClass('kJEnf').addClass("fFBpBV");
        $(this).find(".gRNDCb").hide();
        globalThis.communityAr = globalThis.communityAr.filter((element) => element !=="BOOK");
    }
    if($filterBtn.find(".kJEnf").length==10){
        allCheck();
    }else{
        allCheckCancel();
    }
})
//다이어리 => 이걸 고민상담으로 바꿈
$diary.on("click",function () {
    if($(this).find("button").hasClass("fFBpBV")){
        $(this).find("button").removeClass('fFBpBV').addClass("kJEnf");
        $(this).find(".gRNDCb").show();
        globalThis.communityAr.push("COUNSELING");
    }else{
        $(this).find("button").removeClass('kJEnf').addClass("fFBpBV");
        $(this).find(".gRNDCb").hide();
        globalThis.communityAr = globalThis.communityAr.filter((element) => element !=="COUNSELING");
    }
    if($filterBtn.find(".kJEnf").length===10){
        allCheck();

    }else{
        allCheckCancel();
    }
})

function allCheck(){
    $allBtn.find("button").removeClass('fFBpBV').addClass("kJEnf");
    $allBtn.find(".gRNDCb").show();
    globalThis.communityAr = ['EXERCISE', 'COOK', 'MOVIE', 'BOOK', 'COUNSELING'];
}

function allCheckCancel(){
    $allBtn.find("button").removeClass('kJEnf').addClass("fFBpBV");
    $allBtn.find(".gRNDCb").hide();
}



$allBtn.on("click",function () {

  if($(this).find("button").hasClass("fFBpBV")){
      allCheck();
      $filterBtn.each((i,item)=>{
          $(item).find("button").removeClass('fFBpBV').addClass("kJEnf");
          $(item).find(".gRNDCb").show();
      })
  }else{
      allCheckCancel();
      $filterBtn.each((i,item)=>{
          $(item).find("button").removeClass('kJEnf').addClass("fFBpBV");
          $(item).find(".gRNDCb").hide();
      })
      globalThis.communityAr = [];
  }
})

//유의사항 클릭 시 나오는 것
$(document).on("click", ".bremOa", function(){
    console.log("asdfasdf");
    $(".Accordion__Content-sc-1jd6vdl-3.zRWUs").attr("class", "Accordion__Content-sc-1jd6vdl-3 bJFgwb");
})



//모달
const $modal = $("#modal-root");
const $loginModal = $("#modal-root-login");
const $modalCancelBtn = $(".kXWQPc");
$(".lbwitP").click(function () {
    $("#modal-root").css("display","block");
})

$modalCancelBtn.click(function () {
    $modal.css("display","none");
    $loginModal.css("display", "none");
})

function modalOpen(){
    $("#modal-root").css("display","block");
    modalCheck = false;
}

function loginModalOpen(){
    $("#modal-root-login").css("display", "block");

}


//페이지 소개 모달

const $pageIntroduceModal = $("#modal-page");
const $pageIntroduceBtn = $(".cUtFFV");

$pageIntroduceBtn.click(function () {
    $pageIntroduceModal.css("display","block")
})


$(".pageIntroduce_modalContainer_1h").click(function (e) {
    if($(e.target).parents(".pageIntroduce_Container_1h").length < 1){
        $pageIntroduceModal.css("display","none");
    }
})


// 반응형
// $(document).ready(function(){
//     checkMedia();
// })

$(window).resize(function(){
    if(window.innerWidth<785){
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 media");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 categoryMedia");
        $("a.down785px").show();
        $("a.until785px").hide();
        if(window.innerWidth<594){
            $(".filter_wrapper_3l").css("content","center");
            $(".jIjcTX").css("margin", "");
            $(".categoryMedia >div").css("justify-content", "start");
        } else{
            $(".filter_wrapper_3l").css("content","start");
            $(".jIjcTX").css("margin", "auto");
            $(".categoryMedia >div").css("justify-content", "center");
        }
    } else{
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 ciajVR");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 gRnEmw");
        $(".jIjcTX").css("margin", "");
        $("a.down785px").hide();
        $("a.until785px").show();
        $(".categoryMedia >div").css("justify-content", "start");
    }
}).resize();

function checkMedia(){
    if(window.innerWidth<785){
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 media");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 categoryMedia");
        $("a.down785px").show();
        $("a.until785px").hide();
        if(window.innerWidth<594){
            $(".filter_wrapper_3l").css("content","center");
            $(".jIjcTX").css("margin", "");
            $(".categoryMedia >div").css("justify-content", "start");
        } else{
            $(".filter_wrapper_3l").css("content","start");
            $(".jIjcTX").css("margin", "auto");
            $(".categoryMedia >div").css("justify-content", "center");
        }
    } else{
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 ciajVR");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 gRnEmw");
        $(".jIjcTX").css("margin", "");
        $("a.down785px").hide();
        $("a.until785px").show();
        $(".categoryMedia >div").css("justify-content", "start");
    }
    console.log("checkMedia 들어옴");
}


let communityLikeCheck = false;

let communityService = (function() {
    function getList(param, callback, error) {
        $.ajax({
            url: "/community/list/",
            type: "get",
            success: function (communityPostDTO, status, xhr) {
                console.log("getList 들어옴");
                if (callback) {
                    callback(communityPostDTO);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }
    function getListNotUser(param, callback, error) {
        $.ajax({
            url: "/community/list-not-user/",
            type: "get",
            success: function (communityPostDTO, status, xhr) {
                if (callback) {
                    callback(communityPostDTO);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    function infiniteScroll(param, callback, error){
        $.ajax({
            url: "/community/scroll/" + param.page + "/" + param.keyword,
            type: "get",
            success: function(boards, status, xhr){
                if(callback){
                    callback(boards);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function infiniteScrollUser(param, callback, error){
        $.ajax({
            url: "/community/scroll-user/" + param.page + (("/" + param.keyword) || ""),
            type: "get",
            success: function(boards, status, xhr){
                if(callback){
                    callback(boards);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }


    function addLike(communityPostId, callback, error){
        $.ajax({
            url: "/like/addLike/"  + communityPostId,
            type: "post",
            success: function(result, status, xhr){
                if(callback){
                    callback(result);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function minusLike(communityPostId, callback, error){
        $.ajax({
            url: "/like/minusLike/"  + communityPostId,
            type: "post",
            success: function(result, status, xhr){
                if(callback){
                    callback(result);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function checkLike(communityPostId, callback, error){
        $.ajax({
            url: "/like/checkLike/" + communityPostId,
            type: "post",
            success: function(result, status, xhr){
                if(callback){
                    callback(result);
                }
                communityLikeCheck = result;
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function filterCheck(communityCategories, callback, error){
        $.ajax({
            url: "/community/list-filter/" + globalThis.communityAr,
            type: "get",
            success: function(communityCategories, status, xhr){
                if(callback){
                    callback(communityCategories);
                }
            }, error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function filterCheckLoginUser(communityCategories, callback, error){
        $.ajax({
            url: "/community/list-filter-login-user/" + globalThis.communityAr,
            type: "get",
            success: function(communityCategories, status, xhr){
                if(callback){
                    callback(communityCategories);
                }
            }, error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }


    return {getList: getList, getListNotUser: getListNotUser, infiniteScroll: infiniteScroll, infiniteScrollUser: infiniteScrollUser, addLike: addLike, minusLike: minusLike, checkLike: checkLike, filterCheck: filterCheck, filterCheckLoginUser : filterCheckLoginUser}
})();



let page = 1;
let scrollCheck = false;
$(window).scroll(function(){
    if($(window).scrollTop() * 1.001 >= $(document).height() - $(window).height()){
        if(scrollCheck){
            return;
        }
        scrollCheck = true;
        showCheck=true;
        console.log(globalThis.communityAr);
        if($("input[name='userId']").val()){
            communityService.infiniteScrollUser({
                page:page,
                keyword: globalThis.communityAr
            }, getList);
        }
        else{
            communityService.infiniteScroll({
                page:page,
                keyword: globalThis.communityAr
            }, getList);
        }
        page++;
        setTimeout(function(){
            scrollCheck = false;
        }, 500);
    }
});


/*댓글 창 이동하기*/
$(".view__FeedListWrapper-sc-1fff32g-0").on("click", ".goReplyBoard", function(e){
    e.preventDefault();
    location.href = "/reply/index?communityPostId=" + $(this).attr("href");
})

/*좋아요 누를 때*/
$(".view__FeedListWrapper-sc-1fff32g-0").on("click", ".ActionGroup__LikeButton-sg91np-1", function(e){
    e.preventDefault();
    const $img = $(this).find("img");
    const $span = $(this).find("span");
    let communityPostId = $(this).attr("href");

    if($("input[name='userId']").val()==""){
        $loginModal.show();
        return;
    }

    if($img.attr("class").includes("likePlus")){
        communityService.addLike(
            communityPostId,
            function(){
                console.log("좋아요 성공");
            }
        )
        $img.attr("src", "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='21' height='18' viewBox='0 0 21 18'%3E %3Cpath fill='%23F32D2D' d='M19.437 1.589C18.427.564 17.085 0 15.657 0s-2.77.564-3.78 1.589L10.5 2.986 9.124 1.59C8.114.564 6.77 0 5.344 0c-1.429 0-2.771.564-3.78 1.589-2.085 2.115-2.085 5.557 0 7.673l8.379 8.504c.148.15.348.234.557.234.209 0 .41-.084.557-.234l8.38-8.504c2.084-2.116 2.084-5.558 0-7.673z'/%3E %3C/svg%3E");
        $img.attr("class", "checkLike likeCancel");

        let likeNumber = $span.text();

        $span.text(++likeNumber);
    } else{
        communityService.minusLike(
            communityPostId,
            function(){
                console.log("좋아요 취소 성공");
            }
        )

        $img.attr("src", "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='21' height='18' viewBox='0 0 21 18'%3E %3Cpath fill='none' stroke='%234E5968' stroke-width='1.5' d='M15.657.75c-1.226 0-2.379.485-3.246 1.365l-1.91 1.94-1.912-1.94C7.722 1.235 6.57.75 5.343.75s-2.378.485-3.245 1.365C1.198 3.028.75 4.227.75 5.425c0 1.199.448 2.398 1.348 3.31l8.425 8.504 8.379-8.504c.9-.912 1.348-2.111 1.348-3.31 0-1.198-.448-2.397-1.347-3.31-.867-.88-2.02-1.365-3.246-1.365z'/%3E %3C/svg%3E");
        $img.attr("class", "checkLike likePlus");

        let likeNumber = $span.text();
        $span.text(--likeNumber);
    }
});

let showCheck = true;

// 커뮤니티 카테고리 동적쿼리
$(".jJIWoq").on("click", function(){
    page = 1;
    showCheck = false;

    if($("input[name='userId']").val()==""){
        filteringShow();
    } else{
        filteringShowLoginUser();
    }
});

function filteringShow(){
    communityService.filterCheck(
        globalThis.communityAr, getList);
}

function filteringShowLoginUser(){
    communityService.filterCheckLoginUser(
        globalThis.communityAr, getList);
}


$(".until785px").on("click", function(e){
    e.preventDefault();
    if($("input[name='userId']").val()==""){
        $loginModal.show();
        return;
    }

    location.href=$(".until785px").attr("href");
})


window.onpageshow = function(event) {
    if ( event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
        location.reload();
    }
}
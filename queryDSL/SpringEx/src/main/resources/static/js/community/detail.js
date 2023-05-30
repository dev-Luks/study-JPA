const $detailBtn = $(".jJfiPr");
const $postdetail = $(".DescriptionSection__DescriptionContainer-sc-1vvzceq-0 ")

$detailBtn.click(function () {
    if($postdetail.hasClass("edqUZK")){
        $postdetail.removeClass("edqUZK").addClass("PvHzG");
        $(this).find("span").text("간략히");
        $(this).find("div").removeClass("QiNdM").addClass("dOJyMf")
    }else{
        $postdetail.removeClass("PvHzG").addClass("edqUZK");
        $(this).find("span").text("상세정보 더보기");
        $(this).find("div").removeClass("dOJyMf").addClass("QiNdM")
    }

})

const $updateAndDeleteModal = $(".loungeCardContentsComponents_loungeContentsMoreButtonBox__2jERo");
let modalCheck = false
$(".fOXNjA").on("click",function () {
    if(!modalCheck){
        $updateAndDeleteModal.css("display","inline-flex");
        modalCheck = true;
    }else{
        $updateAndDeleteModal.hide();
        modalCheck=false;
    }
})


// 모임 페이지 수정 페이지로 이동
$(".goUpdate").on("click", function(e){
    e.preventDefault();
    location.href = "/host/update?groupId=" + $(this).attr("href");
})

// 모임 페이지 삭제
$(".goDelete").on("click", function(e){
    e.preventDefault();
    location.href ="/host/deleteGroup?groupId=" + $(this).attr("href");
})

// 모임 페이지 다른 글로 이동
// 모임 상세 게시글로 화면 이동
$(".recommendDetail").on("click", function(e){
    e.preventDefault();
    location.href = "/host/read?groupId=" + $(this).attr("href");
})


// 커뮤니티 페이지 수정
$(".goUpdateCommunity").on("click", function(e){
    e.preventDefault();
    location.href = "/community/update?communityPostId=" + $(this).attr("href");
})

let thumbnailCheck = 0;

// 커뮤니티 페이지 삭제
$(".goDeleteCommunity").on("click", function(e){
    e.preventDefault();
    location.href ="/community/deleteBoard?communityPostId=" + $(this).attr("href");
})

$("#noticeWrap").on("click", function(){
    if($("#notice").attr("class") == "Accordion__Content-sc-1jd6vdl-3 zRWUs"){
        $("#notice").attr("class", "Accordion__Content-sc-1jd6vdl-3 bJFgwb");
    } else{
        $("#notice").attr("class", "Accordion__Content-sc-1jd6vdl-3 zRWUs");
    }
})

/*댓글 창 이동하기*/
$(".goReplyBoard").on("click",function(e){
    e.preventDefault();
    location.href = "/reply/index?communityPostId=" + $(this).attr("href");
})

/*좋아요 누를 때*/
$(".ActionGroup__LikeButton-sg91np-1").on("click", function(e){
    e.preventDefault();
    const $img = $(".checkLike");
    let communityPostId = $(this).attr("href");

    if($("input[name='userId']").val()==""){
        alert("로그인 후 이용 가능합니다.");
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

        let likeNumber = $(".likeNumber").text();
        $(".likeNumber").text(++likeNumber);

    } else{
        communityService.minusLike(
            communityPostId,
            function(){
                console.log("좋아요 취소 성공");
            }
        )

        $img.attr("src", "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='21' height='18' viewBox='0 0 21 18'%3E %3Cpath fill='none' stroke='%234E5968' stroke-width='1.5' d='M15.657.75c-1.226 0-2.379.485-3.246 1.365l-1.91 1.94-1.912-1.94C7.722 1.235 6.57.75 5.343.75s-2.378.485-3.245 1.365C1.198 3.028.75 4.227.75 5.425c0 1.199.448 2.398 1.348 3.31l8.425 8.504 8.379-8.504c.9-.912 1.348-2.111 1.348-3.31 0-1.198-.448-2.397-1.347-3.31-.867-.88-2.02-1.365-3.246-1.365z'/%3E %3C/svg%3E");
        $img.attr("class", "checkLike likePlus");

        let likeNumber = $(".likeNumber").text();
        $(".likeNumber").text(--likeNumber);
    }
});


let communityService = (function() {
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

    return {addLike: addLike, minusLike: minusLike, checkLike: checkLike}
})();


window.onpageshow = function(event) {
    if ( event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
        location.reload();
    }
}
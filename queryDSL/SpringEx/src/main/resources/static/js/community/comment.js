

//텍스트에리아
const $textarea = $(".iHQYSd");
//등록 버튼
const $registerBtn = $(".fWXEgN");




$(document).ready(function(){
    console.log("시작됨");
    console.log($(".ActionMenu__TextButton-s8lvsh-2.kosXvh"));
    //모달 취소 버튼
    const $modalCancelBtn = $(".kXWQPc");
    //모달 창
    const $modal = $("#modal-root")
    // 댓글 삭제 버튼
    const $deleteBtn = $(".ActionMenu__TextButton-s8lvsh-2.kosXvh");

    //댓글 삭제 버튼 클릭시 모달창 팝업
    $deleteBtn.on("click", function(){
        console.log($modal);
        $modal.css("display","block");
    });

    //모달창 끄기
    $modalCancelBtn.on("click", function(){
        $modal.css("display","none");
    });
})

$textarea.keyup(function (e) {
    let content = $(this).val();
    if(content.length==0 || $(".iHQYSd").val()==''){
        $registerBtn.css("cursor","not-allowed");
        $registerBtn.prop("disabled", true);
    }else{
        $registerBtn.css("cursor","pointer");
        $registerBtn.prop("disabled", false);
    }
})

$registerBtn.on("click", function(){
    $registerBtn.css("cursor","not-allowed");
    $registerBtn.prop("disabled", true);
})


// 모듈화
let replyService = (function() {
    function add(reply, callback, error){
        $.ajax({
            url: "/reply/add",
            type: "post",
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
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

    function getReplyList(param, callback, error){
        $.ajax({
            url: "/reply/list/" + param.communityPostId,
            type: "get",
            success: function(communityReplyDTO, status, xhr){
                if(callback){
                    callback(communityReplyDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function remove(communityReplyId, callback, error){
        $.ajax({
            url: "/reply/delete-reply/" + communityReplyId,
            type: "delete",
            success: function(text){
                if(callback){
                    callback(text);
                }
            }, error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    return {add: add, getReplyList: getReplyList, remove: remove}
})();

// 추가
$(".TextAreaSection__SubmitButton-h0o51s-5.fWXEgN").on("click", function(){
    if($("input[name='myId']").val()==""){
        $(".TextAreaSection__SubmitButton-h0o51s-5.fWXEgN").text("로그인 후 댓글 등록이 가능합니다.");
        return;
    }

    replyService.add({
        communityReplyContent: $(".TextAreaSection__Textarea-h0o51s-4.iHQYSd").val(),
        communityPostId: $("input[name='communityPostId']").val(),
        communityPostTitle : postTitle,
        userId: $("input[name='myId']").val(),
        writerId : postUserId
    }, function(){
        $(".TextAreaSection__Textarea-h0o51s-4.iHQYSd").val('');
        show();
    });
});


$(".replyWrap").on("click", "a.deleteReply", function(){
    let communityReplyId = $(this).attr("href");
    replyService.remove(
        communityReplyId,
        function(){
        show();
    });
});


window.onpageshow = function(event) {
    if ( event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
        location.reload();
    }
}
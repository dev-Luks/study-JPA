
$("article div.LinkAccordion__AccordionContainer-sc-1o112j5-1.cJPEO").on("click", "#noticeWrap", function(){
    if($("#notice").attr("class")== "Accordion__Content-sc-1jd6vdl-3 zRWUs"){
        $("#notice").attr("class", "Accordion__Content-sc-1jd6vdl-3 bJFgwb");
    } else{
        $("#notice").attr("class", "Accordion__Content-sc-1jd6vdl-3 zRWUs");
    }
})

$("#refundWrap").on("click", function(){
    if($("#refund").attr("class")== "Accordion__Content-sc-1jd6vdl-3 zRWUs"){
        $("#refund").attr("class", "Accordion__Content-sc-1jd6vdl-3 bJFgwb");
    }else{
        $("#refund").attr("class", "Accordion__Content-sc-1jd6vdl-3 zRWUs");
    }
})


// 참여하기
$(".joinGroup").on("click", function(){
    if(confirm("[" + $(".BasicInfoSection__Title-sc-1f238bn-5.cWsrnE").text() + "] 모임 참여를 하시면 " + $(".PriceInfo__PriceUnit-sc-1s3i0u7-3.kWprXL").text() + "포인트가 차감됩니다.\n참여하시겠습니까?" )){ // 확인 버튼을 눌렀을 때
        $.ajax({
            url: "/host/group-member/" + $(".goUpdate").attr("href"),
            type: "post",
            async: false,
            success: function(result, status, xhr) {
                result == true ? $("#modal-root-group-join").show() : $("#modal-root-group-no-money").show();
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
        location.href= "/host/group-list";
    }else{  // 취소 버튼을 눌렀을 때
        $(".DialogDelete__Content-p8gubf-0.jZuxbV.confirm").text("취소 되었습니다.");
        $("#modal-root-group-join").show();
    }
})

$(".Button-bqxlp0-0.Dialog__StyledButton-sc-16kwpqb-7.kXWQPc").on("click", function(){
    $("#modal-root-group-no-money").css("display", "none");
    $("#modal-root-group-join").css("display", "none");
})


/*모임 신청 취소*/
$(".cancelGroup").on("click", function(){
    if(confirm("[" + $(".BasicInfoSection__Title-sc-1f238bn-5.cWsrnE").text() + "] 모임 참여를 취소하시겠습니까?\n포인트 환불은 관리자 확인에 따라 며칠의 기간이 소요될 수 있습니다.")) {
        $.ajax({
            url: "/host/group-member-delete/" + $(".goUpdate").attr("href"),
            type: "post",
            async: false,
            success: function (result, status, xhr) {
                $(".DialogDelete__Content-p8gubf-0.jZuxbV.confirm").text("모임 참여가 취소되었습니다.");
                $("#modal-root-group-join").show();
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
        location.href= "/host/group-list";
    }else{
        ;
    }
})

function approve(){
    $.ajax({
        url: "/group/"+ "approve" + "/" + $(".goUpdate").attr("href"),
        type: "patch",
        async: false,
        success: function(result, status, xhr) {
            $(".DialogDelete__Content-p8gubf-0.jZuxbV.confirm").text("모임이 승인되었습니다.");
            $("#modal-root-group-join").show();
        },
        error: function(xhr, status, err){
            if(error){
                error(err);
            }
        }
    });
}
function disapprove(){
    $.ajax({
        url: "/group/"+ "disapprove" + "/" + $(".goUpdate").attr("href"),
        type: "patch",
        success: function(result, status, xhr) {
            $(".DialogDelete__Content-p8gubf-0.jZuxbV.confirm").text("모임이 거절되었습니다.");
            $("#modal-root-group-join").show();
        },
        error: function(xhr, status, err){
            if(error){
                error(err);
            }
        }
    });
}

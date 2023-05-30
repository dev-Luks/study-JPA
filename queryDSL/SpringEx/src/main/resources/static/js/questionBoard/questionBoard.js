$wrap=$(".arrange");
$contents = $(".FAQEntity__TitleWrapper-cpxlp5-0");
$category = $(".TabLabels__Tab-sc-1i2f4oa-1");

/*FAQEntity__TitleWrapper-cpxlp5-0 gvlnCr
FAQEntity__TitleWrapper-cpxlp5-0 ldvBHS*/




//질문 누르면 답변 나오게 하기
// $contents.on("click", function(){
//     if($(this).attr("class").includes("arrange")){
//         $(this).find(".ldvBHS").attr("class", "FAQEntity__TitleWrapper-cpxlp5-0 ldvBHS");
//         $(this).next().find(".ldvBHS").hide();
//     } else{
//         $(".gvlnCr").parent().next().find(".ldvBHS").hide();
//         $contents.attr("class", "FAQEntity__TitleWrapper-cpxlp5-0 ldvBHS");
//         $(this).find(".ldvBHS").attr("class", "FAQEntity__TitleWrapper-cpxlp5-0 gvlnCr");
//         $(this).next().find(".ldvBHS").show();
//     }
// })

$(".arrange").on("click",function(){
    console.log(this);
})
$(".ldvBHS").on("click",function(){
    console.log(this);
})
$(".FAQEntity__TitleWrapper-cpxlp5-0").on("click",function(){
    console.log(this);
})
$(".answerStatus").on("click",function(){
    console.log(this);
})

$(".represent").on("click",function(){
    if($(this).attr("class").includes("represent")){
        $(this).next().toggle();
    }
})

/*=======================restful 페이징 처리 변경후 작업=========================*/
$(".content").on("click","div.my_list",function(){
    $(this).next().toggle();
})

let askService = (function(){
    function getList(param, callback, error){
        $.ajax({
            url: encodeURI("/questionBoard/questionBoard/" + (param.page || 0) + "/" + param.keyword),
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
    return {getList: getList}
}) ();
/*=======================restful 페이징 처리 변경후 작업=========================*/













// //질문 누르면 답변 나오게 하기
// $contents.on("click", functdjWtyMion(){
//     if($(this).attr("class").includes("gvlnCr")){
//         $(this).attr("class", "FAQEntity__TitleWrapper-cpxlp5-0 ldvBHS");
//         $(this).next().hide();
//     } else{
//         $(".gvlnCr").next().hide();
//         $contents.attr("class", "FAQEntity__TitleWrapper-cpxlp5-0 ldvBHS");
//         $(this).attr("class", "FAQEntity__TitleWrapper-cpxlp5-0 gvlnCr");
//         $(this).next().show();
//     }
// })

// //화면 안깨지게..
// function resizeApply() {
//     var minWidth = 769;
//     var body = document.getElementsByTagName('body')[0];
//     if (window.innerWidth < minWidth) { body.style.zoom = (window.innerWidth / minWidth); }
//     else body.style.zoom = 1;
// }
//
// window.onload = function() {
//     window.addEventListener('resize', function() {
//         resizeApply();
//     });
// }

// 카테고리 변경
$category.on("click", function(){
    if($(this).attr("class").includes("eCnPke")){
        $category.attr("class", "TabLabels__Tab-sc-1i2f4oa-1 eCnPke");
        $(this).attr("class", "TabLabels__Tab-sc-1i2f4oa-1 djWtyM");
    }
})
$(".my_list").on("click",function(){
    console.log($(this).next().next().find("div:first").text());
    if($(this).attr("class").includes("my_list")){
        $(this).next().toggle();
        if($(this).next().next().find("div:first").text()!=null && $(this).next().next().find("div:first").text()!=""){
            $(this).next().next().toggle();
        }
    }
});
$(".ldvBHS").on("click",function(){
    if($(this).attr("class").includes("ldvBHS")){
        $(this).next().toggle();
    }
})

$(".my_lists").on("click",function(){
    var askData=$("input[name='requestAsk']").val();
    console.log(askData);
    location.href="/questionBoard/managerAnswer?askId="+askData;
});


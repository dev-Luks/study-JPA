
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
checkMedia()
$(window).resize(function(){
    if(window.innerWidth<785){
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 media");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 categoryMedia");
        $(".fzBbGO").css("width", "95%");
    } else{
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 ciajVR");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 gRnEmw");
        $(".fzBbGO").css("width", "100%");
    }
})

function checkMedia(){
    if(window.innerWidth<785){
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 media");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 categoryMedia");
        $(".fzBbGO").css("width", "95%");
    } else{
        $(".view__FeedListWrapper-sc-1fff32g-0").attr("class", "view__FeedListWrapper-sc-1fff32g-0 ciajVR");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-1").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-1 gRnEmw");
        $(".fzBbGO").css("width", "100%");
    }
}
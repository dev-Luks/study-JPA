const $categoryMedia = $(".list_filter_wrapper");
const $categoryOriginal = $("#list_filter_wrapper");

// 카테고리 크기 미디어쿼리
checkWidth();

$(window).resize(function(){
    if(window.innerWidth<867){
        $categoryOriginal.hide();
        $categoryMedia.show();
    } else{
        $categoryOriginal.show();
        $categoryMedia.hide();
    }
})

function checkWidth(){
    if(window.innerWidth<867){
        $categoryOriginal.hide();
        $categoryMedia.show();
    } else{
        $categoryOriginal.show();
        $categoryMedia.hide();
    }
}

$(".content").on("click","div.my_list",function(){
    var askData=$(this).next().val();
    location.href="/host/read?groupId="+askData;
});

let groupService = (function(){
    function getList(param, callback, error){
        $.ajax({
            url: encodeURI("/myGroup/myJoinGroup/" + (param.page || 0) + "/" + param.keyword),
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
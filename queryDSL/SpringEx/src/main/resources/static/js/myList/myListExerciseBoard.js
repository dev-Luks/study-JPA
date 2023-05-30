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

let postService = (function(){
    function getList(param, callback, error){
        $.ajax({
            url: encodeURI("/myList/myListExercise/" + (param.page || 0) + "/" + param.keyword),
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
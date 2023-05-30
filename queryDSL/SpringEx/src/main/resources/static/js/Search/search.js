
checkMedia();

$(window).resize(function(){
    if(window.innerWidth<769){
        $(".ProductList__ProductWrapper-sc-1lo0q2y-0").attr("class", "ProductList__ProductWrapper-sc-1lo0q2y-0 lcsYvn");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-12").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-12 hWBKQE12");
    } else{
        $(".ProductList__ProductWrapper-sc-1lo0q2y-0").attr("class", "ProductList__ProductWrapper-sc-1lo0q2y-0 jjtvVw");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-12").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-12 gRnEmw");
    }
})

function checkMedia(){
    if(window.innerWidth<769){
        $(".ProductList__ProductWrapper-sc-1lo0q2y-0").attr("class", "ProductList__ProductWrapper-sc-1lo0q2y-0 lcsYvn");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-12").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-12 hWBKQE12");
    } else{
        $(".ProductList__ProductWrapper-sc-1lo0q2y-0").attr("class", "ProductList__ProductWrapper-sc-1lo0q2y-0 jjtvVw");
        $(".Filter__FilterButtonWrapper-sc-1buujtg-12").attr("class", "Filter__FilterButtonWrapper-sc-1buujtg-12 gRnEmw");
    }

}


const $filterBtn = $(".jJIWoq")

$filterBtn.click(function () {
    let text= $(this).text().trim();
    if($(this).find("button").hasClass('fFBpBV')) {
        $(this).find("button").removeClass('fFBpBV').addClass("kJEnf");
        $(this).children().append('<button height="18px" class="Button-bqxlp0-0 FilterOpenButton__CloseButton-sc-91gci-1 gRNDCb"><img src="data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'18\' height=\'18\' viewBox=\'0 0 18 18\'%3E %3Cg fill=\'none\' fill-rule=\'nonzero\' stroke=\'%23DDD\' stroke-linecap=\'square\'%3E %3Cpath d=\'M11.828 6.172l-5.656 5.656M11.828 11.828L6.172 6.172\'/%3E %3C/g%3E %3C/svg%3E" class="FilterOpenButton__CloseIcon-sc-91gci-2 hnfyOW"></button>')

    }else{
        $(this).empty();
        $(this).append(`<div class="FilterOpenButton__Wrapper-sc-91gci-0 NVKNq">
                    <button height="auto" color="initial" font-size="14px" font-weight="500" class="Button-bqxlp0-0 fFBpBV">`+text+`</button>
                </div>`)
    }
})


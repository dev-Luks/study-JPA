const $checkYear = $(".year");
const $checkMonth = $(".month");
const $categoryDate = $(".e12i9j8n1");
const $prevDay = $(".prevArrow");
const $nextDay = $(".nextArrow");

let dayText = "";
let yearText = "";
let monthText = "";
let dateText = "";
let dayTempText="";

globalThis.diaryAr=new Array();
console.log(typeof globalThis.diaryAr);
console.log(globalThis.diaryAr);
// 캘린더 크기 미디어쿼리
checkCalendar();

$(window).resize(function(){
    if(window.innerWidth<1905){
        $(".e1k52epl0").css("font-size", "12px");
        $(".dateText").css("font-size", "12px");
        if(window.innerWidth<1650){
            $(".wholeWrap").removeAttr("style");
            $(".dateForm").removeAttr("style");
            $(".e12i9j8n0").removeAttr("style");
            $(".css-16df71f").removeAttr("style");
            $("#list_page_wrapper").removeAttr("style");

            $(".wholeWrap").css({"display":"flex", "flex-direction":"column"});
            $(".dateForm").css("width","98%");
            if(window.innerWidth>867){
                $(".e12i9j8n0").css({"width":"768px", "margin": "0 auto"});
            } else{
                $(".e12i9j8n0").css({"width":"95%", "margin": "0 auto"});
            }
            $(".e1k52epl0").css({"width":"32%"});
            $(".css-16df71f").css("float","none");
            $(".css-zr1uxp").css("height", "391px");
            $(".css-klg5aa").css("top", "-14px");
            $(".css-1kz857u").css("top", "-20px");
            $(".css-oys1qn").css("text-align", "center");
            $(".dayText").css({"top": "-25px", "position": "relative"});
            $(".dayTextArrow").css({"position":"relative", "top": "-20px"});
            $("#list_page_wrapper").css("margin", "0 auto");
        } else{
            $(".wholeWrap").removeAttr("style");
            $(".dateForm").removeAttr("style");
            $(".e12i9j8n0").removeAttr("style");
            $(".css-16df71f").removeAttr("style");
            $(".dateForm").removeAttr("style");
            $("#list_page_wrapper").removeAttr("style");


            $(".wholeWrap").css({"display":"grid", "grid-template-columns":"28% 72%", "grid-gap":"10px"});
            $(".dateForm").css("grid-template-rows","74px 450px");
            $(".e12i9j8n0").css({"width":"48%", "margin-top": "30px"});
            $(".e1k52epl0").css("width", "48%");
            $(".css-16df71f").css({"flex":"1 1 0%", "margin-bottom":"20px", "float":"right"});
            $(".css-zr1uxp").css("height", "519px");
            $(".css-klg5aa").css("top", "0px");
            $(".css-1kz857u").css("top", "0px");
            $(".css-oys1qn").css("text-align", "none");
            $(".dayText").css({"top": "0px", "position": "relative"});
            $(".dayTextArrow").css({"position":"relative", "top": "0px"});
            $("#list_page_wrapper").css("margin", "0px");
        }
    }
    else{
        $(".e1k52epl0").css("font-size", "17px");
        $(".dateText").css("font-size", "14px");
    }
})

function checkCalendar(){
    if(window.innerWidth<1905){
        $(".e1k52epl0").css("font-size", "12px");
        $(".dateText").css("font-size", "12px");
        if(window.innerWidth<1650){
            $(".wholeWrap").removeAttr("style");
            $(".dateForm").removeAttr("style");
            $(".e12i9j8n0").removeAttr("style");
            $(".css-16df71f").removeAttr("style");
            $("#list_page_wrapper").removeAttr("style");

            $(".wholeWrap").css({"display":"flex", "flex-direction":"column"});
            $(".dateForm").css("width","98%");
            if(window.innerWidth>867){
                $(".e12i9j8n0").css({"width":"768px", "margin": "0 auto"});
            } else{
                $(".e12i9j8n0").css({"width":"95%", "margin": "0 auto"});
            }
            $(".e1k52epl0").css({"width":"32%"});
            $(".css-16df71f").css("float","none");
            $(".css-zr1uxp").css("height", "391px");
            $(".css-klg5aa").css("top", "-14px");
            $(".css-1kz857u").css("top", "-20px");
            $(".css-oys1qn").css("text-align", "center");
            $(".dayText").css({"top": "-25px", "position": "relative"});
            $(".dayTextArrow").css({"position":"relative", "top": "-20px"});
            $("#list_page_wrapper").css("margin", "0 auto");
        } else{
            $(".wholeWrap").removeAttr("style");
            $(".dateForm").removeAttr("style");
            $(".e12i9j8n0").removeAttr("style");
            $(".css-16df71f").removeAttr("style");
            $(".dateForm").removeAttr("style");
            $("#list_page_wrapper").removeAttr("style");


            $(".wholeWrap").css({"display":"grid", "grid-template-columns":"28% 72%", "grid-gap":"10px"});
            $(".dateForm").css("grid-template-rows","74px 450px");
            $(".e12i9j8n0").css({"width":"48%", "margin-top": "30px"});
            $(".e1k52epl0").css("width", "48%");
            $(".css-16df71f").css({"flex":"1 1 0%", "margin-bottom":"20px", "float":"right"});
            $(".css-zr1uxp").css("height", "519px");
            $(".css-klg5aa").css("top", "0px");
            $(".css-1kz857u").css("top", "0px");
            $(".css-oys1qn").css("text-align", "none");
            $(".dayText").css({"top": "0px", "position": "relative"});
            $(".dayTextArrow").css({"position":"relative", "top": "0px"});
            $("#list_page_wrapper").css("margin", "0px");
        }
    }
    else{
        $(".e1k52epl0").css("font-size", "17px");
        $(".dateText").css("font-size", "14px");
    }
}



// 카테고리 변경 시
$categoryDate.on("click", function(){
    $categoryDate.attr("class", "css-1pm7srj e12i9j8n1");
    $(this).attr("class","css-10lvc7b e12i9j8n1");
    if($(this).text() == '연'){
        $("#monthCheck").hide();
        $("#yearCheck").show();
        $("#dayCheck").hide();
    } else if($(this).text() == '월'){
        $("#monthCheck").show();
        $("#yearCheck").hide();
        $("#dayCheck").hide();
    } else{
        $("#monthCheck").hide();
        $("#yearCheck").hide();
        $("#dayCheck").show();
    }
})

// 일자 변경(감소)
$prevDay.on("click", function(){
    dayText = $(".dayText").text();
    if(dayText>1) {
        if (dayText - 1 < 10) {
            dayText = "0" + (dayText - 1);
        } else {
            dayText -= 1;
        }
        $(".dayText").text(dayText);
    }
})

// 일자 변경(증가)
$nextDay.on("click", function(){
    dayText = $(".dayText").text();
    dayText = parseInt(dayText);

    if(dayText<31){
        if (dayText + 1 < 10) {
            dayText = "0" + (dayText + 1);
        } else {
            console.log(dayText);
            dayText += 1;
        }
        $(".dayText").text(dayText);
    }
})

// 날짜 선택 시 배경색 변경
$checkYear.on("click", function(){
    $checkYear.attr("class", "css-vvmm1k e1k52epl0 year");

    if($(this).attr("class")=="css-vvmm1k e1k52epl0 year check"){
        $(this).attr("class", "css-vvmm1k e1k52epl0 year");
    } else{
        $(this).attr("class", "css-vvmm1k e1k52epl0 year check");
    }
})

$checkMonth.on("click", function(){
    $checkMonth.attr("class", "css-vvmm1k e1k52epl0 month");

    if($(this).attr("class")=="css-vvmm1k e1k52epl0 month check"){
        $(this).attr("class", "css-vvmm1k e1k52epl0 month");
    } else{
        $(this).attr("class", "css-vvmm1k e1k52epl0 month check");
    }
})

$(".arrow").on("click",function(){
    dayTempText=$(".dayText").text();
})
$("button.eklkj752").on("click", function(){
    globalThis.diaryAr=[];
    let today = new Date();
    let year = today.getFullYear();
    let month = ('0' + (today.getMonth() + 1)).slice(-2);
    let day = ('0' + today.getDate()).slice(-2);
    yearText = $(".year.check").text();
    monthText = $(".month.check").text();
    dayText = $(".dayText").text();

    // if(!yearText && !monthText){
    //     dateText = year +"년 " + month + "월 " + dayText + "일";
    //     $(".selectDate").text(dateText);
    //     return;
    // }
    //
    // if(!yearText){
    //     console.log("yearText in");
    //     yearText = year + "년";
    //     return;
    // }
    // if(!monthText){
    //     console.log("monthText in");
    //     dateText = yearText;
    //     $(".selectDate").text(dateText);
    //     return;
    // }
    // if(!dayText){
    //     dateText=yearText+monthText;
    //     $(".selectDate").text(dateText);
    //     return;
    // }
    dayText ="";
    dateText="";
    console.log("before transmit yearText:"+yearText);
    console.log("before transmit monthText:"+monthText);
    console.log("before transmit dayText:"+dayText);
    console.log("before transmit dateText:"+dateText);

    if((yearText) || (yearText && monthText) || (yearText && monthText && dayTempText)){

        if(yearText){
            dateText = yearText;
            console.log("yearText:"+yearText);
        }if(yearText && monthText){
            dateText=yearText+monthText;
            console.log("yearText && monthText:"+dateText);
        }if(yearText && monthText && dayTempText){
            dateText = yearText+monthText+dayTempText+"일";
            console.log("yearText && monthText && dayText:"+dayTempText);
        }
        console.log("dateText:"+dateText);
        $(".selectDate").text(dateText);

        globalThis.diaryAr.push(yearText.substr(0,4)+"년");
        globalThis.diaryAr.push(monthText.substr(0,2)+"월");
        globalThis.diaryAr.push(dayTempText+"일");


        $(".year.check").attr("class","css-vvmm1k e1k52epl0 year");
        $(".month.check").attr("class","css-vvmm1k e1k52epl0 month");
        dayTempText="";



        let datas={"page":page,"dateData":globalThis.diaryAr};
        diaryService.getList(datas,getList);
        console.log("click method before array:"+globalThis.diaryAr);
        console.log("click method after array:"+globalThis.diaryAr);
    }else{alert("(월) 또는 (월,일) 클릭시 정확한 날짜를 조회할 수 없습니다. 정확한 날짜를 클릭해주세요.");}

    // dateText = yearText + " " + monthText + " " + dayText+ "일";
    // $(".selectDate").text(dateText);

    console.log("dateText:"+dateText);
    console.log("yearText:"+yearText);
    console.log("monthText:"+monthText);
    console.log("dayText:"+dayTempText);
    console.log(typeof dateText);

    dayText = "";
    yearText = "";
    monthText = "";
    dateText = "";
    dayTempText="";

    // let dateData={"page":page,"keyword":keyword,"year":yearText,"month":monthText,"day":dayText};


    // $.ajax({
    //     url:"calendar",
    //     type:"post",
    //     headers:{"Content-Type":"application/json"},
    //     data:JSON.stringify(dateData),
    //     dataType:"text",
    //     success:function(result){
    //         console.log(result);
    //     },error:function(status,error){
    //         console.log(status,error);
    //     }
    // });
    // let datas={"page":page,"dateData":globalThis.diaryAr};
    // diaryService.getList(datas,getList);
    // globalThis.diaryAr=[];
})

console.log("outer click method per array:"+globalThis.diaryAr);
// url: encodeURI("/myList/diary/" + (param.page || 0) + "/" + param.keyword),
var test="";
let diaryService = (function(){
    function getList(param, callback, error){
        $.ajax({
            url: encodeURI("/myList/diary/" + (param.page || 0) + (globalThis.diaryAr != null ? "/"+param.dateData:"")),
            // "/myList/diary/0/" + "dateData"
            // "/myList/diary/0" + ""
            type: "get",
            async : false,
            success: function(diaryDTO, status, xhr){
                if(callback){
                    callback(diaryDTO);
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
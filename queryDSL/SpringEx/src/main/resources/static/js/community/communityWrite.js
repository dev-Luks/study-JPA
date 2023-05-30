//첫번째 셀렉트 박스의 리스트들 의 모임
const $postFilterLi = $(".post_filter li");
//다이어리 필터안 리스트들의 모임
const $diaryFilterLi = $(".diary_filter_1h li");
//교환 필터
const $exchangeFilter = $(".exchange_filter_1h");
//교환 필터안 리스트들의 모임
const $exchangeFilterLi = $(".exchange_filter_1h li");

let text ="";

$postFilterLi.click(function () {
    var text = $(this).text();
    console.log($(this).text());
    $(this).parent().find(".post_filter_input").val(text);
    $(this).parent().parent().removeClass('active');
    $(this).parent().prev().text(text);

    if(text=='일기'){
        console.log("일기들어옴");
        $("div.px-3").attr("style","display:none;");
        $exchangeFilter.show();
    }else{
        console.log("일기아님 들어옴");
        $("div.px-3").attr("style","display:block;");
        $exchangeFilter.hide();
    }
})


$diaryFilterLi.click(function(){
    var text = $(this).text();
    $(this).parent().find(".diary_filter_input").val(text);
    $(this).parent().parent().removeClass('active');
    $(this).parent().prev().text(text);
})


$exchangeFilterLi.click(function () {
    var text = $(this).text();
    $(this).parent().find(".exchange_filter_input").val(text);
    $(this).parent().parent().removeClass('active');
    $(this).parent().prev().text(text);
})

checkMedia()

$(window).resize(function(){
    if(window.innerWidth<650){
        $(".registerBtn_1h").hide()
        $(".until650px").show()
    } else{
        $(".registerBtn_1h").show()
        $(".until650px").hide()
    }
})

function checkMedia(){

    if(window.innerWidth<650){
        $(".registerBtn_1h").hide()
        $(".until650px").show()
    } else{
        $(".registerBtn_1h").show()
        $(".until650px").hide()
    }
}


/*썸머노트*/
// // 서머노트에 text 쓰기
// $('#summernote').summernote('insertText', 써머노트에 쓸 텍스트);

$(document).ready(function() {
    function sendFile(file){
        var data = new FormData();
        data.append("file",file);
        $.ajax({
            url: "/host/summernote/",
            type: "POST",
            enctype: 'multipart/form-data',
            data: data,
            cache: false,
            contentType : false,
            processData : false,
            success: function(image){
                /*컨트롤러를 통해 절대경로로 이미지가 저장되면, 서머노트에 해당 이미지가 출력된다.*/
                $('#summernote').summernote('insertImage',image);
            },
            error: function(e){console.log(e);}
        });
    }

    // summernote
    $('#summernote').summernote({
        height :500,
        minHeight:null,
        maxHeight:1000,
        toolbar: [
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['color', ['forecolor','color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture']],
        ],
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
        focus:true,
        lang : "ko-KR",
        callbacks: {
            /*이미지가 업로드될 때 sendFile 함수를 실행한다.*/
            onImageUpload : function(files){
                sendFile(files[0]);
            }
        }
    });

    /*서머노트 api가 켜지면 생기는 클래스*/
    $(".note-editable").html($('input[name=groupContent]').val());

    /*서머노트에 작성된 값이 변할 때*/
    $('#summernote').on('summernote.change', function(we, contents, $editable) {
        $('input[name=groupContent]').attr('value', $(".note-editable").html());
    });
});


let communitySave = (function(){
    function add(communityContents, callback, error){
        $.ajax({
            url: "/community/index",
            async: false,
            type: "post",
            data: JSON.stringify(communityContents),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr){
                if(callback){
                    callback(result);
                }
                location.href="/community/index";
            },
            error: function(xhr, status, err)
            {
                if(error){
                    error(err);
                }
            }
        });
    }
    function update(communityContents, callback, error){

        $.ajax({
            url: "/community/update",
            async: false,
            type: "post",
            data: JSON.stringify(communityContents),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr) {
                if (callback) {
                    callback(result);
                }
                location.href="/community/index";
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    return {add: add, update: update}
})();

let checkModal = 0;
$(".Button-bqxlp0-0.Dialog__StyledButton-sc-16kwpqb-7.kXWQPc.diary").on("click", function(){
    $("#modal-root-diary").css("display", "none");
    if(checkModal==1){
        location.href="/myList/myExchangeDiary";
    } else if(checkModal==2){
        location.href = "/myList/myDiary";
    }
})

$(".Button-bqxlp0-0.Dialog__StyledButton-sc-16kwpqb-7.kXWQPc.registerCheck").on("click", function(){
    $("#modal-root-register-error").css("display", "none");
})

let diarySave = (function(){
    function diaryadd(diaryContents, callback, error){
        $.ajax({
            url: "/myList/index",
            async: false,
            type: "post",
            data: JSON.stringify(diaryContents),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr){
                if(callback){
                    callback(result);
                }
                checkModal=0;
                if(result=="standby"){
                    $(".DialogDelete__Content-p8gubf-0.jZuxbV.diary").text("현재 교환일기를 전달할 회원이 없습니다.\n다른 회원이 교환일기 작성 시 교환 일기를 확인 하실 수 있습니다.");
                    $("#modal-root-diary").show();
                    checkModal++;
                }else if(result=="exchangeDiary"){
                    checkModal++;
                    $(".DialogDelete__Content-p8gubf-0.jZuxbV.diary").text("신청한 교환일기가 다른 회원과 교환되었습니다.");
                    $("#modal-root-diary").show();
                }else if(result=="MyDiary") {
                    checkModal = checkModal+2;
                    $(".DialogDelete__Content-p8gubf-0.jZuxbV.diary").text("나의 일기 작성이 완료되었습니다.");
                    $("#modal-root-diary").show();
                }else if(result=="duplicate"){
                    $(".DialogDelete__Content-p8gubf-0.jZuxbV.diary").text("나의 일기는 1번만 작성이 가능합니다.");
                    $("#modal-root-diary").show();
                }else if(result=="alreadyRegisterToday"){
                    $(".DialogDelete__Content-p8gubf-0.jZuxbV.diary").text("교환 일기는 하루에 한번만 작성이 가능합니다.");
                    $("#modal-root-diary").show();
                }else {
                    checkModal++;
                    $(".DialogDelete__Content-p8gubf-0.jZuxbV.diary").text("교환 일기 작성이 완료되었습니다. 다른 날짜에 작성한 교환일기 목록은 마이페이지에서 확인이 가능합니다.");
                    $("#modal-root-diary").show();
                }
            },
            error: function(xhr, status, err)
            {
                if(error){
                    error(err);
                }
            }
        });
    }
    return {diaryadd: diaryadd}
})();




// 게시글 작성 진행 후 등록 버튼 눌렀을 때
$(".Button-bqxlp0-0.fFBpBV").on("click", function(e){
    e.preventDefault();

    // 카테고리 설정
    let category = $(".post_filter .label").text();

    // 유효성 검사

    // 제목 기재 안했을 때
    if($(".SocialFeedPage__Title-ky5ymg-2.gVPyuz").val()==""){
        $(".DialogDelete__Content-p8gubf-0.jZuxbV.register-error").text("제목을 작성해주세요");
        $("#modal-root-register-error").show();
        return;
    }

    // 글 길이가 너무 길 때
    if($(".note-editable").text().length>10000){
        $(".DialogDelete__Content-p8gubf-0.jZuxbV.register-error").text("글자는 10,000자 이내로 작성 가능합니다.");
        $("#modal-root-register-error").show();
        return;
    }

    // 글 내용이 없을 때
    if($(".note-editable").text()==""){
        $(".DialogDelete__Content-p8gubf-0.jZuxbV.register-error").text("내용을 기재해주세요");
        $("#modal-root-register-error").show();
        return;
    }

    if(category!="일기") {
        // 썸네일 이미지가 없을 때
        if ($("input[name=communityFileName]").val() == "") {
            $(".DialogDelete__Content-p8gubf-0.jZuxbV.register-error").text("썸네일 이미지를 입력해주세요");
            $("#modal-root-register-error").show();
            return;
        }
    }

    // 나의 일기,교환 일기 설정
    let diaryCategory=$(".exchange_filter_1h .label").text();


    /*작성 내용*/
    /*업데이트 체크*/
    if($('input[name=communityPostId]').val()){
        /*업데이트*/
        communitySave.update({
            communityPostId : $('input[name=communityPostId]').val(),
            communityCategory : category,
            communityContent : $(".note-editable").html(),
            communityTitle: $(".SocialFeedPage__Title-ky5ymg-2.gVPyuz").val(),
            communityFileName: $("input[name=communityFileName]").val(),
            communityFileUuid: $("input[name=communityFileUuid]").val(),
            communityFileSize: $("input[name=communityFileSize]").val(),
            communityFilePath: $("input[name=communityFilePath]").val()
        })
    }

    else{
        /*추가*/
        if(category != "일기"){
            communitySave.add({
                communityCategory : category,
                communityContent : $(".note-editable").html(),
                communityTitle: $(".SocialFeedPage__Title-ky5ymg-2.gVPyuz").val(),
                communityFileName: $("input[name=communityFileName]").val(),
                communityFileUuid: $("input[name=communityFileUuid]").val(),
                communityFileSize: $("input[name=communityFileSize]").val(),
                communityFilePath: $("input[name=communityFilePath]").val()
            })
        }else{
            diarySave.diaryadd({
                diaryCategory : diaryCategory,
                diaryContent : $(".note-editable").html(),
                diaryTitle: $(".SocialFeedPage__Title-ky5ymg-2.gVPyuz").val()
            })
        }
    }
});


//썸네일 이미지
$(".plusThumb").on("change", function(){
    let arrayFile =[];
    let formData = new FormData();
    let $inputFile = $("input[name='plusThumb']");
    let files = $inputFile[0].files;

    Array.from(files).forEach(file => arrayFile.push(file));
    const dataTransfer = new DataTransfer();

    arrayFile.forEach(file => dataTransfer.items.add(file));
    $(this)[0].files = dataTransfer.files;

    $(files).each(function(i, file){
        formData.append("upload", file);
    });

    $.ajax({
        url: "/community/thumbnail",
        type: "post",
        data: formData,
        contentType: false,
        processData: false,
        success: function(result) {
            $('input[name=communityFileName]').attr('value', Object.values(result[0])[12]);
            $('input[name=communityFilePath]').attr('value', Object.values(result[0])[11]);
            $('input[name=communityFileSize]').attr('value', Object.values(result[0])[14]);
            $('input[name=communityFileUuid]').attr('value', Object.values(result[0])[13]);
            let imageSrc = "/community/display?fileName=" + Object.values(result[0])[11] + "/" + Object.values(result[0])[13] + "_" + Object.values(result[0])[12];

            if($('input[name=communityFileSize]').val()>100000){
                $(".DialogDelete__Content-p8gubf-0.jZuxbV.register-error").text("파일 사이즈는 10MB 이하여야합니다.");
                $("#modal-root-register-error").show();
                return;
            }

            $('.image-header').show();
            $('.img-box').show();

            let text = "";
            text += `<li id="thumbnailImage" data-file-size="` + Object.values(result[0])[14] + `" data-file-name="` + Object.values(result[0])[12] + `" data-file-upload-path="` + Object.values(result[0])[11] + `" data-file-uuid="` + Object.values(result[0])[13] + `" style="list-style: none;width:100%;">`;
            text += `<img src=` + imageSrc + ` style="width:100%;" height="auto">`;
            text += `</li>`;

            $(".imgInputBox").append(text);

            $(".text-center.container.thumbnailPlus").hide();
        }
    });
});


//썸네일 삭제
$('.removeImg').on('click', function(){
    $('.image-header').hide();
    $('.img-box').attr('src', '');
    let uploadPath = $("#thumbnailImage").data("file-upload-path");
    let fileName = $("#thumbnailImage").data("file-uuid") + "_" + $("#thumbnailImage").data("file-name");

    $.ajax({
        url: "/community/delete",
        type: "post",
        data: {uploadPath: uploadPath, fileName: fileName},
        success: function(){
            $("#thumbnailImage").remove();
            $(".text-center.container.thumbnailPlus").show();
        }
    });
});

let thumbnailCheck = 0;

$(document).ready(function(){
    if($('input[name=communityFilePath]').val()){
        thumbnailCheck++;
        if(thumbnailCheck<=1){
            let imageSrc = "/community/display?fileName=" + $('input[name=communityFilePath]').val() + "/" + $('input[name=communityFileUuid]').val() + "_" + $('input[name=communityFileName]').val();

            $('.image-header').show();
            $('.img-box').show();
            let text = "";
            text += `<li id="thumbnailImage" data-file-size="` + $('input[name=communityFileSize]').val() + `" data-file-name="` + $('input[name=communityFileName]').val() + `" data-file-upload-path="` +$('input[name=communityFilePath]').val() + `" data-file-uuid="` + $('input[name=communityFileUuid]').val() + `" style="list-style: none;width:100%;">`;
            text += `<img src=` + imageSrc + ` style="width:100%;" height="auto">`;
            text += `</li>`;

            $(".imgInputBox").append(text);
            $(".text-center.container.thumbnailPlus").hide();
        }
    }
})
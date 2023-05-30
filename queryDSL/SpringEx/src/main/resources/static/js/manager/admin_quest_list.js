let Target = document.getElementById("clock");
let temp = 0;
// html dom 이 다 로딩된 후 실행된다.
$(document).ready(function () {
    // menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
    $("#side_menubar>li").click(function () {
        var submenu = $(this).children("ul");


        // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
        if (submenu.is(":visible")) {
            submenu.slideUp();
        } else if (!submenu) {
        } else {
            submenu.slideDown();

        }
    });
});




function clock() {
    var time = new Date();

    var month = time.getMonth();
    var date = time.getDate();
    var day = time.getDay();
    var week = ['일', '월', '화', '수', '목', '금', '토'];

    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();

    Target.innerText =
        `${month + 1}월 ${date}일 ${week[day]}요일 ` +
        `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds}`;

}

clock();
setInterval(clock, 1000); // 1초마다

$('#quest-register-wrapper').hide()

$('.filter span').click(function (){
    if(temp === 0) {
        temp++;
        $('#quest-register-wrapper').show();
        checkModifying = false;
    }else{
        temp--;
        $('#quest-register-wrapper').hide();

    }

})

let moreButtonTemp = 0;
let $tr = $(".info_table").find('tr');
console.log($tr)
console.log($tr.find('.more'))

function modifyQuest(tag){
    if (moreButtonTemp === 1) {
        moreButtonTemp--;
        $(tag).next().remove();
    } else {
        checkModifying = true;
        checkUpload = false;
        moreButtonTemp++;
        //수정 모달 버튼 띄우기
        $(tag).parent().append("<div class=\"account-modal\">\n" +
            "    <button class=\"account-modal-button\">수정</button>\n" +
            "</div>")

        //수정 모달 버튼 클릭시
        $(tag).next().find('.account-modal-button').click(function () {
            //상단에 수정창 만들기
            $('#quest-register-wrapper').show();
            //수정창에 수정할 정보 넣기
            // $('.register td:nth-child(1)').attr("value", $(this).closest('tr').children('td:eq(1)').text())
            $('.register td:nth-child(1) input').val($(tag).closest('tr').children('.questId').text())
            $('.register td:nth-child(2) input').val($(tag).closest('tr').children('td:eq(1)').text())
            $('.register td:nth-child(3) input').val($(tag).closest('tr').children('td:eq(2)').text())
            $('.register td:nth-child(4) input').val($(tag).closest('tr').children('td:eq(3)').text())
            $('.register td:nth-child(5) input').val($(tag).closest('tr').children('td:eq(4)').text())
            $('.register td:nth-child(6) input').val($(tag).closest('tr').children('td:eq(5)').text())
            $('.register td:nth-child(7) input').val($(tag).closest('tr').children('td:eq(6)').text())
            $('#preview').attr('src', $(this).closest('tr').children('td:eq(7)').find('img').attr('src'))
            //수정 모달창과 버튼 삭제
            $(tag).next().remove();
            moreButtonTemp--;

        })
    }
}

$('.close').click(function (){
    $('#quest-register-wrapper').hide();
    $('.register td:nth-child(1) input').val('')
    $('.register td:nth-child(2) input').val('')
    $('.register td:nth-child(3) input').val('')
    $('.register td:nth-child(4) input').val('')
    $('.register td:nth-child(5) input').val('')
    $('.register td:nth-child(6) input').val('')
    $('.register td:nth-child(7) input').val('')
    $('#preview').attr('src', '/imgin/admin_file.png')
    if(temp === 1){
        temp--;}else if (moreButtonTemp === 1){
        moreButtonTemp--;
    }
})

let questName = $('.questName').val();
let questCategory = $('.questCategory').val();
let questType = $('.questType').val();
let questDeadLine = $('.questDeadLine').val();
let questContent = $('.questContent').val();
let questPoint = $('.questPoint').val();
let questFileName;
let questFilePath;
let questFileUuid;
let questFileSize;
let questId;
let checkUpload = false;
let checkModifying = false;


$('.regist').click(function (){
    $('#__BVID__287___BV_modal_outer_').css('display', '');

    questId = $('.register td:nth-child(1) input').val();
    questName = $('.register td:nth-child(2) input').val();
    questCategory = $('.register td:nth-child(3) input').val();
    questType = $('.register td:nth-child(4) input').val();
    questDeadLine = $('.register td:nth-child(5) input').val();
    questContent = $('.register td:nth-child(6) input').val();
    questPoint = $('.register td:nth-child(7) input').val();

    console.log(checkModifying);
    if(!checkModifying){
        questService.add({
            questId : questId,
            questName : questName,
            questCategory : questCategory,
            questType : questType,
            questDeadLine : questDeadLine,
            questContent : questContent,
            questPoint : questPoint,
            questFileName : questFileName,
            questFilePath : questFilePath,
            questFileUuid : questFileUuid,
            questFileSize : questFileSize
        }, function (){show();});
    }else{
        questService.update({
            questId : questId,
            questName : questName,
            questCategory : questCategory,
            questType : questType,
            questDeadLine : questDeadLine,
            questContent : questContent,
            questPoint : questPoint,
            questFileName : questFileName,
            questFilePath : questFilePath,
            questFileUuid : questFileUuid,
            questFileSize : questFileSize
        }, function (){show();});
    }

    $('#quest-register-wrapper').hide();
    $('.register td:nth-child(1) input').val('')
    $('.register td:nth-child(2) input').val('')
    $('.register td:nth-child(3) input').val('')
    $('.register td:nth-child(4) input').val('')
    $('.register td:nth-child(5) input').val('')
    $('.register td:nth-child(6) input').val('')
    $('.register td:nth-child(7) input').val('')

    $('#preview').attr('src', '/imgin/admin_file.png')
    if(temp === 1){
        temp--;}else if (moreButtonTemp === 1){
        moreButtonTemp--;
    }

})


$('.saveRequest').on('click', function (){
    $('#__BVID__287___BV_modal_outer_').css('display', 'none');
})



// 첨부파일 이미지 클릭시 파일 찾기 창 열림
function onClickUpload(){
    let fileInput = $('input[type = file]')
    fileInput.click();

}


// 뱃지 이미지 썸네일 띄우기
$(".badge").on("change", function(event) {
    // let file = event.target.files[0];
    // let reader = new FileReader();
    // reader.onload = function(e) {
    //     $("#preview").attr("src", e.target.result);
    // }
    // reader.readAsDataURL(file);
    let arrayFile =[];

    let formData = new FormData();
    let $inputFile = $('input[type = file]');
    let files = $inputFile[0].files;

    Array.from(files).forEach(file => arrayFile.push(file));
    const dataTransfer = new DataTransfer();

    arrayFile.forEach(file => dataTransfer.items.add(file));
    $(this)[0].files = dataTransfer.files;

    $(files).each(function(i, file){
        formData.append("upload", file);
    });

    $.ajax({
        url: "/quest/uploadFile",
        type: "post",
        data: formData,
        processData : false,
        contentType: false,
        async : false,
        success: function(questDTO){
            questFileName = questDTO.questFileName;
            questFilePath = questDTO.questFilePath;
            questFileUuid = questDTO.questFileUuid;
            questFileSize = questDTO.questFileSize;
            let imageSrc = "/quest/display?fileName=" + questFilePath + "/" + questFileUuid + "_" + questFileName;
            console.log(questFileName);
            console.log(questFilePath);
            console.log(questFileUuid);
            console.log(questFileSize);

            $("#preview").attr("src", imageSrc);
            checkUpload = true;
        }
    });


});

//모듈
let questService = (function(){
    function getList(param, callback, error){
        $.ajax({
            url: encodeURI("/quest/" + (param.page || 0) + "/" + param.keyword),
            type: "get",
            async : false,
            success: function(questDTO, status, xhr){
                if(callback){
                    callback(questDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function update(quest, callback, error){
        $.ajax({
            url: "/quest/update",
            type: "patch",
            data: JSON.stringify(quest),
            contentType: "application/json; charset=utf-8",
            async : false,
            success: function(text){
                if(callback){
                    callback(text);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function add(quest,callback, error){
        $.ajax({
            url: "/quest/upload",
            type: "post",
            data: JSON.stringify(quest),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr){
                if(result){
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
    return {getList: getList,update: update, add : add}
}) ();
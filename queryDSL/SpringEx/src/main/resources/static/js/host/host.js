/* host.html */

// 탭에 따라 변경하는 부분들
$('#__BVID__287___BV_modal_outer_').css('display', '');
$('.place').css('display', '');
$('.image-header').css('display', '');
$('.img-box').css('display', '');
$('.explain').css('display', '');
$('.noticeEx').css('display', '');




const date = new Date();

// 캘린더에 들어갈 데이터
let data = [];

// $(document).ready(function(){
//     showList();
// })

displayCalendar();

function displayCalendar(){
    console.log($(".text-sm")[0].innerText);
    if($(".text-sm")[0].innerText == '신규등록'){
        console.log("==========================")
        $("#calendarWrap").hide();
    } else{
        $("#calendarWrap").show();
    }
}


// 모임 리스트에 저장 후 groupScheduleDTO에 모임의 날짜를 각각 저장해준다.
// 이후 콜백함수를 통해 리스트를 출력
function listDate(groupScheduleDTO){
    displayCalendar();
    data = [];
    groupScheduleDTO.forEach(schedule => {
        let simpleDate = schedule.groupScheduleStartTime.split("T")[0];
        let simpleTime = schedule.groupScheduleStartTime.split("T")[1].split(":");
        let startTime = simpleTime[0] + ':' + simpleTime[1];

        data.push({
            date : simpleDate,
            startTime : startTime
        });
    });
    makeCalendar(date);
}


// pad method ( 2 -> 02 )
Number.prototype.pad = function() {
    return this > 9 ? this : '0' + this;
}


const makeCalendar = (date) => {

    const calendarList = data.reduce(
        (acc, v) =>
            ({ ...acc, [v.date]: [...(acc[v.date] || []), v.startTime] })
        , {}
    );
    const currentYear = new Date(date).getFullYear();
    const currentMonth = new Date(date).getMonth() + 1;

    const firstDay = new Date(currentYear, currentMonth - 1, 1).getDay();
    const lastDay = new Date(currentYear, currentMonth, 0).getDate();

    const limitDay = firstDay + lastDay;
    const nextDay = Math.ceil(limitDay / 7) * 7;

    let htmlDummy = '';
    let count = 0;

    htmlDummy += `<tr data-v-1e397a0c="" class="week">`;
    for (let i = 0; i < firstDay; i++) {
        htmlDummy += `<td data-v-1e397a0c="">`;
        htmlDummy += `<div data-v-3655f65c="" data-v-1e397a0c="" class="day">`;
        htmlDummy += `<div data-v-3655f65c="" class="clearfix px-2 pt-2">`;
        htmlDummy += `<div data-v-3655f65c="" class="float-left">`;
        htmlDummy += `<div data-v-3655f65c="" class="day-btn" onclick="addSchedule();">+</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `<div data-v-3655f65c="" class="text-gray float-right">`;
        htmlDummy += `</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `<div data-v-3655f65c="" class="px-2"></div>`;
        htmlDummy += `</div>`;
        htmlDummy += `</td>`;
        count = i+1;
    }



    for (let i = 1; i <= lastDay; i++) {
        const date = `${currentYear}-${currentMonth.pad()}-${i.pad()}`;
        if (count % 7 === 0) {
            count = 0;
            htmlDummy += `<tr data-v-1e397a0c="" class="week">`;
        }
        htmlDummy += `<td data-v-1e397a0c="">`;
        htmlDummy += `<div data-v-3655f65c="" data-v-1e397a0c="" class="day">`;
        htmlDummy += `<div data-v-3655f65c="" class="clearfix px-2 pt-2">`;
        htmlDummy += `<div data-v-3655f65c="" class="float-left">`;
        htmlDummy += `<div data-v-3655f65c="" class="day-btn" onclick="addSchedule();" data-date="${date}">+</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `<div data-v-3655f65c="" class="text-gray float-right">`;
        htmlDummy += `<!----> ${i}일`;
        htmlDummy += `</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `<div data-v-3655f65c="" class="px-2">`;

        if(calendarList[date]){
            htmlDummy += `<div data-v-3655f65c="" class="bg-frip-primary-50 text-frip-primary rounded p-1 my-2 wg-100" style="cursor: pointer;">`;
            htmlDummy += `<div class="clearfix">`;
            htmlDummy += `<div class="float-left">${calendarList[date]}</div>`;
            htmlDummy += `<div class="float-right">  </div>`;
            htmlDummy += `</div>`;
            htmlDummy += `</div>`;
        }
        htmlDummy += `</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `</td>`;
        if (count % 7 === 0 && count !== 0) {
            htmlDummy += `</tr>`;
        }

        count++
    }
    for (let i = limitDay; i < nextDay; i++) {
        htmlDummy += `<td data-v-1e397a0c="">`;
        htmlDummy += `<div data-v-3655f65c="" data-v-1e397a0c="" class="day">`;
        htmlDummy += `<div data-v-3655f65c="" class="clearfix px-2 pt-2">`;
        htmlDummy += `<div data-v-3655f65c="" class="float-left">`;
        htmlDummy += `<div data-v-3655f65c="" class="day-btn" onclick="addSchedule();">+</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `<div data-v-3655f65c="" class="text-gray float-right">`;
        htmlDummy += `</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `</div>`;
        htmlDummy += `<div data-v-3655f65c="" class="px-2"></div>`;
        htmlDummy += `</div>`;
        htmlDummy += `</td>`;
    }

    document.querySelector(`.calendar`).innerHTML = htmlDummy;
    document.querySelector(`.dateTitle`).innerText = `${currentYear}년 ${currentMonth}월`;
}


// 이전달 이동
$(`.prevDay`).on('click',function(){
    makeCalendar(date);
    makeCalendar(new Date(date.setMonth(date.getMonth() - 1)));
});


// 다음달 이동
$(`.nextDay`).on('click', function(){
    makeCalendar(date);
    makeCalendar(new Date(date.setMonth(date.getMonth() + 1)));
});

//일정 등록 날짜
$('input[type=date]').on('change', function(){
    new Date(this.value) < date ? $('.dateNotice').css('display', ''): $('.dateNotice').css('display', 'none');
});

//신청가능 날짜
$('input[type=number].period').bind('keyup mouseup', function (){
    let selDate = new Date($('input[type=date]').val());
    console.log(selDate);
    selDate = new Date(selDate.setDate(selDate.getDate()-this.value));
    $('.periodNotice').text(`${selDate.getFullYear()}년 ${selDate.getMonth()+1}월 ${selDate.getDate()}일까지 모임을 신청할 수 있습니다.`);
    console.log($('.periodNotice').text());
});


// 기본정보/프립설명 탭 이동
$(".nav-item a").on('click', function(e){
    e.preventDefault();
    if ($(this).attr('id') == '__BVID__562___BV_tab_button__'){
        $('.basic-info').show();
        $('.noticeInfo').show();
        $('.explain').hide();
        $('.noticeEx').hide();
    }else if($(this).attr('id') == '__BVID__696___BV_tab_button__'){
        $('.explain').show();
        $('.noticeEx').show();
        $('.noticeInfo').hide();
        $('.basic-info').hide();
    }

    if($('.nav-item a').attr('aria-selected')){
        $('.nav-item a').attr('aria-selected', 'false');
        $('.nav-item a').attr('class', 'nav-link');
        $('.nav-item a').attr('tabindex', '-1');
    }
    if(!$(this).attr('aira-selected')){
        $(this).attr('aria-selected', 'ture');
        $(this).attr('class', 'nav-link active active-tab');
        $(this).attr('tabindex', '');
    }
});

// focus됐을 때 border 보라색으로 변경
$('.input-group .form-control').on('focus', function(){
    $(this).next().children('span').css('border-color', '#83aad1');
    $(this).css('border-color', '#83aad1');
});

//주소 찾기 버튼클릭 시
$('.findBtn').on('click', function(){
    find();
});

//다음 주소찾기 서비스 실행
function find(){
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("placeAddress").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("placeAddress").focus();
        }
    }).open();
}


// 글자 수 세기
$('.input-group input[type=text].form-control').on('input', function(){
    $(this).next().children('span').children('small').text($(this).val().length + ' / 40');
});

// 인풋태그 블러 이벤트
$('.input-group .form-control').on('blur', function(){
    $(this).next().children('span').css('border-color', '');
    $(this).css('border-color', '');
    if($(this).attr('class') == 'number1 form-control' || $(this).attr('class') == 'number2 form-control'){
        return;
    }
    if(!$(this).val()){
        $(this).attr('class', 'form-control is-invalid');
        $(this).next().children('span').attr('class', 'input-group-text is-invalid');
        $(this).closest('.input-group').next().css('display','block');
    }else{
        $(this).attr('class', 'form-control');
        $(this).next().children('span').attr('class', 'input-group-text');
        $(this).closest('.input-group').next().css('display','none');
    }
});

// 프립 유형 선택
$('.selectBox.mx-2').on('click', function(){
    $('.selectBox.mx-2').attr('class', 'selectBox mx-2');
    if($(this).attr('class') != 'selectBox mx-2 selected'){
        $(this).attr('class', 'selectBox mx-2 selected');
        ($(this).attr('data-type') == 'offline'? $('.place').show() : $('.place').hide());
    }
});

$(document).ready(
    showPlaceDetail()
)

function showPlaceDetail(){
    if($("#offline").attr("class") == "selectBox mx-2 selected"){
        $(".place").show();
    }
}

$("input[name='groupPoint']").blur(function(){
    if($("input[name='groupPoint']").val()>10000 || $("input[name='groupPoint']").val() <99){
        $(".text-muted.text-sm.caption").text("포인트는 100원 이상, 10,000원 미만으로 설정해주세요.");
        $("input[name='groupPoint']").focus();
        return;
    }
})


// 모달창 닫기
function closeModal(){
    $('#__BVID__287___BV_modal_outer_').hide();
}


// 진행 장소 추가 버튼 클릭 -> 모달창 열기
function findPlace(){
    if($("input[name='groupName']").val()=="" || $("input[name='groupCategory']").val()==""){
        $(".siteMessage").text("모임명과 카테고리를 먼저 입력해주세요.");
        return;
    }
    $('#__BVID__287___BV_modal_outer_').show();
    $('#__BVID__287___BV_modal_content_').show();
    $('#__BVID__1216___BV_modal_content_').hide();
    $('#__BVID__21___BV_modal_content_').hide();
    $('#__BVID__123___BV_modal_content_').hide();
}

// 장소 추가 유효성 검사
$(".createPlace.btn.frip-button.btn-frip-primary.btn-tab").on("click", function(){
    if($("input[name='locationName']").val()==""){
        $(".mx-2.alertMessage").text("장소명을 입력해주세요.");

        $("input[name='locationName']").focus();
        return;
    }

    if($("input[id='placeAddress']").val()==""){
        $(".mx-2.alertMessage").text("주소를 입력해주세요.");
        $("input[id='placeAddress']").focus();
        return;
    }

    if($("input[id='locationDetail']").val()==""){
        $(".mx-2.alertMessage").text("상세 주소를 입력해주세요.");
        $("input[id='placeAddress']").focus();
        return;
    }

    closeModal();
})

let checkedLogion = false;
//국내 || 해외 선택
$('.countryOption .custom-radio').on('click', function(){
    checkedLogion = true;
    $('.countryOption .custom-radio').attr('value', 'false');
    $(this).attr('value', 'true');
    if($(this).attr('data-value') != 'country'){
        $('.findBtn').hide();
    }else{
        $('.findBtn').show();
    }
});

//장소 등록 삭제
$('.clearPlace').on('click', function(){
    $('.placeTableBtn').hide();
    $('.my-2.placeTable').hide();
    $('.findPlace').show();
});

// 편집 진행 시 장소 테이블에 나오도록 진행
$(function(){
    if($(".text-sm")[0].innerText != "신규등록"){
        if($('input[data-v-72dffd28]').eq(14).val() != "" && $('#placeAddress').val() != ""){
            $('.placeTable .placeName').text($("input[name='locationName']").val());
            $('.placeTable .placeAddr').text($('#placeAddress').val());
            $('.my-2.placeTable').css("display", "block");
            $('.placeTableBtn').css("display", "block");
            $('.findPlace').hide();
            $(".place").show();
        }
    }
})

// 진행 장소 등록 버튼 클릭 이벤트막기
$('.createPlace').on('click', function(e){
    if($('input[data-v-72dffd28]').eq(12).val() == ""){
        $('input[data-v-72dffd28]').eq(12).blur();
    }

    if($('#placeAddress').val() == ""){
        e.preventDefault();
        $('#placeAddress').blur();
    }

    if($('input[data-v-72dffd28]').eq(14).val() != "" && $('#placeAddress').val() != ""){
        e.preventDefault();
        $('.placeTable .placeName').text($("input[name='locationName']").val());
        $('.placeTable .placeAddr').text($('#placeAddress').val());
        $('.my-2.placeTable').show();
        $('.placeTableBtn').show();
        $('.findPlace').hide();

    }
});

$('#placeAddress').on('blur', function(){
    if(!$(this).val()){
        $(this).attr('class', 'form-control is-invalid');
    }else{
        $(this).attr('class', 'form-control');
    }
});



// 일정 추가 버튼 클릭 -> 모달창 열기
function addSchedule() {
    let dateCheck = false;
    // 일정 추가 버튼 유효성 검사
    if ($(".cancelRecruitment").css("display") == "none") {
        $("p.alertMsg").text("일정 설정은 저장 후 가능합니다.");
        $("p.alertMsg").css("display", "block");
        return;
    }

    $(".table").on("click", ".day-btn", function(){
        if($(this).parent().parent().next().children().text()){
            let scheduleDate = $(this).attr("data-date");
            let groupId = $('input[name=groupId]').val();
            // 삭제
            $.ajax({
                url: "/host/delete-schedule/" + groupId + "/" + scheduleDate,
                type: "post",
                async: false,
                success: function(){
                    showList();
                }
            });
        } else{

            // 이미 지난 날짜인지 비교
            let clickDate = $(".mx-3.dateTitle").text() + $(this).parent().next().text() + "00:00:00";

            let year = clickDate.split("년")[0];
            let month = clickDate.split("년")[1].split("월")[0];
            let day = clickDate.split("년")[1].split("월")[1].split("일")[0];

            let concatenateDate = year + "-" + month.trim() + "-" + day.trim() + " 00:00:00";
            let checkDate = new Date(concatenateDate);

            let presentDate = new Date();
            presentDate = presentDate.getFullYear() + "-" + (presentDate.getMonth()+1) + "-" + presentDate.getDate() + " 00:00:00";
            let presentDateTypeChange = new Date(presentDate).getTime();

            if(presentDateTypeChange - checkDate.getTime()+1 >0){
                $("p.alertMsg").text("오늘 또는 지난 날짜는 선택하실 수 없습니다.");
                $("p.alertMsg").css("display", "block");
                return;
            }

            $('#__BVID__287___BV_modal_outer_').show();
            $('#__BVID__287___BV_modal_content_').hide();
            $('#__BVID__21___BV_modal_content_').hide();
            $('#__BVID__123___BV_modal_content_').hide();
            $('#__BVID__1216___BV_modal_content_').show();

            $('input[type=date]').val($(this).attr('data-date'));
            let selDate = new Date($('input[type=date]').val());
            selDate < date ? $('.dateNotice').css('display', '') : $('.dateNotice').css('display', 'none');
            if ($('input[type=date]').val()) {
                $('.periodNotice').text(`${selDate.getFullYear()}년 ${selDate.getMonth() + 1}월 ${selDate.getDate()}일까지 모임을 신청할 수 있습니다.`);
            } else {
                $('.periodNotice').text(`날짜를 입력해주세요`);
            }
        }
    })
}

//일정 시간 유효성 검사
$('.openTime').on('change', function (){
    if(!$('.closeTime').val()){return;}
    let open = $('.openTime').val().split(':');
    let close = $('.closeTime').val().split(':');
    let openTime = new Date();
    let closeTime = new Date();


    openTime.setHours(open[0]);
    openTime.setMinutes(open[1]);
    closeTime.setHours(close[0]);
    closeTime.setMinutes(close[1]);

    let differ = (closeTime.getTime() - openTime.getTime()) / (1000*60*60)


    if(openTime > closeTime){
        console.log('dd');
        $('.timeNotice').text('종료시간은 시작시간보다 빠를 수 없습니다.');
        $('.timeNotice').css('display', '');
    }else if(differ > 5){
        $('.timeNotice').text('모임시간은 최대 5시간을 초과할 수 없습니다.');
        $('.timeNotice').css('display', '');
    }else{
        $('.timeNotice').css('display', 'none');
    }
});




//일정 등록
$('.createPlan').on('click', function(){
    //이거 백엔드에서 DB일정 등록하시고 rest로 달력이랑 같이 로딩하시면 됨,

    // 날짜 값이 비어있으면 날짜로 포커싱
    if(!$('input[type=date]').val()){
        $(".timeNotice.mt-2.text-sm.text-frip-danger").text("날짜를 입력해주세요.");
        $(".timeNotice.mt-2.text-sm.text-frip-danger").css("display", "block");
        $('input[type=date]').focus();
        return;
    }

    // 현재 날짜보다 진행하고자 하는 날짜가 더 빠르면 날짜로 포커싱
    let pickDate = new Date($('input[type=date]').val() + " 00:00:00").getTime();
    let presentDate = new Date();
    presentDate = presentDate.getFullYear() + "-" + (presentDate.getMonth()+1) + "-" + presentDate.getDate() + " 00:00:00";
    let presentDateTypeChange = new Date(presentDate).getTime();
    console.log(presentDateTypeChange);
    console.log(pickDate);
    if((presentDateTypeChange+10) - pickDate >0){
        $(".timeNotice.mt-2.text-sm.text-frip-danger").text("오늘 또는 지난 날을 선택할 수 없습니다.");
        $(".timeNotice.mt-2.text-sm.text-frip-danger").css("display", "block");
        $('input[type=date]').focus();
        return;
    }


    // 시작 시간이나 끝나는 시간이 비어있으면 시작 시간 칸으로 포커싱
    if(!$('.openTime').val() || !$('.closeTime').val()){
        $(".timeNotice.mt-2.text-sm.text-frip-danger").text("시작 시간 또는 종료 시간을 입력해주세요.");
        $(".timeNotice.mt-2.text-sm.text-frip-danger").css("display", "block");
        $('.openTime').focus();
        return;
    }

    // 시작 시간이 끝나는 시간보다 더 늦으면 시작 시간으로 포커싱
    if($(".openTime").val() >= $(".closeTime").val()){
        $(".timeNotice.mt-2.text-sm.text-frip-danger").text("종료시간은 시작시간보다 빠를 수 없습니다.");
        $(".timeNotice.mt-2.text-sm.text-frip-danger").css("display", "block");
        $('.openTime').focus();
        return;
    }


    $('#__BVID__287___BV_modal_outer_').hide();
    $('#__BVID__1216___BV_modal_content_').hide();

    //rest로 일정 등록해서 집어 넣을 공간
    groupSave.addDate({
        groupScheduleDate :new Date($('input[type=date]').val()),
        groupScheduleStartTime : new Date($('input[type=date]').val() + " " + $('.openTime').val() + ":00"),
        groupScheduleEndTime : new Date($('input[type=date]').val() + " " + $('.closeTime').val() + ":00")
    }, showList);
});



// 주차장 옵션 버튼 눌렀을때, 주차옵션이 있다면 메모를 할 수 있게함
$('#parkingOption .custom-radio').on('click', function(){
    if($(this).children('input').attr('id') != 'parkingOption_BV_option_0'){
        $('#parkingOption_BV_option_0').val('false')
        $('#textarea').val('');
        $('#textarea').prop('disabled', true);
    }else{
        $('#parkingOption_BV_option_0').val('true')
        $('#textarea').prop('disabled', false);
    }
});

// 옵션 선택 박스 선택시
$('.col-lg-5.selectBox').on('click', function(){
    if($(this).attr('class') == 'col-lg-5 selectBox selected'){
        $(this).attr('class', 'col-lg-5 selectBox');
    }
    $(this).attr('class', 'col-lg-5 selectBox selected');
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
        url: "/host/thumbnail",
        type: "post",
        data: formData,
        contentType: false,
        processData: false,
        success: function(result) {
            $('input[name=groupFileName]').attr('value', Object.values(result[0])[20]);
            $('input[name=groupFilePath]').attr('value', Object.values(result[0])[19]);
            $('input[name=groupFileSize]').attr('value', Object.values(result[0])[22]);
            $('input[name=groupFileUuid]').attr('value', Object.values(result[0])[21]);
            let imageSrc = "/host/display?fileName=" + Object.values(result[0])[19] + "/" + Object.values(result[0])[21] + "_" + Object.values(result[0])[20];

            if($('input[name=groupFileSize]').val()>100000){
                $(".thumbMsg").text("파일 사이즈는 10MB 이하여야 합니다.");
                return;
            }

            $('.image-header').show();
            $('.img-box').show();

            let text = "";
            text += `<li id="thumbnailImage" data-file-size="` + Object.values(result[0])[22] + `" data-file-name="` + Object.values(result[0])[20] + `" data-file-upload-path="` + Object.values(result[0])[19] + `" data-file-uuid="` + Object.values(result[0])[21] + `" style="list-style: none;width:100%;">`;
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
        url: "/host/delete",
        type: "post",
        data: {uploadPath: uploadPath, fileName: fileName},
        success: function(){
            $("#thumbnailImage").remove();
            $(".text-center.container.thumbnailPlus").show();
        }
    });
});

//검수 요청 버튼
$('.fixed-bottom .frip-button').on('click', function(){

    if($('.image-header').css('display') == 'none'){
        $(".mx-2.alertMessage").text('이미지를 등록해주세요');
        $('#__BVID__287___BV_modal_outer_').hide();
        return;
    }

    if($("input[name='groupPoint']")>10000 || !$("input[name='groupPoint']")){
        $(".mx-2.alertMessage").text("금액을 10,000원 이하로 기재해주세요.");
        $("input[name='groupPoint']").focus();
        return;
    }

    if($('input[name=groupContent]').val() === ''){
        $(".mx-2.alertMessage").text('모임 설명을 확인해주세요');
        $('#__BVID__287___BV_modal_outer_').hide();
        return;
    }

    if($('.cancelRecruitment').css('display') == 'none'){
        $(".mx-2.alertMessage").text('좌측 저장버튼을 먼저 눌러주세요');
        $('#__BVID__287___BV_modal_outer_').hide();
        return;
    }


    if($('.number1').val() == '' || $('.number2').val() == '' || $('.recruitment').next().css('display') != 'none'){
        $(".mx-2.alertMessage").text('모집인원 항목을 확인해주세요');
        $('#__BVID__287___BV_modal_outer_').hide();
        return;
    }

    if(!$('.selectBox.mx-2.selected').attr('data-type')){
        $(".mx-2.alertMessage").text('모임 유형을 선택해주세요');
        $('#__BVID__287___BV_modal_outer_').hide();
        return;
    }

    //장소등록 안되있으면 return
    if($('.selectBox.mx-2.selected').attr('data-type') == 'offline'){
        if($('.my-2.placeTable').css('display')=='none'){
            $(".mx-2.alertMessage").text("장소를 등록해주세요.");
            return;
        }
    }

    //스케줄 등록이 안되어있으면 return
    if(data.length == 0){
        $(".mx-2.alertMessage").text("일정을 하루 이상 등록해주세요.");
        return;
    }

    $('#__BVID__287___BV_modal_outer_').show();
    $('#__BVID__287___BV_modal_content_').hide();
    $('#__BVID__21___BV_modal_content_').show();
    $('#__BVID__1216___BV_modal_content_').hide();
    $('#__BVID__123___BV_modal_content_').hide();
});

//검수 요청 확인
$('.checkRequest').on('click', function (){

    $('#__BVID__287___BV_modal_outer_').hide();

//  모임 멤버 테이블 생성
    let groupId = $('input[name=groupId]').val();
    $.ajax({
        url: "/host/group-member/" + groupId,
        type: "post",
        async: false,
        success: function(result, status, xhr) {
            result == true? console.log('검수 요청이 완료되었습니다') : $(".mx-2.alertMessage").text("잔여 포인트가 부족하여 모임 등록이 불가합니다.");
        },
        error: function(xhr, status, err){
            if(error){
                error(err);
            }
        }
    });

    // 모임 등록 최상단 부분 문구 변경
    $(".text-muted.font-weight-bold").text("승인 대기중");


//    승인대기 상태로 바꾸기
//    업데이트
    groupUpdate();

    location.href = "/host/group-list";

});

//REST 방식으로 저장하기
let groupSave = (function(){
    function add(groupContents, callback, error){
        $.ajax({
            url: "/host/index",
            type: "post",
            async: false,
            data: JSON.stringify(groupContents),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr) {
                    callback(result);
            },
            error: function(xhr, status, err){
                console.log(xhr, status, err);
                if(error){
                    error(err);
                }
            }
        });
    }

    function update(groupContents, callback, error){
        $.ajax({
            url: "/host/update",
            type: "post",
            data: JSON.stringify(groupContents),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr) {
                if (callback) {
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

    function updateStatus(groupContents, callback, error){
        $.ajax({
            url: "/host/update-status",
            type: "post",
            data: JSON.stringify(groupContents),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr) {
                if (callback) {
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

    function addDate(groupContents, callback, error){
        let groupId = $('input[name=groupId]').val();
        let groupScheduleStartTime = new Date($('input[type=date]').val() + " " + $('.openTime').val() + ":00");
        let groupScheduleEndTime = new Date($('input[type=date]').val() + " " + $('.closeTime').val() + ":00");
        $.ajax({
            url: "/host/addDate/" + groupId + "/" + groupScheduleStartTime + "/" + groupScheduleEndTime,
            type: "post",
            data: JSON.stringify(groupContents),
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

    function listDate(param, callback, error){

        let groupId = $('input[name=groupId]').val();
        $.ajax({
            url: "/host/date-list/" + groupId,
            type: "get",
            success: function(groupScheduleDTO, status, xhr){
                if(callback){
                    console.log("success");
                    callback(groupScheduleDTO);
                }
            },
            error: function(xhr, status, err){
                if(error){
                    console.log("error");
                    error(err);
                }
            }
        });
    }

    return {add: add, update: update, updateStatus: updateStatus, addDate: addDate, listDate: listDate}
})();

// 모임 페이지 삭제
$(".goDelete").on("click", function(e){
    e.preventDefault();
    location.href ="/host/deleteGroup?groupId=" + $(this).attr("href");
})

//임시 저장 버튼
$('.saveRecruitment').on('click', function (){

    // 작성 내용 유효성검사
    if($(".note-editable").text().length>10000 || $(".note-editable").text().length==0){
        $(".mx-2.alertMessage").text("글자는 1자 이상, 10,000자 이내로 작성 가능합니다.");
        return;
    }
    //스케줄 등록이 안되어있으면 return
    $(".mx-2.alertMessage").text("임시 저장 완료되었습니다.");

    // 일정 추가 버튼 생성
    $(".btn.frip-button.btn-outline-frip-primary").eq(1).show();
    $('.cancelRecruitment').show();
    $('#__BVID__287___BV_modal_outer_').show();
    $('#__BVID__287___BV_modal_content_').hide();
    $('#__BVID__21___BV_modal_content_').hide();
    $('#__BVID__1216___BV_modal_content_').hide();
    $('#__BVID__123___BV_modal_content_').show();
});

/*그룹 아이디값 지정*/
$('input[name=groupId]').attr('value', $("#__BVID__579 .text-sm").text());

//임시 저장 확인
$('.saveRequest').on('click', function (e){
    e.preventDefault();
    $('#__BVID__287___BV_modal_outer_').hide();

    //groupLocationType 설정
    if($(".selected .p-3 div")[0].innerText == "오프라인"){
        $('input[name=groupLocationType]').attr('value',"OFFLINE");
    } else{
        $('input[name=groupLocationType]').attr('value',"ONLINE");
    }

    // 장소명
    $('input[name=groupLocationName]').attr('value', $('input[name=locationName]').val());
    // 진행 장소
    $('input[name=groupLocation]').attr('value', $('#placeAddress').val());
    // 진행 장소 상세 내용
    $('input[name=groupLocationDetail]').attr('value', $('#locationDetail').val());
    // 주차 관련 상세 내용
    $('input[name=groupMoreInformation]').attr('value', $('#textarea').val());
    // 주차 가능여부
    if($("#parkingOption_BV_option_0").val()=="true"){
        $('input[name=groupParkingAvailable]').attr('value', "가능");
    } else{
        $('input[name=groupParkingAvailable]').attr('value', "불가");
    }
    // 국내/해외
    if($(".my-2.custom-control.custom-radio").val()==true){
        $('input[name=groupOverSea]').attr('value', "국내");
    } else{
        $('input[name=groupOverSea]').attr('value', "해외");
    }

    //  실제 작성한 내용
    let content = $(".note-editable").html();
    $('input[name=groupContent]').attr('value', content);



    if($('input[name=groupId]').val()=="신규등록"){
        // 컨트롤러로 해당 내용 모두 전송
        groupSave.add({
            groupName : $('input[name=groupName]').val(),
            groupCategory : $('input[name=groupCategory]').val(),
            groupContent :$('input[name=groupContent]').val(),
            groupPoint : $('input[name=groupPoint]').val(),
            groupOverSea : $('input[name=groupOverSea]').val(),
            groupLocationName : $('input[name=groupLocationName]').val(),
            groupLocation : $('input[name=groupLocation]').val(),
            groupLocationDetail : $('input[name=groupLocationDetail]').val(),
            groupParkingAvailable : $('input[name=groupParkingAvailable]').val(),
            groupMoreInformation : $('input[name=groupMoreInformation]').val(),
            groupLocationType : $('input[name=groupLocationType]').val(),
            maxMember : $('input[name=maxMember]').val(),
            minMember : $('input[name=minMember]').val(),
            groupFileName : $('input[name=groupFileName]').val(),
            groupFilePath : $('input[name=groupFilePath]').val(),
            groupFileSize : $('input[name=groupFileSize]').val(),
            groupFileUuid : $('input[name=groupFileUuid]').val()
        }, hostHeader);
    }

    /*게시글 수정일 때*/
    else{
        groupUpdate();
    }
});

function groupUpdate(){
    groupSave.updateStatus({
        groupName : $('input[name=groupName]').val(),
        groupCategory : $('input[name=groupCategory]').val(),
        groupContent :$('input[name=groupContent]').val(),
        groupPoint : $('input[name=groupPoint]').val(),
        groupOverSea : $('input[name=groupOverSea]').val(),
        groupLocationName : $('input[name=groupLocationName]').val(),
        groupLocation : $('input[name=groupLocation]').val(),
        groupLocationDetail : $('input[name=groupLocationDetail]').val(),
        groupParkingAvailable : $('input[name=groupParkingAvailable]').val(),
        groupMoreInformation : $('input[name=groupMoreInformation]').val(),
        groupLocationType : $('input[name=groupLocationType]').val(),
        maxMember : $('input[name=maxMember]').val(),
        minMember : $('input[name=minMember]').val(),
        groupFileName : $('input[name=groupFileName]').val(),
        groupFilePath : $('input[name=groupFilePath]').val(),
        groupFileSize : $('input[name=groupFileSize]').val(),
        groupFileUuid : $('input[name=groupFileUuid]').val(),
        groupId : $('input[name=groupId]').val()
    });
}


//인원 설정 시 유효성검사.
$('.number1').bind('keyup mouseup', function (){
    if($('.number1').val() > $('.number2').val()){
        $('.recruitment').next().css('display', '');
        $('.recruitment').next().show();
    }else{
        $('.recruitment').next().hide();
        $('.recruitment').next().css('display', 'none');
    }
});

$('.number2').bind('keyup mouseup', function (){
    if($('.number1').val() > $('.number2').val()){
        $('.recruitment').next().css('display', '');
        $('.recruitment').next().show();
    }else{
        $('.recruitment').next().hide();
        $('.recruitment').next().css('display', 'none');
    }
});

let thumbnailCheck = 0;

// 수정 진행 시 썸머노트에 기존 내용 출력하기
$(".py-2.px-0.container.ex").on("click", function(){
    thumbnailCheck++;
    if($('input[name=groupFilePath]').val()){
        if(thumbnailCheck<=1){
            let imageSrc = "/host/display?fileName=" + $('input[name=groupFilePath]').val() + "/" + $('input[name=groupFileUuid]').val() + "_" + $('input[name=groupFileName]').val();

            $('.image-header').show();
            $('.img-box').show();
            let text = "";
            text += `<li id="thumbnailImage" data-file-size="` + $('input[name=groupFileSize]').val() + `" data-file-name="` + $('input[name=groupFileName]').val() + `" data-file-upload-path="` +$('input[name=groupFilePath]').val() + `" data-file-uuid="` + $('input[name=groupFileUuid]').val() + `" style="list-style: none;width:100%;">`;
            text += `<img src=` + imageSrc + ` style="width:100%;" height="auto">`;
            text += `</li>`;

            $(".imgInputBox").append(text);
            $(".text-center.container.thumbnailPlus").hide();
        }
    }
})


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
        focus:true,
        lang : "ko-KR",
        callbacks: {
            onImageUpload : function(files){
                sendFile(files[0]);
            }
        }
    });

    /*$('#summernote').summernote('insertText', $('input[name=groupContent]').val());*/
    $(".note-editable").html($('input[name=groupContent]').val());

    $('#summernote').on('summernote.change', function(we, contents, $editable) {
        $('input[name=groupContent]').attr('value', $(".note-editable").html());
    });
}); //ready


function showList(){
    /*모임 상단 헤더에 모임 아이디가 나오기 때문에 거기에 아이디 값을 넣어준다.*/
    groupSave.listDate($(".text-sm")[0].innerText,listDate);
}

// 게시글 임시 저장, 또는 업데이트 시 상단 부분 변경
function hostHeader(groupDTO){
    console.log(groupDTO);
    let groupText = "";
    groupText += `<div><div data-v-59b7de29="" class="row"><div data-v-59b7de29="" class="col"><fieldset data-v-59b7de29="" class="form-group" id="__BVID__579"><div>`;
    groupText += `<label class="form-control-label">ID</label>`;
    if(groupDTO.groupId){
        groupText += `<div class="text-sm">${groupDTO.groupId}</div>`;
    } else{
        groupText =`<div class="text-sm">신규등록</div>`;
    }
    groupText += `</div></fieldset></div><div data-v-59b7de29="" class="col"><fieldset data-v-59b7de29="" class="form-group" id="__BVID__581"><div>`;
    groupText += `<label class="form-control-label">상태</label><div class="text-sm">`;
    if(groupDTO.groupStatus == '승인완료'){
        groupText += `<div data-v-59b7de29="" class="text-frip-primary font-weight-bold">승인 대기중</div>`;
    } else{
        groupText += `<div data-v-59b7de29="" class="text-frip-primary font-weight-bold">등록중</div>`;
    }
    groupText += `</div></div></fieldset></div><div data-v-59b7de29="" class="col"><fieldset data-v-59b7de29="" class="form-group" id="__BVID__583"><div>`;
    groupText += `<label class="form-control-label">검수상태</label>`;
    if(groupDTO.groupStatus == '승인대기'){
        groupText += `<div data-v-59b7de29="" class="text-muted font-weight-bold"> 검수 진행중</div>`;
    } else{
        groupText +=`<div data-v-59b7de29="" class="text-muted font-weight-bold"> 검수 미신청</div></div>`;
    }

    groupText+=`<div class="text-sm">`;
    groupText+= `</div></div></fieldset></div></div></div>`;

    $("#groupHeader").html(groupText);
    $('input[name=groupId]').attr("value", $(".text-sm")[0].innerText);
    showList();
}


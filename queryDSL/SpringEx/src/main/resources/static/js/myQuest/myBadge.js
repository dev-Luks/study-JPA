let filterCheck = false;
//모달창열기


function showModal(questDTO, target){
let text = '';
console.log("target : "+target)
    questDTO.allQuestList.forEach(quest=>{
    if(target.contains(quest.questId.toString())) {
        let questFileName = quest.questFileName;
        let questFilePath = quest.questFilePath;
        let questFileUuid = quest.questFileUuid;
        text += `<div class="BottomSheet__HeaderContainer-e2nchk-3 bXCiMF">`
        text += `<div class="BottomSheet__TitleContainer-e2nchk-4 haGJUN">`
        text += `<strong class="BottomSheet__Title-e2nchk-5 oFqnL">`
        text += quest.questName
        text += `</strong>`
        text += `</div>`
        text += `<button type="button" class="BottomSheet__CloseButton-e2nchk-6 fTfJGA">`
        text += `<img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' fill='none' viewBox='0 0 24 24'%3E %3Cpath fill='%23333' fill-rule='evenodd' d='M18.104 6.707c.39-.39.39-1.024 0-1.414-.39-.39-1.023-.39-1.414 0L11.7 10.284 6.707 5.293c-.39-.39-1.024-.39-1.414 0-.39.39-.39 1.023 0 1.414l4.991 4.992-4.991 4.991c-.39.39-.39 1.024 0 1.414.39.39 1.023.39 1.414 0l4.992-4.991 4.991 4.991c.39.39 1.024.39 1.414 0 .39-.39.39-1.023 0-1.414L13.113 11.7l4.991-4.992z' clip-rule='evenodd'/%3E %3C/svg%3E" alt="close-button"></button>`
        text += `</div>`
        text += `<div class="MonthlyChallenge__ContentContainer-sc-10xmpdd-0 cYNgOe">`
        text += `<div class="MonthlyChallenge__BadgeImageContainer-sc-10xmpdd-1 hkNEqU">`
        text += `<img src="/imgin/myQuest/questBadge/`+questFileName+`" width="80%" height="55%" alt="monthly challenge badge" style="margin: auto;display: block;"alt="monthly challenge badge"></div>`
        text += `<strong class="MonthlyChallenge__BadgeTitle-sc-10xmpdd-2 jZwSMv">`
        text += quest.questName
        text += `</strong>`
        text += `<p class="MonthlyChallenge__BadgeDescription-sc-10xmpdd-3 bOPHSQ">`
        text += quest.questContent
        text += `</p>`
        text += `<div class="MonthlyChallenge__ButtonContainer-sc-10xmpdd-4 iEGbSF">`
        text += `<button width="279" height="56" class="Button__ButtonContainer-sc-1vo4nc0-0 fJydEv">`
        text += `<span class="MonthlyChallenge__ButtonText-sc-10xmpdd-5 gTmuEm"><a href="/myQuest/todayQuest">오늘의 퀘스트 도전!</a></span>`
        text += `</button>`
        text += `</div>`
        text += `</div>`
    }
})
    $(".BottomSheet__Content-e2nchk-2").html(text);
}

//x버튼 눌러서 모달창 닫기
$(".BottomSheet__Content-e2nchk-2").on("click", ".BottomSheet__CloseButton-e2nchk-6", function(){
    $(".BottomSheet__Container-e2nchk-0").css("display", "none");
    filterCheck = false;
})





let myBadgeService = (function(){

    function getBadgeList(callback, error){
        $.ajax({
            url: "/myCompleteQuest/myBadge",
            type: "get",
            success: function (questDTO,status, xhr) {
                if(callback){
                    callback(questDTO);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    function getMonthlyBadgeList(param, callback, error){
        $.ajax({
            url: "/myCompleteQuest/monthlyBadge",
            type: "get",
            success: function (questDTOList,status, xhr) {
                if(callback){
                    console.log(questDTOList);
                    callback(questDTOList);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }
    return {getBadgeList: getBadgeList, getMonthlyBadgeList:getMonthlyBadgeList}
}) ();





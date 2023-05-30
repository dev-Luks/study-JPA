let todayQuestService = (function(){

    function getTodayQuest(callback, error){
        $.ajax({
            url: "/myCompleteQuest/todayQuest",
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


    function addTodayQuestAchievement(error){
        $.ajax({
            url: "/myCompleteQuest/todayQuestAdd",
            type: "get",
            success: function (status, xhr) {
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    function deleteTodayQuestAchievement(error){
        $.ajax({
            url: "/myCompleteQuest/todayQuestDelete",
            type: "get",
            success: function (status, xhr) {
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }


    return {getTodayQuest: getTodayQuest, addTodayQuestAchievement:addTodayQuestAchievement, deleteTodayQuestAchievement:deleteTodayQuestAchievement}
}) ();

// 오늘의 퀘스트 뱃지 눌렀을 때 이미지 색상 변경(이벤트 위임)

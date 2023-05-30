let myQuestService = (function(){

    function getQuestList(param, callback, error){
        $.ajax({
            url: encodeURI("/myCompleteQuest/" + (param.page || 0)),
            type: "get",
            success: function (questDTOList,status, xhr) {
                if(callback){
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
    return {getQuestList: getQuestList}
}) ();
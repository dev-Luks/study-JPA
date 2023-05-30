window.onpageshow = function(event) {
    if ( event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
        location.reload();
    }
}


//모듈
let alarmService = (function(){
    function getList(param, callback, error){
        $.ajax({
            url: "/alarm/" + (param.page || 0),
            type: "get",
            async : false,
            success: function(alarmDTO, status, xhr){
                if(callback){
                    callback(alarmDTO);
                    console.log("성공");
                }
            },
            error: function(xhr, status, err){
                if(error){
                    error(err);
                }
            }
        });
    }

    function update(alarmId, callback, error){
        $.ajax({
            url: "/alarm/update/" + alarmId,
            type: "patch",
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
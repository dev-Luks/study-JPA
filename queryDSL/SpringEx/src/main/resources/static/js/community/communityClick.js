const $heartBtn = $("button.jxmvcs")

let heartImg = 'data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'21\' height=\'18\' viewBox=\'0 0 21 18\'%3E %3Cpath fill=\'%23F32D2D\' d=\'M19.437 1.589C18.427.564 17.085 0 15.657 0s-2.77.564-3.78 1.589L10.5 2.986 9.124 1.59C8.114.564 6.77 0 5.344 0c-1.429 0-2.771.564-3.78 1.589-2.085 2.115-2.085 5.557 0 7.673l8.379 8.504c.148.15.348.234.557.234.209 0 .41-.084.557-.234l8.38-8.504c2.084-2.116 2.084-5.558 0-7.673z\'/%3E %3C/svg%3E';
let emptyImg = `data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='21' height='18' viewBox='0 0 21 18'%3E %3Cpath fill='none' stroke='%234E5968' stroke-width='1.5' d='M15.657.75c-1.226 0-2.379.485-3.246 1.365l-1.91 1.94-1.912-1.94C7.722 1.235 6.57.75 5.343.75s-2.378.485-3.245 1.365C1.198 3.028.75 4.227.75 5.425c0 1.199.448 2.398 1.348 3.31l8.425 8.504 8.379-8.504c.9-.912 1.348-2.111 1.348-3.31 0-1.198-.448-2.397-1.347-3.31-.867-.88-2.02-1.365-3.246-1.365z'/%3E %3C/svg%3E`;
$heartBtn.click(function () {
    var count = parseInt($(this).find("span").text());
    if($(this).find("img").attr("src")==heartImg){
        $(this).find("img").attr("src",emptyImg)
        $(this).find("span").text(count-1)
    }else{
        $(this).find("img").attr("src",heartImg)
        $(this).find("span").text(count+1)
    }
})
//====================================

$mainCategory=$(".ShortcutList__NodeListWrapper-p3h6sa-1.hnuhJm");
console.log($(".BannerWrapper__HomeBannerWrapper-sc-17qp9bv-0"));

$(window).resize(function(){
    if (window.innerWidth < 769) {
        $("#test").attr("class",".ToastContainer__BottomToastWrapper-sqfjqn-0 .boEfRG");

        // 헤더 전체
        $("header").attr("class", ".GlobalNavigationBar__Wrapper-og74wb-0 .bHgsOZ")

        // 헤더 윗 부분 없애기
        $("section.GlobalNavigationBar__TopNavContainer-og74wb-1").hide();

        // 메인 상단 배너 바꾸기
        $(".BannerWrapper__HomeBannerWrapper-sc-17qp9bv-0")[0].style.display = "none";
        $(".BannerWrapper__HomeBannerWrapper-sc-17qp9bv-0")[1].style.display = "block";

        // 헤더 카테고리들 없애기
        if($(".hnuhJm").length<2){
            $(".GlobalNavigationBar__MainNavMenu-og74wb-10").hide();
            $mainCategory.after("<div class='ShortcutList__NodeListWrapper-p3h6sa-1 hnuhJm'></div>");

            // 메인배너 아래 동그란 카테고리 2줄로 바꾸기
            $(".hnuhJm").eq(1).append($mainCategory.children().eq(9));
            $(".hnuhJm").eq(1).append($mainCategory.children().eq(8));
            $(".hnuhJm").eq(1).append($mainCategory.children().eq(7));
            $(".hnuhJm").eq(1).append($mainCategory.children().eq(6));
            $(".hnuhJm").eq(1).append($mainCategory.children().eq(5));
        }

        // 첫 번째 배너들 클래스명 바꾸기
        $(".gbEqzo").attr("class", "CardGroupProduct__Content-sc-1uq4zjb-1 gOedUB");

        //글자 크기 바꾸기
        $(".CardProduct__Title-a817d1-8").attr("class", "CardProduct__Title-a817d1-8 gkYnLQ");

        //마지막 배너 사이즈 바꾸기
        $(".HostBanner__Wrapper-sc-15wm6s2-0").attr("class", "HostBanner__Wrapper-sc-15wm6s2-0 jOmJCY");
        $(".eaJzkp").eq(0).hide();
        $(".eaJzkp").eq(1).show();

        //슬라이즈 배너 사이즈 바꾸기
        $(".Ratio-content .Image__StyledImage-v97gyx-1.VUNpz").css("width", "100%");
        $(".swiper-button-next").hide();
        $(".swiper-button-prev").hide();
        $(".swiper-pagination").hide();
        $(".dIznkQ").css("height", "120px");

        //매거진 바꾸기
        $(".jCmvf").attr("class", "CardGroup__CardWrapper-l79tlh-2 jCmvf magazineChange");
        $(".lmxZja").attr("class", "CardGroup__Content-l79tlh-1 lmxZja magazineWrapChange");

        //채팅 위치 조절
        $(".chat").css("bottom", "90px");

        //채팅 아이콘 위치 조절
        $(".chattingWrap2").css("bottom","95px");
    }
    else{
        $("#test").attr("class", ".ToastContainer__BottomToastWrapper-sqfjqn-0 .boEfOZ");

        // 헤더 전체
        $("header").attr("class", ".GlobalNavigationBar__Wrapper-og74wb-0 .dPJHWR");

        // 헤더 윗 부분 나타내기
        $("section.GlobalNavigationBar__TopNavContainer-og74wb-1").show();

        // 메인 상단 배너 바꾸기
        $(".BannerWrapper__HomeBannerWrapper-sc-17qp9bv-0")[0].style.display = "block";
        $(".BannerWrapper__HomeBannerWrapper-sc-17qp9bv-0")[1].style.display = "none";

        // 헤더 카테고리들 없애기
        $(".GlobalNavigationBar__MainNavMenu-og74wb-10").show();

        // 메인배러 아래 동그란 카테고리 한 줄로 바꾸기
        if($(".hnuhJm").length==2){
            $(".hnuhJm").eq(0).append($(".hnuhJm").eq(1).children(0));
            $(".hnuhJm").eq(0).append($(".hnuhJm").eq(1).children(1));
            $(".hnuhJm").eq(0).append($(".hnuhJm").eq(1).children(2));
            $(".hnuhJm").eq(0).append($(".hnuhJm").eq(1).children(3));
            $(".hnuhJm").eq(0).append($(".hnuhJm").eq(1).children(4));
            $(".hnuhJm").eq(1).remove();
        }

        // 첫 번째 배너들 클래스명 바꾸기
        $(".CardGroupProduct__Content-sc-1uq4zjb-1").attr("class", "CardGroupProduct__Content-sc-1uq4zjb-1 gbEqzo");

        //글자 크기 바꾸기
        $(".CardProduct__Title-a817d1-8").attr("class", "CardProduct__Title-a817d1-8 kxyGXE");

        //마지막 배너 사이즈 바꾸기
        $(".HostBanner__Wrapper-sc-15wm6s2-0").attr("class", "HostBanner__Wrapper-sc-15wm6s2-0 ikopRC");
        $(".eaJzkp").eq(0).show();
        $(".eaJzkp").eq(1).hide();

        //슬라이즈 배너 사이즈 바꾸기

        $(".swiper-button-next").show();
        $(".swiper-button-prev").show();
        $(".swiper-pagination").show();
        $(".dIznkQ").css("height", "180px");


        //매거진 바꾸기
        $(".jCmvf").attr("class", "CardGroup__CardWrapper-l79tlh-2 jCmvf");
        $(".lmxZja").attr("class", "CardGroup__Content-l79tlh-1 lmxZja");

        //채팅 위치 조절
        $(".chat").css("bottom", "30px");

        //채팅 아이콘 위치 조절
        $(".chattingWrap2").css("bottom","30px");
    }
}).resize();

function hideCategory(){
    $mainCategory.children().eq(5).hide();
    $mainCategory.children().eq(6).hide();
    $mainCategory.children().eq(7).hide();
    $mainCategory.children().eq(8).hide();
    $mainCategory.children().eq(9).hide();
}

function showCategory(){
    $mainCategory.children().eq(5).show();
    $mainCategory.children().eq(6).show();
    $mainCategory.children().eq(7).show();
    $mainCategory.children().eq(8).show();
    $mainCategory.children().eq(9).show();
}




/* function resizeApply() {
    var minWidth = 769;
    var body = document.getElementsByTagName('body')[0];
    if (window.innerWidth < minWidth) { body.style.zoom = (window.innerWidth / minWidth); }
    else body.style.zoom = 1;
  }
  
  window.onload = function() {
    window.addEventListener('resize', function() {
      resizeApply();
    });
  }
  
  resizeApply(); */
/**
 * Created by Mattxue on 2015/12/3.
 */
function countDown(secs,surl){
    //alert(surl);
    var jumpTo = document.getElementById('jumpTo');
    jumpTo.innerHTML=secs;
    if(--secs>0){
        setTimeout("countDown("+secs+",'"+surl+"')",1000);
    }
    else{
        location.href=surl;
    }
}